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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the AssetEntryDisplayPageRel service. Represents a row in the &quot;AssetEntryDisplayPageRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryDisplayPageRelModel
 * @see com.liferay.asset.entry.rel.model.impl.AssetEntryDisplayPageRelImpl
 * @see com.liferay.asset.entry.rel.model.impl.AssetEntryDisplayPageRelModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.asset.entry.rel.model.impl.AssetEntryDisplayPageRelImpl")
@ProviderType
public interface AssetEntryDisplayPageRel extends AssetEntryDisplayPageRelModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.asset.entry.rel.model.impl.AssetEntryDisplayPageRelImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetEntryDisplayPageRel, Long> ASSET_ENTRY_DISPLAY_PAGE_REL_ID_ACCESSOR =
		new Accessor<AssetEntryDisplayPageRel, Long>() {
			@Override
			public Long get(AssetEntryDisplayPageRel assetEntryDisplayPageRel) {
				return assetEntryDisplayPageRel.getAssetEntryDisplayPageRelId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AssetEntryDisplayPageRel> getTypeClass() {
				return AssetEntryDisplayPageRel.class;
			}
		};
}