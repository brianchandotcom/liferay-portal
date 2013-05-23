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

package com.liferay.portal.resiliency.spi.agent;

import com.liferay.portal.kernel.resiliency.spi.agent.annotation.Direction;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.Distributed;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.DistributedRegistry;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.MatchType;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shuyang Zhou
 */
public class RequestAttributes {

	@Distributed(direction = Direction.DUPLEX, matchType = MatchType.EXACT)
	public static final String ATTRIBUTE1 = "ATTRIBUTE1";

	@Distributed(direction = Direction.REQUEST, matchType = MatchType.EXACT)
	public static final String ATTRIBUTE2 = "ATTRIBUTE2";

	@Distributed(
		direction = Direction.RESPONSE, matchType = MatchType.EXACT)
	public static final String ATTRIBUTE3 = "ATTRIBUTE3";

	public static void setRequestAttributes(
		HttpServletRequest httpServletRequest) {

		DistributedRegistry.registerDistributed(RequestAttributes.class);

		httpServletRequest.setAttribute(ATTRIBUTE1, ATTRIBUTE1);
		httpServletRequest.setAttribute(ATTRIBUTE2, ATTRIBUTE2);
		httpServletRequest.setAttribute(ATTRIBUTE3, ATTRIBUTE3);
	}

}