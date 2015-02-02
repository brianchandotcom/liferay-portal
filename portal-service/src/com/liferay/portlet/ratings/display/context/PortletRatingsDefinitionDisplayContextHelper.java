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

package com.liferay.portlet.ratings.display.context;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portlet.ratings.definition.PortletRatingsDefinitionUtil;
import com.liferay.portlet.ratings.definition.PortletRatingsDefinitionValues;

import java.util.Map;

/**
 * @author Roberto Díaz
 */
public class PortletRatingsDefinitionDisplayContextHelper {

	public boolean showRatingsSection(String[] sections) {
		if (!ArrayUtil.contains(sections, "ratings")) {
			return false;
		}

		Map<String, PortletRatingsDefinitionValues>
			portletRatingsDefinitionValuesMap =
				PortletRatingsDefinitionUtil.
					getPortletRatingsDefinitionValuesMap();

		if (portletRatingsDefinitionValuesMap.isEmpty()) {
			return false;
		}

		return true;
	}

}