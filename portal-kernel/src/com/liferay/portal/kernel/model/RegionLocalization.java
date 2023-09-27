/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the RegionLocalization service. Represents a row in the &quot;RegionLocalization&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see RegionLocalizationModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.RegionLocalizationImpl")
@ProviderType
public interface RegionLocalization extends RegionLocalizationModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.RegionLocalizationImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<RegionLocalization, Long>
		REGION_LOCALIZATION_ID_ACCESSOR =
			new Accessor<RegionLocalization, Long>() {

				@Override
				public Long get(RegionLocalization regionLocalization) {
					return regionLocalization.getRegionLocalizationId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<RegionLocalization> getTypeClass() {
					return RegionLocalization.class;
				}

			};

}