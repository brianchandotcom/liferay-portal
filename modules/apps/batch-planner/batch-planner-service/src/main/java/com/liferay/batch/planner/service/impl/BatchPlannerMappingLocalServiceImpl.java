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

import com.liferay.batch.planner.exception.BatchPlannerPlanNameException;
import com.liferay.batch.planner.exception.DuplicateBatchPlannerPlanException;
import com.liferay.batch.planner.exception.RequiredBatchPlannerMappingException;
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

		_validateRequiredField("externalFieldName", externalFieldName);
		_validateRequiredField("externalFieldType", externalFieldType);
		_validateRequiredField("internalFieldName", internalFieldName);
		_validateRequiredField("internalFieldType", internalFieldType);

		_validateBatchPlannerMapping(
			batchPlannerPlanId, externalFieldName, internalFieldName);

		BatchPlannerMapping batchPlannerMapping =
			batchPlannerMappingPersistence.create(
				counterLocalService.increment(
					BatchPlannerMapping.class.getName()));

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
			String internalName)
		throws PortalException {

		BatchPlannerMapping batchPlannerMapping =
			batchPlannerMappingPersistence.findByBPPI_EFN_IFN(
				batchPlannerPlanId, externalFieldName, internalName);

		return batchPlannerMappingPersistence.remove(batchPlannerMapping);
	}

	@Override
	public List<BatchPlannerMapping> getBatchPlannerMappings(
		long batchPlannerPlanId) {

		return batchPlannerMappingPersistence.findByBatchPlannerPlanId(
			batchPlannerPlanId);
	}

	private void _validateBatchPlannerMapping(
			long batchPlannerPlanId, String externalName, String internalName)
		throws PortalException {

		BatchPlannerMapping batchPlannerMapping =
			batchPlannerMappingPersistence.fetchByBPPI_EFN_IFN(
				batchPlannerPlanId, externalName, internalName);

		if (batchPlannerMapping == null) {
			return;
		}

		throw new DuplicateBatchPlannerPlanException(
			StringBundler.concat(
				"Batch planner mapping with external field name \"",
				externalName, "\" and internal field name \"", externalName,
				"\" already exists for batch planner plan ",
				batchPlannerPlanId));
	}

	private void _validateRequiredField(String name, String value)
		throws PortalException {

		if (Validator.isNull(value)) {
			throw new RequiredBatchPlannerMappingException(
				StringBundler.concat(
					"Batch planner mapping field \"", name, "\" is null"));
		}

		int maxLength = ModelHintsUtil.getMaxLength(
			BatchPlannerMapping.class.getName(), name);

		if (value.length() > maxLength) {
			throw new BatchPlannerPlanNameException(
				StringBundler.concat(
					"Batch planner mapping field \"", name,
					"\" must not be longer than ", maxLength));
		}
	}

}