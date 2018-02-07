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

package com.liferay.apio.architect.writer.alias;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.Optional;
import java.util.function.BiFunction;

/**
 * @author Alejandro Hernández
 */
public interface SingleModelFunction extends
	BiFunction<Object, Class<? extends Identifier>, Optional<SingleModel>> {
}