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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.constants.DDMFormInstanceReportConstants;
import com.liferay.dynamic.data.mapping.internal.petra.executor.DDMFormInstanceReportPortalExecutor;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport;
import com.liferay.dynamic.data.mapping.report.DDMFormFieldTypeReportProcessor;
import com.liferay.dynamic.data.mapping.report.DDMFormFieldTypeReportProcessorTracker;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.service.base.DDMFormInstanceReportLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstancePersistence;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport",
	service = AopService.class
)
public class DDMFormInstanceReportLocalServiceImpl
	extends DDMFormInstanceReportLocalServiceBaseImpl {

	@Override
	public DDMFormInstanceReport addFormInstanceReport(long formInstanceId)
		throws PortalException {

		DDMFormInstanceReport formInstanceReport =
			ddmFormInstanceReportPersistence.create(
				counterLocalService.increment());

		DDMFormInstance formInstance =
			_ddmFormInstancePersistence.findByPrimaryKey(formInstanceId);

		formInstanceReport.setGroupId(formInstance.getGroupId());
		formInstanceReport.setCompanyId(formInstance.getCompanyId());

		formInstanceReport.setCreateDate(new Date());
		formInstanceReport.setFormInstanceId(formInstance.getFormInstanceId());

		return ddmFormInstanceReportPersistence.update(formInstanceReport);
	}

	@Override
	public DDMFormInstanceReport getFormInstanceReportByFormInstanceId(
			long formInstanceId)
		throws PortalException {

		return ddmFormInstanceReportPersistence.findByFormInstanceId(
			formInstanceId);
	}

	@Override
	public void processFormInstanceReportEvent(
		long formInstanceReportId, long formInstanceRecordVersionId,
		String formInstanceReportEvent) {

		try {
			_ddmFormInstanceReportPortalExecutor.execute(
				() ->
					ddmFormInstanceReportLocalService.updateFormInstanceReport(
						formInstanceReportId, formInstanceRecordVersionId,
						formInstanceReportEvent));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(3);

				sb.append("Unable to update dynamic data mapping form ");
				sb.append("instance report ");
				sb.append(formInstanceReportId);

				_log.warn(sb.toString(), exception);
			}
		}
	}

	@Override
	public DDMFormInstanceReport updateFormInstanceReport(
			long formInstanceReportId, long formInstanceRecordVersionId,
			String formInstanceReportEvent)
		throws PortalException {

		DDMFormInstanceReport formInstanceReport =
			ddmFormInstanceReportPersistence.findByPrimaryKey(
				formInstanceReportId);

		formInstanceReport.setModifiedDate(new Date());
		formInstanceReport.setData(
			_getData(formInstanceRecordVersionId, formInstanceReportEvent));

		return ddmFormInstanceReportPersistence.update(formInstanceReport);
	}

	private String _getData(
			long formInstanceRecordVersionId, String formInstanceReportEvent)
		throws PortalException {

		try {
			DDMFormInstanceRecordVersion formInstanceRecordVersion =
				_ddmFormInstanceRecordVersionLocalService.
					getDDMFormInstanceRecordVersion(
						formInstanceRecordVersionId);

			DDMFormInstanceReport formInstanceReport =
				ddmFormInstanceReportPersistence.findByFormInstanceId(
					formInstanceRecordVersion.getFormInstanceId());

			JSONObject formInstanceReportDataJSONObject =
				JSONFactoryUtil.createJSONObject(formInstanceReport.getData());

			DDMFormValues ddmFormValues =
				formInstanceRecordVersion.getDDMFormValues();

			for (DDMFormFieldValue ddmFormFieldValue :
					ddmFormValues.getDDMFormFieldValues()) {

				DDMFormFieldTypeReportProcessor
					ddmFormFieldTypeReportProcessor =
						_ddmFormFieldTypeReportProcessorTracker.
							getDDMFormFieldTypeReportProcessor(
								ddmFormFieldValue.getType());

				if (ddmFormFieldTypeReportProcessor != null) {
					String fieldName = ddmFormFieldValue.getName();

					JSONObject fieldJSONObject =
						formInstanceReportDataJSONObject.getJSONObject(
							fieldName);

					if (fieldJSONObject == null) {
						fieldJSONObject = JSONUtil.put(
							"type", ddmFormFieldValue.getType()
						).put(
							"values", JSONFactoryUtil.createJSONObject()
						);
					}

					JSONObject processedFieldJSONObject =
						ddmFormFieldTypeReportProcessor.process(
							ddmFormFieldValue,
							JSONFactoryUtil.createJSONObject(
								fieldJSONObject.toJSONString()),
							formInstanceRecordVersion.getFormInstanceRecordId(),
							formInstanceReportEvent);

					formInstanceReportDataJSONObject.put(
						fieldName, processedFieldJSONObject);
				}
			}

			int totalItems = formInstanceReportDataJSONObject.getInt(
				"totalItems");

			if (formInstanceReportEvent.equals(
					DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION)) {

				totalItems++;
			}
			else if (formInstanceReportEvent.equals(
						DDMFormInstanceReportConstants.
							EVENT_DELETE_RECORD_VERSION)) {

				totalItems--;
			}

			formInstanceReportDataJSONObject.put("totalItems", totalItems);

			return formInstanceReportDataJSONObject.toString();
		}
		catch (Exception exception) {
			throw new PortalException(
				"Unable to process data for form instance record version " +
					formInstanceRecordVersionId,
				exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceReportLocalServiceImpl.class);

	@Reference
	private DDMFormFieldTypeReportProcessorTracker
		_ddmFormFieldTypeReportProcessorTracker;

	@Reference
	private DDMFormInstancePersistence _ddmFormInstancePersistence;

	@Reference
	private DDMFormInstanceRecordVersionLocalService
		_ddmFormInstanceRecordVersionLocalService;

	@Reference
	private DDMFormInstanceReportPortalExecutor
		_ddmFormInstanceReportPortalExecutor;

}