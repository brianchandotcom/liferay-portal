/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.action.trigger;

/**
 * @author Marco Leo
 */
public class ObjectActionTrigger {

	public ObjectActionTrigger(String key) {
		_key = key;
	}

	public String getKey() {
		return _key;
	}

	private final String _key;

}