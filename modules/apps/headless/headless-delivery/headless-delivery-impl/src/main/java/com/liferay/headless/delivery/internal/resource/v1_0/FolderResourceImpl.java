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

package com.liferay.headless.document.library.internal.resource.v1_0;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.headless.document.library.internal.dto.v1_0.converter.FolderDTOConverter;
import com.liferay.headless.document.library.internal.odata.entity.v1_0.FolderEntityModel;
import com.liferay.headless.document.library.resource.v1_0.FolderResource;
import com.liferay.headless.web.experience.dto.v1_0.converter.DefaultDTOConverterContext;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Optional;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/folder.properties",
	scope = ServiceScope.PROTOTYPE, service = FolderResource.class
)
public class FolderResourceImpl
	extends BaseFolderResourceImpl implements EntityModelResource {

	@Override
	public void deleteFolder(Long folderId) throws Exception {
		_dlAppService.deleteFolder(folderId);
	}

	@Override
	public Page<Folder> getContentSpaceFoldersPage(
			Long contentSpaceId, Boolean flatten, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		Long folderId = null;

		if (!GetterUtil.getBoolean(flatten)) {
			folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		return _getFoldersPage(
			contentSpaceId, search, filter, folderId, pagination, sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Folder getFolder(Long folderId) throws Exception {
		return _toFolder(_dlAppService.getFolder(folderId));
	}

	@Override
	public Page<Folder> getFolderFoldersPage(
			Long folderId, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		Folder parentFolder = _toFolder(_dlAppService.getFolder(folderId));

		return _getFoldersPage(
			parentFolder.getContentSpaceId(), search, filter,
			parentFolder.getId(), pagination, sorts);
	}

	@Override
	public Folder patchFolder(Long folderId, Folder folder) throws Exception {
		com.liferay.portal.kernel.repository.model.Folder existingFolder =
			_dlAppService.getFolder(folderId);

		return _updateFolder(
			folderId,
			Optional.ofNullable(
				folder.getName()
			).orElse(
				existingFolder.getName()
			),
			Optional.ofNullable(
				folder.getDescription()
			).orElse(
				existingFolder.getDescription()
			));
	}

	@Override
	public Folder postContentSpaceFolder(Long contentSpaceId, Folder folder)
		throws Exception {

		return _addFolder(contentSpaceId, 0L, folder);
	}

	@Override
	public Folder postFolderFolder(Long folderId, Folder folder)
		throws Exception {

		Folder parentFolder = _toFolder(_dlAppService.getFolder(folderId));

		return _addFolder(
			parentFolder.getContentSpaceId(), parentFolder.getId(), folder);
	}

	@Override
	public Folder putFolder(Long folderId, Folder folder) throws Exception {
		return _updateFolder(
			folderId, folder.getName(), folder.getDescription());
	}

	private Folder _addFolder(
			Long contentSpaceId, Long parentFolderId, Folder folder)
		throws Exception {

		return _toFolder(
			_dlAppService.addFolder(
				contentSpaceId, parentFolderId, folder.getName(),
				folder.getDescription(),
				ServiceContextUtil.createServiceContext(
					contentSpaceId, folder.getViewableByAsString())));
	}

	private Page<Folder> _getFoldersPage(
			Long contentSpaceId, String search, Filter filter,
			Long parentFolderId, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> {
				if (parentFolderId != null) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					booleanFilter.add(
						new TermFilter(
							Field.FOLDER_ID, String.valueOf(parentFolderId)),
						BooleanClauseOccur.MUST);
				}
			},
			filter, DLFolder.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {contentSpaceId});
			},
			document -> _toFolder(
				_dlAppService.getFolder(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	private Folder _toFolder(
			com.liferay.portal.kernel.repository.model.Folder folder)
		throws Exception {

		return _folderDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.getPreferredLocale(),
				folder.getFolderId()));
	}

	private Folder _updateFolder(Long folderId, String name, String description)
		throws Exception {

		return _toFolder(
			_dlAppService.updateFolder(
				folderId, name, description, new ServiceContext()));
	}

	private static final EntityModel _entityModel = new FolderEntityModel();

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private FolderDTOConverter _folderDTOConverter;

}