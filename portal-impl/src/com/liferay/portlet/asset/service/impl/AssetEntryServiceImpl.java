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

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCache;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.service.base.AssetEntryServiceBaseImpl;
import com.liferay.portlet.asset.service.permission.AssetEntryPermission;
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
	public AssetEntry fetchEntry(long entryId) throws PortalException {
		AssetEntry entry = assetEntryLocalService.fetchEntry(entryId);

		if (entry != null) {
			AssetEntryPermission.check(
				getPermissionChecker(), entry, ActionKeys.VIEW);
		}

		return entry;
	}

	@Override
	public List<AssetEntry> getCompanyEntries(
		long companyId, int start, int end) {

		List<AssetEntry> entries = new ArrayList<>();

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
			return new ArrayList<>();
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
		AssetEntry entry = assetEntryLocalService.getEntry(entryId);

		AssetEntryPermission.check(
			getPermissionChecker(), entry, ActionKeys.VIEW);

		return entry;
	}

	@Override
	public AssetEntry getEntry(String className, long classPK)
		throws PortalException {

		AssetEntry entry = assetEntryLocalService.getEntry(className, classPK);

		AssetEntryPermission.check(
			getPermissionChecker(), entry, ActionKeys.VIEW);

		return entry;
	}

	@Override
	public void incrementViewCounter(AssetEntry assetEntry)
		throws PortalException {

		AssetEntryPermission.check(
			getPermissionChecker(), assetEntry, ActionKeys.VIEW);

		assetEntryLocalService.incrementViewCounter(
			getGuestOrUserId(), assetEntry);
	}

	@AccessControlled(guestAccessEnabled = true)
	@Override
	public AssetEntry incrementViewCounter(
			long companyId, String className, long classPK)
		throws PortalException {

		AssetEntryPermission.check(
			getPermissionChecker(), className, classPK, ActionKeys.VIEW);

		return assetEntryLocalService.incrementViewCounter(
			companyId, getGuestOrUserId(), className, classPK);
	}

	@Override
	public AssetEntry updateEntry(
			long groupId, Date createDate, Date modifiedDate, String className,
			long classPK, String classUuid, long classTypeId,
			long[] categoryIds, String[] tagNames, boolean listable,
			boolean visible, Date startDate, Date endDate, Date publishDate,
			Date expirationDate, String mimeType, String title,
			String description, String summary, String url, String layoutUuid,
			int height, int width, Double priority)
		throws PortalException {

		AssetEntryPermission.check(
			getPermissionChecker(), className, classPK, ActionKeys.UPDATE);

		return assetEntryLocalService.updateEntry(
			getUserId(), groupId, createDate, modifiedDate, className, classPK,
			classUuid, classTypeId, categoryIds, tagNames, listable, visible,
			startDate, endDate, publishDate, expirationDate, mimeType, title,
			description, summary, url, layoutUuid, height, width, priority);
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
		filteredEntryQuery.setAllTagIdsArray(entryQuery.getAllTagIdsArray());
		filteredEntryQuery.setAnyCategoryIds(
			AssetUtil.filterCategoryIds(
				getPermissionChecker(), entryQuery.getAnyCategoryIds()));
		filteredEntryQuery.setAnyTagIds(entryQuery.getAnyTagIds());

		return filteredEntryQuery;
	}

	protected Object[] filterEntryQuery(
			AssetEntryQuery entryQuery, boolean returnEntriesCountOnly)
		throws PortalException {

		ThreadLocalCache<Object[]> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.REQUEST, AssetEntryServiceImpl.class.getName());

		String key = entryQuery.toString();

		key = key.concat(
			StringPool.POUND
		).concat(
			Boolean.toString(returnEntriesCountOnly)
		);

		Object[] results = threadLocalCache.get(key);

		if (results != null) {
			return results;
		}

		List<AssetEntry> entries = null;

		int count = 0;

		if (!entryQuery.isEnablePermissions()) {
			if (returnEntriesCountOnly) {
				count = assetEntryLocalService.getEntriesCount(entryQuery);

				results = new Object[] {null, count};
			}
			else {
				entries = assetEntryLocalService.getEntries(entryQuery);

				results = new Object[] {entries, entries.size()};
			}
		}
		else {
			int end = entryQuery.getEnd();
			int start = entryQuery.getStart();

			entryQuery.setEnd(end + PropsValues.ASSET_FILTER_SEARCH_LIMIT);
			entryQuery.setStart(0);

			entries = assetEntryLocalService.getEntries(entryQuery);

			List<AssetEntry> filteredEntries = new ArrayList<>();

			PermissionChecker permissionChecker = getPermissionChecker();

			for (AssetEntry entry : entries) {
				long classPK = entry.getClassPK();

				AssetRendererFactory<?> assetRendererFactory =
					AssetRendererFactoryRegistryUtil.
						getAssetRendererFactoryByClassName(
							entry.getClassName());

				try {
					if (assetRendererFactory.hasPermission(
							permissionChecker, classPK, ActionKeys.VIEW)) {

						filteredEntries.add(entry);
					}
				}
				catch (Exception e) {
				}
			}

			count = filteredEntries.size();

			if ((end != QueryUtil.ALL_POS) && (start != QueryUtil.ALL_POS)) {
				if (end > count) {
					end = count;
				}

				if (start > count) {
					start = count;
				}

				filteredEntries = filteredEntries.subList(start, end);
			}

			entryQuery.setEnd(end);
			entryQuery.setStart(start);

			results = new Object[] {filteredEntries, count};
		}

		threadLocalCache.put(key, results);

		return results;
	}

	protected boolean hasEntryQueryResults(
		AssetEntryQuery originalEntryQuery,
		AssetEntryQuery filteredEntryQuery) {

		long[] filteredEntryQueryAllCategoryIds =
			filteredEntryQuery.getAllCategoryIds();
		long[] originalEntryQueryAllCategoryIds =
			originalEntryQuery.getAllCategoryIds();

		if (originalEntryQueryAllCategoryIds.length >
				filteredEntryQueryAllCategoryIds.length) {

			// No results will be available if the user must have access to all
			// category ids, but the user has access to fewer category ids in
			// the filtered entry query than what was specified in the original
			// entry query

			return true;
		}

		long[] filteredEntryQueryAllTagIds = filteredEntryQuery.getAllTagIds();
		long[] originalEntryQueryAllTagIds = originalEntryQuery.getAllTagIds();

		if (originalEntryQueryAllTagIds.length >
				filteredEntryQueryAllTagIds.length) {

			// No results will be available if the user must have access to all
			// tag ids, but the user has access to fewer tag ids in the filtered
			// entry query than what was specified in the original entry query

			return true;
		}

		if (ArrayUtil.isNotEmpty(originalEntryQuery.getAnyCategoryIds()) &&
			ArrayUtil.isEmpty(filteredEntryQuery.getAnyCategoryIds())) {

			// No results will be available if the original entry query
			// specified at least one category id, but the filtered entry query
			// shows that the user does not have access to any category ids

			return true;
		}

		if (ArrayUtil.isNotEmpty(originalEntryQuery.getAnyTagIds()) &&
			ArrayUtil.isEmpty(filteredEntryQuery.getAnyTagIds())) {

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