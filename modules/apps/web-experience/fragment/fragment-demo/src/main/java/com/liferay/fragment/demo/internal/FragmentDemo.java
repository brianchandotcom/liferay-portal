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

package com.liferay.fragment.demo.internal;

import com.liferay.fragment.demo.data.creator.FragmentCollectionDemoDataCreator;
import com.liferay.fragment.demo.data.creator.FragmentEntryDemoDataCreator;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.users.admin.demo.data.creator.SiteAdminUserDemoDataCreator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class FragmentDemo extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		Group group = _groupLocalService.getGroup(
			company.getCompanyId(), "Guest");

		User user = _siteAdminUserDemoDataCreator.create(
			group.getGroupId(), "sharon.choi@liferay.com");

		FragmentCollection fragmentCollection =
			_fragmentCollectionDemoDataCreator.create(
				user.getUserId(), group.getGroupId(), "Demo Collection");

		for (int i = 0; i < 5; i++) {
			_fragmentEntryDemoDataCreator.create(
				user.getUserId(), group.getGroupId(),
				fragmentCollection.getFragmentCollectionId());
		}
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference
	private FragmentCollectionDemoDataCreator
		_fragmentCollectionDemoDataCreator;

	@Reference
	private FragmentEntryDemoDataCreator _fragmentEntryDemoDataCreator;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private SiteAdminUserDemoDataCreator _siteAdminUserDemoDataCreator;

}