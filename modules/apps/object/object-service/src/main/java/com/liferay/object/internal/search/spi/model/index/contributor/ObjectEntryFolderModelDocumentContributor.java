/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.index.contributor;

import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "indexer.class.name=com.liferay.object.model.ObjectEntryFolder",
	service = ModelDocumentContributor.class
)
public class ObjectEntryFolderModelDocumentContributor
	implements ModelDocumentContributor<ObjectEntryFolder> {

	@Override
	public void contribute(
		Document document, ObjectEntryFolder objectEntryFolder) {

		document.addKeyword(
			Field.FOLDER_ID, objectEntryFolder.getObjectEntryFolderId());
		document.addText(Field.NAME, objectEntryFolder.getName());
		document.addLocalizedKeyword(
			"localized_label", objectEntryFolder.getLabelMap(), true, true);

		String[] parts = StringUtil.split(
			objectEntryFolder.getTreePath(), CharPool.SLASH);

		document.addKeyword(Field.TREE_PATH, parts);
		document.addKeyword("cms_section", _getCMSSection(parts));
	}

	private String _getCMSSection(String[] parts) {
		if (parts.length <= 1) {
			return "none";
		}

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.fetchObjectEntryFolder(
				GetterUtil.getLong(parts[1]));

		if (objectEntryFolder == null) {
			return "none";
		}

		String externalReferenceCode =
			objectEntryFolder.getExternalReferenceCode();

		if (externalReferenceCode.equals("L_CONTENT")) {
			return "content";
		}

		if (externalReferenceCode.equals("L_FILES")) {
			return "files";
		}

		return "none";
	}

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

}