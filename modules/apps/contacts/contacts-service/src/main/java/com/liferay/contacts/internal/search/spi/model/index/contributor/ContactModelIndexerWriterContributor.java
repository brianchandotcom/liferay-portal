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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lucas Marques de Paula
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.Contact",
	service = ModelIndexerWriterContributor.class
)
public class ContactModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<Contact> {

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
		return dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				contactLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(Contact contact) {
		return contact.getCompanyId();
	}

	@Reference
	protected ContactLocalService contactLocalService;

	@Reference
	protected DynamicQueryBatchIndexingActionableFactory
		dynamicQueryBatchIndexingActionableFactory;

}