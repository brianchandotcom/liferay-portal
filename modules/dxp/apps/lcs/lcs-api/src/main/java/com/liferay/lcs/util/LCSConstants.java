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

import java.util.Arrays;
import java.util.List;

/**
 * Provides constants and conversion methods for labels, status, and types
 * commonly used within the Cloud Services System.
 *
 * @author  Igor Beslic
 * @author  Ivica Cardic
 * @author  Marko Cikos
 * @version 2.1.1
 * @since   LCS 0.1
 */
public class LCSConstants {

	/**
	 * @deprecated As of 9.0.0, Moved into osb-lcs-api module class
	 *             OSBLCSConstants
	 */
	@Deprecated
	public static final int ALL_LCS_CLUSTER_OBJECTS_ID = -1;

	public static final String ALL_PORTAL_OBJECTS_NAME = "all";

	public static final String CHARSET_ISO_8859_1 = "ISO-8859-1";

	public static final int COMMAND_MESSAGE_TASK_SCHEDULE_PERIOD = 10;

	public static final String CORP_PROJECT_DEFAULT_NAME = "My LCS Project";

	public static final String HARDWARE_SETTINGS_LCS_SERVICE_ENABLED =
		"hardware-settings-lcs-service-enabled";

	/**
	 * @deprecated As of 9.0.0, Moved into osb-lcs-api module class
	 *             JSONConstants
	 */
	@Deprecated
	public static final String JSON_KEY_DATA = "data";

	/**
	 * @deprecated As of 9.0.0, Moved into osb-lcs-api module class
	 *             JSONConstants
	 */
	@Deprecated
	public static final String JSON_KEY_MESSAGE = "message";

	/**
	 * @deprecated As of 9.0.0, Moved into osb-lcs-api module class
	 *             JSONConstants
	 */
	@Deprecated
	public static final String JSON_KEY_RESULT = "result";

	/**
	 * @deprecated As of 9.0.0, Moved into osb-lcs-api module class
	 *             JSONConstants
	 */
	@Deprecated
	public static final String JSON_VALUE_FAILURE = "failure";

	/**
	 * @deprecated As of 9.0.0, Moved into osb-lcs-api module class
	 *             JSONConstants
	 */
	@Deprecated
	public static final String JSON_VALUE_SUCCESS = "success";

	public static final String JVM_SETTINGS_LCS_SERVICE_ENABLED =
		"jvm-settings-lcs-service-enabled";

	public static final String LABEL_CLUSTER = "cluster";

	public static final String LABEL_ENVIRONMENT = "environment";

	public static final String LABEL_MONITORING_ONLINE =
		"monitoring-is-available";

	public static final String LABEL_MONITORING_UNAVAILABLE =
		"monitoring-is-unavailable";

	public static final String LABEL_PATCHES_AVAILABLE = "available";

	public static final String LABEL_PATCHES_DOWNLOAD_INITIATED =
		"started-download";

	public static final String LABEL_PATCHES_DOWNLOADED = "downloaded";

	public static final String LABEL_PATCHES_DOWNLOADING = "downloading";

	public static final String LABEL_PATCHES_ERROR = "error";

	public static final String LABEL_PATCHES_INSTALLED = "installed";

	public static final String LABEL_PATCHES_UNKNOWN = "unknown";

	public static final String LABEL_PATCHING_TOOL_AVAILABLE =
		"the-patching-tool-is-available";

	public static final String LABEL_PATCHING_TOOL_UNAVAILABLE =
		"the-patching-tool-is-unavailable";

	public static final String LABEL_SERVER_OFFLINE = "the-server-is-offline";

	public static final String LABEL_SERVER_ONLINE = "the-server-is-online";

	public static final String LCS_CLUSTER_ENTRY_DEFAULT_NAME = "My Servers";

	public static final int LCS_CLUSTER_ENTRY_TYPE_CLUSTER = 0;

	public static final int LCS_CLUSTER_ENTRY_TYPE_ENVIRONMENT = 1;

	public static final int LCS_CLUSTER_NODE_CLUSTER_LINK_BROKEN = 0;

	public static final int LCS_CLUSTER_NODE_CLUSTER_LINK_HEALTHY = 1;

	public static final int METRICS_LCS_SERVICE_AVAILABLE = 1;

	public static final String METRICS_LCS_SERVICE_ENABLED =
		"metrics-lcs-service-enabled";

	public static final int METRICS_LCS_SERVICE_UNAVAILABLE = 0;

	public static final int MONITORING_AVAILABLE = 1;

	public static final int MONITORING_UNAVAILABLE = 0;

	public static final int PATCHES_AVAILABLE = 1;

	public static final int PATCHES_DOWNLOAD_INITIATED = 2;

	public static final int PATCHES_DOWNLOADED = 4;

	public static final int PATCHES_DOWNLOADING = 3;

	public static final int PATCHES_ERROR = -1;

	public static final int PATCHES_INSTALLED = 5;

	public static final int PATCHES_LCS_SERVICE_AVAILABLE = 1;

	public static final String PATCHES_LCS_SERVICE_ENABLED =
		"patches-lcs-service-enabled";

	public static final int PATCHES_LCS_SERVICE_UNAVAILABLE = 0;

	public static final String PATCHES_MD5SUM_SUFFIX = "-md5sum";

	public static final int PATCHES_UNKNOWN = 0;

	public static final int PATCHING_TOOL_AVAILABLE = 1;

	public static final int PATCHING_TOOL_UNAVAILABLE = 0;

	public static final String PORTAL_EDITION_CE = "CE";

	public static final String PORTAL_EDITION_EE = "EE";

	public static final String PORTAL_PROPERTIES_BLACKLIST =
		"portal-properties-blacklist";

	public static final int PORTAL_PROPERTIES_LCS_SERVICE_AVAILABLE = 1;

	public static final String PORTAL_PROPERTIES_LCS_SERVICE_ENABLED =
		"portal-properties-lcs-service-enabled";

	public static final int PORTAL_PROPERTIES_LCS_SERVICE_UNAVAILABLE = 0;

	public static final String[] PORTAL_PROPERTIES_SECURITY_INSENSITIVE = {
		"login.create.account.allow.custom.password",
		"portal.jaas.plain.password", "portal.jaas.strict.password"
	};

	public static final String[] PORTAL_PROPERTIES_SECURITY_SENSITIVE = {
		"amazon.secret.access.key", "auth.token.shared.secret",
		"auth.mac.shared.key", "auto.deploy.glassfish.jee.dm.passwd",
		"captcha.engine.recaptcha.key.private", "dl.store.s3.secret.key",
		"facebook.connect.app.secret", "ldap.security.credentials.",
		"mail.hook.cyrus.add.user", "mail.hook.cyrus.delete.user",
		"microsoft.translator.client.secret", "omniadmin.users",
		"tunneling.servlet.shared.secret"
	};

	public static final String[] SERVER_METRICS_SUPPORTED_SERVERS =
		{"tomcat", "weblogic"};

	public static final String SETTINGS_MODULE_ADD_ENVIRONMENT =
		"addEnvironment";

	public static final String SETTINGS_MODULE_NOTIFICATIONS = "notifications";

	public static final int SITE_NAMES_LCS_SERVICE_AVAILABLE = 1;

	public static final int SITE_NAMES_LCS_SERVICE_UNAVAILABLE = 0;

	public static final String SOURCE_SYSTEM_NAME_LCS = "LCS";

	public static final String SOURCE_SYSTEM_NAME_LRDCOM = "LRDCOM";

	public static final String SOURCE_SYSTEM_NAME_OSB = "OSB";

	public static final String SYSTEM_SETTINGS_LCS_SERVICE_ENABLED =
		"system-settings-lcs-service-enabled";

	/**
	 * Returns <code>true</code> if the application server has obtainable
	 * metrics.
	 *
	 * @param  server the application server type. To view application servers
	 *         that support metrics, see {@link
	 *         #SERVER_METRICS_SUPPORTED_SERVERS}.
	 * @return <code>true</code> if the application server has obtainable
	 *         metrics; <code>false</code> otherwise
	 * @since  LCS 0.1
	 */
	public static boolean isServerMetricsSupported(String server) {
		List<String> serverMetricsSupportedServers = Arrays.asList(
			SERVER_METRICS_SUPPORTED_SERVERS);

		if (serverMetricsSupportedServers.contains(server)) {
			return true;
		}

		return false;
	}

	/**
	 * Returns the label language key to associate with the cluster entry type.
	 *
	 * @param  lcsClusterEntryType the type of cluster entry
	 * @return the label language key to associate with the cluster entry type
	 * @since  LCS 0.1
	 */
	public static String toLCSClusterEntryTypeLabel(int lcsClusterEntryType) {
		if (lcsClusterEntryType == LCS_CLUSTER_ENTRY_TYPE_CLUSTER) {
			return LABEL_CLUSTER;
		}
		else {
			return LABEL_ENVIRONMENT;
		}
	}

	/**
	 * Returns the label language key to associate with the monitoring status.
	 *
	 * @param  monitoringStatus the monitoring status
	 * @return the label language key to associate with the monitoring status
	 * @since  LCS 0.1
	 */
	public static String toMonitoringStatusLabel(int monitoringStatus) {
		if (monitoringStatus == MONITORING_UNAVAILABLE) {
			return LABEL_MONITORING_UNAVAILABLE;
		}
		else {
			return LABEL_MONITORING_ONLINE;
		}
	}

	/**
	 * Returns the label language key to associate with the patching tool
	 * status.
	 *
	 * @param  patchingToolStatus the patching tool status
	 * @return the label language key to associate with the patching tool status
	 * @since  LCS 0.1
	 */
	public static String toPatchingToolStatusLabel(int patchingToolStatus) {
		if (patchingToolStatus == PATCHING_TOOL_UNAVAILABLE) {
			return LABEL_PATCHING_TOOL_UNAVAILABLE;
		}
		else {
			return LABEL_PATCHING_TOOL_AVAILABLE;
		}
	}

	/**
	 * Returns the label language key to associate with the patch status.
	 *
	 * @param  patchStatus the patch status
	 * @return the label language key to associate with the patch status
	 * @since  LCS 0.1
	 */
	public static String toPatchStatusLabel(int patchStatus) {
		if (patchStatus == PATCHES_AVAILABLE) {
			return LABEL_PATCHES_AVAILABLE;
		}
		else if (patchStatus == PATCHES_DOWNLOAD_INITIATED) {
			return LABEL_PATCHES_DOWNLOAD_INITIATED;
		}
		else if (patchStatus == PATCHES_DOWNLOADING) {
			return LABEL_PATCHES_DOWNLOADING;
		}
		else if (patchStatus == PATCHES_DOWNLOADED) {
			return LABEL_PATCHES_DOWNLOADED;
		}
		else if (patchStatus == PATCHES_INSTALLED) {
			return LABEL_PATCHES_INSTALLED;
		}
		else if (patchStatus == PATCHES_UNKNOWN) {
			return LABEL_PATCHES_UNKNOWN;
		}
		else {
			return LABEL_PATCHES_ERROR;
		}
	}

}