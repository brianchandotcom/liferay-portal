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

package com.liferay.vldap.server.internal.util;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletPropsValues {

	public static final String BIND_SASL_HOSTNAME = GetterUtil.getString(
		PortletProps.get(PortletPropsKeys.BIND_SASL_HOSTNAME));

	public static final String[] EMAIL_ADDRESSES_WHITELIST =
		PortletProps.getArray(PortletPropsKeys.EMAIL_ADDRESSES_WHITELIST);

	public static final String[] HOSTS_ALLOWED = PortletProps.getArray(
		PortletPropsKeys.HOSTS_ALLOWED);

	public static final int LDAP_BIND_PORT = GetterUtil.getInteger(
		PortletProps.get(PortletPropsKeys.LDAP_BIND_PORT));

	public static final int LDAPS_BIND_PORT = GetterUtil.getInteger(
		PortletProps.get(PortletPropsKeys.LDAPS_BIND_PORT));

	public static final String POSIX_GROUP_ID = GetterUtil.getString(
		PortletProps.get(PortletPropsKeys.POSIX_GROUP_ID));

	public static final String[] SAMBA_DOMAIN_NAMES = PortletProps.getArray(
		PortletPropsKeys.SAMBA_DOMAIN_NAMES);

	public static final String[] SAMBA_HOSTS_ALLOWED = PortletProps.getArray(
		PortletPropsKeys.SAMBA_HOSTS_ALLOWED);

	public static final int SEARCH_MAX_SIZE = GetterUtil.getInteger(
		PortletProps.get(PortletPropsKeys.SEARCH_MAX_SIZE));

	public static final int SEARCH_MAX_TIME = GetterUtil.getInteger(
		PortletProps.get(PortletPropsKeys.SEARCH_MAX_TIME));

	public static final String SSL_KEYSTORE_FILE_NAME = GetterUtil.getString(
		PortletProps.get(PortletPropsKeys.SSL_KEYSTORE_FILE_NAME));

	public static final char[] SSL_KEYSTORE_PASSWORD = GetterUtil.getString(
		PortletProps.get(PortletPropsKeys.SSL_KEYSTORE_PASSWORD)).toCharArray();

	public static final String SSL_PROTOCOL = GetterUtil.getString(
		PortletProps.get(PortletPropsKeys.SSL_PROTOCOL));

}