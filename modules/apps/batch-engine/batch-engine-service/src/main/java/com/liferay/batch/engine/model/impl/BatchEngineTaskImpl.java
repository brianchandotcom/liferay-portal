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

package com.liferay.batch.engine.model.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class BatchEngineTaskImpl extends BatchEngineTaskBaseImpl {

	public BatchEngineTaskImpl() {
	}

	@Override
	public Map<String, String> getFieldNameMappingMap() {
		if (Validator.isNull(getFieldNameMapping())) {
			return Collections.emptyMap();
		}

		Map<String, String> fieldNameMappingMap = new HashMap<>();

		String[] fieldNameMappings = StringUtil.split(
			getFieldNameMapping(), ',');

		for (String fieldNameMapping : fieldNameMappings) {
			String[] fieldNames = StringUtil.split(fieldNameMapping, '=');

			fieldNameMappingMap.put(fieldNames[0], fieldNames[1]);
		}

		return fieldNameMappingMap;
	}

	@Override
	public void setFieldNameMappingMap(
		Map<String, String> fieldNameMappingMap) {

		StringBundler sb = new StringBundler();

		for (Map.Entry<String, String> entry : fieldNameMappingMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append(StringPool.EQUAL);
			sb.append(entry.getValue());
			sb.append(StringPool.COMMA);
		}

		if (sb.length() > 0) {
			sb.setIndex(sb.length() - 1);

			setFieldNameMapping(sb.toString());
		}
	}

}