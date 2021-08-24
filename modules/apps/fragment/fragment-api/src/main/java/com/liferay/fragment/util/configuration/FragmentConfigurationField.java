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

import com.liferay.fragment.constants.FragmentConfigurationFieldDataType;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Víctor Galán
 */
public class FragmentConfigurationField {

	public FragmentConfigurationField(JSONObject fieldJSONObject) {
		_name = fieldJSONObject.getString("name");
		_dataType = fieldJSONObject.getString("dataType");
		_defaultValue = fieldJSONObject.getString("defaultValue");
		_localizable = fieldJSONObject.getBoolean("localizable");
		_type = fieldJSONObject.getString("type");
	}

	public FragmentConfigurationField(
		String name, String dataType, String defaultValue, boolean localizable,
		String type) {

		_name = name;
		_dataType = dataType;
		_defaultValue = defaultValue;
		_localizable = localizable;
		_type = type;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 * #FragmentConfigurationField(String, String, String, boolean, String)}
	 */
	@Deprecated
	public FragmentConfigurationField(
		String name, String dataType, String defaultValue, String type) {

		_name = name;
		_dataType = dataType;
		_defaultValue = defaultValue;
		_type = type;

		_localizable = false;
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

	public FragmentConfigurationFieldDataType
		getFragmentConfigurationFieldDataType() {

		if (StringUtil.equalsIgnoreCase(getDataType(), "array")) {
			return FragmentConfigurationFieldDataType.ARRAY;
		}
		else if (StringUtil.equalsIgnoreCase(getDataType(), "bool")) {
			return FragmentConfigurationFieldDataType.BOOLEAN;
		}
		else if (StringUtil.equalsIgnoreCase(getDataType(), "double")) {
			return FragmentConfigurationFieldDataType.DOUBLE;
		}
		else if (StringUtil.equalsIgnoreCase(getDataType(), "int")) {
			return FragmentConfigurationFieldDataType.INTEGER;
		}
		else if (StringUtil.equalsIgnoreCase(getDataType(), "object")) {
			return FragmentConfigurationFieldDataType.OBJECT;
		}
		else if (StringUtil.equalsIgnoreCase(getDataType(), "string")) {
			return FragmentConfigurationFieldDataType.STRING;
		}

		return null;
	}

	public String getName() {
		return _name;
	}

	public String getType() {
		return _type;
	}

	public boolean isLocalizable() {
		return _localizable;
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

				LayoutDisplayPageProviderTracker
					layoutDisplayPageProviderTracker =
						_serviceTracker.getService();

				LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
					layoutDisplayPageProviderTracker.
						getLayoutDisplayPageProviderByClassName(className);

				if (layoutDisplayPageProvider == null) {
					return _defaultValue;
				}

				long classPK = defaultValueJSONObject.getLong("classPK");

				InfoItemReference infoItemReference = new InfoItemReference(
					className, classPK);

				LayoutDisplayPageObjectProvider<?>
					layoutDisplayPageObjectProvider =
						layoutDisplayPageProvider.
							getLayoutDisplayPageObjectProvider(
								infoItemReference);

				defaultValueJSONObject.put(
					"title",
					layoutDisplayPageObjectProvider.getTitle(
						LocaleUtil.getMostRelevantLocale()));

				return defaultValueJSONObject.toString();
			}
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to parse default value JSON object", portalException);
		}

		return _defaultValue;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentConfigurationField.class);

	private static final ServiceTracker
		<LayoutDisplayPageProviderTracker, LayoutDisplayPageProviderTracker>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			FragmentConfigurationField.class);

		_serviceTracker = new ServiceTracker<>(
			bundle.getBundleContext(), LayoutDisplayPageProviderTracker.class,
			null);

		_serviceTracker.open();
	}

	private final String _dataType;
	private final String _defaultValue;
	private final boolean _localizable;
	private final String _name;
	private final String _type;

}