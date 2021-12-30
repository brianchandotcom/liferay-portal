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

package com.liferay.document.library.internal.search.spi.model.query.contributor;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManager;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BaseRelatedEntryIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.RelatedEntryIndexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	property = "indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = ModelPreFilterContributor.class
)
public class DLFileEntryModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		_addAttachmentFilter(booleanFilter, searchContext);
		_addClassTypeIdsFilter(booleanFilter, searchContext);
		_addDDMFieldFilter(booleanFilter, searchContext);
		addWorkflowStatusFilter(
			booleanFilter, modelSearchSettings, searchContext);
		addHiddenFilter(booleanFilter, searchContext);
		_addMimeTypesFilter(booleanFilter, searchContext);
	}

	protected void addHiddenFilter(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		if ((ArrayUtil.isEmpty(searchContext.getFolderIds()) ||
			 ArrayUtil.contains(
				 searchContext.getFolderIds(),
				 DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) &&
			!searchContext.isIncludeAttachments()) {

			booleanFilter.addRequiredTerm(Field.HIDDEN, false);
		}
	}

	protected void addWorkflowStatusFilter(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		workflowStatusModelPreFilterContributor.contribute(
			booleanFilter, modelSearchSettings, searchContext);
	}

	@Reference
	protected DDMIndexer ddmIndexer;

	@Reference
	protected DDMStructureManager ddmStructureManager;

	protected RelatedEntryIndexer relatedEntryIndexer =
		new BaseRelatedEntryIndexer();

	@Reference(target = "(model.pre.filter.contributor.id=WorkflowStatus)")
	protected ModelPreFilterContributor workflowStatusModelPreFilterContributor;

	private void _addAttachmentFilter(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		if (!searchContext.isIncludeAttachments()) {
			return;
		}

		try {
			relatedEntryIndexer.addRelatedClassNames(
				booleanFilter, searchContext);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private void _addClassTypeIdsFilter(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		long[] classTypeIds = searchContext.getClassTypeIds();

		if (ArrayUtil.isEmpty(classTypeIds)) {
			return;
		}

		TermsFilter termsFilter = new TermsFilter(Field.CLASS_TYPE_ID);

		termsFilter.addValues(ArrayUtil.toStringArray(classTypeIds));

		booleanFilter.add(termsFilter, BooleanClauseOccur.MUST);
	}

	private void _addDDMFieldFilter(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		try {
			String ddmStructureFieldName = (String)searchContext.getAttribute(
				"ddmStructureFieldName");
			Serializable ddmStructureFieldValue = searchContext.getAttribute(
				"ddmStructureFieldValue");

			if (Validator.isNotNull(ddmStructureFieldName) &&
				Validator.isNotNull(ddmStructureFieldValue)) {

				QueryFilter queryFilter =
					ddmIndexer.createFieldValueQueryFilter(
						ddmStructureFieldName, ddmStructureFieldValue,
						searchContext.getLocale());

				booleanFilter.add(queryFilter, BooleanClauseOccur.MUST);
			}
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private void _addMimeTypesFilter(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		String[] mimeTypes = (String[])searchContext.getAttribute("mimeTypes");

		if (ArrayUtil.isNotEmpty(mimeTypes)) {
			BooleanFilter mimeTypesBooleanFilter = new BooleanFilter();

			for (String mimeType : mimeTypes) {
				mimeTypesBooleanFilter.addTerm(
					"mimeType",
					StringUtil.replace(
						mimeType, CharPool.FORWARD_SLASH, CharPool.UNDERLINE));
			}

			booleanFilter.add(mimeTypesBooleanFilter, BooleanClauseOccur.MUST);
		}
	}

}