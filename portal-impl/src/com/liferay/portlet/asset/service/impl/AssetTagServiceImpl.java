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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
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
			long groupId, String name, ServiceContext serviceContext)
		throws PortalException {

		AssetPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_TAG);

		return assetTagLocalService.addTag(
			getUserId(), groupId, name, serviceContext);
	}

	@Override
	public void deleteTag(long tagId) throws PortalException {
		AssetTagPermission.check(
			getPermissionChecker(), tagId, ActionKeys.DELETE);

		assetTagLocalService.deleteTag(tagId);
	}

	@Override
	public void deleteTags(long[] tagIds) throws PortalException {
		for (long tagId : tagIds) {
			AssetTagPermission.check(
				getPermissionChecker(), tagId, ActionKeys.DELETE);

			assetTagLocalService.deleteTag(tagId);
		}
	}

	@Override
	public List<AssetTag> getGroupsTags(long[] groupIds) {
		Set<AssetTag> groupsTags = new TreeSet<>(new AssetTagNameComparator());

		for (long groupId : groupIds) {
			List<AssetTag> groupTags = getGroupTags(groupId);

			groupsTags.addAll(groupTags);
		}

		return new ArrayList<>(groupsTags);
	}

	@Override
	public List<AssetTag> getGroupTags(long groupId) {
		return assetTagPersistence.findByGroupId(groupId);
	}

	@Override
	public List<AssetTag> getGroupTags(
		long groupId, int start, int end, OrderByComparator<AssetTag> obc) {

		return assetTagPersistence.findByGroupId(groupId, start, end, obc);
	}

	@Override
	public int getGroupTagsCount(long groupId) {
		return assetTagPersistence.countByGroupId(groupId);
	}

	@Override
	public AssetTagDisplay getGroupTagsDisplay(
		long groupId, String name, int start, int end) {

		List<AssetTag> tags = null;
		int total = 0;

		if (Validator.isNotNull(name)) {
			name = (CustomSQLUtil.keywords(name))[0];

			tags = getTags(groupId, name, start, end);
			total = getTagsCount(groupId, name);
		}
		else {
			tags = getGroupTags(groupId, start, end, null);
			total = getGroupTagsCount(groupId);
		}

		return new AssetTagDisplay(tags, total, start, end);
	}

	@Override
	public AssetTag getTag(long tagId) throws PortalException {
		return assetTagLocalService.getTag(tagId);
	}

	@Override
	public List<AssetTag> getTags(long groupId, long classNameId, String name) {
		return assetTagFinder.findByG_C_N(
			groupId, classNameId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	@Override
	public List<AssetTag> getTags(
		long groupId, long classNameId, String name, int start, int end,
		OrderByComparator<AssetTag> obc) {

		return assetTagFinder.findByG_C_N(
			groupId, classNameId, name, start, end, obc);
	}

	@Override
	public List<AssetTag> getTags(
		long groupId, String name, int start, int end) {

		return getTags(new long[] {groupId}, name, start, end);
	}

	@Override
	public List<AssetTag> getTags(
		long groupId, String name, int start, int end,
		OrderByComparator<AssetTag> obc) {

		return getTags(new long[] {groupId}, name, start, end, obc);
	}

	@Override
	public List<AssetTag> getTags(
		long[] groupIds, String name, int start, int end) {

		return getTags(
			groupIds, name, start, end, new AssetTagNameComparator());
	}

	@Override
	public List<AssetTag> getTags(
		long[] groupIds, String name, int start, int end,
		OrderByComparator<AssetTag> obc) {

		if (Validator.isNull(name)) {
			return assetTagPersistence.findByGroupId(groupIds, start, end, obc);
		}

		return assetTagPersistence.findByG_LikeN(
			groupIds, name, start, end, obc);
	}

	@Override
	public List<AssetTag> getTags(String className, long classPK) {
		return assetTagLocalService.getTags(className, classPK);
	}

	@Override
	public int getTagsCount(long groupId, String name) {
		if (Validator.isNull(name)) {
			return assetTagPersistence.countByGroupId(groupId);
		}

		return assetTagPersistence.countByG_LikeN(groupId, name);
	}

	@Override
	public int getVisibleAssetsTagsCount(
		long groupId, long classNameId, String name) {

		return assetTagFinder.countByG_C_N(groupId, classNameId, name);
	}

	@Override
	public int getVisibleAssetsTagsCount(long groupId, String name) {
		return assetTagFinder.countByG_N(groupId, name);
	}

	@Override
	public void mergeTags(long fromTagId, long toTagId) throws PortalException {
		AssetTagPermission.check(
			getPermissionChecker(), toTagId, ActionKeys.UPDATE);

		assetTagLocalService.mergeTags(fromTagId, toTagId);
	}

	@Override
	public void mergeTags(long[] fromTagIds, long toTagId)
		throws PortalException {

		for (long fromTagId : fromTagIds) {
			mergeTags(fromTagId, toTagId);
		}
	}

	@Override
	public JSONArray search(long groupId, String name, int start, int end) {
		return search(new long[] {groupId}, name, start, end);
	}

	@Override
	public JSONArray search(long[] groupIds, String name, int start, int end) {
		List<AssetTag> tags = getTags(groupIds, name, start, end);

		return Autocomplete.listToJson(tags, "name", "name");
	}

	@Override
	public AssetTag updateTag(
			long tagId, String name, ServiceContext serviceContext)
		throws PortalException {

		AssetTagPermission.check(
			getPermissionChecker(), tagId, ActionKeys.UPDATE);

		return assetTagLocalService.updateTag(
			getUserId(), tagId, name, serviceContext);
	}

}