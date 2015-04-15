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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the Organization service. Represents a row in the &quot;Organization_&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see OrganizationModel
 * @see com.liferay.portal.model.impl.OrganizationImpl
 * @see com.liferay.portal.model.impl.OrganizationModelImpl
 * @generated
 */
@ProviderType
public interface Organization extends OrganizationModel, PersistedModel,
	TreeModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.OrganizationImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Organization, String> NAME_ACCESSOR = new Accessor<Organization, String>() {
			@Override
			public String get(Organization organization) {
				return organization.getName();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<Organization> getTypeClass() {
				return Organization.class;
			}
		};

	public com.liferay.portal.model.Address getAddress();

	public java.util.List<com.liferay.portal.model.Address> getAddresses();

	public long[] getAncestorOrganizationIds()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.model.Organization> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String[] getChildrenTypes();

	public java.util.List<com.liferay.portal.model.Organization> getDescendants();

	public com.liferay.portal.model.Group getGroup();

	public long getGroupId();

	public int getLayoutsPageCount();

	public com.liferay.portal.model.Organization getParentOrganization()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getParentOrganizationName();

	public javax.portlet.PortletPreferences getPreferences();

	public java.util.Set<java.lang.String> getReminderQueryQuestions(
		java.util.Locale locale);

	public java.util.Set<java.lang.String> getReminderQueryQuestions(
		java.lang.String languageId);

	public java.util.List<com.liferay.portal.model.Organization> getSuborganizations();

	public int getSuborganizationsSize();

	public int getTypeOrder();

	public boolean hasLayouts();

	public boolean hasSuborganizations();

	public boolean isParentable();

	public boolean isRoot();
}