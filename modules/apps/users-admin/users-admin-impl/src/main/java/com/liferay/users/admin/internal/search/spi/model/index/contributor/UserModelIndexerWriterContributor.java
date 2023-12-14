/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;
import com.liferay.users.admin.internal.search.ContactBatchReindexer;

/**
 * @author Luan Maoski
 */
public class UserModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<User> {

	public UserModelIndexerWriterContributor(
		ContactBatchReindexer contactBatchReindexer,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory,
		UserLocalService userLocalService) {

		_contactBatchReindexer = contactBatchReindexer;
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
		_contactBatchReindexer.reindex(user.getUserId(), user.getCompanyId());
	}

	private final ContactBatchReindexer _contactBatchReindexer;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;
	private final UserLocalService _userLocalService;

}