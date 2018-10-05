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

package com.liferay.asset.list.service.impl;

import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.exception.AssetListEntryTitleException;
import com.liferay.asset.list.exception.DuplicateAssetListEntryTitleException;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.base.AssetListEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Jürgen Kappler
 */
public class AssetListEntryLocalServiceImpl
	extends AssetListEntryLocalServiceBaseImpl {

	@Override
	public void addAssetEntrySelection(
			long assetListEntryId, long assetEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryPersistence.findByPrimaryKey(assetListEntryId);

		if (Objects.equals(
				assetListEntry.getType(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC)) {

			throw new PortalException();
		}

		assetListEntry.setModifiedDate(new Date());

		assetListEntryPersistence.update(assetListEntry);

		assetListEntryAssetEntryRelLocalService.addAssetListEntryAssetEntryRel(
			assetListEntryId, assetEntryId, serviceContext);
	}

	@Override
	public AssetListEntry addAssetListEntry(
			long userId, long groupId, String title, int type,
			ServiceContext serviceContext)
		throws PortalException {

		return addAssetListEntry(
			userId, groupId, title, type, null, serviceContext);
	}

	@Override
	public AssetListEntry addAssetListEntry(
			long userId, long groupId, String title, int type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException {

		_validateTitle(groupId, title);

		// Asset List entry

		User user = userLocalService.getUser(userId);

		long assetListEntryId = counterLocalService.increment();

		AssetListEntry assetListEntry = assetListEntryPersistence.create(
			assetListEntryId);

		assetListEntry.setUuid(serviceContext.getUuid());
		assetListEntry.setGroupId(groupId);
		assetListEntry.setCompanyId(user.getCompanyId());
		assetListEntry.setUserId(user.getUserId());
		assetListEntry.setUserName(user.getFullName());
		assetListEntry.setCreateDate(serviceContext.getCreateDate(new Date()));
		assetListEntry.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		assetListEntry.setTypeSettings(typeSettings);
		assetListEntry.setTitle(title);
		assetListEntry.setType(type);

		assetListEntryPersistence.update(assetListEntry);

		// Resources

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		resourceLocalService.addModelResources(assetListEntry, serviceContext);

		return assetListEntry;
	}

	@Override
	public AssetListEntry addDynamicAssetListEntry(
			long userId, long groupId, String title, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		AssetListEntry assetListEntry = addAssetListEntry(
			userId, groupId, title, AssetListEntryTypeConstants.TYPE_DYNAMIC,
			serviceContext);

		assetListEntry.setTypeSettings(typeSettings);

		return assetListEntryPersistence.update(assetListEntry);
	}

	@Override
	public AssetListEntry addManualAssetListEntry(
			long userId, long groupId, String title, long[] assetEntryIds,
			ServiceContext serviceContext)
		throws PortalException {

		AssetListEntry assetListEntry = addAssetListEntry(
			userId, groupId, title, AssetListEntryTypeConstants.TYPE_MANUAL,
			serviceContext);

		for (long assetEntryId : assetEntryIds) {
			addAssetEntrySelection(
				assetListEntry.getAssetListEntryId(), assetEntryId,
				serviceContext);
		}

		return assetListEntry;
	}

	@Override
	public void deleteAssetEntrySelection(long assetListEntryId, int position)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryPersistence.findByPrimaryKey(assetListEntryId);

		if (Objects.equals(
				assetListEntry.getType(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC)) {

			throw new PortalException();
		}

		assetListEntry.setModifiedDate(new Date());

		assetListEntryPersistence.update(assetListEntry);

		assetListEntryAssetEntryRelLocalService.
			deleteAssetListEntryAssetEntryRel(assetListEntryId, position);
	}

	@Override
	public AssetListEntry deleteAssetListEntry(AssetListEntry assetListEntry)
		throws PortalException {

		assetListEntryLocalService.deleteAssetListEntry(
			assetListEntry.getAssetListEntryId());

		return assetListEntry;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public AssetListEntry deleteAssetListEntry(long assetListEntryId)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryPersistence.findByPrimaryKey(assetListEntryId);

		// Asset List Entry Rels

		assetListEntryAssetEntryRelPersistence.removeByAssetListEntryId(
			assetListEntryId);

		// Resources

		resourceLocalService.deleteResource(
			assetListEntry, ResourceConstants.SCOPE_INDIVIDUAL);

		return assetListEntryPersistence.remove(assetListEntryId);
	}

	@Override
	public List<AssetListEntry> getAssetListEntries(long groupId) {
		return assetListEntryPersistence.findByGroupId(groupId);
	}

	@Override
	public void moveAssetEntrySelection(
			long assetListEntryId, int position, int newPosition)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryPersistence.findByPrimaryKey(assetListEntryId);

		if (Objects.equals(
				assetListEntry.getType(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC)) {

			throw new PortalException();
		}

		assetListEntry.setModifiedDate(new Date());

		assetListEntryPersistence.update(assetListEntry);

		assetListEntryAssetEntryRelLocalService.moveAssetListEntryAssetEntryRel(
			assetListEntryId, position, newPosition);
	}

	@Override
	public AssetListEntry updateAssetListEntry(
			long assetListEntryId, String title)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryPersistence.findByPrimaryKey(assetListEntryId);

		if (Objects.equals(assetListEntry.getTitle(), title)) {
			return assetListEntry;
		}

		_validateTitle(assetListEntry.getGroupId(), title);

		assetListEntry.setModifiedDate(new Date());
		assetListEntry.setTitle(title);

		return assetListEntryPersistence.update(assetListEntry);
	}

	@Override
	public AssetListEntry updateAssetListEntryTypeSettings(
			long assetListEntryId, String typeSettings)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryPersistence.findByPrimaryKey(assetListEntryId);

		assetListEntry.setModifiedDate(new Date());
		assetListEntry.setTypeSettings(typeSettings);

		return assetListEntryPersistence.update(assetListEntry);
	}

	@Override
	public AssetListEntry updateAssetListEntryTypeSettingsProperties(
			long assetListEntryId, String typeSettingsProperties)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryPersistence.findByPrimaryKey(assetListEntryId);

		UnicodeProperties existingProperties = new UnicodeProperties();

		existingProperties.fastLoad(assetListEntry.getTypeSettings());

		UnicodeProperties newProperties = new UnicodeProperties();

		newProperties.fastLoad(typeSettingsProperties);

		existingProperties.putAll(newProperties);

		assetListEntry.setModifiedDate(new Date());
		assetListEntry.setTypeSettings(existingProperties.toString());

		return assetListEntryPersistence.update(assetListEntry);
	}

	private void _validateTitle(long groupId, String title)
		throws PortalException {

		if (Validator.isNull(title)) {
			throw new AssetListEntryTitleException("Title is null");
		}

		AssetListEntry assetListEntry = assetListEntryPersistence.fetchByG_T(
			groupId, title);

		if (assetListEntry != null) {
			throw new DuplicateAssetListEntryTitleException();
		}
	}

}