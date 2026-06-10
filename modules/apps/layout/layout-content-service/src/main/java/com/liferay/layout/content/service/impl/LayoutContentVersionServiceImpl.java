/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.service.impl;

import com.liferay.layout.content.model.LayoutContentVersion;
import com.liferay.layout.content.service.base.LayoutContentVersionServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = {
		"json.web.service.context.name=layoutcontentversion",
		"json.web.service.context.path=LayoutContentVersion"
	},
	service = AopService.class
)
public class LayoutContentVersionServiceImpl
	extends LayoutContentVersionServiceBaseImpl {

	@Override
	public LayoutContentVersion addLayoutContentVersion(
			String externalReferenceCode, long plid,
			Map<Locale, String> nameMap, String data, int status,
			boolean skipIfUnchanged)
		throws PortalException {

		_layoutModelResourcePermission.check(
			getPermissionChecker(), plid, ActionKeys.UPDATE);

		return layoutContentVersionLocalService.addLayoutContentVersion(
			externalReferenceCode, getUserId(), plid, nameMap, data, status,
			skipIfUnchanged);
	}

	@Override
	public LayoutContentVersion deleteLayoutContentVersion(
			long layoutContentVersionId)
		throws PortalException {

		LayoutContentVersion layoutContentVersion =
			layoutContentVersionLocalService.getLayoutContentVersion(
				layoutContentVersionId);

		_layoutModelResourcePermission.check(
			getPermissionChecker(), layoutContentVersion.getPlid(),
			ActionKeys.UPDATE);

		return layoutContentVersionLocalService.deleteLayoutContentVersion(
			layoutContentVersionId);
	}

	@Override
	public LayoutContentVersion getLayoutContentVersion(
			long layoutContentVersionId)
		throws PortalException {

		LayoutContentVersion layoutContentVersion =
			layoutContentVersionLocalService.getLayoutContentVersion(
				layoutContentVersionId);

		_layoutModelResourcePermission.check(
			getPermissionChecker(), layoutContentVersion.getPlid(),
			ActionKeys.UPDATE);

		return layoutContentVersion;
	}

	@Override
	public LayoutContentVersion getLayoutContentVersionByExternalReferenceCode(
			String externalReferenceCode, long groupId)
		throws PortalException {

		LayoutContentVersion layoutContentVersion =
			layoutContentVersionLocalService.
				getLayoutContentVersionByExternalReferenceCode(
					externalReferenceCode, groupId);

		_layoutModelResourcePermission.check(
			getPermissionChecker(), layoutContentVersion.getPlid(),
			ActionKeys.UPDATE);

		return layoutContentVersion;
	}

	@Override
	public List<LayoutContentVersion> getLayoutContentVersions(long plid)
		throws PortalException {

		_layoutModelResourcePermission.check(
			getPermissionChecker(), plid, ActionKeys.UPDATE);

		return layoutContentVersionLocalService.getLayoutContentVersions(plid);
	}

	@Override
	public LayoutContentVersion updateLayoutContentVersion(
			long layoutContentVersionId, Map<Locale, String> nameMap)
		throws PortalException {

		LayoutContentVersion layoutContentVersion =
			layoutContentVersionLocalService.getLayoutContentVersion(
				layoutContentVersionId);

		_layoutModelResourcePermission.check(
			getPermissionChecker(), layoutContentVersion.getPlid(),
			ActionKeys.UPDATE);

		return layoutContentVersionLocalService.updateLayoutContentVersion(
			layoutContentVersionId, nameMap);
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.Layout)"
	)
	private ModelResourcePermission<Layout> _layoutModelResourcePermission;

}