/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.contacts.demo.internal.data.creator;

import com.liferay.portal.kernel.util.ObjectValuePair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.DeviceType;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;

/**
 * @author Matthew Kong
 */
public class PageContextsDataCreator extends DataCreator {

	@Override
	protected Map<String, Object> doCreate(Object[] params) {
		Map<String, Object> pageContext = new HashMap<>();

		pageContext.put("browserName", "Chrome");

		ObjectValuePair<String, String> page = _pages.get(_count++);

		pageContext.put("canonicalUrl", page.getKey());

		String userAgentString = internet.userAgentAny();

		UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);

		Browser browser = userAgent.getBrowser();

		pageContext.put("browserName", browser.getName());

		pageContext.put("contentLanguageId", "en-US");
		pageContext.put("crawler", "False");

		OperatingSystem operatingSystem = userAgent.getOperatingSystem();

		String deviceName = null;

		DeviceType deviceType = operatingSystem.getDeviceType();

		if (deviceType == DeviceType.COMPUTER) {
			deviceName = "Desktop";
		}
		else {
			deviceName = deviceType.getName();
		}

		pageContext.put("deviceType", deviceName);

		pageContext.put("languageId", "en-US");

		pageContext.put("platformName", operatingSystem.getName());

		pageContext.put("referrer", page.getKey());
		pageContext.put("title", page.getValue());
		pageContext.put("url", page.getKey());
		pageContext.put("userAgent", userAgentString);

		return pageContext;
	}

	private static int _count;

	private final List<ObjectValuePair<String, String>> _pages = Arrays.asList(
		new ObjectValuePair<>(
			"https://www.beryl.com", "Beryl Agriculture Wholesale"),
		new ObjectValuePair<>(
			"https://www.beryl.com/account/orderhistory", "Order History"),
		new ObjectValuePair<>(
			"https://www.beryl.com/blogs/loyaltyrewards",
			"Beryl Loyalty Rewards Program"),
		new ObjectValuePair<>(
			"https://www.beryl.com/delivery",
			"Delivery | Beryl Agriculture Wholesale"),
		new ObjectValuePair<>(
			"https://www.beryl.com/design/indoorplanning",
			"Indoor Urban Farming | Beryl Agriculture Wholesale"),
		new ObjectValuePair<>(
			"https://www.beryl.com/products/blog/best-irrigation-strategies",
			"Best Irrigation Strategies by Crop Type | Beryl Blogs"),
		new ObjectValuePair<>(
			"https://www.beryl.com/products/commercial/irrigation",
			"Irrigation | Beryl Agriculture Wholesale"),
		new ObjectValuePair<>(
			"https://www.beryl.com/products/commercial/irrigation/FF-2100",
			"FF-2100 Center Pivot Irrigator | Beryl Agriculture Wholesale"),
		new ObjectValuePair<>(
			"https://www.beryl.com/products/vehicles",
			"Vehicle Leasing | Beryl Agriculture Wholesale"),
		new ObjectValuePair<>(
			"https://www.beryl.com/recalls",
			"Recall Information | Beryl Agriculture Wholesale"));

}