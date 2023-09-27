/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.external;

import java.util.Date;

/**
 * Represents the external repository model, which describes a folder, file, or
 * file version in the external repository. All data returned by this class'
 * implementation is in native repository format.
 *
 * @author Iván Zaera
 * @author Sergio González
 */
public interface ExtRepositoryModel {

	/**
	 * Returns the external repository model's creation date.
	 *
	 * @return the external repository model's creation date
	 */
	public Date getCreateDate();

	/**
	 * Returns the external repository model's primary key.
	 *
	 * @return the external repository model's primary key
	 */
	public String getExtRepositoryModelKey();

	/**
	 * Returns the external repository model's owner. The returned user
	 * identifier is converted from the native repository format to the Liferay
	 * Portal format by calling the {@link
	 * ExtRepository#getLiferayLogin(String)} method.
	 *
	 * @return the external repository model's owner
	 */
	public String getOwner();

	/**
	 * Returns the external repository's model size in bytes.
	 *
	 * @return the external repository's model size in bytes
	 */
	public long getSize();

}