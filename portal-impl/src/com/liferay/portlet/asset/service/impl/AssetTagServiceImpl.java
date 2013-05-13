/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetTagDisplay;
import com.liferay.portlet.asset.service.base.AssetTagServiceBaseImpl;
import com.liferay.portlet.asset.service.permission.AssetPermission;
import com.liferay.portlet.asset.service.permission.AssetTagPermission;
import com.liferay.portlet.asset.util.comparator.AssetTagNameComparator;
import com.liferay.util.Autocomplete;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Provides the remote service for accessing, adding, checking, deleting,
 * merging, and updating asset tags. Its methods include permission checks.
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Alvaro del Castillo
 * @author Eduardo Lundgren
 * @author Bruno Farache
 * @author Juan Fernández
 */
public class AssetTagServiceImpl extends AssetTagServiceBaseImpl {

	@Override
	public AssetTag addTag(
			String name, String[] tagProperties, ServiceContext serviceContext)
		throws PortalException, SystemException {

		AssetPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_TAG);

		return assetTagLocalService.addTag(
			getUserId(), name, tagProperties, serviceContext);
	}

	@Override
	public void deleteTag(long tagId) throws PortalException, SystemException {
		AssetTagPermission.check(
			getPermissionChecker(), tagId, ActionKeys.DELETE);

		assetTagLocalService.deleteTag(tagId);
	}

	@Override
	public void deleteTags(long[] tagIds)
		throws PortalException, SystemException {

		for (long tagId : tagIds) {
			AssetTagPermission.check(
				getPermissionChecker(), tagId, ActionKeys.DELETE);

			assetTagLocalService.deleteTag(tagId);
		}
	}

	@Override
	public List<AssetTag> getGroupsTags(long[] groupIds)
		throws SystemException {

		Set<AssetTag> groupsTags = new TreeSet<AssetTag>(
			new AssetTagNameComparator());

		for (long groupId : groupIds) {
			List<AssetTag> groupTags = getGroupTags(groupId);

			groupsTags.addAll(groupTags);
		}

		return new ArrayList<AssetTag>(groupsTags);
	}

	@Override
	public List<AssetTag> getGroupTags(long groupId) throws SystemException {
		return assetTagPersistence.filterFindByGroupId(groupId);
	}

	@Override
	public List<AssetTag> getGroupTags(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return assetTagPersistence.filterFindByGroupId(
			groupId, start, end, obc);
	}

	@Override
	public int getGroupTagsCount(long groupId) throws SystemException {
		return assetTagPersistence.filterCountByGroupId(groupId);
	}

	@Override
	public AssetTagDisplay getGroupTagsDisplay(
			long groupId, String name, int start, int end)
		throws SystemException {

		List<AssetTag> tags = null;
		int total = 0;

		if (Validator.isNotNull(name)) {
			name = (CustomSQLUtil.keywords(name))[0];

			tags = getTags(groupId, name, new String[0], start, end);
			total = getTagsCount(groupId, name, new String[0]);
		}
		else {
			tags = getGroupTags(groupId, start, end, null);
			total = getGroupTagsCount(groupId);
		}

		return new AssetTagDisplay(tags, total, start, end);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getGroupTagsDisplay(long,
	 *             String, int, int)}
	 */
	@Override
	public JSONObject getJSONGroupTags(
			long groupId, String name, int start, int end)
		throws PortalException, SystemException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		int page = end / (end - start);

		jsonObject.put("page", page);

		List<AssetTag> tags = null;
		int total = 0;

		if (Validator.isNotNull(name)) {
			name = (CustomSQLUtil.keywords(name))[0];

			tags = getTags(groupId, name, new String[0], start, end);
			total = getTagsCount(groupId, name, new String[0]);
		}
		else {
			tags = getGroupTags(groupId, start, end, null);
			total = getGroupTagsCount(groupId);
		}

		String tagsJSON = JSONFactoryUtil.looseSerialize(tags);

		JSONArray tagsJSONArray = JSONFactoryUtil.createJSONArray(tagsJSON);

		jsonObject.put("tags", tagsJSONArray);

		jsonObject.put("total", total);

		return jsonObject;
	}

	@Override
	public AssetTag getTag(long tagId) throws PortalException, SystemException {
		AssetTagPermission.check(
			getPermissionChecker(), tagId, ActionKeys.VIEW);

		return assetTagLocalService.getTag(tagId);
	}

	@Override
	public List<AssetTag> getTags(long groupId, long classNameId, String name)
		throws SystemException {

		return assetTagFinder.filterFindByG_C_N(
			groupId, classNameId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	@Override
	public List<AssetTag> getTags(
			long groupId, long classNameId, String name, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return assetTagFinder.filterFindByG_C_N(
			groupId, classNameId, name, start, end, obc);
	}

	@Override
	public List<AssetTag> getTags(
			long groupId, String name, String[] tagProperties, int start,
			int end)
		throws SystemException {

		return getTags(new long[] {groupId}, name, tagProperties, start, end);
	}

	@Override
	public List<AssetTag> getTags(
			long[] groupIds, String name, String[] tagProperties, int start,
			int end)
		throws SystemException {

		return assetTagFinder.filterFindByG_N_P(
			groupIds, name, tagProperties, start, end, null);
	}

	@Override
	public List<AssetTag> getTags(String className, long classPK)
		throws PortalException, SystemException {

		return filterTags(assetTagLocalService.getTags(className, classPK));
	}

	@Override
	public int getTagsCount(long groupId, long classNameId, String name)
		throws SystemException {

		return assetTagFinder.filterCountByG_C_N(groupId, classNameId, name);
	}

	@Override
	public int getTagsCount(long groupId, String name) throws SystemException {
		return assetTagFinder.filterCountByG_N(groupId, name);
	}

	@Override
	public int getTagsCount(long groupId, String name, String[] tagProperties)
		throws SystemException {

		return assetTagFinder.filterCountByG_N_P(groupId, name, tagProperties);
	}

	@Override
	public void mergeTags(
			long fromTagId, long toTagId, boolean overrideProperties)
		throws PortalException, SystemException {

		AssetTagPermission.check(
			getPermissionChecker(), fromTagId, ActionKeys.VIEW);

		AssetTagPermission.check(
			getPermissionChecker(), toTagId, ActionKeys.UPDATE);

		assetTagLocalService.mergeTags(fromTagId, toTagId, overrideProperties);
	}

	@Override
	public void mergeTags(
			long[] fromTagIds, long toTagId, boolean overrideProperties)
		throws PortalException, SystemException {

		for (long fromTagId : fromTagIds) {
			mergeTags(fromTagId, toTagId, overrideProperties);
		}
	}

	@Override
	public JSONArray search(
			long groupId, String name, String[] tagProperties, int start,
			int end)
		throws SystemException {

		return search(new long[] {groupId}, name, tagProperties, start, end);
	}

	@Override
	public JSONArray search(
			long[] groupIds, String name, String[] tagProperties, int start,
			int end)
		throws SystemException {

		List<AssetTag> tags = getTags(
			groupIds, name, tagProperties, start, end);

		return Autocomplete.listToJson(tags, "name", "name");
	}

	@Override
	public AssetTag updateTag(
			long tagId, String name, String[] tagProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		AssetTagPermission.check(
			getPermissionChecker(), tagId, ActionKeys.UPDATE);

		return assetTagLocalService.updateTag(
			getUserId(), tagId, name, tagProperties, serviceContext);
	}

	protected List<AssetTag> filterTags(List<AssetTag> tags)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		tags = ListUtil.copy(tags);

		Iterator<AssetTag> itr = tags.iterator();

		while (itr.hasNext()) {
			AssetTag tag = itr.next();

			if (!AssetTagPermission.contains(
					permissionChecker, tag, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return tags;
	}

}