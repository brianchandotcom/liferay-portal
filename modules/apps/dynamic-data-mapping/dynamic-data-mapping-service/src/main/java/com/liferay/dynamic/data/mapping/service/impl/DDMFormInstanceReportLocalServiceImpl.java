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
import com.liferay.portal.kernel.json.JSONException;
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

		DDMFormInstanceReport ddmFormInstanceReport =
			ddmFormInstanceReportPersistence.create(
				counterLocalService.increment());

		DDMFormInstance ddmFormInstance =
			_ddmFormInstancePersistence.findByPrimaryKey(formInstanceId);

		ddmFormInstanceReport.setGroupId(ddmFormInstance.getGroupId());
		ddmFormInstanceReport.setCompanyId(ddmFormInstance.getCompanyId());

		ddmFormInstanceReport.setCreateDate(new Date());
		ddmFormInstanceReport.setFormInstanceId(
			ddmFormInstance.getFormInstanceId());

		return ddmFormInstanceReportPersistence.update(ddmFormInstanceReport);
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
		String ddmFormInstanceReportEvent) {

		try {
			ddmFormInstanceReportLocalService.updateFormInstanceReport(
				formInstanceReportId, formInstanceRecordVersionId,
				ddmFormInstanceReportEvent);
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
			String ddmFormInstanceReportEvent)
		throws PortalException {

		DDMFormInstanceReport ddmFormInstanceReport =
			ddmFormInstanceReportPersistence.findByPrimaryKey(
				formInstanceReportId);

		ddmFormInstanceReport.setModifiedDate(new Date());
		ddmFormInstanceReport.setData(
			_getData(formInstanceRecordVersionId, ddmFormInstanceReportEvent));

		return ddmFormInstanceReportPersistence.update(ddmFormInstanceReport);
	}

	private String _getData(
			long formInstanceRecordVersionId, String ddmFormInstanceReportEvent)
		throws PortalException {

		try {
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
				_ddmFormInstanceRecordVersionLocalService.
					getDDMFormInstanceRecordVersion(
						formInstanceRecordVersionId);

			DDMFormInstanceReport ddmFormInstanceReport =
				ddmFormInstanceReportPersistence.findByFormInstanceId(
					ddmFormInstanceRecordVersion.getFormInstanceId());

			JSONObject ddmFormInstanceReportDataJSONObject =
				JSONFactoryUtil.createJSONObject(
					ddmFormInstanceReport.getData());

			DDMFormValues ddmFormValues =
				ddmFormInstanceRecordVersion.getDDMFormValues();

			for (DDMFormFieldValue ddmFormFieldValue :
					ddmFormValues.getDDMFormFieldValues()) {

				_processDDMFormFieldValue(
					ddmFormFieldValue, ddmFormInstanceRecordVersion,
					ddmFormInstanceReportDataJSONObject,
					ddmFormInstanceReportEvent);

				for (DDMFormFieldValue nestedDDMFormFieldValue :
						ddmFormFieldValue.getNestedDDMFormFieldValues()) {

					_processDDMFormFieldValue(
						nestedDDMFormFieldValue, ddmFormInstanceRecordVersion,
						ddmFormInstanceReportDataJSONObject,
						ddmFormInstanceReportEvent);
				}
			}

			int totalItems = ddmFormInstanceReportDataJSONObject.getInt(
				"totalItems");

			if (ddmFormInstanceReportEvent.equals(
					DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION)) {

				totalItems++;
			}
			else if (ddmFormInstanceReportEvent.equals(
						DDMFormInstanceReportConstants.
							EVENT_DELETE_RECORD_VERSION)) {

				totalItems--;
			}

			ddmFormInstanceReportDataJSONObject.put("totalItems", totalItems);

			return ddmFormInstanceReportDataJSONObject.toString();
		}
		catch (Exception exception) {
			throw new PortalException(
				"Unable to process data for form instance record version " +
					formInstanceRecordVersionId,
				exception);
		}
	}

	private void _processDDMFormFieldValue(
			DDMFormFieldValue ddmFormFieldValue,
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion,
			JSONObject ddmFormInstanceReportDataJSONObject,
			String ddmFormInstanceReportEvent)
		throws Exception, JSONException {

		DDMFormFieldTypeReportProcessor ddmFormFieldTypeReportProcessor =
			_ddmFormFieldTypeReportProcessorTracker.
				getDDMFormFieldTypeReportProcessor(ddmFormFieldValue.getType());

		if (ddmFormFieldTypeReportProcessor != null) {
			String fieldName = ddmFormFieldValue.getName();

			JSONObject fieldJSONObject =
				ddmFormInstanceReportDataJSONObject.getJSONObject(fieldName);

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
					ddmFormInstanceRecordVersion.getFormInstanceRecordId(),
					ddmFormInstanceReportEvent);

			ddmFormInstanceReportDataJSONObject.put(
				fieldName, processedFieldJSONObject);
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

}