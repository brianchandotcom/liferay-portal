/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.web.internal.util;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingLocalService;
import com.liferay.commerce.product.type.virtual.util.VirtualCPTypeHelper;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = VirtualCPTypeHelper.class)
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

		if (cpDefinitionVirtualSetting.isUseSampleURL()) {
			return cpDefinitionVirtualSetting.getSampleURL();
		}

		FileEntry fileEntry = _dlAppService.getFileEntry(
			cpDefinitionVirtualSetting.getSampleFileEntryId());

		return DLURLHelperUtil.getDownloadURL(
			fileEntry, fileEntry.getFileVersion(), themeDisplay,
			StringPool.BLANK);
	}

	@Reference
	private CPDefinitionVirtualSettingLocalService
		_cpDefinitionVirtualSettingLocalService;

	@Reference
	private DLAppService _dlAppService;

}