/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.security.permission.resource;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.service.CommerceOrderNoteLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "model.class.name=com.liferay.commerce.model.CommerceOrderNote",
	service = ModelResourcePermission.class
)
public class CommerceOrderNoteModelResourcePermission
	implements ModelResourcePermission<CommerceOrderNote> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceOrderNote commerceOrderNote, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceOrderNote, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceOrderNote.class.getName(),
				commerceOrderNote.getCommerceOrderNoteId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceOrderNoteId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, commerceOrderNoteId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CommerceOrderNote.class.getName(),
				commerceOrderNoteId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceOrderNote commerceOrderNote, String actionId)
		throws PortalException {

		return contains(
			permissionChecker, commerceOrderNote.getCommerceOrderNoteId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceOrderNoteId,
			String actionId)
		throws PortalException {

		CommerceOrderNote commerceOrderNote =
			_commerceOrderNoteLocalService.fetchCommerceOrderNote(
				commerceOrderNoteId);

		if (commerceOrderNote == null) {
			return false;
		}

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(),
				CommerceOrderNote.class.getName(), commerceOrderNoteId,
				commerceOrderNote.getUserId(), actionId)) {

			return true;
		}

		return _commerceOrderModelResourcePermission.contains(
			permissionChecker, commerceOrderNote.getCommerceOrderId(),
			actionId);
	}

	@Override
	public String getModelName() {
		return CommerceOrderNote.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _commerceOrderModelResourcePermission.
			getPortletResourcePermission();
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	private ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;

	@Reference
	private CommerceOrderNoteLocalService _commerceOrderNoteLocalService;

}