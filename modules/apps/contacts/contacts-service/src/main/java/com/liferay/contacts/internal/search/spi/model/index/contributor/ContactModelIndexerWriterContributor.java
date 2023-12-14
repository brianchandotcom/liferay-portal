/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.contacts.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.service.ContactLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

import java.util.function.Consumer;

/**
 * @author Lucas Marques de Paula
 */
public class ContactModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<Contact> {

	public ContactModelIndexerWriterContributor(
		ContactLocalService contactLocalService,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_contactLocalService = contactLocalService;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		final BatchIndexingActionable batchIndexingActionable,
		final ModelIndexerWriterDocumentHelper
			modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			new Consumer<Contact>() {

				@Override
				public void accept(Contact contact) {
					batchIndexingActionable.addDocuments(
						modelIndexerWriterDocumentHelper.getDocument(contact));
				}

			});
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_contactLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(Contact contact) {
		return contact.getCompanyId();
	}

	private final ContactLocalService _contactLocalService;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}