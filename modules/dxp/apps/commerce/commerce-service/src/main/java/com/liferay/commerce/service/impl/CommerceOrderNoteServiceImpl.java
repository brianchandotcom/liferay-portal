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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.service.base.CommerceOrderNoteServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
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

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, actionId);

		return commerceOrderNoteLocalService.addCommerceOrderNote(
			commerceOrderId, content, restricted, serviceContext);
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

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderNote.getCommerceOrderId(),
			actionId);

		commerceOrderNoteLocalService.deleteCommerceOrderNote(
			commerceOrderNote);
	}

	@Override
	public CommerceOrderNote getCommerceOrderNote(long commerceOrderNoteId)
		throws PortalException {

		CommerceOrderNote commerceOrderNote =
			commerceOrderNoteLocalService.getCommerceOrderNote(
				commerceOrderNoteId);

		String actionId = ActionKeys.VIEW;

		if (commerceOrderNote.isRestricted()) {
			actionId =
				CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES;
		}

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderNote.getCommerceOrderId(),
			actionId);

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
		}

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, actionId);

		return commerceOrderNoteLocalService.getCommerceOrderNotes(
			commerceOrderId, restricted);
	}

	@Override
	public List<CommerceOrderNote> getCommerceOrderNotes(
			long commerceOrderId, int start, int end)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId,
			CommerceOrderActionKeys.MANAGE_COMMERCE_ORDER_RESTRICTED_NOTES);

		return commerceOrderNoteLocalService.getCommerceOrderNotes(
			commerceOrderId, start, end);
	}

	@Override
	public int getCommerceOrderNotesCount(long commerceOrderId)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
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
		}

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, actionId);

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

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderNote.getCommerceOrderId(),
			actionId);

		return commerceOrderNoteLocalService.updateCommerceOrderNote(
			commerceOrderNote.getCommerceOrderNoteId(), content, restricted);
	}

	private static volatile ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceOrderNoteServiceImpl.class,
				"_commerceOrderModelResourcePermission", CommerceOrder.class);

}