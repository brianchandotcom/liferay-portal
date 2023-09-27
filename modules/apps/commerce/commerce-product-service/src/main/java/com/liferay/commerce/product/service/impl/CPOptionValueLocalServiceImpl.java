/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.exception.CPOptionValueKeyException;
import com.liferay.commerce.product.exception.CPOptionValueNameException;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.base.CPOptionValueLocalServiceBaseImpl;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.SystemEventConstants;
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
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPOptionValue",
	service = AopService.class
)
public class CPOptionValueLocalServiceImpl
	extends CPOptionValueLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPOptionValue addCPOptionValue(
			long cpOptionId, Map<Locale, String> nameMap, double priority,
			String key, ServiceContext serviceContext)
		throws PortalException {

		return cpOptionValueLocalService.addCPOptionValue(
			StringPool.BLANK, cpOptionId, nameMap, priority, key,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPOptionValue addCPOptionValue(
			String externalReferenceCode, long cpOptionId,
			Map<Locale, String> nameMap, double priority, String key,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce product option value

		User user = _userLocalService.getUser(serviceContext.getUserId());

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		key = _friendlyURLNormalizer.normalize(key);

		_validateKey(0, cpOptionId, key);

		_validateName(nameMap);

		long cpOptionValueId = counterLocalService.increment();

		CPOptionValue cpOptionValue = cpOptionValuePersistence.create(
			cpOptionValueId);

		cpOptionValue.setExternalReferenceCode(externalReferenceCode);
		cpOptionValue.setCompanyId(user.getCompanyId());
		cpOptionValue.setUserId(user.getUserId());
		cpOptionValue.setUserName(user.getFullName());
		cpOptionValue.setCPOptionId(cpOptionId);
		cpOptionValue.setNameMap(nameMap);
		cpOptionValue.setPriority(priority);
		cpOptionValue.setKey(key);
		cpOptionValue.setExpandoBridgeAttributes(serviceContext);

		cpOptionValue = cpOptionValuePersistence.update(cpOptionValue);

		_reindexCPOption(cpOptionId);

		return cpOptionValue;
	}

	@Override
	public CPOptionValue addOrUpdateCPOptionValue(
			String externalReferenceCode, long cpOptionId,
			Map<Locale, String> nameMap, double priority, String key,
			ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}
		else {
			CPOptionValue cpOptionValue = cpOptionValuePersistence.fetchByERC_C(
				externalReferenceCode, serviceContext.getCompanyId());

			if (cpOptionValue != null) {
				return cpOptionValueLocalService.updateCPOptionValue(
					cpOptionValue.getCPOptionValueId(), nameMap, priority, key,
					serviceContext);
			}
		}

		return cpOptionValueLocalService.addCPOptionValue(
			externalReferenceCode, cpOptionId, nameMap, priority, key,
			serviceContext);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPOptionValue deleteCPOptionValue(CPOptionValue cpOptionValue)
		throws PortalException {

		// Commerce product option value

		cpOptionValuePersistence.remove(cpOptionValue);

		// Expando

		_expandoRowLocalService.deleteRows(cpOptionValue.getCPOptionValueId());

		_reindexCPOption(cpOptionValue.getCPOptionId());

		return cpOptionValue;
	}

	@Override
	public CPOptionValue deleteCPOptionValue(long cpOptionValueId)
		throws PortalException {

		CPOptionValue cpOptionValue = cpOptionValuePersistence.findByPrimaryKey(
			cpOptionValueId);

		return cpOptionValueLocalService.deleteCPOptionValue(cpOptionValue);
	}

	@Override
	public void deleteCPOptionValues(long cpOptionId) throws PortalException {
		List<CPOptionValue> cpOptionValues =
			cpOptionValueLocalService.getCPOptionValues(
				cpOptionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CPOptionValue cpOptionValue : cpOptionValues) {
			cpOptionValueLocalService.deleteCPOptionValue(cpOptionValue);
		}
	}

	@Override
	public CPOptionValue fetchByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		return cpOptionValuePersistence.fetchByERC_C(
			externalReferenceCode, companyId);
	}

	@Override
	public CPOptionValue getCPOptionValue(long cpOptionId, String key)
		throws PortalException {

		return cpOptionValuePersistence.findByC_K(cpOptionId, key);
	}

	@Override
	public List<CPOptionValue> getCPOptionValues(
		long cpOptionId, int start, int end) {

		return cpOptionValuePersistence.findByCPOptionId(
			cpOptionId, start, end);
	}

	@Override
	public List<CPOptionValue> getCPOptionValues(
		long cpOptionId, int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator) {

		return cpOptionValuePersistence.findByCPOptionId(
			cpOptionId, start, end, orderByComparator);
	}

	@Override
	public int getCPOptionValuesCount(long cpOptionId) {
		return cpOptionValuePersistence.countByCPOptionId(cpOptionId);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<CPOptionValue> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(CPOptionValue.class);

			return indexer.search(searchContext);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@Override
	public BaseModelSearchResult<CPOptionValue> searchCPOptionValues(
			long companyId, long cpOptionId, String keywords, int start,
			int end, Sort[] sorts)
		throws PortalException {

		SearchContext searchContext = _buildSearchContext(
			companyId, cpOptionId, keywords, start, end, sorts);

		return _searchCPOptionValues(searchContext);
	}

	@Override
	public int searchCPOptionValuesCount(
			long companyId, long cpOptionId, String keywords)
		throws PortalException {

		SearchContext searchContext = _buildSearchContext(
			companyId, cpOptionId, keywords, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		return _searchCPOptionValuesCount(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPOptionValue updateCPOptionValue(
			long cpOptionValueId, Map<Locale, String> nameMap, double priority,
			String key, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product option value

		CPOptionValue cpOptionValue = cpOptionValuePersistence.findByPrimaryKey(
			cpOptionValueId);

		key = _friendlyURLNormalizer.normalize(key);

		_validateKey(
			cpOptionValue.getCPOptionValueId(), cpOptionValue.getCPOptionId(),
			key);

		_validateName(nameMap);

		cpOptionValue.setNameMap(nameMap);
		cpOptionValue.setPriority(priority);
		cpOptionValue.setKey(key);
		cpOptionValue.setExpandoBridgeAttributes(serviceContext);

		cpOptionValue = cpOptionValuePersistence.update(cpOptionValue);

		_reindexCPOption(cpOptionValue.getCPOptionId());

		return cpOptionValue;
	}

	private SearchContext _buildSearchContext(
		long companyId, long cpOptionId, String keywords, int start, int end,
		Sort[] sorts) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				_FIELD_KEY, keywords
			).put(
				Field.CONTENT, keywords
			).put(
				Field.ENTRY_CLASS_PK, keywords
			).put(
				Field.NAME, keywords
			).put(
				"CPOptionId", cpOptionId
			).put(
				"params",
				LinkedHashMapBuilder.<String, Object>put(
					"keywords", keywords
				).build()
			).build());
		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		if (sorts != null) {
			searchContext.setSorts(sorts);
		}

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	private List<CPOptionValue> _getCPOptionValues(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CPOptionValue> cpOptionValues = new ArrayList<>(documents.size());

		for (Document document : documents) {
			long cpOptionValueId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CPOptionValue cpOptionValue = fetchCPOptionValue(cpOptionValueId);

			if (cpOptionValue == null) {
				cpOptionValues = null;

				Indexer<CPOptionValue> indexer = IndexerRegistryUtil.getIndexer(
					CPOptionValue.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (cpOptionValues != null) {
				cpOptionValues.add(cpOptionValue);
			}
		}

		return cpOptionValues;
	}

	private void _reindexCPOption(long cpOptionId) throws PortalException {
		Indexer<CPOption> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPOption.class);

		indexer.reindex(CPOption.class.getName(), cpOptionId);
	}

	private BaseModelSearchResult<CPOptionValue> _searchCPOptionValues(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CPOptionValue> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPOptionValue.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CPOptionValue> cpOptionValues = _getCPOptionValues(hits);

			if (cpOptionValues != null) {
				return new BaseModelSearchResult<>(
					cpOptionValues, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	private int _searchCPOptionValuesCount(SearchContext searchContext)
		throws PortalException {

		Indexer<CPOptionValue> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPOptionValue.class);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
	}

	private void _validateKey(long cpOptionValueId, long cpOptionId, String key)
		throws PortalException {

		if (Validator.isBlank(key)) {
			throw new CPOptionValueKeyException("Key is null");
		}

		CPOptionValue cpOptionValue = cpOptionValuePersistence.fetchByC_K(
			cpOptionId, key);

		if ((cpOptionValue != null) &&
			(cpOptionValue.getCPOptionValueId() != cpOptionValueId)) {

			throw new CPOptionValueKeyException("Duplicate key " + key);
		}
	}

	private void _validateName(Map<Locale, String> nameMap)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		if ((nameMap == null) || Validator.isNull(nameMap.get(locale))) {
			throw new CPOptionValueNameException(
				"Name is null for locale " + locale.getDisplayName());
		}
	}

	private static final String _FIELD_KEY = "key";

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID
	};

	@Reference
	private ExpandoRowLocalService _expandoRowLocalService;

	@Reference
	private FriendlyURLNormalizer _friendlyURLNormalizer;

	@Reference
	private UserLocalService _userLocalService;

}