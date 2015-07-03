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

package com.liferay.portlet.calendar.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of 7.0.0, with no direct replacement
 * @generated
 */
@Deprecated
@ProviderType
public interface CalEventFinder {
	public int countByG_SD_T(long groupId, java.util.Date startDateGT,
		java.util.Date startDateLT, boolean timeZoneSensitive,
		java.lang.String[] types);

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByFutureReminders();

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByNoAssets();

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_SD_T(
		long groupId, java.util.Date startDateGT, java.util.Date startDateLT,
		boolean timeZoneSensitive, java.lang.String[] types);

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_SD_T(
		long groupId, java.util.Date startDateGT, java.util.Date startDateLT,
		boolean timeZoneSensitive, java.lang.String[] types, int start, int end);
}