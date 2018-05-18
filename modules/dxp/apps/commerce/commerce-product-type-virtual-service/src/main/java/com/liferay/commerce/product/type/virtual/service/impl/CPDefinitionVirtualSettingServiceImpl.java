/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
 * @author Andrea Di Giorgi
 */
public class CPDefinitionVirtualSettingServiceImpl
	extends CPDefinitionVirtualSettingServiceBaseImpl {

	@Override
	public CPDefinitionVirtualSetting addCPDefinitionVirtualSetting(
			long cpDefinitionId, long fileEntryId, String url,
			String activationStatus, long duration, int maxUsages,
			boolean useSample, long sampleFileEntryId, String sampleUrl,
			boolean termsOfUseRequired,
			Map<Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePrimKey,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionVirtualSettingLocalService.
			addCPDefinitionVirtualSetting(
				cpDefinitionId, fileEntryId, url, activationStatus, duration,
				maxUsages, useSample, sampleFileEntryId, sampleUrl,
				termsOfUseRequired, termsOfUseContentMap,
				termsOfUseJournalArticleResourcePrimKey, serviceContext);
	}

	@Override
	public CPDefinitionVirtualSetting
			fetchCPDefinitionVirtualSettingByCPDefinitionId(long cpDefinitionId)
		throws PortalException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			cpDefinitionVirtualSettingLocalService.
				fetchCPDefinitionVirtualSettingByCPDefinitionId(cpDefinitionId);

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
			long cpDefinitionVirtualSettingId, long fileEntryId, String url,
			String activationStatus, long duration, int maxUsages,
			boolean useSample, long sampleFileEntryId, String sampleUrl,
			boolean termsOfUseRequired,
			Map<Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePrimKey,
			ServiceContext serviceContext)
		throws PortalException {

		fetchCPDefinitionVirtualSettingByCPDefinitionId(
			cpDefinitionVirtualSettingId);

		return cpDefinitionVirtualSettingLocalService.
			updateCPDefinitionVirtualSetting(
				cpDefinitionVirtualSettingId, fileEntryId, url,
				activationStatus, duration, maxUsages, useSample,
				sampleFileEntryId, sampleUrl, termsOfUseRequired,
				termsOfUseContentMap, termsOfUseJournalArticleResourcePrimKey,
				serviceContext);
	}

}