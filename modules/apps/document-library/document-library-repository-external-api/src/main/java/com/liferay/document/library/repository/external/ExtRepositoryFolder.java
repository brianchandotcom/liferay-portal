/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.external;

/**
 * Represents the external repository folder object. Implementers of external
 * repositories must provide an implementation of this class to make the bridge
 * between Liferay Portal and external repository domains. All data returned by
 * these implementations is in native repository format.
 *
 * @author Iván Zaera
 * @author Sergio González
 */
public interface ExtRepositoryFolder extends ExtRepositoryObject {

	/**
	 * Returns the external repository folder's name.
	 *
	 * @return the external repository folder's name
	 */
	public String getName();

	/**
	 * Returns <code>true</code> if the external repository folder is a root
	 * folder.
	 *
	 * @return <code>true</code> if the external repository folder is a root
	 *         folder; <code>false</code> otherwise
	 */
	public boolean isRoot();

}