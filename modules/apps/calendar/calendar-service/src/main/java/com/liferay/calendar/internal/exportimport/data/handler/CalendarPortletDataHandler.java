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

package com.liferay.calendar.internal.exportimport.data.handler;

import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.calendar.service.CalendarNotificationTemplateLocalService;
import com.liferay.calendar.service.CalendarResourceLocalService;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 * @author Andrea Di Giorgi
 */
@Component(
	property = "javax.portlet.name=" + CalendarPortletKeys.CALENDAR,
	service = PortletDataHandler.class
)
public class CalendarPortletDataHandler extends BasePortletDataHandler {

	public static final String[] CLASS_NAMES = {
		Calendar.class.getName(), CalendarBooking.class.getName(),
		CalendarNotificationTemplate.class.getName(),
		CalendarResource.class.getName()
	};

	public static final String NAMESPACE = "calendar";

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Activate
	protected void activate() {
		setDataLocalized(true);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(Calendar.class),
			new StagedModelType(CalendarBooking.class),
			new StagedModelType(CalendarNotificationTemplate.class),
			new StagedModelType(CalendarResource.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "calendars", true, false, null,
				Calendar.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "calendar-resources", true, false, null,
				CalendarResource.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "calendar-bookings", true, false, null,
				CalendarBooking.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "calendar-notification-templates", true, false,
				new PortletDataHandlerBoolean[] {
					new PortletDataHandlerBoolean(
						NAMESPACE, "referenced-content")
				},
				CalendarNotificationTemplate.class.getName()));
		setStagingControls(getExportControls());
	}

	protected void addSkipGuestCalendarResourceCriterion(
			ActionableDynamicQuery actionableDynamicQuery,
			PortletDataContext portletDataContext)
		throws PortalException {

		final CalendarResource guestCalendarResource =
			CalendarResourceUtil.fetchGuestCalendarResource(
				portletDataContext.getCompanyId());

		if (guestCalendarResource == null) {
			return;
		}

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property property = PropertyFactoryUtil.forName(
					"calendarResourceId");

				dynamicQuery.add(
					property.ne(guestCalendarResource.getCalendarResourceId()));
			});
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				CalendarPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		_calendarResourceLocalService.deleteCalendarResources(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "calendars")) {
			ActionableDynamicQuery calendarActionableDynamicQuery =
				_calendarLocalService.getExportActionableDynamicQuery(
					portletDataContext);

			addSkipGuestCalendarResourceCriterion(
				calendarActionableDynamicQuery, portletDataContext);

			calendarActionableDynamicQuery.performActions();

			ActionableDynamicQuery calendarResourceActionableDynamicQuery =
				getCalendarResourceActionableDynamicQuery(
					portletDataContext,
					StagedModelType.REFERRER_CLASS_NAME_ID_ALL);

			addSkipGuestCalendarResourceCriterion(
				calendarResourceActionableDynamicQuery, portletDataContext);

			calendarResourceActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "calendar-bookings")) {

			ActionableDynamicQuery calendarBookingActionableDynamicQuery =
				_calendarBookingLocalService.getExportActionableDynamicQuery(
					portletDataContext);

			calendarBookingActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "calendar-notification-templates")) {

			ActionableDynamicQuery
				calendarNotificationTemplateActionableDynamicQuery =
					_calendarNotificationTemplateLocalService.
						getExportActionableDynamicQuery(portletDataContext);

			calendarNotificationTemplateActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		Group scopeGroup = _groupLocalService.fetchGroup(
			portletDataContext.getScopeGroupId());

		String layoutsImportMode = MapUtil.getString(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE);

		if (layoutsImportMode.equals(
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE) &&
			(scopeGroup != null) && scopeGroup.isUser()) {

			return portletPreferences;
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "calendars")) {
			Element calendarsElement =
				portletDataContext.getImportDataGroupElement(Calendar.class);

			List<Element> calendarElements = calendarsElement.elements();

			for (Element calendarElement : calendarElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, calendarElement);
			}

			Element calendarResourcesElement =
				portletDataContext.getImportDataGroupElement(
					CalendarResource.class);

			List<Element> calendarResourceElements =
				calendarResourcesElement.elements();

			for (Element calendarResourceElement : calendarResourceElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, calendarResourceElement);
			}
		}

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "calendar-notification-templates")) {

			Element calendarNotificationTemplatesElement =
				portletDataContext.getImportDataGroupElement(
					CalendarNotificationTemplate.class);

			List<Element> calendarNotificationTemplateElements =
				calendarNotificationTemplatesElement.elements();

			for (Element calendarNotificationTemplateElement :
					calendarNotificationTemplateElements) {

				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, calendarNotificationTemplateElement);
			}
		}

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "calendar-bookings")) {

			Element calendarBookingsElement =
				portletDataContext.getImportDataGroupElement(
					CalendarBooking.class);

			List<Element> calendarBookingElements =
				calendarBookingsElement.elements();

			for (Element calendarBookingElement : calendarBookingElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, calendarBookingElement);
			}
		}

		return portletPreferences;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		if (ExportImportDateUtil.isRangeFromLastPublishDate(
				portletDataContext)) {

			_staging.populateLastPublishDateCounts(
				portletDataContext,
				new StagedModelType[] {
					new StagedModelType(Calendar.class.getName()),
					new StagedModelType(CalendarBooking.class.getName()),
					new StagedModelType(
						CalendarNotificationTemplate.class.getName()),
					new StagedModelType(CalendarResource.class.getName())
				});

			return;
		}

		ActionableDynamicQuery calendarActionableDynamicQuery =
			_calendarLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		addSkipGuestCalendarResourceCriterion(
			calendarActionableDynamicQuery, portletDataContext);

		calendarActionableDynamicQuery.performCount();

		ActionableDynamicQuery calendarBookingActionableDynamicQuery =
			_calendarBookingLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		calendarBookingActionableDynamicQuery.performCount();

		ActionableDynamicQuery
			calendarNotificationTemplateActionableDynamicQuery =
				_calendarNotificationTemplateLocalService.
					getExportActionableDynamicQuery(portletDataContext);

		calendarNotificationTemplateActionableDynamicQuery.performCount();

		ActionableDynamicQuery calendarResourceActionableDynamicQuery =
			getCalendarResourceActionableDynamicQuery(
				portletDataContext,
				_portal.getClassNameId(CalendarResource.class));

		addSkipGuestCalendarResourceCriterion(
			calendarResourceActionableDynamicQuery, portletDataContext);

		calendarResourceActionableDynamicQuery.performCount();
	}

	protected ActionableDynamicQuery getCalendarResourceActionableDynamicQuery(
		PortletDataContext portletDataContext, long referrerClassNameId) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_calendarResourceLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				_portal.getClassNameId(CalendarResource.class),
				referrerClassNameId));

		return exportActionableDynamicQuery;
	}

	@Reference
	private CalendarBookingLocalService _calendarBookingLocalService;

	@Reference
	private CalendarLocalService _calendarLocalService;

	@Reference
	private CalendarNotificationTemplateLocalService
		_calendarNotificationTemplateLocalService;

	@Reference
	private CalendarResourceLocalService _calendarResourceLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private Staging _staging;

}