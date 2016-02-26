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

package com.liferay.portal.kernel.poller;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.Message;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Edward Han
 */
public interface PollerResponse extends Serializable {

	public static final String POLLER_HINT_HIGH_CONNECTIVITY =
		"pollerHintHighConnectivity";

	public void close(
		Message message, PollerHeader pollerHeader, String portletId,
		String chunkId);

	public PollerHeader getPollerHeader();

	public String getPortletId();

	public boolean isEmpty();

	public void setParameter(String name, JSONArray value)
		throws PollerResponseClosedException;

	public void setParameter(String name, JSONObject value)
		throws PollerResponseClosedException;

	public void setParameter(String name, String value)
		throws PollerResponseClosedException;

	public JSONObject toJSONObject();

}