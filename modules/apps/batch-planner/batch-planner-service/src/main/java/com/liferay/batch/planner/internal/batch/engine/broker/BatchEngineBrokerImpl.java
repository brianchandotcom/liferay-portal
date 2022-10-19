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

package com.liferay.batch.planner.internal.batch.engine.broker;

import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.planner.batch.engine.broker.BatchEngineBroker;
import com.liferay.batch.planner.constants.BatchPlannerPlanConstants;
import com.liferay.batch.planner.constants.BatchPlannerPolicyConstants;
import com.liferay.batch.planner.internal.jaxrs.uri.BatchPlannerUriInfo;
import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.batch.planner.model.BatchPlannerMappingModel;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.model.BatchPlannerPolicy;
import com.liferay.batch.planner.service.BatchPlannerMappingLocalService;
import com.liferay.batch.planner.service.BatchPlannerPlanLocalService;
import com.liferay.headless.batch.engine.resource.v1_0.ExportTaskResource;
import com.liferay.headless.batch.engine.resource.v1_0.ImportTaskResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import java.io.File;
import java.io.FileInputStream;

import java.net.URI;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(service = BatchEngineBroker.class)
public class BatchEngineBrokerImpl implements BatchEngineBroker {

	@Override
	public void submit(long batchPlannerPlanId) throws Exception {
		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanLocalService.getBatchPlannerPlan(
				batchPlannerPlanId);

		_batchPlannerPlanLocalService.updateStatus(
			batchPlannerPlan.getBatchPlannerPlanId(),
			BatchPlannerPlanConstants.STATUS_QUEUED);

		try {
			if (batchPlannerPlan.isExport()) {
				_submitExportTask(batchPlannerPlan);
			}
			else {
				_submitImportTask(batchPlannerPlan);
			}
		}
		catch (Exception exception) {
			_batchPlannerPlanLocalService.updateStatus(
				batchPlannerPlan.getBatchPlannerPlanId(),
				BatchPlannerPlanConstants.STATUS_FAILED);

			throw exception;
		}
	}

	private String _getFieldNameMapping(
		List<BatchPlannerMapping> batchPlannerMappings) {

		if (batchPlannerMappings.isEmpty()) {
			throw new IllegalArgumentException(
				"There are no batch planner mappings");
		}

		StringBundler sb = new StringBundler(
			(batchPlannerMappings.size() * 3) - 1);

		Iterator<BatchPlannerMapping> iterator =
			batchPlannerMappings.iterator();

		while (iterator.hasNext()) {
			BatchPlannerMapping batchPlannerMapping = iterator.next();

			sb.append(batchPlannerMapping.getExternalFieldName());

			sb.append(StringPool.EQUAL);
			sb.append(batchPlannerMapping.getInternalFieldName());

			if (iterator.hasNext()) {
				sb.append(StringPool.COMMA);
			}
		}

		return sb.toString();
	}

	private File _getFile(long batchPlannerPlanId) throws Exception {
		BatchPlannerPlan batchPlannerPlan =
			_batchPlannerPlanLocalService.getBatchPlannerPlan(
				batchPlannerPlanId);

		return new File(new URI(batchPlannerPlan.getExternalURL()));
	}

	private String[] _getHeaderNames(
		List<BatchPlannerMapping> batchPlannerMappings,
		UnsafeFunction<BatchPlannerMapping, String, Exception> unsafeFunction) {

		return TransformUtil.transformToArray(
			batchPlannerMappings, unsafeFunction, String.class);
	}

	private String _getImportStrategy(BatchPlannerPlan batchPlannerPlan)
		throws Exception {

		BatchPlannerPolicy batchPlannerPolicy =
			batchPlannerPlan.getBatchPlannerPolicy("onErrorFail");

		boolean onErrorFail = Boolean.valueOf(batchPlannerPolicy.getValue());

		if (onErrorFail) {
			return BatchEngineImportTaskConstants.
				IMPORT_STRATEGY_STRING_ON_ERROR_FAIL;
		}

		return BatchEngineImportTaskConstants.
			IMPORT_STRATEGY_STRING_ON_ERROR_CONTINUE;
	}

	private UriInfo _getImportTaskUriInfo(BatchPlannerPlan batchPlannerPlan) {
		BatchPlannerUriInfo.Builder builder = new BatchPlannerUriInfo.Builder();

		for (String name : BatchPlannerPolicyConstants.nameTypes.keySet()) {
			builder.queryParameter(
				name,
				_getValue(batchPlannerPlan.fetchBatchPlannerPolicy(name)));
		}

		return builder.taskItemDelegateName(
			batchPlannerPlan.getTaskItemDelegateName()
		).build();
	}

	private String _getValue(BatchPlannerPolicy batchPlannerPolicy) {
		if (batchPlannerPolicy != null) {
			return batchPlannerPolicy.getValue();
		}

		return null;
	}

	private void _submitExportTask(BatchPlannerPlan batchPlannerPlan)
		throws Exception {

		_exportTaskResource.setContextCompany(
			_companyLocalService.getCompany(batchPlannerPlan.getCompanyId()));
		_exportTaskResource.setContextUriInfo(
			_getImportTaskUriInfo(batchPlannerPlan));
		_exportTaskResource.setContextUser(
			_userLocalService.getUser(batchPlannerPlan.getUserId()));

		_exportTaskResource.postExportTask(
			batchPlannerPlan.getInternalClassName(),
			batchPlannerPlan.getExternalType(), null,
			String.valueOf(batchPlannerPlan.getBatchPlannerPlanId()),
			StringUtil.merge(
				_getHeaderNames(
					_batchPlannerMappingLocalService.getBatchPlannerMappings(
						batchPlannerPlan.getBatchPlannerPlanId()),
					BatchPlannerMappingModel::getInternalFieldName),
				StringPool.COMMA),
			batchPlannerPlan.getTaskItemDelegateName());
	}

	private void _submitImportTask(BatchPlannerPlan batchPlannerPlan)
		throws Exception {

		_importTaskResource.setContextCompany(
			_companyLocalService.getCompany(batchPlannerPlan.getCompanyId()));
		_importTaskResource.setContextUriInfo(
			_getImportTaskUriInfo(batchPlannerPlan));
		_importTaskResource.setContextUser(
			_userLocalService.getUser(batchPlannerPlan.getUserId()));

		File file = _getFile(batchPlannerPlan.getBatchPlannerPlanId());

		try {
			if (!GetterUtil.getBoolean(
					_getValue(
						batchPlannerPlan.fetchBatchPlannerPolicy(
							"allowUpdate")))) {

				_importTaskResource.postImportTask(
					batchPlannerPlan.getInternalClassName(), null, null,
					String.valueOf(batchPlannerPlan.getBatchPlannerPlanId()),
					_getFieldNameMapping(
						_batchPlannerMappingLocalService.
							getBatchPlannerMappings(
								batchPlannerPlan.getBatchPlannerPlanId())),
					_getImportStrategy(batchPlannerPlan),
					batchPlannerPlan.getTaskItemDelegateName(),
					MultipartBody.of(
						Collections.singletonMap(
							"file",
							new BinaryFile(
								BatchPlannerPlanConstants.getContentType(
									batchPlannerPlan.getExternalType()),
								file.getName(), new FileInputStream(file),
								file.length())),
						null, Collections.emptyMap()));

				return;
			}

			_importTaskResource.putImportTask(
				batchPlannerPlan.getInternalClassName(), null,
				String.valueOf(batchPlannerPlan.getBatchPlannerPlanId()),
				_getImportStrategy(batchPlannerPlan),
				batchPlannerPlan.getTaskItemDelegateName(), null,
				MultipartBody.of(
					Collections.singletonMap(
						"file",
						new BinaryFile(
							BatchPlannerPlanConstants.getContentType(
								batchPlannerPlan.getExternalType()),
							file.getName(), new FileInputStream(file),
							file.length())),
					null, Collections.emptyMap()));
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Reference
	private BatchPlannerMappingLocalService _batchPlannerMappingLocalService;

	@Reference
	private BatchPlannerPlanLocalService _batchPlannerPlanLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ExportTaskResource _exportTaskResource;

	@Reference
	private ImportTaskResource _importTaskResource;

	@Reference
	private UserLocalService _userLocalService;

}