/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.mobile.device.rulegroup.rule.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.mobile.device.Dimensions;
import com.liferay.portal.kernel.mobile.device.rulegroup.rule.RuleHandler;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Edward Han
 * @author Milen Daynkov
 */
public class SimpleRuleHandler implements RuleHandler {

	public static final String PROPERTY_OS = "os";

	public static final String PROPERTY_SCREEN_PHYSICAL_HEIGHT_MAX =
		"screen-physical-height-max";

	public static final String PROPERTY_SCREEN_PHYSICAL_HEIGHT_MIN =
		"screen-physical-height-min";

	public static final String PROPERTY_SCREEN_PHYSICAL_WIDTH_MAX =
		"screen-physical-width-max";

	public static final String PROPERTY_SCREEN_PHYSICAL_WIDTH_MIN =
		"screen-physical-width-min";

	public static final String PROPERTY_SCREEN_RESOLUTION_HEIGHT_MAX =
		"screen-resolution-height-max";

	public static final String PROPERTY_SCREEN_RESOLUTION_HEIGHT_MIN =
		"screen-resolution-height-min";

	public static final String PROPERTY_SCREEN_RESOLUTION_WIDTH_MAX =
		"screen-resolution-width-max";

	public static final String PROPERTY_SCREEN_RESOLUTION_WIDTH_MIN =
		"screen-resolution-width-min";

	public static final String PROPERTY_TABLET = "tablet";

	public static String getHandlerType() {
		return SimpleRuleHandler.class.getName();
	}

	@Override
	public boolean evaluateRule(MDRRule mdrRule, ThemeDisplay themeDisplay) {
		Device device = themeDisplay.getDevice();

		if ((device == null) || Validator.isNull(device.getOS())) {
			return false;
		}

		UnicodeProperties typeSettingsProperties =
			mdrRule.getTypeSettingsProperties();

		String os = typeSettingsProperties.get(PROPERTY_OS);

		if (Validator.isNotNull(os)) {
			String[] operatingSystems = StringUtil.split(os);

			if (!ArrayUtil.contains(operatingSystems, device.getOS())) {
				return false;
			}
		}

		String tablet = typeSettingsProperties.get(PROPERTY_TABLET);

		if (Validator.isNotNull(tablet)) {
			boolean tabletBoolean = GetterUtil.getBoolean(tablet);

			if (tabletBoolean != device.isTablet()) {
				return false;
			}
		}

		Dimensions screenPhysicalSize = device.getScreenPhysicalSize();

		if (!isValidRangeValue(
				mdrRule, PROPERTY_SCREEN_PHYSICAL_HEIGHT_MAX,
				PROPERTY_SCREEN_PHYSICAL_HEIGHT_MIN,
				screenPhysicalSize.getHeight())) {

			return false;
		}

		if (!isValidRangeValue(
				mdrRule, PROPERTY_SCREEN_PHYSICAL_WIDTH_MAX,
				PROPERTY_SCREEN_PHYSICAL_WIDTH_MIN,
				screenPhysicalSize.getWidth())) {

			return false;
		}

		Dimensions screenResolution = device.getScreenResolution();

		if (!isValidRangeValue(
				mdrRule, PROPERTY_SCREEN_RESOLUTION_HEIGHT_MAX,
				PROPERTY_SCREEN_RESOLUTION_HEIGHT_MIN,
				screenResolution.getHeight())) {

			return false;
		}

		if (!isValidRangeValue(
				mdrRule, PROPERTY_SCREEN_RESOLUTION_WIDTH_MAX,
				PROPERTY_SCREEN_RESOLUTION_WIDTH_MIN,
				screenResolution.getWidth())) {

			return false;
		}

		return true;
	}

	@Override
	public Collection<String> getPropertyNames() {
		return _propertyNames;
	}

	@Override
	public String getType() {
		return getHandlerType();
	}

	protected boolean isValidRangeValue(
		MDRRule mdrRule, String maxProperty, String minProperty, float value) {

		UnicodeProperties typeSettingsProperties =
			mdrRule.getTypeSettingsProperties();

		String max = typeSettingsProperties.get(maxProperty);
		String min = typeSettingsProperties.get(minProperty);

		if (Validator.isNull(max) && Validator.isNull(min)) {
			logRangeValue(
				mdrRule, maxProperty, minProperty, value, max, min, true);

			return true;
		}

		if (Validator.isNotNull(max)) {
			float maxFloat = GetterUtil.getFloat(max);

			if (value > maxFloat) {
				logRangeValue(
					mdrRule, maxProperty, minProperty, value, max, min, false);

				return false;
			}

			logRangeValue(
				mdrRule, maxProperty, minProperty, value, max, min, true);
		}

		if (Validator.isNotNull(min)) {
			float minFloat = GetterUtil.getFloat(min);

			if (value < minFloat) {
				logRangeValue(
					mdrRule, maxProperty, minProperty, value, max, min, false);

				return false;
			}

			logRangeValue(
				mdrRule, maxProperty, minProperty, value, max, min, true);
		}

		return true;
	}

	protected void logRangeValue(
		MDRRule mdrRule, String maxProperty, String minProperty, float value,
		String max, String min, boolean valid) {

		if (!_log.isDebugEnabled()) {
			return;
		}

		StringBundler sb = new StringBundler();

		sb.append("Rule ");
		sb.append(mdrRule.getNameCurrentValue());
		sb.append(": The value ");
		sb.append(value);
		sb.append("' is '");

		if (!valid) {
			sb.append("NOT ");
		}

		sb.append("within the allowed range");

		if (Validator.isNotNull(max) && Validator.isNotNull(min)) {
			sb.append(" (");
			sb.append(minProperty);
			sb.append(": ");
			sb.append(min);
			sb.append(", ");
			sb.append(maxProperty);
			sb.append(": ");
			sb.append(max);
			sb.append(")");
		}

		_log.debug(sb.toString());
	}

	private static Log _log = LogFactoryUtil.getLog(SimpleRuleHandler.class);
	private static Collection<String> _propertyNames;

	static {
		_propertyNames = new ArrayList<String>(10);

		_propertyNames.add(PROPERTY_OS);
		_propertyNames.add(PROPERTY_SCREEN_PHYSICAL_WIDTH_MAX);
		_propertyNames.add(PROPERTY_SCREEN_PHYSICAL_WIDTH_MIN);
		_propertyNames.add(PROPERTY_SCREEN_PHYSICAL_HEIGHT_MAX);
		_propertyNames.add(PROPERTY_SCREEN_PHYSICAL_HEIGHT_MIN);
		_propertyNames.add(PROPERTY_SCREEN_RESOLUTION_WIDTH_MAX);
		_propertyNames.add(PROPERTY_SCREEN_RESOLUTION_WIDTH_MIN);
		_propertyNames.add(PROPERTY_SCREEN_RESOLUTION_HEIGHT_MAX);
		_propertyNames.add(PROPERTY_SCREEN_RESOLUTION_HEIGHT_MIN);
		_propertyNames.add(PROPERTY_TABLET);

		_propertyNames = Collections.unmodifiableCollection(_propertyNames);
	}

}