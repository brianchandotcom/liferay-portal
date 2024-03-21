/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.web.internal.info.item.provider;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.web.internal.info.item.CalendarBookingInfoItemFields;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.bundle.ModelResourceLocalizedValue;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.petra.string.StringPool;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = InfoItemFormProvider.class)
public class CalendarBookingInfoItemFormProvider
	implements InfoItemFormProvider<CalendarBooking> {

	@Override
	public InfoForm getInfoForm() {
		return _getInfoForm();
	}

	@Override
	public InfoForm getInfoForm(CalendarBooking calendarBooking) {
		return _getInfoForm();
	}

	@Override
	public InfoForm getInfoForm(String formVariationKey, long groupId) {
		return _getInfoForm();
	}

	private InfoFieldSet _getBasicInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			CalendarBookingInfoItemFields.titleInfoField
		).infoFieldSetEntry(
			CalendarBookingInfoItemFields.descriptionInfoField
		).infoFieldSetEntry(
			CalendarBookingInfoItemFields.locationInfoField
		).infoFieldSetEntry(
			CalendarBookingInfoItemFields.eventURLInfoField
		).infoFieldSetEntry(
			CalendarBookingInfoItemFields.startDateInfoField
		).infoFieldSetEntry(
			CalendarBookingInfoItemFields.endDateInfoField
		).infoFieldSetEntry(
			CalendarBookingInfoItemFields.allDayInfoField
		).infoFieldSetEntry(
			CalendarBookingInfoItemFields.calendarNameInfoField
		).infoFieldSetEntry(
			CalendarBookingInfoItemFields.invitationsInfoField
		).infoFieldSetEntry(
			CalendarBookingInfoItemFields.repetitionsInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "basic-information")
		).name(
			"basic-information"
		).build();
	}

	private InfoForm _getInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).infoFieldSetEntry(
			_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
				CalendarBooking.class.getName(), StringPool.BLANK,
				CalendarBooking.class.getSimpleName(), 0)
		).labelInfoLocalizedValue(
			new ModelResourceLocalizedValue(CalendarBooking.class.getName())
		).name(
			CalendarBooking.class.getName()
		).build();
	}

	@Reference
	private DisplayPageInfoItemFieldSetProvider
		_displayPageInfoItemFieldSetProvider;

}