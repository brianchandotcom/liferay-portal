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
import com.liferay.headless.form.resource.FormResource;
import com.liferay.portal.vulcan.context.AcceptLanguage;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import java.util.Collections;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseFormResourceImpl implements FormResource {

	@Override
	public Page<Form> getContentSpaceFormPage(
			Long parentId, Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public Form getForm(Long id) throws Exception {
		return new Form();
	}

	@Override
	public Form getFormFetchLatestDraft(Long id) throws Exception {
		return new Form();
	}

	@Override
	public Form postFormEvaluateContext(Long id, AcceptLanguage acceptLanguage)
		throws Exception {

		return new Form();
	}

	@Override
	public Form postFormUploadFile(Long id) throws Exception {
		return new Form();
	}

}