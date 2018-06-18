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

package com.liferay.commerce.data.integration.apio.internal.util;

import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.search.CPOptionIndexer;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
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
@Component(immediate = true, service = OptionHelper.class)
public class OptionHelper {

	public SearchContext buildSearchContext(
		String entryClassPK, String keywords, int start, int end, Sort sort,
		ServiceContext serviceContext) {

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes = new HashMap<>();

		if (Validator.isNotNull(entryClassPK)) {
			attributes.put(Field.ENTRY_CLASS_PK, entryClassPK);
		}

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		attributes.put(Field.STATUS, WorkflowConstants.STATUS_APPROVED);

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("keywords", keywords);

		attributes.put("params", params);

		searchContext.setAttributes(attributes);
		searchContext.setCompanyId(serviceContext.getCompanyId());
		searchContext.setEnd(end);
		searchContext.setStart(start);

		long groupId = serviceContext.getScopeGroupId();

		if (groupId > 0) {
			searchContext.setGroupIds(new long[] {groupId});
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.addSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.NAME,
			Field.SCOPE_GROUP_ID, Field.UID, CPOptionIndexer.FIELD_KEY,
			CPOptionIndexer.FIELD_OPTION_VALUE_NAME,
			CPOptionIndexer.FIELD_DDM_FORM_FIELD_TYPE_NAME);

		queryConfig.setHighlightEnabled(false);
		queryConfig.setLocale(serviceContext.getLocale());
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		return searchContext;
	}

	public CPOption createCPOption(
			Long webSiteId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String fieldType, String key)
		throws PortalException {

		ServiceContext serviceContext = _getServiceContext(webSiteId);

		return _cpOptionService.addCPOption(
			nameMap, descriptionMap, fieldType, false, false, false, key,
			serviceContext);
	}

	public CPOption updateCPOption(
			Long cpOptionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String fieldType, String key)
		throws PortalException {

		CPOption cpOption = _cpOptionService.getCPOption(cpOptionId);

		cpOption.setDDMFormFieldTypeName(fieldType);
		cpOption.setDescriptionMap(descriptionMap);
		cpOption.setKey(key);
		cpOption.setNameMap(nameMap);

		ServiceContext serviceContext = _getServiceContext(
			cpOption.getGroupId());

		return _cpOptionService.updateCPOption(
			cpOptionId, cpOption.getNameMap(), cpOption.getDescriptionMap(),
			cpOption.getDDMFormFieldTypeName(), cpOption.getFacetable(),
			cpOption.getRequired(), cpOption.getSkuContributor(),
			cpOption.getKey(), serviceContext);
	}

	private ServiceContext _getServiceContext(Long groupId)
		throws PortalException {

		User user = _userLocalService.getUserById(
			PrincipalThreadLocal.getUserId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setTimeZone(user.getTimeZone());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPOptionService _cpOptionService;

	@Reference
	private ProductIndexerHelper _productIndexerHelper;

	@Reference
	private UserLocalService _userLocalService;

}