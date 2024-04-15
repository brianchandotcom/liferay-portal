/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.defaultpermissions.web.internal.search;

/**
 * @author Stefano Motta
 */
public class PortalDefaultPermissionsSearchEntry
	implements Comparable<PortalDefaultPermissionsSearchEntry> {

	public PortalDefaultPermissionsSearchEntry(String className, String label) {
		_className = className;
		_label = label;
	}

	@Override
	public int compareTo(
		PortalDefaultPermissionsSearchEntry
			portalDefaultPermissionsSearchEntry) {

		return _label.compareToIgnoreCase(
			portalDefaultPermissionsSearchEntry.getLabel());
	}

	public String getClassName() {
		return _className;
	}

	public String getLabel() {
		return _label;
	}

	private final String _className;
	private final String _label;

}