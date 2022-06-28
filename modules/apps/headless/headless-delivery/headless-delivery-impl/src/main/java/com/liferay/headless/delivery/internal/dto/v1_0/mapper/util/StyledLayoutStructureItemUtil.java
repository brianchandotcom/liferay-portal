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

package com.liferay.headless.delivery.internal.dto.v1_0.mapper.util;

import com.liferay.layout.util.structure.StyledLayoutStructureItem;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;

import java.util.Set;

/**
 * @author Jürgen Kappler
 */
public class StyledLayoutStructureItemUtil {

	public static String[] getCssClasses(
		StyledLayoutStructureItem styledLayoutStructureItem) {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-147511"))) {
			return null;
		}

		Set<String> cssClasses = styledLayoutStructureItem.getCssClasses();

		if (SetUtil.isEmpty(cssClasses)) {
			return null;
		}

		return ArrayUtil.toStringArray(cssClasses);
	}

	public static String getCustomCSS(
		StyledLayoutStructureItem styledLayoutStructureItem) {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-147511"))) {
			return null;
		}

		String customCSS = styledLayoutStructureItem.getCustomCSS();

		if (Validator.isNotNull(customCSS)) {
			return customCSS;
		}

		return null;
	}

}