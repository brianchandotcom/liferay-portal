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

package com.liferay.saml.runtime.metadata;

import com.liferay.saml.runtime.SamlException;

import java.security.cert.X509Certificate;

/**
 * @author Michael C. Han
 */
public interface LocalEntityManager {

	public String getEncodedLocalEntityCertificate() throws SamlException;

	public X509Certificate getLocalEntityCertificate() throws SamlException;

	public String getLocalEntityId();

	public boolean hasDefaultIdpRole();

}