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
 * The extended model interface for the AssetLink service. Represents a row in the &quot;AssetLink&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetLinkModel
 * @see com.liferay.portlet.asset.model.impl.AssetLinkImpl
 * @see com.liferay.portlet.asset.model.impl.AssetLinkModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.portlet.asset.model.impl.AssetLinkImpl")
@ProviderType
public interface AssetLink extends AssetLinkModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.asset.model.impl.AssetLinkImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetLink, Long> LINK_ID_ACCESSOR = new Accessor<AssetLink, Long>() {
			@Override
			public Long get(AssetLink assetLink) {
				return assetLink.getLinkId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AssetLink> getTypeClass() {
				return AssetLink.class;
			}
		};

	public static final Accessor<AssetLink, Long> ENTRY_ID2_ACCESSOR = new Accessor<AssetLink, Long>() {
			@Override
			public Long get(AssetLink assetLink) {
				return assetLink.getEntryId2();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AssetLink> getTypeClass() {
				return AssetLink.class;
			}
		};
}