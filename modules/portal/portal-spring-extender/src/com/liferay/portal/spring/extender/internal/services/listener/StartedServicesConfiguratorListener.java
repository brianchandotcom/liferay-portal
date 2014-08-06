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

package com.liferay.portal.spring.extender.internal.services.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author Miguel Pastor
 */
public class StartedServicesConfiguratorListener
	extends ServicesConfiguratorListener
	implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(
		ContextRefreshedEvent contextRefreshedEvent) {

		ApplicationContext applicationContext =
			contextRefreshedEvent.getApplicationContext();

		doInit(applicationContext);
	}

}