/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.content.targeting.upgrade.internal.upgrade.v1_0_0.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;

import java.text.DateFormat;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "rule.converter.key=LastLoginDateRule",
	service = RuleConverter.class
)
public class LastLoginDateRuleConverter implements RuleConverter {

	@Override
	public void convert(
		long companyId, Criteria criteria, String typeSettings) {

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(typeSettings);

			String startDateTimeZoneId = jsonObject.getString(
				"startDateTimeZoneId");

			DateFormat startDateFormat =
				DateFormatFactoryUtil.getSimpleDateFormat(
					"yyyy-MM-dd HH:mm", LocaleUtil.ENGLISH,
					TimeZoneUtil.getTimeZone(startDateTimeZoneId));

			Date startDate = startDateFormat.parse(
				jsonObject.getString("startDate"));

			ZonedDateTime startZonedDateTime = ZonedDateTime.ofInstant(
				startDate.toInstant(), ZoneOffset.UTC);

			String type = jsonObject.getString("type");

			String filterString = null;

			if (type.equals("after")) {
				filterString = StringBundler.concat(
					"(lastSignInDateTime gt ",
					startZonedDateTime.format(
						DateTimeFormatter.ISO_OFFSET_DATE_TIME),
					")");
			}
			else if (type.equals("before")) {
				filterString = StringBundler.concat(
					"(lastSignInDateTime lt ",
					startZonedDateTime.format(
						DateTimeFormatter.ISO_OFFSET_DATE_TIME),
					")");
			}
			else {
				ZonedDateTime endZonedDateTime = ZonedDateTime.now();

				if (Validator.isNotNull(jsonObject.getString("endDate"))) {
					String endDateTimeZoneId = jsonObject.getString(
						"endDateTimeZoneId");

					DateFormat endDateFormat =
						DateFormatFactoryUtil.getSimpleDateFormat(
							"yyyy-MM-dd HH:mm", LocaleUtil.ENGLISH,
							TimeZoneUtil.getTimeZone(endDateTimeZoneId));

					Date endDate = endDateFormat.parse(
						jsonObject.getString("endDate"));

					endZonedDateTime = ZonedDateTime.ofInstant(
						endDate.toInstant(), ZoneOffset.UTC);
				}

				filterString = StringBundler.concat(
					"(lastSignInDateTime gt ",
					startZonedDateTime.format(
						DateTimeFormatter.ISO_OFFSET_DATE_TIME),
					") and (lastSignInDateTime lt ",
					endZonedDateTime.format(
						DateTimeFormatter.ISO_OFFSET_DATE_TIME),
					")");
			}

			_contextSegmentsCriteriaContributor.contribute(
				criteria, filterString, Criteria.Conjunction.AND);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to convert last login date rule with type settings " +
					typeSettings,
				exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LastLoginDateRuleConverter.class);

	@Reference(target = "(segments.criteria.contributor.key=context)")
	private SegmentsCriteriaContributor _contextSegmentsCriteriaContributor;

	@Reference
	private JSONFactory _jsonFactory;

}