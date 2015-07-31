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

package com.liferay.portal.kernel.lifecycle;

/**
 * Marker interface used to notify to the OSGi container when different
 * services of the core are ready
 *
 * @author Miguel Ángel Pastor Olivar
 */
public interface ServiceLifecycle {

	public String DATABASE_SERVICE_INITIALIZED =
		"(service.lifecycle=database.service.initialized)";

	public String PORTAL_CONTEXT_INITIALIZED =
		"(service.lifecycle=portal.context.initialized)";

	public String SPRING_CONTEXT_INITIALIZED =
		"(service.lifecycle=spring.context.initialized)";

}