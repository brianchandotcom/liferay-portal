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

package com.liferay.commerce.product.type.virtual.web.internal.util;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingLocalService;
import com.liferay.commerce.product.type.virtual.util.VirtualCPTypeHelper;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = VirtualCPTypeHelper.class
)
public class VirtualCPTypeHelperImpl implements VirtualCPTypeHelper {

	@Override
	public CPDefinitionVirtualSetting getCPDefinitionVirtualSetting(
		long cpDefinitionId, long cpInstanceId) {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			_cpDefinitionVirtualSettingLocalService.
				fetchCPDefinitionVirtualSetting(
					CPInstance.class.getName(), cpInstanceId);

		if ((cpDefinitionVirtualSetting == null) ||
			!cpDefinitionVirtualSetting.isOverride()) {

			cpDefinitionVirtualSetting =
				_cpDefinitionVirtualSettingLocalService.
					fetchCPDefinitionVirtualSetting(
						CPDefinition.class.getName(), cpDefinitionId);
		}

		return cpDefinitionVirtualSetting;
	}

	@Override
	public String getSampleURL(
			long cpDefinitionId, long cpInstanceId, ThemeDisplay themeDisplay)
		throws PortalException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			getCPDefinitionVirtualSetting(cpDefinitionId, cpInstanceId);

		if ((cpDefinitionVirtualSetting == null) ||
			!cpDefinitionVirtualSetting.isUseSample()) {

			return StringPool.BLANK;
		}

		if (cpDefinitionVirtualSetting.isUseSampleUrl()) {
			return cpDefinitionVirtualSetting.getSampleUrl();
		}

		FileEntry fileEntry = _dlAppService.getFileEntry(
			cpDefinitionVirtualSetting.getSampleFileEntryId());

		return DLUtil.getDownloadURL(
			fileEntry, fileEntry.getFileVersion(), themeDisplay,
			StringPool.BLANK);
	}

	@Reference
	private CPDefinitionVirtualSettingLocalService
		_cpDefinitionVirtualSettingLocalService;

	@Reference
	private DLAppService _dlAppService;

}