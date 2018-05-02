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

package com.liferay.commerce.headless.product.apio.internal.util;

import com.liferay.commerce.product.search.CPDefinitionOptionValueRelIndexer;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Zoltán Takács
 */
public class ProductOptionValueHelper {

	/**
	 * Builds the SearchContext for finding {@link CPDefinitionOptionValueRel}'s
	 * {@link com.liferay.portal.kernel.search.Document}
	 *
	 * @param entryClassPK
	 * @param cpDefinitionOptionRelId
	 * @param keywords
	 * @param start
	 * @param end
	 * @param sort
	 * @param serviceContext
	 * @return
	 */
	public static SearchContext buildSearchContext(
		String entryClassPK, String cpDefinitionOptionRelId, String keywords,
		int start, int end, Sort sort, ServiceContext serviceContext) {

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes = new HashMap<>();

		if (Validator.isNotNull(cpDefinitionOptionRelId)) {
			attributes.put(
				CPDefinitionOptionValueRelIndexer.
					FIELD_CP_DEFINITION_OPTION_REL_ID,
				cpDefinitionOptionRelId);
		}

		if (Validator.isNotNull(entryClassPK)) {
			attributes.put(Field.ENTRY_CLASS_PK, entryClassPK);
		}

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("keywords", keywords);

		attributes.put("params", params);

		searchContext.setAttributes(attributes);

		searchContext.setStart(start);
		searchContext.setEnd(end);
		searchContext.setCompanyId(serviceContext.getCompanyId());

		long groupId = serviceContext.getScopeGroupId();

		if (groupId != 0) {
			searchContext.setGroupIds(new long[] {groupId});
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.addSelectedFieldNames(
			CPDefinitionOptionValueRelIndexer.FIELD_CP_DEFINITION_OPTION_REL_ID,
			Field.CREATE_DATE, Field.ENTRY_CLASS_PK, Field.DESCRIPTION,
			Field.MODIFIED_DATE, Field.TITLE);

		queryConfig.setLocale(serviceContext.getLocale());
		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		return searchContext;
	}

}