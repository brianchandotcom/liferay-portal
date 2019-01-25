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
import com.liferay.data.engine.internal.executor.DEDataEngineRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionDeleteModelPermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionDeletePermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionDeleteRecordRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionDeleteRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionGetRecordRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionGetRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionSaveModelPermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionSavePermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionSaveRecordRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionSaveRequestExecutor;
import com.liferay.data.engine.internal.io.DEDataDefinitionFieldsDeserializerTracker;
import com.liferay.data.engine.internal.security.permission.DEDataEnginePermissionSupport;
import com.liferay.data.engine.internal.storage.DEDataStorageTracker;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.model.DEDataRecord;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.data.engine.service.DEDataRecordCollectionDeleteModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionDeleteModelPermissionsResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionDeletePermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionDeletePermissionsResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionDeleteRecordRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionDeleteRecordResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionDeleteRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionDeleteResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionGetRecordRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionGetRecordResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionGetRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionGetResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionRequestBuilder;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveModelPermissionsResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionSavePermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSavePermissionsResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveRecordRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveRecordResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionService;
import com.liferay.dynamic.data.lists.exception.NoSuchRecordException;
import com.liferay.dynamic.data.lists.exception.NoSuchRecordSetException;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	public DEDataRecordCollectionDeleteRecordResponse execute(
			DEDataRecordCollectionDeleteRecordRequest
				deDataRecordCollectionDeleteRecordRequest)
		throws DEDataRecordCollectionException {

		long deDataRecordId =
			deDataRecordCollectionDeleteRecordRequest.getDEDataRecordId();

		try {
			DEDataRecordCollectionGetRecordRequestExecutor
				deDataRecordCollectionGetRecordRequestExecutor =
					getDEDataRecordCollectionGetRecordRequestExecutor();

			DEDataRecordCollectionGetRecordRequest
				deDataRecordCollectionGetRecordRequest =
					DEDataRecordCollectionRequestBuilder.getRecordBuilder(
						deDataRecordId
					).build();

			DEDataRecordCollectionGetRecordResponse
				deDataRecordCollectionGetRecordResponse =
					deDataRecordCollectionGetRecordRequestExecutor.execute(
						deDataRecordCollectionGetRecordRequest);

			DEDataRecord deDataRecord =
				deDataRecordCollectionGetRecordResponse.getDEDataRecord();

			_modelResourcePermission.check(
				getPermissionChecker(),
				deDataRecord.getDEDataRecordCollectionId(),
				DEActionKeys.DELETE_DATA_RECORD);

			DEDataRecordCollectionDeleteRecordRequestExecutor
				deDataRecordCollectionDeleteRecordRequestExecutor =
					getDEDataRecordCollectionDeleteRecordRequestExecutor();

			deDataRecordCollectionDeleteRecordRequestExecutor.execute(
				deDataRecordCollectionDeleteRecordRequest);

			return DEDataRecordCollectionDeleteRecordResponse.Builder.of(
				deDataRecordId);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataRecordCollectionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (NoSuchRecordException nsre) {
			throw new DEDataRecordCollectionException.NoSuchDataRecord(
				deDataRecordId, nsre);
		}
		catch (DEDataRecordCollectionException dedrce) {
			throw dedrce;
		}
		catch (Exception e) {
			throw new DEDataRecordCollectionException.DeleteDataRecord(e);
		}
	}

	@Override
	public DEDataRecordCollectionDeleteResponse execute(
			DEDataRecordCollectionDeleteRequest
				deDataRecordCollectionDeleteRequest)
		throws DEDataRecordCollectionException {

		try {
			long deDataRecordCollectionId =
				deDataRecordCollectionDeleteRequest.
					getDEDataRecordCollectionId();

			_modelResourcePermission.check(
				getPermissionChecker(), deDataRecordCollectionId,
				ActionKeys.DELETE);

			DEDataRecordCollectionDeleteRequestExecutor
				deDataRecordCollectionDeleteRequestExecutor =
					getDEDataRecordCollectionDeleteRequestExecutor();

			return deDataRecordCollectionDeleteRequestExecutor.execute(
				deDataRecordCollectionDeleteRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataRecordCollectionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (NoSuchRecordSetException nsrse) {
			throw new DEDataRecordCollectionException.
				NoSuchDataRecordCollection(
					deDataRecordCollectionDeleteRequest.
						getDEDataRecordCollectionId(),
					nsrse);
		}
		catch (Exception e) {
			throw new DEDataRecordCollectionException(e);
		}
	}

	@Override
	public DEDataRecordCollectionGetRecordResponse execute(
			DEDataRecordCollectionGetRecordRequest
				deDataRecordCollectionGetRecordRequest)
		throws DEDataRecordCollectionException {

		try {
			DEDataRecordCollectionGetRecordRequestExecutor
				deDataRecordCollectionGetRecordRequestExecutor =
					getDEDataRecordCollectionGetRecordRequestExecutor();

			DEDataRecordCollectionGetRecordResponse
				deDataRecordCollectionGetRecordResponse =
					deDataRecordCollectionGetRecordRequestExecutor.execute(
						deDataRecordCollectionGetRecordRequest);

			DEDataRecord deDataRecord =
				deDataRecordCollectionGetRecordResponse.getDEDataRecord();

			_modelResourcePermission.check(
				getPermissionChecker(),
				deDataRecord.getDEDataRecordCollectionId(),
				DEActionKeys.VIEW_DATA_RECORD);

			return DEDataRecordCollectionGetRecordResponse.Builder.of(
				deDataRecord);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataRecordCollectionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (NoSuchRecordException nsre) {
			throw new DEDataRecordCollectionException.NoSuchDataRecord(
				deDataRecordCollectionGetRecordRequest.getDEDataRecordId(),
				nsre);
		}
		catch (DEDataRecordCollectionException dedrce) {
			throw dedrce;
		}
		catch (Exception e) {
			throw new DEDataRecordCollectionException.GetDataRecord(e);
		}
	}

	@Override
	public DEDataRecordCollectionGetResponse execute(
			DEDataRecordCollectionGetRequest deDataRecordCollectionGetRequest)
		throws DEDataRecordCollectionException {

		try {
			long deDataRecordCollectionId =
				deDataRecordCollectionGetRequest.getDEDataRecordCollectionId();

			_modelResourcePermission.check(
				getPermissionChecker(), deDataRecordCollectionId,
				ActionKeys.VIEW);

			DEDataRecordCollectionGetRequestExecutor
				deDataRecordCollectionGetRequestExecutor =
					getDEDataRecordCollectionGetRequestExecutor();

			return deDataRecordCollectionGetRequestExecutor.execute(
				deDataRecordCollectionGetRequest);
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataRecordCollectionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (NoSuchRecordSetException nsrse) {
			throw new DEDataRecordCollectionException.
				NoSuchDataRecordCollection(
					deDataRecordCollectionGetRequest.
						getDEDataRecordCollectionId(),
					nsrse);
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
	public DEDataRecordCollectionSaveRecordResponse execute(
			DEDataRecordCollectionSaveRecordRequest
				deDataRecordCollectionSaveRecordRequest)
		throws DEDataRecordCollectionException {

		DEDataRecord deDataRecord =
			deDataRecordCollectionSaveRecordRequest.getDEDataRecord();

		try {
			if (deDataRecord.getDEDataRecordId() == 0) {
				_modelResourcePermission.check(
					getPermissionChecker(),
					deDataRecord.getDEDataRecordCollectionId(),
					DEActionKeys.ADD_DATA_RECORD);
			}
			else {
				_modelResourcePermission.check(
					getPermissionChecker(),
					deDataRecord.getDEDataRecordCollectionId(),
					DEActionKeys.UPDATE_DATA_RECORD);
			}

			verifyDataDefinitionFields(deDataRecordCollectionSaveRecordRequest);

			DEDataRecordCollectionSaveRecordRequestExecutor
				deDataRecordCollectionSaveRecordRequestExecutor =
					getDEDataRecordCollectionSaveRecordRequestExecutor();

			DEDataRecordCollectionSaveRecordResponse
				deDataRecordCollectionSaveRecordResponse =
					deDataRecordCollectionSaveRecordRequestExecutor.execute(
						deDataRecordCollectionSaveRecordRequest);

			return DEDataRecordCollectionSaveRecordResponse.Builder.of(
				deDataRecordCollectionSaveRecordResponse.getDEDataRecord());
		}
		catch (PrincipalException.MustHavePermission mhp) {
			throw new DEDataRecordCollectionException.MustHavePermission(
				mhp.actionId, mhp);
		}
		catch (NoSuchRecordSetException nsrse) {
			throw new DEDataRecordCollectionException.
				NoSuchDataRecordCollection(
					deDataRecord.getDEDataRecordCollectionId(), nsrse);
		}
		catch (DEDataRecordCollectionException dedrce) {
			throw dedrce;
		}
		catch (Exception e) {
			throw new DEDataRecordCollectionException.AddDataRecord(e);
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

			return deDataRecordCollectionSaveRequestExecutor.execute(
				deDataRecordCollectionSaveRequest);
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

	protected Map<String, DEDataDefinitionField> getDEDataDefinitionFieldsMap(
		DEDataDefinition deDataDefinition) {

		List<DEDataDefinitionField> deDataDefinitionFields =
			deDataDefinition.getDEDataDefinitionFields();

		Stream<DEDataDefinitionField> stream = deDataDefinitionFields.stream();

		return stream.collect(
			Collectors.toMap(field -> field.getName(), Function.identity()));
	}

	@Override
	protected DEDataEnginePermissionSupport getDEDataEnginePermissionSupport() {
		return new DEDataEnginePermissionSupport(groupLocalService);
	}

	protected DEDataEngineRequestExecutor getDEDataEngineRequestExecutor() {
		if (_deDataEngineRequestExecutor == null) {
			_deDataEngineRequestExecutor = new DEDataEngineRequestExecutor(
				deDataDefinitionFieldsDeserializerTracker,
				deDataStorageTracker);
		}

		return _deDataEngineRequestExecutor;
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

	protected DEDataRecordCollectionDeleteRecordRequestExecutor
		getDEDataRecordCollectionDeleteRecordRequestExecutor() {

		if (_deDataRecordCollectionDeleteRecordRequestExecutor == null) {
			_deDataRecordCollectionDeleteRecordRequestExecutor =
				new DEDataRecordCollectionDeleteRecordRequestExecutor(
					deDataStorageTracker, ddlRecordLocalService);
		}

		return _deDataRecordCollectionDeleteRecordRequestExecutor;
	}

	protected DEDataRecordCollectionDeleteRequestExecutor
		getDEDataRecordCollectionDeleteRequestExecutor() {

		if (_deDataRecordCollectionDeleteRequestExecutor == null) {
			_deDataRecordCollectionDeleteRequestExecutor =
				new DEDataRecordCollectionDeleteRequestExecutor(
					ddlRecordSetLocalService);
		}

		return _deDataRecordCollectionDeleteRequestExecutor;
	}

	protected DEDataRecordCollectionGetRecordRequestExecutor
		getDEDataRecordCollectionGetRecordRequestExecutor() {

		if (_deDataRecordCollectionGetRecordRequestExecutor == null) {
			_deDataRecordCollectionGetRecordRequestExecutor =
				new DEDataRecordCollectionGetRecordRequestExecutor(
					getDEDataEngineRequestExecutor(), ddlRecordLocalService);
		}

		return _deDataRecordCollectionGetRecordRequestExecutor;
	}

	protected DEDataRecordCollectionGetRequestExecutor
		getDEDataRecordCollectionGetRequestExecutor() {

		if (_deDataRecordCollectionGetRequestExecutor == null) {
			_deDataRecordCollectionGetRequestExecutor =
				new DEDataRecordCollectionGetRequestExecutor(
					ddlRecordSetLocalService, getDEDataEngineRequestExecutor());
		}

		return _deDataRecordCollectionGetRequestExecutor;
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

	protected DEDataRecordCollectionSaveRecordRequestExecutor
		getDEDataRecordCollectionSaveRecordRequestExecutor() {

		if (_deDataRecordCollectionSaveRecordRequestExecutor == null) {
			_deDataRecordCollectionSaveRecordRequestExecutor =
				new DEDataRecordCollectionSaveRecordRequestExecutor(
					ddlRecordLocalService, deDataStorageTracker,
					ddmStorageLinkLocalService, portal);
		}

		return _deDataRecordCollectionSaveRecordRequestExecutor;
	}

	protected DEDataRecordCollectionSaveRequestExecutor
		getDEDataRecordCollectionSaveRequestExecutor() {

		if (_deDataRecordCollectionSaveRequestExecutor == null) {
			_deDataRecordCollectionSaveRequestExecutor =
				new DEDataRecordCollectionSaveRequestExecutor(
					getDEDataEngineRequestExecutor(), ddlRecordSetLocalService,
					resourceLocalService);
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

	protected void verifyDataDefinitionFields(
			DEDataRecordCollectionSaveRecordRequest
				deDataRecordCollectionSaveRecordRequest)
		throws DEDataRecordCollectionException {

		DEDataRecord dataRecord =
			deDataRecordCollectionSaveRecordRequest.getDEDataRecord();

		DEDataDefinition deDataDefinition = dataRecord.getDEDataDefinition();

		Map<String, Object> values = dataRecord.getValues();

		Map<String, DEDataDefinitionField> deDataDefinitionFields =
			getDEDataDefinitionFieldsMap(deDataDefinition);

		List<String> fieldNames = new ArrayList<>();

		for (Map.Entry<String, Object> entry : values.entrySet()) {
			if (!deDataDefinitionFields.containsKey(entry.getKey())) {
				fieldNames.add(entry.getKey());
			}
		}

		if (!fieldNames.isEmpty()) {
			throw new DEDataRecordCollectionException.NoSuchFields(
				ArrayUtil.toStringArray(fieldNames));
		}
	}

	@Reference
	protected DDLRecordLocalService ddlRecordLocalService;

	@Reference
	protected DDLRecordSetLocalService ddlRecordSetLocalService;

	@Reference
	protected DDMStorageLinkLocalService ddmStorageLinkLocalService;

	@Reference
	protected DEDataDefinitionFieldsDeserializerTracker
		deDataDefinitionFieldsDeserializerTracker;

	@Reference
	protected DEDataStorageTracker deDataStorageTracker;

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

	private DEDataEngineRequestExecutor _deDataEngineRequestExecutor;
	private DEDataRecordCollectionDeleteModelPermissionsRequestExecutor
		_deDataRecordCollectionDeleteModelPermissionsRequestExecutor;
	private DEDataRecordCollectionDeletePermissionsRequestExecutor
		_deDataRecordCollectionDeletePermissionsRequestExecutor;
	private DEDataRecordCollectionDeleteRecordRequestExecutor
		_deDataRecordCollectionDeleteRecordRequestExecutor;
	private DEDataRecordCollectionDeleteRequestExecutor
		_deDataRecordCollectionDeleteRequestExecutor;
	private DEDataRecordCollectionGetRecordRequestExecutor
		_deDataRecordCollectionGetRecordRequestExecutor;
	private DEDataRecordCollectionGetRequestExecutor
		_deDataRecordCollectionGetRequestExecutor;
	private DEDataRecordCollectionSaveModelPermissionsRequestExecutor
		_deDataRecordCollectionSaveModelPermissionsRequestExecutor;
	private DEDataRecordCollectionSavePermissionsRequestExecutor
		_deDataRecordCollectionSavePermissionsRequestExecutor;
	private DEDataRecordCollectionSaveRecordRequestExecutor
		_deDataRecordCollectionSaveRecordRequestExecutor;
	private DEDataRecordCollectionSaveRequestExecutor
		_deDataRecordCollectionSaveRequestExecutor;
	private ModelResourcePermission<DEDataRecordCollection>
		_modelResourcePermission;

}