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

package com.liferay.segments.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.segments.internal.search.SegmentsEntryField;
import com.liferay.segments.model.SegmentsEntry;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.segments.model.SegmentsEntry",
	service = ModelDocumentContributor.class
)
public class SegmentsEntryModelDocumentContributor
	implements ModelDocumentContributor<SegmentsEntry> {

	@Override
	public void contribute(Document document, SegmentsEntry segmentsEntry) {
		document.addLocalizedKeyword(
			Field.DESCRIPTION, segmentsEntry.getDescriptionMap(), true);
		document.addDate(Field.MODIFIED_DATE, segmentsEntry.getModifiedDate());
		document.addLocalizedKeyword(
			Field.NAME, segmentsEntry.getNameMap(), true);
		document.addKeyword(
			SegmentsEntryField.ACTIVE, segmentsEntry.isActive());
	}

}