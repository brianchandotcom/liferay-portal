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

package com.liferay.roles.admin.internal.segments.entry;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRel;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = ModelListener.class)
public class SegmentsEntryRelModelListener
	extends BaseModelListener<SegmentsEntryRel> {

	@Override
	public void onAfterCreate(SegmentsEntryRel segmentsEntryRel)
		throws ModelListenerException {

		_reindexSegmentsEntry(segmentsEntryRel);
	}

	@Override
	public void onAfterRemove(SegmentsEntryRel segmentsEntryRel)
		throws ModelListenerException {

		_reindexSegmentsEntry(segmentsEntryRel);
	}

	private void _reindexSegmentsEntry(SegmentsEntryRel segmentsEntryRel) {
		long roleClassNameId = _classNameLocalService.getClassNameId(
			Role.class);

		if (segmentsEntryRel.getClassNameId() == roleClassNameId) {
			Indexer<SegmentsEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(SegmentsEntry.class);

			try {
				indexer.reindex(
					SegmentsEntry.class.getName(),
					segmentsEntryRel.getSegmentsEntryId());
			}
			catch (SearchException se) {
				throw new ModelListenerException(se);
			}
		}
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

}