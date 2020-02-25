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

package com.liferay.depot.internal.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.GroupWrapper;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class DepotGroupLocalServiceWrapper extends GroupLocalServiceWrapper {

	public DepotGroupLocalServiceWrapper() {
		super(null);
	}

	public DepotGroupLocalServiceWrapper(GroupLocalService groupLocalService) {
		super(groupLocalService);
	}

	@Override
	public Group fetchCompanyGroup(long companyId) {
		return _wrap(super.fetchCompanyGroup(companyId));
	}

	@Override
	public Group fetchFriendlyURLGroup(long companyId, String friendlyURL) {
		return _wrap(super.fetchFriendlyURLGroup(companyId, friendlyURL));
	}

	@Override
	public Group fetchGroup(long groupId) {
		return _wrap(super.fetchGroup(groupId));
	}

	@Override
	public Group fetchGroup(long companyId, long classNameId, long classPK) {
		return _wrap(super.fetchGroup(companyId, classNameId, classPK));
	}

	@Override
	public Group fetchGroup(long companyId, String groupKey) {
		return _wrap(super.fetchGroup(companyId, groupKey));
	}

	@Override
	public Group fetchGroupByUuidAndCompanyId(String uuid, long companyId) {
		return _wrap(super.fetchGroupByUuidAndCompanyId(uuid, companyId));
	}

	@Override
	public Group fetchStagingGroup(long liveGroupId) {
		return _wrap(super.fetchStagingGroup(liveGroupId));
	}

	@Override
	public Group fetchUserGroup(long companyId, long userId) {
		return _wrap(super.fetchUserGroup(companyId, userId));
	}

	@Override
	public Group fetchUserPersonalSiteGroup(long companyId)
		throws PortalException {

		return _wrap(super.fetchUserPersonalSiteGroup(companyId));
	}

	@Override
	public List<Group> getActiveGroups(long companyId, boolean active) {
		return _wrap(super.getActiveGroups(companyId, active));
	}

	@Override
	public List<Group> getActiveGroups(
		long companyId, boolean site, boolean active, int start, int end,
		OrderByComparator<Group> obc) {

		return _wrap(
			super.getActiveGroups(companyId, site, active, start, end, obc));
	}

	@Override
	public List<Group> getActiveGroups(
		long companyId, boolean active, int start, int end,
		OrderByComparator<Group> obc) {

		return _wrap(super.getActiveGroups(companyId, active, start, end, obc));
	}

	@Override
	public Group getCompanyGroup(long companyId) throws PortalException {
		return _wrap(super.getCompanyGroup(companyId));
	}

	@Override
	public List<Group> getCompanyGroups(long companyId, int start, int end) {
		return _wrap(super.getCompanyGroups(companyId, start, end));
	}

	@Override
	public Group getFriendlyURLGroup(long companyId, String friendlyURL)
		throws PortalException {

		return _wrap(super.getFriendlyURLGroup(companyId, friendlyURL));
	}

	@Override
	public Group getGroup(long groupId) throws PortalException {
		return _wrap(super.getGroup(groupId));
	}

	@Override
	public Group getGroup(long companyId, String groupKey)
		throws PortalException {

		return _wrap(super.getGroup(companyId, groupKey));
	}

	@Override
	public Group getGroupByUuidAndCompanyId(String uuid, long companyId)
		throws PortalException {

		return _wrap(super.getGroupByUuidAndCompanyId(uuid, companyId));
	}

	@Override
	public List<Group> getGroups(int start, int end) {
		return _wrap(super.getGroups(start, end));
	}

	@Override
	public List<Group> getGroups(
		long companyId, long parentGroupId, boolean site) {

		return _wrap(super.getGroups(companyId, parentGroupId, site));
	}

	@Override
	public List<Group> getGroups(
		long companyId, long parentGroupId, boolean site,
		boolean inheritContent) {

		return _wrap(
			super.getGroups(companyId, parentGroupId, site, inheritContent));
	}

	@Override
	public List<Group> getGroups(
		long companyId, long parentGroupId, boolean site, int start, int end) {

		return _wrap(
			super.getGroups(companyId, parentGroupId, site, start, end));
	}

	@Override
	public List<Group> getGroups(
		long companyId, long parentGroupId, String name, boolean site,
		int start, int end) {

		return _wrap(
			super.getGroups(companyId, parentGroupId, name, site, start, end));
	}

	@Override
	public List<Group> getGroups(
		long companyId, String treePath, boolean site) {

		return _wrap(super.getGroups(companyId, treePath, site));
	}

	@Override
	public List<Group> getGroups(
		long companyId, String className, long parentGroupId) {

		return _wrap(super.getGroups(companyId, className, parentGroupId));
	}

	@Override
	public List<Group> getGroups(
		long companyId, String className, long parentGroupId, int start,
		int end) {

		return _wrap(
			super.getGroups(companyId, className, parentGroupId, start, end));
	}

	@Override
	public List<Group> getGroups(long[] groupIds) throws PortalException {
		return _wrap(super.getGroups(groupIds));
	}

	@Override
	public Group getLayoutGroup(long companyId, long plid)
		throws PortalException {

		return _wrap(super.getLayoutGroup(companyId, plid));
	}

	@Override
	public Group getLayoutPrototypeGroup(long companyId, long layoutPrototypeId)
		throws PortalException {

		return _wrap(
			super.getLayoutPrototypeGroup(companyId, layoutPrototypeId));
	}

	@Override
	public Group getLayoutSetPrototypeGroup(
			long companyId, long layoutSetPrototypeId)
		throws PortalException {

		return _wrap(
			super.getLayoutSetPrototypeGroup(companyId, layoutSetPrototypeId));
	}

	@Override
	public List<Group> getLayoutsGroups(
		long companyId, long parentGroupId, boolean site, boolean active,
		int start, int end, OrderByComparator<Group> obc) {

		return _wrap(
			super.getLayoutsGroups(
				companyId, parentGroupId, site, active, start, end, obc));
	}

	@Override
	public List<Group> getLayoutsGroups(
		long companyId, long parentGroupId, boolean site, int start, int end,
		OrderByComparator<Group> obc) {

		return _wrap(
			super.getLayoutsGroups(
				companyId, parentGroupId, site, start, end, obc));
	}

	@Override
	public List<Group> getLiveGroups() {
		return _wrap(super.getLiveGroups());
	}

	@Override
	public Group getOrganizationGroup(long companyId, long organizationId)
		throws PortalException {

		return _wrap(super.getOrganizationGroup(companyId, organizationId));
	}

	@Override
	public List<Group> getOrganizationGroups(long organizationId) {
		return _wrap(super.getOrganizationGroups(organizationId));
	}

	@Override
	public List<Group> getOrganizationGroups(
		long organizationId, int start, int end) {

		return _wrap(super.getOrganizationGroups(organizationId, start, end));
	}

	@Override
	public List<Group> getOrganizationGroups(
		long organizationId, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return _wrap(
			super.getOrganizationGroups(
				organizationId, start, end, orderByComparator));
	}

	@Override
	public List<Group> getOrganizationsGroups(
		List<Organization> organizations) {

		return _wrap(super.getOrganizationsGroups(organizations));
	}

	@Override
	public List<Group> getOrganizationsRelatedGroups(
		List<Organization> organizations) {

		return _wrap(super.getOrganizationsRelatedGroups(organizations));
	}

	@Override
	public List<Group> getParentGroups(long groupId) throws PortalException {
		return _wrap(super.getParentGroups(groupId));
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return _wrap((Group)super.getPersistedModel(primaryKeyObj));
	}

	@Override
	public List<Group> getRoleGroups(long roleId) {
		return _wrap(super.getRoleGroups(roleId));
	}

	@Override
	public List<Group> getRoleGroups(long roleId, int start, int end) {
		return _wrap(super.getRoleGroups(roleId, start, end));
	}

	@Override
	public List<Group> getRoleGroups(
		long roleId, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return _wrap(
			super.getRoleGroups(roleId, start, end, orderByComparator));
	}

	@Override
	public List<Group> getStagedSites() {
		return _wrap(super.getStagedSites());
	}

	@Override
	public Group getStagingGroup(long liveGroupId) throws PortalException {
		return _wrap(super.getStagingGroup(liveGroupId));
	}

	@Override
	public Group getUserGroup(long companyId, long userId)
		throws PortalException {

		return _wrap(super.getUserGroup(companyId, userId));
	}

	@Override
	public Group getUserGroupGroup(long companyId, long userGroupId)
		throws PortalException {

		return _wrap(super.getUserGroupGroup(companyId, userGroupId));
	}

	@Override
	public List<Group> getUserGroupGroups(long userGroupId) {
		return _wrap(super.getUserGroupGroups(userGroupId));
	}

	@Override
	public List<Group> getUserGroupGroups(
		long userGroupId, int start, int end) {

		return _wrap(super.getUserGroupGroups(userGroupId, start, end));
	}

	@Override
	public List<Group> getUserGroupGroups(
		long userGroupId, int start, int end,
		OrderByComparator<Group> orderByComparator) {

		return _wrap(
			super.getUserGroupGroups(
				userGroupId, start, end, orderByComparator));
	}

	@Override
	public List<Group> getUserGroups(long userId) {
		return _wrap(super.getUserGroups(userId));
	}

	@Override
	public List<Group> getUserGroups(long userId, boolean inherit)
		throws PortalException {

		return _wrap(super.getUserGroups(userId, inherit));
	}

	@Override
	public List<Group> getUserGroups(
			long userId, boolean inherit, int start, int end)
		throws PortalException {

		return _wrap(super.getUserGroups(userId, inherit, start, end));
	}

	@Override
	public List<Group> getUserGroups(long userId, int start, int end) {
		return _wrap(super.getUserGroups(userId, start, end));
	}

	@Override
	public List<Group> getUserGroups(
			long userId, int start, int end,
			OrderByComparator<Group> orderByComparator)
		throws PortalException {

		return _wrap(
			super.getUserGroups(userId, start, end, orderByComparator));
	}

	@Override
	public List<Group> getUserGroupsGroups(List<UserGroup> userGroups)
		throws PortalException {

		return _wrap(super.getUserGroupsGroups(userGroups));
	}

	@Override
	public List<Group> getUserGroupsRelatedGroups(List<UserGroup> userGroups) {
		return _wrap(super.getUserGroupsRelatedGroups(userGroups));
	}

	@Override
	public List<Group> getUserOrganizationsGroups(
			long userId, int start, int end)
		throws PortalException {

		return _wrap(super.getUserOrganizationsGroups(userId, start, end));
	}

	@Override
	public Group getUserPersonalSiteGroup(long companyId)
		throws PortalException {

		return _wrap(super.getUserPersonalSiteGroup(companyId));
	}

	@Override
	public List<Group> getUserSitesGroups(long userId) throws PortalException {
		return super.getUserSitesGroups(userId);
	}

	@Override
	public List<Group> getUserSitesGroups(
			long userId, boolean includeAdministrative)
		throws PortalException {

		return super.getUserSitesGroups(userId, includeAdministrative);
	}

	@Override
	public Group loadFetchGroup(long companyId, String groupKey) {
		return super.loadFetchGroup(companyId, groupKey);
	}

	@Override
	public Group loadGetGroup(long companyId, String groupKey)
		throws PortalException {

		return super.loadGetGroup(companyId, groupKey);
	}

	@Override
	public List<Group> search(
		long companyId, LinkedHashMap<String, Object> params, int start,
		int end) {

		return super.search(companyId, params, start, end);
	}

	@Override
	public List<Group> search(
		long companyId, long parentGroupId, String keywords,
		LinkedHashMap<String, Object> params, int start, int end) {

		return super.search(
			companyId, parentGroupId, keywords, params, start, end);
	}

	@Override
	public List<Group> search(
		long companyId, long parentGroupId, String keywords,
		LinkedHashMap<String, Object> params, int start, int end,
		OrderByComparator<Group> obc) {

		return super.search(
			companyId, parentGroupId, keywords, params, start, end, obc);
	}

	@Override
	public List<Group> search(
		long companyId, long parentGroupId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end) {

		return super.search(
			companyId, parentGroupId, name, description, params, andOperator,
			start, end);
	}

	@Override
	public List<Group> search(
		long companyId, long parentGroupId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end, OrderByComparator<Group> obc) {

		return super.search(
			companyId, parentGroupId, name, description, params, andOperator,
			start, end, obc);
	}

	@Override
	public List<Group> search(
		long companyId, long[] classNameIds, long parentGroupId,
		String keywords, LinkedHashMap<String, Object> params, int start,
		int end) {

		return super.search(
			companyId, classNameIds, parentGroupId, keywords, params, start,
			end);
	}

	@Override
	public List<Group> search(
		long companyId, long[] classNameIds, long parentGroupId,
		String keywords, LinkedHashMap<String, Object> params, int start,
		int end, OrderByComparator<Group> obc) {

		return super.search(
			companyId, classNameIds, parentGroupId, keywords, params, start,
			end, obc);
	}

	@Override
	public List<Group> search(
		long companyId, long[] classNameIds, long parentGroupId, String name,
		String description, LinkedHashMap<String, Object> params,
		boolean andOperator, int start, int end) {

		return super.search(
			companyId, classNameIds, parentGroupId, name, description, params,
			andOperator, start, end);
	}

	@Override
	public List<Group> search(
		long companyId, long[] classNameIds, long parentGroupId, String name,
		String description, LinkedHashMap<String, Object> params,
		boolean andOperator, int start, int end, OrderByComparator<Group> obc) {

		return super.search(
			companyId, classNameIds, parentGroupId, name, description, params,
			andOperator, start, end, obc);
	}

	@Override
	public List<Group> search(
		long companyId, long[] classNameIds, String keywords,
		LinkedHashMap<String, Object> params, int start, int end) {

		return super.search(
			companyId, classNameIds, keywords, params, start, end);
	}

	@Override
	public List<Group> search(
		long companyId, long[] classNameIds, String keywords,
		LinkedHashMap<String, Object> params, int start, int end,
		OrderByComparator<Group> obc) {

		return super.search(
			companyId, classNameIds, keywords, params, start, end, obc);
	}

	@Override
	public List<Group> search(
		long companyId, long[] classNameIds, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end) {

		return super.search(
			companyId, classNameIds, name, description, params, andOperator,
			start, end);
	}

	@Override
	public List<Group> search(
		long companyId, long[] classNameIds, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end, OrderByComparator<Group> obc) {

		return super.search(
			companyId, classNameIds, name, description, params, andOperator,
			start, end, obc);
	}

	@Override
	public List<Group> search(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		int start, int end) {

		return super.search(companyId, keywords, params, start, end);
	}

	@Override
	public List<Group> search(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		int start, int end, OrderByComparator<Group> obc) {

		return super.search(companyId, keywords, params, start, end, obc);
	}

	@Override
	public List<Group> search(
		long companyId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end) {

		return super.search(
			companyId, name, description, params, andOperator, start, end);
	}

	@Override
	public List<Group> search(
		long companyId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end, OrderByComparator<Group> obc) {

		return super.search(
			companyId, name, description, params, andOperator, start, end, obc);
	}

	@Override
	public Group updateGroup(
			long groupId, long parentGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, int type,
			boolean manualMembership, int membershipRestriction,
			String friendlyURL, boolean inheritContent, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		return super.updateGroup(
			groupId, parentGroupId, nameMap, descriptionMap, type,
			manualMembership, membershipRestriction, friendlyURL,
			inheritContent, active, serviceContext);
	}

	@Override
	public Group updateGroup(long groupId, String typeSettings)
		throws PortalException {

		return super.updateGroup(groupId, typeSettings);
	}

	@Override
	public Group updateSite(long groupId, boolean site) throws PortalException {
		return super.updateSite(groupId, site);
	}

	private Group _wrap(Group group) {
		if (group == null) {
			return null;
		}

		return new DepotGroupWrapper(group);
	}

	private List<Group> _wrap(List<Group> groups) {
		Stream<Group> stream = groups.stream();

		return stream.map(
			this::_wrap
		).collect(
			Collectors.toList()
		);
	}

	@Reference(target = "(bundle.symbolic.name=com.liferay.depot.service)")
	private ResourceBundleLoader _resourceBundleLoader;

	private class DepotGroupWrapper extends GroupWrapper {

		public DepotGroupWrapper(Group group) {
			super(group);
		}

		@Override
		public String getScopeDescriptiveName(ThemeDisplay themeDisplay)
			throws PortalException {

			if (getType() != GroupConstants.TYPE_DEPOT) {
				return super.getScopeDescriptiveName(themeDisplay);
			}

			if (getGroupId() == themeDisplay.getScopeGroupId()) {
				ResourceBundle resourceBundle =
					_resourceBundleLoader.loadResourceBundle(
						themeDisplay.getLocale());

				return StringUtil.appendParentheticalSuffix(
					ResourceBundleUtil.getString(
						resourceBundle, "current-asset-library"),
					HtmlUtil.escape(
						getDescriptiveName(themeDisplay.getLocale())));
			}

			return HtmlUtil.escape(
				getDescriptiveName(themeDisplay.getLocale()));
		}

		@Override
		public String getScopeLabel(ThemeDisplay themeDisplay) {
			if (getType() != GroupConstants.TYPE_DEPOT) {
				return super.getScopeLabel(themeDisplay);
			}

			if (getGroupId() == themeDisplay.getScopeGroupId()) {
				return "current-asset-library";
			}

			return "asset-library";
		}

	}

}