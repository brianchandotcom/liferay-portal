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

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.base.CPDefinitionVirtualSettingServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CPDefinitionVirtualSetting"
	},
	service = AopService.class
)
public class CPDefinitionVirtualSettingServiceImpl
	extends CPDefinitionVirtualSettingServiceBaseImpl {

	@Override
	public CPDefinitionVirtualSetting addCPDefinitionVirtualSetting(
			String className, long classPK, long fileEntryId, String url,
			int activationStatus, long duration, int maxUsages,
			boolean useSample, long sampleFileEntryId, String sampleUrl,
			boolean termsOfUseRequired,
			Map<Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePrimKey, boolean override,
			ServiceContext serviceContext)
		throws PortalException {

		_checkPermission(className, classPK, ActionKeys.UPDATE);

		return cpDefinitionVirtualSettingLocalService.
			addCPDefinitionVirtualSetting(
				className, classPK, fileEntryId, url, activationStatus,
				duration, maxUsages, useSample, sampleFileEntryId, sampleUrl,
				termsOfUseRequired, termsOfUseContentMap,
				termsOfUseJournalArticleResourcePrimKey, override,
				serviceContext);
	}

	@Override
	public CPDefinitionVirtualSetting addCPDefinitionVirtualSetting(
			String className, long classPK, long fileEntryId, String url,
			int activationStatus, long duration, int maxUsages,
			boolean useSample, long sampleFileEntryId, String sampleUrl,
			boolean termsOfUseRequired,
			Map<Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePrimKey,
			ServiceContext serviceContext)
		throws PortalException {

		_checkPermission(className, classPK, ActionKeys.UPDATE);

		return cpDefinitionVirtualSettingLocalService.
			addCPDefinitionVirtualSetting(
				className, classPK, fileEntryId, url, activationStatus,
				duration, maxUsages, useSample, sampleFileEntryId, sampleUrl,
				termsOfUseRequired, termsOfUseContentMap,
				termsOfUseJournalArticleResourcePrimKey, serviceContext);
	}

	@Override
	public CPDefinitionVirtualSetting deleteCPDefinitionVirtualSetting(
			String className, long classPK)
		throws PortalException {

		_checkPermission(className, classPK, ActionKeys.UPDATE);

		return cpDefinitionVirtualSettingLocalService.
			deleteCPDefinitionVirtualSetting(className, classPK);
	}

	@Override
	public CPDefinitionVirtualSetting fetchCPDefinitionVirtualSetting(
			String className, long classPK)
		throws PortalException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			cpDefinitionVirtualSettingLocalService.
				fetchCPDefinitionVirtualSetting(className, classPK);

		if (cpDefinitionVirtualSetting != null) {
			_checkPermission(className, classPK, ActionKeys.VIEW);
		}

		return cpDefinitionVirtualSetting;
	}

	@Override
	public CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
			long cpDefinitionVirtualSettingId, long fileEntryId, String url,
			int activationStatus, long duration, int maxUsages,
			boolean useSample, long sampleFileEntryId, String sampleUrl,
			boolean termsOfUseRequired,
			Map<Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePrimKey, boolean override,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			cpDefinitionVirtualSettingLocalService.
				getCPDefinitionVirtualSetting(cpDefinitionVirtualSettingId);

		_checkPermission(
			cpDefinitionVirtualSetting.getClassName(),
			cpDefinitionVirtualSetting.getClassPK(), ActionKeys.UPDATE);

		return cpDefinitionVirtualSettingLocalService.
			updateCPDefinitionVirtualSetting(
				cpDefinitionVirtualSettingId, fileEntryId, url,
				activationStatus, duration, maxUsages, useSample,
				sampleFileEntryId, sampleUrl, termsOfUseRequired,
				termsOfUseContentMap, termsOfUseJournalArticleResourcePrimKey,
				override, serviceContext);
	}

	@Override
	public CPDefinitionVirtualSetting updateCPDefinitionVirtualSetting(
			long cpDefinitionVirtualSettingId, long fileEntryId, String url,
			int activationStatus, long duration, int maxUsages,
			boolean useSample, long sampleFileEntryId, String sampleUrl,
			boolean termsOfUseRequired,
			Map<Locale, String> termsOfUseContentMap,
			long termsOfUseJournalArticleResourcePrimKey,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			cpDefinitionVirtualSettingLocalService.
				getCPDefinitionVirtualSetting(cpDefinitionVirtualSettingId);

		_checkPermission(
			cpDefinitionVirtualSetting.getClassName(),
			cpDefinitionVirtualSetting.getClassPK(), ActionKeys.UPDATE);

		return cpDefinitionVirtualSettingLocalService.
			updateCPDefinitionVirtualSetting(
				cpDefinitionVirtualSettingId, fileEntryId, url,
				activationStatus, duration, maxUsages, useSample,
				sampleFileEntryId, sampleUrl, termsOfUseRequired,
				termsOfUseContentMap, termsOfUseJournalArticleResourcePrimKey,
				serviceContext);
	}

	@Reference
	protected CommerceCatalogLocalService commerceCatalogLocalService;

	@Reference
	protected CPDefinitionLocalService cpDefinitionLocalService;

	private void _checkCommerceCatalog(long cpDefinitionId, String actionId)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionLocalService.fetchCPDefinition(
			cpDefinitionId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException();
		}

		CommerceCatalog commerceCatalog =
			commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				cpDefinition.getGroupId());

		if (commerceCatalog == null) {
			throw new PrincipalException();
		}

		_commerceCatalogModelResourcePermission.check(
			getPermissionChecker(), commerceCatalog, actionId);
	}

	private void _checkPermission(String className, long classPK, String action)
		throws PortalException {

		long cpDefinitionId = classPK;

		if (className.equals(CPInstance.class.getName())) {
			CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
				classPK);

			cpDefinitionId = cpInstance.getCPDefinitionId();
		}

		_checkCommerceCatalog(cpDefinitionId, action);
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceCatalog)"
	)
	private ModelResourcePermission<CommerceCatalog>
		_commerceCatalogModelResourcePermission;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

}