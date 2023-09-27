/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.util;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToMapConverter;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

/**
 * @author Alejandro Tardín
 */
public class DataRecordValuesUtil {

	public static Map<String, Object> getDataRecordValues(
			DDMFormValues ddmFormValues, DDMStructure ddmStructure)
		throws PortalException {

		DDMFormValuesToMapConverter ddmFormValuesToMapConverter =
			_ddmFormValuesToMapConverterSnapshot.get();
		DDMStructureLocalService ddmStructureLocalService =
			_ddmStructureLocalServiceSnapshot.get();

		return ddmFormValuesToMapConverter.convert(
			ddmFormValues,
			ddmStructureLocalService.getStructure(
				ddmStructure.getStructureId()));
	}

	private static final Snapshot<DDMFormValuesToMapConverter>
		_ddmFormValuesToMapConverterSnapshot = new Snapshot<>(
			DataRecordValuesUtil.class, DDMFormValuesToMapConverter.class);
	private static final Snapshot<DDMStructureLocalService>
		_ddmStructureLocalServiceSnapshot = new Snapshot<>(
			DataRecordValuesUtil.class, DDMStructureLocalService.class);

}