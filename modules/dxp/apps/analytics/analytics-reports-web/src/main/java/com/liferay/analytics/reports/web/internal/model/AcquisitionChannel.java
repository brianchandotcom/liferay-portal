/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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