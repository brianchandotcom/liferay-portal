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

package com.liferay.portal.search.internal.filter;

import com.liferay.portal.search.filter.TermsSetFilter;
import com.liferay.portal.search.filter.TermsSetFilterBuilder;

import java.util.List;

/**
 * @author André de Oliveira
 */
public class TermsSetFilterBuilderImpl implements TermsSetFilterBuilder {

	@Override
	public TermsSetFilter build() {
		TermsSetFilterImpl termsSetFilterImpl = new TermsSetFilterImpl(
			_fieldName, _values);

		termsSetFilterImpl.setMinimumShouldMatchField(_minimumShouldMatchField);

		return termsSetFilterImpl;
	}

	@Override
	public void setFieldName(String fieldName) {
		_fieldName = fieldName;
	}

	@Override
	public void setMinimumShouldMatchField(String minimumShouldMatchField) {
		_minimumShouldMatchField = minimumShouldMatchField;
	}

	@Override
	public void setValues(List<String> values) {
		_values = values;
	}

	private String _fieldName;
	private String _minimumShouldMatchField;
	private List<String> _values;

}