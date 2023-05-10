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

package com.liferay.object.internal.filter.parser;

import com.liferay.object.constants.ObjectFilterConstants;
import com.liferay.object.model.ObjectFilter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = {
		"filter.type=" + ObjectFilterConstants.TYPE_EXCLUDES,
		"filter.type=" + ObjectFilterConstants.TYPE_INCLUDES
	},
	service = ObjectFilterParser.class
)
public class InclusionOperatorsObjectFilterParser
	implements ObjectFilterParser {

	@Override
	public String parse(ObjectFilter objectFilter) {
		Map<String, Object> map = ObjectMapperUtil.readValue(
			Map.class, objectFilter.getJSON());

		boolean excludes = Objects.equals(
			ObjectFilterConstants.TYPE_EXCLUDES, objectFilter.getFilterType());

		if (excludes) {
			map = (Map<String, Object>)map.get("not");
		}

		List<String> values = new ArrayList<>();

		if (Objects.equals(objectFilter.getFilterBy(), "status")) {
			for (Object value : (Object[])map.get("in")) {
				values.add(
					StringBundler.concat(
						"(x ", excludes ? "ne " : "eq ", value, ")"));
			}

			return StringBundler.concat(
				"(", objectFilter.getFilterBy(), "/any(x:",
				StringUtil.merge(values, excludes ? " and " : " or "), "))");
		}

		for (Object value : (Object[])map.get("in")) {
			values.add(StringUtil.quote(String.valueOf(value)));
		}

		String filterString = StringBundler.concat(
			"( ", objectFilter.getFilterBy(), " in (",
			StringUtil.merge(values, StringPool.COMMA_AND_SPACE), "))");

		if (excludes) {
			return StringBundler.concat("(not ", filterString, ")");
		}

		return filterString;
	}

}