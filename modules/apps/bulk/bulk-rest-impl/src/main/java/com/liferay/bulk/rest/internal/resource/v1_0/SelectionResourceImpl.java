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

package com.liferay.bulk.rest.internal.resource.v1_0;

import com.liferay.bulk.rest.dto.v1_0.DocumentBulkSelection;
import com.liferay.bulk.rest.dto.v1_0.Selection;
import com.liferay.bulk.rest.internal.selection.v1_0.DocumentBulkSelectionFactory;
import com.liferay.bulk.rest.resource.v1_0.SelectionResource;
import com.liferay.bulk.selection.BulkSelection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alejandro Tardín
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/selection.properties",
	scope = ServiceScope.PROTOTYPE, service = SelectionResource.class
)
public class SelectionResourceImpl extends BaseSelectionResourceImpl {

	@Override
	public Selection postBulkSelection(
			DocumentBulkSelection documentBulkSelection)
		throws Exception {

		BulkSelection bulkSelection = _documentBulkSelectionFactory.create(
			documentBulkSelection);

		return new Selection() {
			{
				size = bulkSelection.getSize();
			}
		};
	}

	@Reference
	private DocumentBulkSelectionFactory _documentBulkSelectionFactory;

}