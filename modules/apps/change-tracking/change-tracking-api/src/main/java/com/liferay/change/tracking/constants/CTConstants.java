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

package com.liferay.change.tracking.constants;

/**
 * @author Daniel Kocsis
 */
public interface CTConstants {

	public static final int CT_CHANGE_TYPE_ADDITION = 0;

	public static final int CT_CHANGE_TYPE_DELETION = 1;

	public static final int CT_CHANGE_TYPE_MODIFICATION = 2;

	public static final long CT_COLLECTION_ID_PRODUCTION = 0;

	public static final String RESOURCE_NAME = "com.liferay.change.tracking";

	public static final String TYPE_AFTER = "after";

	public static final String TYPE_BEFORE = "before";

	public static final String TYPE_LATEST = "latest";

}