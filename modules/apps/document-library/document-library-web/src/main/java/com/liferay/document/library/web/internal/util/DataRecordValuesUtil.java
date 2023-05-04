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