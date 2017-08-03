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

/**
 * @author Igor Beslic
 * @author Marko Cikos
 */
public interface PortletPropsKeys {

	public static final String CACHE_METRICS_HIBERNATE_OBJECT_NAME =
		"cache.metrics.hibernate.object.name";

	public static final String CACHE_METRICS_MULTI_VM_OBJECT_NAME =
		"cache.metrics.multi.vm.object.name";

	public static final String CACHE_METRICS_SINGLE_VM_OBJECT_NAME =
		"cache.metrics.single.vm.object.name";

	public static final String COMMAND_SCHEDULE_DEFAULT_INTERVAL =
		"command.schedule.default.interval";

	public static final String COMMAND_SCHEDULE_DELAY_MAX =
		"command.schedule.delay.max";

	public static final String COMMUNICATION_HANDSHAKE_REPLY_READS =
		"communication.handshake.reply.reads";

	public static final String COMMUNICATION_HANDSHAKE_WAIT_TIME =
		"communication.handshake.wait.time";

	public static final String COMMUNICATION_HEARTBEAT_INTERVAL =
		"communication.heartbeat.interval";

	public static final String COMMUNICATION_LCS_GATEWAY_UNAVAILABLE_WAIT_TIME =
		"communication.lcs.gateway.unavailable.wait.time";

	public static final String DIGITAL_SIGNATURE_ALGORITHM_PROVIDER =
		"digital.signature.algorithm.provider";

	public static final String DIGITAL_SIGNATURE_KEY_NAME =
		"digital.signature.key.name";

	public static final String DIGITAL_SIGNATURE_KEY_STORE_PATH =
		"digital.signature.key.store.path";

	public static final String DIGITAL_SIGNATURE_KEY_STORE_TYPE =
		"digital.signature.key.store.type";

	public static final String DIGITAL_SIGNATURE_SIGNING_ALGORITHM =
		"digital.signature.signing.algorithm";

	public static final String KEY_GENERATOR_KEY_ALIAS =
		"key.generator.key.alias";

	public static final String KEY_GENERATOR_KEY_STORE_PASWORD =
		"key.generator.key.store.password";

	public static final String KEY_GENERATOR_KEY_STORE_PATH =
		"key.generator.key.store.path";

	public static final String KEY_GENERATOR_KEY_STORE_TYPE =
		"key.generator.key.store.type";

	public static final String LCS_CLIENT_VERSION = "lcs.client.version";

	public static final String LRDCOM_LCS_CLIENT_DOWNLOAD_URL =
		"lrdcom.lcs.client.download.url";

	public static final String LRDCOM_LCS_PRODUCT_PAGE_URL =
		"lrdcom.lcs.product.page.url";

	public static final String LRDCOM_LCS_USER_DOCUMENTATION_URL =
		"lrdcom.lcs.user.documentation.url";

	public static final String LRDCOM_SALES_EMAIL_ADDRESS =
		"lrdcom.sales.email.address";

	public static final String LRDCOM_SUPPORT_URL = "lrdcom.support.url";

	public static final String METRICS_LCS_SERVICE_ENABLED =
		"metrics.lcs.service.enabled";

	public static final String OSB_LCS_GATEWAY_WEB_HOST_NAME =
		"osb.lcs.gateway.web.host.name";

	public static final String OSB_LCS_GATEWAY_WEB_HOST_PORT =
		"osb.lcs.gateway.web.host.port";

	public static final String OSB_LCS_GATEWAY_WEB_KEY_STORE_PATH =
		"osb.lcs.gateway.web.key.store.path";

	public static final String OSB_LCS_GATEWAY_WEB_KEY_STORE_TYPE =
		"osb.lcs.gateway.web.key.store.type";

	public static final String OSB_LCS_GATEWAY_WEB_PROTOCOL =
		"osb.lcs.gateway.web.protocol";

	public static final String OSB_LCS_GATEWAY_WEB_SECURE_API_TOKEN =
		"osb.lcs.gateway.web.secure.api.token";

	public static final String OSB_LCS_PORTLET_HOST_NAME =
		"osb.lcs.portlet.host.name";

	public static final String OSB_LCS_PORTLET_HOST_PORT =
		"osb.lcs.portlet.host.port";

	public static final String OSB_LCS_PORTLET_KEY_STORE_PATH =
		"osb.lcs.portlet.key.store.path";

	public static final String OSB_LCS_PORTLET_KEY_STORE_TYPE =
		"osb.lcs.portlet.key.store.type";

	public static final String OSB_LCS_PORTLET_LAYOUT_LCS_CLUSTER_ENTRY =
		"osb.lcs.portlet.layout.lcs.cluster.entry";

	public static final String OSB_LCS_PORTLET_LAYOUT_LCS_CLUSTER_NODE =
		"osb.lcs.portlet.layout.lcs.cluster.node";

	public static final String OSB_LCS_PORTLET_LAYOUT_LCS_PROJECT =
		"osb.lcs.portlet.layout.lcs.project";

	public static final String OSB_LCS_PORTLET_OAUTH_ACCESS_TOKEN_URI =
		"osb.lcs.portlet.oauth.access.token.uri";

	public static final String OSB_LCS_PORTLET_OAUTH_AUTHORIZE_URI =
		"osb.lcs.portlet.oauth.authorize.uri";

	public static final String OSB_LCS_PORTLET_OAUTH_CONSUMER_KEY =
		"osb.lcs.portlet.oauth.consumer.key";

	public static final String OSB_LCS_PORTLET_OAUTH_CONSUMER_SECRET =
		"osb.lcs.portlet.oauth.consumer.secret";

	public static final String OSB_LCS_PORTLET_OAUTH_REQUEST_TOKEN_URI =
		"osb.lcs.portlet.oauth.request.token.uri";

	public static final String OSB_LCS_PORTLET_PROTOCOL =
		"osb.lcs.portlet.protocol";

	public static final String OSB_LCS_PORTLET_UPLOAD_EXCEPTIONS_URI =
		"osb.lcs.portlet.upload.exceptions.uri";

	public static final String PORTAL_PROPERTIES_BLACKLIST =
		"portal.properties.blacklist";

	public static final String PROXY_AUTH_TYPE = "proxy.auth.type";

	public static final String PROXY_DOMAIN = "proxy.domain";

	public static final String PROXY_HOST_LOGIN = "proxy.host.login";

	public static final String PROXY_HOST_NAME = "proxy.host.name";

	public static final String PROXY_HOST_PASSWORD = "proxy.host.password";

	public static final String PROXY_HOST_PORT = "proxy.host.port";

	public static final String PROXY_WORKSTATION = "proxy.workstation";

	public static final String SCHEDULED_TASK_PAGE_SIZE =
		"scheduled.task.page.size";

	public static final String SCHEDULED_TASK_PAUSE_INTERVAL =
		"scheduled.task.pause.interval";

}