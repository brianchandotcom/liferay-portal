/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.web.internal.info.item.provider;

import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.web.internal.info.item.CalendarBookingInfoItemFields;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = InfoItemFieldValuesProvider.class)
public class CalendarBookingInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<CalendarBooking> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(
		CalendarBooking calendarBooking) {

		try {
			return InfoItemFieldValues.builder(
			).infoFieldValues(
				_getCalendarBookingInfoFieldValues(calendarBooking)
			).infoFieldValues(
				_displayPageInfoItemFieldSetProvider.getInfoFieldValues(
					new InfoItemReference(
						CalendarBooking.class.getName(),
						calendarBooking.getCalendarBookingId()),
					StringPool.BLANK, CalendarBooking.class.getSimpleName(),
					calendarBooking, _getThemeDisplay())
			).infoItemReference(
				new InfoItemReference(
					CalendarBooking.class.getName(),
					calendarBooking.getCalendarBookingId())
			).build();
		}
		catch (Exception exception) {
			throw new RuntimeException("Unexpected exception", exception);
		}
	}

	private List<InfoFieldValue<Object>> _getCalendarBookingInfoFieldValues(
		CalendarBooking calendarBooking) {

		return Arrays.asList(
			new InfoFieldValue<>(
				CalendarBookingInfoItemFields.titleInfoField,
				InfoLocalizedValue.<String>builder(
				).defaultLocale(
					LocaleUtil.fromLanguageId(
						calendarBooking.getDefaultLanguageId())
				).values(
					calendarBooking.getTitleMap()
				).build()),
			new InfoFieldValue<>(
				CalendarBookingInfoItemFields.descriptionInfoField,
				InfoLocalizedValue.<String>builder(
				).defaultLocale(
					LocaleUtil.fromLanguageId(
						calendarBooking.getDefaultLanguageId())
				).values(
					calendarBooking.getDescriptionMap()
				).build()),
			new InfoFieldValue<>(
				CalendarBookingInfoItemFields.locationInfoField,
				calendarBooking.getLocation()),
			new InfoFieldValue<>(
				CalendarBookingInfoItemFields.eventURLInfoField,
				_getCalendarBookingURL(calendarBooking)),
			new InfoFieldValue<>(
				CalendarBookingInfoItemFields.startDateInfoField,
				new Date(calendarBooking.getStartTime())),
			new InfoFieldValue<>(
				CalendarBookingInfoItemFields.endDateInfoField,
				new Date(calendarBooking.getEndTime())),
			new InfoFieldValue<>(
				CalendarBookingInfoItemFields.allDayInfoField,
				calendarBooking.isAllDay()));
	}

	/**
	 * See {@link
	 * com.liferay.calendar.internal.notification.NotificationTemplateContextFactory#_getCalendarBookingURL(
	 * User, long)}
	 */
	private String _getCalendarBookingURL(CalendarBooking calendarBooking) {
		try {
			Company company = _companyLocalService.getCompany(
				calendarBooking.getCompanyId());

			String portalURL = company.getPortalURL(
				calendarBooking.getGroupId());

			Group group = _groupLocalService.getGroup(
				calendarBooking.getGroupId());

			String layoutActualURL = _portal.getLayoutActualURL(
				_layoutLocalService.fetchLayout(group.getDefaultPublicPlid()));

			String url = portalURL + layoutActualURL;

			String namespace = _portal.getPortletNamespace(
				CalendarPortletKeys.CALENDAR);

			url = HttpComponentsUtil.addParameter(
				url, namespace + "mvcPath", "/view_calendar_booking.jsp");

			url = HttpComponentsUtil.addParameter(
				url, "p_p_id", CalendarPortletKeys.CALENDAR);
			url = HttpComponentsUtil.addParameter(url, "p_p_lifecycle", "0");
			url = HttpComponentsUtil.addParameter(
				url, "p_p_state", WindowState.MAXIMIZED.toString());
			url = HttpComponentsUtil.addParameter(
				url, namespace + "calendarBookingId",
				calendarBooking.getCalendarBookingId());

			return url;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return StringPool.BLANK;
		}
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getThemeDisplay();
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CalendarBookingInfoItemFieldValuesProvider.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DisplayPageInfoItemFieldSetProvider
		_displayPageInfoItemFieldSetProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}