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

package com.liferay.data.engine.rest.internal.field.type.v1_0.util;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.internal.field.type.v1_0.DataFieldOption;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Marcela Cunha
 */
public class DataFieldOptionUtil {

	public static List<DataFieldOption> toDataFieldOptions(
		JSONObject jsonObject) {

		List<DataFieldOption> dataFieldOptions = new ArrayList<>();

		if (jsonObject == null) {
			return dataFieldOptions;
		}

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			Map<String, Object> localizationMap =
				LocalizedValueUtil.toLocalizedValues(
					jsonObject.getJSONObject(key));

			DataFieldOption dataFieldOption = new DataFieldOption(
				localizationMap, key);

			dataFieldOptions.add(dataFieldOption);
		}

		return dataFieldOptions;
	}

	public static List<DataFieldOption> toDataFieldOptions(
		List<DataFieldOption> dataFieldOptions, String languageId) {

		if (ListUtil.isEmpty(dataFieldOptions)) {
			return Collections.emptyList();
		}

		Stream<DataFieldOption> stream = dataFieldOptions.stream();

		return stream.map(
			dataFieldOption -> new DataFieldOption(
				dataFieldOption.getLabel(languageId), languageId,
				dataFieldOption.getValue())
		).collect(
			Collectors.toList()
		);
	}

	public static JSONObject toJSONObject(
			List<DataFieldOption> dataFieldOptions)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (ListUtil.isEmpty(dataFieldOptions)) {
			return jsonObject;
		}

		for (DataFieldOption dataFieldOption : dataFieldOptions) {
			JSONObject labelJSONObject = JSONFactoryUtil.createJSONObject();

			Map<String, Object> labels = dataFieldOption.getLabels();

			for (Map.Entry<String, Object> entry : labels.entrySet()) {
				labelJSONObject.put(entry.getKey(), entry.getValue());
			}

			jsonObject.put(dataFieldOption.getValue(), labelJSONObject);
		}

		return jsonObject;
	}

}