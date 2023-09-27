/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the Region service. Represents a row in the &quot;Region&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see RegionModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.RegionImpl")
@ProviderType
public interface Region extends PersistedModel, RegionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.RegionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Region, Long> REGION_ID_ACCESSOR =
		new Accessor<Region, Long>() {

			@Override
			public Long get(Region region) {
				return region.getRegionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Region> getTypeClass() {
				return Region.class;
			}

		};

	public String getTitleCurrentLanguageId();

	public void setTitleCurrentLanguageId(String languageId);

}