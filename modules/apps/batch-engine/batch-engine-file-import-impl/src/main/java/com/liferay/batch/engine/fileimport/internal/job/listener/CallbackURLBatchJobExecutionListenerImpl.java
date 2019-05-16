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

package com.liferay.batch.engine.fileimport.internal.job.listener;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.batch.engine.core.BatchJobExecutionListener;
import com.liferay.batch.engine.fileimport.constants.BatchFileImportConstants;
import com.liferay.batch.engine.model.BatchJobExecution;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ivica Cardic
 */
@Component(
	name = "CallbackURLBatchJobExecutionListener",
	service = BatchJobExecutionListener.class
)
public class CallbackURLBatchJobExecutionListenerImpl
	implements BatchJobExecutionListener {

	@Override
	public void afterJob(BatchJobExecution batchJobExecution) {
		try {
			_call(batchJobExecution);
		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	@Override
	public void beforeJob(BatchJobExecution batchJobExecution) {
	}

	private void _call(BatchJobExecution batchJobExecution) throws Exception {
		UnicodeProperties jobSettingsProperties =
			batchJobExecution.getJobSettingsProperties();

		if (!jobSettingsProperties.containsKey(
				BatchFileImportConstants.CALLBACK_URL)) {

			return;
		}

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
			Map<Long, String> map = new HashMap<>();

			map.put(
				batchJobExecution.getBatchJobInstanceId(),
				batchJobExecution.getStatus());

			StringEntity requestEntity = new StringEntity(
				_OBJECT_MAPPER.writeValueAsString(map),
				ContentType.APPLICATION_JSON);

			HttpPost postMethod = new HttpPost(
				jobSettingsProperties.get(
					BatchFileImportConstants.CALLBACK_URL));

			postMethod.setEntity(requestEntity);

			httpClient.execute(postMethod);
		}
	}

	private static final ObjectMapper _OBJECT_MAPPER = new ObjectMapper();

	private static final Log _log = LogFactoryUtil.getLog(
		CallbackURLBatchJobExecutionListenerImpl.class);

}