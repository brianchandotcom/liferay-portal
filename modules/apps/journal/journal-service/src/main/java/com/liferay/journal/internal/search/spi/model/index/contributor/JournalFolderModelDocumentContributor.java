/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.search.spi.model.index.contributor;

import com.liferay.journal.model.JournalFolder;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.trash.TrashHelper;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "indexer.class.name=com.liferay.journal.model.JournalFolder",
	service = ModelDocumentContributor.class
)
public class JournalFolderModelDocumentContributor
	implements ModelDocumentContributor<JournalFolder> {

	@Override
	public void contribute(Document document, JournalFolder journalFolder) {
		document.addKeyword(Field.FOLDER_ID, journalFolder.getParentFolderId());
		document.addKeyword(
			Field.TREE_PATH,
			StringUtil.split(journalFolder.getTreePath(), CharPool.SLASH));

		String title = journalFolder.getName();

		if (journalFolder.isInTrash()) {
			title = _trashHelper.getOriginalTitle(title);
		}

		for (Locale locale :
				_language.getAvailableLocales(journalFolder.getGroupId())) {

			String languageId = LocaleUtil.toLanguageId(locale);

			document.addText(
				_localization.getLocalizedName(Field.DESCRIPTION, languageId),
				journalFolder.getDescription());
			document.addText(
				_localization.getLocalizedName(Field.TITLE, languageId), title);
		}
	}

	@Reference
	private Language _language;

	@Reference
	private Localization _localization;

	@Reference
	private TrashHelper _trashHelper;

}