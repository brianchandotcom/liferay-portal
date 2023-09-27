/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.exportimport.staged.model.repository;

import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarResourceLocalService;
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
	property = "model.class.name=com.liferay.calendar.model.CalendarResource",
	service = StagedModelRepository.class
)
public class CalendarResourceStagedModelRepository
	implements StagedModelRepository<CalendarResource> {

	@Override
	public CalendarResource addStagedModel(
			PortletDataContext portletDataContext,
			CalendarResource calendarResource)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(CalendarResource calendarResource)
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
	public CalendarResource fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CalendarResource fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<CalendarResource> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public CalendarResource getStagedModel(long calendarResourceId)
		throws PortalException {

		return _calendarResourceLocalService.getCalendarResource(
			calendarResourceId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			CalendarResource calendarResource)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public CalendarResource saveStagedModel(CalendarResource calendarResource)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public CalendarResource updateStagedModel(
			PortletDataContext portletDataContext,
			CalendarResource calendarResource)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private CalendarResourceLocalService _calendarResourceLocalService;

}