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

	public int getFormInstanceRecordIndex(
		DDMFormInstanceRecord ddmFormInstanceRecord) {

		List<DDMFormInstanceRecord> ddmFormInstanceRecords =
			_getFormInstanceRecords(
				ddmFormInstanceRecord.getFormInstanceId(),
				ddmFormInstanceRecord.getUserId());

		int index = 0;

		for (DDMFormInstanceRecord currFormInstanceRecord :
				ddmFormInstanceRecords) {

			if (currFormInstanceRecord.getFormInstanceRecordId() ==
					ddmFormInstanceRecord.getFormInstanceRecordId()) {

				return index;
			}

			index++;
		}

		return -1;
	}

	private List<DDMFormInstanceRecord> _getFormInstanceRecords(
		long formInstanceId, long userId) {

		DDMFormInstanceRecordUADUserCache cache = null;

		if (_ddmFormInstanceRecordMap.get(formInstanceId) == null) {
			cache = new DDMFormInstanceRecordUADUserCache(
				_ddmFormInstanceRecordLocalService, formInstanceId);

			cache.putFormInstanceRecords(userId);

			_ddmFormInstanceRecordMap.put(formInstanceId, cache);
		}

		cache = _ddmFormInstanceRecordMap.get(formInstanceId);

		return cache.getFormInstanceRecords(userId);
	}

	@Reference
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

	private final Map<Long, DDMFormInstanceRecordUADUserCache>
		_ddmFormInstanceRecordMap = new HashMap<>();

}