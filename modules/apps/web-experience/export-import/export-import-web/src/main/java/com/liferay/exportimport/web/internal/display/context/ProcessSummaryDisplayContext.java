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

package com.liferay.exportimport.web.internal.display.context;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;

/**
 * @author Péter Alius
 */
public class ProcessSummaryDisplayContext {

	public ArrayList getPageNames(JSONArray layoutsArray) {
		ArrayList pageNames = new ArrayList();

		for (int i = 0; i < layoutsArray.length(); ++i) {
			JSONObject layoutArrayElement = layoutsArray.getJSONObject(i);

			String pageName = layoutArrayElement.getString("name");

			pageNames.add(pageName);

			if (layoutArrayElement.getBoolean("hasChildren")) {
				ArrayList childPageNames = _getChildPageNames(
					pageName, layoutArrayElement.getJSONObject("children"));

				pageNames.addAll(childPageNames);
			}
		}

		return pageNames;
	}

	private ArrayList _getChildPageNames(
		String basePageName, JSONObject layoutArrayChildElement) {

		ArrayList pageNames = new ArrayList();

		JSONArray childrenLayouts = layoutArrayChildElement.getJSONArray(
			"layouts");

		for (int i = 0; i < childrenLayouts.length(); ++i) {
			JSONObject childLayout = childrenLayouts.getJSONObject(i);

			String childPageName =
				basePageName + StringPool.FORWARD_SLASH +
					childLayout.getString("name");

			pageNames.add(childPageName);

			if (childLayout.getBoolean("hasChildren")) {
				ArrayList childPageNames = _getChildPageNames(
					childPageName, childLayout.getJSONObject("children"));

				pageNames.addAll(childPageNames);
			}
		}

		return pageNames;
	}

}