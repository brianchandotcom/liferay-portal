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

package com.liferay.object.admin.rest.internal.dto.v1_0.util;

import com.liferay.object.admin.rest.dto.v1_0.ObjectFieldSetting;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFilterConstants;
import com.liferay.object.model.ObjectFilter;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectFilterLocalService;
import com.liferay.object.util.ObjectFilterUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Carolina Barbosa
 */
public class ObjectFieldSettingUtil {

	public static com.liferay.object.model.ObjectFieldSetting
		toObjectFieldSetting(
			String businessType, ObjectFieldSetting objectFieldSetting,
			ObjectFieldSettingLocalService objectFieldSettingLocalService,
			ObjectFilterLocalService objectFilterLocalService) {

		com.liferay.object.model.ObjectFieldSetting
			serviceBuilderObjectFieldSetting =
				objectFieldSettingLocalService.createObjectFieldSetting(0L);

		serviceBuilderObjectFieldSetting.setName(objectFieldSetting.getName());
		serviceBuilderObjectFieldSetting.setValue(
			String.valueOf(objectFieldSetting.getValue()));

		if (Objects.equals(
				businessType, ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION) &&
			Objects.equals(
				objectFieldSetting.getName(), ObjectFilterConstants.FILTERS)) {

			List<ObjectFilter> objectFilters = new ArrayList<>();

			for (Map<String, Object> value :
					(List<Map<String, Object>>)objectFieldSetting.getValue()) {

				ObjectFilter objectFilter =
					objectFilterLocalService.createObjectFilter(0L);

				objectFilter.setFilterBy(
					String.valueOf(value.get(ObjectFilterConstants.FILTER_BY)));
				objectFilter.setFilterType(
					String.valueOf(
						value.get(ObjectFilterConstants.FILTER_TYPE)));
				objectFilter.setJson(
					String.valueOf(
						JSONFactoryUtil.createJSONObject(
							(Map)value.get(ObjectFilterConstants.JSON))));

				objectFilters.add(objectFilter);
			}

			serviceBuilderObjectFieldSetting.setObjectFilters(objectFilters);
		}

		return serviceBuilderObjectFieldSetting;
	}

	public static ObjectFieldSetting toObjectFieldSetting(
		String businessType,
		com.liferay.object.model.ObjectFieldSetting
			serviceBuilderObjectFieldSetting) {

		if (serviceBuilderObjectFieldSetting == null) {
			return null;
		}

		return new ObjectFieldSetting() {
			{
				name = serviceBuilderObjectFieldSetting.getName();
				value = serviceBuilderObjectFieldSetting.getValue();

				if (Objects.equals(
						businessType,
						ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION) &&
					Objects.equals(name, ObjectFilterConstants.FILTERS)) {

					value = ObjectFilterUtil.getObjectFiltersJSONArray(
						serviceBuilderObjectFieldSetting.getObjectFilters());
				}
			}
		};
	}

}