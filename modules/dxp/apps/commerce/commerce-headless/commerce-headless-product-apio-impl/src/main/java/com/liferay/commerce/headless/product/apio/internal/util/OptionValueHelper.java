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

import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.search.CPOptionValueIndexer;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.commerce.product.service.CPOptionValueService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true, service = OptionValueHelper.class)
public class OptionValueHelper {

	public SearchContext buildSearchContext(
		String entryClassPK, String cpOptionId, String keywords, int start,
		int end, Sort sort, ServiceContext serviceContext) {

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes = new HashMap<>();

		if (Validator.isNotNull(entryClassPK)) {
			attributes.put(Field.ENTRY_CLASS_PK, entryClassPK);
		}

		if (Validator.isNotNull(cpOptionId)) {
			attributes.put(CPOptionValueIndexer.FIELD_CP_OPTION_ID, cpOptionId);
		}

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		attributes.put(Field.STATUS, WorkflowConstants.STATUS_APPROVED);

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
			CPOptionValueIndexer.FIELD_CP_OPTION_ID, Field.CREATE_DATE,
			Field.ENTRY_CLASS_PK, Field.NAME, CPOptionValueIndexer.FIELD_KEY);

		queryConfig.setLocale(serviceContext.getLocale());
		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		return searchContext;
	}

	public CPOptionValue createCPOptionValue(
			Long cpOptionId, Map<Locale, String> titleMap, String key)
		throws PortalException {

		CPOption cpOption = _cpOptionService.getCPOption(cpOptionId);

		ServiceContext serviceContext = _getServiceContext(cpOption);

		return _cpOptionValueService.addCPOptionValue(
			cpOptionId, titleMap, 0, key, serviceContext);
	}

	public CPOptionValue updateCPOptionValue(
			Long cpOptionValueId, Map<Locale, String> nameMap, String key)
		throws PortalException {

		CPOptionValue cpOptionValue = _cpOptionValueService.getCPOptionValue(
			cpOptionValueId);

		cpOptionValue.setNameMap(nameMap);
		cpOptionValue.setKey(key);

		ServiceContext serviceContext = _getServiceContext(
			cpOptionValue.getCPOption());

		return _cpOptionValueService.updateCPOptionValue(
			cpOptionValueId, cpOptionValue.getNameMap(), 0,
			cpOptionValue.getKey(), serviceContext);
	}

	private ServiceContext _getServiceContext(CPOption cpOption)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(cpOption.getCompanyId());
		serviceContext.setUserId(cpOption.getUserId());
		serviceContext.setScopeGroupId(cpOption.getGroupId());

		return serviceContext;
	}

	@Reference
	private CPOptionService _cpOptionService;

	@Reference
	private CPOptionValueService _cpOptionValueService;

}