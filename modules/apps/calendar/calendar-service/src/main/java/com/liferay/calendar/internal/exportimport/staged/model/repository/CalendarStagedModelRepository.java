/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.exportimport.staged.model.repository;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.service.CalendarLocalService;
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
	property = "model.class.name=com.liferay.calendar.model.Calendar",
	service = StagedModelRepository.class
)
public class CalendarStagedModelRepository
	implements StagedModelRepository<Calendar> {

	@Override
	public Calendar addStagedModel(
			PortletDataContext portletDataContext, Calendar calendar)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(Calendar calendar) throws PortalException {
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
	public Calendar fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Calendar fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<Calendar> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Calendar getStagedModel(long calendarId) throws PortalException {
		return _calendarLocalService.getCalendar(calendarId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, Calendar calendar)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Calendar saveStagedModel(Calendar calendar) throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Calendar updateStagedModel(
			PortletDataContext portletDataContext, Calendar calendar)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private CalendarLocalService _calendarLocalService;

}