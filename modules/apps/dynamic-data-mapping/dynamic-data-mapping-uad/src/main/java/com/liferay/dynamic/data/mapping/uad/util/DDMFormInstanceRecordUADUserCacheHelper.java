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

package com.liferay.dynamic.data.mapping.uad.util;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	immediate = true, service = DDMFormInstanceRecordUADUserCacheHelper.class
)
public class DDMFormInstanceRecordUADUserCacheHelper {

	public int getDDMFormInstanceRecordIndex(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		List<DDMFormInstanceRecord> ddmFormInstanceRecords =
			_getDDMFormInstanceRecords(
				ddmFormInstanceRecord.getFormInstanceId(),
				ddmFormInstanceRecord.getUserId());

		int index = 0;

		for (DDMFormInstanceRecord currentDDMFormInstanceRecord :
				ddmFormInstanceRecords) {

			if (currentDDMFormInstanceRecord.getFormInstanceRecordId() ==
					ddmFormInstanceRecord.getFormInstanceRecordId()) {

				return index;
			}

			index++;
		}

		return -1;
	}

	private List<DDMFormInstanceRecord> _getDDMFormInstanceRecords(
		long formInstanceId, long userId) {

		DDMFormInstanceRecordUADUserCache ddmFormInstanceRecordUADUserCache =
			null;

		if (_ddmFormInstanceRecordMap.get(formInstanceId) == null) {
			ddmFormInstanceRecordUADUserCache =
				new DDMFormInstanceRecordUADUserCache(
					_ddmFormInstanceRecordLocalService, formInstanceId);

			ddmFormInstanceRecordUADUserCache.putDDMFormInstanceRecords(userId);

			_ddmFormInstanceRecordMap.put(
				formInstanceId, ddmFormInstanceRecordUADUserCache);
		}

		ddmFormInstanceRecordUADUserCache = _ddmFormInstanceRecordMap.get(
			formInstanceId);

		return ddmFormInstanceRecordUADUserCache.getDDMFormInstanceRecords(
			userId);
	}

	@Reference
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

	private final Map<Long, DDMFormInstanceRecordUADUserCache>
		_ddmFormInstanceRecordMap = new HashMap<>();

}