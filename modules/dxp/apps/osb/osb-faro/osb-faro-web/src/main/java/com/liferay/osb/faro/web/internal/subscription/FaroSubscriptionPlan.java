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