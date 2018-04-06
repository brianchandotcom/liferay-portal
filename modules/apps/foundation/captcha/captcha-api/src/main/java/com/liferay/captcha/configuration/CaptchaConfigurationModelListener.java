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

package com.liferay.captcha.configuration;

import com.liferay.captcha.recaptcha.ReCaptchaImpl;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.captcha.CaptchaConfigurationException;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.captcha.configuration.CaptchaConfiguration",
	service = ConfigurationModelListener.class
)
public class CaptchaConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		try {
			String captchaEngine = (String)properties.get("captchaEngine");

			if (Validator.isNotNull(captchaEngine) &&
				captchaEngine.equals(ReCaptchaImpl.class.getName())) {

				validateReCaptchaKeys(properties);
			}
		}
		catch (CaptchaConfigurationException cce) {
			throw new ConfigurationModelListenerException(
				cce.getMessage(), CaptchaConfiguration.class,
				CaptchaConfigurationModelListener.class, properties);
		}
	}

	protected ResourceBundle getResourceBundle() {
		if (_resourceBundle == null) {
			Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

			return ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass());
		}

		return _resourceBundle;
	}

	protected void validateReCaptchaKeys(Dictionary<String, Object> properties)
		throws CaptchaConfigurationException {

		String reCaptchaPublicKey = (String)properties.get(
			"reCaptchaPublicKey");
		String reCaptchaPrivateKey = (String)properties.get(
			"reCaptchaPrivateKey");

		if (Validator.isNull(reCaptchaPublicKey)) {
			throw new CaptchaConfigurationException(
				ResourceBundleUtil.getString(
					getResourceBundle(),
					"the-recaptcha-public-key-is-not-valid"));
		}

		if (Validator.isNull(reCaptchaPrivateKey)) {
			throw new CaptchaConfigurationException(
				ResourceBundleUtil.getString(
					getResourceBundle(),
					"the-recaptcha-private-key-is-not-valid"));
		}
	}

	private ResourceBundle _resourceBundle;

}