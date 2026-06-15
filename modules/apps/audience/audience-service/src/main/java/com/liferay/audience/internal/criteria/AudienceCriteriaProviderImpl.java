/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.internal.criteria;

import com.liferay.audience.constants.AudienceCriteriaKeys;
import com.liferay.audience.criteria.AudienceCriteria;
import com.liferay.audience.criteria.AudienceCriteriaProvider;
import com.liferay.audience.criteria.AudienceCriteriaType;
import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.vulcan.pagination.Pagination;

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

		List<AudienceCriteriaType> audienceCriteriaTypes = new ArrayList<>();

		audienceCriteriaTypes.add(
			_getBrowserAttributesAudienceCriteriaType(locale));

		AudienceCriteriaType customAudienceCriteriaType =
			_getCustomAudienceCriteriaType(companyId, locale);

		if (customAudienceCriteriaType != null) {
			audienceCriteriaTypes.add(customAudienceCriteriaType);
		}

		return audienceCriteriaTypes;
	}

	private AudienceCriteriaType _getBrowserAttributesAudienceCriteriaType(
		Locale locale) {

		return new AudienceCriteriaType(
			Arrays.asList(
				new AudienceCriteria(
					"desktop", AudienceCriteriaKeys.BROWSER_NAME,
					_language.get(locale, "browser-name"),
					AudienceCriteria.Type.STRING),
				new AudienceCriteria(
					"desktop", AudienceCriteriaKeys.BROWSER_VERSION,
					_language.get(locale, "browser-version"),
					AudienceCriteria.Type.STRING),
				new AudienceCriteria(
					"password", AudienceCriteriaKeys.COOKIES,
					_language.get(locale, "cookies"),
					AudienceCriteria.Type.COLLECTION),
				new AudienceCriteria(
					"desktop", AudienceCriteriaKeys.DEVICE_TYPE,
					_language.get(locale, "device-type"),
					AudienceCriteria.Type.STRING),
				new AudienceCriteria(
					"globe", AudienceCriteriaKeys.GEOLOCATION,
					_language.get(locale, "geolocation"),
					AudienceCriteria.Type.STRING),
				new AudienceCriteria(
					"globe", AudienceCriteriaKeys.HOSTNAME,
					_language.get(locale, "hostname"),
					AudienceCriteria.Type.STRING),
				new AudienceCriteria(
					"globe", AudienceCriteriaKeys.LANGUAGE,
					_language.get(locale, "language"),
					_getLanguageOptions(locale), AudienceCriteria.Type.OPTIONS),
				new AudienceCriteria(
					"calendar", AudienceCriteriaKeys.LOCAL_DATE,
					_language.get(locale, "local-date"),
					AudienceCriteria.Type.DATE),
				new AudienceCriteria(
					"time", AudienceCriteriaKeys.LOCAL_HOUR,
					_language.get(locale, "local-hour"), _getLocalHourOptions(),
					AudienceCriteria.Type.NUMBER),
				new AudienceCriteria(
					"link", AudienceCriteriaKeys.PATHNAME,
					_language.get(locale, "pathname"),
					AudienceCriteria.Type.STRING),
				new AudienceCriteria(
					"link", AudienceCriteriaKeys.REFERRER,
					_language.get(locale, "referrer-url"),
					AudienceCriteria.Type.STRING),
				new AudienceCriteria(
					"code", AudienceCriteriaKeys.REQUEST_PARAMETERS,
					_language.get(locale, "request-parameters"),
					AudienceCriteria.Type.COLLECTION),
				new AudienceCriteria(
					"time", AudienceCriteriaKeys.TIMEZONE,
					_language.get(locale, "time-zone"),
					AudienceCriteria.Type.STRING),
				new AudienceCriteria(
					"link", AudienceCriteriaKeys.URL,
					_language.get(locale, "url"), AudienceCriteria.Type.STRING),
				new AudienceCriteria(
					"desktop", AudienceCriteriaKeys.USER_AGENT,
					_language.get(locale, "user-agent"),
					AudienceCriteria.Type.STRING)),
			_language.get(locale, "browser-attributes"));
	}

	private AudienceCriteriaType _getCustomAudienceCriteriaType(
		long companyId, Locale locale) {

		try {
			List<CET> cets = _cetManager.getCETs(
				companyId, null,
				ClientExtensionEntryConstants.TYPE_AUDIENCES_CUSTOM_ATTRIBUTES,
				Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS), null);

			if (cets.isEmpty()) {
				return null;
			}

			return new AudienceCriteriaType(
				TransformUtil.transform(
					cets,
					cet -> new AudienceCriteria(
						"cog", "custom:" + cet.getExternalReferenceCode(),
						cet.getName(locale), AudienceCriteria.Type.STRING)),
				_language.get(locale, "custom"));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
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

	private static final Log _log = LogFactoryUtil.getLog(
		AudienceCriteriaProviderImpl.class);

	@Reference
	private CETManager _cetManager;

	@Reference
	private Language _language;

}