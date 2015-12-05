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

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.workflow.CalendarBookingWorkflowConstants;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.trash.util.TrashUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Victor Brandizzi
 * @author Eduardo Lundgren
 */
@Component(immediate = true, service = Indexer.class)
public class CalendarBookingIndexer extends BaseIndexer<CalendarBooking> {

	public static final String CLASS_NAME = CalendarBooking.class.getName();

	public CalendarBookingIndexer() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID);
		setDefaultSelectedLocalizedFieldNames(Field.DESCRIPTION, Field.TITLE);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	protected void doDelete(CalendarBooking calendarBooking) throws Exception {
		deleteDocument(
			calendarBooking.getCompanyId(),
			calendarBooking.getCalendarBookingId());
	}

	@Override
	protected Document doGetDocument(CalendarBooking calendarBooking)
		throws Exception {

		Document document = getBaseModelDocument(CLASS_NAME, calendarBooking);

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		String descriptionDefaultLanguageId =
			LocalizationUtil.getDefaultLanguageId(
				calendarBooking.getDescription());

		String[] descriptionLanguageIds = getLanguageIds(
			defaultLanguageId, calendarBooking.getDescription());

		for (String descriptionLanguageId : descriptionLanguageIds) {
			String description = calendarBooking.getDescription(
				descriptionLanguageId);

			if (descriptionLanguageId.equals(descriptionDefaultLanguageId)) {
				document.addText(Field.DESCRIPTION, description);
			}

			document.addText(
				Field.DESCRIPTION.concat(StringPool.UNDERLINE).concat(
					descriptionLanguageId),
				description);
		}

		String titleDefaultLanguageId = LocalizationUtil.getDefaultLanguageId(
			calendarBooking.getTitle());

		String[] titleLanguageIds = getLanguageIds(
			defaultLanguageId, calendarBooking.getTitle());

		for (String titleLanguageId : titleLanguageIds) {
			String title = calendarBooking.getTitle(titleLanguageId);

			if (titleLanguageId.equals(titleDefaultLanguageId)) {
				document.addText(Field.TITLE, title);
			}

			document.addText(
				Field.TITLE.concat(StringPool.UNDERLINE).concat(
					titleLanguageId),
				title);
		}

		String calendarBookingId = String.valueOf(
			calendarBooking.getCalendarBookingId());

		if (calendarBooking.isInTrash()) {
			calendarBookingId = TrashUtil.getOriginalTitle(calendarBookingId);
		}

		document.addKeyword("calendarBookingId", calendarBookingId);

		document.addText("defaultLanguageId", defaultLanguageId);
		document.addNumber("endTime", calendarBooking.getEndTime());
		document.addText("location", calendarBooking.getLocation());
		document.addNumber("startTime", calendarBooking.getStartTime());

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, Field.TITLE, Field.DESCRIPTION);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CalendarBooking calendarBooking) throws Exception {
		int status = calendarBooking.getStatus();

		if ((status == CalendarBookingWorkflowConstants.STATUS_APPROVED) ||
			(status == CalendarBookingWorkflowConstants.STATUS_MAYBE)) {

			Document document = getDocument(calendarBooking);

			SearchEngineUtil.updateDocument(
				getSearchEngineId(), calendarBooking.getCompanyId(), document,
				isCommitImmediately());
		}
		else if ((status == CalendarBookingWorkflowConstants.STATUS_DENIED) ||
				 (status == CalendarBookingWorkflowConstants.STATUS_IN_TRASH)) {

			doDelete(calendarBooking);
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CalendarBooking calendarBooking =
			_calendarBookingLocalService.getCalendarBooking(classPK);

		doReindex(calendarBooking);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCalendarBookings(companyId);
	}

	protected String[] getLanguageIds(
		String defaultLanguageId, String content) {

		String[] languageIds = LocalizationUtil.getAvailableLanguageIds(
			content);

		if (languageIds.length == 0) {
			languageIds = new String[] {defaultLanguageId};
		}

		return languageIds;
	}

	protected void reindexCalendarBookings(long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_calendarBookingLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property statusProperty = PropertyFactoryUtil.forName(
						"status");

					int[] statuses = {
						CalendarBookingWorkflowConstants.STATUS_APPROVED,
						CalendarBookingWorkflowConstants.STATUS_MAYBE
					};

					dynamicQuery.add(statusProperty.in(statuses));
				}

			});
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<CalendarBooking>() {

				@Override
				public void performAction(CalendarBooking calendarBooking) {
					try {
						Document document = getDocument(calendarBooking);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index calendar booking " +
									calendarBooking.getCalendarBookingId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	@Reference(unbind = "-")
	protected void setCalendarBookingLocalService(
		CalendarBookingLocalService calendarBookingLocalService) {

		_calendarBookingLocalService = calendarBookingLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CalendarBookingIndexer.class);

	private volatile CalendarBookingLocalService _calendarBookingLocalService;

}