/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.translator.deepl.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.translation.translator.deepl.internal.configuration.DeepLTranslatorConfiguration;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yasuyuki Takeo
 */
@Component(
	property = "model.class.name=com.liferay.translation.translator.deepl.internal.configuration.DeepLTranslatorConfiguration",
	service = ConfigurationModelListener.class
)
public class DeepLTranslatorConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		if (!GetterUtil.getBoolean(properties.get("enabled"))) {
			return;
		}

		String authKey = GetterUtil.getString(properties.get("authKey"));
		String url = GetterUtil.getString(properties.get("url"));

		if (authKey.isEmpty() || url.isEmpty()) {
			throw new ConfigurationModelListenerException(
				_language.get(
					LocaleThreadLocal.getThemeDisplayLocale(),
					"the-auth-key-and-url-must-be-configured"),
				DeepLTranslatorConfiguration.class, getClass(), properties);
		}
	}

	@Reference
	private Language _language;

}