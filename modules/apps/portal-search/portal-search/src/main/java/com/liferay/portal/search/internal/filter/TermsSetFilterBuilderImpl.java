/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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