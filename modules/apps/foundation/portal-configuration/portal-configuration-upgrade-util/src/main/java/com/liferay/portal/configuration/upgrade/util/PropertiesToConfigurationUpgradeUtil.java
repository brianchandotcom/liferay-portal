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

package com.liferay.portal.configuration.upgrade.util;

import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Dictionary;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import org.osgi.service.cm.Configuration;

/**
 * @author Drew Brokke
 */
public class PropertiesToConfigurationUpgradeUtil {

	public static void upgradePropertiesToConfiguration(
			PortletPreferences portletPreferences, Configuration configuration,
			PropertiesToConfigurationUpgradeKey[]
				propertiesToConfigurationUpgradeKeys)
		throws UpgradeException {

		if (ArrayUtil.isEmpty(propertiesToConfigurationUpgradeKeys)) {
			return;
		}

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		try {
			for (PropertiesToConfigurationUpgradeKey
					propertiesToConfigurationUpgradeKey :
						propertiesToConfigurationUpgradeKeys) {

				String propertyKey =
					propertiesToConfigurationUpgradeKey.getPropertyKey();
				PropertyDataType propertyDataType =
					propertiesToConfigurationUpgradeKey.getPropertyDataType();

				Object value = PrefsPropsUtil.getString(
					portletPreferences, propertyKey);

				if (Validator.isNull(value)) {
					continue;
				}

				properties.put(
					propertiesToConfigurationUpgradeKey.
						getConfigurationMethodName(),
					_getTypedValue(value, propertyDataType));

				portletPreferences.reset(propertyKey);
			}

			if (properties.isEmpty()) {
				configuration.delete();
			}
			else {
				configuration.update(properties);
			}
		}
		catch (IOException | ReadOnlyException e) {
			throw new UpgradeException(e);
		}
	}

	private static Object _getTypedValue(
		Object value, PropertyDataType propertyDataType) {

		if (propertyDataType == PropertyDataType.BOOLEAN) {
			return GetterUtil.getBoolean(value);
		}
		else if (propertyDataType == PropertyDataType.DOUBLE) {
			return GetterUtil.getDouble(value);
		}
		else if (propertyDataType == PropertyDataType.INT) {
			return GetterUtil.getInteger(value);
		}
		else if (propertyDataType == PropertyDataType.LONG) {
			return GetterUtil.getLong(value);
		}
		else if (propertyDataType == PropertyDataType.SHORT) {
			return GetterUtil.getShort(value);
		}
		else if (propertyDataType == PropertyDataType.STRING_ARRAY) {
			return StringUtil.split((String)value);
		}
		else {
			return value;
		}
	}

}