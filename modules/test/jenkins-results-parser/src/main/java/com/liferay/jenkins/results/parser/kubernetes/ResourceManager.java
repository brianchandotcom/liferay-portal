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

package com.liferay.jenkins.results.parser.kubernetes;

import io.kubernetes.client.ApiException;
import io.kubernetes.client.models.V1ObjectMeta;
import io.kubernetes.client.models.V1Pod;

/**
 * @author Kenji Heigel
 */
public class ResourceManager {

	public static V1Pod createPod(V1Pod podConfiguration) throws ApiException {
		return createPod(podConfiguration, "default");
	}

	public static V1Pod createPod(V1Pod podConfiguration, String namespace)
		throws ApiException {

		try {
			return _liferayKubernetesApi.core.createNamespacedPod(
				namespace, podConfiguration, null);
		}
		catch (ApiException ae) {
			String message = ae.getMessage();

			if (message.equals("Conflict")) {
				V1ObjectMeta meta = podConfiguration.getMetadata();

				System.out.println(
					"Unable to create new pod with name '" + meta.getName() +
						"' as it already exists in namespace '" + namespace +
							"'");

				return podConfiguration;
			}

			throw ae;
		}
	}

	private static final LiferayKubernetesApi _liferayKubernetesApi =
		LiferayKubernetesApi.getInstance();

}