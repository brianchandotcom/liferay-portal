/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.opensaml.integration.internal.servlet.profile;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.opensaml.integration.SamlBinding;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.opensaml.integration.internal.util.SamlUtil;
import com.liferay.saml.opensaml.integration.metadata.MetadataManager;
import com.liferay.saml.persistence.model.SamlIdpSpSession;
import com.liferay.saml.persistence.model.SamlIdpSsoSession;
import com.liferay.saml.persistence.model.SamlSpSession;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalService;
import com.liferay.saml.persistence.service.SamlIdpSpSessionLocalService;
import com.liferay.saml.persistence.service.SamlIdpSsoSessionLocalService;
import com.liferay.saml.persistence.service.SamlSpSessionLocalService;
import com.liferay.saml.runtime.SamlException;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.exception.UnsolicitedLogoutResponseException;
import com.liferay.saml.runtime.exception.UnsupportedBindingException;
import com.liferay.saml.runtime.profile.SingleLogoutProfile;
import com.liferay.saml.util.JspUtil;
import com.liferay.saml.util.SamlHttpRequestUtil;

import java.io.Writer;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.shibboleth.utilities.java.support.resolver.CriteriaSet;

import org.apache.http.client.HttpClient;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.messaging.context.InOutOperationContext;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.decoder.servlet.HttpServletRequestMessageDecoder;
import org.opensaml.messaging.encoder.servlet.HttpServletResponseMessageEncoder;
import org.opensaml.messaging.pipeline.httpclient.BasicHttpClientMessagePipeline;
import org.opensaml.messaging.pipeline.httpclient.HttpClientMessagePipeline;
import org.opensaml.messaging.pipeline.httpclient.HttpClientMessagePipelineFactory;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.common.messaging.context.SAMLBindingContext;
import org.opensaml.saml.common.messaging.context.SAMLEndpointContext;
import org.opensaml.saml.common.messaging.context.SAMLMetadataContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLProtocolContext;
import org.opensaml.saml.common.messaging.context.SAMLSelfEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLSubjectNameIdentifierContext;
import org.opensaml.saml.common.xml.SAMLConstants;
import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.LogoutRequest;
import org.opensaml.saml.saml2.core.LogoutResponse;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.SessionIndex;
import org.opensaml.saml.saml2.core.Status;
import org.opensaml.saml.saml2.core.StatusCode;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml.saml2.metadata.SSODescriptor;
import org.opensaml.saml.saml2.metadata.SingleLogoutService;
import org.opensaml.security.credential.Credential;
import org.opensaml.soap.client.http.PipelineFactoryHttpSOAPClient;
import org.opensaml.xmlsec.context.SecurityParametersContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Mika Koivisto
 */
@Component(immediate = true, service = SingleLogoutProfile.class)
public class SingleLogoutProfileImpl
	extends BaseProfile implements SingleLogoutProfile {

	@Override
	public boolean isSingleLogoutSupported(HttpServletRequest request) {
		try {
			MetadataResolver metadataResolver =
				metadataManager.getMetadataResolver();

			String entityId = metadataManager.getDefaultIdpEntityId();

			EntityDescriptor entityDescriptor = metadataResolver.resolveSingle(
				new CriteriaSet(new EntityIdCriterion(entityId)));

			IDPSSODescriptor idpSSODescriptor =
				entityDescriptor.getIDPSSODescriptor(SAMLConstants.SAML20P_NS);

			SingleLogoutService singleLogoutService =
				SamlUtil.resolveSingleLogoutService(
					idpSSODescriptor, SAMLConstants.SAML2_REDIRECT_BINDING_URI);

			if (singleLogoutService != null) {
				String binding = singleLogoutService.getBinding();

				if (!binding.equals(SAMLConstants.SAML2_SOAP11_BINDING_URI)) {
					return true;
				}
			}
		}
		catch (Exception e) {
			String message =
				"Unable to verify single logout support: " + e.getMessage();

			if (_log.isDebugEnabled()) {
				_log.debug(message, e);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(message);
			}
		}

		return false;
	}

	@Override
	public void processIdpLogout(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException {

		String requestPath = _samlHttpRequestUtil.getRequestPath(request);

		try {
			response.addHeader(
				HttpHeaders.CACHE_CONTROL,
				HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
			response.addHeader(
				HttpHeaders.PRAGMA, HttpHeaders.PRAGMA_NO_CACHE_VALUE);

			if (requestPath.equals("/c/portal/logout")) {
				initiateIdpSingleLogout(request, response);
			}
			else if (requestPath.equals("/c/portal/saml/slo_logout")) {
				SamlSloContext samlSloContext = getSamlSloContext(
					request, null);

				if (samlSloContext == null) {
					redirectToLogout(request, response);

					return;
				}

				String cmd = ParamUtil.getString(request, Constants.CMD);

				if (Validator.isNull(cmd)) {
					request.setAttribute(
						SamlWebKeys.SAML_SLO_CONTEXT,
						samlSloContext.toJSONObject());

					JspUtil.dispatch(
						request, response, JspUtil.PATH_PORTAL_SAML_SLO,
						"single-sign-out");
				}
				else if (cmd.equals("logout")) {
					performIdpSpLogout(request, response, samlSloContext);
				}
				else if (cmd.equals("finish")) {
					performIdpFinishLogout(request, response, samlSloContext);
				}
				else if (cmd.equals("status")) {
					performIdpStatus(request, response, samlSloContext);
				}
			}
		}
		catch (Exception e) {
			ExceptionHandlerUtil.handleException(e);
		}
	}

	@Override
	public void processSingleLogout(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException {

		SamlBinding samlBinding = null;

		String method = request.getMethod();
		String requestPath = _samlHttpRequestUtil.getRequestPath(request);

		if (requestPath.endsWith("/slo") &&
			StringUtil.equalsIgnoreCase(method, HttpMethods.GET)) {

			samlBinding = getSamlBinding(
				SAMLConstants.SAML2_REDIRECT_BINDING_URI);
		}
		else if (requestPath.endsWith("/slo") &&
				 StringUtil.equalsIgnoreCase(method, HttpMethods.POST)) {

			samlBinding = getSamlBinding(SAMLConstants.SAML2_POST_BINDING_URI);
		}
		else if (requestPath.endsWith("/slo_soap") &&
				 StringUtil.equalsIgnoreCase(method, HttpMethods.POST)) {

			samlBinding = getSamlBinding(
				SAMLConstants.SAML2_SOAP11_BINDING_URI);
		}
		else {
			throw new UnsupportedBindingException();
		}

		try {
			MessageContext<?> messageContext = decodeSamlMessage(
				request, response, samlBinding, true);

			InOutOperationContext inOutOperationContext =
				messageContext.getSubcontext(InOutOperationContext.class);

			MessageContext inboundMessageContext =
				inOutOperationContext.getInboundMessageContext();

			Object inboundSamlMessage = inboundMessageContext.getMessage();

			if (inboundSamlMessage instanceof LogoutRequest) {
				processSingleLogoutRequest(request, response, messageContext);
			}
			else if (inboundSamlMessage instanceof LogoutResponse) {
				processSingleLogoutResponse(request, response, messageContext);
			}
			else {
				throw new SamlException(
					"Unrecognized inbound SAML message " +
						inboundSamlMessage.getClass());
			}
		}
		catch (Exception e) {
			ExceptionHandlerUtil.handleException(e);
		}
	}

	@Override
	public void processSpLogout(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException {

		try {
			sendSpLogoutRequest(request, response);
		}
		catch (Exception e) {
			ExceptionHandlerUtil.handleException(e);
		}
	}

	@Override
	@Reference(unbind = "-")
	public void setIdentifierGenerationStrategyFactory(
		IdentifierGenerationStrategyFactory
			identifierGenerationStrategyFactory) {

		super.setIdentifierGenerationStrategyFactory(
			identifierGenerationStrategyFactory);
	}

	@Override
	@Reference(unbind = "-")
	public void setMetadataManager(MetadataManager metadataManager) {
		super.setMetadataManager(metadataManager);
	}

	@Reference(
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		policyOption = ReferencePolicyOption.GREEDY, unbind = "unsetSamlBinding"
	)
	public void setSamlBinding(SamlBinding samlBinding) {
		addSamlBinding(samlBinding);
	}

	@Override
	@Reference(unbind = "-")
	public void setSamlProviderConfigurationHelper(
		SamlProviderConfigurationHelper samlProviderConfigurationHelper) {

		super.setSamlProviderConfigurationHelper(
			samlProviderConfigurationHelper);
	}

	@Override
	public void terminateSpSession(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			SamlSpSession samlSpSession = getSamlSpSession(request);

			if (samlSpSession == null) {
				return;
			}

			samlSpSessionLocalService.deleteSamlSpSession(samlSpSession);

			addCookie(
				request, response, SamlWebKeys.SAML_SP_SESSION_KEY,
				StringPool.BLANK, 0);
		}
		catch (SystemException se) {
			if (_log.isDebugEnabled()) {
				_log.debug(se.getMessage(), se);
			}
			else {
				_log.error(se.getMessage());
			}
		}
	}

	@Override
	public void terminateSsoSession(
		HttpServletRequest request, HttpServletResponse response) {

		String samlSsoSessionId = getSamlSsoSessionId(request);

		if (Validator.isNotNull(samlSsoSessionId)) {
			try {
				SamlIdpSsoSession samlIdpSsoSession =
					_samlIdpSsoSessionLocalService.fetchSamlIdpSso(
						samlSsoSessionId);

				if (samlIdpSsoSession != null) {
					_samlIdpSsoSessionLocalService.deleteSamlIdpSsoSession(
						samlIdpSsoSession);

					List<SamlIdpSpSession> samlIdpSpSessions =
						_samlIdpSpSessionLocalService.getSamlIdpSpSessions(
							samlIdpSsoSession.getSamlIdpSsoSessionId());

					for (SamlIdpSpSession samlIdpSpSession :
							samlIdpSpSessions) {

						_samlIdpSpSessionLocalService.deleteSamlIdpSpSession(
							samlIdpSpSession);
					}
				}
			}
			catch (SystemException se) {
				if (_log.isDebugEnabled()) {
					_log.debug(se.getMessage(), se);
				}
				else {
					_log.error(se.getMessage());
				}
			}
		}

		addCookie(
			request, response, SamlWebKeys.SAML_SSO_SESSION_ID,
			StringPool.BLANK, 0);
	}

	protected void addSessionIndex(
		LogoutRequest logoutRequest, String sessionIndexString) {

		if (Validator.isNull(sessionIndexString)) {
			return;
		}

		List<SessionIndex> sessionIndexes = logoutRequest.getSessionIndexes();

		SessionIndex sessionIndex = OpenSamlUtil.buildSessionIndex(
			sessionIndexString);

		sessionIndexes.add(sessionIndex);
	}

	protected SamlSloContext getSamlSloContext(
		HttpServletRequest request, MessageContext<?> messageContext) {

		HttpSession session = request.getSession();

		SamlSloContext samlSloContext = (SamlSloContext)session.getAttribute(
			SamlWebKeys.SAML_SLO_CONTEXT);

		String samlSsoSessionId = getSamlSsoSessionId(request);

		if (messageContext != null) {
			InOutOperationContext inOutOperationContext =
				messageContext.getSubcontext(InOutOperationContext.class);

			MessageContext<LogoutRequest> inboundMessageContext =
				inOutOperationContext.getInboundMessageContext();

			LogoutRequest logoutRequest = inboundMessageContext.getMessage();

			List<SessionIndex> sessionIndexes =
				logoutRequest.getSessionIndexes();

			if (!sessionIndexes.isEmpty()) {
				SessionIndex sessionIndex = sessionIndexes.get(0);

				samlSsoSessionId = sessionIndex.getSessionIndex();
			}
		}

		if ((samlSloContext == null) && Validator.isNotNull(samlSsoSessionId)) {
			SamlIdpSsoSession samlIdpSsoSession =
				_samlIdpSsoSessionLocalService.fetchSamlIdpSso(
					samlSsoSessionId);

			if (samlIdpSsoSession != null) {
				samlSloContext = new SamlSloContext(
					samlIdpSsoSession, messageContext,
					_samlIdpSpConnectionLocalService,
					_samlIdpSpSessionLocalService, _userLocalService);

				samlSloContext.setSamlSsoSessionId(samlSsoSessionId);
				samlSloContext.setUserId(portal.getUserId(request));

				session.setAttribute(
					SamlWebKeys.SAML_SLO_CONTEXT, samlSloContext);
			}
		}

		return samlSloContext;
	}

	protected void initiateIdpSingleLogout(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		SamlSloContext samlSloContext = getSamlSloContext(request, null);

		if (samlSloContext != null) {
			String portalURL = portal.getPortalURL(request);

			String pathMain = portal.getPathMain();

			String redirect = portalURL.concat(pathMain).concat(
				"/portal/saml/slo_logout");

			response.sendRedirect(redirect);
		}
		else {
			redirectToLogout(request, response);
		}
	}

	protected void performIdpFinishLogout(
			HttpServletRequest request, HttpServletResponse response,
			SamlSloContext samlSloContext)
		throws Exception {

		if (samlSloContext.getMessageContext() != null) {
			String statusCode = StatusCode.SUCCESS;

			for (SamlSloRequestInfo samlRequestInfo :
					samlSloContext.getSamlSloRequestInfos()) {

				String samlRequestInfoStatusCode =
					samlRequestInfo.getStatusCode();

				if (!samlRequestInfoStatusCode.equals(StatusCode.SUCCESS)) {
					statusCode = StatusCode.PARTIAL_LOGOUT;

					break;
				}
			}

			sendIdpLogoutResponse(
				request, response, statusCode, samlSloContext);
		}
		else {
			redirectToLogout(request, response);
		}
	}

	protected void performIdpSpLogout(
			HttpServletRequest request, HttpServletResponse response,
			SamlSloContext samlSloContext)
		throws Exception {

		String entityId = ParamUtil.getString(request, "entityId");

		SamlSloRequestInfo samlSloRequestInfo =
			samlSloContext.getSamlSloRequestInfo(entityId);

		if (samlSloRequestInfo == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Received logout request for service provider " + entityId +
						" that the user is not logged into");
			}

			JspUtil.dispatch(
				request, response, JspUtil.PATH_PORTAL_SAML_ERROR,
				"single-sign-out", true);

			return;
		}

		if (samlSloRequestInfo.getStatus() ==
				SamlSloRequestInfo.REQUEST_STATUS_SUCCESS) {

			request.setAttribute(
				SamlWebKeys.SAML_SLO_REQUEST_INFO,
				samlSloRequestInfo.toJSONObject());

			JspUtil.dispatch(
				request, response, JspUtil.PATH_PORTAL_SAML_SLO_SP_STATUS,
				"single-sign-out", true);

			return;
		}

		MessageContext<?> messageContext = getMessageContext(
			request, response, entityId);

		SAMLPeerEntityContext samlPeerEntityContext =
			messageContext.getSubcontext(SAMLPeerEntityContext.class);

		SAMLMetadataContext samlPeerMetadataContext =
			samlPeerEntityContext.getSubcontext(SAMLMetadataContext.class);

		SPSSODescriptor spSSODescriptor =
			(SPSSODescriptor)samlPeerMetadataContext.getRoleDescriptor();

		SingleLogoutService singleLogoutService =
			SamlUtil.resolveSingleLogoutService(
				spSSODescriptor, SAMLConstants.SAML2_SOAP11_BINDING_URI);

		if (singleLogoutService == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Single logout not supported by " + entityId);
			}

			samlSloRequestInfo.setStatus(
				SamlSloRequestInfo.REQUEST_STATUS_UNSUPPORTED);
			samlSloRequestInfo.setStatusCode(StatusCode.UNSUPPORTED_BINDING);

			request.setAttribute(
				SamlWebKeys.SAML_SLO_REQUEST_INFO,
				samlSloRequestInfo.toJSONObject());

			JspUtil.dispatch(
				request, response, JspUtil.PATH_PORTAL_SAML_SLO_SP_STATUS,
				"single-sign-out", true);
		}
		else {
			try {
				sendIdpLogoutRequest(
					request, response, samlSloContext, samlSloRequestInfo);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					StringBundler sb = new StringBundler(7);

					sb.append("Unable to perform a single logout for service ");
					sb.append("provider ");
					sb.append(entityId);
					sb.append(" with binding ");
					sb.append(singleLogoutService.getBinding());
					sb.append(" to ");
					sb.append(singleLogoutService.getLocation());

					_log.debug(sb.toString(), e);
				}

				samlSloRequestInfo.setStatus(
					SamlSloRequestInfo.REQUEST_STATUS_FAILED);
				samlSloRequestInfo.setStatusCode(StatusCode.PARTIAL_LOGOUT);

				request.setAttribute(
					SamlWebKeys.SAML_SLO_REQUEST_INFO,
					samlSloRequestInfo.toJSONObject());

				JspUtil.dispatch(
					request, response, JspUtil.PATH_PORTAL_SAML_SLO_SP_STATUS,
					"single-sign-out", true);
			}
		}
	}

	protected void performIdpStatus(
			HttpServletRequest request, HttpServletResponse response,
			SamlSloContext samlSloContext)
		throws Exception {

		for (SamlSloRequestInfo samlRequestInfo :
				samlSloContext.getSamlSloRequestInfos()) {

			int status = samlRequestInfo.getStatus();

			if (status == SamlSloRequestInfo.REQUEST_STATUS_INITIATED) {
				DateTime initiateDateTime = samlRequestInfo.getInitiateTime();

				DateTime expireDateTime = initiateDateTime.plusSeconds(10);

				if (expireDateTime.isBeforeNow()) {
					samlRequestInfo.setStatus(
						SamlSloRequestInfo.REQUEST_STATUS_TIMED_OUT);
					samlRequestInfo.setStatusCode(StatusCode.PARTIAL_LOGOUT);
				}
			}
		}

		response.setContentType(ContentTypes.TEXT_JAVASCRIPT);

		Writer writer = response.getWriter();

		JSONObject jsonObject = samlSloContext.toJSONObject();

		writer.write(jsonObject.toString());
	}

	protected void processIdpLogoutRequest(
			HttpServletRequest request, HttpServletResponse response,
			MessageContext<?> messageContext)
		throws Exception {

		SamlSloContext samlSloContext = getSamlSloContext(
			request, messageContext);

		Set<String> samlSpEntityIds = samlSloContext.getSamlSpEntityIds();

		SAMLBindingContext samlBindingContext = messageContext.getSubcontext(
			SAMLBindingContext.class);

		String binding = samlBindingContext.getBindingUri();

		if (binding.equals(SAMLConstants.SAML2_SOAP11_BINDING_URI)) {
			sendIdpLogoutResponse(
				request, response, StatusCode.UNSUPPORTED_BINDING,
				samlSloContext);
		}
		else if (samlSloContext == null) {
			sendIdpLogoutResponse(
				request, response, StatusCode.UNKNOWN_PRINCIPAL,
				new SamlSloContext(
					null, messageContext, _samlIdpSpConnectionLocalService,
					_samlIdpSpSessionLocalService, _userLocalService));
		}
		else if (!samlSpEntityIds.isEmpty()) {
			initiateIdpSingleLogout(request, response);
		}
		else {
			sendIdpLogoutResponse(
				request, response, StatusCode.SUCCESS, samlSloContext);
		}
	}

	protected void processIdpLogoutResponse(
			HttpServletRequest request, HttpServletResponse response,
			MessageContext<?> messageContext)
		throws Exception {

		SamlSloContext samlSloContext = getSamlSloContext(request, null);

		SAMLPeerEntityContext samlPeerEntityContext =
			messageContext.getSubcontext(SAMLPeerEntityContext.class);

		if (samlSloContext == null) {
			throw new UnsolicitedLogoutResponseException(
				"Received logout response from " +
					samlPeerEntityContext.getEntityId() +
						" without an active SSO session");
		}

		InOutOperationContext inOutOperationContext =
			messageContext.getSubcontext(InOutOperationContext.class);

		MessageContext<LogoutResponse> inboundMessageContext =
			inOutOperationContext.getInboundMessageContext();

		LogoutResponse logoutResponse = inboundMessageContext.getMessage();

		Issuer issuer = logoutResponse.getIssuer();

		String entityId = issuer.getValue();

		SamlSloRequestInfo samlSloRequestInfo =
			samlSloContext.getSamlSloRequestInfo(entityId);

		if (samlSloRequestInfo == null) {
			throw new UnsolicitedLogoutResponseException(
				"Received unsolicited logout response from " +
					samlPeerEntityContext.getEntityId());
		}

		Status status = logoutResponse.getStatus();

		StatusCode statusCode = status.getStatusCode();

		samlSloRequestInfo.setStatusCode(statusCode.getValue());

		request.setAttribute(
			SamlWebKeys.SAML_SLO_REQUEST_INFO,
			samlSloRequestInfo.toJSONObject());

		JspUtil.dispatch(
			request, response, JspUtil.PATH_PORTAL_SAML_SLO_SP_STATUS,
			"single-sign-out", true);
	}

	protected void processSingleLogoutRequest(
			HttpServletRequest request, HttpServletResponse response,
			MessageContext<?> messageContext)
		throws Exception {

		if (samlProviderConfigurationHelper.isRoleIdp()) {
			processIdpLogoutRequest(request, response, messageContext);
		}
		else if (samlProviderConfigurationHelper.isRoleSp()) {
			processSpLogoutRequest(request, response, messageContext);
		}
	}

	protected void processSingleLogoutResponse(
			HttpServletRequest request, HttpServletResponse response,
			MessageContext<?> messageContext)
		throws Exception {

		if (samlProviderConfigurationHelper.isRoleIdp()) {
			processIdpLogoutResponse(request, response, messageContext);
		}
		else if (samlProviderConfigurationHelper.isRoleSp()) {
			processSpLogoutResponse(request, response);
		}
	}

	protected void processSpLogoutRequest(
			HttpServletRequest request, HttpServletResponse response,
			MessageContext<?> messageContext)
		throws Exception {

		InOutOperationContext inOutOperationContext =
			messageContext.getSubcontext(InOutOperationContext.class);

		MessageContext<LogoutRequest> inboundMessageContext =
			inOutOperationContext.getInboundMessageContext();

		LogoutRequest logoutRequest = inboundMessageContext.getMessage();

		NameID nameID = logoutRequest.getNameID();

		List<SessionIndex> sessionIndexes = logoutRequest.getSessionIndexes();

		String statusCodeURI = StatusCode.SUCCESS;

		if (sessionIndexes.isEmpty()) {
			List<SamlSpSession> samlSpSessions =
				samlSpSessionLocalService.getSamlSpSessions(nameID.getValue());

			if (samlSpSessions.isEmpty()) {
				statusCodeURI = StatusCode.UNKNOWN_PRINCIPAL;
			}

			for (SamlSpSession samlSpSession : samlSpSessions) {
				samlSpSession.setTerminated(true);

				samlSpSessionLocalService.updateSamlSpSession(samlSpSession);
			}
		}

		for (SessionIndex sessionIndex : sessionIndexes) {
			SamlSpSession samlSpSession =
				samlSpSessionLocalService.fetchSamlSpSessionBySessionIndex(
					sessionIndex.getSessionIndex());

			if (samlSpSession == null) {
				statusCodeURI = StatusCode.UNKNOWN_PRINCIPAL;

				continue;
			}

			if (Objects.equals(
					samlSpSession.getNameIdValue(), nameID.getValue()) &&
				Objects.equals(
					samlSpSession.getNameIdFormat(), nameID.getFormat())) {

				samlSpSession.setTerminated(true);

				samlSpSessionLocalService.updateSamlSpSession(samlSpSession);
			}
			else if (!statusCodeURI.equals(StatusCode.PARTIAL_LOGOUT)) {
				statusCodeURI = StatusCode.UNKNOWN_PRINCIPAL;

				continue;
			}

			if (statusCodeURI.equals(StatusCode.UNKNOWN_PRINCIPAL)) {
				statusCodeURI = StatusCode.PARTIAL_LOGOUT;
			}
		}

		LogoutResponse logoutResponse = OpenSamlUtil.buildLogoutResponse();

		MessageContext outboundMessageContext =
			inOutOperationContext.getOutboundMessageContext();

		SecurityParametersContext securityParametersContext =
			outboundMessageContext.getSubcontext(
				SecurityParametersContext.class, true);

		OpenSamlUtil.prepareSecurityParametersContext(
			metadataManager.getSigningCredential(), securityParametersContext);

		outboundMessageContext.setMessage(logoutResponse);

		logoutResponse.setID(generateIdentifier(20));
		logoutResponse.setInResponseTo(logoutRequest.getID());
		logoutResponse.setIssueInstant(new DateTime(DateTimeZone.UTC));

		SAMLSelfEntityContext samlSelfEntityContext =
			messageContext.getSubcontext(SAMLSelfEntityContext.class);

		Issuer issuer = OpenSamlUtil.buildIssuer(
			samlSelfEntityContext.getEntityId());

		logoutResponse.setIssuer(issuer);

		StatusCode statusCode = OpenSamlUtil.buildStatusCode(statusCodeURI);

		Status status = OpenSamlUtil.buildStatus(statusCode);

		logoutResponse.setStatus(status);

		logoutResponse.setVersion(SAMLVersion.VERSION_20);

		SAMLPeerEntityContext samlPeerEntityContext =
			messageContext.getSubcontext(SAMLPeerEntityContext.class);

		SAMLMetadataContext samlPeerMetadataContext =
			samlPeerEntityContext.getSubcontext(SAMLMetadataContext.class);

		SSODescriptor ssoDescriptor =
			(SSODescriptor)samlPeerMetadataContext.getRoleDescriptor();

		SAMLBindingContext samlBindingContext = messageContext.getSubcontext(
			SAMLBindingContext.class);

		SingleLogoutService singleLogoutService =
			SamlUtil.resolveSingleLogoutService(
				ssoDescriptor, samlBindingContext.getBindingUri());

		SAMLEndpointContext samlPeerEndpointContext =
			samlPeerEntityContext.getSubcontext(
				SAMLEndpointContext.class, true);

		samlPeerEndpointContext.setEndpoint(singleLogoutService);

		logoutResponse.setDestination(singleLogoutService.getLocation());

		outboundMessageContext.addSubcontext(samlSelfEntityContext);
		outboundMessageContext.addSubcontext(samlPeerEntityContext);

		sendSamlMessage(messageContext, response);
	}

	protected void processSpLogoutResponse(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		redirectToLogout(request, response);
	}

	protected void redirectToLogout(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		if (samlProviderConfigurationHelper.isRoleIdp()) {
			terminateSsoSession(request, response);
		}
		else if (samlProviderConfigurationHelper.isRoleSp()) {
			terminateSpSession(request, response);
		}

		String portalURL = portal.getPortalURL(request);

		String pathMain = portal.getPathMain();

		String redirect = portalURL.concat(pathMain).concat("/portal/logout");

		response.sendRedirect(redirect);
	}

	protected void sendAsyncLogoutRequest(
			MessageContext messageContext, SamlSloContext samlSloContext,
			HttpServletResponse response)
		throws Exception {

		SAMLPeerEntityContext samlPeerEntityContext =
			messageContext.getSubcontext(SAMLPeerEntityContext.class);

		SAMLEndpointContext samlPeerEndpointContext =
			samlPeerEntityContext.getSubcontext(SAMLEndpointContext.class);

		SingleLogoutService singleLogoutService =
			(SingleLogoutService)samlPeerEndpointContext.getEndpoint();

		LogoutRequest logoutRequest = OpenSamlUtil.buildLogoutRequest();

		logoutRequest.setDestination(singleLogoutService.getLocation());
		logoutRequest.setID(generateIdentifier(20));
		logoutRequest.setIssueInstant(new DateTime(DateTimeZone.UTC));

		SAMLSelfEntityContext samlSelfEntityContext =
			messageContext.getSubcontext(SAMLSelfEntityContext.class);

		Issuer issuer = OpenSamlUtil.buildIssuer(
			samlSelfEntityContext.getEntityId());

		logoutRequest.setIssuer(issuer);

		SAMLSubjectNameIdentifierContext samlSubjectNameIdentifierContext =
			messageContext.getSubcontext(
				SAMLSubjectNameIdentifierContext.class);

		logoutRequest.setNameID(
			samlSubjectNameIdentifierContext.getSAML2SubjectNameID());

		logoutRequest.setVersion(SAMLVersion.VERSION_20);

		addSessionIndex(logoutRequest, samlSloContext.getSamlSsoSessionId());

		messageContext.setMessage(logoutRequest);

		Credential credential = metadataManager.getSigningCredential();

		SAMLProtocolContext samlProtocolContext = messageContext.getSubcontext(
			SAMLProtocolContext.class, true);

		samlProtocolContext.setProtocol(SAMLConstants.SAML20P_NS);

		SAMLMetadataContext samlMetadataContext =
			samlPeerEntityContext.getSubcontext(SAMLMetadataContext.class);

		OpenSamlUtil.signObject(
			logoutRequest, credential, samlMetadataContext.getRoleDescriptor());

		SamlBinding samlBinding = getSamlBinding(
			singleLogoutService.getBinding());

		Supplier<HttpServletResponseMessageEncoder>
			httpServletResponseMessageEncoderSupplier =
				samlBinding.getHttpServletResponseMessageEncoderSupplier();

		HttpServletResponseMessageEncoder httpServletResponseMessageEncoder =
			httpServletResponseMessageEncoderSupplier.get();

		SecurityParametersContext securityParametersContext =
			messageContext.getSubcontext(SecurityParametersContext.class);

		OpenSamlUtil.prepareSecurityParametersContext(
			credential, securityParametersContext);

		httpServletResponseMessageEncoder.setHttpServletResponse(response);
		httpServletResponseMessageEncoder.setMessageContext(messageContext);

		httpServletResponseMessageEncoder.initialize();

		httpServletResponseMessageEncoder.encode();
	}

	protected void sendIdpLogoutRequest(
			HttpServletRequest request, HttpServletResponse response,
			SamlSloContext samlSloContext,
			SamlSloRequestInfo samlSloRequestInfo)
		throws Exception {

		MessageContext<?> messageContext = getMessageContext(
			request, response, samlSloRequestInfo.getEntityId());

		SAMLPeerEntityContext samlPeerEntityContext =
			messageContext.getSubcontext(SAMLPeerEntityContext.class);

		SAMLMetadataContext samlMetadataContext =
			samlPeerEntityContext.getSubcontext(SAMLMetadataContext.class);

		SPSSODescriptor spSSODescriptor =
			(SPSSODescriptor)samlMetadataContext.getRoleDescriptor();

		SingleLogoutService singleLogoutService =
			SamlUtil.resolveSingleLogoutService(
				spSSODescriptor, SAMLConstants.SAML2_REDIRECT_BINDING_URI);

		SAMLEndpointContext samlPeerEndpointContext =
			samlPeerEntityContext.getSubcontext(
				SAMLEndpointContext.class, true);

		samlPeerEndpointContext.setEndpoint(singleLogoutService);

		SamlIdpSpSession samlIdpSpSession =
			samlSloRequestInfo.getSamlIdpSpSession();

		NameID nameID = OpenSamlUtil.buildNameId(
			samlIdpSpSession.getNameIdFormat(),
			samlIdpSpSession.getNameIdValue());

		SAMLSubjectNameIdentifierContext samlSubjectNameIdentifierContext =
			messageContext.getSubcontext(
				SAMLSubjectNameIdentifierContext.class, true);

		samlSubjectNameIdentifierContext.setSubjectNameIdentifier(nameID);

		samlSloRequestInfo.setInitiateTime(new DateTime(DateTimeZone.UTC));
		samlSloRequestInfo.setStatus(
			SamlSloRequestInfo.REQUEST_STATUS_INITIATED);

		String binding = singleLogoutService.getBinding();

		if (binding.equals(SAMLConstants.SAML2_SOAP11_BINDING_URI)) {
			String statusCode = sendSyncLogoutRequest(
				messageContext, samlSloContext);

			samlSloRequestInfo.setStatusCode(statusCode);

			request.setAttribute(
				SamlWebKeys.SAML_SLO_REQUEST_INFO,
				samlSloRequestInfo.toJSONObject());

			JspUtil.dispatch(
				request, response, JspUtil.PATH_PORTAL_SAML_SLO_SP_STATUS,
				"single-sign-out", true);
		}
		else {
			sendAsyncLogoutRequest(messageContext, samlSloContext, response);
		}
	}

	protected void sendIdpLogoutResponse(
			HttpServletRequest request, HttpServletResponse response,
			String statusCodeURI, SamlSloContext samlSloContext)
		throws Exception {

		LogoutResponse logoutResponse = OpenSamlUtil.buildLogoutResponse();

		MessageContext<?> messageContext = samlSloContext.getMessageContext();

		InOutOperationContext inOutOperationContext =
			messageContext.getSubcontext(InOutOperationContext.class);

		MessageContext<LogoutRequest> inboundMessageContext =
			inOutOperationContext.getInboundMessageContext();

		SAMLPeerEntityContext samlPeerEntityContext =
			messageContext.getSubcontext(SAMLPeerEntityContext.class);

		SAMLMetadataContext samlPeerMetadataContext =
			samlPeerEntityContext.getSubcontext(SAMLMetadataContext.class);

		SSODescriptor ssoDescriptor =
			(SSODescriptor)samlPeerMetadataContext.getRoleDescriptor();

		SAMLBindingContext samlBindingContext = messageContext.getSubcontext(
			SAMLBindingContext.class);

		SingleLogoutService singleLogoutService =
			SamlUtil.resolveSingleLogoutService(
				ssoDescriptor, samlBindingContext.getBindingUri());

		logoutResponse.setDestination(singleLogoutService.getLocation());

		logoutResponse.setID(generateIdentifier(20));

		LogoutRequest logoutRequest = inboundMessageContext.getMessage();

		logoutResponse.setInResponseTo(logoutRequest.getID());

		logoutResponse.setIssueInstant(new DateTime(DateTimeZone.UTC));

		SAMLSelfEntityContext samlSelfEntityContext =
			messageContext.getSubcontext(SAMLSelfEntityContext.class);

		Issuer issuer = OpenSamlUtil.buildIssuer(
			samlSelfEntityContext.getEntityId());

		logoutResponse.setIssuer(issuer);

		StatusCode statusCode = OpenSamlUtil.buildStatusCode(statusCodeURI);

		Status status = OpenSamlUtil.buildStatus(statusCode);

		logoutResponse.setStatus(status);

		logoutResponse.setVersion(SAMLVersion.VERSION_20);

		MessageContext outboundMessageContext =
			inOutOperationContext.getOutboundMessageContext();

		outboundMessageContext.setMessage(logoutResponse);

		outboundMessageContext.addSubcontext(samlPeerEntityContext);

		SecurityParametersContext securityParametersContext =
			outboundMessageContext.getSubcontext(
				SecurityParametersContext.class, true);

		OpenSamlUtil.prepareSecurityParametersContext(
			metadataManager.getSigningCredential(), securityParametersContext);

		SAMLProtocolContext samlProtocolContext =
			outboundMessageContext.getSubcontext(
				SAMLProtocolContext.class, true);

		samlProtocolContext.setProtocol(SAMLConstants.SAML20P_NS);

		SAMLEndpointContext samlPeerEndpointContext =
			samlPeerEntityContext.getSubcontext(
				SAMLEndpointContext.class, true);

		samlPeerEndpointContext.setEndpoint(singleLogoutService);

		if (!statusCodeURI.equals(StatusCode.UNSUPPORTED_BINDING)) {
			terminateSsoSession(request, response);

			logout(request, response);
		}

		sendSamlMessage(messageContext, response);
	}

	protected void sendSpLogoutRequest(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		SamlSpSession samlSpSession = getSamlSpSession(request);

		if ((samlSpSession == null) || samlSpSession.isTerminated()) {
			redirectToLogout(request, response);

			return;
		}

		LogoutRequest logoutRequest = OpenSamlUtil.buildLogoutRequest();

		String entityId = metadataManager.getDefaultIdpEntityId();

		MessageContext<?> messageContext = getMessageContext(
			request, response, entityId);

		InOutOperationContext inOutOperationContext = new InOutOperationContext(
			new MessageContext(), new MessageContext());

		messageContext.addSubcontext(inOutOperationContext);

		MessageContext outboundMessageContext =
			inOutOperationContext.getOutboundMessageContext();

		outboundMessageContext.setMessage(logoutRequest);

		SAMLPeerEntityContext samlPeerEntityContext =
			messageContext.getSubcontext(SAMLPeerEntityContext.class);

		outboundMessageContext.addSubcontext(samlPeerEntityContext);

		SAMLMetadataContext samlPeerMetadataContext =
			samlPeerEntityContext.getSubcontext(
				SAMLMetadataContext.class, true);

		IDPSSODescriptor idpSSODescriptor =
			(IDPSSODescriptor)samlPeerMetadataContext.getRoleDescriptor();

		SingleLogoutService singleLogoutService =
			SamlUtil.resolveSingleLogoutService(
				idpSSODescriptor, SAMLConstants.SAML2_POST_BINDING_URI);

		logoutRequest.setDestination(singleLogoutService.getLocation());

		logoutRequest.setID(generateIdentifier(20));

		DateTime issueInstantDateTime = new DateTime(DateTimeZone.UTC);

		logoutRequest.setIssueInstant(issueInstantDateTime);

		SAMLSelfEntityContext samlSelfEntityContext =
			messageContext.getSubcontext(SAMLSelfEntityContext.class);

		Issuer issuer = OpenSamlUtil.buildIssuer(
			samlSelfEntityContext.getEntityId());

		outboundMessageContext.addSubcontext(samlSelfEntityContext);

		SecurityParametersContext securityParametersContext =
			outboundMessageContext.getSubcontext(
				SecurityParametersContext.class, true);

		OpenSamlUtil.prepareSecurityParametersContext(
			metadataManager.getSigningCredential(), securityParametersContext);

		logoutRequest.setIssuer(issuer);

		String nameIdFormat = samlSpSession.getNameIdFormat();
		String nameIdNameQualifier = samlSpSession.getNameIdNameQualifier();
		String nameIdSPNameQualifier = samlSpSession.getNameIdSPNameQualifier();
		String nameIdValue = samlSpSession.getNameIdValue();

		NameID nameID = OpenSamlUtil.buildNameId(
			nameIdFormat, nameIdNameQualifier, nameIdSPNameQualifier,
			nameIdValue);

		logoutRequest.setNameID(nameID);

		logoutRequest.setVersion(SAMLVersion.VERSION_20);

		addSessionIndex(logoutRequest, samlSpSession.getSessionIndex());

		SAMLEndpointContext samlPeerEndpointSubcontext =
			samlPeerEntityContext.getSubcontext(
				SAMLEndpointContext.class, true);

		samlPeerEndpointSubcontext.setEndpoint(singleLogoutService);

		sendSamlMessage(messageContext, response);
	}

	protected String sendSyncLogoutRequest(
			MessageContext<?> messageContext, SamlSloContext samlSloContext)
		throws Exception {

		SAMLPeerEntityContext samlPeerEntityContext =
			messageContext.getSubcontext(SAMLPeerEntityContext.class);

		SAMLEndpointContext samlPeerEndpointSubcontext =
			samlPeerEntityContext.getSubcontext(SAMLEndpointContext.class);

		SingleLogoutService singleLogoutService =
			(SingleLogoutService)samlPeerEndpointSubcontext.getEndpoint();

		LogoutRequest logoutRequest = OpenSamlUtil.buildLogoutRequest();

		logoutRequest.setDestination(singleLogoutService.getLocation());
		logoutRequest.setID(generateIdentifier(20));
		logoutRequest.setIssueInstant(new DateTime(DateTimeZone.UTC));

		SAMLSelfEntityContext samlSelfEntityContext =
			messageContext.getSubcontext(SAMLSelfEntityContext.class);

		Issuer issuer = OpenSamlUtil.buildIssuer(
			samlSelfEntityContext.getEntityId());

		logoutRequest.setIssuer(issuer);

		SAMLSubjectNameIdentifierContext samlSubjectNameIdentifierContext =
			messageContext.getSubcontext(
				SAMLSubjectNameIdentifierContext.class);

		logoutRequest.setNameID(
			samlSubjectNameIdentifierContext.getSAML2SubjectNameID());

		logoutRequest.setVersion(SAMLVersion.VERSION_20);

		addSessionIndex(logoutRequest, samlSloContext.getSamlSsoSessionId());

		InOutOperationContext inOutOperationContext =
			messageContext.getSubcontext(InOutOperationContext.class);

		MessageContext outboundMessageContext =
			inOutOperationContext.getOutboundMessageContext();

		outboundMessageContext.addSubcontext(samlPeerEndpointSubcontext);

		outboundMessageContext.setMessage(logoutRequest);

		Credential credential = metadataManager.getSigningCredential();

		SecurityParametersContext securityParametersContext =
			outboundMessageContext.getSubcontext(
				SecurityParametersContext.class, true);

		OpenSamlUtil.prepareSecurityParametersContext(
			credential, securityParametersContext);

		SAMLProtocolContext samlProtocolContext =
			outboundMessageContext.getSubcontext(SAMLProtocolContext.class);

		samlProtocolContext.setProtocol(SAMLConstants.SAML20P_NS);

		SAMLMetadataContext samlPeerMetadataContext =
			samlPeerEntityContext.getSubcontext(SAMLMetadataContext.class);

		OpenSamlUtil.signObject(
			logoutRequest, credential,
			samlPeerMetadataContext.getRoleDescriptor());

		SamlBinding samlBinding = getSamlBinding(
			SAMLConstants.SAML2_SOAP11_BINDING_URI);

		PipelineFactoryHttpSOAPClient<Object, Object>
			pipelineFactoryHttpSOAPClient =
				new PipelineFactoryHttpSOAPClient<>();

		pipelineFactoryHttpSOAPClient.setPipelineFactory(
			new HttpClientMessagePipelineFactory<Object, Object>() {

				@Nonnull
				@Override
				public HttpClientMessagePipeline<Object, Object> newInstance() {
					Supplier<HttpServletResponseMessageEncoder>
						httpServletResponseMessageEncoderSupplier =
							samlBinding.
								getHttpServletResponseMessageEncoderSupplier();

					Supplier<HttpServletRequestMessageDecoder>
						httpServletResponseMessageDecoder =
							samlBinding.
								getHttpServletRequestMessageDecoderSupplier();

					return new BasicHttpClientMessagePipeline<>(
						httpServletResponseMessageEncoderSupplier.get(),
						httpServletResponseMessageDecoder.get());
				}

				@Nonnull
				@Override
				public HttpClientMessagePipeline<Object, Object> newInstance(
					@Nullable String pipelineName) {

					return newInstance();
				}

			});

		pipelineFactoryHttpSOAPClient.setHttpClient(_httpClient);

		pipelineFactoryHttpSOAPClient.initialize();

		pipelineFactoryHttpSOAPClient.send(
			singleLogoutService.getLocation(), inOutOperationContext);

		MessageContext<LogoutResponse> inboundMessageContext =
			inOutOperationContext.getInboundMessageContext();

		LogoutResponse logoutResponse = inboundMessageContext.getMessage();

		Status status = logoutResponse.getStatus();

		StatusCode statusCode = status.getStatusCode();

		return statusCode.getValue();
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		super.portal = portal;
	}

	@Reference(unbind = "-")
	protected void setSamlHttpRequestUtil(
		SamlHttpRequestUtil samlHttpRequestUtil) {

		_samlHttpRequestUtil = samlHttpRequestUtil;
	}

	@Reference(unbind = "-")
	protected void setSamlIdpSpConnectionLocalService(
		SamlIdpSpConnectionLocalService samlIdpSpConnectionLocalService) {

		_samlIdpSpConnectionLocalService = samlIdpSpConnectionLocalService;
	}

	@Reference(unbind = "-")
	protected void setSamlIdpSpSessionLocalService(
		SamlIdpSpSessionLocalService samlIdpSpSessionLocalService) {

		_samlIdpSpSessionLocalService = samlIdpSpSessionLocalService;
	}

	@Reference(unbind = "-")
	protected void setSamlIdpSsoSessionLocalService(
		SamlIdpSsoSessionLocalService samlIdpSsoSessionLocalService) {

		_samlIdpSsoSessionLocalService = samlIdpSsoSessionLocalService;
	}

	@Reference(unbind = "-")
	protected void setSamlSpSessionLocalService(
		SamlSpSessionLocalService samlSpSessionLocalService) {

		super.samlSpSessionLocalService = samlSpSessionLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SingleLogoutProfileImpl.class);

	@Reference
	private HttpClient _httpClient;

	private SamlHttpRequestUtil _samlHttpRequestUtil;
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;
	private SamlIdpSpSessionLocalService _samlIdpSpSessionLocalService;
	private SamlIdpSsoSessionLocalService _samlIdpSsoSessionLocalService;
	private UserLocalService _userLocalService;

}