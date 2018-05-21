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

package com.liferay.saml.opensaml.integration.metadata;

import com.liferay.saml.runtime.SamlException;

import javax.servlet.http.HttpServletRequest;

import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.ws.security.SecurityPolicyResolver;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.signature.SignatureTrustEngine;

/**
 * @author Mika Koivisto
 */
public interface MetadataManager {

	public int getAssertionLifetime(String entityId);

	public String[] getAttributeNames(String entityId);

	public long getClockSkew();

	public String getDefaultIdpEntityId();

	public EntityDescriptor getEntityDescriptor(HttpServletRequest request)
		throws SamlException;

	public MetadataProvider getMetadataProvider() throws SamlException;

	public String getNameIdAttribute(String entityId);

	public String getNameIdFormat(String entityId);

	public SecurityPolicyResolver getSecurityPolicyResolver(
			String communicationProfileId, boolean requireSignature)
		throws SamlException;

	public SignatureTrustEngine getSignatureTrustEngine() throws SamlException;

	public Credential getSigningCredential() throws SamlException;

	public String getUserAttributeMappings(String entityId);

	public boolean isAttributesEnabled(String entityId);

	public boolean isAttributesNamespaceEnabled(String entityId);

}