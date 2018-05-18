/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.discount.model;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceDiscountConstants {

	public static final String LIMITATION_TYPE_LIMITED = "limited";

	public static final String LIMITATION_TYPE_LIMITED_FOR_USERS =
		"limited-for-users";

	public static final String LIMITATION_TYPE_UNLIMITED = "unlimited";

	public static final String[] LIMITATION_TYPES = {
		LIMITATION_TYPE_UNLIMITED, LIMITATION_TYPE_LIMITED,
		LIMITATION_TYPE_LIMITED_FOR_USERS
	};

	public static final String TARGET_CATEGORIES = "categories";

	public static final String TARGET_PRODUCT = "product";

	public static final String TARGET_SHIPMENT = "shipment";

	public static final String TARGET_SUBTOTAL = "subtotal";

	public static final String TARGET_TOTAL = "total";

}