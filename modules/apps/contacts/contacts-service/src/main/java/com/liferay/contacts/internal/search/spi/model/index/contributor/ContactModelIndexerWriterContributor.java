/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
	immediate = true,
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