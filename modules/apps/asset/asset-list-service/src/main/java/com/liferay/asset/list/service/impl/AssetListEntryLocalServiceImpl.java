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
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.service.base.AssetListEntryLocalServiceBaseImpl;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;

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
			long assetListEntryId, long segmentsEntryId, long assetEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		addAssetEntrySelections(
			assetListEntryId, segmentsEntryId, new long[] {assetEntryId},
			serviceContext);
	}

	@Override
	public void addAssetEntrySelections(
			long assetListEntryId, long segmentsEntryId, long[] assetEntryIds,
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

		// Asset list segments entry rel

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			assetListEntrySegmentsEntryRelLocalService.
				fetchAssetListEntrySegmentsEntryRel(
					assetListEntryId, segmentsEntryId);

		if (assetListEntrySegmentsEntryRel == null) {
			assetListEntrySegmentsEntryRelLocalService.
				addAssetListEntrySegmentsEntryRel(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), assetListEntryId,
					segmentsEntryId, StringPool.BLANK, serviceContext);
		}

		for (long assetEntryId : assetEntryIds) {
			assetListEntryAssetEntryRelLocalService.
				addAssetListEntryAssetEntryRel(
					assetListEntryId, segmentsEntryId, assetEntryId,
					serviceContext);
		}
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
		assetListEntry.setTitle(title);
		assetListEntry.setType(type);

		assetListEntryPersistence.update(assetListEntry);

		// Resources

		resourceLocalService.addResources(
			assetListEntry.getCompanyId(), assetListEntry.getGroupId(),
			assetListEntry.getUserId(), AssetListEntry.class.getName(),
			assetListEntry.getPrimaryKey(), false, true, true);

		// Asset list segments entry rel

		if (!ExportImportThreadLocal.isImportInProcess()) {
			SegmentsEntry defaultSegmentsEntry =
				_segmentsEntryLocalService.getDefaultSegmentsEntry(groupId);

			assetListEntrySegmentsEntryRelLocalService.
				addAssetListEntrySegmentsEntryRel(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), assetListEntryId,
					defaultSegmentsEntry.getSegmentsEntryId(), typeSettings,
					serviceContext);
		}

		return assetListEntry;
	}

	@Override
	public AssetListEntry addDynamicAssetListEntry(
			long userId, long groupId, String title, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		return addAssetListEntry(
			userId, groupId, title, AssetListEntryTypeConstants.TYPE_DYNAMIC,
			typeSettings, serviceContext);
	}

	@Override
	public AssetListEntry addManualAssetListEntry(
			long userId, long groupId, String title, long[] assetEntryIds,
			ServiceContext serviceContext)
		throws PortalException {

		AssetListEntry assetListEntry = addAssetListEntry(
			userId, groupId, title, AssetListEntryTypeConstants.TYPE_MANUAL,
			serviceContext);

		SegmentsEntry defaultSegmentsEntry =
			_segmentsEntryLocalService.getDefaultSegmentsEntry(groupId);

		addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(),
			defaultSegmentsEntry.getSegmentsEntryId(), assetEntryIds,
			serviceContext);

		return assetListEntry;
	}

	@Override
	public void deleteAssetEntrySelection(
			long assetListEntryId, long segmentsEntryId, int position)
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

		// Asset list segments entry rel

		assetListEntryAssetEntryRelLocalService.
			deleteAssetListEntryAssetEntryRel(
				assetListEntryId, segmentsEntryId, position);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public AssetListEntry deleteAssetListEntry(AssetListEntry assetListEntry) {
		return assetListEntryPersistence.remove(assetListEntry);
	}

	@Override
	public AssetListEntry deleteAssetListEntry(long assetListEntryId)
		throws PortalException {

		// Asset list entry

		AssetListEntry assetListEntry = getAssetListEntry(assetListEntryId);

		// Resources

		resourceLocalService.deleteResource(
			assetListEntry, ResourceConstants.SCOPE_INDIVIDUAL);

		// Asset list entry rels

		assetListEntryAssetEntryRelPersistence.removeByAssetListEntryId(
			assetListEntryId);

		// Asset list segments entry rels

		assetListEntrySegmentsEntryRelPersistence.removeByAssetListEntryId(
			assetListEntryId);

		return assetListEntryLocalService.deleteAssetListEntry(assetListEntry);
	}

	@Override
	public AssetListEntry deleteAssetListEntry(
			long assetListEntryId, long segmentsEntryId)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryPersistence.findByPrimaryKey(assetListEntryId);

		assetListEntry.setModifiedDate(new Date());

		assetListEntryPersistence.update(assetListEntry);

		// Asset list segments entry rel

		assetListEntrySegmentsEntryRelLocalService.
			deleteAssetListEntrySegmentsEntryRel(
				assetListEntryId, segmentsEntryId);

		return assetListEntry;
	}

	@Override
	public List<AssetListEntry> getAssetListEntries(long groupId) {
		return assetListEntryPersistence.findByGroupId(groupId);
	}

	@Override
	public void moveAssetEntrySelection(
			long assetListEntryId, long segmentsEntryId, int position,
			int newPosition)
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

		// Asset list segments entry rel

		assetListEntryAssetEntryRelLocalService.moveAssetListEntryAssetEntryRel(
			assetListEntryId, segmentsEntryId, position, newPosition);
	}

	@Override
	public AssetListEntry updateAssetListEntry(
			long assetListEntryId, long segmentsEntryId, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryPersistence.findByPrimaryKey(assetListEntryId);

		assetListEntry.setModifiedDate(new Date());

		assetListEntryPersistence.update(assetListEntry);

		// Asset list segments entry rel

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			assetListEntrySegmentsEntryRelLocalService.
				fetchAssetListEntrySegmentsEntryRel(
					assetListEntryId, segmentsEntryId);

		if (assetListEntrySegmentsEntryRel == null) {
			assetListEntrySegmentsEntryRelLocalService.
				addAssetListEntrySegmentsEntryRel(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), assetListEntryId,
					segmentsEntryId, typeSettings, serviceContext);
		}
		else {
			assetListEntrySegmentsEntryRelLocalService.
				updateAssetListEntrySegmentsEntryRelTypeSettings(
					assetListEntryId, segmentsEntryId, typeSettings);
		}

		return assetListEntry;
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
	public void updateAssetListEntryTypeSettings(
			long assetListEntryId, long segmentsEntryId, String typeSettings)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryPersistence.findByPrimaryKey(assetListEntryId);

		assetListEntry.setModifiedDate(new Date());

		assetListEntryPersistence.update(assetListEntry);

		assetListEntrySegmentsEntryRelLocalService.
			updateAssetListEntrySegmentsEntryRelTypeSettings(
				assetListEntryId, segmentsEntryId, typeSettings);
	}

	@Override
	public AssetListEntry updateAssetListEntryTypeSettingsProperties(
			long assetListEntryId, long segmentsEntryId,
			String typeSettingsProperties)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryPersistence.findByPrimaryKey(assetListEntryId);

		assetListEntry.setModifiedDate(new Date());

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			assetListEntrySegmentsEntryRelLocalService.
				getAssetListEntrySegmentsEntryRel(
					assetListEntryId, segmentsEntryId);

		UnicodeProperties existingProperties = new UnicodeProperties();

		existingProperties.fastLoad(
			assetListEntrySegmentsEntryRel.getTypeSettings());

		UnicodeProperties newProperties = new UnicodeProperties();

		newProperties.fastLoad(typeSettingsProperties);

		existingProperties.putAll(newProperties);

		assetListEntrySegmentsEntryRelLocalService.
			updateAssetListEntrySegmentsEntryRelTypeSettings(
				assetListEntryId, segmentsEntryId,
				existingProperties.toString());

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

	@ServiceReference(type = SegmentsEntryLocalService.class)
	private SegmentsEntryLocalService _segmentsEntryLocalService;

}