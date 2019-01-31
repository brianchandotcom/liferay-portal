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

package com.liferay.headless.document.library.internal.resource;

import com.liferay.headless.document.library.dto.Document;
import com.liferay.headless.document.library.dto.Folder;
import com.liferay.headless.document.library.resource.DocumentResource;
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
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=headless-document-library-application.rest)",
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true", "api.version=1.0.0"
	},
	scope = ServiceScope.PROTOTYPE, service = DocumentResource.class
)
@Generated("")
public class DocumentResourceImpl implements DocumentResource {

	@Override
	public Page<Document> getDocumentsRepositoryDocumentPage(
			Long parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<Folder> getDocumentsRepositoryFolderPage(
			Long parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<Document> getFolderDocumentPage(
			Long parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<Folder> getFolderFolderPage(
			Long parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Document postDocumentsRepositoryDocumentBatchCreate(Long parentId)
		throws Exception {

		return new Document();
	}

	@Override
	public Document postFolderDocumentBatchCreate(Long parentId)
		throws Exception {

		return new Document();
	}

}