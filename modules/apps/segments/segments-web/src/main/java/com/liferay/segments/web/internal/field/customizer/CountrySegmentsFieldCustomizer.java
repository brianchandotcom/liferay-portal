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

package com.liferay.segments.web.internal.field.customizer;

import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.field.Field;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizer;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		"segments.field.customizer.entity.name=Organization",
		"segments.field.customizer.key=" + CountrySegmentsFieldCustomizer.KEY,
		"segments.field.customizer.priority:Integer=50"
	},
	service = SegmentsFieldCustomizer.class
)
public class CountrySegmentsFieldCustomizer
	extends BaseSegmentsFieldCustomizer {

	public static final String KEY = "country";

	@Override
	public List<String> getFieldNames() {
		return _fieldNames;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public List<Field.Option> getOptions(Locale locale) {
		List<Country> countries = _countryService.getCompanyCountries(
			CompanyThreadLocal.getCompanyId());

		Stream<Country> stream = countries.stream();

		return stream.map(
			country -> new Field.Option(
				country.getName(locale),
				StringUtil.toLowerCase(String.valueOf(country.getName())))
		).collect(
			Collectors.toList()
		);
	}

	private static final List<String> _fieldNames = ListUtil.fromArray(
		"country");

	@Reference
	private CountryService _countryService;

}