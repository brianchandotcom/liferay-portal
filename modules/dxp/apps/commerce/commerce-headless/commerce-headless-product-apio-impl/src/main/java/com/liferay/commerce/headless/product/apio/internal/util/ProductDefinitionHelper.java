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
import com.liferay.commerce.product.search.CPDefinitionIndexer;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltán Takács
 */
@Component(immediate = true, service = ProductDefinitionHelper.class)
public class ProductDefinitionHelper {

	/**
	 * Builds the SearchContext for finding {@link CPDefinition}'s {@link
	 * com.liferay.portal.kernel.search.Document}.
	 *
	 * @param  entryClassPK
	 * @param  keywords
	 * @param  start
	 * @param  end
	 * @param  sort
	 * @param  serviceContext
	 * @return ServiceContext
	 */
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

		searchContext.setStart(start);
		searchContext.setEnd(end);
		searchContext.setCompanyId(serviceContext.getCompanyId());

		long groupId = serviceContext.getScopeGroupId();

		if (groupId != 0) {
			searchContext.setGroupIds(new long[] {groupId});
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.addSelectedFieldNames(
			CPDefinitionIndexer.FIELD_DEFAULT_IMAGE_FILE_ENTRY_ID,
			CPDefinitionIndexer.FIELD_PRODUCT_TYPE_NAME,
			CPDefinitionIndexer.FIELD_SKUS, Field.CREATE_DATE,
			Field.ENTRY_CLASS_PK, Field.DESCRIPTION, Field.GROUP_ID,
			Field.MODIFIED_DATE, Field.TITLE, Field.USER_ID);

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

		ServiceContext serviceContext = _productIndexerHelper.getServiceContext(
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

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private ProductIndexerHelper _productIndexerHelper;

}