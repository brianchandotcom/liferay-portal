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

package com.liferay.frontend.js.loader.modules.extender.internal.resolution;

import java.util.Collection;
import java.util.Map;

/**
 * Unifies config generator and NPMRegistry modules into a single entity to be
 * used in resolutions.
 * @author Rodolfo Roza Miranda
 * @review
 */
public interface ModuleDescriptor {

	public Collection<String> getDependencies();

	public Map<String, String> getMappings();

	public String getName();

	public String getPath();

}