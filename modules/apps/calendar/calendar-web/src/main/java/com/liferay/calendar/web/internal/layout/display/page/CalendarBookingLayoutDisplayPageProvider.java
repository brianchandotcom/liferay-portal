/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.web.internal.layout.display.page;

import com.liferay.asset.util.AssetHelper;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.friendly.url.info.item.provider.InfoItemFriendlyURLProvider;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = LayoutDisplayPageProvider.class)
public class CalendarBookingLayoutDisplayPageProvider
	implements LayoutDisplayPageProvider<CalendarBooking> {

	@Override
	public String getClassName() {
		return CalendarBooking.class.getName();
	}

	@Override
	public LayoutDisplayPageObjectProvider<CalendarBooking>
		getLayoutDisplayPageObjectProvider(
			InfoItemReference infoItemReference) {

		try {
			InfoItemIdentifier infoItemIdentifier =
				infoItemReference.getInfoItemIdentifier();

			if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
				return null;
			}

			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)
					infoItemReference.getInfoItemIdentifier();

			CalendarBooking calendarBooking =
				_calendarBookingLocalService.fetchCalendarBooking(
					classPKInfoItemIdentifier.getClassPK());

			if ((calendarBooking == null) || calendarBooking.isDraft() ||
				calendarBooking.isInTrash()) {

				return null;
			}

			return new CalendarBookingLayoutDisplayPageObjectProvider(
				_assetHelper, calendarBooking, _infoItemFriendlyURLProvider,
				_language);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public LayoutDisplayPageObjectProvider<CalendarBooking>
		getLayoutDisplayPageObjectProvider(long groupId, String urlTitle) {

		try {
			CalendarBooking calendarBooking =
				_calendarBookingLocalService.fetchCalendarBooking(
					GetterUtil.getLong(
						StringUtil.trimLeading(urlTitle, CharPool.SLASH)));

			if ((calendarBooking == null) || calendarBooking.isInTrash()) {
				return null;
			}

			return new CalendarBookingLayoutDisplayPageObjectProvider(
				_assetHelper, calendarBooking, _infoItemFriendlyURLProvider,
				_language);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public String getURLSeparator() {
		return FriendlyURLResolverConstants.URL_SEPARATOR_CALENDAR_BOOKING;
	}

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private CalendarBookingLocalService _calendarBookingLocalService;

	@Reference(
		target = "(item.class.name=com.liferay.calendar.model.CalendarBooking)"
	)
	private InfoItemFriendlyURLProvider<CalendarBooking>
		_infoItemFriendlyURLProvider;

	@Reference
	private Language _language;

}