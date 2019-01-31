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
import com.liferay.data.engine.exception.DEDataDefinitionException;
import com.liferay.data.engine.internal.executor.DEDataDefinitionCountRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataDefinitionDeleteModelPermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataDefinitionDeletePermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataDefinitionDeleteRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataDefinitionGetRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataDefinitionListRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataDefinitionSaveModelPermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataDefinitionSavePermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataDefinitionSaveRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataDefinitionSearchCountExecutor;
import com.liferay.data.engine.internal.executor.DEDataDefinitionSearchExecutor;
import com.liferay.data.engine.internal.executor.DEDataEngineRequestExecutor;
import com.liferay.data.engine.internal.io.DEDataDefinitionFieldsDeserializerTracker;
import com.liferay.data.engine.internal.io.DEDataDefinitionFieldsSerializerTracker;
import com.liferay.data.engine.internal.security.permission.DEDataEnginePermissionSupport;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.service.DEDataDefinitionCountRequest;
import com.liferay.data.engine.service.DEDataDefinitionCountResponse;
import com.liferay.data.engine.service.DEDataDefinitionDeleteModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataDefinitionDeleteModelPermissionsResponse;
import com.liferay.data.engine.service.DEDataDefinitionDeletePermissionsRequest;
import com.liferay.data.engine.service.DEDataDefinitionDeletePermissionsResponse;
import com.liferay.data.engine.service.DEDataDefinitionDeleteRequest;
import com.liferay.data.engine.service.DEDataDefinitionDeleteResponse;
import com.liferay.data.engine.service.DEDataDefinitionGetRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetResponse;
import com.liferay.data.engine.service.DEDataDefinitionListRequest;
import com.liferay.data.engine.service.DEDataDefinitionListResponse;
import com.liferay.data.engine.service.DEDataDefinitionSaveModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveModelPermissionsResponse;
import com.liferay.data.engine.service.DEDataDefinitionSavePermissionsRequest;
import com.liferay.data.engine.service.DEDataDefinitionSavePermissionsResponse;
import com.liferay.data.engine.service.DEDataDefinitionSaveRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveResponse;
import com.liferay.data.engine.service.DEDataDefinitionSearchCountRequest;
import com.liferay.data.engine.service.DEDataDefinitionSearchCountResponse;
import com.liferay.data.engine.service.DEDataDefinitionSearchRequest;
import com.liferay.data.engine.service.DEDataDefinitionSearchResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
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
@Component(immediate = true, service = DEDataDefinitionService.class)
public class DEDataDefinitionServiceImpl
	extends DEBaseServiceImpl implements DEDataDefinitionService {

	@Override
	public DEDataDefinitionCountResponse execute(
		DEDataDefinitionCountRequest deDataDefinitionCountRequest) {

		DEDataDefinitionCountRequestExecutor
			deDataDefinitionCountRequestExecutor =
				getDEDataDefinitionCountRequestExecutor();

		return deDataDefinitionCountRequestExecutor.execute(
			deDataDefinitionCountRequest);
	}

	@Override
	public DEDataDefinitionDeleteModelPermissionsResponse execute(
			DEDataDefinitionDeleteModelPermissionsRequest
				deDataDefinitionDeleteModelPermissionsRequest)
		throws DEDataDefinitionException {

		DEDataDefinitionDeleteModelPermissionsRequestExecutor
			deDataDefinitionDeleteModelPermissionsRequestExecutor =
				getDEDataDefinitionDeleteModelPermissionsRequestExecutor();

		try {
			checkPermission(
				deDataDefinitionDeleteModelPermissionsRequest.
					getScopedGroupId(),
				ActionKeys.DEFINE_PERMISSIONS, getPermissionChecker());

			return deDataDefinitionDeleteModelPermissionsRequestExecutor.
				execute(deDataDefinitionDeleteModelPermissionsRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataDefinitionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (DEDataDefinitionException dedde) {
			throw dedde;
		}
		catch (Exception e) {
			throw new DEDataDefinitionException(e);
		}
	}

	@Override
	public DEDataDefinitionDeletePermissionsResponse execute(
			DEDataDefinitionDeletePermissionsRequest
				deDataDefinitionDeletePermissionsRequest)
		throws DEDataDefinitionException {

		try {
			checkPermission(
				deDataDefinitionDeletePermissionsRequest.getScopedGroupId(),
				ActionKeys.DEFINE_PERMISSIONS, getPermissionChecker());

			DEDataDefinitionDeletePermissionsRequestExecutor
				deDataDefinitionDeletePermissionsRequestExecutor =
					getDEDataDefinitionDeletePermissionsRequestExecutor();

			return deDataDefinitionDeletePermissionsRequestExecutor.execute(
				deDataDefinitionDeletePermissionsRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataDefinitionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (DEDataDefinitionException dedde) {
			throw dedde;
		}
		catch (Exception e) {
			throw new DEDataDefinitionException(e);
		}
	}

	@Override
	public DEDataDefinitionDeleteResponse execute(
			DEDataDefinitionDeleteRequest deDataDefinitionDeleteRequest)
		throws DEDataDefinitionException {

		try {
			long deDataDefinitionId =
				deDataDefinitionDeleteRequest.getDEDataDefinitionId();

			_modelResourcePermission.check(
				getPermissionChecker(), deDataDefinitionId, ActionKeys.DELETE);

			DEDataDefinitionDeleteRequestExecutor
				deDataDefinitionDeleteRequestExecutor =
					getDEDataDefinitionDeleteRequestExecutor();

			return deDataDefinitionDeleteRequestExecutor.execute(
				deDataDefinitionDeleteRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataDefinitionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (NoSuchStructureException nsse) {
			throw new DEDataDefinitionException.NoSuchDataDefinition(
				deDataDefinitionDeleteRequest.getDEDataDefinitionId(), nsse);
		}
		catch (Exception e) {
			throw new DEDataDefinitionException(e);
		}
	}

	@Override
	public DEDataDefinitionGetResponse execute(
			DEDataDefinitionGetRequest deDataDefinitionGetRequest)
		throws DEDataDefinitionException {

		try {
			long deDataDefinitionId =
				deDataDefinitionGetRequest.getDEDataDefinitionId();

			_modelResourcePermission.check(
				getPermissionChecker(), deDataDefinitionId, ActionKeys.VIEW);

			DEDataDefinitionGetRequestExecutor
				deDataDefinitionGetRequestExecutor =
					getDEDataDefinitionGetRequestExecutor();

			return deDataDefinitionGetRequestExecutor.execute(
				deDataDefinitionGetRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataDefinitionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (NoSuchStructureException nsse) {
			throw new DEDataDefinitionException.NoSuchDataDefinition(
				deDataDefinitionGetRequest.getDEDataDefinitionId(), nsse);
		}
		catch (Exception e) {
			throw new DEDataDefinitionException(e);
		}
	}

	@Override
	public DEDataDefinitionListResponse execute(
			DEDataDefinitionListRequest deDataDefinitionListRequest)
		throws DEDataDefinitionException {

		DEDataDefinitionListRequestExecutor
			deDataDefinitionListRequestExecutor =
				getDEDataDefinitionListRequestExecutor();

		try {
			return deDataDefinitionListRequestExecutor.execute(
				deDataDefinitionListRequest);
		}
		catch (DEDataDefinitionException dedde) {
			throw dedde;
		}
		catch (Exception e) {
			throw new DEDataDefinitionException(e);
		}
	}

	public DEDataDefinitionSaveModelPermissionsResponse execute(
			DEDataDefinitionSaveModelPermissionsRequest
				deDataDefinitionSaveModelPermissionsRequest)
		throws DEDataDefinitionException {

		try {
			checkPermission(
				deDataDefinitionSaveModelPermissionsRequest.getScopedGroupId(),
				ActionKeys.DEFINE_PERMISSIONS, getPermissionChecker());

			DEDataDefinitionSaveModelPermissionsRequestExecutor
				deDataDefinitionSaveModelPermissionsRequestExecutor =
					getDEDataDefinitionSaveModelPermissionsRequestExecutor();

			return deDataDefinitionSaveModelPermissionsRequestExecutor.execute(
				deDataDefinitionSaveModelPermissionsRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataDefinitionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (PrincipalException pe) {
			throw new DEDataDefinitionException.PrincipalException(pe);
		}
		catch (Exception e) {
			throw new DEDataDefinitionException(e);
		}
	}

	@Override
	public DEDataDefinitionSavePermissionsResponse execute(
			DEDataDefinitionSavePermissionsRequest
				deDataDefinitionSavePermissionsRequest)
		throws DEDataDefinitionException {

		try {
			checkPermission(
				deDataDefinitionSavePermissionsRequest.getScopedGroupId(),
				ActionKeys.DEFINE_PERMISSIONS, getPermissionChecker());

			DEDataDefinitionSavePermissionsRequestExecutor
				deDataDefinitionSavePermissionsRequestExecutor =
					getDEDataDefinitionSavePermissionsRequestExecutor();

			return deDataDefinitionSavePermissionsRequestExecutor.execute(
				deDataDefinitionSavePermissionsRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataDefinitionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (PrincipalException pe) {
			throw new DEDataDefinitionException.PrincipalException(pe);
		}
		catch (DEDataDefinitionException dedde) {
			throw dedde;
		}
		catch (Exception e) {
			throw new DEDataDefinitionException(e);
		}
	}

	@Override
	public DEDataDefinitionSaveResponse execute(
			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest)
		throws DEDataDefinitionException {

		DEDataDefinition deDataDefinition =
			deDataDefinitionSaveRequest.getDEDataDefinition();

		try {
			long deDataDefinitionId = deDataDefinition.getDEDataDefinitionId();

			if (deDataDefinitionId == 0) {
				checkPermission(
					deDataDefinitionSaveRequest.getGroupId(),
					DEActionKeys.ADD_DATA_DEFINITION_ACTION,
					getPermissionChecker());
			}
			else {
				_modelResourcePermission.check(
					getPermissionChecker(), deDataDefinitionId,
					ActionKeys.UPDATE);
			}

			DEDataDefinitionSaveRequestExecutor
				deDataDefinitionSaveRequestExecutor =
					getDEDataDefinitionSaveRequestExecutor();

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				deDataDefinitionSaveRequestExecutor.execute(
					deDataDefinitionSaveRequest);

			return DEDataDefinitionSaveResponse.Builder.of(
				deDataDefinitionSaveResponse.getDEDataDefinitionId());
		}
		catch (DEDataDefinitionException dedde) {
			throw dedde;
		}
		catch (NoSuchStructureException nsse) {
			throw new DEDataDefinitionException.NoSuchDataDefinition(
				deDataDefinition.getDEDataDefinitionId(), nsse);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataDefinitionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (Exception e) {
			throw new DEDataDefinitionException(e);
		}
	}

	public DEDataDefinitionSearchCountResponse execute(
		DEDataDefinitionSearchCountRequest deDataDefinitionSearchCountRequest) {

		DEDataDefinitionSearchCountExecutor
			deDataDefinitionSearchCountExecutor =
				getDEDataDefinitionSearchCountExecutor();

		return deDataDefinitionSearchCountExecutor.execute(
			deDataDefinitionSearchCountRequest);
	}

	public DEDataDefinitionSearchResponse execute(
			DEDataDefinitionSearchRequest deDataDefinitionSearchRequest)
		throws DEDataDefinitionException {

		DEDataDefinitionSearchExecutor deDataDefinitionSearchExecutor =
			getDEDataDefinitionSearchExecutor();

		try {
			return deDataDefinitionSearchExecutor.execute(
				deDataDefinitionSearchRequest);
		}
		catch (Exception e) {
			throw new DEDataDefinitionException(e);
		}
	}

	public DEDataDefinitionCountRequestExecutor
		getDEDataDefinitionCountRequestExecutor() {

		if (_deDataDefinitionCountRequestExecutor == null) {
			_deDataDefinitionCountRequestExecutor =
				new DEDataDefinitionCountRequestExecutor(
					ddmStructureService, portal);
		}

		return _deDataDefinitionCountRequestExecutor;
	}

	public DEDataDefinitionDeleteRequestExecutor
		getDEDataDefinitionDeleteRequestExecutor() {

		if (_deDataDefinitionDeleteRequestExecutor == null) {
			_deDataDefinitionDeleteRequestExecutor =
				new DEDataDefinitionDeleteRequestExecutor(
					ddlRecordSetLocalService, ddmStructureLocalService);
		}

		return _deDataDefinitionDeleteRequestExecutor;
	}

	public DEDataDefinitionGetRequestExecutor
		getDEDataDefinitionGetRequestExecutor() {

		if (_deDataDefinitionGetRequestExecutor == null) {
			_deDataDefinitionGetRequestExecutor =
				new DEDataDefinitionGetRequestExecutor(
					ddmStructureLocalService,
					deDataDefinitionFieldsDeserializerTracker);
		}

		return _deDataDefinitionGetRequestExecutor;
	}

	public DEDataDefinitionListRequestExecutor
		getDEDataDefinitionListRequestExecutor() {

		if (_deDataDefinitionListRequestExecutor == null) {
			_deDataDefinitionListRequestExecutor =
				new DEDataDefinitionListRequestExecutor(
					ddmStructureService,
					deDataDefinitionFieldsDeserializerTracker, portal);
		}

		return _deDataDefinitionListRequestExecutor;
	}

	public DEDataDefinitionSaveModelPermissionsRequestExecutor
		getDEDataDefinitionSaveModelPermissionsRequestExecutor() {

		if (_deDataDefinitionSaveModelPermissionsRequestExecutor == null) {
			_deDataDefinitionSaveModelPermissionsRequestExecutor =
				new DEDataDefinitionSaveModelPermissionsRequestExecutor(
					resourcePermissionLocalService);
		}

		return _deDataDefinitionSaveModelPermissionsRequestExecutor;
	}

	public DEDataDefinitionSavePermissionsRequestExecutor
		getDEDataDefinitionSavePermissionsRequestExecutor() {

		if (_deDataDefinitionSavePermissionsRequestExecutor == null) {
			_deDataDefinitionSavePermissionsRequestExecutor =
				new DEDataDefinitionSavePermissionsRequestExecutor(
					resourcePermissionLocalService, roleLocalService);
		}

		return _deDataDefinitionSavePermissionsRequestExecutor;
	}

	public DEDataDefinitionSaveRequestExecutor
		getDEDataDefinitionSaveRequestExecutor() {

		if (_deDataDefinitionSaveRequestExecutor == null) {
			_deDataDefinitionSaveRequestExecutor =
				new DEDataDefinitionSaveRequestExecutor(
					ddlRecordSetLocalService, ddmStructureLocalService,
					deDataDefinitionFieldsSerializerTracker, portal,
					resourceLocalService);
		}

		return _deDataDefinitionSaveRequestExecutor;
	}

	public DEDataDefinitionSearchCountExecutor
		getDEDataDefinitionSearchCountExecutor() {

		if (_deDataDefinitionSearchCountExecutor == null) {
			_deDataDefinitionSearchCountExecutor =
				new DEDataDefinitionSearchCountExecutor(
					ddmStructureService, _deDataEngineRequestExecutor, portal);
		}

		return _deDataDefinitionSearchCountExecutor;
	}

	public DEDataDefinitionSearchExecutor getDEDataDefinitionSearchExecutor() {
		if (_deDataDefinitionSearchExecutor == null) {
			_deDataDefinitionSearchExecutor =
				new DEDataDefinitionSearchExecutor(
					ddmStructureService, getDEDataEngineRequestExecutor(),
					portal);
		}

		return _deDataDefinitionSearchExecutor;
	}

	public DEDataEngineRequestExecutor getDEDataEngineRequestExecutor() {
		if (_deDataEngineRequestExecutor == null) {
			_deDataEngineRequestExecutor = new DEDataEngineRequestExecutor(
				deDataDefinitionFieldsDeserializerTracker);
		}

		return _deDataEngineRequestExecutor;
	}

	protected DEDataDefinitionDeleteModelPermissionsRequestExecutor
		getDEDataDefinitionDeleteModelPermissionsRequestExecutor() {

		if (_deDataDefinitionDeleteModelPermissionsRequestExecutor == null) {
			_deDataDefinitionDeleteModelPermissionsRequestExecutor =
				new DEDataDefinitionDeleteModelPermissionsRequestExecutor(
					resourcePermissionLocalService, roleLocalService);
		}

		return _deDataDefinitionDeleteModelPermissionsRequestExecutor;
	}

	protected DEDataDefinitionDeletePermissionsRequestExecutor
		getDEDataDefinitionDeletePermissionsRequestExecutor() {

		if (_deDataDefinitionDeletePermissionsRequestExecutor == null) {
			_deDataDefinitionDeletePermissionsRequestExecutor =
				new DEDataDefinitionDeletePermissionsRequestExecutor(
					resourcePermissionLocalService, roleLocalService);
		}

		return _deDataDefinitionDeletePermissionsRequestExecutor;
	}

	@Override
	protected DEDataEnginePermissionSupport getDEDataEnginePermissionSupport() {
		return new DEDataEnginePermissionSupport(groupLocalService);
	}

	@Reference(
		target = "(model.class.name=com.liferay.data.engine.model.DEDataDefinition)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<DEDataDefinition> modelResourcePermission) {

		_modelResourcePermission = modelResourcePermission;
	}

	@Reference
	protected DDLRecordSetLocalService ddlRecordSetLocalService;

	@Reference
	protected DDMStructureLocalService ddmStructureLocalService;

	@Reference
	protected DDMStructureService ddmStructureService;

	@Reference
	protected DEDataDefinitionFieldsDeserializerTracker
		deDataDefinitionFieldsDeserializerTracker;

	@Reference
	protected DEDataDefinitionFieldsSerializerTracker
		deDataDefinitionFieldsSerializerTracker;

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

	private DEDataDefinitionCountRequestExecutor
		_deDataDefinitionCountRequestExecutor;
	private DEDataDefinitionDeleteModelPermissionsRequestExecutor
		_deDataDefinitionDeleteModelPermissionsRequestExecutor;
	private DEDataDefinitionDeletePermissionsRequestExecutor
		_deDataDefinitionDeletePermissionsRequestExecutor;
	private DEDataDefinitionDeleteRequestExecutor
		_deDataDefinitionDeleteRequestExecutor;
	private DEDataDefinitionGetRequestExecutor
		_deDataDefinitionGetRequestExecutor;
	private DEDataDefinitionListRequestExecutor
		_deDataDefinitionListRequestExecutor;
	private DEDataDefinitionSaveModelPermissionsRequestExecutor
		_deDataDefinitionSaveModelPermissionsRequestExecutor;
	private DEDataDefinitionSavePermissionsRequestExecutor
		_deDataDefinitionSavePermissionsRequestExecutor;
	private DEDataDefinitionSaveRequestExecutor
		_deDataDefinitionSaveRequestExecutor;
	private DEDataDefinitionSearchCountExecutor
		_deDataDefinitionSearchCountExecutor;
	private DEDataDefinitionSearchExecutor _deDataDefinitionSearchExecutor;
	private DEDataEngineRequestExecutor _deDataEngineRequestExecutor;
	private ModelResourcePermission<DEDataDefinition> _modelResourcePermission;

}