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

package com.liferay.data.engine.internal.service;

import com.liferay.data.engine.constants.DEActionKeys;
import com.liferay.data.engine.exception.DEDataRecordCollectionException;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionSaveRequestExecutor;
import com.liferay.data.engine.internal.security.permission.DEDataEnginePermissionSupport;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionService;
import com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DEDataRecordCollectionService.class)
public class DEDataRecordCollectionServiceImpl
	extends DEBaseServiceImpl implements DEDataRecordCollectionService {

	@Override
	public DEDataRecordCollectionSaveResponse execute(
			DEDataRecordCollectionSaveRequest deDataRecordCollectionSaveRequest)
		throws DEDataRecordCollectionException {

		DEDataRecordCollection deDataRecordCollection =
			deDataRecordCollectionSaveRequest.getDEDataRecordCollection();

		try {
			long deDataRecordCollectionId =
				deDataRecordCollection.getDEDataRecordCollectionId();

			if (deDataRecordCollectionId == 0) {
				checkPermission(
					deDataRecordCollectionSaveRequest.getGroupId(),
					DEActionKeys.ADD_DATA_RECORD_COLLECTION_ACTION,
					getPermissionChecker());
			}
			else {
				_modelResourcePermission.check(
					getPermissionChecker(), deDataRecordCollectionId,
					ActionKeys.UPDATE);
			}

			DEDataRecordCollectionSaveRequestExecutor
				deDataRecordCollectionSaveRequestExecutor =
					getDEDataRecordCollectionSaveRequestExecutor();

			DEDataRecordCollectionSaveResponse
				deDataRecordCollectionSaveResponse =
					deDataRecordCollectionSaveRequestExecutor.execute(
						deDataRecordCollectionSaveRequest);

			return DEDataRecordCollectionSaveResponse.Builder.of(
				deDataRecordCollectionSaveResponse.
					getDEDataRecordCollectionId());
		}
		catch (DEDataRecordCollectionException dedrce) {
			throw dedrce;
		}
		catch (NoSuchRecordSetException nsrse) {
			throw new DEDataRecordCollectionException.
				NoSuchDataRecordCollection(
					deDataRecordCollection.getDEDataRecordCollectionId(),
					nsrse);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataRecordCollectionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (Exception e) {
			throw new DEDataRecordCollectionException(e);
		}
	}

	@Override
	protected DEDataEnginePermissionSupport getDEDataEnginePermissionSupport() {
		return new DEDataEnginePermissionSupport(groupLocalService);
	}

	protected DEDataRecordCollectionSaveRequestExecutor
		getDEDataRecordCollectionSaveRequestExecutor() {

		if (_deDataRecordCollectionSaveRequestExecutor == null) {
			_deDataRecordCollectionSaveRequestExecutor =
				new DEDataRecordCollectionSaveRequestExecutor(
					ddlRecordSetLocalService, portal, resourceLocalService);
		}

		return _deDataRecordCollectionSaveRequestExecutor;
	}

	@Reference(
		target = "(model.class.name=com.liferay.data.engine.model.DEDataRecordCollection)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<DEDataRecordCollection>
			modelResourcePermission) {

		_modelResourcePermission = modelResourcePermission;
	}

	@Reference
	protected DDLRecordSetLocalService ddlRecordSetLocalService;

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected Portal portal;

	@Reference
	protected ResourceLocalService resourceLocalService;

	private DEDataRecordCollectionSaveRequestExecutor
		_deDataRecordCollectionSaveRequestExecutor;
	private ModelResourcePermission<DEDataRecordCollection>
		_modelResourcePermission;

}