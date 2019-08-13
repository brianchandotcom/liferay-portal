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

package com.liferay.batch.engine.rest.internal.resource;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.batch.engine.BatchContentType;
import com.liferay.batch.engine.BatchOperation;
import com.liferay.batch.engine.BatchTaskExecutor;
import com.liferay.batch.engine.BatchTaskExecutorFactory;
import com.liferay.batch.engine.ClassRegistry;
import com.liferay.batch.engine.model.BatchTask;
import com.liferay.batch.engine.service.BatchTaskLocalService;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.activation.DataHandler;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Ivica Cardic
 */
@Component(
	property = {
		"batch.size=100",
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Batch)",
		"osgi.jaxrs.resource=true"
	},
	scope = ServiceScope.PROTOTYPE, service = BatchEngineResource.class
)
@Path("/batch-engine")
public class BatchEngineResource {

	@Activate
	public void activate(Map<String, Object> properties) {
		_batchSize = GetterUtil.getLong(properties.get("batch.size"));

		if (_batchSize <= 0) {
			_batchSize = 1;
		}
	}

	@DELETE
	@Path("/{version}/{className}")
	@RequiresScope("Batch.write")
	public Response delete(
			@PathParam("className") String className,
			@PathParam("version") String version, MultipartBody multipartBody,
			@QueryParam("callbackURL") String callbackURL)
		throws Exception {

		return _process(
			multipartBody.getRootAttachment(), className, version,
			BatchOperation.DELETE, callbackURL);
	}

	@GET
	@Path("/{batchTaskId}")
	@RequiresScope("Batch.read")
	public Response getBatchExecutionStatus(
		@PathParam("batchTaskId") long batchTaskId) {

		try {
			BatchTask batchTask = _batchTaskLocalService.getBatchTask(
				batchTaskId);

			Response.ResponseBuilder responseBuilder = Response.ok();

			responseBuilder.entity(batchTask.getStatus());

			return responseBuilder.build();
		}
		catch (PortalException pe) {
			Response.ResponseBuilder responseBuilder = Response.serverError();

			responseBuilder.entity(pe);

			return responseBuilder.build();
		}
	}

	@Path("/{version}/{className}")
	@POST
	@RequiresScope("Batch.write")
	public Response post(
			@PathParam("className") String className,
			@PathParam("version") String version, MultipartBody multipartBody,
			@QueryParam("callbackURL") String callbackURL)
		throws Exception {

		return _process(
			multipartBody.getRootAttachment(), className, version,
			BatchOperation.CREATE, callbackURL);
	}

	@Path("/{version}/{className}")
	@PUT
	@RequiresScope("Batch.write")
	public Response put(
			@PathParam("className") String className,
			@PathParam("version") String version, MultipartBody multipartBody,
			@QueryParam("callbackURL") String callbackURL)
		throws Exception {

		return _process(
			multipartBody.getRootAttachment(), className, version,
			BatchOperation.UPDATE, callbackURL);
	}

	private Response _process(
			Attachment attachment, String className, String version,
			BatchOperation operation, String callbackURL)
		throws Exception {

		Class<?> clazz = _classRegistry.getClass(className);

		if (clazz == null) {
			throw new IllegalArgumentException(
				"Unknown class name : " + className);
		}

		DataHandler dataHandler = attachment.getDataHandler();

		BatchTask batchTask = _batchTaskLocalService.addBatchTask(
			className, version,
			StreamUtil.toByteArray(dataHandler.getInputStream()),
			BatchContentType.valueOf(
				StringUtil.upperCase(
					_file.getExtension(dataHandler.getName()))),
			operation, _batchSize);

		ExecutorService executorService =
			_portalExecutorManager.getPortalExecutor(
				BatchEngineResource.class.getName());

		executorService.submit(
			() -> {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setCompanyId(batchTask.getCompanyId());

				ServiceContextThreadLocal.pushServiceContext(serviceContext);

				try {
					BatchTaskExecutor batchJob = _batchJobFactory.create(clazz);

					batchJob.execute(batchTask);

					if (!Validator.isBlank(callbackURL)) {
						HttpClientBuilder httpClientBuilder =
							HttpClientBuilder.create();

						try (CloseableHttpClient httpClient =
								httpClientBuilder.build()) {

							StringEntity requestEntity = new StringEntity(
								_OBJECT_MAPPER.writeValueAsString(
									Collections.singletonMap(
										batchTask.getBatchTaskId(),
										batchTask.getStatus())),
								ContentType.APPLICATION_JSON);

							HttpPost postMethod = new HttpPost(callbackURL);

							postMethod.setEntity(requestEntity);

							httpClient.execute(postMethod);
						}
					}
				}
				catch (Exception e) {
					_log.error(e, e);
				}
				finally {
					ServiceContextThreadLocal.popServiceContext();
				}

				return null;
			});

		Response.ResponseBuilder responseBuilder = Response.accepted();

		responseBuilder.entity(
			_OBJECT_MAPPER.writeValueAsString(
				Collections.singletonMap(
					batchTask.getBatchTaskId(), dataHandler.getName())));

		return responseBuilder.build();
	}

	private static final ObjectMapper _OBJECT_MAPPER = new ObjectMapper();

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineResource.class);

	@Reference
	private BatchTaskExecutorFactory _batchJobFactory;

	private long _batchSize;

	@Reference
	private BatchTaskLocalService _batchTaskLocalService;

	@Reference
	private ClassRegistry _classRegistry;

	@Reference
	private File _file;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

}