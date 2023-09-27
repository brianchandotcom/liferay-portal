/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AssetListEntryUsage service. Represents a row in the &quot;AssetListEntryUsage&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryUsageModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.asset.list.model.impl.AssetListEntryUsageImpl"
)
@ProviderType
public interface AssetListEntryUsage
	extends AssetListEntryUsageModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.asset.list.model.impl.AssetListEntryUsageImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetListEntryUsage, Long>
		ASSET_LIST_ENTRY_USAGE_ID_ACCESSOR =
			new Accessor<AssetListEntryUsage, Long>() {

				@Override
				public Long get(AssetListEntryUsage assetListEntryUsage) {
					return assetListEntryUsage.getAssetListEntryUsageId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<AssetListEntryUsage> getTypeClass() {
					return AssetListEntryUsage.class;
				}

			};

}