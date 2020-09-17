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

package com.liferay.asset.internal.info.list.provider;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.util.AssetHelper;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SortFactoryUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = InfoListProvider.class)
public class RecentContentInfoListProvider
	extends BaseAssetsInfoListProvider implements InfoListProvider<AssetEntry> {

	@Override
	public List<AssetEntry> getInfoList(
		InfoListProviderContext infoListProviderContext) {

		return getInfoList(infoListProviderContext, null, null);
	}

	@Override
	public List<AssetEntry> getInfoList(
		InfoListProviderContext infoListProviderContext, Pagination pagination,
		Sort sort) {

		AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
			infoListProviderContext, Field.MODIFIED_DATE, "DESC", pagination);

		try {
			Hits hits = _assetHelper.search(
				_getSearchContext(infoListProviderContext), assetEntryQuery,
				assetEntryQuery.getStart(), assetEntryQuery.getEnd());

			return _assetHelper.getAssetEntries(hits);
		}
		catch (Exception exception) {
			_log.error("Unable to get asset entries", exception);
		}

		return Collections.emptyList();
	}

	@Override
	public int getInfoListCount(
		InfoListProviderContext infoListProviderContext) {

		try {
			Long count = _assetHelper.searchCount(
				_getSearchContext(infoListProviderContext),
				getAssetEntryQuery(
					infoListProviderContext, Field.MODIFIED_DATE, "DESC",
					null));

			return count.intValue();
		}
		catch (Exception exception) {
			_log.error("Unable to get asset entries count", exception);
		}

		return 0;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "recent-content");
	}

	private SearchContext _getSearchContext(
			InfoListProviderContext infoListProviderContext)
		throws Exception {

		Company company = infoListProviderContext.getCompany();

		long groupId = company.getGroupId();

		Optional<Group> groupOptional =
			infoListProviderContext.getGroupOptional();

		if (groupOptional.isPresent()) {
			Group group = groupOptional.get();

			groupId = group.getGroupId();
		}

		User user = infoListProviderContext.getUser();

		Optional<Layout> layoutOptional =
			infoListProviderContext.getLayoutOptional();

		SearchContext searchContext = SearchContextFactory.getInstance(
			new long[0], new String[0], new HashMap<>(), company.getCompanyId(),
			null, layoutOptional.orElse(null), null, groupId, null,
			user.getUserId());

		searchContext.setSorts(
			SortFactoryUtil.create(
				Field.MODIFIED_DATE,
				com.liferay.portal.kernel.search.Sort.LONG_TYPE, true),
			SortFactoryUtil.create(
				Field.CREATE_DATE,
				com.liferay.portal.kernel.search.Sort.LONG_TYPE, true));

		return searchContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RecentContentInfoListProvider.class);

	@Reference
	private AssetHelper _assetHelper;

	@Reference(target = "(bundle.symbolic.name=com.liferay.asset.service)")
	private ResourceBundleLoader _resourceBundleLoader;

}