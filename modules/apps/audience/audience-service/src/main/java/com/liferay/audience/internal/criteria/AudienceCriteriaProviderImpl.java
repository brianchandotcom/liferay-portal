/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.internal.criteria;

import com.liferay.audience.constants.AudienceCriteriaKeys;
import com.liferay.audience.criteria.AudienceCriteria;
import com.liferay.audience.criteria.AudienceCriteriaProvider;
import com.liferay.audience.criteria.AudienceCriteriaType;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = AudienceCriteriaProvider.class)
public class AudienceCriteriaProviderImpl implements AudienceCriteriaProvider {

	@Override
	public List<AudienceCriteriaType> getAudienceCriteriaTypes(
		long companyId, Locale locale) {

		return ListUtil.fromArray(
			_getBrowserAttributesAudienceCriteriaType(locale));
	}

	private AudienceCriteriaType _getBrowserAttributesAudienceCriteriaType(
		Locale locale) {

		return new AudienceCriteriaType(
			Arrays.asList(
				new AudienceCriteria(
					AudienceCriteriaKeys.BROWSER_NAME,
					_language.get(locale, "browser-name"), "desktop",
					AudienceCriteria.Type.STRING),
				new AudienceCriteria(
					AudienceCriteriaKeys.BROWSER_VERSION,
					_language.get(locale, "browser-version"), "desktop",
					AudienceCriteria.Type.STRING),
				new AudienceCriteria(
					AudienceCriteriaKeys.COOKIES,
					_language.get(locale, "cookies"), "password",
					AudienceCriteria.Type.COLLECTION),
				new AudienceCriteria(
					AudienceCriteriaKeys.DEVICE_TYPE,
					_language.get(locale, "device-type"), "desktop",
					AudienceCriteria.Type.STRING),
				new AudienceCriteria(
					AudienceCriteriaKeys.GEOLOCATION,
					_language.get(locale, "geolocation"), "globe",
					AudienceCriteria.Type.STRING),
				new AudienceCriteria(
					AudienceCriteriaKeys.HOSTNAME,
					_language.get(locale, "hostname"), "globe",
					AudienceCriteria.Type.STRING),
				new AudienceCriteria(
					AudienceCriteriaKeys.LANGUAGE,
					_language.get(locale, "language"), "globe",
					_getLanguageOptions(locale), AudienceCriteria.Type.OPTIONS),
				new AudienceCriteria(
					AudienceCriteriaKeys.LOCAL_DATE,
					_language.get(locale, "local-date"), "calendar",
					AudienceCriteria.Type.DATE),
				new AudienceCriteria(
					AudienceCriteriaKeys.LOCAL_HOUR,
					_language.get(locale, "local-hour"), "time",
					_getLocalHourOptions(), AudienceCriteria.Type.NUMBER),
				new AudienceCriteria(
					AudienceCriteriaKeys.PATHNAME,
					_language.get(locale, "pathname"), "link",
					AudienceCriteria.Type.STRING),
				new AudienceCriteria(
					AudienceCriteriaKeys.REFERRER,
					_language.get(locale, "referrer-url"), "link",
					AudienceCriteria.Type.STRING),
				new AudienceCriteria(
					AudienceCriteriaKeys.REQUEST_PARAMETERS,
					_language.get(locale, "request-parameters"), "code",
					AudienceCriteria.Type.COLLECTION),
				new AudienceCriteria(
					AudienceCriteriaKeys.TIMEZONE,
					_language.get(locale, "time-zone"), "time",
					AudienceCriteria.Type.STRING),
				new AudienceCriteria(
					AudienceCriteriaKeys.URL, _language.get(locale, "url"),
					"link", AudienceCriteria.Type.STRING),
				new AudienceCriteria(
					AudienceCriteriaKeys.USER_AGENT,
					_language.get(locale, "user-agent"), "desktop",
					AudienceCriteria.Type.STRING)),
			_language.get(locale, "browser-attributes"));
	}

	private List<AudienceCriteria.Option> _getLanguageOptions(Locale locale) {
		return TransformUtil.transform(
			_language.getAvailableLocales(),
			availableLocale -> new AudienceCriteria.Option(
				availableLocale.getDisplayName(locale),
				_language.getBCP47LanguageId(availableLocale)));
	}

	private List<AudienceCriteria.Option> _getLocalHourOptions() {
		List<AudienceCriteria.Option> options = new ArrayList<>(24);

		for (int hour = 0; hour < 24; hour++) {
			options.add(
				new AudienceCriteria.Option(
					String.format("%02d:00", hour), String.valueOf(hour)));
		}

		return options;
	}

	@Reference
	private Language _language;

}