/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.user.segment.internal.util;

import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.commerce.user.segment.util.CommerceUserSegmentHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
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

		User user = _portal.getUser(httpServletRequest);

		if (user == null) {
			user = _userLocalService.getDefaultUser(
				_portal.getCompanyId(httpServletRequest));
		}

		return _commerceUserSegmentEntryLocalService.
			getCommerceUserSegmentEntryIds(
				_portal.getScopeGroupId(httpServletRequest), organizationId,
				user.getUserId());
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

	@Reference
	private UserLocalService _userLocalService;

}