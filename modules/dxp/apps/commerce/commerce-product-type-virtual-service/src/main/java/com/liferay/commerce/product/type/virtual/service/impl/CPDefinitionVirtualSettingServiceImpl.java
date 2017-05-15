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

package com.liferay.commerce.product.type.virtual.service.impl;

import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.base.CPDefinitionVirtualSettingServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionVirtualSettingServiceImpl
	extends CPDefinitionVirtualSettingServiceBaseImpl {

	@Override
	public CPDefinitionVirtualSetting addCPDefinitionVirtualSetting(
			long cpDefinitionId, boolean useFileEntry, long fileEntryId,
			String url, String activationStatus, long duration, int maxUsages,
			boolean useSampleFileEntry, long sampleFileEntryId,
			String sampleUrl, boolean termsOfUseRequired,
			Map<Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleId, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionVirtualSettingLocalService.
			addCPDefinitionVirtualSetting(
				cpDefinitionId, useFileEntry, fileEntryId, url,
				activationStatus, duration, maxUsages, useSampleFileEntry,
				sampleFileEntryId, sampleUrl, termsOfUseRequired,
				termsOfUseContentMap, termsOfUseJournalArticleId,
				serviceContext);
	}

	@Override
	public CPDefinitionVirtualSetting deleteCPDefinitionVirtualSetting(
			CPDefinitionVirtualSetting cpDefinitionVirtualSetting)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(),
			cpDefinitionVirtualSetting.getCPDefinitionId(), ActionKeys.UPDATE);

		return cpDefinitionVirtualSettingLocalService.
			deleteCPDefinitionVirtualSetting(cpDefinitionVirtualSetting);
	}

	@Override
	public CPDefinitionVirtualSetting fetchCPDefinitionVirtualSetting(
			long cpDefinitionVirtualSettingId)
		throws PortalException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			cpDefinitionVirtualSettingLocalService.
				fetchCPDefinitionVirtualSetting(cpDefinitionVirtualSettingId);

		if (cpDefinitionVirtualSetting != null) {
			CPDefinitionPermission.check(
				getPermissionChecker(),
				cpDefinitionVirtualSetting.getCPDefinitionId(),
				ActionKeys.UPDATE);
		}

		return cpDefinitionVirtualSetting;
	}

	@Override
	public CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
			long cpDefinitionVirtualSettingId, boolean useFileEntry,
			long fileEntryId, String url, String activationStatus,
			long duration, int maxUsages, boolean useSampleFileEntry,
			long sampleFileEntryId, String sampleUrl,
			boolean termsOfUseRequired,
			Map<Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleId, ServiceContext serviceContext)
		throws PortalException {

		fetchCPDefinitionVirtualSetting(cpDefinitionVirtualSettingId);

		return cpDefinitionVirtualSettingLocalService.
			updateCPDefinitionVirtualSetting(
				cpDefinitionVirtualSettingId, useFileEntry, fileEntryId, url,
				activationStatus, duration, maxUsages, useSampleFileEntry,
				sampleFileEntryId, sampleUrl, termsOfUseRequired,
				termsOfUseContentMap, termsOfUseJournalArticleId,
				serviceContext);
	}

}