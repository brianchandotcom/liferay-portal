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

package com.liferay.journal.web.messaging;

import com.liferay.journal.web.configuration.JournalWebConfigurationValues;
import com.liferay.journal.web.constants.JournalPortletKeys;
import com.liferay.journal.web.portlet.JournalPortlet;
import com.liferay.portal.kernel.messaging.BaseSchedulerEntryMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Tina Tian
 */
@Component(
	property = {"javax.portlet.name=" + JournalPortletKeys.JOURNAL},
	service = SchedulerEntry.class
)
public class CheckArticleMessageListener
	extends BaseSchedulerEntryMessageListener {

	@Activate
	protected void activate() {
		schedulerEntry.setTimeUnit(TimeUnit.MINUTE);
		schedulerEntry.setTriggerType(TriggerType.SIMPLE);
		schedulerEntry.setTriggerValue(
			JournalWebConfigurationValues.CHECK_INTERVAL);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		JournalArticleLocalServiceUtil.checkArticles();
	}

	@Reference
	protected void setJournalPortlet(JournalPortlet journalPortlet) {
	}

}