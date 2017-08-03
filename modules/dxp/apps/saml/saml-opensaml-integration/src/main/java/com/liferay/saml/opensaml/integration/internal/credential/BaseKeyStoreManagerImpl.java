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

package com.liferay.saml.opensaml.integration.internal.credential;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.runtime.configuration.SamlConfiguration;
import com.liferay.saml.runtime.credential.KeyStoreManager;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseKeyStoreManagerImpl implements KeyStoreManager {

	protected long getCompanyId() {
		return CompanyThreadLocal.getCompanyId();
	}

	protected String getSamlKeyStorePassword() {
		return samlConfiguration.keyStorePassword();
	}

	protected String getSamlKeyStorePath() {
		String keyStorePath = samlConfiguration.keyStorePath();

		if (Validator.isNull(keyStorePath)) {
			String liferayHome = PropsUtil.get(PropsKeys.LIFERAY_HOME);

			keyStorePath = liferayHome.concat("/data/keystore.jks");
		}

		return keyStorePath;
	}

	protected String getSamlKeyStoreType() {
		return samlConfiguration.keyStoreType();
	}

	protected void updateConfigurations(Map<String, Object> properties)
		throws Exception {

		samlConfiguration = ConfigurableUtil.createConfigurable(
			SamlConfiguration.class, properties);
	}

	protected SamlConfiguration samlConfiguration;

}