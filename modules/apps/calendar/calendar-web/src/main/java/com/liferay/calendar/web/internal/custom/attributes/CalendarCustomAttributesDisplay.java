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

package com.liferay.calendar.web.internal.custom.attributes;

import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.expando.kernel.model.BaseCustomAttributesDisplay;
import com.liferay.expando.kernel.model.CustomAttributesDisplay;

import org.osgi.service.component.annotations.Component;

/**
 * @author Fabio Pezzutto
 * @author Eduardo Lundgren
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + CalendarPortletKeys.CALENDAR,
	service = CustomAttributesDisplay.class
)
public class CalendarCustomAttributesDisplay
	extends BaseCustomAttributesDisplay {

	@Override
	public String getClassName() {
		return CalendarBooking.class.getName();
	}

}