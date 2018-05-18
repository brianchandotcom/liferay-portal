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

import com.liferay.commerce.exception.CommerceOrderNoteContentException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.service.base.CommerceOrderNoteLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderNoteLocalServiceImpl
	extends CommerceOrderNoteLocalServiceBaseImpl {

	@Override
	public CommerceOrderNote addCommerceOrderNote(
			long commerceOrderId, String content, boolean restricted,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.getCommerceOrder(commerceOrderId);
		User user = userLocalService.getUser(serviceContext.getUserId());

		validate(content);

		long commerceOrderNoteId = counterLocalService.increment();

		CommerceOrderNote commerceOrderNote =
			commerceOrderNotePersistence.create(commerceOrderNoteId);

		commerceOrderNote.setGroupId(commerceOrder.getGroupId());
		commerceOrderNote.setCompanyId(user.getCompanyId());
		commerceOrderNote.setUserId(user.getUserId());
		commerceOrderNote.setUserName(user.getFullName());
		commerceOrderNote.setCommerceOrderId(
			commerceOrder.getCommerceOrderId());
		commerceOrderNote.setContent(content);
		commerceOrderNote.setRestricted(restricted);

		commerceOrderNotePersistence.update(commerceOrderNote);

		return commerceOrderNote;
	}

	@Override
	public void deleteCommerceOrderNotes(long commerceOrderId) {
		commerceOrderNotePersistence.removeByCommerceOrderId(commerceOrderId);
	}

	@Override
	public List<CommerceOrderNote> getCommerceOrderNotes(
		long commerceOrderId, boolean restricted) {

		return commerceOrderNotePersistence.findByC_R(
			commerceOrderId, restricted);
	}

	@Override
	public List<CommerceOrderNote> getCommerceOrderNotes(
		long commerceOrderId, int start, int end) {

		return commerceOrderNotePersistence.findByCommerceOrderId(
			commerceOrderId, start, end);
	}

	@Override
	public int getCommerceOrderNotesCount(long commerceOrderId) {
		return commerceOrderNotePersistence.countByCommerceOrderId(
			commerceOrderId);
	}

	@Override
	public int getCommerceOrderNotesCount(
		long commerceOrderId, boolean restricted) {

		return commerceOrderNotePersistence.countByC_R(
			commerceOrderId, restricted);
	}

	@Override
	public CommerceOrderNote updateCommerceOrderNote(
			long commerceOrderNoteId, String content, boolean restricted)
		throws PortalException {

		CommerceOrderNote commerceOrderNote =
			commerceOrderNotePersistence.findByPrimaryKey(commerceOrderNoteId);

		validate(content);

		commerceOrderNote.setContent(content);
		commerceOrderNote.setRestricted(restricted);

		commerceOrderNotePersistence.update(commerceOrderNote);

		return commerceOrderNote;
	}

	protected void validate(String content) throws PortalException {
		if (Validator.isNull(content)) {
			throw new CommerceOrderNoteContentException();
		}
	}

}