/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.headless.admin.user.dto.v1_0.SegmentUser;
import com.liferay.headless.admin.user.resource.v1_0.SegmentUserResource;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;
import com.liferay.segments.service.SegmentsEntryLocalService;

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

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.getSegmentsEntry(segmentId);

		return Page.of(
			transformToList(
				ArrayUtil.toArray(
					_segmentsEntryProviderRegistry.getSegmentsEntryClassPKs(
						segmentsEntry.getSegmentsEntryId(),
						pagination.getStartPosition(),
						pagination.getEndPosition())),
				this::_toSegmentUser),
			pagination,
			_segmentsEntryProviderRegistry.getSegmentsEntryClassPKsCount(
				segmentsEntry.getSegmentsEntryId()));
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
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;

	@Reference
	private UserService _userService;

}