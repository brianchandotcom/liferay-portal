/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.entry.rel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AssetEntryAssetCategoryRel service. Represents a row in the &quot;AssetEntryAssetCategoryRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryAssetCategoryRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.asset.entry.rel.model.impl.AssetEntryAssetCategoryRelImpl"
)
@ProviderType
public interface AssetEntryAssetCategoryRel
	extends AssetEntryAssetCategoryRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.asset.entry.rel.model.impl.AssetEntryAssetCategoryRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetEntryAssetCategoryRel, Long>
		ASSET_ENTRY_ASSET_CATEGORY_REL_ID_ACCESSOR =
			new Accessor<AssetEntryAssetCategoryRel, Long>() {

				@Override
				public Long get(
					AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {

					return assetEntryAssetCategoryRel.
						getAssetEntryAssetCategoryRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<AssetEntryAssetCategoryRel> getTypeClass() {
					return AssetEntryAssetCategoryRel.class;
				}

			};

}