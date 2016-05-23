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

package com.liferay.portal.service.convert.database;

import com.liferay.portal.convert.database.DatabaseConverter;
import com.liferay.portal.convert.util.ModelMigrator;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.model.impl.AccountImpl;
import com.liferay.portal.model.impl.AddressImpl;
import com.liferay.portal.model.impl.BrowserTrackerImpl;
import com.liferay.portal.model.impl.ClassNameImpl;
import com.liferay.portal.model.impl.ClusterGroupImpl;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.model.impl.ContactImpl;
import com.liferay.portal.model.impl.CountryImpl;
import com.liferay.portal.model.impl.EmailAddressImpl;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.ImageImpl;
import com.liferay.portal.model.impl.LayoutBranchImpl;
import com.liferay.portal.model.impl.LayoutFriendlyURLImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.LayoutPrototypeImpl;
import com.liferay.portal.model.impl.LayoutRevisionImpl;
import com.liferay.portal.model.impl.LayoutSetBranchImpl;
import com.liferay.portal.model.impl.LayoutSetImpl;
import com.liferay.portal.model.impl.LayoutSetPrototypeImpl;
import com.liferay.portal.model.impl.ListTypeImpl;
import com.liferay.portal.model.impl.MembershipRequestImpl;
import com.liferay.portal.model.impl.OrgGroupRoleImpl;
import com.liferay.portal.model.impl.OrgLaborImpl;
import com.liferay.portal.model.impl.OrganizationImpl;
import com.liferay.portal.model.impl.PasswordPolicyImpl;
import com.liferay.portal.model.impl.PasswordPolicyRelImpl;
import com.liferay.portal.model.impl.PasswordTrackerImpl;
import com.liferay.portal.model.impl.PhoneImpl;
import com.liferay.portal.model.impl.PluginSettingImpl;
import com.liferay.portal.model.impl.PortalPreferencesImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.model.impl.PortletItemImpl;
import com.liferay.portal.model.impl.PortletPreferencesImpl;
import com.liferay.portal.model.impl.RecentLayoutBranchImpl;
import com.liferay.portal.model.impl.RecentLayoutRevisionImpl;
import com.liferay.portal.model.impl.RecentLayoutSetBranchImpl;
import com.liferay.portal.model.impl.RegionImpl;
import com.liferay.portal.model.impl.ReleaseImpl;
import com.liferay.portal.model.impl.RepositoryEntryImpl;
import com.liferay.portal.model.impl.RepositoryImpl;
import com.liferay.portal.model.impl.ResourceActionImpl;
import com.liferay.portal.model.impl.ResourceBlockImpl;
import com.liferay.portal.model.impl.ResourceBlockPermissionImpl;
import com.liferay.portal.model.impl.ResourcePermissionImpl;
import com.liferay.portal.model.impl.ResourceTypePermissionImpl;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.model.impl.ServiceComponentImpl;
import com.liferay.portal.model.impl.SubscriptionImpl;
import com.liferay.portal.model.impl.SystemEventImpl;
import com.liferay.portal.model.impl.TeamImpl;
import com.liferay.portal.model.impl.TicketImpl;
import com.liferay.portal.model.impl.UserGroupGroupRoleImpl;
import com.liferay.portal.model.impl.UserGroupImpl;
import com.liferay.portal.model.impl.UserGroupRoleImpl;
import com.liferay.portal.model.impl.UserIdMapperImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.model.impl.UserNotificationDeliveryImpl;
import com.liferay.portal.model.impl.UserNotificationEventImpl;
import com.liferay.portal.model.impl.UserTrackerImpl;
import com.liferay.portal.model.impl.UserTrackerPathImpl;
import com.liferay.portal.model.impl.VirtualHostImpl;
import com.liferay.portal.model.impl.WebDAVPropsImpl;
import com.liferay.portal.model.impl.WebsiteImpl;
import com.liferay.portal.model.impl.WorkflowDefinitionLinkImpl;
import com.liferay.portal.model.impl.WorkflowInstanceLinkImpl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PortalDatabaseConverter implements DatabaseConverter {
	@Override
	public void convert(DataSource dataSource) throws Exception {
		_modelMigrator.migrate(dataSource, _getModels());
	}

	public void setModelMigrator(ModelMigrator modelMigrator) {
		_modelMigrator = modelMigrator;
	}

	private List<Class<?extends BaseModel<?>>> _getModels() {
		List<Class<?extends BaseModel<?>>> models = new ArrayList<Class<?extends BaseModel<?>>>();

		models.add(AccountImpl.class);
		models.add(AddressImpl.class);
		models.add(BrowserTrackerImpl.class);
		models.add(ClassNameImpl.class);
		models.add(ClusterGroupImpl.class);
		models.add(CompanyImpl.class);
		models.add(ContactImpl.class);
		models.add(CountryImpl.class);
		models.add(EmailAddressImpl.class);
		models.add(GroupImpl.class);
		models.add(ImageImpl.class);
		models.add(LayoutImpl.class);
		models.add(LayoutBranchImpl.class);
		models.add(LayoutFriendlyURLImpl.class);
		models.add(LayoutPrototypeImpl.class);
		models.add(LayoutRevisionImpl.class);
		models.add(LayoutSetImpl.class);
		models.add(LayoutSetBranchImpl.class);
		models.add(LayoutSetPrototypeImpl.class);
		models.add(ListTypeImpl.class);
		models.add(MembershipRequestImpl.class);
		models.add(OrganizationImpl.class);
		models.add(OrgGroupRoleImpl.class);
		models.add(OrgLaborImpl.class);
		models.add(PasswordPolicyImpl.class);
		models.add(PasswordPolicyRelImpl.class);
		models.add(PasswordTrackerImpl.class);
		models.add(PhoneImpl.class);
		models.add(PluginSettingImpl.class);
		models.add(PortalPreferencesImpl.class);
		models.add(PortletImpl.class);
		models.add(PortletItemImpl.class);
		models.add(PortletPreferencesImpl.class);
		models.add(RecentLayoutBranchImpl.class);
		models.add(RecentLayoutRevisionImpl.class);
		models.add(RecentLayoutSetBranchImpl.class);
		models.add(RegionImpl.class);
		models.add(ReleaseImpl.class);
		models.add(RepositoryImpl.class);
		models.add(RepositoryEntryImpl.class);
		models.add(ResourceActionImpl.class);
		models.add(ResourceBlockImpl.class);
		models.add(ResourceBlockPermissionImpl.class);
		models.add(ResourcePermissionImpl.class);
		models.add(ResourceTypePermissionImpl.class);
		models.add(RoleImpl.class);
		models.add(ServiceComponentImpl.class);
		models.add(SubscriptionImpl.class);
		models.add(SystemEventImpl.class);
		models.add(TeamImpl.class);
		models.add(TicketImpl.class);
		models.add(UserImpl.class);
		models.add(UserGroupImpl.class);
		models.add(UserGroupGroupRoleImpl.class);
		models.add(UserGroupRoleImpl.class);
		models.add(UserIdMapperImpl.class);
		models.add(UserNotificationDeliveryImpl.class);
		models.add(UserNotificationEventImpl.class);
		models.add(UserTrackerImpl.class);
		models.add(UserTrackerPathImpl.class);
		models.add(VirtualHostImpl.class);
		models.add(WebDAVPropsImpl.class);
		models.add(WebsiteImpl.class);
		models.add(WorkflowDefinitionLinkImpl.class);
		models.add(WorkflowInstanceLinkImpl.class);

		return models;
	}

	@BeanReference(type = ModelMigrator.class)
	private ModelMigrator _modelMigrator;
}