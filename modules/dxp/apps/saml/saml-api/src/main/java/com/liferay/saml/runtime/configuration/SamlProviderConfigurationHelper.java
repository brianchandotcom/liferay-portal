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

package com.liferay.saml.runtime.configuration;

import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Mika Koivisto
 */
public interface SamlProviderConfigurationHelper {

	public SamlProviderConfiguration getSamlProviderConfiguration();

	public boolean isEnabled();

	public boolean isLDAPImportEnabled();

	public boolean isRoleIdp();

	public boolean isRoleSp();

	public void updateProperties(UnicodeProperties properties) throws Exception;

}