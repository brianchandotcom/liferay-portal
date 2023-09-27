/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.security.permission.resource;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.LayoutPrototypePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Couso
 */
@Component(
	property = "extended=true", service = LayoutPrototypePermission.class
)
public class LayoutPageTemplateEntryLayoutPrototypePermission
	implements LayoutPrototypePermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long layoutPrototypeId,
			String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, layoutPrototypeId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, LayoutPrototype.class.getName(),
				layoutPrototypeId, actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long layoutPrototypeId,
		String actionId) {

		try {
			if (LayoutPageTemplateEntryPermission.contains(
					permissionChecker,
					_getLayoutPageTemplateEntry(layoutPrototypeId), actionId)) {

				return true;
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}

		return false;
	}

	private LayoutPageTemplateEntry _getLayoutPageTemplateEntry(
		long layoutPrototypeId) {

		return _layoutPageTemplateEntryLocalService.
			fetchFirstLayoutPageTemplateEntry(layoutPrototypeId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateEntryLayoutPrototypePermission.class);

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}