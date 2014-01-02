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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
public class EventsProcessorUtil {

	public static void process(String key, String[] classes)
		throws ActionException {

		_instance.process(key, classes, new LifecycleEvent());
	}

	public static void process(
			String key, String[] classes, LifecycleEvent lifecycleEvent)
		throws ActionException {

		_instance.process(key, classes, lifecycleEvent);
	}

	public static void process(
			String key, String[] classes, HttpServletRequest request,
			HttpServletResponse response)
		throws ActionException {

		_instance.process(key, classes, new LifecycleEvent(request, response));
	}

	public static void process(
			String key, String[] classes, HttpSession session)
		throws ActionException {

		_instance.process(key, classes, new LifecycleEvent(session));
	}

	public static void process(String key, String[] classes, String[] ids)
		throws ActionException {

		_instance.process(key, classes, new LifecycleEvent(ids));
	}

	public static void processEvent(
			LifecycleAction lifecycleAction, LifecycleEvent lifecycleEvent)
		throws ActionException {

		_instance.processEvent(lifecycleAction, lifecycleEvent);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.service.registry.ServiceRegistry}
	 */
	public static void registerEvent(String key, Object event) {
		_instance.registerEvent(key, event);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.service.registry.ServiceRegistry}
	 */
	public static void unregisterEvent(String key, Object event) {
		_instance.unregisterEvent(key, event);
	}

	private static EventsProcessor _instance = new EventsProcessorImpl();

}