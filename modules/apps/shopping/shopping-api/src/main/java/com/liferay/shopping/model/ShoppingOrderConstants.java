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

package com.liferay.shopping.model;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingOrderConstants {

	public static final String STATUS_CHECKOUT = "LIFERAY_STATUS_CHECKOUT";

	public static final String STATUS_COMPLETED = "Completed";

	public static final String STATUS_DENIED = "Denied";

	public static final String STATUS_LATEST = "LIFERAY_STATUS_LATEST";

	public static final String STATUS_PENDING = "Pending";

	public static final String STATUS_REFUNDED = "Refunded";

	public static final String[] STATUSES = {
		"checkout", "completed", "denied", "pending", "refunded"
	};

}