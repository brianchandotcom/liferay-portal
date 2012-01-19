/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetEntryDisplay;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.service.base.AssetEntryServiceBaseImpl;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;
import com.liferay.portlet.asset.service.permission.AssetTagPermission;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.social.model.SocialActivityConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class AssetEntryServiceImpl extends AssetEntryServiceBaseImpl {

	public List<AssetEntry> getCompanyEntries(
			long companyId, int start, int end)
		throws SystemException {

		return assetEntryLocalService.getCompanyEntries(companyId, start, end);
	}

	public int getCompanyEntriesCount(long companyId) throws SystemException {
		return assetEntryLocalService.getCompanyEntriesCount(companyId);
	}

	public AssetEntryDisplay[] getCompanyEntryDisplays(
			long companyId, int start, int end, String languageId)
		throws SystemException {

		return assetEntryLocalService.getCompanyEntryDisplays(
			companyId, start, end, languageId);
	}

	public List<AssetEntry> getEntries(AssetEntryQuery entryQuery)
		throws PortalException, SystemException {

		AssetEntryQuery filteredEntryQuery = buildFilteredEntryQuery(
			entryQuery);

		if (hasEntryQueryResults(entryQuery, filteredEntryQuery)) {
			return new ArrayList<AssetEntry>();
		}

		Object[] results = filterEntryQuery(filteredEntryQuery);

		return (List<AssetEntry>)results[0];
	}

	public int getEntriesCount(AssetEntryQuery entryQuery)
		throws PortalException, SystemException {

		AssetEntryQuery filteredEntryQuery = buildFilteredEntryQuery(
			entryQuery);

		if (hasEntryQueryResults(entryQuery, filteredEntryQuery)) {
			return 0;
		}

		Object[] results = filterEntryQuery(filteredEntryQuery);

		return (Integer)results[1];
	}

	public AssetEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return assetEntryLocalService.getEntry(entryId);
	}

	public AssetEntry incrementViewCounter(String className, long classPK)
		throws PortalException, SystemException {

		if (!PropsValues.ASSET_ENTRY_INCREMENT_VIEW_COUNTER_ENABLED) {
			return null;
		}

		User user = getGuestOrUser();

		assetEntryLocalService.incrementViewCounter(
			user.getUserId(), className, classPK, 1);

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			className, classPK);

		if (!user.isDefaultUser()) {
			socialActivityLocalService.addActivity(
				user.getUserId(), assetEntry.getGroupId(), className, classPK,
				SocialActivityConstants.TYPE_VIEW, StringPool.BLANK, 0);
		}

		return assetEntry;
	}

	public AssetEntryDisplay[] searchEntryDisplays(
			long companyId, long[] groupIds, String className, String keywords,
			String languageId, int start, int end)
		throws SystemException {

		return assetEntryLocalService.searchEntryDisplays(
			companyId, groupIds, className, keywords, languageId, start, end);
	}

	public int searchEntryDisplaysCount(
			long companyId, long[] groupIds, String className, String keywords,
			String languageId)
		throws SystemException {

		return assetEntryLocalService.searchEntryDisplaysCount(
			companyId, groupIds, className, keywords, languageId);
	}

	public AssetEntry updateEntry(
			long groupId, String className, long classPK, String classUuid,
			long classTypeId, long[] categoryIds, String[] tagNames,
			boolean visible, Date startDate, Date endDate, Date publishDate,
			Date expirationDate, String mimeType, String title,
			String description, String summary, String url, String layoutUuid,
			int height, int width, Integer priority, boolean sync)
		throws PortalException, SystemException {

		return assetEntryLocalService.updateEntry(
			getUserId(), groupId, className, classPK, classUuid, classTypeId,
			categoryIds, tagNames, visible, startDate, endDate, publishDate,
			expirationDate, mimeType, title, description, summary, url,
			layoutUuid, height, width, priority, sync);
	}

	protected AssetEntryQuery buildFilteredEntryQuery(
			AssetEntryQuery entryQuery)
		throws PortalException, SystemException {

		// Return an entry query with only the category ids and tag ids that the
		// user has access to

		AssetEntryQuery filteredEntryQuery = new AssetEntryQuery(entryQuery);

		filteredEntryQuery.setAllCategoryIds(
			filterCategoryIds(entryQuery.getAllCategoryIds()));
		filteredEntryQuery.setAllTagIds(
			filterTagIds(entryQuery.getAllTagIds()));
		filteredEntryQuery.setAnyCategoryIds(
			filterCategoryIds(entryQuery.getAnyCategoryIds()));
		filteredEntryQuery.setAnyTagIds(
			filterTagIds(entryQuery.getAnyTagIds()));

		return filteredEntryQuery;
	}

	protected long[] filterCategoryIds(long[] categoryIds)
		throws PortalException, SystemException {

		List<Long> viewableCategoryIds = new ArrayList<Long>();

		for (long categoryId : categoryIds) {
			if (AssetCategoryPermission.contains(
					getPermissionChecker(), categoryId, ActionKeys.VIEW)) {

				viewableCategoryIds.add(categoryId);
			}
		}

		return ArrayUtil.toArray(
			viewableCategoryIds.toArray(new Long[viewableCategoryIds.size()]));
	}

	protected Object[] filterEntryQuery(AssetEntryQuery entryQuery)
		throws PortalException, SystemException {

		ThreadLocalCache<Object[]> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.REQUEST, AssetEntryServiceImpl.class.getName());

		String key = entryQuery.toString();

		Object[] results = threadLocalCache.get(key);

		if (results != null) {
			return results;
		}

		int end = entryQuery.getEnd();
		int start = entryQuery.getStart();

		if (entryQuery.isEnablePermissions()) {
			entryQuery.setEnd(end + PropsValues.ASSET_FILTER_SEARCH_LIMIT);
			entryQuery.setStart(0);
		}

		List<AssetEntry> entries = assetEntryLocalService.getEntries(
			entryQuery);

		List<AssetEntry> filteredEntries = null;
		int filteredEntriesCount = 0;

		if (entryQuery.isEnablePermissions()) {
			PermissionChecker permissionChecker = getPermissionChecker();

			filteredEntries = new ArrayList<AssetEntry>();

			for (AssetEntry entry : entries) {
				String className = entry.getClassName();
				long classPK = entry.getClassPK();

				AssetRendererFactory assetRendererFactory =
					AssetRendererFactoryRegistryUtil.
						getAssetRendererFactoryByClassName(className);

				try {
					if (assetRendererFactory.hasPermission(
							permissionChecker, classPK, ActionKeys.VIEW)) {

						filteredEntries.add(entry);
					}
				}
				catch (Exception e) {
				}

				if (filteredEntries.size() > end) {
					break;
				}
			}

			filteredEntriesCount = filteredEntries.size();

			if ((end != QueryUtil.ALL_POS) && (start != QueryUtil.ALL_POS)) {
				if (end > filteredEntriesCount) {
					end = filteredEntriesCount;
				}

				if (start > filteredEntriesCount) {
					start = filteredEntriesCount;
				}

				filteredEntries = filteredEntries.subList(start, end);
			}

			entryQuery.setEnd(end);
			entryQuery.setStart(start);
		}
		else {
			filteredEntries = entries;
			filteredEntriesCount = entries.size();
		}

		results = new Object[] {filteredEntries, filteredEntriesCount};

		threadLocalCache.put(key, results);

		return results;
	}

	protected long[] filterTagIds(long[] tagIds)
		throws PortalException, SystemException {

		List<Long> viewableTagIds = new ArrayList<Long>();

		for (long tagId : tagIds) {
			if (AssetTagPermission.contains(
					getPermissionChecker(), tagId, ActionKeys.VIEW)) {

				viewableTagIds.add(tagId);
			}
		}

		return ArrayUtil.toArray(
			viewableTagIds.toArray(new Long[viewableTagIds.size()]));
	}

	protected boolean hasEntryQueryResults(
		AssetEntryQuery originalEntryQuery,
		AssetEntryQuery filteredEntryQuery) {

		if (originalEntryQuery.getAllCategoryIds().length >
				filteredEntryQuery.getAllCategoryIds().length) {

			// No results will be available if the user must have access to all
			// category ids, but the user has access to fewer category ids in
			// the filtered entry query than what was specified in the original
			// entry query

			return true;
		}

		if (originalEntryQuery.getAllTagIds().length >
				filteredEntryQuery.getAllTagIds().length) {

			// No results will be available if the user must have access to all
			// tag ids, but the user has access to fewer tag ids in the filtered
			// entry query than what was specified in the original entry query

			return true;
		}

		if ((originalEntryQuery.getAnyCategoryIds().length > 0) &&
			(filteredEntryQuery.getAnyCategoryIds().length == 0)) {

			// No results will be available if the original entry query
			// specified at least one category id, but the filtered entry query
			// shows that the user does not have access to any category ids

			return true;
		}

		if ((originalEntryQuery.getAnyTagIds().length > 0) &&
			(filteredEntryQuery.getAnyTagIds().length == 0)) {

			// No results will be available if the original entry query
			// specified at least one tag id, but the filtered entry query
			// shows that the user does not have access to any tag ids

			return true;
		}

		return false;
	}

}