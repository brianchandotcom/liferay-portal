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

package com.liferay.portal.search.web.internal.util;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.Props;

import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Bryan Engler
 */
public class PropsTracker {

	public static Props getProps() {
		return _serviceTracker.getService();
	}

	private static final ServiceTracker<Props, Props> _serviceTracker =
		ServiceTrackerFactory.open(Props.class);

}