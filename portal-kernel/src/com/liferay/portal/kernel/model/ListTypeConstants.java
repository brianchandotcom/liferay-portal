/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	// Company

	public static final String COMPANY_ADDRESS =
		Company.class.getName() + ADDRESS;

	public static final int COMPANY_ADDRESS_DEFAULT = GetterUtil.getInteger(
		PropsUtil.get(
			PropsKeys.
				SQL_DATA_COM_LIFERAY_PORTAL_MODEL_LISTTYPE_COMPANY_ADDRESS));

	public static final String COMPANY_EMAIL_ADDRESS =
		Company.class.getName() + EMAIL_ADDRESS;

	public static final int COMPANY_EMAIL_ADDRESS_DEFAULT =
		GetterUtil.getInteger(
			PropsUtil.get(
				PropsKeys.
					SQL_DATA_COM_LIFERAY_PORTAL_MODEL_LISTTYPE_COMPANY_EMAIL_ADDRESS));

	public static final String COMPANY_PHONE = Company.class.getName() + PHONE;

	public static final String COMPANY_WEBSITE =
		Company.class.getName() + WEBSITE;

	// Address

	public static final String ADDRESS_PHONE = Address.class.getName() + PHONE;

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