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

package com.liferay.blogs.web.messaging;

import com.liferay.blogs.web.configuration.BlogsWebConfigurationValues;
import com.liferay.blogs.web.constants.BlogsPortletKeys;
import com.liferay.portal.kernel.messaging.BaseSchedulerEntryMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.model.Portlet;
import com.liferay.portlet.blogs.linkback.LinkbackConsumer;
import com.liferay.portlet.blogs.linkback.LinkbackConsumerUtil;
import com.liferay.portlet.blogs.util.LinkbackProducerUtil;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 * @author Tina Tian
 */
@Component(
	property = {"javax.portlet.name=" + BlogsPortletKeys.BLOGS},
	service = SchedulerEntry.class
)
public class LinkbackMessageListener extends BaseSchedulerEntryMessageListener {

	@Activate
	protected void activate() {
		schedulerEntry.setTimeUnit(TimeUnit.MINUTE);
		schedulerEntry.setTriggerType(TriggerType.SIMPLE);
		schedulerEntry.setTriggerValue(
			BlogsWebConfigurationValues.LINKBACK_JOB_INTERVAL);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		_linkbackConsumer.verifyNewTrackbacks();

		LinkbackProducerUtil.sendQueuedPingbacks();
	}

	@Reference(target = "(javax.portlet.name=" + BlogsPortletKeys.BLOGS + ")")
	protected void setPortlet(Portlet portlet) {
	}

	@Reference(target = "(original.bean=*)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

	private final LinkbackConsumer _linkbackConsumer =
		LinkbackConsumerUtil.getLinkbackConsumer();

}