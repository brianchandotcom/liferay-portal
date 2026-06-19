/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audiences.internal.criteria;

import com.liferay.audiences.constants.AudiencesCriteriaKeys;
import com.liferay.audiences.criteria.AudiencesCriteria;
import com.liferay.audiences.criteria.AudiencesCriteriaProvider;
import com.liferay.audiences.criteria.AudiencesCriteriaType;
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
@Component(service = AudiencesCriteriaProvider.class)
public class AudiencesCriteriaProviderImpl
	implements AudiencesCriteriaProvider {

	@Override
	public List<AudiencesCriteriaType> getAudiencesCriteriaTypes(
		long companyId, Locale locale) {

		List<AudiencesCriteriaType> audiencesCriteriaTypes = new ArrayList<>();

		audiencesCriteriaTypes.add(
			_getBrowserAttributesAudiencesCriteriaType(locale));

		AudiencesCriteriaType customAudiencesCriteriaType =
			_getCustomAudiencesCriteriaType(companyId, locale);

		if (customAudiencesCriteriaType != null) {
			audiencesCriteriaTypes.add(customAudiencesCriteriaType);
		}

		return audiencesCriteriaTypes;
	}

	private AudiencesCriteriaType _getBrowserAttributesAudiencesCriteriaType(
		Locale locale) {

		return new AudiencesCriteriaType(
			Arrays.asList(
				new AudiencesCriteria(
					"desktop", AudiencesCriteriaKeys.BROWSER_NAME,
					_language.get(locale, "browser-name"),
					AudiencesCriteria.Type.STRING),
				new AudiencesCriteria(
					"desktop", AudiencesCriteriaKeys.BROWSER_VERSION,
					_language.get(locale, "browser-version"),
					AudiencesCriteria.Type.STRING),
				new AudiencesCriteria(
					"password", AudiencesCriteriaKeys.COOKIES,
					_language.get(locale, "cookies"),
					AudiencesCriteria.Type.COLLECTION),
				new AudiencesCriteria(
					"desktop", AudiencesCriteriaKeys.DEVICE_TYPE,
					_language.get(locale, "device-type"),
					AudiencesCriteria.Type.STRING),
				new AudiencesCriteria(
					"globe", AudiencesCriteriaKeys.GEOLOCATION,
					_language.get(locale, "geolocation"),
					AudiencesCriteria.Type.STRING),
				new AudiencesCriteria(
					"globe", AudiencesCriteriaKeys.HOSTNAME,
					_language.get(locale, "hostname"),
					AudiencesCriteria.Type.STRING),
				new AudiencesCriteria(
					"globe", AudiencesCriteriaKeys.LANGUAGE,
					_language.get(locale, "language"),
					_getLanguageOptions(locale),
					AudiencesCriteria.Type.OPTIONS),
				new AudiencesCriteria(
					"calendar", AudiencesCriteriaKeys.LOCAL_DATE,
					_language.get(locale, "local-date"),
					AudiencesCriteria.Type.DATE),
				new AudiencesCriteria(
					"time", AudiencesCriteriaKeys.LOCAL_HOUR,
					_language.get(locale, "local-hour"), _getLocalHourOptions(),
					AudiencesCriteria.Type.NUMBER),
				new AudiencesCriteria(
					"link", AudiencesCriteriaKeys.PATHNAME,
					_language.get(locale, "pathname"),
					AudiencesCriteria.Type.STRING),
				new AudiencesCriteria(
					"link", AudiencesCriteriaKeys.REFERRER,
					_language.get(locale, "referrer-url"),
					AudiencesCriteria.Type.STRING),
				new AudiencesCriteria(
					"code", AudiencesCriteriaKeys.REQUEST_PARAMETERS,
					_language.get(locale, "request-parameters"),
					AudiencesCriteria.Type.COLLECTION),
				new AudiencesCriteria(
					"time", AudiencesCriteriaKeys.TIMEZONE,
					_language.get(locale, "time-zone"),
					AudiencesCriteria.Type.STRING),
				new AudiencesCriteria(
					"link", AudiencesCriteriaKeys.URL,
					_language.get(locale, "url"),
					AudiencesCriteria.Type.STRING),
				new AudiencesCriteria(
					"desktop", AudiencesCriteriaKeys.USER_AGENT,
					_language.get(locale, "user-agent"),
					AudiencesCriteria.Type.STRING)),
			_language.get(locale, "browser-attributes"));
	}

	private AudiencesCriteriaType _getCustomAudiencesCriteriaType(
		long companyId, Locale locale) {

		try {
			List<CET> cets = _cetManager.getCETs(
				companyId, null,
				ClientExtensionEntryConstants.TYPE_AUDIENCES_CUSTOM_ATTRIBUTES,
				Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS), null);

			if (cets.isEmpty()) {
				return null;
			}

			return new AudiencesCriteriaType(
				TransformUtil.transform(
					cets,
					cet -> new AudiencesCriteria(
						"cog", "custom:" + cet.getExternalReferenceCode(),
						cet.getName(locale), AudiencesCriteria.Type.STRING)),
				_language.get(locale, "custom"));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private List<AudiencesCriteria.Option> _getLanguageOptions(Locale locale) {
		return TransformUtil.transform(
			_language.getAvailableLocales(),
			availableLocale -> new AudiencesCriteria.Option(
				availableLocale.getDisplayName(locale),
				_language.getBCP47LanguageId(availableLocale)));
	}

	private List<AudiencesCriteria.Option> _getLocalHourOptions() {
		List<AudiencesCriteria.Option> options = new ArrayList<>(24);

		for (int hour = 0; hour < 24; hour++) {
			options.add(
				new AudiencesCriteria.Option(
					String.format("%02d:00", hour), String.valueOf(hour)));
		}

		return options;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AudiencesCriteriaProviderImpl.class);

	@Reference
	private CETManager _cetManager;

	@Reference
	private Language _language;

}