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

package com.liferay.mail.reader.internal.search.spi.model.index.contributor;

import com.liferay.mail.reader.model.Folder;
import com.liferay.mail.reader.service.FolderLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Scott Lee
 * @author Peter Fellwock
 * @author Luan Maoski
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.mail.reader.model.Folder",
	service = ModelIndexerWriterContributor.class
)
public class FolderModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<Folder> {

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(Folder folder) -> batchIndexingActionable.addDocuments(
				modelIndexerWriterDocumentHelper.getDocument(folder)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				folderLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(Folder folder) {
		return folder.getCompanyId();
	}

	@Reference
	protected DynamicQueryBatchIndexingActionableFactory
		dynamicQueryBatchIndexingActionableFactory;

	@Reference
	protected FolderLocalService folderLocalService;

}