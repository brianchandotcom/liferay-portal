/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.model.listener;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(service = ModelListener.class)
public class CalendarStagingModelListener extends BaseModelListener<Calendar> {

	@Override
	public void onAfterCreate(Calendar calendar) throws ModelListenerException {
		if (_isSkipEvent(calendar)) {
			return;
		}

		_stagingModelListener.onAfterCreate(calendar);
	}

	@Override
	public void onAfterRemove(Calendar calendar) throws ModelListenerException {
		_stagingModelListener.onAfterRemove(calendar);
	}

	@Override
	public void onAfterUpdate(Calendar originalCalendar, Calendar calendar)
		throws ModelListenerException {

		if (_isSkipEvent(calendar)) {
			return;
		}

		_stagingModelListener.onAfterUpdate(calendar);
	}

	private boolean _isSkipEvent(Calendar calendar) {
		CalendarResource guestCalendarResource = null;

		try {
			guestCalendarResource =
				CalendarResourceUtil.fetchGuestCalendarResource(
					calendar.getCompanyId());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		if ((guestCalendarResource != null) &&
			(guestCalendarResource.getCalendarResourceId() ==
				calendar.getCalendarResourceId())) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CalendarStagingModelListener.class);

	@Reference
	private StagingModelListener<Calendar> _stagingModelListener;

}