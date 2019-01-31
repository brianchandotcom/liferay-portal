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

package com.liferay.headless.form.internal.resource;

import com.liferay.headless.form.dto.Form;
import com.liferay.headless.form.dto.FormRecord;
import com.liferay.headless.form.dto.FormStructure;
import com.liferay.headless.form.resource.FormResource;
import com.liferay.portal.vulcan.context.AcceptLanguage;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import java.util.Collections;

import javax.annotation.Generated;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=headless-form-application.rest)",
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true", "api.version=1.0.0"
	},
	scope = ServiceScope.PROTOTYPE, service = FormResource.class
)
@Generated("")
public class FormResourceImpl implements FormResource {

	@Override
	public Page<FormStructure> getContentSpaceContentStructuresPage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<Form> getContentSpaceFormPage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Form getForm(Integer id) throws Exception {
		return new Form();
	}

	@Override
	public Form getFormFetchLatestDraft(Integer id) throws Exception {
		return new Form();
	}

	@Override
	public Page<FormRecord> getFormFormRecordPage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Form postFormEvaluateContext(
			Integer id, AcceptLanguage acceptLanguage)
		throws Exception {

		return new Form();
	}

	@Override
	public Form postFormUploadFile(Integer id) throws Exception {
		return new Form();
	}

}