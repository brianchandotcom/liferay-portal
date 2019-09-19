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

package com.liferay.portal.search.elasticsearch6.spi.index;

/**
 * @author     André de Oliveira
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             com.liferay.portal.search.spi.index.IndexDefinition}
 */
@Deprecated
public interface IndexDefinition {

	public static final String PROPERTY_KEY_INDEX_NAME = "index.name";

	public static final String PROPERTY_KEY_INDEX_SETTINGS_RESOURCE_NAME =
		"index.settings.resource.name";

}