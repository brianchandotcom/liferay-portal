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

package com.liferay.portal.mobile.device.rulegroup.action.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
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

	public Collection<String> getPropertyNames() {
		return _propertyNames;
	}

	public String getType() {
		return getHandlerType();
	}

	protected String getURL(
			MDRAction action, HttpServletRequest request,
			HttpServletResponse response)
		throws PortalException, SystemException {

		UnicodeProperties typeSettingsProperties =
			action.getTypeSettingsProperties();

		return GetterUtil.get(
			typeSettingsProperties.getProperty(URL), StringPool.BLANK);
	}

	private static final String URL = "url";

	private static Collection<String> _propertyNames;

	static {
		_propertyNames = new ArrayList<String>(1);

		_propertyNames.add(URL);

		_propertyNames = Collections.unmodifiableCollection(_propertyNames);
	}
}