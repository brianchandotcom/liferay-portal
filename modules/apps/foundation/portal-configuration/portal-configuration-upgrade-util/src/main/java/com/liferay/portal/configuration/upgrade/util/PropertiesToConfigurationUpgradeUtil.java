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
			PropertiesToConfigurationUpgradeKey[] upgradeKeys)
		throws UpgradeException {

		if (ArrayUtil.isEmpty(upgradeKeys)) {
			return;
		}

		Dictionary properties = configuration.getProperties();

		if (properties == null) {
			properties = new HashMapDictionary();
		}

		try {
			for (PropertiesToConfigurationUpgradeKey upgradeKey : upgradeKeys) {
				String propertyKey = upgradeKey.getPropertyKey();
				PropertyDataType type = upgradeKey.getType();

				Object value = PrefsPropsUtil.getString(
					portletPreferences, propertyKey);

				if (Validator.isNull(value)) {
					continue;
				}

				properties.put(
					upgradeKey.getConfigurationMethodName(),
					_getTypedValue(value, type));

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

	private static Object _getTypedValue(Object value, PropertyDataType type) {
		switch (type) {
			case BOOLEAN:
				return GetterUtil.getBoolean(value);
			case DOUBLE:
				return GetterUtil.getDouble(value);
			case INT:
				return GetterUtil.getInteger(value);
			case LONG:
				return GetterUtil.getLong(value);
			case SHORT:
				return GetterUtil.getShort(value);
			case STRING_ARRAY:
				return StringUtil.split((String)value);
			default:
				return value;
		}
	}

}