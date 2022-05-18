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

package com.liferay.portal.k8s.agent;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Raymond Augé
 */
@FunctionalInterface
public interface PortalK8sExtensionConfigMapModifier {

	/**
	 * Modify the data of an extension's service config map.
	 * <p>
	 * An update which adds data to an extension's service config map which does
	 * not exists results in the creation of the config map. An update which
	 * results in a config map which has no data results in the deletion of the
	 * config map.
	 *
	 * @param  configMapDataConsumer a consumer that receives the config map's
	 *         data, must not be {@code null}
	 * @param  serviceId the service id of the extension to be modified, must
	 *         not be {@code null}
	 * @return a {@link Result} indicating the outcome of the update
	 * @throws NullPointerException if {@code configMapDataConsumer} or {@code
	 *         serviceId} are {@code null}
	 */
	public Result modifyExtensionConfigMap(
		Consumer<Map<String, String>> configMapDataConsumer, String serviceId);

	public enum Result {

		CREATED, DELETED, UNCHANGED, UPDATED

	}

}