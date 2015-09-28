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
 * The extended model interface for the LayoutSetBranch service. Represents a row in the &quot;LayoutSetBranch&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetBranchModel
 * @see com.liferay.portal.model.impl.LayoutSetBranchImpl
 * @see com.liferay.portal.model.impl.LayoutSetBranchModelImpl
 * @generated
 */
@ProviderType
public interface LayoutSetBranch extends LayoutSetBranchModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.LayoutSetBranchImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<LayoutSetBranch, Long> LAYOUT_SET_BRANCH_ID_ACCESSOR =
		new Accessor<LayoutSetBranch, Long>() {
			@Override
			public Long get(LayoutSetBranch layoutSetBranch) {
				return layoutSetBranch.getLayoutSetBranchId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<LayoutSetBranch> getTypeClass() {
				return LayoutSetBranch.class;
			}
		};

	public com.liferay.portal.model.ColorScheme getColorScheme();

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.model.LayoutSet getLayoutSet();

	public long getLiveLogoId();

	public boolean getLogo();

	public com.liferay.portal.kernel.util.UnicodeProperties getSettingsProperties();

	public java.lang.String getSettingsProperty(java.lang.String key);

	public com.liferay.portal.model.Theme getTheme();

	public java.lang.String getThemeSetting(java.lang.String key,
		java.lang.String device);

	public com.liferay.portal.model.ColorScheme getWapColorScheme();

	public com.liferay.portal.model.Theme getWapTheme();

	public boolean isLayoutSetPrototypeLinkActive();

	public boolean isLogo();

	public void setSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties settingsProperties);
}