/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.subscription;

/**
 * @author Matthew Kong
 */
public class FaroSubscriptionPlan {

	public FaroSubscriptionPlan(
		String baseSubscriptionPlan, String name, int individualsLimit,
		int pageViewsLimit, int price) {

		_baseSubscriptionPlan = baseSubscriptionPlan;
		_name = name;
		_individualsLimit = individualsLimit;
		_pageViewsLimit = pageViewsLimit;
		_price = price;
	}

	public String getBaseSubscriptionPlan() {
		return _baseSubscriptionPlan;
	}

	public int getIndividualsLimit() {
		return _individualsLimit;
	}

	public String getName() {
		return _name;
	}

	public int getPageViewsLimit() {
		return _pageViewsLimit;
	}

	public int getPrice() {
		return _price;
	}

	public void setBaseSubscriptionPlan(String baseSubscriptionPlan) {
		_baseSubscriptionPlan = baseSubscriptionPlan;
	}

	public void setIndividualsLimit(int individualsLimit) {
		_individualsLimit = individualsLimit;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPageViewsLimit(int pageViewsLimit) {
		_pageViewsLimit = pageViewsLimit;
	}

	public void setPrice(int price) {
		_price = price;
	}

	private String _baseSubscriptionPlan;
	private int _individualsLimit;
	private String _name;
	private int _pageViewsLimit;
	private int _price;

}