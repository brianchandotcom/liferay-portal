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

package com.liferay.batch.planner.service.impl;

import com.liferay.batch.planner.exception.BatchPlannerMappingExternalFieldNameException;
import com.liferay.batch.planner.exception.BatchPlannerMappingExternalFieldTypeException;
import com.liferay.batch.planner.exception.BatchPlannerMappingInternalFieldNameException;
import com.liferay.batch.planner.exception.BatchPlannerMappingInternalFieldTypeException;
import com.liferay.batch.planner.exception.DuplicateBatchPlannerPlanException;
import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.batch.planner.service.base.BatchPlannerMappingLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(
	property = "model.class.name=com.liferay.batch.planner.model.BatchPlannerMapping",
	service = AopService.class
)
public class BatchPlannerMappingLocalServiceImpl
	extends BatchPlannerMappingLocalServiceBaseImpl {

	@Override
	public BatchPlannerMapping addBatchPlannerMapping(
			long userId, long batchPlannerPlanId, String externalFieldName,
			String externalFieldType, String internalFieldName,
			String internalFieldType, String script)
		throws PortalException {

		BatchPlannerMapping batchPlannerMapping =
			batchPlannerMappingPersistence.fetchByBPPI_EFN_IFN(
				batchPlannerPlanId, externalFieldName, internalFieldName);

		if (batchPlannerMapping != null) {
			throw new DuplicateBatchPlannerPlanException(
				StringBundler.concat(
					"Batch planner mapping with external field name \"",
					externalFieldName, "\" and internal field name \"",
					internalFieldName, "\" already exists"));
		}

		_validateExternalFieldName(externalFieldName);
		_validateExternalFieldType(externalFieldType);
		_validateInternalFieldName(internalFieldName);
		_validateInternalFieldType(internalFieldType);

		batchPlannerMapping = batchPlannerMappingPersistence.create(
			counterLocalService.increment(BatchPlannerMapping.class.getName()));

		User user = userLocalService.getUser(userId);

		batchPlannerMapping.setCompanyId(user.getCompanyId());

		batchPlannerMapping.setUserId(userId);
		batchPlannerMapping.setBatchPlannerPlanId(batchPlannerPlanId);
		batchPlannerMapping.setExternalFieldName(externalFieldName);
		batchPlannerMapping.setExternalFieldType(externalFieldType);
		batchPlannerMapping.setInternalFieldName(internalFieldName);
		batchPlannerMapping.setInternalFieldType(internalFieldType);
		batchPlannerMapping.setScript(script);

		return batchPlannerMappingPersistence.update(batchPlannerMapping);
	}

	@Override
	public BatchPlannerMapping deleteBatchPlannerMapping(
			long batchPlannerPlanId, String externalFieldName,
			String internalFieldName)
		throws PortalException {

		BatchPlannerMapping batchPlannerMapping =
			batchPlannerMappingPersistence.findByBPPI_EFN_IFN(
				batchPlannerPlanId, externalFieldName, internalFieldName);

		return batchPlannerMappingPersistence.remove(batchPlannerMapping);
	}

	@Override
	public List<BatchPlannerMapping> getBatchPlannerMappings(
		long batchPlannerPlanId) {

		return batchPlannerMappingPersistence.findByBatchPlannerPlanId(
			batchPlannerPlanId);
	}

	@Override
	public BatchPlannerMapping updateBatchPlannerMapping(
			long batchPlannerMappingId, String externalFieldName,
			String externalFieldType, String script)
		throws PortalException {

		BatchPlannerMapping batchPlannerMapping =
			batchPlannerMappingPersistence.findByPrimaryKey(
				batchPlannerMappingId);

		batchPlannerMapping.setExternalFieldName(externalFieldName);
		batchPlannerMapping.setExternalFieldType(externalFieldType);
		batchPlannerMapping.setScript(script);

		return batchPlannerMappingPersistence.update(batchPlannerMapping);
	}

	private void _validateExternalFieldName(String externalFieldName)
		throws PortalException {

		if (Validator.isNull(externalFieldName)) {
			throw new BatchPlannerMappingExternalFieldNameException(
				"External field name is null");
		}

		int maxLength = ModelHintsUtil.getMaxLength(
			BatchPlannerMapping.class.getName(), "externalFieldName");

		if (externalFieldName.length() > maxLength) {
			throw new BatchPlannerMappingExternalFieldNameException(
				"External field name is too long");
		}
	}

	private void _validateExternalFieldType(String externalFieldType)
		throws PortalException {

		if (Validator.isNull(externalFieldType)) {
			throw new BatchPlannerMappingExternalFieldTypeException(
				"External field type is null");
		}

		int maxLength = ModelHintsUtil.getMaxLength(
			BatchPlannerMapping.class.getName(), "externalFieldType");

		if (externalFieldType.length() > maxLength) {
			throw new BatchPlannerMappingExternalFieldTypeException(
				"External field type is too long");
		}
	}

	private void _validateInternalFieldName(String internalFieldName)
		throws PortalException {

		if (Validator.isNull(internalFieldName)) {
			throw new BatchPlannerMappingInternalFieldNameException(
				"Internal field name is null");
		}

		int maxLength = ModelHintsUtil.getMaxLength(
			BatchPlannerMapping.class.getName(), "internalFieldName");

		if (internalFieldName.length() > maxLength) {
			throw new BatchPlannerMappingInternalFieldNameException(
				"Internal field name is too long");
		}
	}

	private void _validateInternalFieldType(String internalFieldType)
		throws PortalException {

		if (Validator.isNull(internalFieldType)) {
			throw new BatchPlannerMappingInternalFieldTypeException(
				"Internal field type is null");
		}

		int maxLength = ModelHintsUtil.getMaxLength(
			BatchPlannerMapping.class.getName(), "internalFieldType");

		if (internalFieldType.length() > maxLength) {
			throw new BatchPlannerMappingInternalFieldTypeException(
				"Internal field type is too long");
		}
	}

}