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

package com.liferay.headless.delivery.dynamic.data.mapping;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;

/**
 * @author Luis Miguel Barcos
 */
public class DDMFormFieldUtil {

	public static DDMFormField getDDMFormField(
		DDMStructureService ddmStructureService, DDMStructure ddmStructure,
		String name) {

		DDMFormField ddmFormField = _getDDMFormField(
			ddmStructure.getDDMFormFields(true), name);

		if (ddmFormField != null) {
			return ddmFormField;
		}

		if (ddmStructure.getParentStructureId() != -1) {
			try {
				DDMStructure parentDDMStructure =
					ddmStructureService.getStructure(
						ddmStructure.getParentStructureId());

				ddmFormField = _getDDMFormField(
					parentDDMStructure.getDDMFormFields(true), name);

				if (ddmFormField != null) {
					return ddmFormField;
				}
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(portalException, portalException);
				}
			}
		}

		return null;
	}

	private static DDMFormField _getDDMFormField(
		List<DDMFormField> ddmFormFields, String name) {

		for (DDMFormField ddmFormField : ddmFormFields) {
			if (name.equals(ddmFormField.getName())) {
				return ddmFormField;
			}
			else if (name.equals(ddmFormField.getFieldReference())) {
				return ddmFormField;
			}
			else {
				DDMFormField nestedDDMFormField = _getDDMFormField(
					ddmFormField.getNestedDDMFormFields(), name);

				if (nestedDDMFormField != null) {
					return nestedDDMFormField;
				}
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormFieldUtil.class);

}