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

package com.liferay.commerce.product.demo.internal;

import com.liferay.commerce.product.demo.data.creator.CPDemoDataCreator;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.users.admin.demo.data.creator.OmniAdminUserDemoDataCreator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class CPDemo extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		Group guestGroup = _groupLocalService.getGroup(
			company.getCompanyId(), "Guest");

		User user = _omniAdminUserDemoDataCreator.create(
			company.getCompanyId(), "alessio.rendina@liferay.com");

		_cpDemoDataCreator.create(
			user.getUserId(), guestGroup.getGroupId(), true);
	}

	@Activate
	protected void activate() throws PortalException {
		_cpDemoDataCreator.init();
	}

	@Deactivate
	protected void deactivate() throws PortalException {
		_cpDemoDataCreator.delete();
		_omniAdminUserDemoDataCreator.delete();
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference
	private CPDemoDataCreator _cpDemoDataCreator;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OmniAdminUserDemoDataCreator _omniAdminUserDemoDataCreator;

}