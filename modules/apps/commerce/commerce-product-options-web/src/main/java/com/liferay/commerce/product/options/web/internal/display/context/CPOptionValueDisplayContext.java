/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.options.web.internal.display.context;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.exception.CPDefinitionDisplayDateException;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

/**
 * @author Andrea Sbarra
 */
public class CPOptionValueDisplayContext {

	public CPOptionValueDisplayContext(
		CPOptionValue cpOptionValue, Portal portal) {

		_cpOptionValue = cpOptionValue;
		_portal = portal;
	}

	public Calendar getCalendar() throws PortalException {
		if (_cpOptionValue == null) {
			return CalendarFactoryUtil.getCalendar();
		}

		String cpDefinitionOptionValueRelKey = _cpOptionValue.getKey();

		String[] splits = cpDefinitionOptionValueRelKey.split(StringPool.DASH);

		Integer month = Integer.valueOf(splits[0]);
		Integer day = Integer.valueOf(splits[1]);
		Integer year = Integer.valueOf(splits[2]);
		Integer hour = Integer.valueOf(splits[3]);
		Integer minute = Integer.valueOf(splits[4]);

		TimeZone timeZone = TimeZoneUtil.getTimeZone(_getTimeZone(splits));

		Date optionValueDate = _portal.getDate(
			month - 1, day, year, hour, minute, timeZone,
			CPDefinitionDisplayDateException.class);

		return CalendarFactoryUtil.getCalendar(
			optionValueDate.getTime(), timeZone);
	}

	public int getDuration() throws PortalException {
		if (_cpOptionValue == null) {
			return 0;
		}

		String cpDefinitionOptionValueRelKey = _cpOptionValue.getKey();

		String[] splits = cpDefinitionOptionValueRelKey.split(StringPool.DASH);

		return Integer.valueOf(splits[5]);
	}

	public String getDurationType() throws PortalException {
		if (_cpOptionValue == null) {
			return CPConstants.HOURS_DURATION_TYPE;
		}

		String cpDefinitionOptionValueRelKey = _cpOptionValue.getKey();

		String[] splits = cpDefinitionOptionValueRelKey.split(StringPool.DASH);

		return splits[6];
	}

	public TimeZone getTimeZone() throws PortalException {
		if (_cpOptionValue == null) {
			return TimeZoneUtil.getDefault();
		}

		String cpDefinitionOptionValueRelKey = _cpOptionValue.getKey();

		String[] splits = cpDefinitionOptionValueRelKey.split(StringPool.DASH);

		return TimeZoneUtil.getTimeZone(_getTimeZone(splits));
	}

	public boolean isCPOptionSelectDateType() throws PortalException {
		CPOption cpOption = _cpOptionValue.getCPOption();

		boolean cpOptionSelectDateType = Objects.equals(
			CPConstants.PRODUCT_OPTION_SELECT_DATE_KEY,
			cpOption.getCommerceOptionTypeKey());

		if (cpOptionSelectDateType) {
			return true;
		}

		return false;
	}

	private String _getTimeZone(String[] splits) {
		if (splits.length <= 8) {
			return splits[7].toUpperCase();
		}

		String timeZone = StringBundler.concat(
			StringUtil.upperCaseFirstLetter(splits[7]),
			StringPool.FORWARD_SLASH,
			StringUtil.upperCaseFirstLetter(splits[8]));

		if ((splits.length > 9) && Validator.isNotNull(splits[9])) {
			return StringBundler.concat(
				timeZone, StringPool.UNDERLINE,
				StringUtil.upperCaseFirstLetter(splits[9]));
		}

		return timeZone;
	}

	private final CPOptionValue _cpOptionValue;
	private final Portal _portal;

}