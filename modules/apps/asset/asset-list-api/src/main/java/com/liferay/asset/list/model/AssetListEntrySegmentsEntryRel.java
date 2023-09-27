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
 * The extended model interface for the AssetListEntrySegmentsEntryRel service. Represents a row in the &quot;AssetListEntrySegmentsEntryRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntrySegmentsEntryRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.asset.list.model.impl.AssetListEntrySegmentsEntryRelImpl"
)
@ProviderType
public interface AssetListEntrySegmentsEntryRel
	extends AssetListEntrySegmentsEntryRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.asset.list.model.impl.AssetListEntrySegmentsEntryRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetListEntrySegmentsEntryRel, Long>
		ASSET_LIST_ENTRY_SEGMENTS_ENTRY_REL_ID_ACCESSOR =
			new Accessor<AssetListEntrySegmentsEntryRel, Long>() {

				@Override
				public Long get(
					AssetListEntrySegmentsEntryRel
						assetListEntrySegmentsEntryRel) {

					return assetListEntrySegmentsEntryRel.
						getAssetListEntrySegmentsEntryRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<AssetListEntrySegmentsEntryRel> getTypeClass() {
					return AssetListEntrySegmentsEntryRel.class;
				}

			};

}