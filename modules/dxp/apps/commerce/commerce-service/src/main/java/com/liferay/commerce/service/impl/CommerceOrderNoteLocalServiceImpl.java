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