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

package com.liferay.calendar.internal.search.spi.model.index.contributor;

import com.liferay.calendar.internal.search.CalendarField;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarResourceLocalService;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.calendar.model.Calendar",
	service = ModelDocumentContributor.class
)
public class CalendarModelDocumentContributor
	implements ModelDocumentContributor<Calendar> {

	@Override
	public void contribute(Document document, Calendar calendar) {
		document.addLocalizedKeyword(
			Field.DESCRIPTION, calendar.getDescriptionMap(), true);
		document.addLocalizedKeyword(Field.NAME, calendar.getNameMap(), true);
		document.addKeyword("calendarId", calendar.getCalendarId());

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		document.addText("defaultLanguageId", defaultLanguageId);

		CalendarResource calendarResource =
			calendarResourceLocalService.fetchCalendarResource(
				calendar.getCalendarResourceId());

		if (calendarResource != null) {
			document.addLocalizedKeyword(
				CalendarField.RESOURCE_NAME, calendarResource.getNameMap(),
				true);
		}
	}

	@Reference
	protected CalendarResourceLocalService calendarResourceLocalService;

}