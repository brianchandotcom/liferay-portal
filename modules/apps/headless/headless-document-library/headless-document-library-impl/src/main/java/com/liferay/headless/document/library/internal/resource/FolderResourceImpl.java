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

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.headless.document.library.dto.Folder;
import com.liferay.headless.document.library.dto.FolderCollection;
import com.liferay.headless.document.library.resource.FolderResource;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.vulcan.context.Pagination;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
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
	scope = ServiceScope.PROTOTYPE, service = FolderResource.class
)
@Generated("")
public class FolderResourceImpl implements FolderResource {

	@Override
	public FolderCollection<Folder> getFolderCollection(
			Pagination pagination, String size)
		throws Exception {

		Company company = _companyService.getCompanyByWebId(
			PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));

		Group group = company.getGroup();

		List<com.liferay.portal.kernel.repository.model.Folder> folders =
			_dlAppService.getFolders(
				group.getGroupId(), 0, pagination.getStartPosition(),
				pagination.getEndPosition(), null);

		Stream<com.liferay.portal.kernel.repository.model.Folder> stream =
			folders.stream();

		List<Folder> folders1 = stream.map(
			folder -> {
				Folder folder1 = new Folder();

				folder1.setId(folder.getFolderId());

				return folder1;
			}
		).collect(
			Collectors.toList()
		);

		int count = _dlAppService.getFoldersCount(group.getGroupId(), 0);

		return new FolderCollection(folders1, count);
	}

	@Reference
	private CompanyService _companyService;

	@Reference
	private DLAppService _dlAppService;

}