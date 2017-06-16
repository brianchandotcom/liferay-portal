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

package com.liferay.frontend.js.loader.modules.extender.npm;

/**
 * Provides an object related to the NPM description model that can be directly
 * referenced by its unique ID. A {@link JSBundle} contains
 * {@link JSBundleObject}s.
 *
 * @author Iván Zaera
 */
public interface JSBundleObject {

	/**
	 * Returns the model object's unique ID. Since these IDs are guaranteed to
	 * be unique across one portal, they can be used for referencing the object
	 * and lookups.
	 *
	 * @return the unique ID of the model object
	 */
	public String getId();

	/**
	 * Returns the model object's name. Unlike IDs, names can be duplicated and
	 * may be not unique.
	 *
	 * @return the symbolic name of the object
	 */
	public String getName();

}