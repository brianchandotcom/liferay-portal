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

package com.liferay.portal.repository.registry;

import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.event.RepositoryEventTrigger;
import com.liferay.portal.kernel.repository.registry.RepositoryCreator;

import java.util.Map;
import java.util.Set;

/**
 * @author Adolfo Pérez
 */
public interface RepositoryDefinition {

	public Set<Class<? extends Capability>> getExportedCapabilities();

	public RepositoryCreator getRepositoryCreator();

	public RepositoryEventTrigger getRepositoryEventTrigger();

	public Map<Class<? extends Capability>, Capability>
		getSupportedCapabilities();

}