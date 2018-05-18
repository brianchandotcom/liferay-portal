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

package com.liferay.commerce.model;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxRate {

	public CommerceTaxRate(String name, String label, double rate) {
		_name = name;
		_label = label;
		_rate = rate;
	}

	public String getLabel() {
		return _label;
	}

	public String getName() {
		return _name;
	}

	public double getRate() {
		return _rate;
	}

	private final String _label;
	private final String _name;
	private final double _rate;

}