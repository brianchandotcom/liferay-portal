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

package com.liferay.translation.translator.aws.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.translation.translator.aws.internal.configuration.AWSTranslatorConfiguration;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
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
				LanguageUtil.get(
					LocaleThreadLocal.getThemeDisplayLocale(),
					"access-key-and-secret-key-must-not-be-empty"),
				AWSTranslatorConfiguration.class, getClass(), properties);
		}
	}

}