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
import com.liferay.data.engine.constants.DEDataDefinitionRuleConstants;
import com.liferay.data.engine.exception.DEDataRecordCollectionException;
import com.liferay.data.engine.internal.executor.DEDataEngineRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionDeleteModelPermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionDeletePermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionDeleteRecordRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionDeleteRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionGetRecordRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionGetRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionListRecordRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionListRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionSaveModelPermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionSavePermissionsRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionSaveRecordRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionSaveRequestExecutor;
import com.liferay.data.engine.internal.executor.DEDataRecordCollectionSearchExecutor;
import com.liferay.data.engine.internal.io.DEDataDefinitionDeserializerTracker;
import com.liferay.data.engine.internal.rule.DEDataDefinitionRuleFunctionTracker;
import com.liferay.data.engine.internal.security.permission.DEDataEnginePermissionSupport;
import com.liferay.data.engine.internal.storage.DEDataStorageTracker;
import com.liferay.data.engine.internal.util.DEDataEngineUtil;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.model.DEDataDefinitionRule;
import com.liferay.data.engine.model.DEDataRecord;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.data.engine.rule.DEDataDefinitionRuleFunction;
import com.liferay.data.engine.rule.DEDataDefinitionRuleFunctionApplyRequest;
import com.liferay.data.engine.rule.DEDataDefinitionRuleFunctionApplyResponse;
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
import com.liferay.data.engine.service.DEDataRecordCollectionListRecordRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionListRecordResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionListRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionListResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionRequestBuilder;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveModelPermissionsResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionSavePermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSavePermissionsResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveRecordRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveRecordResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionSearchRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSearchResponse;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
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

		try {
			checkPermission(
				deDataRecordCollectionDeleteModelPermissionsRequest.
					getScopedGroupId(),
				ActionKeys.DEFINE_PERMISSIONS, getPermissionChecker());

			return _deDataRecordCollectionDeleteModelPermissionsRequestExecutor.
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

			return _deDataRecordCollectionDeletePermissionsRequestExecutor.
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
			DEDataRecordCollectionGetRecordRequest
				deDataRecordCollectionGetRecordRequest =
					DEDataRecordCollectionRequestBuilder.getRecordBuilder(
						deDataRecordId
					).build();

			DEDataRecordCollectionGetRecordResponse
				deDataRecordCollectionGetRecordResponse =
					_deDataRecordCollectionGetRecordRequestExecutor.execute(
						deDataRecordCollectionGetRecordRequest);

			DEDataRecord deDataRecord =
				deDataRecordCollectionGetRecordResponse.getDEDataRecord();

			_modelResourcePermission.check(
				getPermissionChecker(),
				deDataRecord.getDEDataRecordCollectionId(),
				DEActionKeys.DELETE_DATA_RECORD);

			_deDataRecordCollectionDeleteRecordRequestExecutor.execute(
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

			return _deDataRecordCollectionDeleteRequestExecutor.execute(
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
			DEDataRecordCollectionGetRecordResponse
				deDataRecordCollectionGetRecordResponse =
					_deDataRecordCollectionGetRecordRequestExecutor.execute(
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

			return _deDataRecordCollectionGetRequestExecutor.execute(
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
	public DEDataRecordCollectionListRecordResponse execute(
			DEDataRecordCollectionListRecordRequest
				deDataRecordCollectionListRecordRequest)
		throws DEDataRecordCollectionException {

		try {
			return _deDataRecordCollectionListRecordRequestExecutor.execute(
				deDataRecordCollectionListRecordRequest);
		}
		catch (DEDataRecordCollectionException dedrce) {
			throw dedrce;
		}
		catch (Exception e) {
			throw new DEDataRecordCollectionException(e);
		}
	}

	@Override
	public DEDataRecordCollectionListResponse execute(
			DEDataRecordCollectionListRequest deDataRecordCollectionListRequest)
		throws DEDataRecordCollectionException {

		try {
			return _deDataRecordCollectionListRequestExecutor.execute(
				deDataRecordCollectionListRequest);
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

		try {
			checkPermission(
				deDataRecordCollectionSaveModelPermissionsRequest.
					getScopedGroupId(),
				ActionKeys.DEFINE_PERMISSIONS, getPermissionChecker());

			return _deDataRecordCollectionSaveModelPermissionsRequestExecutor.
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

			return _deDataRecordCollectionSavePermissionsRequestExecutor.
				execute(deDataRecordCollectionSavePermissionsRequest);
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

			validate(deDataRecordCollectionSaveRecordRequest);

			DEDataRecordCollectionSaveRecordResponse
				deDataRecordCollectionSaveRecordResponse =
					_deDataRecordCollectionSaveRecordRequestExecutor.execute(
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

			return _deDataRecordCollectionSaveRequestExecutor.execute(
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

	@Override
	public DEDataRecordCollectionSearchResponse execute(
			DEDataRecordCollectionSearchRequest
				deDataRecordCollectionSearchRequest)
		throws DEDataRecordCollectionException {

		try {
			return _deDataRecordCollectionSearchExecutor.execute(
				deDataRecordCollectionSearchRequest);
		}
		catch (Exception e) {
			throw new DEDataRecordCollectionException(e);
		}
	}

	protected DEDataDefinitionRuleFunctionApplyRequest
		createDEDataDefinitionRuleFunctionApplyRequest(
			DEDataDefinitionField deDataDefinitionField,
			DEDataDefinitionRule deDataDefinitionRule,
			DEDataRecord deDataRecord) {

		DEDataDefinitionRuleFunctionApplyRequest
			deDataDefinitionRuleFunctionApplyRequest =
				new DEDataDefinitionRuleFunctionApplyRequest();

		deDataDefinitionRuleFunctionApplyRequest.setDEDataDefinitionField(
			deDataDefinitionField);
		deDataDefinitionRuleFunctionApplyRequest.setParameters(
			deDataDefinitionRule.getParameters());

		Object value = DEDataEngineUtil.getDEDataDefinitionFieldValue(
			deDataDefinitionField, deDataRecord.getValues());

		deDataDefinitionRuleFunctionApplyRequest.setValue(value);

		return deDataDefinitionRuleFunctionApplyRequest;
	}

	protected void doValidation(
		Map<String, DEDataDefinitionField> deDataDefinitionFields,
		DEDataDefinitionRule deDataDefinitionRule, DEDataRecord deDataRecord,
		Map<String, Set<String>> validationErrors) {

		DEDataDefinitionRuleFunction deDataDefinitionRuleFunction =
			deDataDefinitionRuleFunctionTracker.getDEDataDefinitionRuleFunction(
				deDataDefinitionRule.getName());

		if (deDataDefinitionRuleFunction == null) {
			return;
		}

		for (String deDataDefinitionFieldName :
				deDataDefinitionRule.getDEDataDefinitionFieldNames()) {

			DEDataDefinitionField deDataDefinitionField =
				deDataDefinitionFields.get(deDataDefinitionFieldName);

			DEDataDefinitionRuleFunctionApplyRequest
				deDataDefinitionRuleFunctionApplyRequest =
					createDEDataDefinitionRuleFunctionApplyRequest(
						deDataDefinitionField, deDataDefinitionRule,
						deDataRecord);

			DEDataDefinitionRuleFunctionApplyResponse
				deDataDefinitionRuleFunctionApplyResponse =
					deDataDefinitionRuleFunction.apply(
						deDataDefinitionRuleFunctionApplyRequest);

			if (!deDataDefinitionRuleFunctionApplyResponse.isValid()) {
				Set<String> errorCodes = validationErrors.getOrDefault(
					deDataDefinitionFieldName, new HashSet<>());

				errorCodes.add(
					deDataDefinitionRuleFunctionApplyResponse.getErrorCode());

				validationErrors.put(deDataDefinitionFieldName, errorCodes);
			}
		}
	}

	protected Set<String> getDEDataDefinitionFields(
		DEDataDefinition deDataDefinition) {

		List<DEDataDefinitionField> deDataDefinitionFields =
			deDataDefinition.getDEDataDefinitionFields();

		Stream<DEDataDefinitionField> stream = deDataDefinitionFields.stream();

		return stream.map(
			field -> field.getName()
		).collect(
			Collectors.toSet()
		);
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

	protected boolean isValidationRule(
		DEDataDefinitionRule deDataDefinitionRule) {

		String ruleType = deDataDefinitionRule.getRuleType();

		return ruleType.equals(
			DEDataDefinitionRuleConstants.VALIDATION_RULE_TYPE);
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

	@Activate
	protected void setUpExecutors() {
		_deDataEngineRequestExecutor = new DEDataEngineRequestExecutor(
			deDataDefinitionDeserializerTracker, deDataStorageTracker);

		_deDataRecordCollectionDeleteModelPermissionsRequestExecutor =
			new DEDataRecordCollectionDeleteModelPermissionsRequestExecutor(
				resourcePermissionLocalService, roleLocalService);

		_deDataRecordCollectionDeletePermissionsRequestExecutor =
			new DEDataRecordCollectionDeletePermissionsRequestExecutor(
				resourcePermissionLocalService, roleLocalService);

		_deDataRecordCollectionDeleteRecordRequestExecutor =
			new DEDataRecordCollectionDeleteRecordRequestExecutor(
				deDataStorageTracker, ddlRecordLocalService);

		_deDataRecordCollectionDeleteRequestExecutor =
			new DEDataRecordCollectionDeleteRequestExecutor(
				ddlRecordSetLocalService);

		_deDataRecordCollectionGetRecordRequestExecutor =
			new DEDataRecordCollectionGetRecordRequestExecutor(
				_deDataEngineRequestExecutor, ddlRecordLocalService);

		_deDataRecordCollectionGetRequestExecutor =
			new DEDataRecordCollectionGetRequestExecutor(
				ddlRecordSetLocalService, _deDataEngineRequestExecutor);

		_deDataRecordCollectionListRequestExecutor =
			new DEDataRecordCollectionListRequestExecutor(
				ddlRecordSetLocalService, _deDataEngineRequestExecutor);

		_deDataRecordCollectionListRecordRequestExecutor =
			new DEDataRecordCollectionListRecordRequestExecutor(
				ddlRecordLocalService, _deDataEngineRequestExecutor);

		_deDataRecordCollectionSaveModelPermissionsRequestExecutor =
			new DEDataRecordCollectionSaveModelPermissionsRequestExecutor(
				resourcePermissionLocalService);

		_deDataRecordCollectionSavePermissionsRequestExecutor =
			new DEDataRecordCollectionSavePermissionsRequestExecutor(
				resourcePermissionLocalService, roleLocalService);

		_deDataRecordCollectionSaveRecordRequestExecutor =
			new DEDataRecordCollectionSaveRecordRequestExecutor(
				ddlRecordLocalService, deDataStorageTracker,
				ddmStorageLinkLocalService, portal);

		_deDataRecordCollectionSaveRequestExecutor =
			new DEDataRecordCollectionSaveRequestExecutor(
				_deDataEngineRequestExecutor, ddlRecordSetLocalService,
				resourceLocalService);

		_deDataRecordCollectionSearchExecutor =
			new DEDataRecordCollectionSearchExecutor(
				ddlRecordSetLocalService, _deDataEngineRequestExecutor);
	}

	protected void validate(
			DEDataRecordCollectionSaveRecordRequest
				deDataRecordCollectionSaveRecordRequest)
		throws DEDataRecordCollectionException {

		validateDEDataDefinitionFields(deDataRecordCollectionSaveRecordRequest);
		validateDEDataDefinitionFieldValues(
			deDataRecordCollectionSaveRecordRequest);
	}

	protected void validateDEDataDefinitionFields(
			DEDataRecordCollectionSaveRecordRequest
				deDataRecordCollectionSaveRecordRequest)
		throws DEDataRecordCollectionException {

		DEDataRecord deDataRecord =
			deDataRecordCollectionSaveRecordRequest.getDEDataRecord();

		DEDataDefinition deDataDefinition = deDataRecord.getDEDataDefinition();

		Set<String> deDataDefinitionFields = getDEDataDefinitionFields(
			deDataDefinition);

		Map<String, Object> values = deDataRecord.getValues();

		Set<String> keySet = values.keySet();

		Stream<String> stream = keySet.stream();

		List<String> orphanFieldNames = stream.filter(
			fieldName -> !deDataDefinitionFields.contains(fieldName)
		).collect(
			Collectors.toList()
		);

		if (!orphanFieldNames.isEmpty()) {
			throw new DEDataRecordCollectionException.NoSuchFields(
				ArrayUtil.toStringArray(orphanFieldNames));
		}
	}

	protected void validateDEDataDefinitionFieldValues(
			DEDataRecordCollectionSaveRecordRequest
				deDataRecordCollectionSaveRecordRequest)
		throws DEDataRecordCollectionException {

		DEDataRecord deDataRecord =
			deDataRecordCollectionSaveRecordRequest.getDEDataRecord();

		DEDataDefinition deDataDefinition = deDataRecord.getDEDataDefinition();

		List<DEDataDefinitionRule> deDataDefinitionRules =
			deDataDefinition.getDEDataDefinitionRules();

		Stream<DEDataDefinitionRule> stream = deDataDefinitionRules.stream();

		List<DEDataDefinitionRule> deDataDefinitionValidationRules =
			stream.filter(
				this::isValidationRule
			).collect(
				Collectors.toList()
			);

		if (deDataDefinitionValidationRules.isEmpty()) {
			return;
		}

		Map<String, DEDataDefinitionField> deDataDefinitionFields =
			getDEDataDefinitionFieldsMap(deDataDefinition);

		Map<String, Set<String>> validationErrors = new HashMap<>();

		for (DEDataDefinitionRule deDataDefinitionRule :
				deDataDefinitionValidationRules) {

			doValidation(
				deDataDefinitionFields, deDataDefinitionRule, deDataRecord,
				validationErrors);
		}

		if (!validationErrors.isEmpty()) {
			throw new DEDataRecordCollectionException.InvalidDataRecord(
				validationErrors);
		}
	}

	@Reference
	protected DDLRecordLocalService ddlRecordLocalService;

	@Reference
	protected DDLRecordSetLocalService ddlRecordSetLocalService;

	@Reference
	protected DDMStorageLinkLocalService ddmStorageLinkLocalService;

	@Reference
	protected DEDataDefinitionDeserializerTracker
		deDataDefinitionDeserializerTracker;

	@Reference
	protected DEDataDefinitionRuleFunctionTracker
		deDataDefinitionRuleFunctionTracker;

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
	private DEDataRecordCollectionListRecordRequestExecutor
		_deDataRecordCollectionListRecordRequestExecutor;
	private DEDataRecordCollectionListRequestExecutor
		_deDataRecordCollectionListRequestExecutor;
	private DEDataRecordCollectionSaveModelPermissionsRequestExecutor
		_deDataRecordCollectionSaveModelPermissionsRequestExecutor;
	private DEDataRecordCollectionSavePermissionsRequestExecutor
		_deDataRecordCollectionSavePermissionsRequestExecutor;
	private DEDataRecordCollectionSaveRecordRequestExecutor
		_deDataRecordCollectionSaveRecordRequestExecutor;
	private DEDataRecordCollectionSaveRequestExecutor
		_deDataRecordCollectionSaveRequestExecutor;
	private DEDataRecordCollectionSearchExecutor
		_deDataRecordCollectionSearchExecutor;
	private ModelResourcePermission<DEDataRecordCollection>
		_modelResourcePermission;

}