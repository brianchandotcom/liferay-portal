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

package com.liferay.user.associated.data.display;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.user.associated.data.entity.UADEntity;

import java.util.List;
import java.util.Map;

/**
 * @author William Newbury
 */
public abstract class BaseUADEntityDisplay implements UADEntityDisplay {

	@Override
	public String getUADEntityNonanonymizableFieldValues(UADEntity uadEntity) {
		Map<String, Object> uadEntityNonanonymizableFieldValuesMap =
			uadEntity.getUADEntityNonanonymizableFieldValues();

		if (MapUtil.isEmpty(uadEntityNonanonymizableFieldValuesMap)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(
			(uadEntityNonanonymizableFieldValuesMap.size() * 4) - 1);

		for (Map.Entry<String, Object> entry :
				uadEntityNonanonymizableFieldValuesMap.entrySet()) {

			sb.append(entry.getKey());
			sb.append(StringPool.COLON);
			sb.append(StringPool.SPACE);
			sb.append(String.valueOf(entry.getValue()));
			sb.append(StringPool.NEW_LINE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	@Override
	public String getUADEntityTypeNonanonymizableFieldNames() {
		List<String> uadEntityTypeNonanonymizableFieldNamesList =
			getUADEntityTypeNonanonymizableFieldNamesList();

		if (ListUtil.isEmpty(uadEntityTypeNonanonymizableFieldNamesList)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(
			(uadEntityTypeNonanonymizableFieldNamesList.size() * 2) - 1);

		for (String fieldName : uadEntityTypeNonanonymizableFieldNamesList) {
			sb.append(fieldName);
			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

}