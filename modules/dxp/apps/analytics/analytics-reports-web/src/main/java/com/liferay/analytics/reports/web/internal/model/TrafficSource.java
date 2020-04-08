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

package com.liferay.analytics.reports.web.internal.model;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Objects;

/**
 * @author David Arques
 */
public class TrafficSource {

	public TrafficSource() {
	}

	public TrafficSource(String name, int trafficAmount, double trafficShare) {
		_name = name;
		_trafficAmount = trafficAmount;
		_trafficShare = trafficShare;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TrafficSource)) {
			return false;
		}

		TrafficSource trafficSource = (TrafficSource)obj;

		if (Objects.equals(_name, trafficSource._name) &&
			Objects.equals(_trafficAmount, trafficSource._trafficAmount) &&
			Objects.equals(_trafficShare, trafficSource._trafficShare)) {

			return true;
		}

		return false;
	}

	public String getName() {
		return _name;
	}

	public int getTrafficAmount() {
		return _trafficAmount;
	}

	public double getTrafficShare() {
		return _trafficShare;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_name, _trafficAmount, _trafficShare);
	}

	public void setName(String name) {
		_name = name;
	}

	public void setTrafficAmount(int trafficAmount) {
		_trafficAmount = trafficAmount;
	}

	public void setTrafficShare(double trafficShare) {
		_trafficShare = trafficShare;
	}

	public JSONObject toJSONObject(String helpMessage, String title) {
		return JSONUtil.put(
			"helpMessage", helpMessage
		).put(
			"name", getName()
		).put(
			"share", getTrafficShare()
		).put(
			"title", title
		).put(
			"value", getTrafficAmount()
		);
	}

	private String _name;
	private int _trafficAmount;
	private double _trafficShare;

}