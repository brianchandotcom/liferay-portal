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

package com.liferay.roles.admin.internal.segments.entry.search.spi.model.index.contributor;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
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
		try {
			document.addKeyword("roleIds", getRoleIds(segmentsEntry));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to index segments entry " +
						segmentsEntry.getSegmentsEntryId(),
					e);
			}
		}
	}

	protected long[] getRoleIds(SegmentsEntry segmentsEntry) {
		Set<Long> roleIds = new HashSet<>();

		List<SegmentsEntryRel> segmentsEntryRels =
			_segmentsEntryRelLocalService.getSegmentsEntryRels(
				segmentsEntry.getSegmentsEntryId());

		long roleClassNameId = _classNameLocalService.getClassNameId(
			Role.class);

		for (SegmentsEntryRel segmentsEntryRel : segmentsEntryRels) {
			if (segmentsEntryRel.getClassNameId() == roleClassNameId) {
				roleIds.add(segmentsEntryRel.getClassPK());
			}
		}

		return ArrayUtil.toLongArray(roleIds);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryModelDocumentContributor.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

}