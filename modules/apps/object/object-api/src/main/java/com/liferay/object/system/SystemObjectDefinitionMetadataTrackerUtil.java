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

package com.liferay.object.system;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Paulo Albuquerque
 */
public class SystemObjectDefinitionMetadataTrackerUtil {

	public static SystemObjectDefinitionMetadata
		getSystemObjectDefinitionMetadata(String name) {

		SystemObjectDefinitionMetadataTracker
			systemObjectDefinitionMetadataTracker =
				_serviceTracker.getService();

		return systemObjectDefinitionMetadataTracker.
			getSystemObjectDefinitionMetadata(name);
	}

	private static final ServiceTracker
		<SystemObjectDefinitionMetadataTracker,
		 SystemObjectDefinitionMetadataTracker> _serviceTracker =
			ServiceTrackerFactory.open(
				FrameworkUtil.getBundle(
					SystemObjectDefinitionMetadataTracker.class),
				SystemObjectDefinitionMetadataTracker.class);

}