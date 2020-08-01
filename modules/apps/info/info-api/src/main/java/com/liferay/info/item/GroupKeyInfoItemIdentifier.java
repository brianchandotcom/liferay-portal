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

package com.liferay.info.item;

/**
 * @author Jorge Ferrer
 */
public class GroupKeyInfoItemIdentifier extends InfoItemIdentifier {

	public GroupKeyInfoItemIdentifier(long groupId, String key) {
		_groupId = groupId;
		_key = key;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getKey() {
		return _key;
	}

	private final long _groupId;
	private final String _key;

}