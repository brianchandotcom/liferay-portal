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

import com.liferay.bulk.rest.dto.v1_0.DocumentSelection;
import com.liferay.bulk.rest.dto.v1_0.SelectionToAddCategoryIds;
import com.liferay.bulk.rest.internal.helper.BulkSelectionHelper;
import com.liferay.bulk.rest.resource.v1_0.CategoryResource;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionInputParameters;
import com.liferay.bulk.selection.BulkSelectionRunner;
import com.liferay.document.library.bulk.selection.EditCategoriesBulkSelectionAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.io.Serializable;

import java.util.HashMap;

import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alejandro Tardín
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/category.properties",
	scope = ServiceScope.PROTOTYPE, service = CategoryResource.class
)
public class CategoryResourceImpl extends BaseCategoryResourceImpl {

	@Override
	public boolean patchCategoryBatch(DocumentSelection documentSelection)
		throws Exception {

		_updateCategories(documentSelection, true);

		return true;
	}

	@Override
	public boolean putCategoryBatch(DocumentSelection documentSelection)
		throws Exception {

		_updateCategories(documentSelection, false);

		return true;
	}

	private void _updateCategories(
			DocumentSelection documentSelection, boolean append)
		throws PortalException {

		BulkSelection<?> bulkSelection = _bulkSelectionHelper.getBulkSelection(
			documentSelection);

		SelectionToAddCategoryIds selectionToAddCategoryIds =
			(SelectionToAddCategoryIds)documentSelection;

		_bulkSelectionRunner.run(
			_user, bulkSelection.toAssetEntryBulkSelection(),
			_editCategoriesBulkSelectionAction,
			new HashMap<String, Serializable>() {
				{
					put(
						BulkSelectionInputParameters.ASSET_ENTRY_BULK_SELECTION,
						true);
					put("append", append);
					put(
						"toAddCategoryIds",
						selectionToAddCategoryIds.
							getSelectionToAddCategoryIds());
					put(
						"toRemoveCategoryIds",
						selectionToAddCategoryIds.
							getSelectionToRemoveCategoryIds());
				}
			});
	}

	@Reference
	private BulkSelectionHelper _bulkSelectionHelper;

	@Reference
	private BulkSelectionRunner _bulkSelectionRunner;

	@Reference
	private EditCategoriesBulkSelectionAction
		_editCategoriesBulkSelectionAction;

	@Context
	private User _user;

}