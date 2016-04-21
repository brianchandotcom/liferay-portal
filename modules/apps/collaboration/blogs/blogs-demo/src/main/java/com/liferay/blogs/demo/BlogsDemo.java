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

package com.liferay.blogs.demo;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.users.admin.demo.data.creator.BasicUserDemoDataCreator;
import com.liferay.users.admin.demo.data.creator.OmniAdminUserDemoDataCreator;
import com.liferay.users.admin.demo.data.creator.SiteAdminUserDemoDataCreator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class BlogsDemo extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		_omniAdminUserDemoDataCreator.create(
			company.getCompanyId(), "sergio.gonzalez@liferay.com");

		Group guestGroup = _groupLocalService.getGroup(
			company.getCompanyId(), "Guest");

		_siteAdminUserDemoDataCreator.create(
			guestGroup.getGroupId(), "sharon.choi@liferay.com");
		_basicUserDemoDataCreator.create(
			company.getCompanyId(), "nikki.prudencio@liferay.com");
	}

	@Deactivate
	protected void deactivate() throws PortalException {
		_omniAdminUserDemoDataCreator.delete();
		_basicUserDemoDataCreator.delete();
		_siteAdminUserDemoDataCreator.delete();
	}

	@Reference(unbind = "-")
	protected void setBasicUserDemoDataCreator(
		BasicUserDemoDataCreator basicUserDemoDataCreator) {

		_basicUserDemoDataCreator = basicUserDemoDataCreator;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setOmniAdminUserDemoDataCreator(
		OmniAdminUserDemoDataCreator omniAdminUserDemoDataCreator) {

		_omniAdminUserDemoDataCreator = omniAdminUserDemoDataCreator;
	}

	@Reference(unbind = "-", target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setSiteAdminUserDemoDataCreator(
		SiteAdminUserDemoDataCreator siteAdminUserDemoDataCreator) {

		_siteAdminUserDemoDataCreator = siteAdminUserDemoDataCreator;
	}

	private BasicUserDemoDataCreator _basicUserDemoDataCreator;
	private GroupLocalService _groupLocalService;
	private OmniAdminUserDemoDataCreator _omniAdminUserDemoDataCreator;
	private SiteAdminUserDemoDataCreator _siteAdminUserDemoDataCreator;

}