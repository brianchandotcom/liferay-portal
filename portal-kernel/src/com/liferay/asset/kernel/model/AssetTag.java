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

package com.liferay.asset.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the AssetTag service. Represents a row in the &quot;AssetTag&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagModel
 * @see com.liferay.portlet.asset.model.impl.AssetTagImpl
 * @see com.liferay.portlet.asset.model.impl.AssetTagModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.portlet.asset.model.impl.AssetTagImpl")
@ProviderType
public interface AssetTag extends AssetTagModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.asset.model.impl.AssetTagImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetTag, Long> TAG_ID_ACCESSOR = new Accessor<AssetTag, Long>() {
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

	public static final Accessor<AssetTag, String> NAME_ACCESSOR = new Accessor<AssetTag, String>() {
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