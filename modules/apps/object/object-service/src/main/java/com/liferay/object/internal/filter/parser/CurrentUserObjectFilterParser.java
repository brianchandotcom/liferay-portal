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

import org.osgi.service.component.annotations.Component;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	property = "filter.type=" + ObjectFilterConstants.TYPE_CURRENT_USER,
	service = ObjectFilterParser.class
)
public class CurrentUserObjectFilterParser implements ObjectFilterParser {

	@Override
	public String parse(ObjectFilter objectFilter) {
		Map<String, Object> map = ObjectMapperUtil.readValue(
			Map.class, objectFilter.getJSON());

		return StringBundler.concat(
			"(", objectFilter.getFilterBy(), " eq '", map.get("currentUserId"),
			"')");
	}

}