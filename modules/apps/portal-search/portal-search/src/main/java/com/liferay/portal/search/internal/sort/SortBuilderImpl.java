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

package com.liferay.portal.search.internal.sort;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortBuilder;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;

import java.util.Locale;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class SortBuilderImpl implements SortBuilder {

	public SortBuilderImpl(Sorts sorts) {
		_sorts = sorts;
	}

	@Override
	public Sort build() {
		if (!Validator.isBlank(_field)) {
			return _sorts.field(getSortableField(), _sortOrder);
		}

		throw new UnsupportedOperationException();
	}

	@Override
	public SortBuilder field(String field) {
		_field = field;

		return this;
	}

	@Override
	public SortBuilder locale(Locale locale) {
		_locale = locale;

		return this;
	}

	@Override
	public SortBuilder sortOrder(SortOrder sortOrder) {
		_sortOrder = sortOrder;

		return this;
	}

	protected Localization getLocalization() {

		// See LPS-72507 and LPS-76500

		if (_localization != null) {
			return _localization;
		}

		return LocalizationUtil.getLocalization();
	}

	protected String getLocalizedName(String name, Locale locale) {
		Localization localization = getLocalization();

		return localization.getLocalizedName(
			name, LocaleUtil.toLanguageId(locale));
	}

	protected String getSortableField() {
		if ((_locale != null) && _field.equals(Field.TITLE)) {
			return "localized_".concat(
				getLocalizedName(_field, _locale)
			).concat(
				"_sortable"
			);
		}

		return _field;
	}

	private String _field;
	private Locale _locale;
	private Localization _localization;
	private SortOrder _sortOrder;
	private final Sorts _sorts;

}