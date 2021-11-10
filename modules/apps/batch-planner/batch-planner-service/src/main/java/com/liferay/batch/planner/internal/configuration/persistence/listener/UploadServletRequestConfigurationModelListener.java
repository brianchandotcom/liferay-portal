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

package com.liferay.batch.planner.internal.configuration.persistence.listener;

import com.liferay.batch.planner.configuration.BatchPlannerConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Dictionary;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(
	configurationPid = "com.liferay.batch.planner.configuration.BatchPlannerConfiguration",
	immediate = true,
	property = "model.class.name=com.liferay.portal.upload.internal.configuration.UploadServletRequestConfiguration",
	service = ConfigurationModelListener.class
)
public class UploadServletRequestConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		long maxSize = GetterUtil.getLong(properties.get("maxSize"));

		if (maxSize > _importFileMaxSize) {
			return;
		}

		throw new ConfigurationModelListenerException(
			ResourceBundleUtil.getString(
				_getResourceBundle(),
				"import-file-max-size-must-be-within-upload-request-max-size-" +
					"boundaries"),
			BatchPlannerConfiguration.class, getClass(), properties);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		BatchPlannerConfiguration batchPlannerConfiguration =
			ConfigurableUtil.createConfigurable(
				BatchPlannerConfiguration.class, properties);

		_importFileMaxSize = batchPlannerConfiguration.importFileMaxSize();
	}

	private ResourceBundle _getResourceBundle() {
		return ResourceBundleUtil.getBundle(
			LocaleThreadLocal.getThemeDisplayLocale(), getClass());
	}

	private long _importFileMaxSize;

}