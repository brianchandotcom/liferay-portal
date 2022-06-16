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
public interface PortalK8sConfigMapModifier {

	public Result modifyConfigMap(
		Consumer<PortalK8sConfigMapModifier.ConfigMapModel>
			configMapModelConsumer,
		String configMapName);

	public interface ConfigMapModel {

		public Map<String, String> annotations();

		public Map<String, String> binaryData();

		public Map<String, String> data();

		public Map<String, String> labels();

	}

	public enum Result {

		CREATED, DELETED, UNCHANGED, UPDATED

	}

}