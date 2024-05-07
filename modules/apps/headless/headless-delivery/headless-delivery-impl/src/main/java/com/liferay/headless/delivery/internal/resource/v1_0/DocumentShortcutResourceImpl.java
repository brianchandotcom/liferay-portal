/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.headless.delivery.dto.v1_0.Document;
import com.liferay.headless.delivery.dto.v1_0.DocumentShortcut;
import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.resource.v1_0.DocumentShortcutResource;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.HashMap;

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
	public DocumentShortcut getDocumentShortcut(Long documentShortcutId)
		throws Exception {

		FileShortcut fileShortcut = _dlAppService.getFileShortcut(
			documentShortcutId);

		FileEntry fileEntry = _dlAppService.getFileEntry(
			fileShortcut.getToFileEntryId());

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), new HashMap<>(),
				_dtoConverterRegistry, fileShortcut.getFileShortcutId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser);

		return new DocumentShortcut() {
			{
				setCreator(
					() -> CreatorUtil.toCreator(
						defaultDTOConverterContext, _portal,
						_userLocalService.fetchUser(fileShortcut.getUserId())));
				setDateCreated(fileShortcut::getCreateDate);
				setDateModified(fileShortcut::getModifiedDate);
				setFolderId(fileShortcut::getFolderId);
				setReferencedDocument(() -> _toDocument(fileEntry));
				setSiteId(fileShortcut::getGroupId);
			}
		};
	}

	private Document _toDocument(FileEntry fileEntry) throws Exception {
		return _documentDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), new HashMap<>(),
				_dtoConverterRegistry, fileEntry.getFileEntryId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference(
		target = "(component.name=com.liferay.headless.delivery.internal.dto.v1_0.converter.DocumentDTOConverter)"
	)
	private DTOConverter<DLFileEntry, Document> _documentDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}