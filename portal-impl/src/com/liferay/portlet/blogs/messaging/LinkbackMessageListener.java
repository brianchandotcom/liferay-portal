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

package com.liferay.portlet.blogs.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portlet.blogs.linkback.LinkbackConsumer;
import com.liferay.portlet.blogs.linkback.LinkbackConsumerUtil;
import com.liferay.portlet.blogs.util.LinkbackProducerUtil;

/**
 * @author Alexander Chow
 * @author Tina Tian
 */
public class LinkbackMessageListener extends BaseMessageListener {

	public LinkbackMessageListener() {
		_linkbackConsumer = LinkbackConsumerUtil.getLinkbackConsumer();
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		_linkbackConsumer.verifyNewTrackbacks();
		LinkbackProducerUtil.sendQueuedPingbacks();
	}

	private LinkbackConsumer _linkbackConsumer;

}