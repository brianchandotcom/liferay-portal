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

package com.liferay.knowledge.base.web.internal.info.item.provider;

import com.liferay.info.exception.InfoItemPermissionException;
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

		return hasPermission(
			permissionChecker,
			_getKBArticle(
				infoItemReference.getClassPK(),
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