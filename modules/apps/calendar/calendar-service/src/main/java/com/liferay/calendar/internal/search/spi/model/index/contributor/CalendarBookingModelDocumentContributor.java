/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.search.spi.model.index.contributor;

import com.liferay.calendar.constants.CalendarActionKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.trash.TrashHelper;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "indexer.class.name=com.liferay.calendar.model.CalendarBooking",
	service = ModelDocumentContributor.class
)
public class CalendarBookingModelDocumentContributor
	implements ModelDocumentContributor<CalendarBooking> {

	@Override
	public void contribute(Document document, CalendarBooking calendarBooking) {
		document.addKeyword(
			Field.CLASS_NAME_ID,
			classNameLocalService.getClassNameId(Calendar.class));
		document.addKeyword(Field.CLASS_PK, calendarBooking.getCalendarId());

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		String[] descriptionLanguageIds = _getLanguageIds(
			defaultLanguageId, calendarBooking.getDescription());

		for (String descriptionLanguageId : descriptionLanguageIds) {
			String description = calendarBooking.getDescription(
				descriptionLanguageId);

			document.addText(
				_localization.getLocalizedName(
					Field.DESCRIPTION, descriptionLanguageId),
				description);
		}

		document.addKeyword(Field.RELATED_ENTRY, true);

		String[] titleLanguageIds = _getLanguageIds(
			defaultLanguageId, calendarBooking.getTitle());

		for (String titleLanguageId : titleLanguageIds) {
			String title = calendarBooking.getTitle(titleLanguageId);

			document.addText(
				_localization.getLocalizedName(Field.TITLE, titleLanguageId),
				title);
		}

		document.addKeyword(
			Field.VIEW_ACTION_ID, CalendarActionKeys.VIEW_BOOKING_DETAILS);

		String calendarBookingId = String.valueOf(
			calendarBooking.getCalendarBookingId());

		if (calendarBooking.isInTrash()) {
			calendarBookingId = trashHelper.getOriginalTitle(calendarBookingId);
		}

		document.addKeyword("calendarBookingId", calendarBookingId);

		document.addText("defaultLanguageId", defaultLanguageId);
		document.addNumber("endTime", calendarBooking.getEndTime());
		document.addText("location", calendarBooking.getLocation());
		document.addNumber("startTime", calendarBooking.getStartTime());
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	protected TrashHelper trashHelper;

	private String[] _getLanguageIds(String defaultLanguageId, String content) {
		String[] languageIds = _localization.getAvailableLanguageIds(content);

		if (languageIds.length == 0) {
			languageIds = new String[] {defaultLanguageId};
		}

		return languageIds;
	}

	@Reference
	private Localization _localization;

}