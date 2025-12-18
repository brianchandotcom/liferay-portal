/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.search.spi.model.index.contributor;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderTable;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBMessageTable;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentHelper;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ReindexCacheThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = ModelDocumentContributor.class
)
public class DLFileEntryRelatedEntryModelDocumentContributor
	implements ModelDocumentContributor<DLFileEntry> {

	@Override
	public void contribute(Document document, DLFileEntry dlFileEntry) {
		if (!dlFileEntry.isInHiddenFolder()) {
			return;
		}

		long[] mbMessageValues = _getMBMessageValues(dlFileEntry.getFolderId());

		if (mbMessageValues != null) {
			document.addKeyword(Field.CATEGORY_ID, mbMessageValues[0]);
			document.addKeyword("discussion", false);
			document.addKeyword("threadId", mbMessageValues[1]);
		}

		DocumentHelper documentHelper = new DocumentHelper(document);

		documentHelper.setAttachmentOwnerKey(
			_portal.getClassNameId(dlFileEntry.getClassName()),
			dlFileEntry.getClassPK());

		document.addKeyword(Field.RELATED_ENTRY, true);
	}

	private long[] _getMBMessageValues(long dlFolderId) {
		Map<Long, long[]> mbMessageValuesMap =
			ReindexCacheThreadLocal.getGlobalReindexCache(
				() -> -1,
				DLFileEntryRelatedEntryModelDocumentContributor.class.getName(),
				count -> {
					DSLQuery dslQuery = DSLQueryFactoryUtil.select(
						DLFolderTable.INSTANCE.folderId,
						MBMessageTable.INSTANCE.categoryId,
						MBMessageTable.INSTANCE.threadId
					).from(
						DLFolderTable.INSTANCE
					).innerJoinON(
						MBMessageTable.INSTANCE,
						DLFolderTable.INSTANCE.name.eq(
							DSLFunctionFactoryUtil.castText(
								MBMessageTable.INSTANCE.messageId))
					);

					List<Object[]> valuesList = _dlFolderLocalService.dslQuery(
						dslQuery, false);

					if (valuesList.isEmpty()) {
						return Collections.emptyMap();
					}

					Map<Long, long[]> localMBMessageValuesMap = new HashMap<>();

					for (Object[] values : valuesList) {
						localMBMessageValuesMap.put(
							(Long)values[0],
							new long[] {(long)values[1], (long)values[2]});
					}

					return localMBMessageValuesMap;
				});

		if (mbMessageValuesMap == null) {
			DLFolder dlFolder = _dlFolderLocalService.fetchDLFolder(dlFolderId);

			if (dlFolder == null) {
				return null;
			}

			long messageId = GetterUtil.getLong(dlFolder.getName());

			if (messageId == 0) {
				return null;
			}

			MBMessage mbMessage = _mbMessageLocalService.fetchMBMessage(
				messageId);

			if (mbMessage == null) {
				return null;
			}

			return new long[] {
				mbMessage.getCategoryId(), mbMessage.getThreadId()
			};
		}

		return mbMessageValuesMap.get(dlFolderId);
	}

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private Portal _portal;

}