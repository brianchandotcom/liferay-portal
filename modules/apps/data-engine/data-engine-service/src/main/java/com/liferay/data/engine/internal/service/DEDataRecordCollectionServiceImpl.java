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
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionDeleteModelPermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionDeletePermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionSaveModelPermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionSavePermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionSaveRequestExecutor;
import com.liferay.data.engine.internal.security.permission.DEDataEnginePermissionSupport;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.data.engine.service.DEDataRecordCollectionDeleteModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionDeleteModelPermissionsResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionDeletePermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionDeletePermissionsResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveModelPermissionsResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionSavePermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSavePermissionsResponse;
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
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
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
	public DEDataRecordCollectionDeleteModelPermissionsResponse execute(
			DEDataRecordCollectionDeleteModelPermissionsRequest
				deDataRecordCollectionDeleteModelPermissionsRequest)
		throws DEDataRecordCollectionException {

		DEDataRecordCollectionDeleteModelPermissionsRequestExecutor
			deDataRecordCollectionDeleteModelPermissionsRequestExecutor =
				getDEDataRecordCollectionDeleteModelPermissionsRequestExecutor();

		try {
			checkPermission(
				deDataRecordCollectionDeleteModelPermissionsRequest.
					getScopedGroupId(),
				ActionKeys.DEFINE_PERMISSIONS, getPermissionChecker());

			return deDataRecordCollectionDeleteModelPermissionsRequestExecutor.
				execute(deDataRecordCollectionDeleteModelPermissionsRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataRecordCollectionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (DEDataRecordCollectionException dedrce) {
			throw dedrce;
		}
		catch (Exception e) {
			throw new DEDataRecordCollectionException(e);
		}
	}

	@Override
	public DEDataRecordCollectionDeletePermissionsResponse execute(
			DEDataRecordCollectionDeletePermissionsRequest
				deDataRecordCollectionDeletePermissionsRequest)
		throws DEDataRecordCollectionException {

		try {
			checkPermission(
				deDataRecordCollectionDeletePermissionsRequest.
					getScopedGroupId(),
				ActionKeys.DEFINE_PERMISSIONS, getPermissionChecker());

			DEDataRecordCollectionDeletePermissionsRequestExecutor
				deDataRecordCollectionDeletePermissionsRequestExecutor =
					getDEDataRecordCollectionDeletePermissionsRequestExecutor();

			return deDataRecordCollectionDeletePermissionsRequestExecutor.
				execute(deDataRecordCollectionDeletePermissionsRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataRecordCollectionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (DEDataRecordCollectionException dedrce) {
			throw dedrce;
		}
		catch (Exception e) {
			throw new DEDataRecordCollectionException(e);
		}
	}

	@Override
	public DEDataRecordCollectionSaveModelPermissionsResponse execute(
			DEDataRecordCollectionSaveModelPermissionsRequest
				deDataRecordCollectionSaveModelPermissionsRequest)
		throws DEDataRecordCollectionException {

		DEDataRecordCollectionSaveModelPermissionsRequestExecutor
			deDataRecordCollectionSaveModelPermissionsRequestExecutor =
				getDEDataRecordCollectionSaveModelPermissionsRequestExecutor();

		try {
			checkPermission(
				deDataRecordCollectionSaveModelPermissionsRequest.
					getScopedGroupId(),
				ActionKeys.DEFINE_PERMISSIONS, getPermissionChecker());

			return deDataRecordCollectionSaveModelPermissionsRequestExecutor.
				execute(deDataRecordCollectionSaveModelPermissionsRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataRecordCollectionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (PrincipalException pe) {
			throw new DEDataRecordCollectionException.PrincipalException(pe);
		}
		catch (Exception e) {
			throw new DEDataRecordCollectionException(e);
		}
	}

	@Override
	public DEDataRecordCollectionSavePermissionsResponse execute(
			DEDataRecordCollectionSavePermissionsRequest
				deDataRecordCollectionSavePermissionsRequest)
		throws DEDataRecordCollectionException {

		try {
			checkPermission(
				deDataRecordCollectionSavePermissionsRequest.getScopedGroupId(),
				ActionKeys.DEFINE_PERMISSIONS, getPermissionChecker());

			DEDataRecordCollectionSavePermissionsRequestExecutor
				deDataRecordCollectionSavePermissionsRequestExecutor =
					getDEDataRecordCollectionSavePermissionsRequestExecutor();

			return deDataRecordCollectionSavePermissionsRequestExecutor.execute(
				deDataRecordCollectionSavePermissionsRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataRecordCollectionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (PrincipalException pe) {
			throw new DEDataRecordCollectionException.PrincipalException(pe);
		}
		catch (DEDataRecordCollectionException dedrce) {
			throw dedrce;
		}
		catch (Exception e) {
			throw new DEDataRecordCollectionException(e);
		}
	}

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

	protected DEDataRecordCollectionDeleteModelPermissionsRequestExecutor
		getDEDataRecordCollectionDeleteModelPermissionsRequestExecutor() {

		if (_deDataRecordCollectionDeleteModelPermissionsRequestExecutor ==
				null) {

			_deDataRecordCollectionDeleteModelPermissionsRequestExecutor =
				new DEDataRecordCollectionDeleteModelPermissionsRequestExecutor(
					resourcePermissionLocalService, roleLocalService);
		}

		return _deDataRecordCollectionDeleteModelPermissionsRequestExecutor;
	}

	protected DEDataRecordCollectionDeletePermissionsRequestExecutor
		getDEDataRecordCollectionDeletePermissionsRequestExecutor() {

		if (_deDataRecordCollectionDeletePermissionsRequestExecutor == null) {
			_deDataRecordCollectionDeletePermissionsRequestExecutor =
				new DEDataRecordCollectionDeletePermissionsRequestExecutor(
					resourcePermissionLocalService, roleLocalService);
		}

		return _deDataRecordCollectionDeletePermissionsRequestExecutor;
	}

	protected DEDataRecordCollectionSaveModelPermissionsRequestExecutor
		getDEDataRecordCollectionSaveModelPermissionsRequestExecutor() {

		if (_deDataRecordCollectionSaveModelPermissionsRequestExecutor ==
				null) {

			_deDataRecordCollectionSaveModelPermissionsRequestExecutor =
				new DEDataRecordCollectionSaveModelPermissionsRequestExecutor(
					resourcePermissionLocalService);
		}

		return _deDataRecordCollectionSaveModelPermissionsRequestExecutor;
	}

	protected DEDataRecordCollectionSavePermissionsRequestExecutor
		getDEDataRecordCollectionSavePermissionsRequestExecutor() {

		if (_deDataRecordCollectionSavePermissionsRequestExecutor == null) {
			_deDataRecordCollectionSavePermissionsRequestExecutor =
				new DEDataRecordCollectionSavePermissionsRequestExecutor(
					resourcePermissionLocalService, roleLocalService);
		}

		return _deDataRecordCollectionSavePermissionsRequestExecutor;
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

	@Reference
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Reference
	protected RoleLocalService roleLocalService;

	private DEDataRecordCollectionDeleteModelPermissionsRequestExecutor
		_deDataRecordCollectionDeleteModelPermissionsRequestExecutor;
	private DEDataRecordCollectionDeletePermissionsRequestExecutor
		_deDataRecordCollectionDeletePermissionsRequestExecutor;
	private DEDataRecordCollectionSaveModelPermissionsRequestExecutor
		_deDataRecordCollectionSaveModelPermissionsRequestExecutor;
	private DEDataRecordCollectionSavePermissionsRequestExecutor
		_deDataRecordCollectionSavePermissionsRequestExecutor;
	private DEDataRecordCollectionSaveRequestExecutor
		_deDataRecordCollectionSaveRequestExecutor;
	private ModelResourcePermission<DEDataRecordCollection>
		_modelResourcePermission;

}