/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.PasswordPolicyRel;
import com.liferay.portal.service.AddressLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.service.PasswordPolicyRelLocalServiceUtil;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class OrganizationImpl extends OrganizationBaseImpl {

	public static String[] getChildrenTypes(String type) {
		return PropsUtil.getArray(
			PropsKeys.ORGANIZATIONS_CHILDREN_TYPES, new Filter(type));
	}

	public static String[] getParentTypes(String type) {
		String[] types = PropsUtil.getArray(
			PropsKeys.ORGANIZATIONS_TYPES, new Filter(type));

		List<String> parentTypes = new ArrayList<String>();

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
		else {
			return false;
		}
	}

	public static boolean isRootable(String type) {
		return GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.ORGANIZATIONS_ROOTABLE, new Filter(type)));
	}

	public OrganizationImpl() {
	}

	public String buildTreePath() throws PortalException, SystemException {
		StringBundler sb = new StringBundler();

		buildTreePath(sb, this);

		return sb.toString();
	}

	public Address getAddress() {
		Address address = null;

		try {
			List<Address> addresses = getAddresses();

			if (addresses.size() > 0) {
				address = addresses.get(0);
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		if (address == null) {
			address = new AddressImpl();
		}

		return address;
	}

	public List<Address> getAddresses() throws SystemException {
		return AddressLocalServiceUtil.getAddresses(
			getCompanyId(), Organization.class.getName(), getOrganizationId());
	}

	public List<Organization> getAncestors()
		throws PortalException, SystemException {

		List<Organization> ancestors = new ArrayList<Organization>();

		Organization organization = this;

		while (!organization.isRoot()) {
			organization = organization.getParentOrganization();

			ancestors.add(organization);
		}

		return ancestors;
	}

	public String[] getChildrenTypes() {
		return getChildrenTypes(getType());
	}

	public List<Organization> getDescendants() throws SystemException {
		List<Organization> descendants = new UniqueList<Organization>();

		for (Organization suborganization : getSuborganizations()) {
			descendants.add(suborganization);
			descendants.addAll(suborganization.getDescendants());
		}

		return descendants;
	}

	public Group getGroup() {
		if (getOrganizationId() > 0) {
			try {
				return GroupLocalServiceUtil.getOrganizationGroup(
					getCompanyId(), getOrganizationId());
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return new GroupImpl();
	}

	public long getGroupId() {
		Group group = getGroup();

		return group.getGroupId();
	}

	public long getLogoId() {
		long logoId = 0;

		try {
			Group group = getGroup();

			LayoutSet publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				group.getGroupId(), false);

			logoId = publicLayoutSet.getLogoId();

			if (logoId == 0) {
				LayoutSet privateLayoutSet =
					LayoutSetLocalServiceUtil.getLayoutSet(
						group.getGroupId(), true);

				logoId = privateLayoutSet.getLogoId();
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return logoId;
	}

	public Organization getParentOrganization()
		throws PortalException, SystemException {

		if (getParentOrganizationId() ==
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			return null;
		}

		return OrganizationLocalServiceUtil.getOrganization(
			getParentOrganizationId());
	}

	public PasswordPolicy getPasswordPolicy()
		throws PortalException, SystemException {

		PasswordPolicyRel passwordPolicyRel =
			PasswordPolicyRelLocalServiceUtil.fetchPasswordPolicyRel(
				Organization.class.getName(), getOrganizationId());

		if (passwordPolicyRel == null) {
			return null;
		}

		PasswordPolicy passwordPolicy =
			PasswordPolicyLocalServiceUtil.getPasswordPolicy(
				passwordPolicyRel.getPasswordPolicyId());

		return passwordPolicy;
	}

	public PortletPreferences getPreferences() throws SystemException {
		long companyId = getCompanyId();
		long ownerId = getOrganizationId();
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_ORGANIZATION;

		return PortalPreferencesLocalServiceUtil.getPreferences(
			companyId, ownerId, ownerType);
	}

	public int getPrivateLayoutsPageCount() {
		try {
			Group group = getGroup();

			if (group == null) {
				return 0;
			}
			else {
				return group.getPrivateLayoutsPageCount();
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return 0;
	}

	public int getPublicLayoutsPageCount() {
		try {
			Group group = getGroup();

			if (group == null) {
				return 0;
			}
			else {
				return group.getPublicLayoutsPageCount();
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return 0;
	}

	public Set<String> getReminderQueryQuestions(Locale locale)
		throws SystemException {

		return getReminderQueryQuestions(LanguageUtil.getLanguageId(locale));
	}

	public Set<String> getReminderQueryQuestions(String languageId)
		throws SystemException {

		PortletPreferences preferences = getPreferences();

		String[] questions = StringUtil.splitLines(
			LocalizationUtil.getPreferencesValue(
				preferences, "reminderQueries", languageId, false));

		return SetUtil.fromArray(questions);
	}

	public List<Organization> getSuborganizations() throws SystemException {
		return OrganizationLocalServiceUtil.getSuborganizations(
			getCompanyId(), getOrganizationId());
	}

	public int getSuborganizationsSize() throws SystemException {
		return OrganizationLocalServiceUtil.getSuborganizationsCount(
			getCompanyId(), getOrganizationId());
	}

	public int getTypeOrder() {
		String[] types = PropsValues.ORGANIZATIONS_TYPES;

		for (int i = 0; i < types.length; i++) {
			String type = types[i];

			if (type.equals(getType())) {
				return i + 1;
			}
		}

		return 0;
	}

	public boolean hasPrivateLayouts() {
		if (getPrivateLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasPublicLayouts() {
		if (getPublicLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasSuborganizations() throws SystemException {
		if (getSuborganizationsSize() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isParentable() {
		return isParentable(getType());
	}

	public boolean isRoot() {
		if (getParentOrganizationId() ==
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			return true;
		}

		return false;
	}

	protected void buildTreePath(StringBundler sb, Organization organization)
		throws PortalException, SystemException {

		if (organization == null) {
			sb.append(StringPool.SLASH);
		}
		else {
			buildTreePath(sb, organization.getParentOrganization());

			sb.append(organization.getOrganizationId());
			sb.append(StringPool.SLASH);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(Organization.class);

}