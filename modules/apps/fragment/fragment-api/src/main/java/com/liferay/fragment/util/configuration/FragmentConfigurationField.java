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

package com.liferay.fragment.util.configuration;

import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Víctor Galán
 */
public class FragmentConfigurationField {

	public FragmentConfigurationField(JSONObject fieldJSONObject) {
		_name = fieldJSONObject.getString("name");
		_dataType = fieldJSONObject.getString("dataType");
		_defaultValue = fieldJSONObject.getString("defaultValue");
		_type = fieldJSONObject.getString("type");
	}

	public FragmentConfigurationField(
		String name, String dataType, String defaultValue, String type) {

		_name = name;
		_dataType = dataType;
		_defaultValue = defaultValue;
		_type = type;
	}

	public String getDataType() {
		return _dataType;
	}

	public String getDefaultValue() {
		if (Validator.isNotNull(_defaultValue) &&
			!Objects.equals("itemSelector", _type)) {

			return _defaultValue;
		}
		else if (Objects.equals("colorPalette", _type)) {
			return _getColorPaletteDefaultValue();
		}
		else if (Objects.equals("itemSelector", _type)) {
			return _getItemSelectorDefaultValue();
		}

		return StringPool.BLANK;
	}

	public String getName() {
		return _name;
	}

	public String getType() {
		return _type;
	}

	private String _getColorPaletteDefaultValue() {
		JSONObject defaultValueJSONObject = JSONUtil.put(
			"cssClass", StringPool.BLANK
		).put(
			"rgbValue", StringPool.BLANK
		);

		return defaultValueJSONObject.toString();
	}

	private String _getItemSelectorDefaultValue() {
		if (Validator.isNull(_defaultValue)) {
			return _defaultValue;
		}

		try {
			JSONObject defaultValueJSONObject =
				JSONFactoryUtil.createJSONObject(_defaultValue);

			if (defaultValueJSONObject.has("className") &&
				defaultValueJSONObject.has("classPK")) {

				String className = defaultValueJSONObject.getString(
					"className");

				InfoDisplayContributor infoDisplayContributor =
					_serviceTrackerMap.getService(className);

				if (infoDisplayContributor == null) {
					return _defaultValue;
				}

				long classPK = defaultValueJSONObject.getLong("classPK");

				InfoDisplayObjectProvider infoDisplayObjectProvider =
					infoDisplayContributor.getInfoDisplayObjectProvider(
						classPK);

				defaultValueJSONObject.put(
					"title",
					infoDisplayObjectProvider.getTitle(
						LocaleUtil.getMostRelevantLocale()));

				return defaultValueJSONObject.toString();
			}
		}
		catch (PortalException pe) {
			_log.error("Unable to parse default value JSON object", pe);
		}

		return _defaultValue;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentConfigurationField.class);

	private static final ServiceTrackerMap<String, InfoDisplayContributor>
		_serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			FragmentConfigurationField.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, InfoDisplayContributor.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(infoDisplayContributor, emitter) -> emitter.emit(
					infoDisplayContributor.getClassName())));
	}

	private final String _dataType;
	private final String _defaultValue;
	private final String _name;
	private final String _type;

}