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

package com.liferay.portal.search.elasticsearch7.internal.test.util.microcontainer;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author André de Oliveira
 */
public interface Microcontainer {

	public void deploy(Object... components);

	public void start();

	public <T> void wire(
		Class<T> clazz, BiConsumer<T, Map>... referencePropsBiConsumers);

	public <T> void wire(Class<T> clazz, Consumer<T>... referenceConsumers);

}