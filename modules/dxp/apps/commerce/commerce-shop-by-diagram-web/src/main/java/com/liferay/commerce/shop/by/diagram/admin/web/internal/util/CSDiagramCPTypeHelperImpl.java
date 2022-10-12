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

package com.liferay.commerce.shop.by.diagram.admin.web.internal.util;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.media.CommerceMediaProvider;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramSettingLocalService;
import com.liferay.commerce.shop.by.diagram.type.CSDiagramType;
import com.liferay.commerce.shop.by.diagram.type.CSDiagramTypeRegistry;
import com.liferay.commerce.shop.by.diagram.util.CSDiagramCPTypeHelper;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 * @author Crescenzo Rega
 */
@Component(immediate = true, service = CSDiagramCPTypeHelper.class)
public class CSDiagramCPTypeHelperImpl implements CSDiagramCPTypeHelper {

	public FileVersion getCPDiagramImageFileVersion(
			long cpDefinitionId, CSDiagramSetting csDiagramSetting,
			HttpServletRequest httpServletRequest)
		throws Exception {

		FileVersion fileVersion = CSDiagramSettingUtil.getFileVersion(
			csDiagramSetting);

		if (fileVersion != null) {
			return fileVersion;
		}

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		FileEntry fileEntry = _commerceMediaProvider.getDefaultImageFileEntry(
			_portal.getCompanyId(httpServletRequest),
			cpDefinition.getGroupId());

		return fileEntry.getFileVersion();
	}

	@Override
	public CSDiagramSetting getCSDiagramSetting(
			CommerceAccount commerceAccount, long cpDefinitionId,
			PermissionChecker permissionChecker)
		throws PortalException {

		long commerceAccountId = 0;

		if (commerceAccount != null) {
			commerceAccountId = commerceAccount.getCommerceAccountId();
		}

		_commerceProductViewPermission.check(
			permissionChecker, commerceAccountId, cpDefinitionId);

		return _csDiagramSettingLocalService.
			fetchCSDiagramSettingByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public CSDiagramType getCSDiagramType(String type) {
		return _csDiagramTypeRegistry.getCSDiagramType(type);
	}

	@Override
	public String getImageURL(CSDiagramSetting csDiagramSetting)
		throws Exception {

		return CSDiagramSettingUtil.getImageURL(csDiagramSetting, _dlURLHelper);
	}

	@Reference
	private CommerceMediaProvider _commerceMediaProvider;

	@Reference
	private CommerceProductViewPermission _commerceProductViewPermission;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CSDiagramSettingLocalService _csDiagramSettingLocalService;

	@Reference
	private CSDiagramTypeRegistry _csDiagramTypeRegistry;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private Portal _portal;

}