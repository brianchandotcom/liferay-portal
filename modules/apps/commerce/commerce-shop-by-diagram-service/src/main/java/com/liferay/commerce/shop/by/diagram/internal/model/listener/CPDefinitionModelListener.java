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

package com.liferay.commerce.shop.by.diagram.internal.model.listener;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mahmoud Azzam
 */
@Component(immediate = true, service = ModelListener.class)
public class CPDefinitionModelListener extends BaseModelListener<CPDefinition> {

	@Override
	public void onAfterUpdate(
		CPDefinition originalCPDefinition, CPDefinition cpDefinition) {

		Indexer<CSDiagramEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CSDiagramEntry.class);

		try {
			indexer.reindex(
				_csDiagramEntryLocalService.
					getCPDefinitionRelatedCSDiagramEntries(
						cpDefinition.getCPDefinitionId()));
		}
		catch (SearchException searchException) {
			throw new ModelListenerException(searchException);
		}
	}

	@Reference
	private CSDiagramEntryLocalService _csDiagramEntryLocalService;

}