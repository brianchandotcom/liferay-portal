/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.admin.poller;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.poller.BasePollerProcessor;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.util.ShutdownUtil;

/**
 * @author Zsolt Szabo
 */
public class ShutdownPollerProcessor extends BasePollerProcessor {

	protected void checkShutdown(
			PollerRequest pollerRequest, PollerResponse pollerResponse)
		throws Exception {

		JSONObject shutdownInfo = JSONFactoryUtil.createJSONObject();

		if (ShutdownUtil.isInProcess()) {
			shutdownInfo.put("process", ShutdownUtil.getInProcess());
			shutdownInfo.put(
				"message", HtmlUtil.escape(ShutdownUtil.getMessage()));
		}
		else {
			shutdownInfo.put("process", 0);
		}

		pollerResponse.setParameter("shutdown", shutdownInfo);
	}

	@Override
	protected void doReceive(
			PollerRequest pollerRequest, PollerResponse pollerResponse)
		throws Exception {

		checkShutdown(pollerRequest, pollerResponse);
	}

	@Override
	protected void doSend(PollerRequest pollerRequest)
		throws Exception {
	}

}