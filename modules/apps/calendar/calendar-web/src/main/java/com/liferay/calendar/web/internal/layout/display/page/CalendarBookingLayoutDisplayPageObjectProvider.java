/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.web.internal.layout.display.page;

import com.liferay.asset.util.AssetHelper;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.friendly.url.info.item.provider.InfoItemFriendlyURLProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class CalendarBookingLayoutDisplayPageObjectProvider
	implements LayoutDisplayPageObjectProvider<CalendarBooking> {

	public CalendarBookingLayoutDisplayPageObjectProvider(
			AssetHelper assetHelper, CalendarBooking calendarBooking,
			InfoItemFriendlyURLProvider<CalendarBooking>
				infoItemFriendlyURLProvider,
			Language language)
		throws PortalException {

		_assetHelper = assetHelper;
		_calendarBooking = calendarBooking;
		_infoItemFriendlyURLProvider = infoItemFriendlyURLProvider;
		_language = language;
	}

	@Override
	public String getClassName() {
		return CalendarBooking.class.getName();
	}

	@Override
	public long getClassNameId() {
		return PortalUtil.getClassNameId(CalendarBooking.class.getName());
	}

	@Override
	public long getClassPK() {
		return _calendarBooking.getCalendarBookingId();
	}

	@Override
	public long getClassTypeId() {
		return 0;
	}

	@Override
	public String getDescription(Locale locale) {
		return _calendarBooking.getDescription();
	}

	@Override
	public CalendarBooking getDisplayObject() {
		return _calendarBooking;
	}

	@Override
	public long getGroupId() {
		return _calendarBooking.getGroupId();
	}

	@Override
	public String getKeywords(Locale locale) {
		return _assetHelper.getAssetKeywords(
			CalendarBooking.class.getName(),
			_calendarBooking.getCalendarBookingId(), locale);
	}

	@Override
	public String getTitle(Locale locale) {
		return _calendarBooking.getTitle(locale);
	}

	@Override
	public String getURLTitle(Locale locale) {
		return _infoItemFriendlyURLProvider.getFriendlyURL(
			_calendarBooking, _language.getLanguageId(locale));
	}

	private final AssetHelper _assetHelper;
	private final CalendarBooking _calendarBooking;
	private final InfoItemFriendlyURLProvider<CalendarBooking>
		_infoItemFriendlyURLProvider;
	private final Language _language;

}