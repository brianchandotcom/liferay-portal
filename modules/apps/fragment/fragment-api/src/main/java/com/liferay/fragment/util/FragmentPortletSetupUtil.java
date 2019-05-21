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

package com.liferay.fragment.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.PortletSetupUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author Pavel Savinov
 */
public class FragmentPortletSetupUtil {

	public static void setPortletBareboneCSSClassName(
			PortletPreferences portletPreferences)
		throws Exception {

		JSONObject jsonObject = PortletSetupUtil.cssToJSONObject(
			portletPreferences);

		JSONObject advancedDataJSONObject = Optional.ofNullable(
			jsonObject.getJSONObject("advancedData")
		).orElse(
			JSONFactoryUtil.createJSONObject()
		);

		String customCSSClassNames = advancedDataJSONObject.getString(
			"customCSSClassName", StringPool.BLANK);

		if (ArrayUtil.contains(
				customCSSClassNames.split(StringPool.SPACE),
				"portlet-barebone")) {

			return;
		}

		advancedDataJSONObject.put(
			"customCSSClassName",
			customCSSClassNames + StringPool.SPACE + "portlet-barebone");

		jsonObject.put("advancedData", advancedDataJSONObject);

		portletPreferences.setValue("portletSetupCss", jsonObject.toString());
	}

}