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

package com.liferay.lcs.messaging;

import java.util.Map;

/**
 * Represents a Liferay Cloud Services Protocol health message. Health messages
 * transfer the sender's health state information in their payload.
 *
 * <p>
 * Health messages include a health type, which indicates its particular
 * software module or infrastructure (e.g. cluster). These health types are
 * represented in this class's public health type constants.
 * </p>
 *
 * @author  Ivica Cardic
 * @version 2.1.1
 * @since   LCS 0.1
 */
public class HealthMessage extends Message {

	public static final String HEALTH_TYPE_CLUSTER = "cluster";

	/**
	 * Returns the message's health type.
	 *
	 * @return the message's health type
	 * @since  LCS 0.1
	 */
	public String getHealthType() {
		return _healthType;
	}

	/**
	 * Sets the message's health type.
	 *
	 * @param healthType the health type
	 * @since LCS 0.1
	 */
	public void setHealthType(String healthType) {
		_healthType = healthType;
	}

	@Override
	public String toString() {
		if (_toString != null) {
			return _toString;
		}

		StringBuilder sb = new StringBuilder(11);

		sb.append("{className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getName());

		sb.append(", createTime=");
		sb.append(getCreateTime());
		sb.append(", healthType=");
		sb.append(_healthType);
		sb.append(", key=");
		sb.append(getKey());
		sb.append(", payload=");
		sb.append(getPayload());
		sb.append(", values=");

		if (getValues() != null) {
			Map<String, Object> values = getValues();

			sb.append(values.toString());
		}

		sb.append("}");

		_toString = sb.toString();

		return _toString;
	}

	private String _healthType;
	private String _toString;

}