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

package com.liferay.frontend.data.set.views.web.internal.display.context;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author Marko Cikos
 */
public class FDSViewsDisplayContext {

	public JSONArray getHeadlessResourcesJSONArray() {
		return JSONUtil.putAll(
			JSONUtil.put(
				"internalClassName", "sampleInternalClassName1"
			).put(
				"label", "Sample Provider 1"
			),
			JSONUtil.put(
				"internalClassName", "sampleInternalClassName2"
			).put(
				"label", "Sample Provider 2"
			));
	}

}