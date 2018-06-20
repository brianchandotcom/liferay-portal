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

package com.liferay.asset.kernel.model;

import com.liferay.petra.string.StringPool;

/**
 * @author Jorge Ferrer
 */
public class AssetCategoryConstants {

	public static final long ALL_CLASS_NAME_ID = 0;

	public static final String ALL_CLASS_NAME_IDS_AND_CLASS_TYPE_PKS =
		ALL_CLASS_NAME_ID + StringPool.COLON +
			AssetCategoryConstants.ALL_CLASS_TYPE_PK;

	public static final long ALL_CLASS_TYPE_PK = -1;

	public static final long DEFAULT_PARENT_CATEGORY_ID = 0;

	public static final String PROPERTY_KEY_VALUE_SEPARATOR = "_KEY_VALUE_";

}