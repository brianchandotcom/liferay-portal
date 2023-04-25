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

package com.liferay.batch.engine.internal.unit;

import com.liferay.batch.engine.unit.BatchEngineUnit;
import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(service = BatchEngineUnitProcessor.class)
public class BatchEngineUnitProcessorImpl implements BatchEngineUnitProcessor {

	@Override
	public void processBatchEngineUnits(
		Iterable<BatchEngineUnit> batchEngineUnits) {

		for (BatchEngineUnit batchEngineUnit : batchEngineUnits) {
			try {
				_processBatchEngineUnit(batchEngineUnit);

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Successfully enqueued batch file ",
							batchEngineUnit.getFileName(), " ",
							batchEngineUnit.getDataFileName()));
				}
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void _processBatchEngineUnit(BatchEngineUnit batchEngineUnit)
		throws Exception {

		BatchEngineUnitConfiguration batchEngineUnitConfiguration = null;
		byte[] content = null;
		String contentType = null;

		if (batchEngineUnit.isValid()) {
			batchEngineUnitConfiguration = _updateBatchEngineUnitConfiguration(
				batchEngineUnit.getBatchEngineUnitConfiguration());

			UnsyncByteArrayOutputStream compressedUnsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			try (InputStream inputStream = batchEngineUnit.getDataInputStream();
				ZipOutputStream zipOutputStream = new ZipOutputStream(
					compressedUnsyncByteArrayOutputStream)) {

				zipOutputStream.putNextEntry(
					new ZipEntry(batchEngineUnit.getDataFileName()));

				StreamUtil.transfer(inputStream, zipOutputStream, false);
			}

			content = compressedUnsyncByteArrayOutputStream.toByteArray();

			contentType = _file.getExtension(batchEngineUnit.getDataFileName());
		}

		if ((batchEngineUnitConfiguration == null) || (content == null) ||
			Validator.isNull(contentType)) {

			throw new IllegalStateException(
				StringBundler.concat(
					"Invalid batch engine file ", batchEngineUnit.getFileName(),
					" ", batchEngineUnit.getDataFileName()));
		}

		Map<String, Serializable> parameters =
			batchEngineUnitConfiguration.getParameters();

		String featureFlag = (String)parameters.get("featureFlag");

		if (Validator.isNotNull(featureFlag) &&
			!FeatureFlagManagerUtil.isEnabled(featureFlag)) {

			return;
		}

		_componentFactory.newInstance(
			HashMapDictionaryBuilder.<String, Object>put(
				"_vulcanBatchEngineTaskItemDelegate.target",
				StringBundler.concat(
					"(batch.engine.entity.class.name=",
					batchEngineUnitConfiguration.getClassName(), ")")
			).put(
				"batchEngineUnit", batchEngineUnit
			).put(
				"batchEngineUnitConfiguration", batchEngineUnitConfiguration
			).put(
				"content", content
			).put(
				"contentType", contentType
			).build());
	}

	private BatchEngineUnitConfiguration _updateBatchEngineUnitConfiguration(
		BatchEngineUnitConfiguration batchEngineUnitConfiguration) {

		if (batchEngineUnitConfiguration.getCompanyId() == 0) {
			if (_log.isInfoEnabled()) {
				_log.info("Using default company ID for this batch process");
			}

			try {
				Company company = _companyLocalService.getCompanyByWebId(
					PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));

				batchEngineUnitConfiguration.setCompanyId(
					company.getCompanyId());
			}
			catch (PortalException portalException) {
				_log.error("Unable to get default company ID", portalException);
			}
		}

		if (batchEngineUnitConfiguration.getUserId() == 0) {
			if (_log.isInfoEnabled()) {
				_log.info("Using default user ID for this batch process");
			}

			try {
				batchEngineUnitConfiguration.setUserId(
					_userLocalService.getUserIdByScreenName(
						batchEngineUnitConfiguration.getCompanyId(),
						PropsUtil.get(PropsKeys.DEFAULT_ADMIN_SCREEN_NAME)));
			}
			catch (PortalException portalException) {
				_log.error("Unable to get default user ID", portalException);
			}
		}

		return batchEngineUnitConfiguration;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineUnitProcessorImpl.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(
		target = "(component.factory=batch.engine.import.task.component)"
	)
	@SuppressWarnings("rawtypes")
	private ComponentFactory _componentFactory;

	@Reference
	private File _file;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

	@Reference
	private UserLocalService _userLocalService;

}