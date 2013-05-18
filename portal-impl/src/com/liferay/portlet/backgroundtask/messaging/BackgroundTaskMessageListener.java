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

package com.liferay.portlet.backgroundtask.messaging;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portlet.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portlet.backgroundtask.model.BTEntry;
import com.liferay.portlet.backgroundtask.service.BTEntryLocalService;
import com.liferay.portlet.backgroundtask.util.BackgroundTaskConstants;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		long btEntryId = (Long)message.get("btEntryId");

		BTEntry btEntry= btEntryLocalService.getBTEntry(btEntryId);

		String taskExecutorClassName = btEntry.getTaskExecutorClassName();
		String servletContextNames = btEntry.getServletContextNames();

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		if (Validator.isNotNull(servletContextNames)) {
			classLoader = ClassLoaderUtil.getAggregatedPluginsClassLoader(
				StringUtil.split(servletContextNames, StringPool.COMMA), false);
		}

		ServiceContext serviceContext = new ServiceContext();

		btEntryLocalService.updateBTEntry(
			btEntryId, BackgroundTaskConstants.STATUS_IN_PROGRESS,
			serviceContext);

		try {
			BackgroundTaskExecutor backgroundTaskExecutor =
				(BackgroundTaskExecutor)InstanceFactory.newInstance(
					classLoader, taskExecutorClassName);

			backgroundTaskExecutor.execute(btEntry, classLoader);

			btEntryLocalService.updateBTEntry(
				btEntryId, BackgroundTaskConstants.STATUS_SUCCESSFUL,
				serviceContext);
		}
		catch (Exception e) {
			btEntryLocalService.updateBTEntry(
				btEntryId, BackgroundTaskConstants.STATUS_FAILED,
				serviceContext);
		}
	}

	@BeanReference(type = BTEntryLocalService.class)
	private BTEntryLocalService btEntryLocalService;

}