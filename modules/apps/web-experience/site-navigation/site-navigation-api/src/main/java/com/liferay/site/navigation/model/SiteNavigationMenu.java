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

package com.liferay.site.navigation.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the SiteNavigationMenu service. Represents a row in the &quot;SiteNavigationMenu&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuModel
 * @see com.liferay.site.navigation.model.impl.SiteNavigationMenuImpl
 * @see com.liferay.site.navigation.model.impl.SiteNavigationMenuModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.site.navigation.model.impl.SiteNavigationMenuImpl")
@ProviderType
public interface SiteNavigationMenu extends SiteNavigationMenuModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.site.navigation.model.impl.SiteNavigationMenuImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SiteNavigationMenu, Long> SITE_NAVIGATION_MENU_ID_ACCESSOR =
		new Accessor<SiteNavigationMenu, Long>() {
			@Override
			public Long get(SiteNavigationMenu siteNavigationMenu) {
				return siteNavigationMenu.getSiteNavigationMenuId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<SiteNavigationMenu> getTypeClass() {
				return SiteNavigationMenu.class;
			}
		};

	public static final Accessor<SiteNavigationMenu, String> NAME_ACCESSOR = new Accessor<SiteNavigationMenu, String>() {
			@Override
			public String get(SiteNavigationMenu siteNavigationMenu) {
				return siteNavigationMenu.getName();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<SiteNavigationMenu> getTypeClass() {
				return SiteNavigationMenu.class;
			}
		};

	public java.lang.String getTypeKey();

	public boolean isPrimary();
}