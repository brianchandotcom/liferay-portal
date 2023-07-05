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

package com.liferay.portal.vulcan.internal.batch.engine.resource;

import com.liferay.headless.batch.engine.resource.v1_0.ExportTaskResource;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineExportTaskResource;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * @author Carlos Correa
 */
public class VulcanBatchEngineExportTaskResourceImpl
	implements VulcanBatchEngineExportTaskResource {

	public VulcanBatchEngineExportTaskResourceImpl(
		ExportTaskResource exportTaskResource) {

		_exportTaskResource = exportTaskResource;
	}

	@Override
	public Object postExportTask(
			String name, String callbackURL, String contentType,
			String fieldNames)
		throws Exception {

		_exportTaskResource.setContextAcceptLanguage(_contextAcceptLanguage);
		_exportTaskResource.setContextCompany(_contextCompany);
		_exportTaskResource.setContextHttpServletRequest(
			_contextHttpServletRequest);
		_exportTaskResource.setContextUriInfo(_contextUriInfo);
		_exportTaskResource.setContextUser(_contextUser);
		_exportTaskResource.setGroupLocalService(_groupLocalService);

		return _exportTaskResource.postExportTask(
			name, contentType, callbackURL,
			_getQueryParameterValue("externalReferenceCode"), fieldNames,
			_getTaskItemDelegateName());
	}

	@Override
	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		_contextAcceptLanguage = contextAcceptLanguage;
	}

	@Override
	public void setContextCompany(Company contextCompany) {
		_contextCompany = contextCompany;
	}

	@Override
	public void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest) {

		_contextHttpServletRequest = contextHttpServletRequest;
	}

	@Override
	public void setContextUriInfo(UriInfo contextUriInfo) {
		_contextUriInfo = contextUriInfo;
	}

	@Override
	public void setContextUser(User contextUser) {
		_contextUser = contextUser;
	}

	@Override
	public void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Override
	public void setTaskItemDelegateName(String taskItemDelegateName) {
		_taskItemDelegateName = taskItemDelegateName;
	}

	private String _getQueryParameterValue(String queryParameterName) {
		MultivaluedMap<String, String> queryParameters =
			_contextUriInfo.getQueryParameters();

		return queryParameters.getFirst(queryParameterName);
	}

	private String _getTaskItemDelegateName() {
		if (_taskItemDelegateName == null) {
			return _getQueryParameterValue("taskItemDelegateName");
		}

		return _taskItemDelegateName;
	}

	private AcceptLanguage _contextAcceptLanguage;
	private Company _contextCompany;
	private HttpServletRequest _contextHttpServletRequest;
	private UriInfo _contextUriInfo;
	private User _contextUser;
	private final ExportTaskResource _exportTaskResource;
	private GroupLocalService _groupLocalService;
	private String _taskItemDelegateName;

}