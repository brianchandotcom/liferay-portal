/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.GetterUtil;
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

	public static final String PARAMETER_OS = "os";
	public static final String PARAMETER_TABLET = "tablet";

	public boolean evaluateRule(MDRRule rule, ThemeDisplay themeDisplay) {
		Device device = themeDisplay.getDevice();

		if (device == null) {
			return false;
		}

		UnicodeProperties ruleTypeSettings =
			rule.getTypeSettingsProperties();

		String os = ruleTypeSettings.get(PARAMETER_OS);
		String tablet = ruleTypeSettings.get(PARAMETER_TABLET);

		boolean result = true;

		if (Validator.isNotNull(os)) {
			result = os.equals(device.getOS());
		}

		if (Validator.isNotNull(tablet)) {
			boolean tabletRequired = GetterUtil.get(tablet, false);

			result = result && (tabletRequired == device.isTablet());
		}

		return result;
	}

	public Collection<String> getPropertyNames() {
		return _propertyNames;
	}

	public static String getHandlerType() {
		return SimpleRuleHandler.class.getName();
	}

	public String getType() {
		return getHandlerType();
	}

	private static Collection<String> _propertyNames;

	static {
		_propertyNames = new ArrayList<String>(2);

		_propertyNames.add(PARAMETER_OS);
		_propertyNames.add(PARAMETER_TABLET);

		_propertyNames = Collections.unmodifiableCollection(_propertyNames);
	}

}