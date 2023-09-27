/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.configuration.persistence.listener;

import com.google.re2j.PatternSyntaxException;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.redirect.internal.configuration.RedirectPatternConfiguration;
import com.liferay.redirect.internal.util.PatternUtil;

import java.util.Arrays;
import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.redirect.internal.configuration.RedirectPatternConfiguration",
	service = ConfigurationModelListener.class
)
public class RedirectPatternConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		try {
			String[] patternStrings = (String[])properties.get(
				"patternStrings");

			if (ArrayUtil.isEmpty(patternStrings)) {
				return;
			}

			PatternUtil.parse(patternStrings);
		}
		catch (PatternSyntaxException patternSyntaxException) {
			_log.error(patternSyntaxException);

			throw new ConfigurationModelListenerException(
				Arrays.toString((String[])properties.get("patternStrings")) +
					" must contain regular expression/replacement pairs",
				RedirectPatternConfiguration.class,
				RedirectPatternConfigurationModelListener.class, properties);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RedirectPatternConfigurationModelListener.class);

}