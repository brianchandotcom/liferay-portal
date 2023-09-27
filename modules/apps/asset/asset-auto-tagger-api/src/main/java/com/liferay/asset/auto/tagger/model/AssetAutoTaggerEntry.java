/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AssetAutoTaggerEntry service. Represents a row in the &quot;AssetAutoTaggerEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetAutoTaggerEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.asset.auto.tagger.model.impl.AssetAutoTaggerEntryImpl"
)
@ProviderType
public interface AssetAutoTaggerEntry
	extends AssetAutoTaggerEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.asset.auto.tagger.model.impl.AssetAutoTaggerEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetAutoTaggerEntry, Long>
		ASSET_AUTO_TAGGER_ENTRY_ID_ACCESSOR =
			new Accessor<AssetAutoTaggerEntry, Long>() {

				@Override
				public Long get(AssetAutoTaggerEntry assetAutoTaggerEntry) {
					return assetAutoTaggerEntry.getAssetAutoTaggerEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<AssetAutoTaggerEntry> getTypeClass() {
					return AssetAutoTaggerEntry.class;
				}

			};

}