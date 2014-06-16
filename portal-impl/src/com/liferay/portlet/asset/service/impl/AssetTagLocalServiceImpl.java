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

	@Override
	public AssetTag addTag(
			long userId, String name, String[] tagProperties,
			ServiceContext serviceContext)
		throws PortalException {

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

	@Override
	public void addTagResources(
			AssetTag tag, String[] groupPermissions, String[] guestPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			tag.getCompanyId(), tag.getGroupId(), tag.getUserId(),
			AssetTag.class.getName(), tag.getTagId(), groupPermissions,
			guestPermissions);
	}

	/**
	 * Returns the tags matching the group and names, creating new tags with the
	 * names if the group doesn't already have them.
	 *
	 * <p>
	 * For each name, if a tag with that name doesn't already exist for the
	 * group, this method creates a new tag with that name for the group. If a
	 * tag with that name already exists in the company group, this method
	 * copies that company group's tag's properties to the group's new tag.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @param  group ID the primary key of the tag's group
	 * @param  names the tag names
	 * @return the tags matching the group and names and new tags matching the
	 *         names that don't already exist for the group
	 * @throws PortalException if a matching group could not be found, if the
	 *         tag's key or value were invalid, or if a portal exception
	 *         occurred
	 */
	@Override
	public List<AssetTag> checkTags(long userId, Group group, String[] names)
		throws PortalException {

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

	@Override
	public void checkTags(long userId, long groupId, String[] names)
		throws PortalException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		checkTags(userId, group, names);
	}

	@Override
	public AssetTag decrementAssetCount(long tagId, long classNameId)
		throws PortalException {

		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		tag.setAssetCount(Math.max(0, tag.getAssetCount() - 1));

		assetTagPersistence.update(tag);

		assetTagStatsLocalService.updateTagStats(tagId, classNameId);

		return tag;
	}

	@Override
	public void deleteGroupTags(long groupId) throws PortalException {
		List<AssetTag> tags = getGroupTags(groupId);

		for (AssetTag tag : tags) {
			deleteTag(tag);
		}
	}

	@Override
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

		// Properties

		assetTagPropertyLocalService.deleteTagProperties(tag.getTagId());

		// Indexer

		assetEntryLocalService.reindex(entries);
	}

	@Override
	public void deleteTag(long tagId) throws PortalException {
		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		deleteTag(tag);
	}

	@Override
	public List<AssetTag> getEntryTags(long entryId) {
		return assetEntryPersistence.getAssetTags(entryId);
	}

	@Override
	public List<AssetTag> getGroupsTags(long[] groupIds) {
		List<AssetTag> groupsTags = new ArrayList<AssetTag>();

		for (long groupId : groupIds) {
			List<AssetTag> groupTags = getGroupTags(groupId);

			groupsTags.addAll(groupTags);
		}

		return groupsTags;
	}

	@Override
	public List<AssetTag> getGroupTags(long groupId) {
		return assetTagPersistence.findByGroupId(groupId);
	}

	@Override
	public List<AssetTag> getGroupTags(long groupId, int start, int end) {
		return assetTagPersistence.findByGroupId(groupId, start, end);
	}

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

	@Override
	public AssetTag getTag(long tagId) throws PortalException {
		return assetTagPersistence.findByPrimaryKey(tagId);
	}

	@Override
	public AssetTag getTag(long groupId, String name) throws PortalException {
		return assetTagFinder.findByG_N(groupId, name);
	}

	@Override
	public long[] getTagIds(long groupId, String[] names)
		throws PortalException {

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

	@Override
	public long[] getTagIds(long[] groupIds, String name)
		throws PortalException {

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

	@Override
	public long[] getTagIds(long[] groupIds, String[] names)
		throws PortalException {

		long[] tagsIds = new long[0];

		for (long groupId : groupIds) {
			tagsIds = ArrayUtil.append(tagsIds, getTagIds(groupId, names));
		}

		return tagsIds;
	}

	@Override
	public String[] getTagNames() {
		return getTagNames(getTags());
	}

	@Override
	public String[] getTagNames(long classNameId, long classPK) {
		return getTagNames(getTags(classNameId, classPK));
	}

	@Override
	public String[] getTagNames(String className, long classPK) {
		return getTagNames(getTags(className, classPK));
	}

	@Override
	public List<AssetTag> getTags() {
		return assetTagPersistence.findAll();
	}

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

	@Override
	public boolean hasTag(long groupId, String name) throws PortalException {
		try {
			getTag(groupId, name);

			return true;
		}
		catch (NoSuchTagException nste) {
			return false;
		}
	}

	@Override
	public AssetTag incrementAssetCount(long tagId, long classNameId)
		throws PortalException {

		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		tag.setAssetCount(tag.getAssetCount() + 1);

		assetTagPersistence.update(tag);

		assetTagStatsLocalService.updateTagStats(tagId, classNameId);

		return tag;
	}

	@Override
	public void mergeTags(
			long fromTagId, long toTagId, boolean overrideProperties)
		throws PortalException {

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

	@Override
	public List<AssetTag> search(
		long groupId, String name, String[] tagProperties, int start, int end) {

		return search(new long[] {groupId}, name, tagProperties, start, end);
	}

	@Override
	public List<AssetTag> search(
		long[] groupIds, String name, String[] tagProperties, int start,
		int end) {

		return assetTagFinder.findByG_N_P(
			groupIds, name, tagProperties, start, end, null);
	}

	@Override
	public AssetTag updateTag(
			long userId, long tagId, String name, String[] tagProperties,
			ServiceContext serviceContext)
		throws PortalException {

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

		for (AssetTagProperty tagProperty : oldTagProperties) {
			assetTagPropertyLocalService.deleteTagProperty(tagProperty);
		}

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