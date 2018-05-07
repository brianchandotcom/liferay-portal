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

package com.liferay.asset.display.page.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the AssetDisplayPageEntry service. Represents a row in the &quot;AssetDisplayPageEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayPageEntryModel
 * @see com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryImpl
 * @see com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryImpl")
@ProviderType
public interface AssetDisplayPageEntry extends AssetDisplayPageEntryModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.asset.display.page.model.impl.AssetDisplayPageEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetDisplayPageEntry, Long> ASSET_DISPLAY_PAGE_ENTRY_ID_ACCESSOR =
		new Accessor<AssetDisplayPageEntry, Long>() {
			@Override
			public Long get(AssetDisplayPageEntry assetDisplayPageEntry) {
				return assetDisplayPageEntry.getAssetDisplayPageEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AssetDisplayPageEntry> getTypeClass() {
				return AssetDisplayPageEntry.class;
			}
		};
}