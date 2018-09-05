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

package com.liferay.asset.list.constants;

/**
 * @author Jürgen Kappler
 */
public class AssetListEntryTypeConstants {

	public static final int TYPE_DYNAMIC = 0;

	public static final String TYPE_DYNAMIC_LABEL = "dynamic";

	public static final int TYPE_MANUAL = 1;

	public static final String TYPE_MANUAL_LABEL = "manual";

	public static String getTypeLabel(int type) {
		if (type == TYPE_DYNAMIC) {
			return TYPE_DYNAMIC_LABEL;
		}
		else if (type == TYPE_MANUAL) {
			return TYPE_MANUAL_LABEL;
		}

		return null;
	}

}