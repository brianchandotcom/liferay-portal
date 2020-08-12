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

package com.liferay.portal.vulcan.util;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;

/**
 * @author Javier Gamarra
 */
public class GroupUtil {

	public static String getAssetLibraryKey(Group group) {
		if (_isDepot(group)) {
			return group.getGroupKey();
		}

		return null;
	}

	public static Long getSiteId(Group group) {
		if (_isDepot(group)) {
			return null;
		}

		return group.getGroupId();
	}

	private static boolean _isDepot(Group group) {
		if (GroupConstants.TYPE_DEPOT == group.getType()) {
			return true;
		}

		return false;
	}

}