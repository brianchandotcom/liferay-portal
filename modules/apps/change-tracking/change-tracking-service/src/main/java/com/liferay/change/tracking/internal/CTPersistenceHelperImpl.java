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

package com.liferay.change.tracking.internal;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = CTPersistenceHelper.class)
public class CTPersistenceHelperImpl implements CTPersistenceHelper {

	@Override
	public <T extends CTModel<T>> boolean isInsert(T ctModel) {
		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		ctModel.setCtCollectionId(ctCollectionId);

		if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			return ctModel.isNew();
		}

		_validateCTCollectionStatus(ctCollectionId);

		long modelClassNameId = _classNameLocalService.getClassNameId(
			ctModel.getModelClass());

		long modelClassPK = ctModel.getPrimaryKey();

		CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
			ctCollectionId, modelClassNameId, modelClassPK);

		long userId = PrincipalThreadLocal.getUserId();

		if (ctEntry == null) {
			int changeType = CTConstants.CT_CHANGE_TYPE_MODIFICATION;

			if (ctModel.isNew()) {
				changeType = CTConstants.CT_CHANGE_TYPE_ADDITION;
			}

			try {
				_ctEntryLocalService.addCTEntry(
					ctCollectionId, modelClassNameId, ctModel, userId,
					changeType);
			}
			catch (PortalException pe) {
				throw new SystemException(pe);
			}

			ctModel.setNew(true);

			return true;
		}

		if (userId != ctEntry.getUserId()) {
			ctEntry.setUserId(userId);

			_ctEntryLocalService.updateCTEntry(ctEntry);
		}

		return false;
	}

	@Override
	public <T extends CTModel<T>> boolean isProductionMode(
		Class<T> ctModelClass) {

		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			return true;
		}

		long modelClassNameId = _classNameLocalService.getClassNameId(
			ctModelClass);

		if (_ctEntryLocalService.hasCTEntries(
				ctCollectionId, modelClassNameId)) {

			return false;
		}

		return true;
	}

	@Override
	public <T extends CTModel<T>> boolean isRemove(T ctModel) {
		if (ctModel == null) {
			return false;
		}

		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			return true;
		}

		_validateCTCollectionStatus(ctCollectionId);

		long modelClassNameId = _classNameLocalService.getClassNameId(
			ctModel.getModelClass());

		long modelClassPK = ctModel.getPrimaryKey();

		CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
			ctCollectionId, modelClassNameId, modelClassPK);

		if (ctEntry == null) {
			try {
				_ctEntryLocalService.addCTEntry(
					ctCollectionId, modelClassNameId, ctModel,
					PrincipalThreadLocal.getUserId(),
					CTConstants.CT_CHANGE_TYPE_DELETION);
			}
			catch (PortalException pe) {
				throw new SystemException(pe);
			}
		}
		else {
			int changeType = ctEntry.getChangeType();

			if (changeType == CTConstants.CT_CHANGE_TYPE_ADDITION) {
				_ctEntryLocalService.deleteCTEntry(ctEntry);

				return true;
			}

			ctEntry.setChangeType(CTConstants.CT_CHANGE_TYPE_DELETION);

			_ctEntryLocalService.updateCTEntry(ctEntry);

			if ((changeType == CTConstants.CT_CHANGE_TYPE_MODIFICATION) &&
				(ctModel.getCtCollectionId() !=
					CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

				return true;
			}
		}

		return false;
	}

	private void _validateCTCollectionStatus(long ctCollectionId) {
		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		if ((ctCollection == null) ||
			(ctCollection.getStatus() != WorkflowConstants.STATUS_DRAFT)) {

			throw new IllegalStateException(
				"CTCollection is read only " + String.valueOf(ctCollection));
		}
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

}