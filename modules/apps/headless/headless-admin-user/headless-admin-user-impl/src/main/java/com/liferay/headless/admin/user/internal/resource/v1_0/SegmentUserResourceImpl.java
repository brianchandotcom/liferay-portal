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

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.headless.admin.user.dto.v1_0.SegmentUser;
import com.liferay.headless.admin.user.resource.v1_0.SegmentUserResource;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/segment-user.properties",
	scope = ServiceScope.PROTOTYPE, service = SegmentUserResource.class
)
public class SegmentUserResourceImpl extends BaseSegmentUserResourceImpl {

	@Override
	public Page<SegmentUser> getSegmentUserAccountsPage(
			Long segmentId, Pagination pagination)
		throws Exception {

		long[] segmentsEntryClassPKs =
			_segmentsEntryProviderRegistry.getSegmentsEntryClassPKs(
				segmentId, pagination.getStartPosition(),
				pagination.getEndPosition());

		return Page.of(
			transformToList(
				ArrayUtil.toArray(segmentsEntryClassPKs), this::_toSegmentUser),
			pagination,
			_segmentsEntryProviderRegistry.getSegmentsEntryClassPKsCount(
				segmentId));
	}

	private SegmentUser _toSegmentUser(long segmentsEntryClassPK)
		throws Exception {

		User user = _userService.getUserById(segmentsEntryClassPK);

		return new SegmentUser() {
			{
				emailAddress = user.getEmailAddress();
				id = user.getUserId();
				name = user.getFullName();
			}
		};
	}

	@Reference
	private SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;

	@Reference
	private UserService _userService;

}