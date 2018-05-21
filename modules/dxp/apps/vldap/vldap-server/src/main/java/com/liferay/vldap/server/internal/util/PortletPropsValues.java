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