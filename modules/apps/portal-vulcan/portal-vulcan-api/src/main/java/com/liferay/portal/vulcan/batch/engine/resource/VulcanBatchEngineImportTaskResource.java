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

package com.liferay.portal.vulcan.batch.engine.resource;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 */
public interface VulcanBatchEngineImportTaskResource {

	public Object deleteImportTask(
			String name, String callbackURL, Object object)
		throws Exception;

	public Object postImportTask(
			String name, String callbackURL, String fields, Object object)
		throws Exception;

	public Object putImportTask(String name, String callbackURL, Object object)
		throws Exception;

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage);

	public void setContextCompany(Company contextCompany);

	public void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest);

	public void setContextUriInfo(UriInfo contextUriInfo);

	public void setContextUser(User contextUser);

}