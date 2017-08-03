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

package com.liferay.lcs.util;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.util.portlet.PortletProps;

/**
 * @author Igor Beslic
 * @author Marko Cikos
 */
public class PortletPropsValues {

	public static final String CACHE_METRICS_HIBERNATE_OBJECT_NAME =
		PortletProps.get(
			PortletPropsKeys.CACHE_METRICS_HIBERNATE_OBJECT_NAME,
			new Filter(ServerDetector.getServerId()));

	public static final String CACHE_METRICS_MULTI_VM_OBJECT_NAME =
		PortletProps.get(
			PortletPropsKeys.CACHE_METRICS_MULTI_VM_OBJECT_NAME,
			new Filter(ServerDetector.getServerId()));

	public static final String CACHE_METRICS_SINGLE_VM_OBJECT_NAME =
		PortletProps.get(
			PortletPropsKeys.CACHE_METRICS_SINGLE_VM_OBJECT_NAME,
			new Filter(ServerDetector.getServerId()));

	public static final String COMMAND_SCHEDULE_DEFAULT_INTERVAL =
		PortletProps.get(PortletPropsKeys.COMMAND_SCHEDULE_DEFAULT_INTERVAL);

	public static final String COMMAND_SCHEDULE_DELAY_MAX = PortletProps.get(
		PortletPropsKeys.COMMAND_SCHEDULE_DELAY_MAX);

	public static final String COMMUNICATION_HANDSHAKE_REPLY_READS =
		PortletProps.get(PortletPropsKeys.COMMUNICATION_HANDSHAKE_REPLY_READS);

	public static final String COMMUNICATION_HANDSHAKE_WAIT_TIME =
		PortletProps.get(PortletPropsKeys.COMMUNICATION_HANDSHAKE_WAIT_TIME);

	public static final String COMMUNICATION_HEARTBEAT_INTERVAL =
		PortletProps.get(PortletPropsKeys.COMMUNICATION_HEARTBEAT_INTERVAL);

	public static final long COMMUNICATION_LCS_GATEWAY_UNAVAILABLE_WAIT_TIME =
		GetterUtil.getLong(
			PortletProps.get(
				PortletPropsKeys.
					COMMUNICATION_LCS_GATEWAY_UNAVAILABLE_WAIT_TIME));

	public static final String DIGITAL_SIGNATURE_ALGORITHM_PROVIDER =
		PortletProps.get(PortletPropsKeys.DIGITAL_SIGNATURE_ALGORITHM_PROVIDER);

	public static final String DIGITAL_SIGNATURE_KEY_NAME = PortletProps.get(
		PortletPropsKeys.DIGITAL_SIGNATURE_KEY_NAME);

	public static final String DIGITAL_SIGNATURE_KEY_STORE_PATH =
		PortletProps.get(PortletPropsKeys.DIGITAL_SIGNATURE_KEY_STORE_PATH);

	public static final String DIGITAL_SIGNATURE_KEY_STORE_TYPE =
		PortletProps.get(PortletPropsKeys.DIGITAL_SIGNATURE_KEY_STORE_TYPE);

	public static final String DIGITAL_SIGNATURE_SIGNING_ALGORITHM =
		PortletProps.get(PortletPropsKeys.DIGITAL_SIGNATURE_SIGNING_ALGORITHM);

	public static final String KEY_GENERATOR_KEY_ALIAS = PortletProps.get(
		PortletPropsKeys.KEY_GENERATOR_KEY_ALIAS);

	public static final String KEY_GENERATOR_KEY_STORE_PASWORD =
		PortletProps.get(PortletPropsKeys.KEY_GENERATOR_KEY_STORE_PASWORD);

	public static final String KEY_GENERATOR_KEY_STORE_PATH = PortletProps.get(
		PortletPropsKeys.KEY_GENERATOR_KEY_STORE_PATH);

	public static final String KEY_GENERATOR_KEY_STORE_TYPE = PortletProps.get(
		PortletPropsKeys.KEY_GENERATOR_KEY_STORE_TYPE);

	public static final String LCS_CLIENT_VERSION = PortletProps.get(
		PortletPropsKeys.LCS_CLIENT_VERSION);

	public static final String LRDCOM_LCS_CLIENT_DOWNLOAD_URL =
		PortletProps.get(PortletPropsKeys.LRDCOM_LCS_CLIENT_DOWNLOAD_URL);

	public static final String LRDCOM_LCS_PRODUCT_PAGE_URL = PortletProps.get(
		PortletPropsKeys.LRDCOM_LCS_PRODUCT_PAGE_URL);

	public static final String LRDCOM_LCS_USER_DOCUMENTATION_URL =
		PortletProps.get(PortletPropsKeys.LRDCOM_LCS_USER_DOCUMENTATION_URL);

	public static final String LRDCOM_SALES_EMAIL_ADDRESS = PortletProps.get(
		PortletPropsKeys.LRDCOM_SALES_EMAIL_ADDRESS);

	public static final String LRDCOM_SUPPORT_URL = PortletProps.get(
		PortletPropsKeys.LRDCOM_SUPPORT_URL);

	public static final String METRICS_LCS_SERVICE_ENABLED = PortletProps.get(
		PortletPropsKeys.METRICS_LCS_SERVICE_ENABLED);

	public static final String OSB_LCS_GATEWAY_WEB_HOST_NAME = PortletProps.get(
		PortletPropsKeys.OSB_LCS_GATEWAY_WEB_HOST_NAME);

	public static final String OSB_LCS_GATEWAY_WEB_HOST_PORT = PortletProps.get(
		PortletPropsKeys.OSB_LCS_GATEWAY_WEB_HOST_PORT);

	public static final String OSB_LCS_GATEWAY_WEB_KEY_STORE_PATH =
		PortletProps.get(PortletPropsKeys.OSB_LCS_GATEWAY_WEB_KEY_STORE_PATH);

	public static final String OSB_LCS_GATEWAY_WEB_KEY_STORE_TYPE =
		PortletProps.get(PortletPropsKeys.OSB_LCS_GATEWAY_WEB_KEY_STORE_TYPE);

	public static final String OSB_LCS_GATEWAY_WEB_PROTOCOL = PortletProps.get(
		PortletPropsKeys.OSB_LCS_GATEWAY_WEB_PROTOCOL);

	public static final String OSB_LCS_GATEWAY_WEB_SECURE_API_TOKEN =
		PortletProps.get(PortletPropsKeys.OSB_LCS_GATEWAY_WEB_SECURE_API_TOKEN);

	public static final String OSB_LCS_PORTLET_HOST_NAME = PortletProps.get(
		PortletPropsKeys.OSB_LCS_PORTLET_HOST_NAME);

	public static final int OSB_LCS_PORTLET_HOST_PORT = GetterUtil.getInteger(
		PortletProps.get(PortletPropsKeys.OSB_LCS_PORTLET_HOST_PORT));

	public static final String OSB_LCS_PORTLET_KEY_STORE_PATH =
		PortletProps.get(PortletPropsKeys.OSB_LCS_PORTLET_KEY_STORE_PATH);

	public static final String OSB_LCS_PORTLET_KEY_STORE_TYPE =
		PortletProps.get(PortletPropsKeys.OSB_LCS_PORTLET_KEY_STORE_TYPE);

	public static final String OSB_LCS_PORTLET_LAYOUT_LCS_CLUSTER_ENTRY =
		PortletProps.get(
			PortletPropsKeys.OSB_LCS_PORTLET_LAYOUT_LCS_CLUSTER_ENTRY);

	public static final String OSB_LCS_PORTLET_LAYOUT_LCS_CLUSTER_NODE =
		PortletProps.get(
			PortletPropsKeys.OSB_LCS_PORTLET_LAYOUT_LCS_CLUSTER_NODE);

	public static final String OSB_LCS_PORTLET_LAYOUT_LCS_PROJECT =
		PortletProps.get(PortletPropsKeys.OSB_LCS_PORTLET_LAYOUT_LCS_PROJECT);

	public static final String OSB_LCS_PORTLET_OAUTH_ACCESS_TOKEN_URI =
		PortletProps.get(
			PortletPropsKeys.OSB_LCS_PORTLET_OAUTH_ACCESS_TOKEN_URI);

	public static final String OSB_LCS_PORTLET_OAUTH_AUTHORIZE_URI =
		PortletProps.get(PortletPropsKeys.OSB_LCS_PORTLET_OAUTH_AUTHORIZE_URI);

	public static final String OSB_LCS_PORTLET_OAUTH_CONSUMER_KEY =
		PortletProps.get(PortletPropsKeys.OSB_LCS_PORTLET_OAUTH_CONSUMER_KEY);

	public static final String OSB_LCS_PORTLET_OAUTH_CONSUMER_SECRET =
		PortletProps.get(
			PortletPropsKeys.OSB_LCS_PORTLET_OAUTH_CONSUMER_SECRET);

	public static final String OSB_LCS_PORTLET_OAUTH_REQUEST_TOKEN_URI =
		PortletProps.get(
			PortletPropsKeys.OSB_LCS_PORTLET_OAUTH_REQUEST_TOKEN_URI);

	public static final String OSB_LCS_PORTLET_PROTOCOL = PortletProps.get(
		PortletPropsKeys.OSB_LCS_PORTLET_PROTOCOL);

	public static final String OSB_LCS_PORTLET_UPLOAD_EXCEPTIONS_URI =
		PortletProps.get(
			PortletPropsKeys.OSB_LCS_PORTLET_UPLOAD_EXCEPTIONS_URI);

	public static final String PORTAL_PROPERTIES_BLACKLIST = PortletProps.get(
		PortletPropsKeys.PORTAL_PROPERTIES_BLACKLIST);

	public static final String PROXY_AUTH_TYPE = PortletProps.get(
		PortletPropsKeys.PROXY_AUTH_TYPE);

	public static final String PROXY_DOMAIN = PortletProps.get(
		PortletPropsKeys.PROXY_DOMAIN);

	public static final String PROXY_HOST_LOGIN = PortletProps.get(
		PortletPropsKeys.PROXY_HOST_LOGIN);

	public static final String PROXY_HOST_NAME = PortletProps.get(
		PortletPropsKeys.PROXY_HOST_NAME);

	public static final String PROXY_HOST_PASSWORD = PortletProps.get(
		PortletPropsKeys.PROXY_HOST_PASSWORD);

	public static final int PROXY_HOST_PORT = GetterUtil.getInteger(
		PortletProps.get(PortletPropsKeys.PROXY_HOST_PORT));

	public static final String PROXY_WORKSTATION = PortletProps.get(
		PortletPropsKeys.PROXY_WORKSTATION);

	public static final int SCHEDULED_TASK_PAGE_SIZE = GetterUtil.getInteger(
		PortletProps.get(PortletPropsKeys.SCHEDULED_TASK_PAGE_SIZE));

	public static final long SCHEDULED_TASK_PAUSE_INTERVAL = GetterUtil.getLong(
		PortletProps.get(PortletPropsKeys.SCHEDULED_TASK_PAUSE_INTERVAL));

}