/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.exportimport.staged.model.repository;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	property = "model.class.name=com.liferay.calendar.model.CalendarBooking",
	service = StagedModelRepository.class
)
public class CalendarBookingStagedModelRepository
	implements StagedModelRepository<CalendarBooking> {

	@Override
	public CalendarBooking addStagedModel(
			PortletDataContext portletDataContext,
			CalendarBooking calendarBooking)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(CalendarBooking calendarBooking)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public CalendarBooking fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CalendarBooking fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<CalendarBooking> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public CalendarBooking getStagedModel(long calendarBookingId)
		throws PortalException {

		return _calendarBookingLocalService.getCalendarBooking(
			calendarBookingId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			CalendarBooking calendarBooking)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public CalendarBooking saveStagedModel(CalendarBooking calendarBooking)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public CalendarBooking updateStagedModel(
			PortletDataContext portletDataContext,
			CalendarBooking calendarBooking)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private CalendarBookingLocalService _calendarBookingLocalService;

}