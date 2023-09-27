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
 * The extended model interface for the SiteNavigationMenuItem service. Represents a row in the &quot;SiteNavigationMenuItem&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuItemModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.site.navigation.model.impl.SiteNavigationMenuItemImpl"
)
@ProviderType
public interface SiteNavigationMenuItem
	extends PersistedModel, SiteNavigationMenuItemModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.site.navigation.model.impl.SiteNavigationMenuItemImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SiteNavigationMenuItem, Long>
		SITE_NAVIGATION_MENU_ITEM_ID_ACCESSOR =
			new Accessor<SiteNavigationMenuItem, Long>() {

				@Override
				public Long get(SiteNavigationMenuItem siteNavigationMenuItem) {
					return siteNavigationMenuItem.getSiteNavigationMenuItemId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<SiteNavigationMenuItem> getTypeClass() {
					return SiteNavigationMenuItem.class;
				}

			};

}