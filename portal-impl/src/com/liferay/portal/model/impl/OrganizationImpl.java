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

package com.liferay.portal.model.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Marco Leo
 */
public class OrganizationImpl extends OrganizationBaseImpl {

	public static String[] getChildrenTypes(String type) {
		return OrganizationLocalServiceUtil.getChildrenTypes(type);
	}

	public static String[] getParentTypes(String type) {
		String[] types = OrganizationLocalServiceUtil.getTypes();

		List<String> parentTypes = new ArrayList<>();

		for (String curType : types) {
			if (ArrayUtil.contains(getChildrenTypes(curType), type)) {
				parentTypes.add(curType);
			}
		}

		return parentTypes.toArray(new String[parentTypes.size()]);
	}

	public static boolean isParentable(String type) {
		String[] childrenTypes = getChildrenTypes(type);

		if (childrenTypes.length > 0) {
			return true;
		}

		return false;
	}

	public static boolean isRootable(String type) {
		return OrganizationLocalServiceUtil.isRootable(type);
	}

	@Override
	public Address getAddress() {
		Address address = null;

		try {
			List<Address> addresses = getAddresses();

			if (!addresses.isEmpty()) {
				address = addresses.get(0);
			}
		}
		catch (Exception e) {
			_log.error("Unable to get address", e);
		}

		if (address == null) {
			address = new AddressImpl();
		}

		return address;
	}

	@Override
	public List<Address> getAddresses() {
		return AddressLocalServiceUtil.getAddresses(
			getCompanyId(), Organization.class.getName(), getOrganizationId());
	}

	@Override
	public long[] getAncestorOrganizationIds() throws PortalException {
		if (Validator.isNull(getTreePath())) {
			List<Organization> ancestorOrganizations = getAncestors();

			long[] ancestorOrganizationIds =
				new long[ancestorOrganizations.size()];

			for (int i = 0; i < ancestorOrganizations.size(); i++) {
				Organization organization = ancestorOrganizations.get(i);

				ancestorOrganizationIds[ancestorOrganizations.size() - i - 1] =
					organization.getOrganizationId();
			}

			return ancestorOrganizationIds;
		}

		long[] primaryKeys = StringUtil.split(
			getTreePath(), StringPool.SLASH, 0L);

		if (primaryKeys.length <= 2) {
			return new long[0];
		}

		long[] ancestorOrganizationIds = new long[primaryKeys.length - 2];

		System.arraycopy(
			primaryKeys, 1, ancestorOrganizationIds, 0, primaryKeys.length - 2);

		return ancestorOrganizationIds;
	}

	@Override
	public List<Organization> getAncestors() throws PortalException {
		List<Organization> ancestors = new ArrayList<>();

		Organization organization = this;

		while (!organization.isRoot()) {
			organization = organization.getParentOrganization();

			ancestors.add(organization);
		}

		return ancestors;
	}

	@Override
	public String[] getChildrenTypes() {
		return getChildrenTypes(getType());
	}

	@Override
	public List<Organization> getDescendants() {
		return OrganizationLocalServiceUtil.getOrganizations(
			getCompanyId(), getTreePath().concat("_%"));
	}

	@Override
	public Group getGroup() {
		if (getOrganizationId() > 0) {
			try {
				return GroupLocalServiceUtil.getOrganizationGroup(
					getCompanyId(), getOrganizationId());
			}
			catch (Exception e) {
				_log.error("Unable to get organization group", e);
			}
		}

		return new GroupImpl();
	}

	@Override
	public long getGroupId() {
		Group group = getGroup();

		return group.getGroupId();
	}

	@Override
	public Organization getParentOrganization() throws PortalException {
		if (getParentOrganizationId() ==
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			return null;
		}

		return OrganizationLocalServiceUtil.getOrganization(
			getParentOrganizationId());
	}

	@Override
	public String getParentOrganizationName() {
		if (getParentOrganizationId() ==
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			return StringPool.BLANK;
		}

		Organization parentOrganization =
			OrganizationLocalServiceUtil.fetchOrganization(
				getParentOrganizationId());

		if (parentOrganization != null) {
			return parentOrganization.getName();
		}

		return StringPool.BLANK;
	}

	@Override
	public PortletPreferences getPreferences() {
		long ownerId = getOrganizationId();
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_ORGANIZATION;

		return PortalPreferencesLocalServiceUtil.getPreferences(
			ownerId, ownerType);
	}

	@Override
	public int getPrivateLayoutsPageCount() {
		try {
			Group group = getGroup();

			if (group == null) {
				return 0;
			}

			return group.getPrivateLayoutsPageCount();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return 0;
	}

	@Override
	public int getPublicLayoutsPageCount() {
		try {
			Group group = getGroup();

			if (group == null) {
				return 0;
			}

			return group.getPublicLayoutsPageCount();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return 0;
	}

	@Override
	public Set<String> getReminderQueryQuestions(Locale locale) {
		return getReminderQueryQuestions(LanguageUtil.getLanguageId(locale));
	}

	@Override
	public Set<String> getReminderQueryQuestions(String languageId) {
		PortletPreferences preferences = getPreferences();

		String[] questions = StringUtil.splitLines(
			LocalizationUtil.getPreferencesValue(
				preferences, "reminderQueries", languageId, false));

		return SetUtil.fromArray(questions);
	}

	@Override
	public List<Organization> getSuborganizations() {
		return OrganizationLocalServiceUtil.getSuborganizations(
			getCompanyId(), getOrganizationId());
	}

	@Override
	public int getSuborganizationsSize() {
		return OrganizationLocalServiceUtil.getSuborganizationsCount(
			getCompanyId(), getOrganizationId());
	}

	@Override
	public int getTypeOrder() {
		String[] types = OrganizationLocalServiceUtil.getTypes();

		for (int i = 0; i < types.length; i++) {
			String type = types[i];

			if (type.equals(getType())) {
				return i + 1;
			}
		}

		return 0;
	}

	@Override
	public boolean hasPrivateLayouts() {
		if (getPrivateLayoutsPageCount() > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasPublicLayouts() {
		if (getPublicLayoutsPageCount() > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasSuborganizations() {
		if (getSuborganizationsSize() > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isParentable() {
		return isParentable(getType());
	}

	@Override
	public boolean isRoot() {
		if (getParentOrganizationId() ==
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationImpl.class);

}