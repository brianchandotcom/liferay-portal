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

package com.liferay.portal.mobile.device.rulegroup.action.impl;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward Han
 */
public class SimpleRedirectActionHandler extends BaseRedirectActionHandler {

	public static String getHandlerType() {
		return SimpleRedirectActionHandler.class.getName();
	}

	@Override
	public Collection<String> getPropertyNames() {
		return _propertyNames;
	}

	@Override
	public String getType() {
		return getHandlerType();
	}

	@Override
	protected String getURL(
		MDRAction mdrAction, HttpServletRequest request,
		HttpServletResponse response) {

		UnicodeProperties typeSettingsProperties =
			mdrAction.getTypeSettingsProperties();

		return GetterUtil.getString(typeSettingsProperties.getProperty("url"));
	}

	private static Collection<String> _propertyNames;

	static {
		_propertyNames = new ArrayList<String>(1);

		_propertyNames.add("url");

		_propertyNames = Collections.unmodifiableCollection(_propertyNames);
	}

}