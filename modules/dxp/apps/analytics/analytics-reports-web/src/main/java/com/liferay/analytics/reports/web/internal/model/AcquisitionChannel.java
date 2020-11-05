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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Objects;

/**
 * @author Cristina González
 */
public class AcquisitionChannel {

	public AcquisitionChannel() {
	}

	public AcquisitionChannel(
		String name, long trafficAmount, double trafficShare) {

		_name = name;
		_trafficAmount = trafficAmount;
		_trafficShare = trafficShare;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AcquisitionChannel)) {
			return false;
		}

		AcquisitionChannel acquisitionChannel = (AcquisitionChannel)object;

		if (Objects.equals(_name, acquisitionChannel._name) &&
			Objects.equals(_trafficAmount, acquisitionChannel._trafficAmount) &&
			Objects.equals(_trafficShare, acquisitionChannel._trafficShare)) {

			return true;
		}

		return false;
	}

	public String getName() {
		return _name;
	}

	public long getTrafficAmount() {
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

	public void setTrafficAmount(long trafficAmount) {
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
			"share", String.format("%.1f", getTrafficShare())
		).put(
			"title", title
		).put(
			"value", Math.toIntExact(getTrafficAmount())
		);
	}

	@Override
	public String toString() {
		JSONObject jsonObject = toJSONObject(StringPool.BLANK, _name);

		return jsonObject.toString();
	}

	private String _name;
	private long _trafficAmount;
	private double _trafficShare;

}