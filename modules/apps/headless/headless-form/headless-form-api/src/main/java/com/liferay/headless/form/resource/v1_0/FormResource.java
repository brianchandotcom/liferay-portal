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

package com.liferay.headless.form.resource.v1_0;

import com.liferay.headless.form.dto.v1_0.Form;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.annotation.Generated;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-form/v1.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface FormResource {

	public Page<Form> getContentSpaceFormsPage(
				Long contentSpaceId,Pagination pagination)
			throws Exception;
	public Form getForm(
				Long formId)
			throws Exception;
	public Form postFormEvaluateContext(
				Long formId,Form form)
			throws Exception;
	public Form getFormFetchLatestDraft(
				Long formId)
			throws Exception;
	public Form postFormUploadFile(
				Long formId,Form form)
			throws Exception;

	public void setContextCompany(Company contextCompany);

}