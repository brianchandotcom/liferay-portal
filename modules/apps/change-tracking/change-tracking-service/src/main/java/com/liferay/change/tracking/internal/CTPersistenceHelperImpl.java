/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = CTPersistenceHelper.class)
public class CTPersistenceHelperImpl implements CTPersistenceHelper {

	@Override
	public <T extends CTModel<T>> boolean isInsert(T ctModel) {
		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		ctModel.setCtCollectionId(ctCollectionId);

		if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			return ctModel.isNew();
		}

		long modelClassNameId = _classNameLocalService.getClassNameId(
			ctModel.getModelClass());

		long modelClassPK = ctModel.getPrimaryKey();

		CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
			ctCollectionId, modelClassNameId, modelClassPK);

		long userId = PrincipalThreadLocal.getUserId();

		try {
			if (ctEntry == null) {
				int changeType = CTConstants.CT_CHANGE_TYPE_MODIFICATION;

				if (ctModel.isNew()) {
					changeType = CTConstants.CT_CHANGE_TYPE_ADDITION;
				}

				_ctEntryLocalService.addCTEntry(
					ctCollectionId, modelClassNameId, ctModel, userId,
					changeType);

				return true;
			}

			if (userId != ctEntry.getUserId()) {
				ctEntry.setUserId(userId);

				_ctEntryLocalService.updateCTEntry(ctEntry);
			}
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}

		return false;
	}

	@Override
	public <T extends CTModel<T>> boolean isProductionMode(
		Class<T> ctModelClass) {

		return isProductionMode(ctModelClass, null);
	}

	public <T extends CTModel<T>> boolean isProductionMode(
		Class<T> ctModelClass, Serializable primaryKey) {

		long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

		if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			return true;
		}

		long modelClassNameId = _classNameLocalService.getClassNameId(
			ctModelClass);

		if (primaryKey instanceof Long) {
			if (_ctEntryLocalService.hasCTEntry(
					ctCollectionId, modelClassNameId, (Long)primaryKey)) {

				return false;
			}

			return true;
		}

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

		long modelClassNameId = _classNameLocalService.getClassNameId(
			ctModel.getModelClass());

		long modelClassPK = ctModel.getPrimaryKey();

		CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
			ctCollectionId, modelClassNameId, modelClassPK);

		try {
			if (ctEntry == null) {
				_ctEntryLocalService.addCTEntry(
					ctCollectionId, modelClassNameId, ctModel,
					PrincipalThreadLocal.getUserId(),
					CTConstants.CT_CHANGE_TYPE_DELETION);
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
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}

		return false;
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

}