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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.User",
	service = ModelDocumentContributor.class
)
public class UserModelDocumentContributor
	implements ModelDocumentContributor<User> {

	@Override
	public void contribute(Document document, User user) {
		try {
			long[] segmentsEntryIds = _getSegmentsEntryIds(user);

			if (ArrayUtil.isNotEmpty(segmentsEntryIds)) {
				document.addKeyword("segmentsEntryIds", segmentsEntryIds);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to index user " + user.getUserId(), exception);
			}
		}
	}

	private long[] _getSegmentsEntryIds(User user) throws Exception {
		List<SegmentsEntryRel> segmentsEntryRels =
			_segmentsEntryRelLocalService.getSegmentsEntryRels(
				_portal.getClassNameId(User.class), user.getUserId());

		Stream<SegmentsEntryRel> stream = segmentsEntryRels.stream();

		return stream.mapToLong(
			SegmentsEntryRel::getSegmentsEntryId
		).toArray();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserModelDocumentContributor.class);

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

}