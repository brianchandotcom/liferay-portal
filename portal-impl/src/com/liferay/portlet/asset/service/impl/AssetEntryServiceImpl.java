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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.service.base.AssetEntryServiceBaseImpl;
import com.liferay.portlet.asset.service.permission.AssetEntryPermission;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.asset.util.AssetUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Provides the remote service for accessing and updating asset entries. Its
 * methods include permission checks.
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class AssetEntryServiceImpl extends AssetEntryServiceBaseImpl {

	@Override
	public List<AssetEntry> getCompanyEntries(
		long companyId, int start, int end) {

		List<AssetEntry> entries = new ArrayList<AssetEntry>();

		List<AssetEntry> companyEntries =
			assetEntryLocalService.getCompanyEntries(companyId, start, end);

		for (AssetEntry entry : companyEntries) {
			try {
				if (AssetEntryPermission.contains(
						getPermissionChecker(), entry, ActionKeys.VIEW)) {

					entries.add(entry);
				}
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(pe, pe);
				}
			}
		}

		return entries;
	}

	@Override
	public int getCompanyEntriesCount(long companyId) {
		return assetEntryLocalService.getCompanyEntriesCount(companyId);
	}

	@Override
	public List<AssetEntry> getEntries(AssetEntryQuery entryQuery)
		throws PortalException {

		AssetEntryQuery filteredEntryQuery = buildFilteredEntryQuery(
			entryQuery);

		if (hasEntryQueryResults(entryQuery, filteredEntryQuery)) {
			return new ArrayList<AssetEntry>();
		}

		Object[] results = filterEntryQuery(filteredEntryQuery, false);

		return (List<AssetEntry>)results[0];
	}

	@Override
	public int getEntriesCount(AssetEntryQuery entryQuery)
		throws PortalException {

		AssetEntryQuery filteredEntryQuery = buildFilteredEntryQuery(
			entryQuery);

		if (hasEntryQueryResults(entryQuery, filteredEntryQuery)) {
			return 0;
		}

		Object[] results = filterEntryQuery(filteredEntryQuery, true);

		return (Integer)results[1];
	}

	@Override
	public AssetEntry getEntry(long entryId) throws PortalException {
		AssetEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.VIEW);

		return assetEntryLocalService.getEntry(entryId);
	}

	@Override
	public AssetEntry incrementViewCounter(String className, long classPK)
		throws PortalException {

		AssetEntryPermission.check(
			getPermissionChecker(), className, classPK, ActionKeys.VIEW);

		return assetEntryLocalService.incrementViewCounter(
			getGuestOrUserId(), className, classPK);
	}

	@Override
	public AssetEntry updateEntry(
			long groupId, Date createDate, Date modifiedDate, String className,
			long classPK, String classUuid, long classTypeId,
			long[] categoryIds, String[] tagNames, boolean visible,
			Date startDate, Date endDate, Date expirationDate, String mimeType,
			String title, String description, String summary, String url,
			String layoutUuid, int height, int width, Integer priority,
			boolean sync)
		throws PortalException {

		AssetEntryPermission.check(
			getPermissionChecker(), className, classPK, ActionKeys.UPDATE);

		return assetEntryLocalService.updateEntry(
			getUserId(), groupId, createDate, modifiedDate, className, classPK,
			classUuid, classTypeId, categoryIds, tagNames, visible, startDate,
			endDate, expirationDate, mimeType, title, description, summary, url,
			layoutUuid, height, width, priority, sync);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #updateEntry(long, String,
	 *             long, String, long, long[], String[], boolean, Date, Date,
	 *             Date, String, String, String, String, String, String, int,
	 *             int, Integer, boolean)}
	 */
	@Deprecated
	@Override
	public AssetEntry updateEntry(
			long groupId, String className, long classPK, String classUuid,
			long classTypeId, long[] categoryIds, String[] tagNames,
			boolean visible, Date startDate, Date endDate, Date publishDate,
			Date expirationDate, String mimeType, String title,
			String description, String summary, String url, String layoutUuid,
			int height, int width, Integer priority, boolean sync)
		throws PortalException {

		AssetEntryPermission.check(
			getPermissionChecker(), className, classPK, ActionKeys.UPDATE);

		return assetEntryLocalService.updateEntry(
			getUserId(), groupId, className, classPK, classUuid, classTypeId,
			categoryIds, tagNames, visible, startDate, endDate, expirationDate,
			mimeType, title, description, summary, url, layoutUuid, height,
			width, priority, sync);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #updateEntry(long, Date,
	 *             Date, String, long, String, long, long[], String[], boolean,
	 *             Date, Date, Date, String, String, String, String, String,
	 *             String, int, int, Integer, boolean)}
	 */
	@Deprecated
	@Override
	public AssetEntry updateEntry(
			long groupId, String className, long classPK, String classUuid,
			long classTypeId, long[] categoryIds, String[] tagNames,
			boolean visible, Date startDate, Date endDate, Date expirationDate,
			String mimeType, String title, String description, String summary,
			String url, String layoutUuid, int height, int width,
			Integer priority, boolean sync)
		throws PortalException {

		return assetEntryLocalService.updateEntry(
			getUserId(), groupId, null, null, className, classPK, classUuid,
			classTypeId, categoryIds, tagNames, visible, startDate, endDate,
			expirationDate, mimeType, title, description, summary, url,
			layoutUuid, height, width, priority, sync);
	}

	protected AssetEntryQuery buildFilteredEntryQuery(
			AssetEntryQuery entryQuery)
		throws PortalException {

		// Return an entry query with only the category ids and tag ids that the
		// user has access to

		AssetEntryQuery filteredEntryQuery = new AssetEntryQuery(entryQuery);

		filteredEntryQuery.setAllCategoryIds(
			AssetUtil.filterCategoryIds(
				getPermissionChecker(), entryQuery.getAllCategoryIds()));
		filteredEntryQuery.setAllTagIdsArray(
			AssetUtil.filterTagIdsArray(
				getPermissionChecker(), entryQuery.getAllTagIdsArray()));
		filteredEntryQuery.setAnyCategoryIds(
			AssetUtil.filterCategoryIds(
				getPermissionChecker(), entryQuery.getAnyCategoryIds()));
		filteredEntryQuery.setAnyTagIds(
			AssetUtil.filterTagIds(
				getPermissionChecker(), entryQuery.getAnyTagIds()));

		return filteredEntryQuery;
	}

	protected Object[] filterEntryQuery(
			AssetEntryQuery entryQuery, boolean returnEntriesCountOnly)
		throws PortalException {

		ThreadLocalCache<Object[]> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.REQUEST, AssetEntryServiceImpl.class.getName());

		String key = entryQuery.toString();

		key = key.concat(StringPool.POUND).concat(
			Boolean.toString(returnEntriesCountOnly));

		Object[] results = threadLocalCache.get(key);

		if (results != null) {
			return results;
		}

		if (returnEntriesCountOnly && !entryQuery.isEnablePermissions()) {
			int entriesCount = assetEntryLocalService.getEntriesCount(
				entryQuery);

			results = new Object[] {null, entriesCount};

			threadLocalCache.put(key, results);

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

				if ((end != QueryUtil.ALL_POS) &&
					(filteredEntries.size() > end)) {

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
			filteredEntriesCount = filteredEntries.size();
		}

		results = new Object[] {filteredEntries, filteredEntriesCount};

		threadLocalCache.put(key, results);

		return results;
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
			// specified at least one tag id, but the filtered entry query shows
			// that the user does not have access to any tag ids

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntryServiceImpl.class);

}