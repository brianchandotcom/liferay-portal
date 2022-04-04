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

import com.liferay.headless.batch.engine.resource.v1_0.ImportTaskResource;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(service = VulcanBatchEngineImportTaskResource.class)
public class VulcanBatchEngineImportTaskResourceImpl
	implements VulcanBatchEngineImportTaskResource {

	@Override
	public Object deleteImportTask(
			String name, String callbackURL, Object object)
		throws Exception {

		_initializeContext();

		return _importTaskResource.deleteImportTask(
			name, callbackURL, _getExternalReferenceCode(),
			_getImportStrategy(), _getTaskItemDelegateName(), object);
	}

	@Override
	public Object postImportTask(
			String name, String callbackURL, String fields, Object object)
		throws Exception {

		_initializeContext();

		return _importTaskResource.postImportTask(
			name, callbackURL, _getExternalReferenceCode(), fields,
			_getImportStrategy(), _getTaskItemDelegateName(), object);
	}

	@Override
	public Object putImportTask(String name, String callbackURL, Object object)
		throws Exception {

		_initializeContext();

		return _importTaskResource.putImportTask(
			name, callbackURL, _getExternalReferenceCode(),
			_getImportStrategy(), _getTaskItemDelegateName(), object);
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

	private String _getExternalReferenceCode() {
		return _getQueryParameterValue("externalReferenceCode");
	}

	private String _getImportStrategy() {
		return _getQueryParameterValue("importStrategy");
	}

	private String _getQueryParameterValue(String queryParameterName) {
		MultivaluedMap<String, String> queryParameters =
			_contextUriInfo.getQueryParameters();

		for (Map.Entry<String, List<String>> entry :
				queryParameters.entrySet()) {

			if (!Objects.equals(entry.getKey(), queryParameterName)) {
				continue;
			}

			List<String> values = entry.getValue();

			if (values.isEmpty()) {
				continue;
			}

			return values.get(0);
		}

		return null;
	}

	private String _getTaskItemDelegateName() {
		return _getQueryParameterValue("taskItemDelegateName");
	}

	private void _initializeContext() {
		_importTaskResource.setContextAcceptLanguage(_contextAcceptLanguage);
		_importTaskResource.setContextCompany(_contextCompany);
		_importTaskResource.setContextHttpServletRequest(
			_contextHttpServletRequest);
		_importTaskResource.setContextUriInfo(_contextUriInfo);
		_importTaskResource.setContextUser(_contextUser);
	}

	private AcceptLanguage _contextAcceptLanguage;
	private Company _contextCompany;
	private HttpServletRequest _contextHttpServletRequest;
	private UriInfo _contextUriInfo;
	private User _contextUser;

	@Reference
	private ImportTaskResource _importTaskResource;

}