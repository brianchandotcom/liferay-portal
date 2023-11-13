/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.info.permission.provider;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.info.permission.provider.InfoPermissionProvider;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = InfoPermissionProvider.class)
public class JournalArticleInfoPermissionProvider
	implements InfoPermissionProvider<JournalArticle> {

	@Override
	public boolean hasAddPermission(
		long groupId, PermissionChecker permissionChecker) {

		return _portletResourcePermission.contains(
			permissionChecker, groupId, ActionKeys.ADD_ARTICLE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		return true;
	}

	@Override
	public boolean hasViewPermission(
		String formVariationKey, long groupId,
		PermissionChecker permissionChecker) {

		if (Validator.isNull(formVariationKey) || (permissionChecker == null)) {
			return false;
		}

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			GetterUtil.getLong(formVariationKey));

		if (ddmStructure == null) {
			ddmStructure = _ddmStructureLocalService.fetchStructure(
				groupId, _portal.getClassNameId(JournalArticle.class.getName()),
				formVariationKey);
		}

		if (ddmStructure == null) {
			return false;
		}

		try {
			return _ddmStructureModelResourcePermission.contains(
				permissionChecker, ddmStructure, ActionKeys.VIEW);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleInfoPermissionProvider.class);

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMStructure)"
	)
	private ModelResourcePermission<DDMStructure>
		_ddmStructureModelResourcePermission;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + JournalConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}