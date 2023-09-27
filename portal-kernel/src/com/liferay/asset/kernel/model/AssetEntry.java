/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AssetEntry service. Represents a row in the &quot;AssetEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.portlet.asset.model.impl.AssetEntryImpl")
@ProviderType
public interface AssetEntry extends AssetEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.asset.model.impl.AssetEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetEntry, Long> ENTRY_ID_ACCESSOR =
		new Accessor<AssetEntry, Long>() {

			@Override
			public Long get(AssetEntry assetEntry) {
				return assetEntry.getEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AssetEntry> getTypeClass() {
				return AssetEntry.class;
			}

		};

	public AssetRenderer<?> getAssetRenderer();

	public AssetRendererFactory<?> getAssetRendererFactory();

	public java.util.List<AssetCategory> getCategories();

	public long[] getCategoryIds();

	public String[] getTagNames();

	public java.util.List<AssetTag> getTags();

	public long getViewCount();

}