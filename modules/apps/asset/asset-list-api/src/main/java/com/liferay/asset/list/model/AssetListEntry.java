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
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the AssetListEntry service. Represents a row in the &quot;AssetListEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.asset.list.model.impl.AssetListEntryImpl")
@ProviderType
public interface AssetListEntry extends AssetListEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.asset.list.model.impl.AssetListEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetListEntry, Long>
		ASSET_LIST_ENTRY_ID_ACCESSOR = new Accessor<AssetListEntry, Long>() {

			@Override
			public Long get(AssetListEntry assetListEntry) {
				return assetListEntry.getAssetListEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AssetListEntry> getTypeClass() {
				return AssetListEntry.class;
			}

		};

	public java.util.List<com.liferay.asset.kernel.model.AssetEntry>
		getAssetEntries(long segmentsEntryId);

	public java.util.List<com.liferay.asset.kernel.model.AssetEntry>
		getAssetEntries(long segmentsEntryId, int start, int end);

	public java.util.List<com.liferay.asset.kernel.model.AssetEntry>
		getAssetEntries(long[] segmentsEntryIds);

	public java.util.List<com.liferay.asset.kernel.model.AssetEntry>
		getAssetEntries(long[] segmentsEntryIds, int start, int end);

	public int getAssetEntriesCount(long segmentsEntryId);

	public int getAssetEntriesCount(long[] segmentsEntryIds);

	public com.liferay.asset.kernel.service.persistence.AssetEntryQuery
		getAssetEntryQuery(long segmentsEntryId);

	public com.liferay.asset.kernel.service.persistence.AssetEntryQuery
		getAssetEntryQuery(long[] segmentsEntryIds);

	public String getTypeLabel();

	public String getTypeSettings(long segmentsEntryId);

}