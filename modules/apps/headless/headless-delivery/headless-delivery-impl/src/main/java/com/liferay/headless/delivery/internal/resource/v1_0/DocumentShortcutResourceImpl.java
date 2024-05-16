/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.headless.delivery.dto.v1_0.DocumentShortcut;
import com.liferay.headless.delivery.resource.v1_0.DocumentShortcutResource;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.List;

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
			Long assetLibraryId)
		throws Exception {

		return getSiteDocumentShortcutsPage(assetLibraryId);
	}

	@Override
	public DocumentShortcut getDocumentShortcut(Long documentShortcutId)
		throws Exception {

		return _toDocumentShortcut(
			_dlAppService.getFileShortcut(documentShortcutId));
	}

	@Override
	public Page<DocumentShortcut> getSiteDocumentShortcutsPage(Long siteId)
		throws Exception {

		List<FileShortcut> fileShortcuts = _dlAppService.getGroupFileShortcuts(
			siteId);

		return Page.of(
			transform(
				fileShortcuts,
				fileShortcut -> _toDocumentShortcut(fileShortcut)));
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

	@Reference(
		target = "(component.name=com.liferay.headless.delivery.internal.dto.v1_0.converter.DocumentShortcutDTOConverter)"
	)
	private DTOConverter<DLFileShortcut, DocumentShortcut>
		_documentShortcutDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

}