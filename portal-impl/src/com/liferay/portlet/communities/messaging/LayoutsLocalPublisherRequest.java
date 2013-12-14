/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.communities.messaging;

import java.util.Date;
import java.util.Map;

/**
 * @author     Bruno Farache
 * @deprecated As of 6.1.0, replaced by {@link
 *             com.liferay.portal.messaging.LayoutsLocalPublisherRequest}
 */
@Deprecated
public class LayoutsLocalPublisherRequest
	extends com.liferay.portal.messaging.LayoutsLocalPublisherRequest {

	public LayoutsLocalPublisherRequest() {
	}

	public LayoutsLocalPublisherRequest(
		String command, long userId, long sourceGroupId, long targetGroupId,
		boolean privateLayout, Map<Long, Boolean> layoutIdMap,
		Map<String, String[]> parameterMap, Date startDate, Date endDate) {

		super(
			command, userId, sourceGroupId, targetGroupId, privateLayout,
			layoutIdMap, parameterMap, startDate, endDate);
	}

}