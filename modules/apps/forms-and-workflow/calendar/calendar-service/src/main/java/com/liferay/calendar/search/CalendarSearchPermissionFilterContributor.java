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

package com.liferay.calendar.search;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.portal.search.permission.SearchPermissionFilterContributor;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(immediate = true, service = SearchPermissionFilterContributor.class)
public class CalendarSearchPermissionFilterContributor
	implements SearchPermissionFilterContributor {

	@Override
	public Optional<String> getParentEntryClassNameOptional(
		String entryClassName) {

		if (entryClassName.equals(CalendarBooking.class.getName())) {
			return Optional.of(Calendar.class.getName());
		}

		return Optional.empty();
	}

}