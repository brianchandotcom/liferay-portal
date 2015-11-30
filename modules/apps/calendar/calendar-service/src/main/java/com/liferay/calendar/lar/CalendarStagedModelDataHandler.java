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

package com.liferay.calendar.lar;

import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Daniel Kocsis
 */
@Component(
	immediate = true, property = {"javax.portlet.name=" + CalendarPortletKeys.CALENDAR},
	service = StagedModelDataHandler.class
)
public class CalendarStagedModelDataHandler
	extends BaseStagedModelDataHandler<Calendar> {

	public static final String[] CLASS_NAMES = {Calendar.class.getName()};

	@Override
	public void deleteStagedModel(Calendar calendar) throws PortalException {
		_calendarLocalService.deleteCalendar(calendar);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Calendar calendar = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (calendar != null) {
			deleteStagedModel(calendar);
		}
	}

	@Override
	public Calendar fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _calendarLocalService.fetchCalendarByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<Calendar> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _calendarLocalService.getCalendarsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<Calendar>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(Calendar calendar) {
		return calendar.getNameCurrentValue();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Calendar calendar)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, calendar, calendar.getCalendarResource(),
			PortletDataContext.REFERENCE_TYPE_STRONG);

		Element calendarElement = portletDataContext.getExportDataElement(
			calendar);

		portletDataContext.addClassedModel(
			calendarElement, ExportImportPathUtil.getModelPath(calendar),
			calendar);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long calendarId)
		throws Exception {

		Calendar existingCalendar = fetchMissingReference(uuid, groupId);

		if (existingCalendar == null) {
			return;
		}

		Map<Long, Long> calendarIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Calendar.class);

		calendarIds.put(calendarId, existingCalendar.getCalendarId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Calendar calendar)
		throws Exception {

		long userId = portletDataContext.getUserId(calendar.getUserUuid());

		Map<Long, Long> calendarResourceIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CalendarResource.class);

		long calendarResourceId = MapUtil.getLong(
			calendarResourceIds, calendar.getCalendarResourceId(),
			calendar.getCalendarResourceId());

		Map<Locale, String> calendarNameMap = getCalendarNameMap(
			portletDataContext, calendar);

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			calendar);

		Calendar importedCalendar = null;

		if (portletDataContext.isDataStrategyMirror()) {
			Calendar existingCalendar = fetchStagedModelByUuidAndGroupId(
				calendar.getUuid(), portletDataContext.getScopeGroupId());

			if (existingCalendar == null) {
				serviceContext.setUuid(calendar.getUuid());

				importedCalendar = _calendarLocalService.addCalendar(
					userId, portletDataContext.getScopeGroupId(),
					calendarResourceId, calendarNameMap,
					calendar.getDescriptionMap(), calendar.getTimeZoneId(),
					calendar.getColor(), calendar.isDefaultCalendar(),
					calendar.isEnableComments(), calendar.isEnableRatings(),
					serviceContext);
			}
			else {
				importedCalendar = _calendarLocalService.updateCalendar(
					existingCalendar.getCalendarId(), calendar.getNameMap(),
					calendar.getDescriptionMap(), calendar.getTimeZoneId(),
					calendar.getColor(), calendar.isDefaultCalendar(),
					calendar.isEnableComments(), calendar.isEnableRatings(),
					serviceContext);
			}
		}
		else {
			importedCalendar = _calendarLocalService.addCalendar(
				userId, portletDataContext.getScopeGroupId(),
				calendarResourceId, calendarNameMap,
				calendar.getDescriptionMap(), calendar.getTimeZoneId(),
				calendar.getColor(), calendar.isDefaultCalendar(),
				calendar.isEnableComments(), calendar.isEnableRatings(),
				serviceContext);
		}

		portletDataContext.importClassedModel(calendar, importedCalendar);
	}

	protected Map<Locale, String> getCalendarNameMap(
			PortletDataContext portletDataContext, Calendar calendar)
		throws Exception {

		Group sourceGroup = _groupLocalService.fetchGroup(
			portletDataContext.getSourceGroupId());

		String calendarName = calendar.getName(LocaleUtil.getDefault());

		if ((sourceGroup == null) ||
			!calendarName.equals(sourceGroup.getDescriptiveName())) {

			return calendar.getNameMap();
		}

		Map<Locale, String> calendarNameMap = new HashMap<>();

		Group scopeGroup = _groupLocalService.getGroup(
			portletDataContext.getScopeGroupId());

		calendarNameMap.put(
			LocaleUtil.getDefault(), scopeGroup.getDescriptiveName());

		return calendarNameMap;
	}

	@Reference(unbind = "-")
	protected void setCalendarLocalService(
		CalendarLocalService calendarLocalService) {

		_calendarLocalService = calendarLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	private volatile CalendarLocalService _calendarLocalService;
	private volatile GroupLocalService _groupLocalService;

}