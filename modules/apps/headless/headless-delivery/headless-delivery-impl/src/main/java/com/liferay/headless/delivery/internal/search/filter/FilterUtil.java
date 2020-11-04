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

package com.liferay.headless.delivery.internal.search.filter;

import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.headless.delivery.internal.dynamic.data.mapping.DDMStructureField;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.LocaleUtil;

/**
 * @author Javier de Arcos
 */
public class FilterUtil {

	public static Filter processFilter(DDMIndexer ddmIndexer, Filter filter)
		throws Exception {

		if (ddmIndexer.isLegacyDDMIndexFieldsEnabled() ||
			!(filter instanceof TermFilter)) {

			return filter;
		}

		TermFilter termFilter = (TermFilter)filter;

		String termFilterField = termFilter.getField();

		if (!termFilterField.startsWith(DDMIndexer.DDM_FIELD_PREFIX)) {
			return filter;
		}

		DDMStructureField ddmStructureField = DDMStructureField.from(
			termFilterField);

		return ddmIndexer.createFieldValueQueryFilter(
			ddmStructureField.getDDMStructureFieldName(), termFilter.getValue(),
			LocaleUtil.fromLanguageId(ddmStructureField.getLocale()));
	}

}