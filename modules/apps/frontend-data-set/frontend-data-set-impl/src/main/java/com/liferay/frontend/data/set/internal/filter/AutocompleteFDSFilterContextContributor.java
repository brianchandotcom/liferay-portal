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

package com.liferay.frontend.data.set.internal.filter;

import com.liferay.frontend.data.set.filter.BaseAutocompleteFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilterContextContributor;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	property = "frontend.data.set.filter.type=autocomplete",
	service = FDSFilterContextContributor.class
)
public class AutocompleteFDSFilterContextContributor
	implements FDSFilterContextContributor {

	@Override
	public Map<String, Object> getFDSFilterContext(
		FDSFilter fdsFilter, Locale locale) {

		if (fdsFilter instanceof BaseAutocompleteFDSFilter) {
			return _serialize((BaseAutocompleteFDSFilter)fdsFilter, locale);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseAutocompleteFDSFilter baseAutocompleteFDSFilter, Locale locale) {

		return HashMapBuilder.<String, Object>put(
			"apiURL", baseAutocompleteFDSFilter.getAPIURL()
		).put(
			"inputPlaceholder",
			LanguageUtil.get(locale, baseAutocompleteFDSFilter.getPlaceholder())
		).put(
			"itemKey", baseAutocompleteFDSFilter.getItemKey()
		).put(
			"itemLabel", baseAutocompleteFDSFilter.getItemLabel()
		).put(
			"selectionType",
			() -> {
				if (baseAutocompleteFDSFilter.isMultipleSelection()) {
					return "multiple";
				}

				return "single";
			}
		).build();
	}

}