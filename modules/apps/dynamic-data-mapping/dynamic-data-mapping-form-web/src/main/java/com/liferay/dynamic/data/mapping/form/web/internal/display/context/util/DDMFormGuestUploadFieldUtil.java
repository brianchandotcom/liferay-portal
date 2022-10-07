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

package com.liferay.dynamic.data.mapping.form.web.internal.display.context.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(service = {})
public class DDMFormGuestUploadFieldUtil {

	public static boolean isMaximumSubmissionLimitReached(
			DDMFormInstance ddmFormInstance,
			HttpServletRequest httpServletRequest,
			int guestUploadMaximumSubmissions)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay.isSignedIn() ||
			!hasGuestUploadField(ddmFormInstance)) {

			return false;
		}

		List<DDMFormInstanceRecord> ddmFormInstanceRecords =
			_ddmFormInstanceRecordLocalService.getFormInstanceRecords(
				ddmFormInstance.getFormInstanceId(),
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (int i = 0; i < ddmFormInstanceRecords.size(); i++) {
			DDMFormInstanceRecord ddmFormInstanceRecord =
				ddmFormInstanceRecords.get(i);

			if (Objects.equals(
					ddmFormInstanceRecord.getIpAddress(),
					httpServletRequest.getRemoteAddr()) &&
				((i + 1) == guestUploadMaximumSubmissions)) {

				return true;
			}
		}

		return false;
	}

	protected static boolean hasGuestUploadField(
			DDMFormInstance ddmFormInstance)
		throws PortalException {

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		DDMForm ddmForm = ddmStructure.getDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		Collection<DDMFormField> ddmFormFields = ddmFormFieldsMap.values();

		Stream<DDMFormField> ddmFormFieldsStream = ddmFormFields.stream();

		Optional<DDMFormField> ddmFormFieldOptional =
			ddmFormFieldsStream.filter(
				ddmFormField ->
					Objects.equals(
						ddmFormField.getType(), "document_library") &&
					GetterUtil.getBoolean(
						ddmFormField.getProperty("allowGuestUsers"))
			).findFirst();

		return ddmFormFieldOptional.isPresent();
	}

	@Reference(unbind = "-")
	private void _setDDMFormInstanceRecordLocalService(
		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService) {

		_ddmFormInstanceRecordLocalService = ddmFormInstanceRecordLocalService;
	}

	private static DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

}