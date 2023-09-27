/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.service.base.CommerceOrderNoteServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceOrderNote"
	},
	service = AopService.class
)
public class CommerceOrderNoteServiceImpl
	extends CommerceOrderNoteServiceBaseImpl {

	@Override
	public CommerceOrderNote addCommerceOrderNote(
			long commerceOrderId, String content, boolean restricted,
			ServiceContext serviceContext)
		throws PortalException {

		String actionId = CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_NOTES;

		if (restricted) {
			actionId =
				CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES;
		}

		_portletResourcePermission.check(
			getPermissionChecker(), commerceOrderId, actionId);

		return commerceOrderNoteLocalService.addCommerceOrderNote(
			commerceOrderId, content, restricted, serviceContext);
	}

	@Override
	public CommerceOrderNote addOrUpdateCommerceOrderNote(
			String externalReferenceCode, long commerceOrderNoteId,
			long commerceOrderId, String content, boolean restricted,
			ServiceContext serviceContext)
		throws PortalException {

		String actionId = CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_NOTES;

		if (restricted) {
			actionId =
				CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES;
		}

		_portletResourcePermission.check(
			getPermissionChecker(), commerceOrderId, actionId);

		return commerceOrderNoteLocalService.addOrUpdateCommerceOrderNote(
			externalReferenceCode, commerceOrderNoteId, commerceOrderId,
			content, restricted, serviceContext);
	}

	@Override
	public void deleteCommerceOrderNote(long commerceOrderNoteId)
		throws PortalException {

		CommerceOrderNote commerceOrderNote =
			commerceOrderNoteLocalService.getCommerceOrderNote(
				commerceOrderNoteId);

		String actionId = CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_NOTES;

		if (commerceOrderNote.isRestricted()) {
			actionId =
				CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES;
		}

		_portletResourcePermission.check(
			getPermissionChecker(), commerceOrderNote.getCommerceOrderId(),
			actionId);

		commerceOrderNoteLocalService.deleteCommerceOrderNote(
			commerceOrderNote);
	}

	@Override
	public CommerceOrderNote fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		CommerceOrderNote commerceOrderNote =
			commerceOrderNoteLocalService.fetchByExternalReferenceCode(
				externalReferenceCode, companyId);

		if (commerceOrderNote != null) {
			_portletResourcePermission.check(
				getPermissionChecker(), commerceOrderNote.getCommerceOrderId(),
				CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES);
		}

		return commerceOrderNote;
	}

	@Override
	public CommerceOrderNote fetchCommerceOrderNote(long commerceOrderNoteId)
		throws PortalException {

		CommerceOrderNote commerceOrderNote =
			commerceOrderNoteLocalService.fetchCommerceOrderNote(
				commerceOrderNoteId);

		_checkCommerceOrderNotePermissions(commerceOrderNote);

		return commerceOrderNote;
	}

	@Override
	public CommerceOrderNote getCommerceOrderNote(long commerceOrderNoteId)
		throws PortalException {

		CommerceOrderNote commerceOrderNote =
			commerceOrderNoteLocalService.getCommerceOrderNote(
				commerceOrderNoteId);

		_checkCommerceOrderNotePermissions(commerceOrderNote);

		return commerceOrderNote;
	}

	@Override
	public List<CommerceOrderNote> getCommerceOrderNotes(
			long commerceOrderId, boolean restricted)
		throws PortalException {

		String actionId = ActionKeys.VIEW;

		if (restricted) {
			actionId =
				CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES;

			_portletResourcePermission.check(
				getPermissionChecker(), commerceOrderId, actionId);
		}
		else {
			_commerceOrderModelResourcePermission.check(
				getPermissionChecker(), commerceOrderId, actionId);
		}

		return commerceOrderNoteLocalService.getCommerceOrderNotes(
			commerceOrderId, restricted);
	}

	@Override
	public List<CommerceOrderNote> getCommerceOrderNotes(
			long commerceOrderId, boolean restricted, int start, int end)
		throws PortalException {

		String actionId = ActionKeys.VIEW;

		if (restricted) {
			actionId =
				CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES;

			_portletResourcePermission.check(
				getPermissionChecker(), commerceOrderId, actionId);
		}
		else {
			_commerceOrderModelResourcePermission.check(
				getPermissionChecker(), commerceOrderId, actionId);
		}

		return commerceOrderNoteLocalService.getCommerceOrderNotes(
			commerceOrderId, restricted, start, end);
	}

	@Override
	public List<CommerceOrderNote> getCommerceOrderNotes(
			long commerceOrderId, int start, int end)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), commerceOrderId,
			CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES);

		return commerceOrderNoteLocalService.getCommerceOrderNotes(
			commerceOrderId, start, end);
	}

	@Override
	public int getCommerceOrderNotesCount(long commerceOrderId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), commerceOrderId,
			CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES);

		return commerceOrderNoteLocalService.getCommerceOrderNotesCount(
			commerceOrderId);
	}

	@Override
	public int getCommerceOrderNotesCount(
			long commerceOrderId, boolean restricted)
		throws PortalException {

		String actionId = ActionKeys.VIEW;

		if (restricted) {
			actionId =
				CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES;

			_portletResourcePermission.check(
				getPermissionChecker(), commerceOrderId, actionId);
		}
		else {
			_commerceOrderModelResourcePermission.check(
				getPermissionChecker(), commerceOrderId, actionId);
		}

		return commerceOrderNoteLocalService.getCommerceOrderNotesCount(
			commerceOrderId, restricted);
	}

	@Override
	public CommerceOrderNote updateCommerceOrderNote(
			long commerceOrderNoteId, String content, boolean restricted)
		throws PortalException {

		CommerceOrderNote commerceOrderNote =
			commerceOrderNoteLocalService.getCommerceOrderNote(
				commerceOrderNoteId);

		String actionId = CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_NOTES;

		if (restricted) {
			actionId =
				CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES;
		}

		_portletResourcePermission.check(
			getPermissionChecker(), commerceOrderNote.getCommerceOrderId(),
			actionId);

		return commerceOrderNoteLocalService.updateCommerceOrderNote(
			commerceOrderNote.getCommerceOrderNoteId(), content, restricted);
	}

	private void _checkCommerceOrderNotePermissions(
			CommerceOrderNote commerceOrderNote)
		throws PortalException {

		if (commerceOrderNote == null) {
			return;
		}

		String actionId = ActionKeys.VIEW;

		if (commerceOrderNote.isRestricted()) {
			actionId =
				CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES;

			_portletResourcePermission.check(
				getPermissionChecker(), commerceOrderNote.getCommerceOrderId(),
				actionId);
		}
		else {
			_commerceOrderModelResourcePermission.check(
				getPermissionChecker(), commerceOrderNote.getCommerceOrderId(),
				actionId);
		}
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	private ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;

	@Reference(
		target = "(resource.name=" + CommerceOrderConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}