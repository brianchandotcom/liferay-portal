/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

/**
 * @author Geyson Silva
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class JoinableProjectDisplay {

	public JoinableProjectDisplay(
		long groupId, String name, boolean requested) {

		_groupId = groupId;
		_name = name;
		_requested = requested;
	}

	private final long _groupId;
	private final String _name;
	private final boolean _requested;

}