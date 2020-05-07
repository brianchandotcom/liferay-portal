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

package com.liferay.redirect.service.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.redirect.constants.RedirectConstants;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.base.RedirectEntryServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=redirect",
		"json.web.service.context.path=RedirectEntry"
	},
	service = AopService.class
)
public class RedirectEntryServiceImpl extends RedirectEntryServiceBaseImpl {

	@Override
	public RedirectEntry addRedirectEntry(
			long groupId, String destinationURL, Date expirationDate,
			boolean permanent, String sourceURL, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_ENTRY);

		return redirectEntryLocalService.addRedirectEntry(
			groupId, destinationURL, expirationDate, permanent, sourceURL,
			serviceContext);
	}

	@Override
	public RedirectEntry deleteRedirectEntry(long redirectEntryId)
		throws PortalException {

		_redirectEntryModelResourcePermission.check(
			getPermissionChecker(), redirectEntryId, ActionKeys.DELETE);

		return redirectEntryLocalService.deleteRedirectEntry(redirectEntryId);
	}

	@Override
	public RedirectEntry fetchRedirectEntry(long redirectEntryId)
		throws PortalException {

		RedirectEntry redirectEntry =
			redirectEntryLocalService.fetchRedirectEntry(redirectEntryId);

		if (redirectEntry != null) {
			_redirectEntryModelResourcePermission.check(
				getPermissionChecker(), redirectEntry, ActionKeys.VIEW);
		}

		return redirectEntry;
	}

	@Override
	public List<RedirectEntry> getRedirectEntries(
			long groupId, int start, int end,
			OrderByComparator<RedirectEntry> obc)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.hasPermission(
				groupId, RedirectEntry.class.getName(),
				RedirectEntry.class.getName(), ActionKeys.VIEW)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, RedirectEntry.class.getName(),
				RedirectEntry.class.getName(), ActionKeys.VIEW);
		}

		return redirectEntryLocalService.getRedirectEntries(
			groupId, start, end, obc);
	}

	@Override
	public int getRedirectEntriesCount(long groupId) throws PortalException {
		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.hasPermission(
				groupId, RedirectEntry.class.getName(),
				RedirectEntry.class.getName(), ActionKeys.VIEW)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, RedirectEntry.class.getName(),
				RedirectEntry.class.getName(), ActionKeys.VIEW);
		}

		return redirectEntryLocalService.getRedirectEntriesCount(groupId);
	}

	@Override
	public void updateChainedRedirectEntries(
			long groupId, String destinationURL, String groupBaseURL,
			String sourceURL)
		throws PortalException {

		List<RedirectEntry> redirectEntries =
			redirectEntryLocalService.
				getRedirectEntriesByGroupIdAndDestinationURL(
					groupId, groupBaseURL + StringPool.SLASH + sourceURL);

		for (RedirectEntry redirectEntry : redirectEntries) {
			updateRedirectEntry(
				redirectEntry.getRedirectEntryId(), destinationURL,
				redirectEntry.getExpirationDate(), redirectEntry.isPermanent(),
				redirectEntry.getSourceURL());
		}

		RedirectEntry chainedRedirectEntry =
			redirectEntryLocalService.fetchRedirectEntry(
				groupId,
				StringUtil.removeSubstring(
					destinationURL, groupBaseURL + StringPool.SLASH));

		if (chainedRedirectEntry != null) {
			RedirectEntry redirectEntry =
				redirectEntryLocalService.fetchRedirectEntry(
					groupId, sourceURL);

			updateRedirectEntry(
				redirectEntry.getRedirectEntryId(),
				chainedRedirectEntry.getDestinationURL(),
				redirectEntry.getExpirationDate(), redirectEntry.isPermanent(),
				redirectEntry.getSourceURL());
		}
	}

	@Override
	public RedirectEntry updateRedirectEntry(
			long redirectEntryId, String destinationURL, Date expirationDate,
			boolean permanent, String sourceURL)
		throws PortalException {

		_redirectEntryModelResourcePermission.check(
			getPermissionChecker(), redirectEntryId, ActionKeys.UPDATE);

		return redirectEntryLocalService.updateRedirectEntry(
			redirectEntryId, destinationURL, expirationDate, permanent,
			sourceURL);
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(resource.name=" + RedirectConstants.RESOURCE_NAME + ")"
	)
	private volatile PortletResourcePermission _portletResourcePermission;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.redirect.model.RedirectEntry)"
	)
	private volatile ModelResourcePermission<RedirectEntry>
		_redirectEntryModelResourcePermission;

}