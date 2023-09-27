/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.external;

/**
 * Provides methods to locate an external repository file version. An external
 * repository file's version descriptor consists of an {@link
 * ExtRepositoryFileEntry} key and the version name belonging to that file
 * entry.
 *
 * @author Iván Zaera
 * @author Sergio González
 */
public class ExtRepositoryFileVersionDescriptor {

	/**
	 * Creates an external repository file version descriptor with the
	 * repository file entry key and version name.
	 *
	 * @param extRepositoryFileEntryKey the repository file entry key
	 * @param version the repository file entry's version name
	 */
	public ExtRepositoryFileVersionDescriptor(
		String extRepositoryFileEntryKey, String version) {

		_extRepositoryFileEntryKey = extRepositoryFileEntryKey;
		_version = version;
	}

	/**
	 * Returns the external repository file entry key.
	 *
	 * @return the external repository file entry key
	 */
	public String getExtRepositoryFileEntryKey() {
		return _extRepositoryFileEntryKey;
	}

	/**
	 * Returns the external repository version name.
	 *
	 * @return the external repository version name
	 */
	public String getVersion() {
		return _version;
	}

	private final String _extRepositoryFileEntryKey;
	private final String _version;

}