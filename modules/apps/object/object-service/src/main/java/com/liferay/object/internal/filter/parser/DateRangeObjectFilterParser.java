/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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