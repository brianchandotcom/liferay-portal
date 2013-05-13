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

import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.mobile.device.rulegroup.rule.RuleHandler;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
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
 */
public class SimpleRuleHandler implements RuleHandler {

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

		boolean result = true;

		String os = typeSettingsProperties.get("os");

		if (Validator.isNotNull(os)) {
			String[] operatingSystems = StringUtil.split(os);

			if (ArrayUtil.contains(operatingSystems, device.getOS())) {
				result = true;
			}
			else {
				result = false;
			}
		}

		String tablet = typeSettingsProperties.get("tablet");

		if (Validator.isNotNull(tablet)) {
			boolean tabletBoolean = GetterUtil.getBoolean(tablet);

			if (result && (tabletBoolean == device.isTablet())) {
				result = true;
			}
			else {
				result = false;
			}
		}

		return result;
	}

	@Override
	public Collection<String> getPropertyNames() {
		return _propertyNames;
	}

	@Override
	public String getType() {
		return getHandlerType();
	}

	private static Collection<String> _propertyNames;

	static {
		_propertyNames = new ArrayList<String>(2);

		_propertyNames.add("os");
		_propertyNames.add("tablet");

		_propertyNames = Collections.unmodifiableCollection(_propertyNames);
	}

}