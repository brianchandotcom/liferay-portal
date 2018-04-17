/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.saml.opensaml.integration.internal.profile;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.opensaml.integration.SamlBinding;
import com.liferay.saml.opensaml.integration.internal.resolver.AttributePublisherImpl;
import com.liferay.saml.opensaml.integration.internal.resolver.AttributeResolverRegistry;
import com.liferay.saml.opensaml.integration.internal.resolver.AttributeResolverSAMLContextImpl;
import com.liferay.saml.opensaml.integration.internal.resolver.NameIdResolverRegistry;
import com.liferay.saml.opensaml.integration.internal.resolver.NameIdResolverSAMLContextImpl;
import com.liferay.saml.opensaml.integration.internal.resolver.UserResolverSAMLContextImpl;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.opensaml.integration.internal.util.SamlUtil;
import com.liferay.saml.opensaml.integration.metadata.MetadataManager;
import com.liferay.saml.opensaml.integration.resolver.AttributeResolver;
import com.liferay.saml.opensaml.integration.resolver.NameIdResolver;
import com.liferay.saml.opensaml.integration.resolver.UserResolver;
import com.liferay.saml.persistence.exception.NoSuchIdpSpSessionException;
import com.liferay.saml.persistence.exception.NoSuchSpIdpConnectionException;
import com.liferay.saml.persistence.model.SamlIdpSsoSession;
import com.liferay.saml.persistence.model.SamlSpAuthRequest;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.model.SamlSpMessage;
import com.liferay.saml.persistence.model.SamlSpSession;
import com.liferay.saml.persistence.service.SamlIdpSpSessionLocalService;
import com.liferay.saml.persistence.service.SamlIdpSsoSessionLocalService;
import com.liferay.saml.persistence.service.SamlSpAuthRequestLocalService;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.persistence.service.SamlSpMessageLocalService;
import com.liferay.saml.persistence.service.SamlSpSessionLocalService;
import com.liferay.saml.runtime.SamlException;
import com.liferay.saml.runtime.configuration.SamlConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.exception.AssertionException;
import com.liferay.saml.runtime.exception.AudienceException;
import com.liferay.saml.runtime.exception.DestinationException;
import com.liferay.saml.runtime.exception.ExpiredException;
import com.liferay.saml.runtime.exception.InResponseToException;
import com.liferay.saml.runtime.exception.IssuerException;
import com.liferay.saml.runtime.exception.ReplayException;
import com.liferay.saml.runtime.exception.SignatureException;
import com.liferay.saml.runtime.exception.StatusException;
import com.liferay.saml.runtime.exception.SubjectException;
import com.liferay.saml.runtime.profile.WebSsoProfile;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;

import org.opensaml.common.IdentifierGenerator;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.Audience;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NameIDPolicy;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.saml2.core.SubjectConfirmationData;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml2.metadata.SingleSignOnService;
import org.opensaml.security.MetadataCriteria;
import org.opensaml.security.SAMLSignatureProfileValidator;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.opensaml.xml.security.criteria.UsageCriteria;
import org.opensaml.xml.security.trust.TrustEngine;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureTrustEngine;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Mika Koivisto
 */
@Component(
	configurationPid = "com.liferay.saml.runtime.configuration.SamlConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true
)
public class WebSsoProfileImpl extends BaseProfile implements WebSsoProfile {

	@Override
	public void processAuthnRequest(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException {

		try {
			doProcessAuthnRequest(request, response);
		}
		catch (Exception e) {
			ExceptionHandlerUtil.handleException(e);
		}
	}

	@Override
	public void processResponse(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException {

		try {
			doProcessResponse(request, response);
		}
		catch (Exception e) {
			ExceptionHandlerUtil.handleException(e);
		}
	}

	@Override
	public void sendAuthnRequest(
			HttpServletRequest request, HttpServletResponse response,
			String relayState)
		throws PortalException {

		try {
			doSendAuthnRequest(request, response, relayState);
		}
		catch (Exception e) {
			ExceptionHandlerUtil.handleException(e);
		}
	}

	@Override
	public void updateSamlSpSession(
		HttpServletRequest request, HttpServletResponse response) {

		SamlSpSession samlSpSession = getSamlSpSession(request);

		HttpSession session = request.getSession();

		String jSessionId = session.getId();

		if ((samlSpSession != null) &&
			!jSessionId.equals(samlSpSession.getJSessionId())) {

			try {
				samlSpSessionLocalService.updateSamlSpSession(
					samlSpSession.getPrimaryKey(), jSessionId);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_samlConfiguration = ConfigurableUtil.createConfigurable(
			SamlConfiguration.class, properties);
	}

	protected void addSamlSsoSession(
			HttpServletRequest request, HttpServletResponse response,
			SamlSsoRequestContext samlSsoRequestContext, NameID nameID)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			request);

		SamlIdpSsoSession samlIdpSsoSession =
			_samlIdpSsoSessionLocalService.addSamlIdpSsoSession(
				samlSsoRequestContext.getSamlSsoSessionId(), serviceContext);

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			samlSsoRequestContext.getSAMLMessageContext();

		_samlIdpSpSessionLocalService.addSamlIdpSpSession(
			samlIdpSsoSession.getSamlIdpSsoSessionId(),
			samlMessageContext.getPeerEntityId(), nameID.getFormat(),
			nameID.getValue(), serviceContext);

		addCookie(
			request, response, SamlWebKeys.SAML_SSO_SESSION_ID,
			samlSsoRequestContext.getSamlSsoSessionId(), -1);
	}

	protected SamlSsoRequestContext decodeAuthnConversationAfterLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		HttpSession session = request.getSession();

		SamlSsoRequestContext samlSsoRequestContext =
			(SamlSsoRequestContext)session.getAttribute(
				SamlWebKeys.SAML_SSO_REQUEST_CONTEXT);

		if (samlSsoRequestContext != null) {
			session.removeAttribute(SamlWebKeys.SAML_SSO_REQUEST_CONTEXT);

			SAMLMessageContext<AuthnRequest, Response, NameID>
				samlMessageContext =
					(SAMLMessageContext<AuthnRequest, Response, NameID>)
						getSamlMessageContext(
							request, response,
							samlSsoRequestContext.getPeerEntityId());

			samlSsoRequestContext.setSAMLMessageContext(samlMessageContext);

			String authnRequestXml = samlSsoRequestContext.getAutnRequestXml();

			if (Validator.isNotNull(authnRequestXml)) {
				AuthnRequest authnRequest =
					(AuthnRequest)OpenSamlUtil.unmarshall(authnRequestXml);

				samlMessageContext.setInboundSAMLMessage(authnRequest);
				samlMessageContext.setInboundSAMLMessageId(
					authnRequest.getID());
			}

			String relayState = samlSsoRequestContext.getRelayState();

			samlMessageContext.setRelayState(relayState);

			String samlSsoSessionId = getSamlSsoSessionId(request);

			if (Validator.isNotNull(samlSsoSessionId)) {
				samlSsoRequestContext.setSamlSsoSessionId(samlSsoSessionId);
			}
			else {
				samlSsoRequestContext.setNewSession(true);
				samlSsoRequestContext.setSamlSsoSessionId(
					generateIdentifier(30));
			}

			samlSsoRequestContext.setStage(
				SamlSsoRequestContext.STAGE_AUTHENTICATED);

			long userId = portal.getUserId(request);

			samlSsoRequestContext.setUserId(userId);

			return samlSsoRequestContext;
		}

		return null;
	}

	protected SamlSsoRequestContext decodeAuthnRequest(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String samlMessageId = ParamUtil.getString(request, "saml_message_id");

		if (!Validator.isBlank(samlMessageId)) {
			SamlSsoRequestContext samlSsoRequestContext =
				decodeAuthnConversationAfterLogin(request, response);

			if (samlSsoRequestContext != null) {
				SAMLMessageContext<AuthnRequest, Response, NameID>
					samlMessageContext =
						samlSsoRequestContext.getSAMLMessageContext();

				if ((samlMessageContext != null) &&
					samlMessageId.equals(
						samlMessageContext.getInboundSAMLMessageId())) {

					return samlSsoRequestContext;
				}
			}
		}

		boolean idpInitiatedSSO = false;

		String entityId = ParamUtil.getString(request, "entityId");
		String samlRequest = ParamUtil.getString(request, "SAMLRequest");

		if (Validator.isNotNull(entityId) && Validator.isNull(samlRequest)) {
			idpInitiatedSSO = true;
		}

		if (idpInitiatedSSO) {
			SamlSsoRequestContext samlSsoRequestContext =
				decodeAuthnConversationAfterLogin(request, response);

			if (samlSsoRequestContext != null) {
				SAMLMessageContext<AuthnRequest, Response, NameID>
					samlMessageContext =
						samlSsoRequestContext.getSAMLMessageContext();

				if ((samlMessageContext != null) &&
					entityId.equals(samlMessageContext.getPeerEntityId())) {

					return samlSsoRequestContext;
				}
			}
		}

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			null;

		SamlBinding samlBinding = null;

		if (StringUtil.equalsIgnoreCase(request.getMethod(), "GET")) {
			samlBinding = getSamlBinding(
				SAMLConstants.SAML2_REDIRECT_BINDING_URI);
		}
		else {
			samlBinding = getSamlBinding(SAMLConstants.SAML2_POST_BINDING_URI);
		}

		SamlSsoRequestContext samlSsoRequestContext = null;

		if (idpInitiatedSSO) {
			samlMessageContext =
				(SAMLMessageContext<AuthnRequest, Response, NameID>)
					getSamlMessageContext(request, response, entityId);

			samlMessageContext.setCommunicationProfileId(
				samlBinding.getCommunicationProfileId());

			String relayState = ParamUtil.getString(request, "RelayState");

			samlMessageContext.setRelayState(relayState);

			samlSsoRequestContext = new SamlSsoRequestContext(
				samlMessageContext.getPeerEntityId(), relayState,
				samlMessageContext, _userLocalService);
		}
		else {
			SamlProviderConfiguration samlProviderConfiguration =
				samlProviderConfigurationHelper.getSamlProviderConfiguration();

			samlMessageContext =
				(SAMLMessageContext<AuthnRequest, Response, NameID>)
					decodeSamlMessage(
						request, response, samlBinding,
						samlProviderConfiguration.
							authnRequestSignatureRequired());

			AuthnRequest authnRequest =
				samlMessageContext.getInboundSAMLMessage();

			String authnRequestXml = OpenSamlUtil.marshall(authnRequest);

			samlSsoRequestContext = new SamlSsoRequestContext(
				authnRequestXml, samlMessageContext.getPeerEntityId(),
				samlMessageContext.getRelayState(), samlMessageContext,
				_userLocalService);
		}

		String samlSsoSessionId = getSamlSsoSessionId(request);

		if (Validator.isNotNull(samlSsoSessionId)) {
			samlSsoRequestContext.setSamlSsoSessionId(samlSsoSessionId);
		}
		else {
			samlSsoRequestContext.setNewSession(true);
			samlSsoRequestContext.setSamlSsoSessionId(generateIdentifier(30));
		}

		samlSsoRequestContext.setStage(SamlSsoRequestContext.STAGE_INITIAL);

		long userId = portal.getUserId(request);

		samlSsoRequestContext.setUserId(userId);

		return samlSsoRequestContext;
	}

	protected void doProcessAuthnRequest(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		SamlSsoRequestContext samlSsoRequestContext = decodeAuthnRequest(
			request, response);

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			samlSsoRequestContext.getSAMLMessageContext();

		AuthnRequest authnRequest = samlMessageContext.getInboundSAMLMessage();

		User user = samlSsoRequestContext.getUser();

		if ((authnRequest != null) && authnRequest.isPassive() &&
			(user == null)) {

			sendFailureResponse(
				samlSsoRequestContext, StatusCode.NO_PASSIVE_URI);

			return;
		}

		boolean sessionExpired = false;

		if (!samlSsoRequestContext.isNewSession()) {
			String samlSsoSessionId =
				samlSsoRequestContext.getSamlSsoSessionId();

			SamlIdpSsoSession samlIdpSsoSession =
				_samlIdpSsoSessionLocalService.fetchSamlIdpSso(
					samlSsoSessionId);

			if (samlIdpSsoSession != null) {
				sessionExpired = samlIdpSsoSession.isExpired();
			}
			else {
				samlSsoSessionId = null;

				samlSsoRequestContext.setSamlSsoSessionId(null);
			}

			if (sessionExpired || Validator.isNull(samlSsoSessionId)) {
				addCookie(
					request, response, SamlWebKeys.SAML_SSO_SESSION_ID,
					StringPool.BLANK, 0);

				samlSsoRequestContext.setNewSession(true);
				samlSsoRequestContext.setSamlSsoSessionId(
					generateIdentifier(30));
			}
		}

		if (sessionExpired || (user == null) ||
			((authnRequest != null) && authnRequest.isForceAuthn() &&
			 (user != null) &&
			 (samlSsoRequestContext.getStage() ==
				 SamlSsoRequestContext.STAGE_INITIAL))) {

			boolean forceAuthn = false;

			if (sessionExpired ||
				((authnRequest != null) && authnRequest.isForceAuthn())) {

				forceAuthn = true;
			}

			redirectToLogin(
				request, response, samlSsoRequestContext, forceAuthn);
		}
		else {
			sendSuccessResponse(request, response, samlSsoRequestContext);

			HttpSession session = request.getSession(false);

			if (Validator.isNotNull(session)) {
				session.removeAttribute(SamlWebKeys.FORCE_REAUTHENTICATION);
			}
		}
	}

	protected void doProcessResponse(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		SAMLMessageContext<Response, SAMLObject, NameID> samlMessageContext =
			(SAMLMessageContext<Response, SAMLObject, NameID>)decodeSamlMessage(
				request, response,
				getSamlBinding(SAMLConstants.SAML2_POST_BINDING_URI), true);

		Response samlResponse = samlMessageContext.getInboundSAMLMessage();

		Status status = samlResponse.getStatus();

		StatusCode statusCode = status.getStatusCode();

		String statusCodeURI = statusCode.getValue();

		if (!statusCodeURI.equals(StatusCode.SUCCESS_URI)) {
			StatusCode childStatusCode = statusCode.getStatusCode();

			if ((childStatusCode != null) &&
				Validator.isNotNull(childStatusCode.getValue())) {

				throw new StatusException(childStatusCode.getValue());
			}
			else {
				throw new StatusException(statusCodeURI);
			}
		}

		verifyInResponseTo(samlResponse);
		verifyDestination(samlMessageContext, samlResponse.getDestination());
		verifyIssuer(samlMessageContext, samlResponse.getIssuer());

		Assertion assertion = null;

		SignatureTrustEngine signatureTrustEngine =
			metadataManager.getSignatureTrustEngine();

		List<Attribute> attributes = new ArrayList<>();

		for (Assertion curAssertion : samlResponse.getAssertions()) {
			try {
				verifyAssertion(
					curAssertion, samlMessageContext, signatureTrustEngine);
			}
			catch (SamlException se) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Rejecting assertion " + curAssertion.getID(), se);
				}

				continue;
			}

			List<AuthnStatement> authnStatements =
				curAssertion.getAuthnStatements();

			if (!authnStatements.isEmpty()) {
				Subject subject = curAssertion.getSubject();

				if ((subject != null) &&
					(subject.getSubjectConfirmations() != null)) {

					for (SubjectConfirmation subjectConfirmation :
							subject.getSubjectConfirmations()) {

						if (SubjectConfirmation.METHOD_BEARER.equals(
								subjectConfirmation.getMethod())) {

							assertion = curAssertion;

							break;
						}
					}
				}
			}

			if (assertion != null) {
				for (AttributeStatement attributeStatement :
						curAssertion.getAttributeStatements()) {

					for (Attribute attribute :
							attributeStatement.getAttributes()) {

						attributes.add(attribute);
					}
				}

				break;
			}
		}

		if (assertion == null) {
			throw new AssertionException(
				"Response does not contain any acceptable assertions");
		}

		NameID nameID = samlMessageContext.getSubjectNameIdentifier();

		if (nameID == null) {
			throw new SamlException("Name ID not present in subject");
		}

		if (_log.isDebugEnabled()) {
			_log.debug("SAML authenticated user " + nameID.getValue());
		}

		String assertionXml = OpenSamlUtil.marshall(assertion);

		List<AuthnStatement> authnStatements = assertion.getAuthnStatements();

		AuthnStatement authnStatement = authnStatements.get(0);

		String sessionIndex = authnStatement.getSessionIndex();

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			request);

		User user = _userResolver.resolveUser(
			new UserResolverSAMLContextImpl(samlMessageContext),
			serviceContext);

		serviceContext.setUserId(user.getUserId());

		HttpSession session = request.getSession();

		SamlSpSession samlSpSession = getSamlSpSession(request);

		if (samlSpSession != null) {
			samlSpSessionLocalService.updateSamlSpSession(
				samlSpSession.getSamlSpSessionId(),
				samlSpSession.getSamlSpSessionKey(), assertionXml,
				session.getId(), nameID.getFormat(), nameID.getNameQualifier(),
				nameID.getSPNameQualifier(), nameID.getValue(), sessionIndex,
				serviceContext);
		}
		else {
			String samlSpSessionKey = generateIdentifier(30);

			samlSpSession = samlSpSessionLocalService.addSamlSpSession(
				samlSpSessionKey, assertionXml, session.getId(),
				nameID.getFormat(), nameID.getNameQualifier(),
				nameID.getSPNameQualifier(), nameID.getValue(), sessionIndex,
				serviceContext);
		}

		session.setAttribute(
			SamlWebKeys.SAML_SP_SESSION_KEY,
			samlSpSession.getSamlSpSessionKey());

		addCookie(
			request, response, SamlWebKeys.SAML_SP_SESSION_KEY,
			samlSpSession.getSamlSpSessionKey(), -1);

		StringBundler sb = new StringBundler(3);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		sb.append(themeDisplay.getPathMain());

		sb.append("/portal/saml/auth_redirect?redirect=");

		String relayState = portal.escapeRedirect(
			samlMessageContext.getRelayState());

		if (Validator.isNull(relayState)) {
			relayState = portal.getHomeURL(request);
		}

		sb.append(URLCodec.encodeURL(relayState));

		response.sendRedirect(sb.toString());
	}

	protected void doSendAuthnRequest(
			HttpServletRequest request, HttpServletResponse response,
			String relayState)
		throws Exception {

		String entityId = metadataManager.getDefaultIdpEntityId();

		SAMLMessageContext<SAMLObject, AuthnRequest, SAMLObject>
			samlMessageContext =
				(SAMLMessageContext<SAMLObject, AuthnRequest, SAMLObject>)
					getSamlMessageContext(request, response, entityId);

		SPSSODescriptor spSSODescriptor =
			(SPSSODescriptor)samlMessageContext.getLocalEntityRoleMetadata();

		AssertionConsumerService assertionConsumerService =
			SamlUtil.getAssertionConsumerServiceForBinding(
				spSSODescriptor, SAMLConstants.SAML2_POST_BINDING_URI);

		IDPSSODescriptor idpSSODescriptor =
			(IDPSSODescriptor)samlMessageContext.getPeerEntityRoleMetadata();

		SingleSignOnService singleSignOnService =
			SamlUtil.resolveSingleSignOnService(
				idpSSODescriptor, SAMLConstants.SAML2_POST_BINDING_URI);

		NameIDPolicy nameIDPolicy = OpenSamlUtil.buildNameIdPolicy();

		nameIDPolicy.setAllowCreate(true);
		nameIDPolicy.setFormat(metadataManager.getNameIdFormat(entityId));

		AuthnRequest authnRequest = OpenSamlUtil.buildAuthnRequest(
			spSSODescriptor, assertionConsumerService, singleSignOnService,
			nameIDPolicy);

		authnRequest.setID(generateIdentifier(20));

		long companyId = portal.getCompanyId(request);

		boolean forceAuthn = false;

		try {
			SamlSpIdpConnection samlSpIdpConnection =
				_samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
					companyId, entityId);

			forceAuthn = samlSpIdpConnection.isForceAuthn();
		}
		catch (NoSuchSpIdpConnectionException nssice) {
		}

		authnRequest.setForceAuthn(forceAuthn);

		samlMessageContext.setOutboundSAMLMessage(authnRequest);

		if (spSSODescriptor.isAuthnRequestsSigned() ||
			idpSSODescriptor.getWantAuthnRequestsSigned()) {

			Credential credential = metadataManager.getSigningCredential();

			samlMessageContext.setOutboundSAMLMessageSigningCredential(
				credential);

			OpenSamlUtil.signObject(authnRequest, credential);
		}

		samlMessageContext.setPeerEntityEndpoint(singleSignOnService);
		samlMessageContext.setRelayState(relayState);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			request);

		_samlSpAuthRequestLocalService.addSamlSpAuthRequest(
			samlMessageContext.getPeerEntityId(), authnRequest.getID(),
			serviceContext);

		sendSamlMessage(samlMessageContext);
	}

	protected Assertion getSuccessAssertion(
		SamlSsoRequestContext samlSsoRequestContext,
		AssertionConsumerService assertionConsumerService, NameID nameID) {

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			samlSsoRequestContext.getSAMLMessageContext();

		Assertion assertion = OpenSamlUtil.buildAssertion();

		DateTime issueInstantDateTime = new DateTime(DateTimeZone.UTC);

		SubjectConfirmationData subjectConfirmationData =
			getSuccessSubjectConfirmationData(
				samlSsoRequestContext, assertionConsumerService,
				issueInstantDateTime);

		Conditions conditions = getSuccessConditions(
			samlSsoRequestContext, issueInstantDateTime,
			subjectConfirmationData.getNotOnOrAfter());

		assertion.setConditions(conditions);

		assertion.setID(generateIdentifier(20));
		assertion.setIssueInstant(issueInstantDateTime);

		Issuer issuer = OpenSamlUtil.buildIssuer(
			samlMessageContext.getLocalEntityId());

		assertion.setIssuer(issuer);

		Subject subject = getSuccessSubject(
			samlSsoRequestContext, assertionConsumerService, nameID,
			subjectConfirmationData);

		assertion.setSubject(subject);

		assertion.setVersion(SAMLVersion.VERSION_20);

		List<AuthnStatement> authnStatements = assertion.getAuthnStatements();

		authnStatements.add(
			getSuccessAuthnStatement(samlSsoRequestContext, assertion));

		boolean attributesEnabled = metadataManager.isAttributesEnabled(
			samlMessageContext.getPeerEntityId());

		if (!attributesEnabled) {
			return assertion;
		}

		User user = samlSsoRequestContext.getUser();

		AttributeResolver attributeResolver =
			_attributeResolverRegistry.getAttributeResolver(
				samlMessageContext.getPeerEntityId());

		AttributePublisherImpl attributePublisherImpl =
			new AttributePublisherImpl();

		attributeResolver.resolve(
			user, new AttributeResolverSAMLContextImpl(samlMessageContext),
			attributePublisherImpl);

		List<Attribute> attributes = attributePublisherImpl.getAttributes();

		if (attributes.isEmpty()) {
			return assertion;
		}

		List<AttributeStatement> attributeStatements =
			assertion.getAttributeStatements();

		AttributeStatement attributeStatement =
			OpenSamlUtil.buildAttributeStatement();

		attributeStatements.add(attributeStatement);

		List<Attribute> attributeStatementAttributes =
			attributeStatement.getAttributes();

		attributeStatementAttributes.addAll(attributes);

		return assertion;
	}

	protected AudienceRestriction getSuccessAudienceRestriction(
		String entityId) {

		AudienceRestriction audienceRestriction =
			OpenSamlUtil.buildAudienceRestriction();

		List<Audience> audiences = audienceRestriction.getAudiences();

		Audience audience = OpenSamlUtil.buildAudience();

		audience.setAudienceURI(entityId);

		audiences.add(audience);

		return audienceRestriction;
	}

	protected AuthnContext getSuccessAuthnContext() {
		AuthnContext authnContext = OpenSamlUtil.buildAuthnContext();

		AuthnContextClassRef authnContextClassRef =
			OpenSamlUtil.buildAuthnContextClassRef();

		authnContextClassRef.setAuthnContextClassRef(
			AuthnContext.UNSPECIFIED_AUTHN_CTX);

		authnContext.setAuthnContextClassRef(authnContextClassRef);

		return authnContext;
	}

	protected AuthnStatement getSuccessAuthnStatement(
		SamlSsoRequestContext samlSsoRequestContext, Assertion assertion) {

		AuthnStatement authnStatement = OpenSamlUtil.buildAuthnStatement();

		AuthnContext authnContext = getSuccessAuthnContext();

		authnStatement.setAuthnContext(authnContext);

		authnStatement.setAuthnInstant(assertion.getIssueInstant());
		authnStatement.setSessionIndex(
			samlSsoRequestContext.getSamlSsoSessionId());

		return authnStatement;
	}

	protected Conditions getSuccessConditions(
		SamlSsoRequestContext samlSsoRequestContext, DateTime notBeforeDateTime,
		DateTime notOnOrAfterDateTime) {

		Conditions conditions = OpenSamlUtil.buildConditions();

		conditions.setNotBefore(notBeforeDateTime);
		conditions.setNotOnOrAfter(notOnOrAfterDateTime);

		List<AudienceRestriction> audienceRestrictions =
			conditions.getAudienceRestrictions();

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			samlSsoRequestContext.getSAMLMessageContext();

		AudienceRestriction audienceRestriction = getSuccessAudienceRestriction(
			samlMessageContext.getPeerEntityId());

		audienceRestrictions.add(audienceRestriction);

		return conditions;
	}

	protected NameID getSuccessNameId(
			SamlSsoRequestContext samlSsoRequestContext)
		throws Exception {

		String nameIdFormat = null;
		String spNameQualifier = null;

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			samlSsoRequestContext.getSAMLMessageContext();

		NameIdResolver nameIdResolver =
			_nameIdResolverRegistry.getNameIdResolver(
				samlMessageContext.getPeerEntityId());

		boolean allowCreate = false;

		AuthnRequest authnRequest = samlMessageContext.getInboundSAMLMessage();

		if (authnRequest != null) {
			NameIDPolicy nameIDPolicy = authnRequest.getNameIDPolicy();

			if (nameIDPolicy != null) {
				nameIdFormat = nameIDPolicy.getFormat();
				spNameQualifier = nameIDPolicy.getSPNameQualifier();
				allowCreate = nameIDPolicy.getAllowCreate();
			}
		}

		if (nameIdFormat == null) {
			nameIdFormat = metadataManager.getNameIdFormat(
				samlMessageContext.getPeerEntityId());
		}

		return OpenSamlUtil.buildNameId(
			nameIdFormat, null, spNameQualifier,
			nameIdResolver.resolve(
				samlSsoRequestContext.getUser(),
				samlMessageContext.getPeerEntityId(), nameIdFormat,
				spNameQualifier, allowCreate,
				new NameIdResolverSAMLContextImpl(samlMessageContext)));
	}

	protected Response getSuccessResponse(
		SamlSsoRequestContext samlSsoRequestContext,
		AssertionConsumerService assertionConsumerService,
		Assertion assertion) {

		Response response = OpenSamlUtil.buildResponse();

		response.setDestination(assertionConsumerService.getLocation());
		response.setID(generateIdentifier(20));

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			samlSsoRequestContext.getSAMLMessageContext();

		if (Validator.isNotNull(samlMessageContext.getInboundSAMLMessageId())) {
			response.setInResponseTo(
				samlMessageContext.getInboundSAMLMessageId());
		}

		response.setIssueInstant(assertion.getIssueInstant());

		Issuer issuer = OpenSamlUtil.buildIssuer(
			samlMessageContext.getLocalEntityId());

		response.setIssuer(issuer);

		StatusCode statusCode = OpenSamlUtil.buildStatusCode(
			StatusCode.SUCCESS_URI);

		Status status = OpenSamlUtil.buildStatus(statusCode);

		response.setStatus(status);

		response.setVersion(SAMLVersion.VERSION_20);

		List<Assertion> assertions = response.getAssertions();

		assertions.add(assertion);

		return response;
	}

	protected Subject getSuccessSubject(
		SamlSsoRequestContext samlSsoRequestContext,
		AssertionConsumerService assertionConsumerService, NameID nameID,
		SubjectConfirmationData subjectConfirmationData) {

		SubjectConfirmation subjectConfirmation =
			OpenSamlUtil.buildSubjectConfirmation();

		subjectConfirmation.setMethod(SubjectConfirmation.METHOD_BEARER);
		subjectConfirmation.setSubjectConfirmationData(subjectConfirmationData);

		Subject subject = OpenSamlUtil.buildSubject(nameID);

		List<SubjectConfirmation> subjectConfirmations =
			subject.getSubjectConfirmations();

		subjectConfirmations.add(subjectConfirmation);

		return subject;
	}

	protected SubjectConfirmationData getSuccessSubjectConfirmationData(
		SamlSsoRequestContext samlSsoRequestContext,
		AssertionConsumerService assertionConsumerService,
		DateTime issueInstantDateTime) {

		SubjectConfirmationData subjectConfirmationData =
			OpenSamlUtil.buildSubjectConfirmationData();

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			samlSsoRequestContext.getSAMLMessageContext();

		subjectConfirmationData.setInResponseTo(
			samlMessageContext.getInboundSAMLMessageId());

		subjectConfirmationData.setRecipient(
			assertionConsumerService.getLocation());

		int assertionLifetime = metadataManager.getAssertionLifetime(
			samlMessageContext.getPeerEntityId());

		DateTime notOnOrAfterDateTime = issueInstantDateTime.plusSeconds(
			assertionLifetime);

		subjectConfirmationData.setNotOnOrAfter(notOnOrAfterDateTime);

		return subjectConfirmationData;
	}

	protected void redirectToLogin(
		HttpServletRequest request, HttpServletResponse response,
		SamlSsoRequestContext samlSsoRequestContext, boolean forceAuthn) {

		HttpSession session = request.getSession();

		if (forceAuthn) {
			logout(request, response);

			session = request.getSession(true);

			session.setAttribute(
				SamlWebKeys.FORCE_REAUTHENTICATION, Boolean.TRUE);
		}

		SAMLMessageContext samlMessageContext =
			samlSsoRequestContext.getSAMLMessageContext();

		samlSsoRequestContext.setSAMLMessageContext(null);

		session.setAttribute(
			SamlWebKeys.SAML_SSO_REQUEST_CONTEXT, samlSsoRequestContext);

		response.addHeader(
			HttpHeaders.CACHE_CONTROL,
			HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
		response.addHeader(
			HttpHeaders.PRAGMA, HttpHeaders.PRAGMA_NO_CACHE_VALUE);

		StringBundler sb = new StringBundler(3);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		sb.append(themeDisplay.getPathMain());

		sb.append("/portal/login?redirect=");

		StringBundler redirectSB = new StringBundler(4);

		redirectSB.append(themeDisplay.getPathMain());
		redirectSB.append("/portal/saml/sso");

		if (samlMessageContext.getInboundSAMLMessageId() != null) {
			redirectSB.append("?saml_message_id=");
			redirectSB.append(
				URLCodec.encodeURL(
					samlMessageContext.getInboundSAMLMessageId()));
		}
		else if (samlMessageContext.getPeerEntityId() != null) {
			redirectSB.append("?entityId=");
			redirectSB.append(
				URLCodec.encodeURL(samlMessageContext.getPeerEntityId()));
		}

		sb.append(URLCodec.encodeURL(redirectSB.toString()));

		String redirect = sb.toString();

		try {
			response.sendRedirect(redirect);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected void sendFailureResponse(
			SamlSsoRequestContext samlSsoRequestContext, String statusURI)
		throws PortalException {

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			samlSsoRequestContext.getSAMLMessageContext();

		SamlBinding samlBinding = getSamlBinding(
			SAMLConstants.SAML2_POST_BINDING_URI);

		AssertionConsumerService assertionConsumerService =
			SamlUtil.resolverAssertionConsumerService(
				samlMessageContext, samlBinding.getCommunicationProfileId());

		samlMessageContext.setPeerEntityEndpoint(assertionConsumerService);

		Credential credential = metadataManager.getSigningCredential();

		samlMessageContext.setOutboundSAMLMessageSigningCredential(credential);

		Response response = OpenSamlUtil.buildResponse();

		response.setDestination(assertionConsumerService.getLocation());
		response.setInResponseTo(samlMessageContext.getInboundSAMLMessageId());

		DateTime issueInstantDateTime = new DateTime(DateTimeZone.UTC);

		response.setIssueInstant(issueInstantDateTime);

		Issuer issuer = OpenSamlUtil.buildIssuer(
			samlMessageContext.getLocalEntityId());

		response.setIssuer(issuer);

		StatusCode statusCode = OpenSamlUtil.buildStatusCode(statusURI);

		Status status = OpenSamlUtil.buildStatus(statusCode);

		response.setStatus(status);

		samlMessageContext.setOutboundSAMLMessage(response);

		sendSamlMessage(samlMessageContext);
	}

	protected void sendSuccessResponse(
			HttpServletRequest request, HttpServletResponse response,
			SamlSsoRequestContext samlSsoRequestContext)
		throws Exception {

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			samlSsoRequestContext.getSAMLMessageContext();

		SamlBinding samlBinding = getSamlBinding(
			SAMLConstants.SAML2_POST_BINDING_URI);

		AssertionConsumerService assertionConsumerService =
			SamlUtil.resolverAssertionConsumerService(
				samlMessageContext, samlBinding.getCommunicationProfileId());

		NameID nameID = getSuccessNameId(samlSsoRequestContext);

		Assertion assertion = getSuccessAssertion(
			samlSsoRequestContext, assertionConsumerService, nameID);

		Credential credential = metadataManager.getSigningCredential();

		SPSSODescriptor spSSODescriptor =
			(SPSSODescriptor)samlMessageContext.getPeerEntityRoleMetadata();

		if (spSSODescriptor.getWantAssertionsSigned()) {
			OpenSamlUtil.signObject(assertion, credential);
		}

		Response samlResponse = getSuccessResponse(
			samlSsoRequestContext, assertionConsumerService, assertion);

		samlMessageContext.setOutboundSAMLMessage(samlResponse);

		samlMessageContext.setOutboundSAMLMessageSigningCredential(credential);
		samlMessageContext.setOutboundSAMLProtocol(SAMLConstants.SAML20P_NS);

		samlMessageContext.setPeerEntityEndpoint(assertionConsumerService);

		if (samlSsoRequestContext.isNewSession()) {
			addSamlSsoSession(request, response, samlSsoRequestContext, nameID);
		}
		else {
			updateSamlSsoSession(request, samlSsoRequestContext, nameID);
		}

		sendSamlMessage(samlMessageContext);
	}

	@Reference(unbind = "-")
	protected void setAttributeResolverRegistry(
		AttributeResolverRegistry attributeResolverRegistry) {

		_attributeResolverRegistry = attributeResolverRegistry;
	}

	@Override
	@Reference(unbind = "-")
	protected void setIdentifierGenerator(
		IdentifierGenerator identifierGenerator) {

		super.setIdentifierGenerator(identifierGenerator);
	}

	@Override
	@Reference(unbind = "-")
	protected void setMetadataManager(MetadataManager metadataManager) {
		super.setMetadataManager(metadataManager);
	}

	@Reference(unbind = "-")
	protected void setNameIdResolverRegistry(
		NameIdResolverRegistry nameIdResolverRegistry) {

		_nameIdResolverRegistry = nameIdResolverRegistry;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		super.portal = portal;
	}

	@Reference(
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		policyOption = ReferencePolicyOption.GREEDY, unbind = "unsetSamlBinding"
	)
	protected void setSamlBinding(SamlBinding samlBinding) {
		addSamlBinding(samlBinding);
	}

	@Override
	@Reference(unbind = "-")
	protected void setSamlProviderConfigurationHelper(
		SamlProviderConfigurationHelper samlProviderConfigurationHelper) {

		super.setSamlProviderConfigurationHelper(
			samlProviderConfigurationHelper);
	}

	@Reference(unbind = "-")
	protected void setSamlSpAuthRequestLocalService(
		SamlSpAuthRequestLocalService samlSpAuthRequestLocalService) {

		_samlSpAuthRequestLocalService = samlSpAuthRequestLocalService;
	}

	@Reference(unbind = "-")
	protected void setSamlSpIdpConnectionLocalService(
		SamlSpIdpConnectionLocalService samlSpIdpConnectionLocalService) {

		_samlSpIdpConnectionLocalService = samlSpIdpConnectionLocalService;
	}

	@Reference(unbind = "-")
	protected void setSamlSpMessageLocalService(
		SamlSpMessageLocalService samlSpMessageLocalService) {

		_samlSpMessageLocalService = samlSpMessageLocalService;
	}

	@Reference(unbind = "-")
	protected void setSamlSpSessionLocalService(
		SamlSpSessionLocalService samlSpSessionLocalService) {

		super.samlSpSessionLocalService = samlSpSessionLocalService;
	}

	@Reference(policyOption = ReferencePolicyOption.GREEDY, unbind = "-")
	protected void setUserResolver(UserResolver userResolver) {
		_userResolver = userResolver;
	}

	@Override
	protected void unsetSamlBinding(SamlBinding samlBinding) {
		removeSamlBinding(samlBinding);
	}

	protected void updateSamlSsoSession(
			HttpServletRequest request,
			SamlSsoRequestContext samlSsoRequestContext, NameID nameID)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			request);

		SamlIdpSsoSession samlIdpSsoSession =
			_samlIdpSsoSessionLocalService.updateModifiedDate(
				samlSsoRequestContext.getSamlSsoSessionId());

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			samlSsoRequestContext.getSAMLMessageContext();

		try {
			_samlIdpSpSessionLocalService.updateModifiedDate(
				samlIdpSsoSession.getSamlIdpSsoSessionId(),
				samlMessageContext.getPeerEntityId());
		}
		catch (NoSuchIdpSpSessionException nsisse) {
			_samlIdpSpSessionLocalService.addSamlIdpSpSession(
				samlIdpSsoSession.getSamlIdpSsoSessionId(),
				samlMessageContext.getPeerEntityId(), nameID.getFormat(),
				nameID.getValue(), serviceContext);
		}
	}

	protected void verifyAssertion(
			Assertion assertion,
			SAMLMessageContext<?, ?, NameID> samlMessageContext,
			TrustEngine<Signature> trustEngine)
		throws PortalException {

		verifyReplay(samlMessageContext, assertion);
		verifyIssuer(samlMessageContext, assertion.getIssuer());
		verifyAssertionSignature(
			assertion.getSignature(), samlMessageContext, trustEngine);
		verifyConditions(samlMessageContext, assertion.getConditions());
		verifySubject(samlMessageContext, assertion.getSubject());
	}

	protected void verifyAssertionSignature(
			Signature signature, SAMLMessageContext<?, ?, ?> samlMessageContext,
			TrustEngine<Signature> trustEngine)
		throws PortalException {

		SPSSODescriptor spSSODescriptor =
			(SPSSODescriptor)samlMessageContext.getLocalEntityRoleMetadata();

		if (signature != null) {
			verifySignature(samlMessageContext, signature, trustEngine);
		}
		else if (spSSODescriptor.getWantAssertionsSigned()) {
			throw new SignatureException("SAML assertion is not signed");
		}
	}

	protected void verifyAudienceRestrictions(
			List<AudienceRestriction> audienceRestrictions,
			SAMLMessageContext<?, ?, ?> samlMessageContext)
		throws PortalException {

		if (audienceRestrictions.isEmpty()) {
			return;
		}

		for (AudienceRestriction audienceRestriction : audienceRestrictions) {
			for (Audience audience : audienceRestriction.getAudiences()) {
				String audienceURI = audience.getAudienceURI();

				if (audienceURI.equals(samlMessageContext.getLocalEntityId())) {
					return;
				}
			}
		}

		throw new AudienceException("Unable verify audience");
	}

	protected void verifyConditions(
			SAMLMessageContext<?, ?, ?> samlMessageContext,
			Conditions conditions)
		throws PortalException {

		verifyAudienceRestrictions(
			conditions.getAudienceRestrictions(), samlMessageContext);

		DateTime nowDateTime = new DateTime(DateTimeZone.UTC);

		DateTime notBeforeDateTime = conditions.getNotBefore();

		if (notBeforeDateTime != null) {
			verifyNotBeforeDateTime(
				nowDateTime, metadataManager.getClockSkew(), notBeforeDateTime);
		}

		DateTime notOnOrAfterDateTime = conditions.getNotOnOrAfter();

		if (notOnOrAfterDateTime != null) {
			verifyNotOnOrAfterDateTime(
				nowDateTime, metadataManager.getClockSkew(),
				notOnOrAfterDateTime);
		}
	}

	protected void verifyDestination(
			SAMLMessageContext<?, ?, ?> samlMessageContext, String destination)
		throws PortalException {

		SPSSODescriptor spSSODescriptor =
			(SPSSODescriptor)samlMessageContext.getLocalEntityRoleMetadata();

		List<AssertionConsumerService> assertionConsumerServices =
			spSSODescriptor.getAssertionConsumerServices();

		for (AssertionConsumerService assertionConsumerService :
				assertionConsumerServices) {

			String binding = assertionConsumerService.getBinding();

			if (destination.equals(assertionConsumerService.getLocation()) &&
				binding.equals(
					samlMessageContext.getCommunicationProfileId())) {

				return;
			}
		}

		throw new DestinationException(
			"Destination " + destination + " does not match any assertion " +
				"consumer location with binding " +
					samlMessageContext.getCommunicationProfileId());
	}

	protected void verifyInResponseTo(Response samlResponse)
		throws PortalException {

		if (Validator.isNull(samlResponse.getInResponseTo())) {
			return;
		}

		Issuer issuer = samlResponse.getIssuer();

		String issuerEntityId = issuer.getValue();

		String inResponseTo = samlResponse.getInResponseTo();

		SamlSpAuthRequest samlSpAuthRequest =
			_samlSpAuthRequestLocalService.fetchSamlSpAuthRequest(
				issuerEntityId, inResponseTo);

		if (samlSpAuthRequest != null) {
			_samlSpAuthRequestLocalService.deleteSamlSpAuthRequest(
				samlSpAuthRequest);
		}
		else {
			throw new InResponseToException(
				"Response in response to " + inResponseTo + " does not match " +
					"any authentication requests");
		}
	}

	protected void verifyIssuer(
			SAMLMessageContext<?, ?, ?> samlMessageContext, Issuer issuer)
		throws PortalException {

		String issuerFormat = issuer.getFormat();

		if ((issuerFormat != null) && !issuerFormat.equals(NameIDType.ENTITY)) {
			throw new IssuerException("Invalid issuer format " + issuerFormat);
		}

		String peerEntityId = samlMessageContext.getPeerEntityId();

		if (!peerEntityId.equals(issuer.getValue())) {
			throw new IssuerException(
				"Issuer does not match expected peer entity ID " +
					peerEntityId);
		}
	}

	protected void verifyNotBeforeDateTime(
			DateTime nowDateTime, long clockSkew, DateTime dateTime)
		throws PortalException {

		DateTime lowerBoundDateTime = dateTime.minus(new Duration(clockSkew));

		if (!nowDateTime.isBefore(lowerBoundDateTime)) {
			return;
		}

		throw new AssertionException(
			"Date " + nowDateTime.toString() + " is before " +
				lowerBoundDateTime.toString() + " including clock skew " +
					clockSkew);
	}

	protected void verifyNotOnOrAfterDateTime(
			DateTime nowDateTime, long clockSkew, DateTime dateTime)
		throws PortalException {

		DateTime upperBoundDateTime = dateTime.plus(new Duration(clockSkew));

		if (!(nowDateTime.isEqual(upperBoundDateTime) ||
			nowDateTime.isAfter(upperBoundDateTime))) {

			return;
		}

		throw new ExpiredException(
			"Date " + nowDateTime.toString() + " is after " +
				upperBoundDateTime.toString() + " including clock skew " +
					clockSkew);
	}

	protected void verifyReplay(
			SAMLMessageContext<?, ?, ?> samlMessageContext, Assertion assertion)
		throws PortalException {

		Issuer issuer = assertion.getIssuer();

		String idpEntityId = issuer.getValue();

		String messageKey = assertion.getID();

		DateTime notOnOrAfterDateTime = new DateTime(DateTimeZone.UTC);

		notOnOrAfterDateTime = notOnOrAfterDateTime.plus(
			_samlConfiguration.getReplayChacheDuration() +
				metadataManager.getClockSkew());

		try {
			SamlSpMessage samlSpMessage =
				_samlSpMessageLocalService.fetchSamlSpMessage(
					idpEntityId, messageKey);

			if ((samlSpMessage != null) && !samlSpMessage.isExpired()) {
				throw new ReplayException(
					"SAML assertion " + messageKey + " replayed from IdP " +
						idpEntityId);
			}
			else {
				if (samlSpMessage != null) {
					_samlSpMessageLocalService.deleteSamlSpMessage(
						samlSpMessage);
				}

				ServiceContext serviceContext = new ServiceContext();

				long companyId = CompanyThreadLocal.getCompanyId();

				serviceContext.setCompanyId(companyId);

				_samlSpMessageLocalService.addSamlSpMessage(
					idpEntityId, messageKey, notOnOrAfterDateTime.toDate(),
					serviceContext);
			}
		}
		catch (SystemException se) {
			throw new SamlException(se);
		}
	}

	protected void verifySignature(
			SAMLMessageContext<?, ?, ?> samlMessageContext, Signature signature,
			TrustEngine<Signature> trustEngine)
		throws PortalException {

		try {
			_samlSignatureProfileValidator.validate(signature);

			CriteriaSet criteriaSet = new CriteriaSet();

			criteriaSet.add(
				new EntityIDCriteria(samlMessageContext.getPeerEntityId()));
			criteriaSet.add(
				new MetadataCriteria(
					IDPSSODescriptor.DEFAULT_ELEMENT_NAME,
					SAMLConstants.SAML20P_NS));
			criteriaSet.add(new UsageCriteria(UsageType.SIGNING));

			if (!trustEngine.validate(signature, criteriaSet)) {
				throw new SignatureException("Unable validate signature trust");
			}
		}
		catch (Exception e) {
			if (e instanceof PortalException) {
				throw (PortalException)e;
			}

			throw new SignatureException("Unable to verify signature", e);
		}
	}

	protected void verifySubject(
			SAMLMessageContext<?, ?, NameID> samlMessageContext,
			Subject subject)
		throws PortalException {

		List<SubjectConfirmation> subjectConfirmations =
			subject.getSubjectConfirmations();

		for (SubjectConfirmation subjectConfirmation : subjectConfirmations) {
			String method = subjectConfirmation.getMethod();

			if (method.equals(SubjectConfirmation.METHOD_BEARER)) {
				SubjectConfirmationData subjectConfirmationData =
					subjectConfirmation.getSubjectConfirmationData();

				if (subjectConfirmationData == null) {
					continue;
				}

				DateTime nowDateTime = new DateTime(DateTimeZone.UTC);
				long clockSkew = metadataManager.getClockSkew();

				DateTime notBeforeDateTime =
					subjectConfirmationData.getNotBefore();

				if (notBeforeDateTime != null) {
					verifyNotBeforeDateTime(
						nowDateTime, clockSkew, notBeforeDateTime);
				}

				DateTime notOnOrAfterDateTime =
					subjectConfirmationData.getNotOnOrAfter();

				if (notOnOrAfterDateTime != null) {
					verifyNotOnOrAfterDateTime(
						nowDateTime, clockSkew, notOnOrAfterDateTime);
				}

				if (Validator.isNull(subjectConfirmationData.getRecipient())) {
					continue;
				}
				else {
					verifyDestination(
						samlMessageContext,
						subjectConfirmationData.getRecipient());
				}

				NameID nameID = subject.getNameID();

				samlMessageContext.setSubjectNameIdentifier(nameID);

				return;
			}
		}

		throw new SubjectException("Unable to verify subject");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WebSsoProfileImpl.class);

	private static final SAMLSignatureProfileValidator
		_samlSignatureProfileValidator = new SAMLSignatureProfileValidator();

	private AttributeResolverRegistry _attributeResolverRegistry;
	private NameIdResolverRegistry _nameIdResolverRegistry;
	private SamlConfiguration _samlConfiguration;

	@Reference
	private SamlIdpSpSessionLocalService _samlIdpSpSessionLocalService;

	@Reference
	private SamlIdpSsoSessionLocalService _samlIdpSsoSessionLocalService;

	private SamlSpAuthRequestLocalService _samlSpAuthRequestLocalService;
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;
	private SamlSpMessageLocalService _samlSpMessageLocalService;

	@Reference
	private UserLocalService _userLocalService;

	private UserResolver _userResolver;

}