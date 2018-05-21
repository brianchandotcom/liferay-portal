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

package com.liferay.saml.opensaml.integration.internal.credential;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
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

		String liferayHome = PropsUtil.get(PropsKeys.LIFERAY_HOME);

		if (Validator.isNull(keyStorePath)) {
			return liferayHome.concat("/data/keystore.jks");
		}

		return StringUtil.replace(keyStorePath, "${liferay.home}", liferayHome);
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