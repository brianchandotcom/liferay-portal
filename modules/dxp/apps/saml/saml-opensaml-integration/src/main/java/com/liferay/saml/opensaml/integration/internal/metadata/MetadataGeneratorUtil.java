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

package com.liferay.saml.opensaml.integration.internal.metadata;

import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.runtime.exception.CredentialException;
import com.liferay.saml.runtime.exception.EntityIdException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.xml.SAMLConstants;
import org.opensaml.saml.ext.saml2alg.DigestMethod;
import org.opensaml.saml.ext.saml2alg.SigningMethod;
import org.opensaml.saml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml.saml2.metadata.SingleLogoutService;
import org.opensaml.saml.saml2.metadata.SingleSignOnService;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.UsageType;
import org.opensaml.xmlsec.SignatureSigningConfiguration;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.AlgorithmRegistry;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;

/**
 * @author Mika Koivisto
 */
public class MetadataGeneratorUtil {

	public static EntityDescriptor buildIdpEntityDescriptor(
			HttpServletRequest request, String entityId,
			boolean wantAuthnRequestSigned, boolean signMetadata,
			boolean requireSSL, Credential credential)
		throws Exception {

		if (Validator.isNull(entityId)) {
			throw new EntityIdException("Entity ID is required");
		}

		if (credential == null) {
			throw new CredentialException("Credential is required");
		}

		EntityDescriptor entityDescriptor =
			OpenSamlUtil.buildEntityDescriptor();

		entityDescriptor.setEntityID(entityId);

		List<RoleDescriptor> roleDescriptors =
			entityDescriptor.getRoleDescriptors();

		RoleDescriptor roleDescriptor = buildIdpSsoDescriptor(
			request, entityId, wantAuthnRequestSigned, requireSSL, credential);

		Extensions extensions = (Extensions)XMLObjectSupport.buildXMLObject(
			Extensions.DEFAULT_ELEMENT_NAME);

		List<XMLObject> unknownXMLObjects = extensions.getUnknownXMLObjects();

		unknownXMLObjects.addAll(_getExtensionXmlObjects(credential));

		roleDescriptor.setExtensions(extensions);

		roleDescriptors.add(roleDescriptor);

		if (signMetadata) {
			OpenSamlUtil.signObject(entityDescriptor, credential, null);
		}

		return entityDescriptor;
	}

	public static IDPSSODescriptor buildIdpSsoDescriptor(
			HttpServletRequest request, String entityId,
			boolean wantAuthnRequestSigned, boolean requireSSL,
			Credential credential)
		throws Exception {

		IDPSSODescriptor idpSSODescriptor =
			OpenSamlUtil.buildIdpSsoDescriptor();

		idpSSODescriptor.addSupportedProtocol(SAMLConstants.SAML20P_NS);
		idpSSODescriptor.setWantAuthnRequestsSigned(wantAuthnRequestSigned);

		List<KeyDescriptor> keyDescriptors =
			idpSSODescriptor.getKeyDescriptors();

		KeyDescriptor keyDescriptor = OpenSamlUtil.buildKeyDescriptor(
			UsageType.SIGNING, OpenSamlUtil.buildKeyInfo(credential));

		keyDescriptors.add(keyDescriptor);

		List<SingleSignOnService> singleSignOnServices =
			idpSSODescriptor.getSingleSignOnServices();

		String portalURL = PortalUtil.getPortalURL(request, requireSSL);
		String pathMain = PortalUtil.getPathMain();

		SingleSignOnService singleSignOnService =
			OpenSamlUtil.buildSingleSignOnService(
				SAMLConstants.SAML2_REDIRECT_BINDING_URI,
				portalURL.concat(pathMain).concat("/portal/saml/sso"));

		singleSignOnServices.add(singleSignOnService);

		singleSignOnService = OpenSamlUtil.buildSingleSignOnService(
			SAMLConstants.SAML2_POST_BINDING_URI,
			portalURL.concat(pathMain).concat("/portal/saml/sso"));

		singleSignOnServices.add(singleSignOnService);

		List<SingleLogoutService> singleLogoutServices =
			idpSSODescriptor.getSingleLogoutServices();

		SingleLogoutService postSingleLogoutService =
			OpenSamlUtil.buildSingleLogoutService(
				SAMLConstants.SAML2_POST_BINDING_URI,
				portalURL.concat(pathMain).concat("/portal/saml/slo"));

		singleLogoutServices.add(postSingleLogoutService);

		SingleLogoutService redirectSingleLogoutService =
			OpenSamlUtil.buildSingleLogoutService(
				SAMLConstants.SAML2_REDIRECT_BINDING_URI,
				portalURL.concat(pathMain).concat("/portal/saml/slo"));

		singleLogoutServices.add(redirectSingleLogoutService);

		return idpSSODescriptor;
	}

	public static EntityDescriptor buildSpEntityDescriptor(
			HttpServletRequest request, String entityId,
			boolean signAuthnRequests, boolean signMetadata, boolean requireSSL,
			boolean wantAssertionsSigned, Credential credential)
		throws Exception {

		EntityDescriptor entityDescriptor =
			OpenSamlUtil.buildEntityDescriptor();

		entityDescriptor.setEntityID(entityId);

		List<RoleDescriptor> roleDescriptors =
			entityDescriptor.getRoleDescriptors();

		RoleDescriptor roleDescriptor = buildSpSsoDescriptor(
			request, entityId, signAuthnRequests, requireSSL,
			wantAssertionsSigned, credential);

		Extensions extensions = (Extensions)XMLObjectSupport.buildXMLObject(
			Extensions.DEFAULT_ELEMENT_NAME);

		List<XMLObject> unknownXMLObjects = extensions.getUnknownXMLObjects();

		unknownXMLObjects.addAll(_getExtensionXmlObjects(credential));

		roleDescriptor.setExtensions(extensions);

		roleDescriptors.add(roleDescriptor);

		if (signMetadata) {
			OpenSamlUtil.signObject(entityDescriptor, credential, null);
		}

		return entityDescriptor;
	}

	public static SPSSODescriptor buildSpSsoDescriptor(
			HttpServletRequest request, String entityId,
			boolean signAuthnRequests, boolean requireSSL,
			boolean wantAssertionsSigned, Credential credential)
		throws Exception {

		SPSSODescriptor spSSODescriptor = OpenSamlUtil.buildSpSsoDescriptor();

		spSSODescriptor.addSupportedProtocol(SAMLConstants.SAML20P_NS);

		spSSODescriptor.setAuthnRequestsSigned(signAuthnRequests);
		spSSODescriptor.setWantAssertionsSigned(wantAssertionsSigned);

		List<AssertionConsumerService> assertionConsumerServices =
			spSSODescriptor.getAssertionConsumerServices();

		String portalURL = PortalUtil.getPortalURL(request, requireSSL);
		String pathMain = PortalUtil.getPathMain();

		AssertionConsumerService assertionConsumerService =
			OpenSamlUtil.buildAssertionConsumerService(
				SAMLConstants.SAML2_POST_BINDING_URI, 1, true,
				portalURL.concat(pathMain).concat("/portal/saml/acs"));

		assertionConsumerServices.add(assertionConsumerService);

		List<KeyDescriptor> keyDescriptors =
			spSSODescriptor.getKeyDescriptors();

		KeyDescriptor keyDescriptor = OpenSamlUtil.buildKeyDescriptor(
			UsageType.SIGNING, OpenSamlUtil.buildKeyInfo(credential));

		keyDescriptors.add(keyDescriptor);

		List<SingleLogoutService> singleLogoutServices =
			spSSODescriptor.getSingleLogoutServices();

		SingleLogoutService postSingleLogoutService =
			OpenSamlUtil.buildSingleLogoutService(
				SAMLConstants.SAML2_POST_BINDING_URI,
				portalURL.concat(pathMain).concat("/portal/saml/slo"));

		singleLogoutServices.add(postSingleLogoutService);

		SingleLogoutService redirectSingleLogoutService =
			OpenSamlUtil.buildSingleLogoutService(
				SAMLConstants.SAML2_REDIRECT_BINDING_URI,
				portalURL.concat(pathMain).concat("/portal/saml/slo"));

		singleLogoutServices.add(redirectSingleLogoutService);

		SingleLogoutService soapSingleLogoutService =
			OpenSamlUtil.buildSingleLogoutService(
				SAMLConstants.SAML2_SOAP11_BINDING_URI,
				portalURL.concat(pathMain).concat("/portal/saml/slo_soap"));

		singleLogoutServices.add(soapSingleLogoutService);

		return spSSODescriptor;
	}

	private static List<XMLObject> _getExtensionXmlObjects(
		Credential credential) {

		SignatureSigningConfiguration signatureSigningConfiguration =
			ConfigurationService.get(SignatureSigningConfiguration.class);

		AlgorithmRegistry algorithmRegistry =
			AlgorithmSupport.getGlobalAlgorithmRegistry();

		Collection<String> blacklistedAlgorithms =
			signatureSigningConfiguration.getBlacklistedAlgorithms();

		ArrayList<XMLObject> xmlObjects = new ArrayList<>();

		for (String digestMethodString :
				signatureSigningConfiguration.
					getSignatureReferenceDigestMethods()) {

			if (!algorithmRegistry.isRuntimeSupported(digestMethodString) ||
				blacklistedAlgorithms.contains(digestMethodString)) {

				continue;
			}

			AlgorithmDescriptor algorithmDescriptor = algorithmRegistry.get(
				digestMethodString);

			DigestMethod digestMethod =
				(DigestMethod)XMLObjectSupport.buildXMLObject(
					DigestMethod.DEFAULT_ELEMENT_NAME);

			digestMethod.setAlgorithm(algorithmDescriptor.getURI());

			xmlObjects.add(digestMethod);
		}

		for (String signatureAlgorithms :
				signatureSigningConfiguration.getSignatureAlgorithms()) {

			if (!algorithmRegistry.isRuntimeSupported(signatureAlgorithms) ||
				blacklistedAlgorithms.contains(signatureAlgorithms)) {

				continue;
			}

			AlgorithmDescriptor algorithmDescriptor = algorithmRegistry.get(
				signatureAlgorithms);

			if (!AlgorithmSupport.credentialSupportsAlgorithmForSigning(
					credential, algorithmDescriptor)) {

				continue;
			}

			SigningMethod signingMethod =
				(SigningMethod)XMLObjectSupport.buildXMLObject(
					SigningMethod.DEFAULT_ELEMENT_NAME);

			signingMethod.setAlgorithm(algorithmDescriptor.getURI());

			xmlObjects.add(signingMethod);
		}

		return xmlObjects;
	}

}