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

package com.liferay.commerce.address.italy.internal.upgrade.v1_1_0;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAddressItalyUpgradeSchema extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		populateCommerceRegion();
	}

	protected void populateCommerceRegion() throws Exception {
		JSONObject addressItalyJSONObject =
			_getCommerceAddressItalyJSONObject();

		long commerceCountryId = addressItalyJSONObject.getLong(
			"commerceCountryId");

		JSONArray regionsJSONArray = addressItalyJSONObject.getJSONArray(
			"regions");

		for (int i = 0; i < regionsJSONArray.length(); i++) {
			JSONObject regionJSONObject = regionsJSONArray.getJSONObject(i);

			long commerceRegionId = regionJSONObject.getLong(
				"commerceRegionId");
			String name = regionJSONObject.getString("name");
			String code = regionJSONObject.getString("code");
			double priority = regionJSONObject.getInt("priority");

			StringBuilder sb = new StringBuilder(19);

			sb.append("insert into CommerceRegion ");
			sb.append("(commerceCountryId, commerceRegionId, name, ");
			sb.append("code_, priority) values ");
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(commerceCountryId);
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(commerceRegionId);
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(StringPool.QUOTE);
			sb.append(name);
			sb.append(StringPool.QUOTE);
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(StringPool.QUOTE);
			sb.append(code);
			sb.append(StringPool.QUOTE);
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(priority);
			sb.append(StringPool.CLOSE_PARENTHESIS);
			sb.append(StringPool.SEMICOLON);

			runSQL(sb.toString());
		}
	}

	private JSONObject _getCommerceAddressItalyJSONObject() throws Exception {
		Class<?> clazz = getClass();

		String addressItalyPath =
			"com/liferay/commerce/address/italy/internal/upgrade/v1_1_0" +
				"/dependencies/italy.json";

		String addressItalyJSON = StringUtil.read(
			clazz.getClassLoader(), addressItalyPath, false);

		JSONObject addressItalyJSONObject = JSONFactoryUtil.createJSONObject(
			addressItalyJSON);

		return addressItalyJSONObject;
	}

}