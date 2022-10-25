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

package com.liferay.segments.demo.internal;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.segments.demo.data.creator.SegmentsEntryDemoDataCreator;
import com.liferay.site.demo.data.creator.SiteDemoDataCreator;
import com.liferay.users.admin.demo.data.creator.SiteAdminUserDemoDataCreator;
import com.liferay.users.admin.demo.data.creator.SiteMemberUserDemoDataCreator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class SegmentsDemo extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		Group group = _siteDemoDataCreator.create(
			company.getCompanyId(), "Segments Demo Site");

		for (int i = 0; i < 30; i++) {
			_siteMemberUserDemoDataCreator.create(group.getGroupId());
		}

		User user = _siteAdminUserDemoDataCreator.create(
			group.getGroupId(), "segments.admin@liferay.com");

		for (int i = 0; i < 5; i++) {
			_segmentsEntryDemoDataCreator.create(
				user.getUserId(), group.getGroupId());
		}
	}

	@Deactivate
	protected void deactivate() throws PortalException {
		_segmentsEntryDemoDataCreator.delete();

		_siteMemberUserDemoDataCreator.delete();

		_siteAdminUserDemoDataCreator.delete();

		_siteDemoDataCreator.delete();
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private SegmentsEntryDemoDataCreator _segmentsEntryDemoDataCreator;

	@Reference
	private SiteAdminUserDemoDataCreator _siteAdminUserDemoDataCreator;

	@Reference
	private SiteDemoDataCreator _siteDemoDataCreator;

	@Reference
	private SiteMemberUserDemoDataCreator _siteMemberUserDemoDataCreator;

}