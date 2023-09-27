/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.internal.configuration.persistence.listener;

import com.liferay.asset.auto.tagger.internal.configuration.AssetAutoTaggerSystemConfiguration;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Dictionary;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 * @author Katie Nesterovich
 */
@Component(
	property = "model.class.name=com.liferay.asset.auto.tagger.internal.configuration.AssetAutoTaggerSystemConfiguration",
	service = ConfigurationModelListener.class
)
public class AssetAutoTaggerSystemConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		int maximumNumberOfTagsPerAsset = GetterUtil.getInteger(
			properties.get("maximumNumberOfTagsPerAsset"));

		if (maximumNumberOfTagsPerAsset < 0) {
			throw new ConfigurationModelListenerException(
				ResourceBundleUtil.getString(
					_getResourceBundle(),
					"maximum-number-of-tags-per-asset-cannot-be-negative"),
				AssetAutoTaggerSystemConfiguration.class, getClass(),
				properties);
		}
	}

	private ResourceBundle _getResourceBundle() {
		return ResourceBundleUtil.getBundle(
			LocaleThreadLocal.getThemeDisplayLocale(), getClass());
	}

}