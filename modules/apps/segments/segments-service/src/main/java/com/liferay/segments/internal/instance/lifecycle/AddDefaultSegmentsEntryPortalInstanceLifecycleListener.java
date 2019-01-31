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

package com.liferay.segments.internal.instance.lifecycle;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author David Arques
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class AddDefaultSegmentsEntryPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);

		Group group = _groupLocalService.getCompanyGroup(
			company.getCompanyId());

		serviceContext.setScopeGroupId(group.getGroupId());

		long defaultUserId = _userLocalService.getDefaultUserId(
			company.getCompanyId());

		serviceContext.setUserId(defaultUserId);

		_addDefaultSegmentsEntry(serviceContext);
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private void _addDefaultSegmentsEntry(ServiceContext serviceContext)
		throws PortalException {

		SegmentsEntry segmentsEntry = _fetchSegmentsEntryIncludingAncestors(
			serviceContext.getScopeGroupId(), SegmentsConstants.KEY_DEFAULT);

		if (segmentsEntry != null) {
			return;
		}

		Map<Locale, String> nameMap = ResourceBundleUtil.getLocalizationMap(
			_resourceBundleLoader, "default-segment");

		_segmentsEntryLocalService.addSegmentsEntry(
			nameMap, Collections.emptyMap(), true, StringPool.BLANK,
			SegmentsConstants.KEY_DEFAULT, SegmentsConstants.SOURCE_DEFAULT,
			User.class.getName(), serviceContext);
	}

	private SegmentsEntry _fetchSegmentsEntryIncludingAncestors(
		long groupId, String key) {

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(groupId, key);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		for (long ancestorSiteGroupId :
				_portal.getAncestorSiteGroupIds(groupId)) {

			segmentsEntry = _segmentsEntryLocalService.fetchSegmentsEntry(
				ancestorSiteGroupId, key);

			if (segmentsEntry != null) {
				return segmentsEntry;
			}
		}

		return null;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.segments.lang)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}