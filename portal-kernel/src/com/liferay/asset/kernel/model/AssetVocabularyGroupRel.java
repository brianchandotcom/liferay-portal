/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AssetVocabularyGroupRel service. Represents a row in the &quot;AssetVocabularyGroupRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyGroupRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.asset.model.impl.AssetVocabularyGroupRelImpl"
)
@ProviderType
public interface AssetVocabularyGroupRel
	extends AssetVocabularyGroupRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.asset.model.impl.AssetVocabularyGroupRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetVocabularyGroupRel, Long>
		ASSET_VOCABULARY_GROUP_REL_ID_ACCESSOR =
			new Accessor<AssetVocabularyGroupRel, Long>() {

				@Override
				public Long get(
					AssetVocabularyGroupRel assetVocabularyGroupRel) {

					return assetVocabularyGroupRel.
						getAssetVocabularyGroupRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<AssetVocabularyGroupRel> getTypeClass() {
					return AssetVocabularyGroupRel.class;
				}

			};

}
// LIFERAY-SERVICE-BUILDER-HASH:-1848028148