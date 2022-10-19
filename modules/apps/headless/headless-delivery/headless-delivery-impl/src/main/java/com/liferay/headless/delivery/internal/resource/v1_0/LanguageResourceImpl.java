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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.headless.delivery.dto.v1_0.Language;
import com.liferay.headless.delivery.resource.v1_0.LanguageResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/language.properties",
	scope = ServiceScope.PROTOTYPE, service = LanguageResource.class
)
public class LanguageResourceImpl extends BaseLanguageResourceImpl {

	@Override
	public Page<Language> getAssetLibraryLanguagesPage(Long assetLibraryId)
		throws Exception {

		return getSiteLanguagesPage(assetLibraryId);
	}

	@Override
	public Page<Language> getSiteLanguagesPage(Long siteId) throws Exception {
		Set<Locale> availableLocales = _language.getAvailableLocales(siteId);
		Locale defaultLocale = _getDefaultLocale(siteId);

		return Page.of(
			TransformUtil.transform(
				availableLocales,
				availableLocale -> _toLanguage(
					contextAcceptLanguage.isAcceptAllLanguages(),
					availableLocales, defaultLocale, availableLocale,
					contextAcceptLanguage.getPreferredLocale())));
	}

	private Locale _getDefaultLocale(long groupId) throws Exception {
		Group group = _groupService.getGroup(groupId);

		String defaultLanguageId = _localization.getDefaultLanguageId(
			group.getName());

		if (Validator.isNotNull(defaultLanguageId)) {
			return LocaleUtil.fromLanguageId(defaultLanguageId);
		}

		return LocaleUtil.getSiteDefault();
	}

	private Language _toLanguage(
		boolean acceptAllLanguages, Set<Locale> availableLocales,
		Locale defaultLocale, Locale locale, Locale preferredLocale) {

		return new Language() {
			{
				countryName = locale.getDisplayCountry(preferredLocale);
				id = locale.toLanguageTag();
				markedAsDefault = Objects.equals(defaultLocale, locale);
				name = locale.getDisplayLanguage(preferredLocale);

				setCountryName_i18n(
					() -> {
						if (!acceptAllLanguages) {
							return null;
						}

						Stream<Locale> stream = availableLocales.stream();

						return stream.collect(
							Collectors.toMap(
								LocaleUtil::toBCP47LanguageId,
								availableLocale -> locale.getDisplayCountry(
									availableLocale)));
					});
				setName_i18n(
					() -> {
						if (!acceptAllLanguages) {
							return null;
						}

						Stream<Locale> stream = availableLocales.stream();

						return stream.collect(
							Collectors.toMap(
								LocaleUtil::toBCP47LanguageId,
								availableLocale -> locale.getDisplayLanguage(
									availableLocale)));
					});
			}
		};
	}

	@Reference
	private GroupService _groupService;

	@Reference
	private com.liferay.portal.kernel.language.Language _language;

	@Reference
	private Localization _localization;

}