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

package com.liferay.commerce.user.segment.internal.util;

import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.commerce.user.segment.util.CommerceUserSegmentHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component
public class CommerceUserSegmentHelperImpl
	implements CommerceUserSegmentHelper {

	@Override
	public long[] getCommerceUserSegmentIds(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		Organization organization =
			_commerceOrganizationHelper.getCurrentOrganization(
				httpServletRequest);

		long organizationId = 0;

		if (organization != null) {
			organizationId = organization.getOrganizationId();
		}

		return _commerceUserSegmentEntryLocalService.
			getCommerceUserSegmentEntryIds(
				_portal.getScopeGroupId(httpServletRequest), organizationId,
				_portal.getUserId(httpServletRequest));
	}

	@Override
	public long[] getCommerceUserSegmentIds(
			long groupId, long organizationId, long userId)
		throws PortalException {

		return _commerceUserSegmentEntryLocalService.
			getCommerceUserSegmentEntryIds(groupId, organizationId, userId);
	}

	@Reference
	private CommerceOrganizationHelper _commerceOrganizationHelper;

	@Reference
	private CommerceUserSegmentEntryLocalService
		_commerceUserSegmentEntryLocalService;

	@Reference
	private Portal _portal;

}