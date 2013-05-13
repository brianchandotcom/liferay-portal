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

package com.liferay.portal.dao.shard;

import com.liferay.portal.kernel.dao.shard.ShardUtil;
import com.liferay.portal.kernel.poller.PollerException;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;

/**
 * @author Alexander Chow
 */
public class ShardPollerProcessorWrapper implements PollerProcessor {

	public ShardPollerProcessorWrapper(PollerProcessor pollerProcessor) {
		_pollerProcessor = pollerProcessor;
	}

	@Override
	public void receive(
			PollerRequest pollerRequest, PollerResponse pollerResponse)
		throws PollerException {

		try {
			ShardUtil.pushCompanyService(pollerRequest.getCompanyId());

			_pollerProcessor.receive(pollerRequest, pollerResponse);
		}
		finally {
			ShardUtil.popCompanyService();
		}
	}

	@Override
	public void send(PollerRequest pollerRequest) throws PollerException {
		try {
			ShardUtil.pushCompanyService(pollerRequest.getCompanyId());

			_pollerProcessor.send(pollerRequest);
		}
		finally {
			ShardUtil.popCompanyService();
		}
	}

	private PollerProcessor _pollerProcessor;

}