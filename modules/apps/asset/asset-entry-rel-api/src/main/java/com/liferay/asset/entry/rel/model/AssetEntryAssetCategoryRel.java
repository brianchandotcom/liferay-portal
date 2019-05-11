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