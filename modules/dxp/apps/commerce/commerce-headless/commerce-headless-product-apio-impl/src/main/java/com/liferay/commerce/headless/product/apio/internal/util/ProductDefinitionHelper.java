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

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltán Takács
 */
@Component(immediate = true, service = ProductDefinitionHelper.class)
public class ProductDefinitionHelper {

	/**
	 * Builds the SearchContext for finding {@link CPDefinition}'s {@link
	 * com.liferay.portal.kernel.search.Document}
	 *
	 * @param  keywords
	 * @param  start
	 * @param  end
	 * @param  sort
	 * @param  serviceContext
	 * @return SearchContext
	 */
	public SearchContext buildSearchContext(
		String keywords, int start, int end, Sort sort,
		ServiceContext serviceContext) {

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.ENTRY_CLASS_PK, keywords);
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

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.addSelectedFieldNames(
			Field.CREATE_DATE, Field.ENTRY_CLASS_PK, Field.DESCRIPTION,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.TITLE, Field.USER_ID,
			"skus");

		queryConfig.setLocale(serviceContext.getLocale());
		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		return searchContext;
	}

	public CPDefinition createCPDefinition(
			long groupId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String productTypeName,
			long[] assetCategoryIds)
		throws PortalException {

		ServiceContext serviceContext = getServiceContext(
			groupId, assetCategoryIds);

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		displayCalendar.add(Calendar.YEAR, -1);

		int displayDateMonth = displayCalendar.get(Calendar.MONTH);
		int displayDateDay = displayCalendar.get(Calendar.DAY_OF_MONTH);
		int displayDateYear = displayCalendar.get(Calendar.YEAR);
		int displayDateHour = displayCalendar.get(Calendar.HOUR);
		int displayDateMinute = displayCalendar.get(Calendar.MINUTE);
		int displayDateAmPm = displayCalendar.get(Calendar.AM_PM);

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		expirationCalendar.add(Calendar.MONTH, 1);

		int expirationDateMonth = expirationCalendar.get(Calendar.MONTH);
		int expirationDateDay = expirationCalendar.get(Calendar.DAY_OF_MONTH);
		int expirationDateYear = expirationCalendar.get(Calendar.YEAR);
		int expirationDateHour = expirationCalendar.get(Calendar.HOUR);
		int expirationDateMinute = expirationCalendar.get(Calendar.MINUTE);
		int expirationDateAmPm = expirationCalendar.get(Calendar.AM_PM);

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		return _cpDefinitionService.addCPDefinition(
			titleMap, null, descriptionMap, titleMap, null, null, null,
			productTypeName, false, true, false, false, 0, 10, 10, 10, 10, 0,
			false, false, null, true, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, true, serviceContext);
	}

	public <T extends BaseModel> Indexer<T> getIndexer(Class<T> clazz) {
		Indexer<T> indexer = _indexerRegistry.getIndexer(clazz.getName());

		if (indexer == null) {
			throw new NotFoundException(
				"Unable to get indexer for " + clazz.getName());
		}

		return indexer;
	}

	public ServiceContext getServiceContext() throws PortalException {
		return getServiceContext(0, new long[0]);
	}

	/**
	 * Compose the ServiceContext object which is needed to add or update a
	 * {@link CPDefinition}
	 *
	 * @param  groupId
	 * @param  assetCategoryIds
	 * @return ServiceContext
	 * @throws PortalException
	 * @see    BaseCPDemoDataCreatorHelper
	 */
	public ServiceContext getServiceContext(
			long groupId, long[] assetCategoryIds)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		User user = _userService.getUserById(PrincipalThreadLocal.getUserId());

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setTimeZone(user.getTimeZone());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private UserService _userService;

}