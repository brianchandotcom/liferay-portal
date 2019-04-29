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

package com.liferay.headless.form.internal.resource.v1_0;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.form.dto.v1_0.FormDocument;
import com.liferay.headless.form.internal.dto.v1_0.util.FormDocumentUtil;
import com.liferay.headless.form.resource.v1_0.FormDocumentResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 * @author Victor Oliveira
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/form-document.properties",
	scope = ServiceScope.PROTOTYPE, service = FormDocumentResource.class
)
public class FormDocumentResourceImpl extends BaseFormDocumentResourceImpl {

	@Override
	public void deleteFormDocument(Long formDocumentId) throws Exception {
		_dlAppService.deleteFileEntry(formDocumentId);
	}

	@Override
	public FormDocument getFormDocument(Long formDocumentId) throws Exception {
		return FormDocumentUtil.toFormDocument(
			_dlurlHelper, _dlAppService.getFileEntry(formDocumentId));
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlurlHelper;

}