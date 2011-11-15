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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;

import java.util.List;

/**
 * @author Juan Fernández
 */
public class VerifyCalendar extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		List<CalEvent> events = CalEventLocalServiceUtil.getNoAssetEvents();

		if (_log.isDebugEnabled()) {
			_log.debug("Processing " + events.size() + " events with no asset");
		}

		for (CalEvent event : events) {
			try {
				CalEventLocalServiceUtil.updateAsset(
					event.getUserId(), event, null, null, null);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to update asset for event " +
							event.getEventId() + ": " + e.getMessage());
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Assets verified for events");
		}

	}

	private static Log _log = LogFactoryUtil.getLog(VerifyCalendar.class);

}