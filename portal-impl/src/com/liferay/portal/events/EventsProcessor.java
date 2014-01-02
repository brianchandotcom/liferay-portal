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

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
public interface EventsProcessor {

	public void process(
			String key, String[] classes, LifecycleEvent lifecycleEvent)
		throws ActionException;

	public void processEvent(
			LifecycleAction lifecycleAction, LifecycleEvent lifecycleEvent)
		throws ActionException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.service.registry.ServiceRegistry}
	 */
	public void registerEvent(String key, Object event);

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             com.liferay.portal.service.registry.ServiceRegistry}
	 */
	public void unregisterEvent(String key, Object event);

}