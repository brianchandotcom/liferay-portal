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

package com.liferay.saml.opensaml.integration.internal.metadata;

import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.runtime.exception.CredentialException;
import com.liferay.saml.runtime.exception.EntityIdException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml2.metadata.SingleLogoutService;
import org.opensaml.saml2.metadata.SingleSignOnService;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.UsageType;

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

		roleDescriptors.add(roleDescriptor);

		if (signMetadata) {
			OpenSamlUtil.signObject(entityDescriptor, credential);
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

		idpSSODescriptor.setID(entityId);
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

		roleDescriptors.add(roleDescriptor);

		if (signMetadata) {
			OpenSamlUtil.signObject(entityDescriptor, credential);
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
		spSSODescriptor.setID(entityId);
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

}