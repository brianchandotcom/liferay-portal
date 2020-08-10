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

package com.liferay.commerce.product.content.web.internal.util;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPQuery;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = CPPublisherWebHelper.class)
public class CPPublisherWebHelper {

	public List<CPCatalogEntry> getCPCatalogEntries(
			long commerceAccountId, long groupId,
			PortletPreferences portletPreferences, ThemeDisplay themeDisplay)
		throws Exception {

		String[] catalogEntryXmls = portletPreferences.getValues(
			"catalogEntryXml", new String[0]);

		List<CPCatalogEntry> cpCatalogEntries = new ArrayList<>();

		List<Long> missingAssetCPDefinitionIds = new ArrayList<>();

		for (String catalogEntryXml : catalogEntryXmls) {
			Document document = SAXReaderUtil.read(catalogEntryXml);

			Element rootElement = document.getRootElement();

			long cpDefinitionId = GetterUtil.getLong(
				rootElement.elementText("product-id"));

			try {
				CPCatalogEntry cpCatalogEntry =
					_cpDefinitionHelper.getCPCatalogEntry(
						commerceAccountId, groupId, cpDefinitionId,
						themeDisplay.getLocale());

				if (cpCatalogEntry != null) {
					cpCatalogEntries.add(cpCatalogEntry);
				}
			}
			catch (NoSuchCPDefinitionException nscpde) {
				missingAssetCPDefinitionIds.add(cpDefinitionId);
			}
		}

		removeAndStoreSelection(
			missingAssetCPDefinitionIds, portletPreferences);

		return cpCatalogEntries;
	}

	public String getSku(CPDefinition cpDefinition, Locale locale) {
		List<CPInstance> cpInstances = cpDefinition.getCPInstances();

		if (cpInstances.size() > 1) {
			return LanguageUtil.get(locale, "multiple-skus");
		}

		CPInstance cpInstance = cpInstances.get(0);

		return cpInstance.getSku();
	}

	public boolean isDataSourceSelection(
		PortletPreferences portletPreferences) {

		String selectionStyle = GetterUtil.getString(
			portletPreferences.getValue("selectionStyle", null), "dynamic");

		if (Objects.equals(selectionStyle, "dataSource")) {
			return true;
		}

		return false;
	}

	public boolean isDynamicSelection(PortletPreferences portletPreferences) {
		String selectionStyle = GetterUtil.getString(
			portletPreferences.getValue("selectionStyle", null), "dynamic");

		if (Objects.equals(selectionStyle, "dynamic")) {
			return true;
		}

		return false;
	}

	public boolean isManualSelection(PortletPreferences portletPreferences) {
		String selectionStyle = GetterUtil.getString(
			portletPreferences.getValue("selectionStyle", null), "dynamic");

		if (Objects.equals(selectionStyle, "manual")) {
			return true;
		}

		return false;
	}

	public void removeAndStoreSelection(
			List<Long> cpDefinitionIds, PortletPreferences portletPreferences)
		throws Exception {

		if (cpDefinitionIds.isEmpty()) {
			return;
		}

		String[] catalogEntryXmls = portletPreferences.getValues(
			"catalogEntryXml", new String[0]);

		List<String> catalogEntryXmlsList = ListUtil.fromArray(
			catalogEntryXmls);

		Iterator<String> itr = catalogEntryXmlsList.iterator();

		while (itr.hasNext()) {
			String catalogEntryXml = itr.next();

			Document document = SAXReaderUtil.read(catalogEntryXml);

			Element rootElement = document.getRootElement();

			long cpDefinitionId = GetterUtil.getLong(
				rootElement.elementText("product-id"));

			if (cpDefinitionIds.contains(cpDefinitionId)) {
				itr.remove();
			}
		}

		portletPreferences.setValues(
			"catalogEntryXml", catalogEntryXmlsList.toArray(new String[0]));

		portletPreferences.store();
	}

	public void setCategoriesAndTags(
		long groupId, CPQuery cpQuery, PortletPreferences portletPreferences) {

		long[] allAssetCategoryIds = new long[0];
		long[] anyAssetCategoryIds = new long[0];
		long[] notAllAssetCategoryIds = new long[0];
		long[] notAnyAssetCategoryIds = new long[0];

		String[] allAssetTagNames = new String[0];
		String[] anyAssetTagNames = new String[0];
		String[] notAllAssetTagNames = new String[0];
		String[] notAnyAssetTagNames = new String[0];

		for (int i = 0; true; i++) {
			String[] queryValues = portletPreferences.getValues(
				"queryValues" + i, null);

			if (ArrayUtil.isEmpty(queryValues)) {
				break;
			}

			boolean queryContains = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"queryContains" + i, StringPool.BLANK));
			boolean queryAndOperator = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"queryAndOperator" + i, StringPool.BLANK));
			String queryName = portletPreferences.getValue(
				"queryName" + i, StringPool.BLANK);

			if (Objects.equals(queryName, "assetCategories")) {
				long[] assetCategoryIds = GetterUtil.getLongValues(queryValues);

				if (queryContains && queryAndOperator) {
					allAssetCategoryIds = assetCategoryIds;
				}
				else if (queryContains && !queryAndOperator) {
					anyAssetCategoryIds = assetCategoryIds;
				}
				else if (!queryContains && queryAndOperator) {
					notAllAssetCategoryIds = assetCategoryIds;
				}
				else {
					notAnyAssetCategoryIds = assetCategoryIds;
				}
			}
			else {
				if (queryContains && queryAndOperator) {
					allAssetTagNames = queryValues;
				}
				else if (queryContains && !queryAndOperator) {
					anyAssetTagNames = queryValues;
				}
				else if (!queryContains && queryAndOperator) {
					notAllAssetTagNames = queryValues;
				}
				else {
					notAnyAssetTagNames = queryValues;
				}
			}
		}

		allAssetCategoryIds = _filterAssetCategoryIds(allAssetCategoryIds);

		cpQuery.setAllCategoryIds(allAssetCategoryIds);

		long[] siteGroupIds = {groupId};

		for (String assetTagName : allAssetTagNames) {
			long[] allAssetTagIds = _assetTagLocalService.getTagIds(
				siteGroupIds, assetTagName);

			cpQuery.addAllTagIdsArray(allAssetTagIds);
		}

		cpQuery.setAnyCategoryIds(anyAssetCategoryIds);

		long[] anyAssetTagIds = _assetTagLocalService.getTagIds(
			groupId, anyAssetTagNames);

		cpQuery.setAnyTagIds(anyAssetTagIds);

		cpQuery.setNotAllCategoryIds(notAllAssetCategoryIds);

		for (String assetTagName : notAllAssetTagNames) {
			long[] notAllAssetTagIds = _assetTagLocalService.getTagIds(
				siteGroupIds, assetTagName);

			cpQuery.addNotAllTagIdsArray(notAllAssetTagIds);
		}

		cpQuery.setNotAnyCategoryIds(notAnyAssetCategoryIds);

		long[] notAnyAssetTagIds = _assetTagLocalService.getTagIds(
			siteGroupIds, notAnyAssetTagNames);

		cpQuery.setNotAnyTagIds(notAnyAssetTagIds);
	}

	public void setOrdering(
		CPQuery cpQuery, PortletPreferences portletPreferences) {

		String orderByColumn1 = GetterUtil.getString(
			portletPreferences.getValue("orderByColumn1", "modifiedDate"));

		cpQuery.setOrderByCol1(orderByColumn1);

		String orderByColumn2 = GetterUtil.getString(
			portletPreferences.getValue("orderByColumn2", "title"));

		cpQuery.setOrderByCol2(orderByColumn2);

		String orderByType1 = GetterUtil.getString(
			portletPreferences.getValue("orderByType1", "DESC"));

		cpQuery.setOrderByType1(orderByType1);

		String orderByType2 = GetterUtil.getString(
			portletPreferences.getValue("orderByType2", "ASC"));

		cpQuery.setOrderByType2(orderByType2);
	}

	private long[] _filterAssetCategoryIds(long[] assetCategoryIds) {
		List<Long> assetCategoryIdsList = new ArrayList<>();

		for (long assetCategoryId : assetCategoryIds) {
			AssetCategory category =
				_assetCategoryLocalService.fetchAssetCategory(assetCategoryId);

			if (category == null) {
				continue;
			}

			assetCategoryIdsList.add(assetCategoryId);
		}

		return ArrayUtil.toArray(assetCategoryIdsList.toArray(new Long[0]));
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

}