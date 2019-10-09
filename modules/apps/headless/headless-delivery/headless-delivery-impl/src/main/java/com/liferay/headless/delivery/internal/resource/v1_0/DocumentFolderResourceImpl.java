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
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.headless.delivery.dto.v1_0.CustomField;
import com.liferay.headless.delivery.dto.v1_0.DocumentFolder;
import com.liferay.headless.delivery.dto.v1_0.converter.DefaultDTOConverterContext;
import com.liferay.headless.delivery.internal.dto.v1_0.converter.DocumentFolderDTOConverter;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.EntityFieldsUtil;
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
import com.liferay.portal.kernel.util.Portal;
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
			Long parentDocumentFolderId, Boolean flatten, String search,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		DocumentFolder parentDocumentFolder = _toDocumentFolder(
			_dlAppService.getFolder(parentDocumentFolderId));

		return _getDocumentFoldersPage(
			parentDocumentFolder.getSiteId(), flatten, search, filter,
			parentDocumentFolder.getId(), pagination, sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return new DocumentFolderEntityModel(
			EntityFieldsUtil.getEntityFields(
				_portal.getClassNameId(DLFolder.class.getName()),
				contextCompany.getCompanyId(), _expandoColumnLocalService,
				_expandoTableLocalService));
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
			siteId, flatten, search, filter, documentFolderId, pagination,
			sorts);
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

	@Override
	public void putDocumentFolderSubscribe(Long documentFolderId)
		throws Exception {

		Folder folder = _dlAppService.getFolder(documentFolderId);

		_dlAppService.subscribeFolder(
			folder.getGroupId(), folder.getFolderId());
	}

	@Override
	public void putDocumentFolderUnsubscribe(Long documentFolderId)
		throws Exception {

		Folder folder = _dlAppService.getFolder(documentFolderId);

		_dlAppService.unsubscribeFolder(
			folder.getGroupId(), folder.getFolderId());
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
					CustomFieldsUtil.toMap(
						DLFolder.class.getName(), contextCompany.getCompanyId(),
						documentFolder.getCustomFields(),
						contextAcceptLanguage.getPreferredLocale()),
					siteId, documentFolder.getViewableByAsString())));
	}

	private Page<DocumentFolder> _getDocumentFoldersPage(
			Long siteId, Boolean flatten, String search, Filter filter,
			Long parentDocumentFolderId, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> {
				if (parentDocumentFolderId != null) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					String field = Field.FOLDER_ID;

					if (GetterUtil.getBoolean(flatten)) {
						booleanFilter.add(
							new TermFilter(
								field, String.valueOf(parentDocumentFolderId)),
							BooleanClauseOccur.MUST_NOT);
						field = "treePath";
					}

					booleanFilter.add(
						new TermFilter(
							field, String.valueOf(parentDocumentFolderId)),
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
				folder.getFolderId(), contextUriInfo, contextUser));
	}

	private DocumentFolder _updateDocumentFolder(
			Long documentFolderId, CustomField[] customFields,
			String description, String name)
		throws Exception {

		return _toDocumentFolder(
			_dlAppService.updateFolder(
				documentFolderId, name, description,
				ServiceContextUtil.createServiceContext(
					CustomFieldsUtil.toMap(
						DLFolder.class.getName(), contextCompany.getCompanyId(),
						customFields,
						contextAcceptLanguage.getPreferredLocale()),
					0, null)));
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DocumentFolderDTOConverter _documentFolderDTOConverter;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private Portal _portal;

}