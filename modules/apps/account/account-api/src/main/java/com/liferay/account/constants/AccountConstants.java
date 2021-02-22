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

package com.liferay.account.constants;

/**
 * @author Pei-Jung Lan
 */
public class AccountConstants {

	public static final long ACCOUNT_ENTRY_ID_ANY = -1;

	public static final long ACCOUNT_ENTRY_ID_DEFAULT = 0;

	public static final long ACCOUNT_ENTRY_ID_GUEST = -1;

	public static final String ACCOUNT_ENTRY_TYPE_BUSINESS = "business";

	public static final String ACCOUNT_ENTRY_TYPE_GUEST = "guest";

	public static final String ACCOUNT_ENTRY_TYPE_PERSON = "person";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #ACCOUNT_ENTRY_TYPE_PERSON}
	 */
	@Deprecated
	public static final String ACCOUNT_ENTRY_TYPE_PERSONAL = "person";

	public static final String[] ACCOUNT_ENTRY_TYPES = {
		ACCOUNT_ENTRY_TYPE_BUSINESS, ACCOUNT_ENTRY_TYPE_GUEST,
		ACCOUNT_ENTRY_TYPE_PERSON
	};

	public static final String ACCOUNT_GROUP_NAME_GUEST = "Guest";

	public static final String ACCOUNT_GROUP_TYPE_DYNAMIC = "dynamic";

	public static final String ACCOUNT_GROUP_TYPE_GUEST = "guest";

	public static final String ACCOUNT_GROUP_TYPE_STATIC = "static";

	public static final String[] ACCOUNT_GROUP_TYPES = {
		ACCOUNT_GROUP_TYPE_STATIC, ACCOUNT_GROUP_TYPE_DYNAMIC
	};

	public static final long PARENT_ACCOUNT_ENTRY_ID_DEFAULT = 0;

	public static final String RESOURCE_NAME = "com.liferay.account";

}