/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ip.geocoder.internal.segments.field.customizer;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.segments.field.Field;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizer;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Allen Ziegenfus
 */
@Component(
	property = {
		"segments.field.customizer.entity.name=Context",
		"segments.field.customizer.key=" + IPGeocoderCountrySegmentsFieldCustomizer.KEY,
		"segments.field.customizer.priority:Integer=50"
	},
	service = SegmentsFieldCustomizer.class
)
public class IPGeocoderCountrySegmentsFieldCustomizer
	implements SegmentsFieldCustomizer {

	public static final String KEY = "ipGeocoderCountry";

	@Override
	public List<String> getFieldNames() {
		return _fieldNames;
	}

	@Override
	public String getKey() {
		return IPGeocoderCountrySegmentsFieldCustomizer.KEY;
	}

	public String getLabel(String fieldName, Locale locale) {
		return _language.get(locale, "ip-geocoder-country");
	}

	@Override
	public List<Field.Option> getOptions(Locale locale) {
		return TransformUtil.transform(
			_countryService.getCompanyCountries(
				CompanyThreadLocal.getCompanyId()),
			country -> new Field.Option(
				country.getName(locale), country.getA2()));
	}

	private static final List<String> _fieldNames = ListUtil.fromArray(KEY);

	@Reference
	private CountryService _countryService;

	@Reference
	private Language _language;

}