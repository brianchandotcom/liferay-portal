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

package com.liferay.commerce.wish.list.internal.messaging;

import com.liferay.commerce.wish.list.internal.configuration.CommerceWishListConfiguration;
import com.liferay.commerce.wish.list.service.CommerceWishListLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.wish.list.internal.configuration.CommerceWishListConfiguration",
	immediate = true, service = CheckGuestCommerceWishListsMessageListener.class
)
public class CheckGuestCommerceWishListsMessageListener
	extends BaseMessageListener {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		Class<?> clazz = getClass();

		String className = clazz.getName();

		_commerceWishListConfiguration = ConfigurableUtil.createConfigurable(
			CommerceWishListConfiguration.class, properties);

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null,
			_commerceWishListConfiguration.checkInterval(), TimeUnit.MINUTE);

		SchedulerEntry schedulerEntry = new SchedulerEntryImpl(
			className, trigger);

		_schedulerEngineHelper.register(
			this, schedulerEntry, DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		int deleteInterval = _commerceWishListConfiguration.deleteInterval();

		Date createDate = new Date(
			System.currentTimeMillis() -
				java.util.concurrent.TimeUnit.MINUTES.toMillis(deleteInterval));

		_commerceWishListLocalService.deleteCommerceWishLists(
			UserConstants.USER_ID_DEFAULT, createDate);
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private CommerceWishListConfiguration _commerceWishListConfiguration;

	@Reference
	private CommerceWishListLocalService _commerceWishListLocalService;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}