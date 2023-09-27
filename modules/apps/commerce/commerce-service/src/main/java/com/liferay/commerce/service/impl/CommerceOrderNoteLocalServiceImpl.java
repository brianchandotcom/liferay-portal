/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.impl;

import com.liferay.commerce.exception.CommerceOrderNoteContentException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.service.base.CommerceOrderNoteLocalServiceBaseImpl;
import com.liferay.commerce.service.persistence.CommerceOrderPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	property = "model.class.name=com.liferay.commerce.model.CommerceOrderNote",
	service = AopService.class
)
public class CommerceOrderNoteLocalServiceImpl
	extends CommerceOrderNoteLocalServiceBaseImpl {

	@Override
	public CommerceOrderNote addCommerceOrderNote(
			long commerceOrderId, String content, boolean restricted,
			ServiceContext serviceContext)
		throws PortalException {

		return addCommerceOrderNote(
			null, commerceOrderId, content, restricted, serviceContext);
	}

	@Override
	public CommerceOrderNote addCommerceOrderNote(
			String externalReferenceCode, long commerceOrderId, String content,
			boolean restricted, ServiceContext serviceContext)
		throws PortalException {

		CommerceOrder commerceOrder =
			_commerceOrderPersistence.findByPrimaryKey(commerceOrderId);
		User user = _userLocalService.getUser(serviceContext.getUserId());

		_validate(content);

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		long commerceOrderNoteId = counterLocalService.increment();

		CommerceOrderNote commerceOrderNote =
			commerceOrderNotePersistence.create(commerceOrderNoteId);

		commerceOrderNote.setExternalReferenceCode(externalReferenceCode);
		commerceOrderNote.setGroupId(commerceOrder.getGroupId());
		commerceOrderNote.setCompanyId(user.getCompanyId());
		commerceOrderNote.setUserId(user.getUserId());
		commerceOrderNote.setUserName(user.getFullName());
		commerceOrderNote.setCommerceOrderId(
			commerceOrder.getCommerceOrderId());
		commerceOrderNote.setContent(content);
		commerceOrderNote.setRestricted(restricted);

		return commerceOrderNotePersistence.update(commerceOrderNote);
	}

	@Override
	public CommerceOrderNote addOrUpdateCommerceOrderNote(
			String externalReferenceCode, long commerceOrderNoteId,
			long commerceOrderId, String content, boolean restricted,
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
			commerceOrderNote = commerceOrderNotePersistence.fetchByERC_C(
				externalReferenceCode, serviceContext.getCompanyId());
		}

		if (commerceOrderNote != null) {
			return updateCommerceOrderNote(
				externalReferenceCode,
				commerceOrderNote.getCommerceOrderNoteId(), content,
				restricted);
		}

		return addCommerceOrderNote(
			externalReferenceCode, commerceOrderId, content, restricted,
			serviceContext);
	}

	@Override
	public void deleteCommerceOrderNotes(long commerceOrderId) {
		commerceOrderNotePersistence.removeByCommerceOrderId(commerceOrderId);
	}

	@Override
	public CommerceOrderNote fetchByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		return commerceOrderNotePersistence.fetchByERC_C(
			externalReferenceCode, companyId);
	}

	@Override
	public List<CommerceOrderNote> getCommerceOrderNotes(
		long commerceOrderId, boolean restricted) {

		return commerceOrderNotePersistence.findByC_R(
			commerceOrderId, restricted);
	}

	@Override
	public List<CommerceOrderNote> getCommerceOrderNotes(
		long commerceOrderId, boolean restricted, int start, int end) {

		return commerceOrderNotePersistence.findByC_R(
			commerceOrderId, restricted, start, end);
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
			null, commerceOrderNoteId, content, restricted);
	}

	@Override
	public CommerceOrderNote updateCommerceOrderNote(
			String externalReferenceCode, long commerceOrderNoteId,
			String content, boolean restricted)
		throws PortalException {

		CommerceOrderNote commerceOrderNote =
			commerceOrderNotePersistence.findByPrimaryKey(commerceOrderNoteId);

		_validate(content);

		if (Validator.isNull(commerceOrderNote.getExternalReferenceCode())) {
			if (Validator.isBlank(externalReferenceCode)) {
				externalReferenceCode = null;
			}

			commerceOrderNote.setExternalReferenceCode(externalReferenceCode);
		}

		commerceOrderNote.setContent(content);
		commerceOrderNote.setRestricted(restricted);

		return commerceOrderNotePersistence.update(commerceOrderNote);
	}

	private void _validate(String content) throws PortalException {
		if (Validator.isNull(content)) {
			throw new CommerceOrderNoteContentException();
		}
	}

	@Reference
	private CommerceOrderPersistence _commerceOrderPersistence;

	@Reference
	private UserLocalService _userLocalService;

}