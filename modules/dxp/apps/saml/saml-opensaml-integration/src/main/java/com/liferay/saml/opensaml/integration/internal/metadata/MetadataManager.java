/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.metadata;

import com.liferay.saml.runtime.SamlException;

import javax.servlet.http.HttpServletRequest;

import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.security.impl.MetadataCredentialResolver;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;

/**
 * @author Mika Koivisto
 */
public interface MetadataManager {

	public Credential getEncryptionCredential() throws SamlException;

	public EntityDescriptor getEntityDescriptor(
			HttpServletRequest httpServletRequest)
		throws SamlException;

	public MetadataCredentialResolver getMetadataCredentialResolver();

	public MetadataResolver getMetadataResolver();

	public SignatureTrustEngine getSignatureTrustEngine() throws SamlException;

	public Credential getSigningCredential() throws SamlException;

	public boolean isAttributesEnabled(String entityId);

	public boolean isAttributesNamespaceEnabled(String entityId);

}