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
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringPool;
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
				String configurationMethodName =
					upgradeKey.getConfigurationMethodName();
				PropertyDataType type = upgradeKey.getType();

				Object value = _getValue(portletPreferences, propertyKey, type);

				if (Validator.isNull(value)) {
					continue;
				}

				properties.put(configurationMethodName, value);

				portletPreferences.reset(propertyKey);
			}

			configuration.update(properties);
		}
		catch (IOException | ReadOnlyException e) {
			throw new UpgradeException(e);
		}
	}

	private static Object _getValue(
		PortletPreferences portletPreferences, String propertyKey,
		PropertyDataType type) {

		switch (type) {
			case BOOLEAN:
				return PrefsPropsUtil.getBoolean(
					portletPreferences, propertyKey);
			case DOUBLE:
				return PrefsPropsUtil.getDouble(
					portletPreferences, propertyKey);
			case INT:
				return PrefsPropsUtil.getInteger(
					portletPreferences, propertyKey);
			case LONG:
				return PrefsPropsUtil.getLong(portletPreferences, propertyKey);
			case SHORT:
				return PrefsPropsUtil.getShort(portletPreferences, propertyKey);
			case STRING_ARRAY:
				return PrefsPropsUtil.getStringArray(
					portletPreferences, propertyKey, StringPool.COMMA);
			default:
				return PrefsPropsUtil.getString(
					portletPreferences, propertyKey);
		}
	}

}