/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.external;

/**
 * Represents the external repository file version. Implementers of external
 * repositories must provide an implementation of this class to make the bridge
 * between Liferay Portal and external repository domains. All data returned by
 * these implementations is in native repository format.
 *
 * @author Iván Zaera
 * @author Sergio González
 */
public interface ExtRepositoryFileVersion extends ExtRepositoryModel {

	/**
	 * Returns the comments associated with the commit of this file version.
	 *
	 * @return the comments associated with the commit of this file version
	 */
	public String getChangeLog();

	/**
	 * Returns the MIME type of the file version, or <code>null</code> if the
	 * MIME type is not available in the external repository. If the MIME type
	 * is unavailable, Liferay Portal guesses the MIME type (usually by looking
	 * at the extension).
	 *
	 * @return the MIME type of the file version, or <code>null</code> if the
	 *         MIME type is not available in the external repository
	 */
	public String getMimeType();

	/**
	 * Returns the ext repository version's name (e.g. <code>1.0</code>).
	 *
	 * @return the ext repository version's name
	 */
	public String getVersion();

}