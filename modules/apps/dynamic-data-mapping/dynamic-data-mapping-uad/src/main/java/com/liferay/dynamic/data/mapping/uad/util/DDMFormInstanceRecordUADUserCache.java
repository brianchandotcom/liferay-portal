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
import com.liferay.portal.kernel.dao.orm.QueryUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marcos Martins
 */
public class DDMFormInstanceRecordUADUserCache {

	public DDMFormInstanceRecordUADUserCache(
		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService,
		long formInstanceId) {

		_ddmFormInstanceRecordLocalService = ddmFormInstanceRecordLocalService;
		_ddmFormInstanceRecordMap = new HashMap<>();
		_formInstanceId = formInstanceId;
	}

	public List<DDMFormInstanceRecord> getDDMFormInstanceRecords(long userId) {
		if (_ddmFormInstanceRecordMap.get(userId) == null) {
			putDDMFormInstanceRecords(userId);
		}

		return _ddmFormInstanceRecordMap.get(userId);
	}

	public void putDDMFormInstanceRecords(long userId) {
		List<DDMFormInstanceRecord> ddmFormInstanceRecords = new ArrayList<>();

		ddmFormInstanceRecords.addAll(
			_ddmFormInstanceRecordLocalService.getFormInstanceRecords(
				_formInstanceId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null));

		ddmFormInstanceRecords.sort(
			Comparator.comparing(DDMFormInstanceRecord::getCreateDate));

		_ddmFormInstanceRecordMap.put(userId, ddmFormInstanceRecords);
	}

	private final DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;
	private final Map<Long, List<DDMFormInstanceRecord>>
		_ddmFormInstanceRecordMap;
	private final long _formInstanceId;

}