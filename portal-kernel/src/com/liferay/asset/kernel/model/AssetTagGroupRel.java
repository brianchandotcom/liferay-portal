/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AssetTagGroupRel service. Represents a row in the &quot;AssetTagGroupRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagGroupRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.asset.model.impl.AssetTagGroupRelImpl"
)
@ProviderType
public interface AssetTagGroupRel
	extends AssetTagGroupRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.asset.model.impl.AssetTagGroupRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetTagGroupRel, Long>
		ASSET_TAG_GROUP_REL_ID_ACCESSOR =
			new Accessor<AssetTagGroupRel, Long>() {

				@Override
				public Long get(AssetTagGroupRel assetTagGroupRel) {
					return assetTagGroupRel.getAssetTagGroupRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<AssetTagGroupRel> getTypeClass() {
					return AssetTagGroupRel.class;
				}

			};

}