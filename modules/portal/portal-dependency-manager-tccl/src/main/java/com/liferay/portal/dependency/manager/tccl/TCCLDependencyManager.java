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

package com.liferay.portal.dependency.manager.tccl;

import org.apache.felix.dm.DependencyManager;
import org.apache.felix.dm.ServiceDependency;
import org.apache.felix.dm.impl.Logger;

import org.osgi.framework.BundleContext;

/**
 * @author Carlos Sierra Andrés
 */
public class TCCLDependencyManager extends DependencyManager {

	public TCCLDependencyManager(BundleContext bundleContext) {
		super(bundleContext);
	}

	public ServiceDependency createTCCLServiceDependency() {
		return new ServiceReferenceTCCLServiceDependency(
			getBundleContext(), _logger);
	}

	private final Logger _logger = new Logger(getBundleContext());

}