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

package com.liferay.journal.change.tracking.internal.util;

import com.liferay.change.tracking.CTManager;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTCollectionModel;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalChangeTrackingHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 */
@Component(immediate = true, service = JournalChangeTrackingHelper.class)
public class JournalChangeTrackingHelperImpl
	implements JournalChangeTrackingHelper {

	@Override
	public String getJournalArticleCTCollectionName(long userId, long id) {
		long classNameId = _portal.getClassNameId(
			JournalArticle.class.getName());

		Optional<CTEntry> ctEntryOptional =
			_ctManager.getActiveCTCollectionCTEntryOptional(
				userId, classNameId, id);

		Stream<CTCollection> stream = ctEntryOptional.map(
			CTEntry::getCtEntryId
		).map(
			_ctCollectionLocalService::getCTEntryCTCollections
		).map(
			List::stream
		).orElse(
			Stream.empty()
		);

		return stream.filter(
			ctCollection -> !ctCollection.isProduction()
		).map(
			CTCollectionModel::getName
		).findFirst(
		).orElse(
			StringPool.BLANK
		);
	}

	@Override
	public boolean hasActiveCTCollection(long companyId, long userId) {
		Optional<CTCollection> ctCollectionOptional =
			_ctManager.getActiveCTCollectionOptional(userId);

		return ctCollectionOptional.map(
			ctCollection -> !ctCollection.isProduction()
		).orElse(
			false
		);
	}

	@Override
	public boolean isJournalArticleInChangeList(long userId, long id) {
		long classNameId = _portal.getClassNameId(
			JournalArticle.class.getName());

		long activeCTCollectionId = _getActiveCTCollectionId(userId);

		Optional<CTEntry> ctEntryOptional =
			_ctManager.getActiveCTCollectionCTEntryOptional(
				userId, classNameId, id);

		ctEntryOptional = ctEntryOptional.filter(
			ctEntry ->
				ctEntry.getOriginalCTCollectionId() == activeCTCollectionId);

		if (ctEntryOptional.isPresent()) {
			return true;
		}

		return false;
	}

	private long _getActiveCTCollectionId(long userId) {
		Optional<CTCollection> ctCollectionOptional =
			_ctManager.getActiveCTCollectionOptional(userId);

		return ctCollectionOptional.filter(
			ctCollection -> !ctCollection.isProduction()
		).map(
			CTCollection::getCtCollectionId
		).orElse(
			0L
		);
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTManager _ctManager;

	@Reference
	private Portal _portal;

}