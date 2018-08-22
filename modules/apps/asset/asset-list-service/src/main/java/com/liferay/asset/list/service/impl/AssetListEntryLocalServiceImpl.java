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

import com.liferay.asset.list.exception.NoSuchEntryException;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryLocalization;
import com.liferay.asset.list.service.base.AssetListEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public class AssetListEntryLocalServiceImpl
	extends AssetListEntryLocalServiceBaseImpl {

	@Override
	public AssetListEntry addAssetListEntry(
			long userId, long groupId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, int type,
			ServiceContext serviceContext)
		throws PortalException {

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
		assetListEntry.setType(type);

		Group group = groupLocalService.getGroup(groupId);

		_updateAssetListEntryLocalizations(
			assetListEntryId, group.getCompanyId(), groupId, titleMap,
			descriptionMap);

		assetListEntryPersistence.update(assetListEntry);

		return assetListEntry;
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
		throws NoSuchEntryException {

		AssetListEntry assetListEntry =
			assetListEntryPersistence.fetchByPrimaryKey(assetListEntryId);

		if (assetListEntry == null) {
			throw new NoSuchEntryException();
		}

		assetListEntryLocalizationPersistence.removeByAssetListEntryId(
			assetListEntryId);

		return assetListEntryPersistence.remove(assetListEntryId);
	}

	@Override
	public AssetListEntry updateAssetListEntry(
			long assetListEntryId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap)
		throws PortalException {

		AssetListEntry assetListEntry =
			assetListEntryPersistence.findByPrimaryKey(assetListEntryId);

		assetListEntry.setModifiedDate(new Date());

		_updateAssetListEntryLocalizations(
			assetListEntryId, assetListEntry.getCompanyId(),
			assetListEntry.getGroupId(), titleMap, descriptionMap);

		assetListEntryPersistence.update(assetListEntry);

		return assetListEntry;
	}

	private void _updateAssetListEntryLocalizations(
		long assetListEntryId, long companyId, long groupId,
		Map<Locale, String> titleMap, Map<Locale, String> descriptionMap) {

		for (Map.Entry<Locale, String> entry : titleMap.entrySet()) {
			String title = entry.getValue();

			String languageId = LocaleUtil.toLanguageId(entry.getKey());

			if (Validator.isNull(title)) {
				continue;
			}

			String description = descriptionMap.get(entry.getKey());

			AssetListEntryLocalization existingAssetListEntryLocalization =
				assetListEntryLocalizationPersistence.
					fetchByAssetListEntryId_LanguageId(
						assetListEntryId, languageId);

			if (existingAssetListEntryLocalization != null) {
				existingAssetListEntryLocalization.setTitle(title);
				existingAssetListEntryLocalization.setDescription(description);

				assetListEntryLocalizationPersistence.update(
					existingAssetListEntryLocalization);

				continue;
			}

			long assetListEntryLocalizationId = counterLocalService.increment(
				AssetListEntryLocalization.class.getName());

			AssetListEntryLocalization assetListEntryLocalization =
				assetListEntryLocalizationPersistence.create(
					assetListEntryLocalizationId);

			assetListEntryLocalization.setCompanyId(companyId);
			assetListEntryLocalization.setAssetListEntryId(assetListEntryId);
			assetListEntryLocalization.setGroupId(groupId);
			assetListEntryLocalization.setTitle(title);
			assetListEntryLocalization.setDescription(description);
			assetListEntryLocalization.setLanguageId(languageId);

			assetListEntryLocalizationPersistence.update(
				assetListEntryLocalization);
		}
	}

}