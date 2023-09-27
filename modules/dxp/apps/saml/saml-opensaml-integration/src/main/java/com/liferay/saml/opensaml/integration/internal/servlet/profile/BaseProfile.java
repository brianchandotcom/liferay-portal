/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.servlet.profile;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.cookies.constants.CookiesConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.opensaml.integration.internal.binding.SamlBinding;
import com.liferay.saml.opensaml.integration.internal.binding.SamlBindingProvider;
import com.liferay.saml.opensaml.integration.internal.metadata.MetadataManager;
import com.liferay.saml.opensaml.integration.internal.util.ConfigurationServiceBootstrapUtil;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.persistence.model.SamlSpSession;
import com.liferay.saml.persistence.service.SamlSpSessionLocalService;
import com.liferay.saml.runtime.SamlException;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;

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
import org.opensaml.xmlsec.SignatureValidationConfiguration;
import org.opensaml.xmlsec.SignatureValidationParameters;
import org.opensaml.xmlsec.context.SecurityParametersContext;
import org.opensaml.xmlsec.criterion.SignatureValidationConfigurationCriterion;
import org.opensaml.xmlsec.impl.BasicSignatureValidationParametersResolver;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
public abstract class BaseProfile {

	public MessageContext<?> decodeSamlMessage(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, SamlBinding samlBinding,
			boolean requireSignature)
		throws Exception {

		MessageContext<SAMLObject> messageContext = getMessageContext(
			httpServletRequest, httpServletResponse);

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
				StringBundler.concat(
					"Received message using binding ",
					samlBindingContext.getBindingUri(), " ",
					OpenSamlUtil.marshall(samlObject)));
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

		MessageHandler<SAMLObject> messageHandler =
			(MessageHandler<SAMLObject>)
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
						ConfigurationServiceBootstrapUtil.get(
							SignatureValidationConfiguration.class))));

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

		return identifierGenerationStrategyFactory;
	}

	public MessageContext<SAMLObject> getMessageContext(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
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
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String peerEntityId)
		throws Exception {

		MessageContext<?> messageContext = getMessageContext(
			httpServletRequest, httpServletResponse);

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

		HttpSession httpSession = httpServletRequest.getSession();

		return samlSpSessionLocalService.fetchSamlSpSessionByJSessionId(
			httpSession.getId());
	}

	public String getSamlSpSessionKey(HttpServletRequest httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession();

		String samlSpSessionKey = (String)httpSession.getAttribute(
			SamlWebKeys.SAML_SP_SESSION_KEY);

		if (Validator.isNull(samlSpSessionKey)) {
			samlSpSessionKey = CookiesManagerUtil.getCookieValue(
				SamlWebKeys.SAML_SP_SESSION_KEY, httpServletRequest);
		}

		return samlSpSessionKey;
	}

	public String getSamlSsoSessionId(HttpServletRequest httpServletRequest) {
		return CookiesManagerUtil.getCookieValue(
			SamlWebKeys.SAML_SSO_SESSION_ID, httpServletRequest);
	}

	public void logout(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		String domain = CookiesManagerUtil.getDomain(httpServletRequest);

		CookiesManagerUtil.deleteCookies(
			domain, httpServletRequest, httpServletResponse,
			CookiesConstants.NAME_COMPANY_ID);

		CookiesManagerUtil.deleteCookies(
			domain, httpServletRequest, httpServletResponse,
			CookiesConstants.NAME_ID);

		CookiesManagerUtil.deleteCookies(
			domain, httpServletRequest, httpServletResponse,
			CookiesConstants.NAME_PASSWORD);

		CookiesManagerUtil.deleteCookies(
			domain, httpServletRequest, httpServletResponse,
			CookiesConstants.NAME_REMEMBER_ME);

		boolean rememberMe = GetterUtil.getBoolean(
			CookiesManagerUtil.getCookieValue(
				CookiesConstants.NAME_REMEMBER_ME, httpServletRequest));

		if (!rememberMe) {
			CookiesManagerUtil.deleteCookies(
				domain, httpServletRequest, httpServletResponse,
				CookiesConstants.NAME_LOGIN);
		}

		HttpSession httpSession = httpServletRequest.getSession();

		try {
			httpSession.invalidate();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	public void sendSamlMessage(
			MessageContext<?> messageContext,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		InOutOperationContext<?, XMLObject> inOutOperationContext =
			messageContext.getSubcontext(InOutOperationContext.class);

		MessageContext<XMLObject> outboundMessageContext =
			inOutOperationContext.getOutboundMessageContext();

		SAMLPeerEntityContext samlPeerEntityContext =
			outboundMessageContext.getSubcontext(SAMLPeerEntityContext.class);

		SAMLEndpointContext samlPeerEndpointContext =
			samlPeerEntityContext.getSubcontext(SAMLEndpointContext.class);

		Endpoint endpoint = samlPeerEndpointContext.getEndpoint();

		SamlBinding samlBinding = samlBindingProvider.getSamlBinding(
			endpoint.getBinding());

		if (_log.isDebugEnabled()) {
			try {
				XMLObject xmlObject = outboundMessageContext.getMessage();

				String samlMessage = OpenSamlUtil.marshall(xmlObject);

				_log.debug(
					StringBundler.concat(
						"Sending SAML message ", samlMessage, " to ",
						endpoint.getLocation(), " with binding ",
						endpoint.getBinding()));
			}
			catch (MarshallingException marshallingException) {
				if (_log.isDebugEnabled()) {
					_log.debug(marshallingException);
				}
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

			httpServletResponseMessageEncoder.setHttpServletResponse(
				httpServletResponse);
			httpServletResponseMessageEncoder.setMessageContext(
				outboundMessageContext);

			httpServletResponseMessageEncoder.initialize();

			httpServletResponseMessageEncoder.encode();
		}
		catch (Exception exception) {
			throw new SamlException(
				StringBundler.concat(
					"Unable to send SAML message to ", endpoint.getLocation(),
					" with binding ", endpoint.getBinding()),
				exception);
		}
	}

	protected void addNonpersistentCookie(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String cookieName,
		String cookieValue) {

		Cookie cookie = new Cookie(cookieName, cookieValue);

		cookie.setMaxAge(-1);

		if (Validator.isNull(portal.getPathContext())) {
			cookie.setPath(StringPool.SLASH);
		}
		else {
			cookie.setPath(portal.getPathContext());
		}

		CookiesManagerUtil.addCookie(
			CookiesConstants.CONSENT_TYPE_FUNCTIONAL, cookie,
			httpServletRequest, httpServletResponse,
			httpServletRequest.isSecure());
	}

	@Reference
	protected IdentifierGenerationStrategyFactory
		identifierGenerationStrategyFactory;

	@Reference
	protected MetadataManager metadataManager;

	@Reference
	protected Portal portal;

	@Reference
	protected SamlBindingProvider samlBindingProvider;

	@Reference
	protected SamlProviderConfigurationHelper samlProviderConfigurationHelper;

	@Reference
	protected SamlSpSessionLocalService samlSpSessionLocalService;

	private static final Log _log = LogFactoryUtil.getLog(BaseProfile.class);

}