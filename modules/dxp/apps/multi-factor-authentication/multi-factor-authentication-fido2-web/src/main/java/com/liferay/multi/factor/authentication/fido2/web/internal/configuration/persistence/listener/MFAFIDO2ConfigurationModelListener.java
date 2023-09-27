/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.fido2.web.internal.configuration.persistence.listener;

import com.liferay.multi.factor.authentication.fido2.web.internal.configuration.MFAFIDO2Configuration;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Dictionary;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marta Medio
 */
@Component(
	property = "model.class.name=com.liferay.multi.factor.authentication.fido2.web.internal.configuration.MFAFIDO2Configuration",
	service = ConfigurationModelListener.class
)
public class MFAFIDO2ConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		String[] origins = GetterUtil.getStringValues(
			properties.get("origins"));

		for (String origin : origins) {
			try {
				new URI(origin);
			}
			catch (URISyntaxException uriSyntaxException) {
				_log.error("Unable to parse origin value", uriSyntaxException);

				throw new ConfigurationModelListenerException(
					ResourceBundleUtil.getString(
						_getResourceBundle(), "invalid-origin-value-specified"),
					MFAFIDO2Configuration.class, getClass(), properties);
			}
		}
	}

	private ResourceBundle _getResourceBundle() {
		return ResourceBundleUtil.getBundle(
			LocaleThreadLocal.getThemeDisplayLocale(), getClass());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MFAFIDO2ConfigurationModelListener.class);

}