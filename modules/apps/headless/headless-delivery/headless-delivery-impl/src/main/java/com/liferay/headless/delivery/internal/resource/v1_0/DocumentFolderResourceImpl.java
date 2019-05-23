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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.headless.delivery.dto.v1_0.DocumentFolder;
import com.liferay.headless.delivery.dto.v1_0.converter.DefaultDTOConverterContext;
import com.liferay.headless.delivery.internal.dto.v1_0.converter.DocumentFolderDTOConverter;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.DocumentFolderEntityModel;
import com.liferay.headless.delivery.resource.v1_0.DocumentFolderResource;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Map;
import java.util.Optional;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/document-folder.properties",
	scope = ServiceScope.PROTOTYPE, service = DocumentFolderResource.class
)
public class DocumentFolderResourceImpl
	extends BaseDocumentFolderResourceImpl implements EntityModelResource {

	@Override
	public void deleteDocumentFolder(Long documentFolderId) throws Exception {
		_dlAppService.deleteFolder(documentFolderId);
	}

	@Override
	public DocumentFolder getDocumentFolder(Long documentFolderId)
		throws Exception {

		return _toDocumentFolder(_dlAppService.getFolder(documentFolderId));
	}

	@Override
	public Page<DocumentFolder> getDocumentFolderDocumentFoldersPage(
			Long parentDocumentFolderId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		DocumentFolder parentDocumentFolder = _toDocumentFolder(
			_dlAppService.getFolder(parentDocumentFolderId));

		return _getDocumentFoldersPage(
			parentDocumentFolder.getSiteId(), search, filter,
			parentDocumentFolder.getId(), pagination, sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Page<DocumentFolder> getSiteDocumentFoldersPage(
			Long siteId, Boolean flatten, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		Long documentFolderId = null;

		if (!GetterUtil.getBoolean(flatten)) {
			documentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		return _getDocumentFoldersPage(
			siteId, search, filter, documentFolderId, pagination, sorts);
	}

	@Override
	public DocumentFolder patchDocumentFolder(
			Long documentFolderId, DocumentFolder documentFolder)
		throws Exception {

		Folder existingFolder = _dlAppService.getFolder(documentFolderId);

		return _updateDocumentFolder(
			documentFolderId, documentFolder.getCustomFields(),
			Optional.ofNullable(
				documentFolder.getDescription()
			).orElse(
				existingFolder.getDescription()
			),
			Optional.ofNullable(
				documentFolder.getName()
			).orElse(
				existingFolder.getName()
			));
	}

	@Override
	public DocumentFolder postDocumentFolderDocumentFolder(
			Long parentDocumentFolderId, DocumentFolder documentFolder)
		throws Exception {

		DocumentFolder parentDocumentFolder = _toDocumentFolder(
			_dlAppService.getFolder(parentDocumentFolderId));

		return _addFolder(
			parentDocumentFolder.getSiteId(), parentDocumentFolder.getId(),
			documentFolder);
	}

	@Override
	public DocumentFolder postSiteDocumentFolder(
			Long siteId, DocumentFolder documentFolder)
		throws Exception {

		return _addFolder(siteId, 0L, documentFolder);
	}

	@Override
	public DocumentFolder putDocumentFolder(
			Long documentFolderId, DocumentFolder documentFolder)
		throws Exception {

		return _updateDocumentFolder(
			documentFolderId, documentFolder.getCustomFields(),
			documentFolder.getDescription(), documentFolder.getName());
	}

	private DocumentFolder _addFolder(
			Long siteId, Long parentDocumentFolderId,
			DocumentFolder documentFolder)
		throws Exception {

		return _toDocumentFolder(
			_dlAppService.addFolder(
				siteId, parentDocumentFolderId, documentFolder.getName(),
				documentFolder.getDescription(),
				ServiceContextUtil.createServiceContext(
					DLFolder.class, contextCompany.getCompanyId(),
					documentFolder.getCustomFields(), siteId,
					contextAcceptLanguage.getPreferredLocale(),
					documentFolder.getViewableByAsString())));
	}

	private Page<DocumentFolder> _getDocumentFoldersPage(
			Long siteId, String search, Filter filter,
			Long parentDocumentFolderId, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> {
				if (parentDocumentFolderId != null) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					booleanFilter.add(
						new TermFilter(
							Field.FOLDER_ID,
							String.valueOf(parentDocumentFolderId)),
						BooleanClauseOccur.MUST);
				}
			},
			filter, DLFolder.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {siteId});
			},
			document -> _toDocumentFolder(
				_dlAppService.getFolder(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	private DocumentFolder _toDocumentFolder(Folder folder) throws Exception {
		return _documentFolderDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.getPreferredLocale(),
				folder.getFolderId()));
	}

	private DocumentFolder _updateDocumentFolder(
			Long documentFolderId, Map<String, Object> customFields,
			String description, String name)
		throws Exception {

		return _toDocumentFolder(
			_dlAppService.updateFolder(
				documentFolderId, name, description,
				ServiceContextUtil.createServiceContext(
					DLFolder.class, contextCompany.getCompanyId(), customFields,
					0, contextAcceptLanguage.getPreferredLocale(), null)));
	}

	private static final EntityModel _entityModel =
		new DocumentFolderEntityModel();

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DocumentFolderDTOConverter _documentFolderDTOConverter;

}