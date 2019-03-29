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

import com.google.gson.JsonSyntaxException;

import io.kubernetes.client.ApiException;
import io.kubernetes.client.models.V1DeleteOptions;
import io.kubernetes.client.models.V1ObjectMeta;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1Status;

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

	public static V1Status deletePod(V1Pod podConfiguration)
		throws ApiException {

		return deletePod(podConfiguration, "default");
	}

	public static V1Status deletePod(V1Pod podConfiguration, String namespace)
		throws ApiException {

		V1ObjectMeta meta = podConfiguration.getMetadata();

		try {
			return _liferayKubernetesApi.core.deleteNamespacedPod(
				meta.getName(), namespace, new V1DeleteOptions(), null, 60,
				true, null);
		}
		catch (ApiException ae) {
			String message = ae.getMessage();

			if (message.equals("Not Found")) {
				System.out.println(
					"Unable to delete pod with name '" + meta.getName() +
						"' as it was not found in namespace '" + namespace +
							"'");

				return null;
			}

			throw ae;
		}
		catch (JsonSyntaxException jse) {
			String message = jse.getMessage();

			if (message == null) {
				throw jse;
			}

			if (message.contains("Expected a string but was BEGIN_OBJECT")) {
				System.out.println(
					"Catching issue: " +
						"https://github.com/kubernetes-client/java/issues/86");

				return null;
			}

			throw jse;
		}
	}

	private static final LiferayKubernetesApi _liferayKubernetesApi =
		LiferayKubernetesApi.getInstance();

}