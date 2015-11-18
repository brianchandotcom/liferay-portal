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

import com.liferay.portal.kernel.cache.ThreadLocalCachable;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.asset.DuplicateTagException;
import com.liferay.portlet.asset.NoSuchTagException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetTagConstants;
import com.liferay.portlet.asset.model.AssetTagProperty;
import com.liferay.portlet.asset.service.base.AssetTagLocalServiceBaseImpl;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.portlet.social.util.SocialCounterPeriodUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	 * @param  name the asset tag's name
	 * @param  tagProperties the tag's properties
	 * @param  serviceContext the service context
	 * @return the asset tag that was added
	 * @throws PortalException if a user with the primary key could not be
	 *         found, if an asset tag already exists with the name, or if a
	 *         portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetTag addTag(
			long userId, String name, String[] tagProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Tag

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();

		if (tagProperties == null) {
			tagProperties = new String[0];
		}

		Date now = new Date();

		long tagId = counterLocalService.increment();

		AssetTag tag = assetTagPersistence.create(tagId);

		tag.setGroupId(groupId);
		tag.setCompanyId(user.getCompanyId());
		tag.setUserId(user.getUserId());
		tag.setUserName(user.getFullName());
		tag.setCreateDate(now);
		tag.setModifiedDate(now);

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
			addTagResources(
				tag, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Properties

		for (int i = 0; i < tagProperties.length; i++) {
			String[] tagProperty = StringUtil.split(
				tagProperties[i],
				AssetTagConstants.PROPERTY_KEY_VALUE_SEPARATOR);

			if (tagProperty.length <= 1) {
				tagProperty = StringUtil.split(
					tagProperties[i], CharPool.COLON);
			}

			String key = StringPool.BLANK;

			if (tagProperty.length > 0) {
				key = GetterUtil.getString(tagProperty[0]);
			}

			String value = StringPool.BLANK;

			if (tagProperty.length > 1) {
				value = GetterUtil.getString(tagProperty[1]);
			}

			if (Validator.isNotNull(key)) {
				assetTagPropertyLocalService.addTagProperty(
					userId, tagId, key, value);
			}
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addTagResources(
			AssetTag tag, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			tag.getCompanyId(), tag.getGroupId(), tag.getUserId(),
			AssetTag.class.getName(), tag.getTagId(), false,
			addGroupPermissions, addGuestPermissions);
	}

	/**
	 * Adds resources for the asset tag using the group and guest permissions.
	 *
	 * @param  tag the asset tag for which to add resources
	 * @param  groupPermissions the group permissions to be applied
	 * @param  guestPermissions the guest permissions to be applied
	 * @throws PortalException if resources could not be added for the asset tag
	 *         or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addTagResources(
			AssetTag tag, String[] groupPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			tag.getCompanyId(), tag.getGroupId(), tag.getUserId(),
			AssetTag.class.getName(), tag.getTagId(), groupPermissions,
			guestPermissions);
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetTag> checkTags(long userId, Group group, String[] names)
		throws PortalException, SystemException {

		List<AssetTag> tags = new ArrayList<AssetTag>();

		for (String name : names) {
			AssetTag tag = null;

			try {
				tag = getTag(group.getGroupId(), name);
			}
			catch (NoSuchTagException nste1) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setAddGroupPermissions(true);
				serviceContext.setAddGuestPermissions(true);
				serviceContext.setScopeGroupId(group.getGroupId());

				tag = addTag(
					userId, name, PropsValues.ASSET_TAG_PROPERTIES_DEFAULT,
					serviceContext);

				Group companyGroup = groupLocalService.getCompanyGroup(
					group.getCompanyId());

				try {
					AssetTag companyGroupTag = getTag(
						companyGroup.getGroupId(), name);

					List<AssetTagProperty> tagProperties =
						assetTagPropertyLocalService.getTagProperties(
							companyGroupTag.getTagId());

					for (AssetTagProperty tagProperty : tagProperties) {
						assetTagPropertyLocalService.addTagProperty(
							userId, tag.getTagId(), tagProperty.getKey(),
							tagProperty.getValue());
					}
				}
				catch (NoSuchTagException nste2) {
				}
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
	 * @throws PortalException if a matching group could not be found or if a
	 *         portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void checkTags(long userId, long groupId, String[] names)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		checkTags(userId, group, names);
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetTag decrementAssetCount(long tagId, long classNameId)
		throws PortalException, SystemException {

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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteGroupTags(long groupId)
		throws PortalException, SystemException {

		List<AssetTag> tags = getGroupTags(groupId);

		for (AssetTag tag : tags) {
			deleteTag(tag);
		}
	}

	/**
	 * Deletes the asset tag.
	 *
	 * @param  tag the asset tag to be deleted
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteTag(AssetTag tag)
		throws PortalException, SystemException {

		// Entries

		List<AssetEntry> entries = assetTagPersistence.getAssetEntries(
			tag.getTagId());

		// Tag

		assetTagPersistence.remove(tag);

		// Resources

		resourceLocalService.deleteResource(
			tag.getCompanyId(), AssetTag.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, tag.getTagId());

		// Properties

		assetTagPropertyLocalService.deleteTagProperties(tag.getTagId());

		// Indexer

		assetEntryLocalService.reindex(entries);
	}

	/**
	 * Deletes the asset tag.
	 *
	 * @param  tagId the primary key of the asset tag
	 * @throws PortalException if no asset tag could be found with the primary
	 *         key or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteTag(long tagId) throws PortalException, SystemException {
		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		deleteTag(tag);
	}

	/**
	 * Returns the asset tags of the asset entry.
	 *
	 * @param  entryId the primary key of the asset entry
	 * @return the asset tags of the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetTag> getEntryTags(long entryId) throws SystemException {
		return assetEntryPersistence.getAssetTags(entryId);
	}

	/**
	 * Returns the asset tags in the groups.
	 *
	 * @param  groupIds the primary keys of the groups
	 * @return the asset tags in the groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetTag> getGroupsTags(long[] groupIds)
		throws SystemException {

		List<AssetTag> groupsTags = new ArrayList<AssetTag>();

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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetTag> getGroupTags(long groupId) throws SystemException {
		return assetTagPersistence.findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the asset tags in the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  start the lower bound of the range of asset tags
	 * @param  end the upper bound of the range of asset tags (not inclusive)
	 * @return the range of matching asset tags
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetTag> getGroupTags(long groupId, int start, int end)
		throws SystemException {

		return assetTagPersistence.findByGroupId(groupId, start, end);
	}

	/**
	 * Returns the number of asset tags in the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return the number of asset tags in the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getGroupTagsCount(long groupId) throws SystemException {
		return assetTagPersistence.countByGroupId(groupId);
	}

	@Override
	public List<AssetTag> getSocialActivityCounterOffsetTags(
			long groupId, String socialActivityCounterName, int startOffset,
			int endOffset)
		throws SystemException {

		int startPeriod = SocialCounterPeriodUtil.getStartPeriod(startOffset);
		int endPeriod = SocialCounterPeriodUtil.getEndPeriod(endOffset);

		return getSocialActivityCounterPeriodTags(
			groupId, socialActivityCounterName, startPeriod, endPeriod);
	}

	@Override
	public List<AssetTag> getSocialActivityCounterPeriodTags(
			long groupId, String socialActivityCounterName, int startPeriod,
			int endPeriod)
		throws SystemException {

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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetTag getTag(long tagId) throws PortalException, SystemException {
		return assetTagPersistence.findByPrimaryKey(tagId);
	}

	/**
	 * Returns the asset tag with the name in the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  name the name of the asset tag
	 * @return the asset tag with the name in the group
	 * @throws PortalException if a matching asset tag could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetTag getTag(long groupId, String name)
		throws PortalException, SystemException {

		return assetTagFinder.findByG_N(groupId, name);
	}

	/**
	 * Returns the primary keys of the asset tags with the names in the group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  names the names of the asset tags
	 * @return the primary keys of the asset tags with the names in the group
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long[] getTagIds(long groupId, String[] names)
		throws PortalException, SystemException {

		List<Long> tagIds = new ArrayList<Long>(names.length);

		for (String name : names) {
			try {
				AssetTag tag = getTag(groupId, name);

				tagIds.add(tag.getTagId());
			}
			catch (NoSuchTagException nste) {
			}
		}

		return ArrayUtil.toArray(tagIds.toArray(new Long[tagIds.size()]));
	}

	/**
	 * Returns the primary keys of the asset tags with the name in the groups.
	 *
	 * @param  groupIds the primary keys of the groups
	 * @param  name the name of the asset tags
	 * @return the primary keys of the asset tags with the name in the groups
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long[] getTagIds(long[] groupIds, String name)
		throws PortalException, SystemException {

		List<Long> tagIds = new ArrayList<Long>(groupIds.length);

		for (long groupId : groupIds) {
			try {
				AssetTag tag = getTag(groupId, name);

				tagIds.add(tag.getTagId());
			}
			catch (NoSuchTagException nste) {
			}
		}

		return ArrayUtil.toArray(tagIds.toArray(new Long[tagIds.size()]));
	}

	/**
	 * Returns the primary keys of the asset tags with the names in the groups.
	 *
	 * @param  groupIds the primary keys of the groups
	 * @param  names the names of the asset tags
	 * @return the primary keys of the asset tags with the names in the groups
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long[] getTagIds(long[] groupIds, String[] names)
		throws PortalException, SystemException {

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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public String[] getTagNames() throws SystemException {
		return getTagNames(getTags());
	}

	/**
	 * Returns the names of the asset tags of the entity.
	 *
	 * @param  classNameId the class name ID of the entity
	 * @param  classPK the primary key of the entity
	 * @return the names of the asset tags of the entity
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public String[] getTagNames(long classNameId, long classPK)
		throws SystemException {

		return getTagNames(getTags(classNameId, classPK));
	}

	/**
	 * Returns the names of the asset tags of the entity
	 *
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @return the names of the asset tags of the entity
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public String[] getTagNames(String className, long classPK)
		throws SystemException {

		return getTagNames(getTags(className, classPK));
	}

	/**
	 * Returns all the asset tags.
	 *
	 * @return the asset tags
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetTag> getTags() throws SystemException {
		return assetTagPersistence.findAll();
	}

	/**
	 * Returns the asset tags of the entity.
	 *
	 * @param  classNameId the class name ID of the entity
	 * @param  classPK the primary key of the entity
	 * @return the asset tags of the entity
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetTag> getTags(long classNameId, long classPK)
		throws SystemException {

		AssetEntry entry = assetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (entry == null) {
			return Collections.emptyList();
		}

		return assetEntryPersistence.getAssetTags(entry.getEntryId());
	}

	@Override
	public List<AssetTag> getTags(long groupId, long classNameId, String name)
		throws SystemException {

		return assetTagFinder.findByG_C_N(
			groupId, classNameId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	@Override
	public List<AssetTag> getTags(
			long groupId, long classNameId, String name, int start, int end)
		throws SystemException {

		return assetTagFinder.findByG_C_N(
			groupId, classNameId, name, start, end, null);
	}

	/**
	 * Returns the asset tags of the entity.
	 *
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @return the asset tags of the entity
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@ThreadLocalCachable
	public List<AssetTag> getTags(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getTags(classNameId, classPK);
	}

	@Override
	public int getTagsSize(long groupId, long classNameId, String name)
		throws SystemException {

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
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean hasTag(long groupId, String name)
		throws PortalException, SystemException {

		try {
			getTag(groupId, name);

			return true;
		}
		catch (NoSuchTagException nste) {
			return false;
		}
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetTag incrementAssetCount(long tagId, long classNameId)
		throws PortalException, SystemException {

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
	 * @param  overrideProperties whether to override the properties of the
	 *         second asset tag with the properties of the first asset tag
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void mergeTags(
			long fromTagId, long toTagId, boolean overrideProperties)
		throws PortalException, SystemException {

		List<AssetEntry> entries = assetTagPersistence.getAssetEntries(
			fromTagId);

		assetTagPersistence.addAssetEntries(toTagId, entries);

		List<AssetTagProperty> tagProperties =
			assetTagPropertyPersistence.findByTagId(fromTagId);

		for (AssetTagProperty fromTagProperty : tagProperties) {
			AssetTagProperty toTagProperty =
				assetTagPropertyPersistence.fetchByT_K(
					toTagId, fromTagProperty.getKey());

			if (overrideProperties && (toTagProperty != null)) {
				toTagProperty.setValue(fromTagProperty.getValue());

				assetTagPropertyPersistence.update(toTagProperty);
			}
			else if (toTagProperty == null) {
				fromTagProperty.setTagId(toTagId);

				assetTagPropertyPersistence.update(fromTagProperty);
			}
		}

		deleteTag(fromTagId);
	}

	/**
	 * Returns the asset tags in the group whose names match the pattern and the
	 * properties.
	 *
	 * @param  groupId the primary key of the group
	 * @param  name the pattern to match
	 * @param  tagProperties the properties to match
	 * @param  start the lower bound of the range of asset tags
	 * @param  end the upper bound of the range of asset tags (not inclusive)
	 * @return the asset tags in the group whose names match the pattern
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetTag> search(
			long groupId, String name, String[] tagProperties, int start,
			int end)
		throws SystemException {

		return search(new long[] {groupId}, name, tagProperties, start, end);
	}

	/**
	 * Returns the asset tags in the groups whose names match the pattern and
	 * the properties.
	 *
	 * @param  groupIds the primary keys of the groups
	 * @param  name the pattern to match
	 * @param  tagProperties the properties to match
	 * @param  start the lower bound of the range of asset tags
	 * @param  end the upper bound of the range of asset tags (not inclusive)
	 * @return the asset tags in the groups whose names match the pattern
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetTag> search(
			long[] groupIds, String name, String[] tagProperties, int start,
			int end)
		throws SystemException {

		return assetTagFinder.findByG_N_P(
			groupIds, name, tagProperties, start, end, null);
	}

	@Override
	public AssetTag updateTag(
			long userId, long tagId, String name, String[] tagProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Tag

		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		String oldName = tag.getName();

		tag.setModifiedDate(new Date());

		name = name.trim();
		name = StringUtil.toLowerCase(name);

		if (tagProperties == null) {
			tagProperties = new String[0];
		}

		if (!name.equals(tag.getName()) && hasTag(tag.getGroupId(), name)) {
			throw new DuplicateTagException(
				"A tag with the name " + name + " already exists");
		}

		if (!tag.getName().equals(name)) {
			try {
				AssetTag existingAssetTag = getTag(tag.getGroupId(), name);

				if (existingAssetTag.getTagId() != tagId) {
					throw new DuplicateTagException(
						"A tag with the name " + name + " already exists");
				}
			}
			catch (NoSuchTagException nste) {
			}
		}

		validate(name);

		tag.setName(name);

		assetTagPersistence.update(tag);

		// Properties

		List<AssetTagProperty> oldTagProperties =
			assetTagPropertyPersistence.findByTagId(tagId);

		Set<String> newKeys = new HashSet<String>();

		for (int i = 0; i < tagProperties.length; i++) {
			String[] tagProperty = StringUtil.split(
				tagProperties[i],
				AssetTagConstants.PROPERTY_KEY_VALUE_SEPARATOR);

			if (tagProperty.length <= 1) {
				tagProperty = StringUtil.split(
					tagProperties[i], CharPool.COLON);
			}

			String key = StringPool.BLANK;

			if (tagProperty.length > 0) {
				key = GetterUtil.getString(tagProperty[0]);
			}

			String value = StringPool.BLANK;

			if (tagProperty.length > 1) {
				value = GetterUtil.getString(tagProperty[1]);
			}

			if (Validator.isNotNull(key)) {
				newKeys.add(key);

				AssetTagProperty keyTagProperty =
					assetTagPropertyPersistence.fetchByT_K(tagId, key);

				if (keyTagProperty == null) {
					assetTagPropertyLocalService.addTagProperty(
						userId, tagId, key, value);
				}
				else {
					assetTagPropertyLocalService.updateTagProperty(
						keyTagProperty.getTagPropertyId(), key, value);
				}
			}
		}

		for (AssetTagProperty tagProperty : oldTagProperties) {
			if (!newKeys.contains(tagProperty.getKey())) {
				assetTagPropertyLocalService.deleteTagProperty(tagProperty);
			}
		}

		// Indexer

		if (!oldName.equals(name)) {
			List<AssetEntry> entries = assetTagPersistence.getAssetEntries(
				tag.getTagId());

			assetEntryLocalService.reindex(entries);
		}

		return tag;
	}

	protected String[] getTagNames(List<AssetTag>tags) {
		return StringUtil.split(
			ListUtil.toString(tags, AssetTag.NAME_ACCESSOR));
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