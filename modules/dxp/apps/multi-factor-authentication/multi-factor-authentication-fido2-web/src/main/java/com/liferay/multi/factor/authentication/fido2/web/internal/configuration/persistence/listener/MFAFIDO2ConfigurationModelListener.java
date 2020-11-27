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
	immediate = true,
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