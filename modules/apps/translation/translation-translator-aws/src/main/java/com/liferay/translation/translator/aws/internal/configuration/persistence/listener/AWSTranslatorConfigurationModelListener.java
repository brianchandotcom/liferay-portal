/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.translator.aws.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.translation.translator.aws.internal.configuration.AWSTranslatorConfiguration;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.translation.translator.aws.internal.configuration.AWSTranslatorConfiguration",
	service = ConfigurationModelListener.class
)
public class AWSTranslatorConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		if (!GetterUtil.getBoolean(properties.get("enabled"))) {
			return;
		}

		if (Validator.isNull(
				GetterUtil.getString(properties.get("accessKey"))) ||
			Validator.isNull(
				GetterUtil.getString(properties.get("secretKey")))) {

			throw new ConfigurationModelListenerException(
				_language.get(
					LocaleThreadLocal.getThemeDisplayLocale(),
					"access-key-and-secret-key-must-not-be-empty"),
				AWSTranslatorConfiguration.class, getClass(), properties);
		}
	}

	@Reference
	private Language _language;

}