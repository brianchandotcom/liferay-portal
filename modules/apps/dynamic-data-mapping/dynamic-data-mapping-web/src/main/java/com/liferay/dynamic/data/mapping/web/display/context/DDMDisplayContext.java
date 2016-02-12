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

package com.liferay.dynamic.data.mapping.web.display.context;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletRequest;

/**
 * @author Rafael Praxedes
 */
public class DDMDisplayContext {

	public DDMDisplayContext(PortletRequest portletRequest)
		throws PortalException {

		_portletRequest = portletRequest;
	}

	public String getOrderByCol() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_portletRequest);

		String orderByCol = ParamUtil.getString(_portletRequest, "orderByCol");

		if (Validator.isNull(orderByCol)) {
			orderByCol = portalPreferences.getValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-col",
				"modified-date");
		}
		else {
			portalPreferences.setValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-col",
				orderByCol);
		}

		return orderByCol;
	}

	public String getOrderByType() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_portletRequest);

		String orderByType = ParamUtil.getString(
			_portletRequest, "orderByType");

		if (Validator.isNull(orderByType)) {
			orderByType = portalPreferences.getValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-type",
				"asc");
		}
		else {
			portalPreferences.setValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-type",
				orderByType);
		}

		return orderByType;
	}

	private final PortletRequest _portletRequest;

}