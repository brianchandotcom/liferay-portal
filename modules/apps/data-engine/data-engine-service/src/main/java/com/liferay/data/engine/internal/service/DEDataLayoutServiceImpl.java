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
import com.liferay.data.engine.exception.DEDataLayoutException;
import com.liferay.data.engine.internal.executor.DEDataLayoutCountRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataLayoutDeleteModelPermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataLayoutDeletePermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataLayoutDeleteRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataLayoutGetRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataLayoutListRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataLayoutSaveModelPermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataLayoutSavePermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataLayoutSaveRequestExecutor;
import com.liferay.data.engine.internal.io.DEDataLayoutDeserializerTracker;
import com.liferay.data.engine.internal.io.DEDataLayoutSerializerTracker;
import com.liferay.data.engine.internal.security.permission.DEDataEnginePermissionSupport;
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.service.DEDataLayoutCountRequest;
import com.liferay.data.engine.service.DEDataLayoutCountResponse;
import com.liferay.data.engine.service.DEDataLayoutDeleteModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataLayoutDeleteModelPermissionsResponse;
import com.liferay.data.engine.service.DEDataLayoutDeletePermissionsRequest;
import com.liferay.data.engine.service.DEDataLayoutDeletePermissionsResponse;
import com.liferay.data.engine.service.DEDataLayoutDeleteRequest;
import com.liferay.data.engine.service.DEDataLayoutDeleteResponse;
import com.liferay.data.engine.service.DEDataLayoutGetRequest;
import com.liferay.data.engine.service.DEDataLayoutGetResponse;
import com.liferay.data.engine.service.DEDataLayoutListRequest;
import com.liferay.data.engine.service.DEDataLayoutListResponse;
import com.liferay.data.engine.service.DEDataLayoutSaveModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataLayoutSaveModelPermissionsResponse;
import com.liferay.data.engine.service.DEDataLayoutSavePermissionsRequest;
import com.liferay.data.engine.service.DEDataLayoutSavePermissionsResponse;
import com.liferay.data.engine.service.DEDataLayoutSaveRequest;
import com.liferay.data.engine.service.DEDataLayoutSaveResponse;
import com.liferay.data.engine.service.DEDataLayoutService;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = DEDataLayoutService.class)
public class DEDataLayoutServiceImpl
	extends DEBaseServiceImpl implements DEDataLayoutService {

	public DEDataLayoutCountResponse execute(
			DEDataLayoutCountRequest deDataLayoutCountRequest)
		throws DEDataLayoutException {

		return _deDataLayoutCountRequestExecutor.execute(
			deDataLayoutCountRequest);
	}

	@Override
	public DEDataLayoutDeleteModelPermissionsResponse execute(
			DEDataLayoutDeleteModelPermissionsRequest
				deDataLayoutDeleteModelPermissionsRequest)
		throws DEDataLayoutException {

		try {
			checkPermission(
				deDataLayoutDeleteModelPermissionsRequest.getScopedGroupId(),
				ActionKeys.DEFINE_PERMISSIONS, getPermissionChecker());

			return _deDataLayoutDeleteModelPermissionsRequestExecutor.execute(
				deDataLayoutDeleteModelPermissionsRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataLayoutException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (DEDataLayoutException dedle) {
			throw dedle;
		}
		catch (Exception e) {
			throw new DEDataLayoutException(e);
		}
	}

	@Override
	public DEDataLayoutDeletePermissionsResponse execute(
			DEDataLayoutDeletePermissionsRequest
				deDataLayoutDeletePermissionsRequest)
		throws DEDataLayoutException {

		try {
			checkPermission(
				deDataLayoutDeletePermissionsRequest.getScopedGroupId(),
				ActionKeys.DEFINE_PERMISSIONS, getPermissionChecker());

			return _deDataLayoutDeletePermissionsRequestExecutor.execute(
				deDataLayoutDeletePermissionsRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataLayoutException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (DEDataLayoutException dedle) {
			throw dedle;
		}
		catch (Exception e) {
			throw new DEDataLayoutException(e);
		}
	}

	@Override
	public DEDataLayoutDeleteResponse execute(
			DEDataLayoutDeleteRequest deDataLayoutDeleteRequest)
		throws DEDataLayoutException {

		try {
			long deDataLayoutId = deDataLayoutDeleteRequest.getDEDataLayoutId();

			_modelResourcePermission.check(
				getPermissionChecker(), deDataLayoutId, ActionKeys.DELETE);

			return _deDataLayoutDeleteRequestExecutor.execute(
				deDataLayoutDeleteRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataLayoutException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (NoSuchStructureLayoutException nssle) {
			throw new DEDataLayoutException.NoSuchDataLayout(
				deDataLayoutDeleteRequest.getDEDataLayoutId(), nssle);
		}
		catch (Exception e) {
			throw new DEDataLayoutException(e);
		}
	}

	@Override
	public DEDataLayoutGetResponse execute(
			DEDataLayoutGetRequest deDataLayoutGetRequest)
		throws DEDataLayoutException {

		long deDataLayoutId = deDataLayoutGetRequest.getDEDataLayoutId();

		try {
			_modelResourcePermission.check(
				getPermissionChecker(), deDataLayoutId, ActionKeys.VIEW);

			return _deDataLayoutGetRequestExecutor.execute(
				deDataLayoutGetRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataLayoutException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (NoSuchStructureLayoutException nssle) {
			throw new DEDataLayoutException.NoSuchDataLayout(
				deDataLayoutId, nssle);
		}
		catch (Exception e) {
			throw new DEDataLayoutException(e);
		}
	}

	@Override
	public DEDataLayoutListResponse execute(
			DEDataLayoutListRequest deDataLayoutListRequest)
		throws DEDataLayoutException {

		try {
			return _deDataLayoutListRequestExecutor.execute(
				deDataLayoutListRequest);
		}
		catch (DEDataLayoutException dedle) {
			throw dedle;
		}
		catch (Exception e) {
			throw new DEDataLayoutException(e);
		}
	}

	public DEDataLayoutSaveModelPermissionsResponse execute(
			DEDataLayoutSaveModelPermissionsRequest
				deDataLayoutSaveModelPermissionsRequest)
		throws DEDataLayoutException {

		try {
			checkPermission(
				deDataLayoutSaveModelPermissionsRequest.getScopedGroupId(),
				ActionKeys.DEFINE_PERMISSIONS, getPermissionChecker());

			return _deDataLayoutSaveModelPermissionsRequestExecutor.execute(
				deDataLayoutSaveModelPermissionsRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataLayoutException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (PrincipalException pe) {
			throw new DEDataLayoutException.PrincipalException(pe);
		}
		catch (Exception e) {
			throw new DEDataLayoutException(e);
		}
	}

	@Override
	public DEDataLayoutSavePermissionsResponse execute(
			DEDataLayoutSavePermissionsRequest
				deDataLayoutSavePermissionsRequest)
		throws DEDataLayoutException {

		try {
			checkPermission(
				deDataLayoutSavePermissionsRequest.getScopedGroupId(),
				ActionKeys.DEFINE_PERMISSIONS, getPermissionChecker());

			return _deDataLayoutSavePermissionsRequestExecutor.execute(
				deDataLayoutSavePermissionsRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataLayoutException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (PrincipalException pe) {
			throw new DEDataLayoutException.PrincipalException(pe);
		}
		catch (DEDataLayoutException dedle) {
			throw dedle;
		}
		catch (Exception e) {
			throw new DEDataLayoutException(e);
		}
	}

	@Override
	public DEDataLayoutSaveResponse execute(
			DEDataLayoutSaveRequest deDataLayoutSaveRequest)
		throws DEDataLayoutException {

		DEDataLayout deDataLayout = deDataLayoutSaveRequest.getDEDataLayout();

		try {
			long deDataLayoutId = deDataLayout.getDEDataLayoutId();

			if (deDataLayoutId == 0) {
				checkPermission(
					deDataLayoutSaveRequest.getGroupId(),
					DEActionKeys.ADD_DATA_LAYOUT, getPermissionChecker());
			}
			else {
				_modelResourcePermission.check(
					getPermissionChecker(), deDataLayoutId, ActionKeys.UPDATE);
			}

			return _deDataLayoutSaveRequestExecutor.execute(
				deDataLayoutSaveRequest);
		}
		catch (NoSuchStructureException nsse) {
			throw new DEDataLayoutException.NoSuchDataLayout(
				deDataLayout.getDEDataLayoutId(), nsse);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataLayoutException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (DEDataLayoutException dedle) {
			throw dedle;
		}
		catch (Exception e) {
			throw new DEDataLayoutException(e);
		}
	}

	@Override
	protected DEDataEnginePermissionSupport getDEDataEnginePermissionSupport() {
		return new DEDataEnginePermissionSupport(groupLocalService);
	}

	@Reference(
		target = "(model.class.name=com.liferay.data.engine.model.DEDataLayout)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<DEDataLayout> modelResourcePermission) {

		_modelResourcePermission = modelResourcePermission;
	}

	@Activate
	protected void setUpExecutors() {
		_deDataLayoutCountRequestExecutor =
			new DEDataLayoutCountRequestExecutor(
				_ddmStructureLayoutLocalService);

		_deDataLayoutDeleteModelPermissionsRequestExecutor =
			new DEDataLayoutDeleteModelPermissionsRequestExecutor(
				resourcePermissionLocalService, roleLocalService);

		_deDataLayoutDeletePermissionsRequestExecutor =
			new DEDataLayoutDeletePermissionsRequestExecutor(
				resourcePermissionLocalService, roleLocalService);

		_deDataLayoutDeleteRequestExecutor =
			new DEDataLayoutDeleteRequestExecutor(
				_ddmStructureLayoutLocalService);

		_deDataLayoutGetRequestExecutor = new DEDataLayoutGetRequestExecutor(
			_ddmStructureLayoutLocalService, _ddmStructureVersionLocalService,
			_deDataLayoutDeserializerTracker);

		_deDataLayoutListRequestExecutor = new DEDataLayoutListRequestExecutor(
			_ddmStructureLayoutLocalService, _ddmStructureVersionLocalService,
			_deDataLayoutDeserializerTracker);

		_deDataLayoutSaveModelPermissionsRequestExecutor =
			new DEDataLayoutSaveModelPermissionsRequestExecutor(
				resourcePermissionLocalService);

		_deDataLayoutSavePermissionsRequestExecutor =
			new DEDataLayoutSavePermissionsRequestExecutor(
				resourcePermissionLocalService, roleLocalService);

		_deDataLayoutSaveRequestExecutor = new DEDataLayoutSaveRequestExecutor(
			_ddmStructureLayoutLocalService, _ddmStructureVersionLocalService,
			_ddmStructureLocalService, _deDataLayoutSerializerTracker,
			resourceLocalService);
	}

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected ResourceLocalService resourceLocalService;

	@Reference
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Reference
	protected RoleLocalService roleLocalService;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	private DEDataLayoutCountRequestExecutor _deDataLayoutCountRequestExecutor;
	private DEDataLayoutDeleteModelPermissionsRequestExecutor
		_deDataLayoutDeleteModelPermissionsRequestExecutor;
	private DEDataLayoutDeletePermissionsRequestExecutor
		_deDataLayoutDeletePermissionsRequestExecutor;
	private DEDataLayoutDeleteRequestExecutor
		_deDataLayoutDeleteRequestExecutor;

	@Reference
	private DEDataLayoutDeserializerTracker _deDataLayoutDeserializerTracker;

	private DEDataLayoutGetRequestExecutor _deDataLayoutGetRequestExecutor;
	private DEDataLayoutListRequestExecutor _deDataLayoutListRequestExecutor;
	private DEDataLayoutSaveModelPermissionsRequestExecutor
		_deDataLayoutSaveModelPermissionsRequestExecutor;
	private DEDataLayoutSavePermissionsRequestExecutor
		_deDataLayoutSavePermissionsRequestExecutor;
	private DEDataLayoutSaveRequestExecutor _deDataLayoutSaveRequestExecutor;

	@Reference
	private DEDataLayoutSerializerTracker _deDataLayoutSerializerTracker;

	private ModelResourcePermission<DEDataLayout> _modelResourcePermission;

}