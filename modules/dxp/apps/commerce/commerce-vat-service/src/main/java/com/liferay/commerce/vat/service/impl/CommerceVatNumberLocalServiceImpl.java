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

package com.liferay.commerce.vat.service.impl;

import com.liferay.commerce.vat.exception.CommerceVatNumberValueException;
import com.liferay.commerce.vat.internal.search.CommerceVatNumberIndexer;
import com.liferay.commerce.vat.model.CommerceVatNumber;
import com.liferay.commerce.vat.service.base.CommerceVatNumberLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceVatNumberLocalServiceImpl
	extends CommerceVatNumberLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceVatNumber addCommerceVatNumber(
			String className, long classPK, String value,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		validate(value);

		long commerceVatNumberId = counterLocalService.increment();

		CommerceVatNumber commerceVatNumber =
			commerceVatNumberPersistence.create(commerceVatNumberId);

		commerceVatNumber.setGroupId(groupId);
		commerceVatNumber.setCompanyId(user.getCompanyId());
		commerceVatNumber.setUserId(user.getUserId());
		commerceVatNumber.setUserName(user.getFullName());
		commerceVatNumber.setClassName(className);
		commerceVatNumber.setClassPK(classPK);
		commerceVatNumber.setValue(value);

		commerceVatNumberPersistence.update(commerceVatNumber);

		return commerceVatNumber;
	}

	@Override
	public void deleteCommerceVatNumbers(String className, long classPK) {
		long classNameId = classNameLocalService.getClassNameId(className);

		commerceVatNumberPersistence.removeByC_C(classNameId, classPK);
	}

	@Override
	public CommerceVatNumber fetchCommerceVatNumber(
		long groupId, String className, long classPK) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commerceVatNumberPersistence.fetchByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public List<CommerceVatNumber> getCommerceVatNumbers(
		long groupId, int start, int end,
		OrderByComparator<CommerceVatNumber> orderByComparator) {

		return commerceVatNumberPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceVatNumbersCount(long groupId) {
		return commerceVatNumberPersistence.countByGroupId(groupId);
	}

	@Override
	public BaseModelSearchResult<CommerceVatNumber> searchCommerceVatNumbers(
			long companyId, long groupId, String keywords, int start, int end,
			Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, keywords, start, end, sort);

		return searchCommerceVatNumbers(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceVatNumber updateCommerceVatNumber(
			long commerceVatNumberId, String value)
		throws PortalException {

		CommerceVatNumber commerceVatNumber =
			commerceVatNumberPersistence.findByPrimaryKey(commerceVatNumberId);

		validate(value);

		commerceVatNumber.setValue(value);

		commerceVatNumberPersistence.update(commerceVatNumber);

		return commerceVatNumber;
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, String keywords, int start, int end,
		Sort sort) {

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(CommerceVatNumberIndexer.FIELD_VAT_NUMBER, keywords);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setStart(start);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		return searchContext;
	}

	protected List<CommerceVatNumber> getCommerceVatNumbers(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CommerceVatNumber> commerceVatNumbers = new ArrayList<>(
			documents.size());

		for (Document document : documents) {
			long commerceVatNumberId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CommerceVatNumber commerceVatNumber = fetchCommerceVatNumber(
				commerceVatNumberId);

			if (commerceVatNumber == null) {
				commerceVatNumbers = null;

				Indexer<CommerceVatNumber> indexer =
					IndexerRegistryUtil.getIndexer(CommerceVatNumber.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (commerceVatNumbers != null) {
				commerceVatNumbers.add(commerceVatNumber);
			}
		}

		return commerceVatNumbers;
	}

	protected BaseModelSearchResult<CommerceVatNumber> searchCommerceVatNumbers(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceVatNumber> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommerceVatNumber.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CommerceVatNumber> commerceVatNumbers = getCommerceVatNumbers(
				hits);

			if (commerceVatNumbers != null) {
				return new BaseModelSearchResult<>(
					commerceVatNumbers, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected void validate(String value) throws PortalException {
		if (Validator.isNull(value)) {
			throw new CommerceVatNumberValueException();
		}
	}

	private static final String[] _SELECTED_FIELD_NAMES =
		{Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID};

}