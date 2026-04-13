/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.dynamic.data.mapping.form.field.type.internal.phone.number;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldTemplateContextContributorUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldValueUtil;
import com.liferay.object.dynamic.data.mapping.form.field.type.constants.ObjectDDMFormFieldTypeConstants;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "ddm.form.field.type.name=" + ObjectDDMFormFieldTypeConstants.PHONE_NUMBER,
	service = DDMFormFieldTemplateContextContributor.class
)
public class PhoneNumberDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		DDMForm ddmForm = ddmFormField.getDDMForm();
		boolean localizedObjectField = GetterUtil.getBoolean(
			ddmFormField.getProperty("localizedObjectField"));

		return HashMapBuilder.<String, Object>put(
			"countries", _getCountries()
		).put(
			"localizedObjectField", localizedObjectField
		).put(
			"value",
			() -> {
				if (localizedObjectField) {
					return DDMFormFieldValueUtil.getValueJSONObject(
						ddmFormFieldRenderingContext);
				}

				return GetterUtil.getString(
					ddmFormFieldRenderingContext.getValue());
			}
		).putAll(
			DDMFormFieldTemplateContextContributorUtil.
				getLocalizationParameters(
					ddmFormField, ddmForm.getDefaultLocale())
		).build();
	}

	@Activate
	protected void activate() {
		Set<String> countryA2List = new HashSet<>();

		for (String languageId : PropsValues.LOCALES) {
			Locale locale = LocaleUtil.fromLanguageId(languageId, false);

			String countryA2 = locale.getCountry();

			if (Validator.isNotNull(countryA2)) {
				countryA2List.add(countryA2);
			}
		}

		_availableCountryA2List = Collections.unmodifiableSet(countryA2List);
	}

	private List<Map<String, String>> _getCountries() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return Collections.emptyList();
		}

		long companyId = serviceContext.getCompanyId();

		if (companyId == 0) {
			return Collections.emptyList();
		}

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		if (locale == null) {
			locale = LocaleUtil.getDefault();
		}

		List<Map<String, String>> countries = new ArrayList<>();

		String languageId = LocaleUtil.toLanguageId(locale);

		for (Country country :
				_countryLocalService.getCompanyCountries(companyId, true)) {

			String a2 = country.getA2();

			if (!_availableCountryA2List.contains(a2)) {
				continue;
			}

			String idd = country.getIdd();

			if (Validator.isNull(idd)) {
				continue;
			}

			countries.add(
				HashMapBuilder.put(
					"a2", a2
				).put(
					"idd", idd
				).put(
					"name", country.getTitle(languageId)
				).build());
		}

		return ListUtil.sort(
			countries, Comparator.comparing(country -> country.get("name")));
	}

	private Set<String> _availableCountryA2List;

	@Reference
	private CountryLocalService _countryLocalService;

}