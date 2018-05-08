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

package com.liferay.apio.architect.response.control;

import aQute.bnd.annotation.ProviderType;

import java.util.function.Predicate;

/**
 * Defines the embedded context selected by clients. An instance of this
 * interface is handed to {@code javax.ws.rs.ext.MessageBodyWriter} to decide
 * which resources to embed.
 *
 * <p>
 * Instances of this interface act as the predicate that can be used to test if
 * a relation must be embedded
 * </p>
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@ProviderType
public interface Embedded extends Predicate<String> {
}