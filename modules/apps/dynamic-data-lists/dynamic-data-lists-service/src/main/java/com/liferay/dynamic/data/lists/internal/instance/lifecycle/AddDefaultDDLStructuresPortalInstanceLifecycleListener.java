/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.instance.lifecycle;

import com.liferay.dynamic.data.lists.internal.configuration.DDLServiceConfiguration;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.osgi.util.configuration.ConfigurationPersistenceUtil;
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
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.dynamic.data.lists.internal.configuration.DDLServiceConfiguration",
	service = PortalInstanceLifecycleListener.class
)
public class AddDefaultDDLStructuresPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public long getLastModifiedTime() {
		return _lastModifiedTime;
	}

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

		long guestUserId = _userLocalService.getGuestUserId(
			company.getCompanyId());

		serviceContext.setUserId(guestUserId);

		_defaultDDMStructureHelper.addDDMStructures(
			guestUserId, group.getGroupId(),
			_portal.getClassNameId(DDLRecordSet.class),
			AddDefaultDDLStructuresPortalInstanceLifecycleListener.class.
				getClassLoader(),
			"com/liferay/dynamic/data/lists/internal/events/dependencies" +
				"/default-dynamic-data-lists-structures.xml",
			serviceContext);
	}

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		_lastModifiedTime = ConfigurationPersistenceUtil.update(
			this, properties);

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

	private long _lastModifiedTime;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}