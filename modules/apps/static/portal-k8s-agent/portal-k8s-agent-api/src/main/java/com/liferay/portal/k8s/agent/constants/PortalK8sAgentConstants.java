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

package com.liferay.portal.k8s.agent.constants;

/**
 * @author Raymond Augé
 */
public class PortalK8sAgentConstants {

	public static final String AGENT_NAME = "Kubernetes Agent";

	public static final String CONTEXT_ANNOTATION =
		"cloud.liferay.com/context-data";

	public static final String FILE_EXT = ".config";

	public static final String FILE_JSON_EXT = ".config.json";

	public static final String K8S_CONFIG_KEY = ".k8s.config.key";

	public static final String K8S_CONFIG_RESOURCE_VERSION =
		".k8s.config.resource.version";

	public static final String K8S_CONFIG_UID = ".k8s.config.uid";

	public static final String K8S_PROPERTY_KEY = "k8s.";

	public static final String K8S_SERVICE_ID =
		"k8s.cloud.liferay.com.serviceId";

}