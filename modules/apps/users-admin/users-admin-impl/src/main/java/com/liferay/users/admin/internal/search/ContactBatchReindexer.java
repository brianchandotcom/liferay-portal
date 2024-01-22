/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.internal.search;

import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.indexer.IndexerWriter;

/**
 * @author Luan Maoski
 */
public class ContactBatchReindexer {

	public ContactBatchReindexer(
		ClassNameLocalService classNameLocalService,
		IndexerDocumentBuilder indexerDocumentBuilder,
		IndexerWriter<Contact> indexerWriter) {

		_classNameLocalService = classNameLocalService;
		_indexerDocumentBuilder = indexerDocumentBuilder;
		_indexerWriter = indexerWriter;
	}

	public void reindex(long userId, long companyId) {
		BatchIndexingActionable batchIndexingActionable =
			_indexerWriter.getBatchIndexingActionable();

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				dynamicQuery.add(
					classNameIdProperty.eq(
						_classNameLocalService.getClassNameId(User.class)));

				Property classPKProperty = PropertyFactoryUtil.forName(
					"classPK");

				dynamicQuery.add(classPKProperty.eq(userId));
			});
		batchIndexingActionable.setCompanyId(companyId);
		batchIndexingActionable.setPerformActionMethod(
			(Contact contact) -> batchIndexingActionable.addDocuments(
				_indexerDocumentBuilder.getDocument(contact)));

		batchIndexingActionable.performActions();
	}

	private final ClassNameLocalService _classNameLocalService;
	private final IndexerDocumentBuilder _indexerDocumentBuilder;
	private final IndexerWriter<Contact> _indexerWriter;

}