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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.opensaml.integration.SamlBinding;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.opensaml.integration.metadata.MetadataManager;
import com.liferay.saml.persistence.model.SamlSpSession;
import com.liferay.saml.persistence.service.SamlSpSessionLocalService;
import com.liferay.saml.runtime.SamlException;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.security.IdentifierGenerationStrategy;

import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.messaging.context.InOutOperationContext;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.decoder.servlet.HttpServletRequestMessageDecoder;
import org.opensaml.messaging.encoder.servlet.HttpServletResponseMessageEncoder;
import org.opensaml.messaging.handler.MessageHandler;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.security.impl.SAMLOutboundProtocolMessageSigningHandler;
import org.opensaml.saml.common.messaging.context.SAMLBindingContext;
import org.opensaml.saml.common.messaging.context.SAMLEndpointContext;
import org.opensaml.saml.common.messaging.context.SAMLMetadataContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLProtocolContext;
import org.opensaml.saml.common.messaging.context.SAMLSelfEntityContext;
import org.opensaml.saml.common.xml.SAMLConstants;
import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.opensaml.xmlsec.SignatureValidationParameters;
import org.opensaml.xmlsec.config.DefaultSecurityConfigurationBootstrap;
import org.opensaml.xmlsec.context.SecurityParametersContext;
import org.opensaml.xmlsec.criterion.SignatureValidationConfigurationCriterion;
import org.opensaml.xmlsec.impl.BasicSignatureValidationParametersResolver;

/**
 * @author Mika Koivisto
 */
public abstract class BaseProfile {

	public MessageContext decodeSamlMessage(
			HttpServletRequest httpServletRequest, HttpServletResponse response,
			SamlBinding samlBinding, boolean requireSignature)
		throws Exception {

		MessageContext<SAMLObject> messageContext = getMessageContext(
			httpServletRequest, response);

		Supplier<HttpServletRequestMessageDecoder> messageDecoderSupplier =
			samlBinding.getHttpServletRequestMessageDecoderSupplier();

		HttpServletRequestMessageDecoder httpServletRequestMessageDecoder =
			messageDecoderSupplier.get();

		httpServletRequestMessageDecoder.setHttpServletRequest(
			httpServletRequest);

		httpServletRequestMessageDecoder.initialize();

		httpServletRequestMessageDecoder.decode();

		MessageContext<SAMLObject> inboundMessageContext =
			httpServletRequestMessageDecoder.getMessageContext();

		InOutOperationContext<?, ?> inOutOperationContext =
			new InOutOperationContext(
				inboundMessageContext, new MessageContext());

		messageContext.addSubcontext(inOutOperationContext);

		SAMLBindingContext samlBindingContext =
			inboundMessageContext.getSubcontext(SAMLBindingContext.class);

		messageContext.addSubcontext(samlBindingContext);

		if (_log.isDebugEnabled()) {
			SAMLObject samlObject = inboundMessageContext.getMessage();

			_log.debug(
				"Received message using binding " +
					samlBindingContext.getBindingUri() + " " +
						OpenSamlUtil.marshall(samlObject));
		}

		SAMLPeerEntityContext samlPeerEntityContext =
			inboundMessageContext.getSubcontext(
				SAMLPeerEntityContext.class, true);

		MetadataResolver metadataResolver =
			metadataManager.getMetadataResolver();

		EntityDescriptor entityDescriptor = metadataResolver.resolveSingle(
			new CriteriaSet(
				new EntityIdCriterion(samlPeerEntityContext.getEntityId())));

		if (entityDescriptor == null) {
			SAMLObject samlObject = messageContext.getMessage();

			RequestAbstractType samlRequest = (RequestAbstractType)samlObject;

			throw new SamlException(
				"Unable to resolve metadata for issuer " +
					samlRequest.getIssuer());
		}

		RoleDescriptor roleDescriptor = null;

		if (samlProviderConfigurationHelper.isRoleIdp()) {
			roleDescriptor = entityDescriptor.getSPSSODescriptor(
				SAMLConstants.SAML20P_NS);

			samlPeerEntityContext.setRole(SPSSODescriptor.DEFAULT_ELEMENT_NAME);
		}
		else if (samlProviderConfigurationHelper.isRoleSp()) {
			roleDescriptor = entityDescriptor.getIDPSSODescriptor(
				SAMLConstants.SAML20P_NS);

			samlPeerEntityContext.setRole(
				IDPSSODescriptor.DEFAULT_ELEMENT_NAME);
		}

		SAMLMetadataContext samlMetadataContext =
			samlPeerEntityContext.getSubcontext(
				SAMLMetadataContext.class, true);

		samlMetadataContext.setEntityDescriptor(entityDescriptor);
		samlMetadataContext.setRoleDescriptor(roleDescriptor);

		MessageHandler messageHandler =
			metadataManager.getSecurityMessageHandler(
				httpServletRequest, samlBindingContext.getBindingUri(),
				requireSignature);

		SecurityParametersContext securityParametersContext =
			inboundMessageContext.getSubcontext(
				SecurityParametersContext.class, true);

		BasicSignatureValidationParametersResolver
			basicSignatureValidationParametersResolver =
				new BasicSignatureValidationParametersResolver();

		SignatureValidationParameters signatureValidationParameters =
			basicSignatureValidationParametersResolver.resolveSingle(
				new CriteriaSet(
					new SignatureValidationConfigurationCriterion(
						DefaultSecurityConfigurationBootstrap.
							buildDefaultSignatureValidationConfiguration())));

		signatureValidationParameters.setSignatureTrustEngine(
			metadataManager.getSignatureTrustEngine());

		securityParametersContext.setSignatureValidationParameters(
			signatureValidationParameters);

		inboundMessageContext.addSubcontext(
			messageContext.getSubcontext(SAMLProtocolContext.class));

		messageHandler.invoke(inboundMessageContext);

		messageContext.removeSubcontext(SAMLPeerEntityContext.class);

		messageContext.addSubcontext(samlPeerEntityContext);

		return messageContext;
	}

	public String generateIdentifier(int length) {
		IdentifierGenerationStrategyFactory
			identifierGenerationStrategyFactory =
				getIdentifierGenerationStrategyFactory();

		IdentifierGenerationStrategy identifierGenerationStrategy =
			identifierGenerationStrategyFactory.create(length);

		return identifierGenerationStrategy.generateIdentifier();
	}

	public IdentifierGenerationStrategyFactory
		getIdentifierGenerationStrategyFactory() {

		return _identifierGenerationStrategyFactory;
	}

	public MessageContext<SAMLObject> getMessageContext(
			HttpServletRequest httpServletRequest, HttpServletResponse response)
		throws Exception {

		MessageContext<SAMLObject> messageContext = new MessageContext<>();

		messageContext.setAutoCreateSubcontexts(true);

		RoleDescriptor roleDescriptor = null;

		EntityDescriptor entityDescriptor = metadataManager.getEntityDescriptor(
			httpServletRequest);

		SAMLSelfEntityContext samlSelfEntityContext =
			messageContext.getSubcontext(SAMLSelfEntityContext.class);

		SAMLMetadataContext samlSelfMetadataContext =
			samlSelfEntityContext.getSubcontext(
				SAMLMetadataContext.class, true);

		samlSelfMetadataContext.setEntityDescriptor(entityDescriptor);

		SAMLProtocolContext samlProtocolContext = messageContext.getSubcontext(
			SAMLProtocolContext.class, true);

		samlProtocolContext.setProtocol(SAMLConstants.SAML20P_NS);

		if (samlProviderConfigurationHelper.isRoleIdp()) {
			roleDescriptor = entityDescriptor.getIDPSSODescriptor(
				SAMLConstants.SAML20P_NS);
		}
		else if (samlProviderConfigurationHelper.isRoleSp()) {
			roleDescriptor = entityDescriptor.getSPSSODescriptor(
				SAMLConstants.SAML20P_NS);
		}

		SAMLPeerEntityContext samlPeerEntityContext =
			messageContext.getSubcontext(SAMLPeerEntityContext.class);

		if (samlProviderConfigurationHelper.isRoleIdp()) {
			samlPeerEntityContext.setRole(SPSSODescriptor.DEFAULT_ELEMENT_NAME);
		}
		else if (samlProviderConfigurationHelper.isRoleSp()) {
			samlPeerEntityContext.setRole(
				IDPSSODescriptor.DEFAULT_ELEMENT_NAME);
		}

		samlSelfEntityContext.setEntityId(entityDescriptor.getEntityID());

		samlSelfMetadataContext.setRoleDescriptor(roleDescriptor);

		return messageContext;
	}

	public MessageContext<?> getMessageContext(
			HttpServletRequest httpServletRequest, HttpServletResponse response,
			String peerEntityId)
		throws Exception {

		MessageContext<?> messageContext = getMessageContext(
			httpServletRequest, response);

		SAMLPeerEntityContext samlPeerEntityContext =
			messageContext.getSubcontext(SAMLPeerEntityContext.class, true);

		samlPeerEntityContext.setEntityId(peerEntityId);

		MetadataResolver metadataResolver =
			metadataManager.getMetadataResolver();

		EntityDescriptor entityDescriptor = metadataResolver.resolveSingle(
			new CriteriaSet(new EntityIdCriterion(peerEntityId)));

		if (entityDescriptor == null) {
			throw new SamlException("Unknown peer entity ID " + peerEntityId);
		}

		SAMLMetadataContext samlPeerMetadataContext =
			samlPeerEntityContext.getSubcontext(
				SAMLMetadataContext.class, true);

		samlPeerMetadataContext.setEntityDescriptor(entityDescriptor);

		RoleDescriptor roleDescriptor = null;

		if (samlProviderConfigurationHelper.isRoleIdp()) {
			roleDescriptor = entityDescriptor.getSPSSODescriptor(
				SAMLConstants.SAML20P_NS);
		}
		else if (samlProviderConfigurationHelper.isRoleSp()) {
			roleDescriptor = entityDescriptor.getIDPSSODescriptor(
				SAMLConstants.SAML20P_NS);
		}

		samlPeerMetadataContext.setRoleDescriptor(roleDescriptor);

		return messageContext;
	}

	public SamlBinding getSamlBinding(String communicationProfileId)
		throws PortalException {

		for (SamlBinding samlBinding : _samlBindings) {
			if (communicationProfileId.equals(
					samlBinding.getCommunicationProfileId())) {

				return samlBinding;
			}
		}

		throw new SamlException(
			"Unsupported binding " + communicationProfileId);
	}

	public SamlSpSession getSamlSpSession(
		HttpServletRequest httpServletRequest) {

		String samlSpSessionKey = getSamlSpSessionKey(httpServletRequest);

		if (Validator.isNotNull(samlSpSessionKey)) {
			SamlSpSession samlSpSession =
				samlSpSessionLocalService.fetchSamlSpSessionBySamlSpSessionKey(
					samlSpSessionKey);

			if (samlSpSession != null) {
				return samlSpSession;
			}
		}

		HttpSession session = httpServletRequest.getSession();

		return samlSpSessionLocalService.fetchSamlSpSessionByJSessionId(
			session.getId());
	}

	public String getSamlSpSessionKey(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();

		String samlSpSessionKey = (String)session.getAttribute(
			SamlWebKeys.SAML_SP_SESSION_KEY);

		if (Validator.isNull(samlSpSessionKey)) {
			samlSpSessionKey = CookieKeys.getCookie(
				httpServletRequest, SamlWebKeys.SAML_SP_SESSION_KEY);
		}

		return samlSpSessionKey;
	}

	public String getSamlSsoSessionId(HttpServletRequest httpServletRequest) {
		return CookieKeys.getCookie(
			httpServletRequest, SamlWebKeys.SAML_SSO_SESSION_ID);
	}

	public void logout(
		HttpServletRequest httpServletRequest, HttpServletResponse response) {

		String domain = CookieKeys.getDomain(httpServletRequest);

		Cookie companyIdCookie = new Cookie(
			CookieKeys.COMPANY_ID, StringPool.BLANK);

		if (Validator.isNotNull(domain)) {
			companyIdCookie.setDomain(domain);
		}

		companyIdCookie.setMaxAge(0);
		companyIdCookie.setPath(StringPool.SLASH);

		Cookie idCookie = new Cookie(CookieKeys.ID, StringPool.BLANK);

		if (Validator.isNotNull(domain)) {
			idCookie.setDomain(domain);
		}

		idCookie.setMaxAge(0);
		idCookie.setPath(StringPool.SLASH);

		Cookie passwordCookie = new Cookie(
			CookieKeys.PASSWORD, StringPool.BLANK);

		if (Validator.isNotNull(domain)) {
			passwordCookie.setDomain(domain);
		}

		passwordCookie.setMaxAge(0);
		passwordCookie.setPath(StringPool.SLASH);

		boolean rememberMe = GetterUtil.getBoolean(
			CookieKeys.getCookie(httpServletRequest, CookieKeys.REMEMBER_ME));

		if (!rememberMe) {
			Cookie loginCookie = new Cookie(CookieKeys.LOGIN, StringPool.BLANK);

			if (Validator.isNotNull(domain)) {
				loginCookie.setDomain(domain);
			}

			loginCookie.setMaxAge(0);
			loginCookie.setPath(StringPool.SLASH);

			CookieKeys.addCookie(httpServletRequest, response, loginCookie);
		}

		Cookie rememberMeCookie = new Cookie(
			CookieKeys.REMEMBER_ME, StringPool.BLANK);

		if (Validator.isNotNull(domain)) {
			rememberMeCookie.setDomain(domain);
		}

		rememberMeCookie.setMaxAge(0);
		rememberMeCookie.setPath(StringPool.SLASH);

		CookieKeys.addCookie(httpServletRequest, response, companyIdCookie);
		CookieKeys.addCookie(httpServletRequest, response, idCookie);
		CookieKeys.addCookie(httpServletRequest, response, passwordCookie);
		CookieKeys.addCookie(httpServletRequest, response, rememberMeCookie);

		HttpSession session = httpServletRequest.getSession();

		try {
			session.invalidate();
		}
		catch (Exception e) {
		}
	}

	public void sendSamlMessage(
			MessageContext<?> messageContext, HttpServletResponse response)
		throws PortalException {

		InOutOperationContext inOutOperationContext =
			messageContext.getSubcontext(InOutOperationContext.class);

		MessageContext<XMLObject> outboundMessageContext =
			inOutOperationContext.getOutboundMessageContext();

		SAMLPeerEntityContext samlPeerEntityContext =
			outboundMessageContext.getSubcontext(SAMLPeerEntityContext.class);

		SAMLEndpointContext samlPeerEndpointContext =
			samlPeerEntityContext.getSubcontext(SAMLEndpointContext.class);

		Endpoint endpoint = samlPeerEndpointContext.getEndpoint();

		SamlBinding samlBinding = getSamlBinding(endpoint.getBinding());

		if (_log.isDebugEnabled()) {
			try {
				XMLObject xmlObject = outboundMessageContext.getMessage();

				String samlMessage = OpenSamlUtil.marshall(xmlObject);

				_log.debug(
					"Sending SAML message " + samlMessage + " to " +
						endpoint.getLocation() + " with binding " +
							endpoint.getBinding());
			}
			catch (MarshallingException me) {
			}
		}

		Supplier<HttpServletResponseMessageEncoder> messageEncoderSupplier =
			samlBinding.getHttpServletResponseMessageEncoderSupplier();

		HttpServletResponseMessageEncoder httpServletResponseMessageEncoder =
			messageEncoderSupplier.get();

		SAMLOutboundProtocolMessageSigningHandler
			samlOutboundProtocolMessageSigningHandler =
				new SAMLOutboundProtocolMessageSigningHandler();

		try {
			samlOutboundProtocolMessageSigningHandler.initialize();
			samlOutboundProtocolMessageSigningHandler.invoke(
				outboundMessageContext);

			httpServletResponseMessageEncoder.setHttpServletResponse(response);
			httpServletResponseMessageEncoder.setMessageContext(
				outboundMessageContext);

			httpServletResponseMessageEncoder.initialize();

			httpServletResponseMessageEncoder.encode();
		}
		catch (Exception e) {
			throw new SamlException(
				"Unable to send SAML message to " + endpoint.getLocation() +
					" with binding " + endpoint.getBinding(),
				e);
		}
	}

	protected void addCookie(
		HttpServletRequest httpServletRequest, HttpServletResponse response,
		String cookieName, String cookieValue, int maxAge) {

		Cookie cookie = new Cookie(cookieName, cookieValue);

		cookie.setMaxAge(maxAge);

		if (Validator.isNull(portal.getPathContext())) {
			cookie.setPath(StringPool.SLASH);
		}
		else {
			cookie.setPath(portal.getPathContext());
		}

		cookie.setSecure(httpServletRequest.isSecure());

		response.addCookie(cookie);
	}

	protected void addSamlBinding(SamlBinding samlBinding) {
		_samlBindings.add(samlBinding);
	}

	protected void removeSamlBinding(SamlBinding samlBinding) {
		_samlBindings.remove(samlBinding);
	}

	protected void setIdentifierGenerationStrategyFactory(
		IdentifierGenerationStrategyFactory
			identifierGenerationStrategyFactory) {

		_identifierGenerationStrategyFactory =
			identifierGenerationStrategyFactory;
	}

	protected void setMetadataManager(MetadataManager metadataManager) {
		this.metadataManager = metadataManager;
	}

	protected void setSamlBindings(List<SamlBinding> samlBindings) {
		_samlBindings = samlBindings;
	}

	protected void setSamlProviderConfigurationHelper(
		SamlProviderConfigurationHelper samlProviderConfigurationHelper) {

		this.samlProviderConfigurationHelper = samlProviderConfigurationHelper;
	}

	protected void unsetSamlBinding(SamlBinding samlBinding) {
		removeSamlBinding(samlBinding);
	}

	protected MetadataManager metadataManager;
	protected Portal portal;
	protected SamlProviderConfigurationHelper samlProviderConfigurationHelper;
	protected SamlSpSessionLocalService samlSpSessionLocalService;

	private static final Log _log = LogFactoryUtil.getLog(BaseProfile.class);

	private IdentifierGenerationStrategyFactory
		_identifierGenerationStrategyFactory;
	private List<SamlBinding> _samlBindings = new ArrayList<>();

}