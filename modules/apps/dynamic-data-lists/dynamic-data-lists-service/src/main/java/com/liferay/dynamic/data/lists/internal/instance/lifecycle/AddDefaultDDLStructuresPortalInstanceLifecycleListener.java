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

package com.liferay.dynamic.data.lists.internal.instance.lifecycle;

import com.liferay.dynamic.data.lists.internal.configuration.DDLServiceConfiguration;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.dynamic.data.lists.internal.configuration.DDLServiceConfiguration",
	immediate = true, service = PortalInstanceLifecycleListener.class
)
public class AddDefaultDDLStructuresPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (!_ddlServiceConfiguration.addDefaultStructures()) {
			return;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Group group = _groupLocalService.getCompanyGroup(
			company.getCompanyId());

		serviceContext.setScopeGroupId(group.getGroupId());

		long defaultUserId = _userLocalService.getDefaultUserId(
			company.getCompanyId());

		serviceContext.setUserId(defaultUserId);

		_defaultDDMStructureHelper.addDDMStructures(
			defaultUserId, group.getGroupId(),
			_portal.getClassNameId(DDLRecordSet.class),
			AddDefaultDDLStructuresPortalInstanceLifecycleListener.class.
				getClassLoader(),
			"com/liferay/dynamic/data/lists/internal/events/dependencies" +
				"/default-dynamic-data-lists-structures.xml",
			serviceContext);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ddlServiceConfiguration = ConfigurableUtil.createConfigurable(
			DDLServiceConfiguration.class, properties);
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	private volatile DDLServiceConfiguration _ddlServiceConfiguration;

	@Reference
	private DefaultDDMStructureHelper _defaultDDMStructureHelper;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}