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

import com.liferay.portal.kernel.cache.ThreadLocalCachable;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.permission.ModelPermissions;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.asset.DuplicateTagException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.base.AssetTagLocalServiceBaseImpl;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.portlet.asset.util.comparator.AssetTagNameComparator;
import com.liferay.portlet.social.util.SocialCounterPeriodUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides the local service for accessing, adding, checking, deleting,
 * merging, and updating asset tags.
 *
 * @author Brian Wing Shun Chan
 * @author Alvaro del Castillo
 * @author Jorge Ferrer
 * @author Bruno Farache
 */
public class AssetTagLocalServiceImpl extends AssetTagLocalServiceBaseImpl {

	/**
	 * Adds an asset tag.
	 *
	 * @param  userId the primary key of the user adding the asset tag
	 * @param  groupId the primary key of the group in which the asset tag is to
	 *         be added
	 * @param  name the asset tag's name
	 * @param  serviceContext the service context to be applied
	 * @return the asset tag that was added
	 * @throws PortalException if a user with the primary key could not be found
	 *         or if a portal exception occurred
	 */
	@Override
	public AssetTag addTag(
			long userId, long groupId, String name,
			ServiceContext serviceContext)
		throws PortalException {

		// Tag

		User user = userPersistence.findByPrimaryKey(userId);

		long tagId = counterLocalService.increment();

		AssetTag tag = assetTagPersistence.create(tagId);

		tag.setUuid(serviceContext.getUuid());
		tag.setGroupId(groupId);
		tag.setCompanyId(user.getCompanyId());
		tag.setUserId(user.getUserId());
		tag.setUserName(user.getFullName());

		name = name.trim();
		name = StringUtil.toLowerCase(name);

		if (hasTag(groupId, name)) {
			throw new DuplicateTagException(
				"A tag with the name " + name + " already exists");
		}

		validate(name);

		tag.setName(name);

		assetTagPersistence.update(tag);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addTagResources(
				tag, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addTagResources(tag, serviceContext.getModelPermissions());
		}

		return tag;
	}

	/**
	 * Adds resources for the asset tag.
	 *
	 * @param  tag the asset tag for which to add resources
	 * @param  addGroupPermissions whether to add group permissions
	 * @param  addGuestPermissions whether to add guest permissions
	 * @throws PortalException if resources could not be added for the asset tag
	 *         or if a portal exception occurred
	 */
	@Override
	public void addTagResources(
			AssetTag tag, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			tag.getCompanyId(), tag.getGroupId(), tag.getUserId(),
			AssetTag.class.getName(), tag.getTagId(), false,
			addGroupPermissions, addGuestPermissions);
	}

	/**
	 * Adds resources for the asset tag using the group and guest permissions.
	 *
	 * @param  tag the asset tag for which to add resources
	 * @param  modelPermissions the model permissions to be applied
	 * @throws PortalException if resources could not be added for the asset tag
	 *         or if a portal exception occurred
	 */
	@Override
	public void addTagResources(AssetTag tag, ModelPermissions modelPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			tag.getCompanyId(), tag.getGroupId(), tag.getUserId(),
			AssetTag.class.getName(), tag.getTagId(), modelPermissions);
	}

	/**
	 * Returns the asset tags matching the group and names, creating new asset
	 * tags matching the names if the group doesn't already have them.
	 *
	 * <p>
	 * For each name, if an asset tag with the name doesn't already exist in the
	 * group, this method creates a new asset tag with the name in the group.
	 * </p>
	 *
	 * @param  userId the primary key of the user checking the asset tags
	 * @param  group the group in which to check the asset tags
	 * @param  names the asset tag names
	 * @return the asset tags matching the group and names and new asset tags
	 *         matching the names that don't already exist in the group
	 * @throws PortalException if a matching group could not be found or if a
	 *         portal exception occurred
	 */
	@Override
	public List<AssetTag> checkTags(long userId, Group group, String[] names)
		throws PortalException {

		List<AssetTag> tags = new ArrayList<>();

		for (String name : names) {
			AssetTag tag = fetchTag(group.getGroupId(), name);

			if (tag == null) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setAddGroupPermissions(true);
				serviceContext.setAddGuestPermissions(true);
				serviceContext.setScopeGroupId(group.getGroupId());

				tag = addTag(userId, group.getGroupId(), name, serviceContext);
			}

			if (tag != null) {
				tags.add(tag);
			}
		}

		return tags;
	}

	/**
	 * Returns the asset tags matching the group and names, creating new asset
	 * tags matching the names if the group doesn't already have them.
	 *
	 * @param  userId the primary key of the user checking the asset tags
	 * @param  groupId the primary key of the group in which check the asset
	 *         tags
	 * @param  names the asset tag names
	 * @return the asset tags matching the group and names and new asset tags
	 *         matching the names that don't already exist in the group
	 * @throws PortalException if a matching group could not be found or if a
	 *         portal exception occurred
	 */
	@Override
	public List<AssetTag> checkTags(long userId, long groupId, String[] names)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		return checkTags(userId, group, names);
	}

	/**
	 * Decrements the number of assets to which the asset tag has been applied.
	 *
	 * @param  tagId the primary key of the asset tag
	 * @param  classNameId the class name ID of the entity to which the asset
	 *         tag had been applied
	 * @return the asset tag
	 * @throws PortalException if an asset tag with the primary key could not be
	 *         found or if a portal exception occurred
	 */
	@Override
	public AssetTag decrementAssetCount(long tagId, long classNameId)
		throws PortalException {

		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		tag.setAssetCount(Math.max(0, tag.getAssetCount() - 1));

		assetTagPersistence.update(tag);

		assetTagStatsLocalService.updateTagStats(tagId, classNameId);

		return tag;
	}

	/**
	 * Deletes all asset tags in the group.
	 *
	 * @param  groupId the primary key of the group in which to delete all asset
	 *         tags
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteGroupTags(long groupId) throws PortalException {
		List<AssetTag> tags = getGroupTags(groupId);

		for (AssetTag tag : tags) {
			assetTagLocalService.deleteTag(tag);
		}
	}

	/**
	 * Deletes the asset tag.
	 *
	 * @param  tag the asset tag to be deleted
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteTag(AssetTag tag) throws PortalException {

		// Entries

		List<AssetEntry> entries = assetTagPersistence.getAssetEntries(
			tag.getTagId());

		// Tag

		assetTagPersistence.remove(tag);

		// Resources

		resourceLocalService.deleteResource(
			tag.getCompanyId(), AssetTag.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, tag.getTagId());

		// Stats

		assetTagStatsLocalService.deleteTagStatsByTagId(tag.getTagId());

		// Indexer

		assetEntryLocalService.reindex(entries);
	}

	/**
	 * Deletes the asset tag.
	 *
	 * @param  tagId the primary key of the asset tag
	 * @throws PortalException if no asset tag could be found with the primary
	 *         key or if a portal exception occurred
	 */
	@Override
	public void deleteTag(long tagId) throws PortalException {
		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		assetTagLocalService.deleteTag(tag);
	}

	/**
	 * Returns the asset tag with the name in the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  name the asset tag's name
	 * @return the asset tag with the name in the group or <code>null</code> if
	 *         it could not be found
	 */
	@Override
	public AssetTag fetchTag(long groupId, String name) {
		return assetTagPersistence.fetchByG_N(groupId, name);
	}

	/**
	 * Returns the asset tags of the asset entry.
	 *
	 * @param  entryId the primary key of the asset entry
	 * @return the asset tags of the asset entry
	 */
	@Override
	public List<AssetTag> getEntryTags(long entryId) {
		return assetEntryPersistence.getAssetTags(entryId);
	}

	/**
	 * Returns the asset tags in the groups.
	 *
	 * @param  groupIds the primary keys of the groups
	 * @return the asset tags in the groups
	 */
	@Override
	public List<AssetTag> getGroupsTags(long[] groupIds) {
		List<AssetTag> groupsTags = new ArrayList<>();

		for (long groupId : groupIds) {
			List<AssetTag> groupTags = getGroupTags(groupId);

			groupsTags.addAll(groupTags);
		}

		return groupsTags;
	}

	/**
	 * Returns the asset tags in the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return the asset tags in the group
	 */
	@Override
	public List<AssetTag> getGroupTags(long groupId) {
		return assetTagPersistence.findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the asset tags in the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  start the lower bound of the range of asset tags
	 * @param  end the upper bound of the range of asset tags (not inclusive)
	 * @return the range of matching asset tags
	 */
	@Override
	public List<AssetTag> getGroupTags(long groupId, int start, int end) {
		return assetTagPersistence.findByGroupId(groupId, start, end);
	}

	/**
	 * Returns the number of asset tags in the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return the number of asset tags in the group
	 */
	@Override
	public int getGroupTagsCount(long groupId) {
		return assetTagPersistence.countByGroupId(groupId);
	}

	@Override
	public List<AssetTag> getSocialActivityCounterOffsetTags(
		long groupId, String socialActivityCounterName, int startOffset,
		int endOffset) {

		int startPeriod = SocialCounterPeriodUtil.getStartPeriod(startOffset);
		int endPeriod = SocialCounterPeriodUtil.getEndPeriod(endOffset);

		return getSocialActivityCounterPeriodTags(
			groupId, socialActivityCounterName, startPeriod, endPeriod);
	}

	@Override
	public List<AssetTag> getSocialActivityCounterPeriodTags(
		long groupId, String socialActivityCounterName, int startPeriod,
		int endPeriod) {

		int offset = SocialCounterPeriodUtil.getOffset(endPeriod);

		int periodLength = SocialCounterPeriodUtil.getPeriodLength(offset);

		return assetTagFinder.findByG_N_S_E(
			groupId, socialActivityCounterName, startPeriod, endPeriod,
			periodLength);
	}

	/**
	 * Returns the asset tag with the primary key.
	 *
	 * @param  tagId the primary key of the asset tag
	 * @return the asset tag with the primary key
	 * @throws PortalException if an asset tag with the primary key could not be
	 *         found
	 */
	@Override
	public AssetTag getTag(long tagId) throws PortalException {
		return assetTagPersistence.findByPrimaryKey(tagId);
	}

	/**
	 * Returns the asset tag with the name in the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  name the name of the asset tag
	 * @return the asset tag with the name in the group
	 * @throws PortalException if a matching asset tag could not be found
	 */
	@Override
	public AssetTag getTag(long groupId, String name) throws PortalException {
		return assetTagPersistence.findByG_N(groupId, name);
	}

	/**
	 * Returns the primary keys of the asset tags with the names in the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  names the names of the asset tags
	 * @return the primary keys of the asset tags with the names in the group
	 */
	@Override
	public long[] getTagIds(long groupId, String[] names) {
		List<Long> tagIds = new ArrayList<>(names.length);

		for (String name : names) {
			AssetTag tag = fetchTag(groupId, name);

			if (tag == null) {
				continue;
			}

			tagIds.add(tag.getTagId());
		}

		return ArrayUtil.toArray(tagIds.toArray(new Long[tagIds.size()]));
	}

	/**
	 * Returns the primary keys of the asset tags with the name in the groups.
	 *
	 * @param  groupIds the primary keys of the groups
	 * @param  name the name of the asset tags
	 * @return the primary keys of the asset tags with the name in the groups
	 */
	@Override
	public long[] getTagIds(long[] groupIds, String name) {
		List<Long> tagIds = new ArrayList<>(groupIds.length);

		for (long groupId : groupIds) {
			AssetTag tag = fetchTag(groupId, name);

			if (tag == null) {
				continue;
			}

			tagIds.add(tag.getTagId());
		}

		return ArrayUtil.toArray(tagIds.toArray(new Long[tagIds.size()]));
	}

	/**
	 * Returns the primary keys of the asset tags with the names in the groups.
	 *
	 * @param  groupIds the primary keys of the groups
	 * @param  names the names of the asset tags
	 * @return the primary keys of the asset tags with the names in the groups
	 */
	@Override
	public long[] getTagIds(long[] groupIds, String[] names) {
		long[] tagsIds = new long[0];

		for (long groupId : groupIds) {
			tagsIds = ArrayUtil.append(tagsIds, getTagIds(groupId, names));
		}

		return tagsIds;
	}

	/**
	 * Returns the names of all the asset tags.
	 *
	 * @return the names of all the asset tags
	 */
	@Override
	public String[] getTagNames() {
		return getTagNames(getTags());
	}

	/**
	 * Returns the names of the asset tags of the entity.
	 *
	 * @param  classNameId the class name ID of the entity
	 * @param  classPK the primary key of the entity
	 * @return the names of the asset tags of the entity
	 */
	@Override
	public String[] getTagNames(long classNameId, long classPK) {
		return getTagNames(getTags(classNameId, classPK));
	}

	/**
	 * Returns the names of the asset tags of the entity
	 *
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @return the names of the asset tags of the entity
	 */
	@Override
	public String[] getTagNames(String className, long classPK) {
		return getTagNames(getTags(className, classPK));
	}

	/**
	 * Returns all the asset tags.
	 *
	 * @return the asset tags
	 */
	@Override
	public List<AssetTag> getTags() {
		return assetTagPersistence.findAll();
	}

	/**
	 * Returns the asset tags of the entity.
	 *
	 * @param  classNameId the class name ID of the entity
	 * @param  classPK the primary key of the entity
	 * @return the asset tags of the entity
	 */
	@Override
	public List<AssetTag> getTags(long classNameId, long classPK) {
		AssetEntry entry = assetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (entry == null) {
			return Collections.emptyList();
		}

		return assetEntryPersistence.getAssetTags(entry.getEntryId());
	}

	@Override
	public List<AssetTag> getTags(long groupId, long classNameId, String name) {
		return assetTagFinder.findByG_C_N(
			groupId, classNameId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	@Override
	public List<AssetTag> getTags(
		long groupId, long classNameId, String name, int start, int end) {

		return assetTagFinder.findByG_C_N(
			groupId, classNameId, name, start, end, null);
	}

	/**
	 * Returns the asset tags of the entity.
	 *
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @return the asset tags of the entity
	 */
	@Override
	@ThreadLocalCachable
	public List<AssetTag> getTags(String className, long classPK) {
		long classNameId = classNameLocalService.getClassNameId(className);

		return getTags(classNameId, classPK);
	}

	@Override
	public int getTagsSize(long groupId, long classNameId, String name) {
		return assetTagFinder.countByG_C_N(groupId, classNameId, name);
	}

	/**
	 * Returns <code>true</code> if the group contains an asset tag with the
	 * name.
	 *
	 * @param  groupId the primary key of the group
	 * @param  name the name of the asset tag
	 * @return <code>true</code> if the group contains an asset tag with the
	 *         name; <code>false</code> otherwise.
	 */
	@Override
	public boolean hasTag(long groupId, String name) {
		AssetTag tag = fetchTag(groupId, name);

		if (tag != null) {
			return true;
		}

		return false;
	}

	/**
	 * Increments the number of assets to which the asset tag has been applied.
	 *
	 * @param  tagId the primary key of the asset tag
	 * @param  classNameId the class name ID of the entity to which the asset
	 *         tag is being applied
	 * @return the asset tag
	 * @throws PortalException if a asset tag with the primary key could not be
	 *         found or if a portal exception occurred
	 */
	@Override
	public AssetTag incrementAssetCount(long tagId, long classNameId)
		throws PortalException {

		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		tag.setAssetCount(tag.getAssetCount() + 1);

		assetTagPersistence.update(tag);

		assetTagStatsLocalService.updateTagStats(tagId, classNameId);

		return tag;
	}

	/**
	 * Replaces all occurrences of the first asset tag with the second asset tag
	 * and deletes the first asset tag.
	 *
	 * @param  fromTagId the primary key of the asset tag to be replaced
	 * @param  toTagId the primary key of the asset tag to apply to the asset
	 *         entries of the other asset tag
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void mergeTags(long fromTagId, long toTagId) throws PortalException {
		List<AssetEntry> entries = assetTagPersistence.getAssetEntries(
			fromTagId);

		assetTagPersistence.addAssetEntries(toTagId, entries);

		deleteTag(fromTagId);

		for (AssetEntry entry : entries) {
			incrementAssetCount(toTagId, entry.getClassNameId());
		}
	}

	/**
	 * Returns the asset tags in the group whose names match the pattern.
	 *
	 * @param  groupId the primary key of the group
	 * @param  name the pattern to match
	 * @param  start the lower bound of the range of asset tags
	 * @param  end the upper bound of the range of asset tags (not inclusive)
	 * @return the asset tags in the group whose names match the pattern
	 */
	@Override
	public List<AssetTag> search(
		long groupId, String name, int start, int end) {

		return search(new long[] {groupId}, name, start, end);
	}

	/**
	 * Returns the asset tags in the groups whose names match the pattern.
	 *
	 * @param  groupIds the primary keys of the groups
	 * @param  name the pattern to match
	 * @param  start the lower bound of the range of asset tags
	 * @param  end the upper bound of the range of asset tags (not inclusive)
	 * @return the asset tags in the groups whose names match the pattern
	 */
	@Override
	public List<AssetTag> search(
		long[] groupIds, String name, int start, int end) {

		return assetTagPersistence.findByG_LikeN(
			groupIds, name, start, end, new AssetTagNameComparator());
	}

	@Override
	public AssetTag updateTag(
			long userId, long tagId, String name, ServiceContext serviceContext)
		throws PortalException {

		// Tag

		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		String oldName = tag.getName();

		name = name.trim();
		name = StringUtil.toLowerCase(name);

		if (!name.equals(tag.getName()) && hasTag(tag.getGroupId(), name)) {
			throw new DuplicateTagException(
				"A tag with the name " + name + " already exists");
		}

		if (!tag.getName().equals(name)) {
			AssetTag existingAssetTag = fetchTag(tag.getGroupId(), name);

			if ((existingAssetTag != null) &&
				(existingAssetTag.getTagId() != tagId)) {

				throw new DuplicateTagException(
					"A tag with the name " + name + " already exists");
			}
		}

		validate(name);

		tag.setName(name);

		assetTagPersistence.update(tag);

		// Indexer

		if (!oldName.equals(name)) {
			List<AssetEntry> entries = assetTagPersistence.getAssetEntries(
				tag.getTagId());

			assetEntryLocalService.reindex(entries);
		}

		return tag;
	}

	protected String[] getTagNames(List<AssetTag>tags) {
		return ListUtil.toArray(tags, AssetTag.NAME_ACCESSOR);
	}

	protected void validate(String name) throws PortalException {
		if (!AssetUtil.isValidWord(name)) {
			throw new AssetTagException(
				StringUtil.merge(
					AssetUtil.INVALID_CHARACTERS, StringPool.SPACE),
				AssetTagException.INVALID_CHARACTER);
		}
	}

}