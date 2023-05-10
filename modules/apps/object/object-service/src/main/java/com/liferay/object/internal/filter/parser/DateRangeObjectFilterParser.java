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
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "filter.type=" + ObjectFilterConstants.TYPE_DATE_RANGE,
	service = ObjectFilterParser.class
)
public class DateRangeObjectFilterParser implements ObjectFilterParser {

	@Override
	public String parse(ObjectFilter objectFilter) {
		Map<String, Object> map = ObjectMapperUtil.readValue(
			Map.class, objectFilter.getJSON());

		String ge = String.valueOf(map.get("ge"));
		String le = String.valueOf(map.get("le"));

		String filterBy = objectFilter.getFilterBy();

		if (Objects.equals(objectFilter.getFilterBy(), "createDate") ||
			Objects.equals(objectFilter.getFilterBy(), "modifiedDate")) {

			ge += "T00:00:00.000Z";
			le += "T23:59:59.999Z";

			if (Objects.equals(objectFilter.getFilterBy(), "createDate")) {
				filterBy = "dateCreated";
			}
			else {
				filterBy = "dateModified";
			}
		}

		return StringBundler.concat(
			"((", filterBy, " ge ", ge, ") and (", filterBy, " le ", le, "))");
	}

}