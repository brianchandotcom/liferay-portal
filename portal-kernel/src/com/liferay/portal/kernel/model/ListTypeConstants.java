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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

/**
 * @author Alexander Chow
 */
public class ListTypeConstants {

	// Common

	public static final String ADDRESS = ".address";

	public static final String EMAIL_ADDRESS = ".emailAddress";

	public static final String PHONE = ".phone";

	public static final String WEBSITE = ".website";

	// Account

	public static final String ACCOUNT_ADDRESS =
		Account.class.getName() + ADDRESS;

	public static final int ACCOUNT_ADDRESS_DEFAULT = GetterUtil.getInteger(
		PropsUtil.get(
			PropsKeys.
				SQL_DATA_COM_LIFERAY_PORTAL_MODEL_LISTTYPE_ACCOUNT_ADDRESS));

	public static final String ACCOUNT_EMAIL_ADDRESS =
		Account.class.getName() + EMAIL_ADDRESS;

	public static final int ACCOUNT_EMAIL_ADDRESS_DEFAULT =
		GetterUtil.getInteger(
			PropsUtil.get(
				PropsKeys.
					SQL_DATA_COM_LIFERAY_PORTAL_MODEL_LISTTYPE_ACCOUNT_EMAIL_ADDRESS));

	public static final String ACCOUNT_PHONE = Account.class.getName() + PHONE;

	public static final String ACCOUNT_WEBSITE =
		Account.class.getName() + WEBSITE;

	// Contact

	public static final String CONTACT_ADDRESS =
		Contact.class.getName() + ADDRESS;

	public static final String CONTACT_EMAIL_ADDRESS =
		Contact.class.getName() + EMAIL_ADDRESS;

	public static final int CONTACT_EMAIL_ADDRESS_DEFAULT =
		GetterUtil.getInteger(
			PropsUtil.get(
				PropsKeys.
					SQL_DATA_COM_LIFERAY_PORTAL_MODEL_LISTTYPE_CONTACT_EMAIL_ADDRESS));

	public static final String CONTACT_PHONE = Contact.class.getName() + PHONE;

	public static final String CONTACT_PREFIX =
		Contact.class.getName() + ".prefix";

	public static final String CONTACT_SUFFIX =
		Contact.class.getName() + ".suffix";

	public static final String CONTACT_WEBSITE =
		Contact.class.getName() + WEBSITE;

	// Organization

	public static final String ORGANIZATION_ADDRESS =
		Organization.class.getName() + ADDRESS;

	public static final String ORGANIZATION_EMAIL_ADDRESS =
		Organization.class.getName() + EMAIL_ADDRESS;

	public static final String ORGANIZATION_PHONE =
		Organization.class.getName() + PHONE;

	public static final String ORGANIZATION_SERVICE =
		Organization.class.getName() + ".service";

	public static final String ORGANIZATION_STATUS =
		Organization.class.getName() + ".status";

	public static final int ORGANIZATION_STATUS_DEFAULT = GetterUtil.getInteger(
		PropsUtil.get(
			PropsKeys.
				SQL_DATA_COM_LIFERAY_PORTAL_MODEL_LISTTYPE_ORGANIZATION_STATUS));

	public static final String ORGANIZATION_WEBSITE =
		Organization.class.getName() + WEBSITE;

}