/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the LayoutSEOSite service. Represents a row in the &quot;LayoutSEOSite&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOSiteModel
 * @generated
 */
@ImplementationClassName("com.liferay.layout.seo.model.impl.LayoutSEOSiteImpl")
@ProviderType
public interface LayoutSEOSite extends LayoutSEOSiteModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.layout.seo.model.impl.LayoutSEOSiteImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<LayoutSEOSite, Long>
		LAYOUT_SEO_SITE_ID_ACCESSOR = new Accessor<LayoutSEOSite, Long>() {

			@Override
			public Long get(LayoutSEOSite layoutSEOSite) {
				return layoutSEOSite.getLayoutSEOSiteId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<LayoutSEOSite> getTypeClass() {
				return LayoutSEOSite.class;
			}

		};

}