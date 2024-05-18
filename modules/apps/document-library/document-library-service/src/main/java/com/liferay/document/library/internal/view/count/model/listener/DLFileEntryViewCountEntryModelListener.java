/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.view.count.model.listener;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.index.UpdateDocumentIndexWriter;
import com.liferay.portal.search.indexer.BaseModelDocumentFactory;
import com.liferay.view.count.model.ViewCountEntry;
import com.liferay.view.count.model.listener.ViewCountEntryModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ViewCountEntryModelListener.class)
public class DLFileEntryViewCountEntryModelListener
	implements ViewCountEntryModelListener {

	@Override
	public String getModelClassName() {
		return DLFileEntry.class.getName();
	}

	@Override
	public void onAfterIncrement(ViewCountEntry viewCountEntry) {
		DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchDLFileEntry(
			viewCountEntry.getClassPK());

		if (dlFileEntry == null) {
			return;
		}

		Document document = _baseModelDocumentFactory.createDocument(
			dlFileEntry);

		document.addNumber("readCount", viewCountEntry.getViewCount());
		document.addNumber("viewCount", viewCountEntry.getViewCount());

		_updateDocumentIndexWriter.updateDocumentPartially(
			dlFileEntry.getCompanyId(), document, false);
	}

	@Reference
	private BaseModelDocumentFactory _baseModelDocumentFactory;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private UpdateDocumentIndexWriter _updateDocumentIndexWriter;

}