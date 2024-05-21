/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileShortcutService;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.headless.delivery.dto.v1_0.DocumentShortcut;
import com.liferay.headless.delivery.resource.v1_0.DocumentShortcutResource;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileShortcut;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/document-shortcut.properties",
	scope = ServiceScope.PROTOTYPE, service = DocumentShortcutResource.class
)
public class DocumentShortcutResourceImpl
	extends BaseDocumentShortcutResourceImpl {

	@Override
	public Page<DocumentShortcut> getAssetLibraryDocumentShortcutsPage(
			Long assetLibraryId, Pagination pagination)
		throws Exception {

		return getSiteDocumentShortcutsPage(assetLibraryId, pagination);
	}

	@Override
	public DocumentShortcut getDocumentShortcut(Long documentShortcutId)
		throws Exception {

		return _toDocumentShortcut(
			_dlAppService.getFileShortcut(documentShortcutId));
	}

	@Override
	public Page<DocumentShortcut> getSiteDocumentShortcutsPage(
			Long siteId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_dlFileShortcutService.getGroupFileShortcuts(
					siteId, pagination.getStartPosition(),
					pagination.getEndPosition()),
				dlFileShortcut -> _toDocumentShortcut(
					new LiferayFileShortcut(dlFileShortcut))),
			pagination,
			_dlFileShortcutService.getGroupFileShortcutsCount(siteId));
	}

	@Override
	public DocumentShortcut postAssetLibraryDocumentShortcut(
			Long assetLibraryId, DocumentShortcut documentShortcut)
		throws Exception {

		return postSiteDocumentShortcut(assetLibraryId, documentShortcut);
	}

	@Override
	public DocumentShortcut postSiteDocumentShortcut(
			Long siteId, DocumentShortcut documentShortcut)
		throws Exception {

		Long documentFolderId = documentShortcut.getFolderId();

		if (documentFolderId == null) {
			documentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		return _toDocumentShortcut(
			_dlAppService.addFileShortcut(
				siteId, documentFolderId,
				documentShortcut.getTargetDocumentId(),
				_createServiceContext(documentShortcut, siteId)));
	}

	private ServiceContext _createServiceContext(
		DocumentShortcut documentShortcut, long groupId) {

		return ServiceContextBuilder.create(
			groupId, contextHttpServletRequest,
			documentShortcut.getViewableByAsString()
		).build();
	}

	private DocumentShortcut _toDocumentShortcut(FileShortcut fileShortcut)
		throws Exception {

		return _documentShortcutDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				HashMapBuilder.put(
					"get",
					addAction(
						ActionKeys.VIEW, fileShortcut.getFileShortcutId(),
						"getDocumentShortcut", fileShortcut.getUserId(),
						DLFileShortcut.class.getName(),
						fileShortcut.getGroupId())
				).build(),
				_dtoConverterRegistry, fileShortcut.getFileShortcutId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFileShortcutService _dlFileShortcutService;

	@Reference(
		target = "(component.name=com.liferay.headless.delivery.internal.dto.v1_0.converter.DocumentShortcutDTOConverter)"
	)
	private DTOConverter<DLFileShortcut, DocumentShortcut>
		_documentShortcutDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

}