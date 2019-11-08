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

package com.liferay.saml.runtime.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Props;
import com.liferay.saml.runtime.internal.constants.LegacySamlPropsKeys;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Stian Sigvartsen
 * @author Tomas Polesovsky
 */
public abstract class BaseUpgradeSaml extends UpgradeProcess {

	protected String getDefaultValue(String key) {
		return _defaultValues.get(key);
	}

	protected String getPropsValue(Props props, String key, Filter filter) {
		String value = null;

		if ((filter != null) &&
			ArrayUtil.contains(LegacySamlPropsKeys.SAML_KEYS_FILTERED, key)) {

			value = props.get(key, filter);
		}

		if (value == null) {
			value = props.get(key);
		}

		return value;
	}

	private static final Map<String, String> _defaultValues =
		HashMapBuilder.put(
			LegacySamlPropsKeys.SAML_IDP_ASSERTION_LIFETIME, "1800"
		).put(
			LegacySamlPropsKeys.SAML_IDP_METADATA_NAME_ID_ATTRIBUTE,
			"emailAddress"
		).put(
			LegacySamlPropsKeys.SAML_IDP_METADATA_NAME_ID_FORMAT,
			"urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress"
		).put(
			LegacySamlPropsKeys.SAML_IDP_SSO_SESSION_CHECK_INTERVAL, "60"
		).put(
			LegacySamlPropsKeys.SAML_IDP_SSO_SESSION_MAX_AGE, "86400000"
		).put(
			LegacySamlPropsKeys.SAML_KEYSTORE_MANAGER_IMPL,
			"com.liferay.saml.credential." + "FileSystemKeyStoreManagerImpl"
		).put(
			LegacySamlPropsKeys.SAML_KEYSTORE_PASSWORD, "liferay"
		).put(
			LegacySamlPropsKeys.SAML_KEYSTORE_TYPE, "jks"
		).put(
			LegacySamlPropsKeys.SAML_METADATA_MAX_REFRESH_DELAY, "14400000"
		).put(
			LegacySamlPropsKeys.SAML_METADATA_MIN_REFRESH_DELAY, "300000"
		).put(
			LegacySamlPropsKeys.SAML_METADATA_REFRESH_INTERVAL, "30"
		).put(
			LegacySamlPropsKeys.SAML_REPLAY_CACHE_DURATION, "3600000"
		).put(
			LegacySamlPropsKeys.SAML_SP_AUTH_REQUEST_CHECK_INTERVAL, "60"
		).put(
			LegacySamlPropsKeys.SAML_SP_AUTH_REQUEST_MAX_AGE, "86400000"
		).put(
			LegacySamlPropsKeys.SAML_SP_CLOCK_SKEW, "3000"
		).put(
			LegacySamlPropsKeys.SAML_SP_MESSAGE_CHECK_INTERVAL, "60"
		).put(
			LegacySamlPropsKeys.SAML_SP_NAME_ID_FORMAT,
			"urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress"
		).build();

}