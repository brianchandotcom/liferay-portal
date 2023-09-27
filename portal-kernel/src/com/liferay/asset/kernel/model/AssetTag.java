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
 * The extended model interface for the AssetTag service. Represents a row in the &quot;AssetTag&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagModel
 * @generated
 */
@ImplementationClassName("com.liferay.portlet.asset.model.impl.AssetTagImpl")
@ProviderType
public interface AssetTag extends AssetTagModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.asset.model.impl.AssetTagImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetTag, Long> TAG_ID_ACCESSOR =
		new Accessor<AssetTag, Long>() {

			@Override
			public Long get(AssetTag assetTag) {
				return assetTag.getTagId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AssetTag> getTypeClass() {
				return AssetTag.class;
			}

		};
	public static final Accessor<AssetTag, String> NAME_ACCESSOR =
		new Accessor<AssetTag, String>() {

			@Override
			public String get(AssetTag assetTag) {
				return assetTag.getName();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<AssetTag> getTypeClass() {
				return AssetTag.class;
			}

		};

}