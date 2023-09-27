/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.info.item.provider;

import com.liferay.info.exception.InfoItemPermissionException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = InfoItemPermissionProvider.class)
public class KBArticleInfoItemPermissionProvider
	implements InfoItemPermissionProvider<KBArticle> {

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker,
			InfoItemReference infoItemReference, String actionId)
		throws InfoItemPermissionException {

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
			return false;
		}

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		return hasPermission(
			permissionChecker,
			_getKBArticle(
				classPKInfoItemIdentifier.getClassPK(),
				WorkflowConstants.STATUS_APPROVED),
			actionId);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, KBArticle kbArticle,
			String actionId)
		throws InfoItemPermissionException {

		return _hasPermission(
			permissionChecker, kbArticle.getKbArticleId(), actionId);
	}

	private KBArticle _getKBArticle(long classPK, int status)
		throws InfoItemPermissionException {

		KBArticle kbArticle = _kbArticleLocalService.fetchKBArticle(classPK);

		if (kbArticle != null) {
			return kbArticle;
		}

		try {
			return _kbArticleLocalService.getLatestKBArticle(classPK, status);
		}
		catch (PortalException portalException) {
			throw new InfoItemPermissionException(classPK, portalException);
		}
	}

	private boolean _hasPermission(
			PermissionChecker permissionChecker, long id, String actionId)
		throws InfoItemPermissionException {

		try {
			return _kbArticleModelResourcePermission.contains(
				permissionChecker, id, actionId);
		}
		catch (PortalException portalException) {
			throw new InfoItemPermissionException(id, portalException);
		}
	}

	@Reference
	private KBArticleLocalService _kbArticleLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.knowledge.base.model.KBArticle)"
	)
	private ModelResourcePermission<KBArticle>
		_kbArticleModelResourcePermission;

}