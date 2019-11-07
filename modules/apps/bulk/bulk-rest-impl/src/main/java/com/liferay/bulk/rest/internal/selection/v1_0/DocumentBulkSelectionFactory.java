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

package com.liferay.bulk.rest.internal.selection.v1_0;

import com.liferay.bulk.rest.dto.v1_0.DocumentBulkSelection;
import com.liferay.bulk.rest.dto.v1_0.SelectionScope;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.bulk.selection.BulkSelectionFactoryRegistry;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(service = DocumentBulkSelectionFactory.class)
public class DocumentBulkSelectionFactory {

	public BulkSelection<?> create(
		DocumentBulkSelection documentBulkSelection) {

		BulkSelectionFactory<Object> bulkSelectionFactory =
			_bulkSelectionFactoryRegistry.getBulkSelectionFactory(
				DLFileEntry.class.getName());

		return bulkSelectionFactory.create(
			_getParameterMap(
				documentBulkSelection.getDocumentIds(),
				documentBulkSelection.getSelectionScope()));
	}

	private Map<String, String[]> _getParameterMap(
		String[] rowIdsFileEntry, SelectionScope selectionScope) {

		if (selectionScope.getRepositoryId() == 0) {
			return Collections.singletonMap("rowIdsFileEntry", rowIdsFileEntry);
		}

		return HashMapBuilder.put(
			"folderId",
			new String[] {String.valueOf(selectionScope.getFolderId())}
		).put(
			"repositoryId",
			new String[] {String.valueOf(selectionScope.getRepositoryId())}
		).put(
			"rowIdsFileEntry", rowIdsFileEntry
		).put(
			"selectAll",
			new String[] {Boolean.toString(selectionScope.getSelectAll())}
		).build();
	}

	@Reference
	private BulkSelectionFactoryRegistry _bulkSelectionFactoryRegistry;

}