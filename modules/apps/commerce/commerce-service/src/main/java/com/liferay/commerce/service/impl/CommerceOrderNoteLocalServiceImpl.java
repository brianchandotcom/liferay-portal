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
import com.liferay.commerce.exception.DuplicateCommerceOrderNoteException;
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

		return addCommerceOrderNote(
			commerceOrderId, content, restricted, null, serviceContext);
	}

	@Override
	public CommerceOrderNote addCommerceOrderNote(
			long commerceOrderId, String content, boolean restricted,
			String externalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.getCommerceOrder(commerceOrderId);
		User user = userLocalService.getUser(serviceContext.getUserId());

		validate(content);

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		validateExternalReferenceCode(
			serviceContext.getCompanyId(), externalReferenceCode);

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
		commerceOrderNote.setExternalReferenceCode(externalReferenceCode);

		return commerceOrderNotePersistence.update(commerceOrderNote);
	}

	@Override
	public void deleteCommerceOrderNotes(long commerceOrderId) {
		commerceOrderNotePersistence.removeByCommerceOrderId(commerceOrderId);
	}

	@Override
	public CommerceOrderNote fetchByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		return commerceOrderNotePersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
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

		return updateCommerceOrderNote(
			commerceOrderNoteId, content, restricted, null);
	}

	@Override
	public CommerceOrderNote updateCommerceOrderNote(
			long commerceOrderNoteId, String content, boolean restricted,
			String externalReferenceCode)
		throws PortalException {

		CommerceOrderNote commerceOrderNote =
			commerceOrderNotePersistence.findByPrimaryKey(commerceOrderNoteId);

		validate(content);

		commerceOrderNote.setContent(content);
		commerceOrderNote.setRestricted(restricted);

		if (Validator.isNull(commerceOrderNote.getExternalReferenceCode())) {
			if (Validator.isBlank(externalReferenceCode)) {
				externalReferenceCode = null;
			}

			commerceOrderNote.setExternalReferenceCode(externalReferenceCode);
		}

		return commerceOrderNotePersistence.update(commerceOrderNote);
	}

	public CommerceOrderNote upsertCommerceOrderNote(
			long commerceOrderNoteId, long commerceOrderId, String content,
			boolean restricted, String externalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		CommerceOrderNote commerceOrderNote;

		if (commerceOrderNoteId > 0) {
			commerceOrderNote = getCommerceOrderNote(commerceOrderNoteId);
		}
		else {
			commerceOrderNote = commerceOrderNotePersistence.fetchByC_ERC(
				serviceContext.getCompanyId(), externalReferenceCode);
		}

		if (commerceOrderNote != null) {
			return updateCommerceOrderNote(
				commerceOrderNote.getCommerceOrderNoteId(), content, restricted,
				externalReferenceCode);
		}

		return addCommerceOrderNote(
			commerceOrderId, content, restricted, externalReferenceCode,
			serviceContext);
	}

	protected void validate(String content) throws PortalException {
		if (Validator.isNull(content)) {
			throw new CommerceOrderNoteContentException();
		}
	}

	protected void validateExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		if (Validator.isNull(externalReferenceCode)) {
			return;
		}

		CommerceOrderNote commerceOrderNote =
			commerceOrderNotePersistence.fetchByC_ERC(
				companyId, externalReferenceCode);

		if (commerceOrderNote != null) {
			throw new DuplicateCommerceOrderNoteException(
				"There is another commerce order note with external " +
					"reference code " + externalReferenceCode);
		}
	}

}