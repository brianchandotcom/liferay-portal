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

package com.liferay.headless.foundation.internal.resource.v1_0;

import com.liferay.headless.foundation.dto.v1_0.Segment;
import com.liferay.headless.foundation.internal.mapper.HeaderContextMapper;
import com.liferay.headless.foundation.resource.v1_0.SegmentResource;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.provider.SegmentsEntryProvider;
import com.liferay.segments.service.SegmentsEntryService;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/segment.properties",
	scope = ServiceScope.PROTOTYPE, service = SegmentResource.class
)
public class SegmentResourceImpl extends BaseSegmentResourceImpl {

	@Override
	public Page<Segment> getUserSegmentsPage(Long userId, Pagination pagination)
		throws Exception {

		User user = _userService.getUserById(userId);

		MultivaluedMap<String, String> requestHeaders =
			_httpHeaders.getRequestHeaders();

		long[] segmentsEntryIds = _segmentsEntryProvider.getSegmentsEntryIds(
			user.getModelClassName(), user.getPrimaryKey(),
			_headerContextMapper.create(requestHeaders));

		List<SegmentsEntry> segmentsEntries = new ArrayList<>(
			segmentsEntryIds.length);

		for (long segmentEntryId : segmentsEntryIds) {
			SegmentsEntry segmentsEntry =
				_segmentsEntryService.getSegmentsEntry(segmentEntryId);

			segmentsEntries.add(segmentsEntry);
		}

		return Page.of(
			transform(segmentsEntries, this::_toSegment), pagination,
			segmentsEntries.size());
	}

	private Segment _toSegment(SegmentsEntry segmentsEntry) {
		return new Segment() {
			{
				active = segmentsEntry.isActive();
				criteria = segmentsEntry.getCriteria();
				dateCreated = segmentsEntry.getCreateDate();
				dateModified = segmentsEntry.getModifiedDate();
				id = segmentsEntry.getSegmentsEntryId();
				name = segmentsEntry.getName(
					segmentsEntry.getDefaultLanguageId());
				source = segmentsEntry.getSource();
			}
		};
	}

	@Reference
	private HeaderContextMapper _headerContextMapper;

	@Context
	private HttpHeaders _httpHeaders;

	@Reference
	private SegmentsEntryProvider _segmentsEntryProvider;

	@Reference
	private SegmentsEntryService _segmentsEntryService;

	@Reference
	private UserService _userService;

}