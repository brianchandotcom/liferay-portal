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

import java.util.Arrays;
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

		if (device == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Rule evaluation is not possible because the information " +
						"about the device is not available");
			}

			return false;
		}

		if (!isValidMultiValue(mdrRule, PROPERTY_OS, device.getOS())) {
			return false;
		}

		if (!isValidBooleanValue(mdrRule, PROPERTY_TABLET, device.isTablet())) {
			return false;
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

	protected StringBundler getLogStringBundler(
		MDRRule mdrRule, String value, boolean valid) {

		StringBundler sb = new StringBundler();

		sb.append("Rule ");
		sb.append(mdrRule.getNameCurrentValue());
		sb.append(" with the value ");
		sb.append(value);
		sb.append(" is ");

		if (!valid) {
			sb.append("not ");
		}

		return sb;
	}

	protected boolean isValidBooleanValue(
		MDRRule mdrRule, String property, boolean value) {

		UnicodeProperties typeSettingsProperties =
			mdrRule.getTypeSettingsProperties();

		String validValueString = typeSettingsProperties.get(property);

		if (Validator.isNull(validValueString)) {
			return true;
		}

		boolean ruleValue = GetterUtil.getBoolean(validValueString);

		if (ruleValue != value) {
			logBooleanValue(mdrRule, property, value, false);

			return false;
		}

		logBooleanValue(mdrRule, property, value, true);

		return true;
	}

	protected boolean isValidMultiValue(
		MDRRule mdrRule, String property, String value) {

		UnicodeProperties typeSettingsProperties =
			mdrRule.getTypeSettingsProperties();

		String validValueString = typeSettingsProperties.get(property);

		if (Validator.isNull(validValueString)) {
			return true;
		}

		String[] validValues = StringUtil.split(validValueString);

		if (!ArrayUtil.contains(validValues, value)) {
			logMultiValue(mdrRule, property, value, validValues, false);

			return false;
		}

		logMultiValue(mdrRule, property, value, validValues, true);

		return true;
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

	protected void logBooleanValue(
		MDRRule mdrRule, String property, boolean value, boolean valid) {

		if (!_log.isDebugEnabled()) {
			return;
		}

		StringBundler sb = getLogStringBundler(
			mdrRule, String.valueOf(value), valid);

		sb.append("the value configured for the property ");
		sb.append(property);

		_log.debug(sb.toString());
	}

	protected void logMultiValue(
		MDRRule mdrRule, String property, String value, String[] validValues,
		boolean valid) {

		if (!_log.isDebugEnabled()) {
			return;
		}

		StringBundler sb = getLogStringBundler(mdrRule, value, valid);

		sb.append("among the allowed values of ");
		sb.append(StringUtil.merge(validValues));
		sb.append(" for the property \"");
		sb.append(property);
		sb.append("\"");

		_log.debug(sb.toString());
	}

	protected void logRangeValue(
		MDRRule mdrRule, String maxProperty, String minProperty, float value,
		String max, String min, boolean valid) {

		if (!_log.isDebugEnabled()) {
			return;
		}

		StringBundler sb = getLogStringBundler(
			mdrRule, String.valueOf(value), valid);

		sb.append("within the allowed range");

		if (Validator.isNotNull(max) && Validator.isNotNull(min)) {
			sb.append(" of ");
			sb.append(min);
			sb.append(" and ");
			sb.append(max);
			sb.append(" for the minimum property \"");
			sb.append(minProperty);
			sb.append("\" and the maximum property \"");
			sb.append(maxProperty);
			sb.append("\"");
		}

		_log.debug(sb.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SimpleRuleHandler.class);

	private final Collection<String> _propertyNames =
		Collections.unmodifiableCollection(
			Arrays.asList(
				PROPERTY_OS, PROPERTY_SCREEN_PHYSICAL_WIDTH_MAX,
				PROPERTY_SCREEN_PHYSICAL_WIDTH_MIN,
				PROPERTY_SCREEN_PHYSICAL_HEIGHT_MAX,
				PROPERTY_SCREEN_PHYSICAL_HEIGHT_MIN,
				PROPERTY_SCREEN_RESOLUTION_WIDTH_MAX,
				PROPERTY_SCREEN_RESOLUTION_WIDTH_MIN,
				PROPERTY_SCREEN_RESOLUTION_HEIGHT_MAX,
				PROPERTY_SCREEN_RESOLUTION_HEIGHT_MIN, PROPERTY_TABLET));

}