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

package com.liferay.apio.architect.router;

import aQute.bnd.annotation.ConsumerType;

import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.ItemRoutes.Builder;

/**
 * Instances of this interface represent the mapping between HTTP requests for
 * an item resource and the functions that perform that requests.
 *
 * <p>
 * You can add the different supported routes for the item resource via the
 * {@link #itemRoutes(ItemRoutes.Builder)} method.
 * </p>
 *
 * <p>
 * The union of an instance of this interface with a {@link
 * com.liferay.apio.architect.representor.Representable} creates a complete
 * resource that will behave as its own API.
 * </p>
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 * @param  <S> the model identifier's type ({@link Long}, {@link String}, etc.)
 * @see    ItemRoutes.Builder
 * @review
 */
@ConsumerType
public interface ItemRouter<T, S> {

	/**
	 * Creates the {@link ItemRoutes} supported by the item resource. Use the
	 * provided routes builder to create the {@code ItemRoutes} instance.
	 *
	 * @param builder the routes builder to use to create the {@code ItemRoutes}
	 *        instance
	 * @see   ItemRoutes.Builder
	 */
	public ItemRoutes<T> itemRoutes(Builder<T, S> builder);

}