/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the SiteNavigationMenu service. Represents a row in the &quot;SiteNavigationMenu&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.site.navigation.model.impl.SiteNavigationMenuImpl"
)
@ProviderType
public interface SiteNavigationMenu
	extends PersistedModel, SiteNavigationMenuModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.site.navigation.model.impl.SiteNavigationMenuImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SiteNavigationMenu, Long>
		SITE_NAVIGATION_MENU_ID_ACCESSOR =
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
	public static final Accessor<SiteNavigationMenu, String> NAME_ACCESSOR =
		new Accessor<SiteNavigationMenu, String>() {

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

	public String getTypeKey();

	public boolean isPrimary();

}