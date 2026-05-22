/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.guardrail.util;

import com.google.cloud.modelarmor.v1.ModelArmorClient;
import com.google.cloud.modelarmor.v1.ModelArmorSettings;

import java.io.IOException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author João Victor Alves
 */
public class ModelArmorClientUtil {

	public static void closeAll(long companyId) {
		ModelArmorClient modelArmorClient = _modelArmorClients.remove(
			companyId);

		if (modelArmorClient != null) {
			modelArmorClient.close();
		}
	}

	public static ModelArmorClient getModelArmorClient(
			long companyId, String location)
		throws IOException {

		ModelArmorClient modelArmorClient = _modelArmorClients.get(companyId);

		if (modelArmorClient != null) {
			return modelArmorClient;
		}

		synchronized (_modelArmorClients) {
			modelArmorClient = _modelArmorClients.get(companyId);

			if (modelArmorClient != null) {
				return modelArmorClient;
			}

			modelArmorClient = ModelArmorClient.create(
				ModelArmorSettings.newBuilder(
				).setEndpoint(
					"modelarmor." + location + ".rep.googleapis.com:443"
				).build());

			_modelArmorClients.put(companyId, modelArmorClient);

			return modelArmorClient;
		}
	}

	private static final Map<Long, ModelArmorClient> _modelArmorClients =
		new ConcurrentHashMap<>();

}