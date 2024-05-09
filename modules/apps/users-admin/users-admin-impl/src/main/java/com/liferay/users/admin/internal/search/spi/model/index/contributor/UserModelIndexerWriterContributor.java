/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Luan Maoski
 */
public class UserModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<User> {

	public UserModelIndexerWriterContributor(
		IndexerDocumentBuilder indexerDocumentBuilder,
		IndexWriterHelper indexWriterHelper,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory,
		UserLocalService userLocalService) {

		_indexerDocumentBuilder = indexerDocumentBuilder;
		_indexWriterHelper = indexWriterHelper;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
		_userLocalService = userLocalService;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(User user) -> {
				if (!user.isGuestUser()) {
					batchIndexingActionable.addDocuments(
						modelIndexerWriterDocumentHelper.getDocument(user));
				}
			});
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_userLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(User user) {
		return user.getCompanyId();
	}

	@Override
	public void modelIndexed(User user) {
		Contact contact = user.fetchContact();

		if (contact == null) {
			return;
		}

		try {
			_indexWriterHelper.updateDocument(
				user.getCompanyId(),
				_indexerDocumentBuilder.getDocument(contact));
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;
	private final IndexerDocumentBuilder _indexerDocumentBuilder;
	private final IndexWriterHelper _indexWriterHelper;
	private final UserLocalService _userLocalService;

}