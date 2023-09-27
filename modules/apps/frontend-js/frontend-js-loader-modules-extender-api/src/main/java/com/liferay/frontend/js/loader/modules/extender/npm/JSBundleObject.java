/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.npm;

/**
 * Provides an object related to the NPM description model that can be directly
 * referenced by its unique ID. A {@link JSBundle} contains {@link
 * JSBundleObject}s.
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