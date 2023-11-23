/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.service.impl;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.type.virtual.model.CPDVirtualSettingFileEntry;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.base.CPDVirtualSettingFileEntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CPDVirtualSettingFileEntry"
	},
	service = AopService.class
)
public class CPDVirtualSettingFileEntryServiceImpl
	extends CPDVirtualSettingFileEntryServiceBaseImpl {

	@Override
	public CPDVirtualSettingFileEntry addCPDefinitionVirtualSetting(
			long groupId, String className, long classPK,
			long cpDefinitionVirtualSettingId, long fileEntryId, String url,
			String version)
		throws PortalException {

		_checkPermission(className, classPK, ActionKeys.UPDATE);

		return cpdVirtualSettingFileEntryLocalService.
			addCPDVirtualSettingFileEntry(
				getUserId(), groupId, cpDefinitionVirtualSettingId, fileEntryId,
				url, version);
	}

	@Override
	public CPDVirtualSettingFileEntry deleteCPDVirtualSettingFileEntry(
			String className, long classPK, long cpdVirtualSettingFileEntryId)
		throws PortalException {

		_checkPermission(className, classPK, ActionKeys.UPDATE);

		return cpdVirtualSettingFileEntryLocalService.
			deleteCPDVirtualSettingFileEntry(cpdVirtualSettingFileEntryId);
	}

	@Override
	public CPDVirtualSettingFileEntry fetchCPDVirtualSettingFileEntry(
			long cpdVirtualSettingFileEntryId)
		throws PortalException {

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			cpdVirtualSettingFileEntryLocalService.
				fetchCPDVirtualSettingFileEntry(cpdVirtualSettingFileEntryId);

		if (cpdVirtualSettingFileEntry != null) {
			CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
				cpdVirtualSettingFileEntry.getCPDefinitionVirtualSetting();

			_checkPermission(
				cpDefinitionVirtualSetting.getClassName(),
				cpDefinitionVirtualSetting.getClassPK(), ActionKeys.VIEW);
		}

		return cpdVirtualSettingFileEntry;
	}

	@Override
	public CPDVirtualSettingFileEntry getCPDVirtualSettingFileEntry(
			long cpdVirtualSettingFileEntryId)
		throws PortalException {

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			cpdVirtualSettingFileEntryLocalService.
				getCPDVirtualSettingFileEntry(cpdVirtualSettingFileEntryId);

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			cpdVirtualSettingFileEntry.getCPDefinitionVirtualSetting();

		_checkPermission(
			cpDefinitionVirtualSetting.getClassName(),
			cpDefinitionVirtualSetting.getClassPK(), ActionKeys.VIEW);

		return cpdVirtualSettingFileEntry;
	}

	@Override
	public CPDVirtualSettingFileEntry updateCPDefinitionVirtualSetting(
			long cpdVirtualSettingFileEntryId, long fileEntryId, String url,
			String version)
		throws PortalException {

		CPDVirtualSettingFileEntry cpdVirtualSettingFileEntry =
			cpdVirtualSettingFileEntryLocalService.
				getCPDVirtualSettingFileEntry(cpdVirtualSettingFileEntryId);

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			cpdVirtualSettingFileEntry.getCPDefinitionVirtualSetting();

		_checkPermission(
			cpDefinitionVirtualSetting.getClassName(),
			cpDefinitionVirtualSetting.getClassPK(), ActionKeys.UPDATE);

		return cpdVirtualSettingFileEntryLocalService.
			updateCPDVirtualSettingFileEntry(
				cpdVirtualSettingFileEntryId, fileEntryId, url, version);
	}

	private void _checkCommerceCatalog(long cpDefinitionId, String actionId)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionLocalService.fetchCPDefinition(
			cpDefinitionId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException();
		}

		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
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

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceCatalog)"
	)
	private ModelResourcePermission<CommerceCatalog>
		_commerceCatalogModelResourcePermission;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

}