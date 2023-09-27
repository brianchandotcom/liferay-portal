/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.filter.parser;

import com.liferay.object.constants.ObjectFilterConstants;
import com.liferay.object.model.ObjectFilter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = {
		"filter.type=" + ObjectFilterConstants.TYPE_EQUALS,
		"filter.type=" + ObjectFilterConstants.TYPE_NOT_EQUALS
	},
	service = ObjectFilterParser.class
)
public class EqualityOperatorsObjectFilterParser implements ObjectFilterParser {

	@Override
	public String parse(ObjectFilter objectFilter) {
		Map<String, Object> map = ObjectMapperUtil.readValue(
			Map.class, objectFilter.getJSON());

		String operator = "ne";

		if (Objects.equals(
				ObjectFilterConstants.TYPE_EQUALS,
				objectFilter.getFilterType())) {

			operator = "eq";
		}

		return StringBundler.concat(
			"( ", objectFilter.getFilterBy(), StringPool.SPACE, operator,
			StringPool.SPACE, map.get(operator), " )");
	}

}