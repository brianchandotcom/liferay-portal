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

package com.liferay.change.tracking.internal.search.spi.model.index.contributor;

import com.liferay.change.tracking.configuration.CTConfigurationRegistryUtil;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTCollectionModel;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.change.tracking.model.CTEntry",
	service = ModelDocumentContributor.class
)
public class CTEntryModelDocumentContributor
	implements ModelDocumentContributor<CTEntry> {

	@Override
	public void contribute(Document document, CTEntry ctEntry) {
		document.addDate(Field.CREATE_DATE, ctEntry.getCreateDate());
		document.addKeyword(Field.GROUP_ID, _getGroupId(ctEntry));
		document.addDate(Field.MODIFIED_DATE, ctEntry.getModifiedDate());
		document.addKeyword(Field.STATUS, ctEntry.getStatus());
		document.addText(Field.TITLE, _getTitle(ctEntry));
		document.addKeyword("changeType", ctEntry.getChangeType());
		document.addKeyword("collision", ctEntry.isCollision());
		document.addKeyword("ctCollectionId", _getCTCollectionIds(ctEntry));
		document.addKeyword(
			"modelClassName",
			_portal.getClassName(ctEntry.getModelClassNameId()));
		document.addKeyword("modelClassNameId", ctEntry.getModelClassNameId());
		document.addKeyword("modelClassPK", ctEntry.getModelClassPK());
		document.addKeyword(
			"modelResourcePrimKey", ctEntry.getModelResourcePrimKey());
		document.addKeyword(
			"originalCTCollectionId", ctEntry.getOriginalCTCollectionId());
	}

	private long[] _getCTCollectionIds(CTEntry ctEntry) {
		List<CTCollection> ctEntryCTCollections =
			_ctCollectionLocalService.getCTEntryCTCollections(
				ctEntry.getCtEntryId());

		Stream<CTCollection> ctCollectionStream = ctEntryCTCollections.stream();

		return ctCollectionStream.map(
			CTCollectionModel::getCtCollectionId
		).mapToLong(
			Long::valueOf
		).toArray();
	}

	private long _getGroupId(CTEntry ctEntry) {
		return CTConfigurationRegistryUtil.getVersionEntityGroupId(
			ctEntry.getModelClassNameId(), ctEntry.getModelClassPK());
	}

	private String _getTitle(CTEntry ctEntry) {
		return CTConfigurationRegistryUtil.getVersionEntityTitle(
			ctEntry.getModelClassNameId(), ctEntry.getModelClassPK());
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private Portal _portal;

}