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

package com.liferay.asset.list.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the AssetListEntryLocalization service. Represents a row in the &quot;AssetListEntryLocalization&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryLocalizationModel
 * @see com.liferay.asset.list.model.impl.AssetListEntryLocalizationImpl
 * @see com.liferay.asset.list.model.impl.AssetListEntryLocalizationModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.asset.list.model.impl.AssetListEntryLocalizationImpl")
@ProviderType
public interface AssetListEntryLocalization
	extends AssetListEntryLocalizationModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.asset.list.model.impl.AssetListEntryLocalizationImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetListEntryLocalization, Long> ASSET_LIST_ENTRY_LOCALIZATION_ID_ACCESSOR =
		new Accessor<AssetListEntryLocalization, Long>() {
			@Override
			public Long get(
				AssetListEntryLocalization assetListEntryLocalization) {
				return assetListEntryLocalization.getAssetListEntryLocalizationId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AssetListEntryLocalization> getTypeClass() {
				return AssetListEntryLocalization.class;
			}
		};
}