/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the AssetLink service. Represents a row in the &quot;AssetLink&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetLinkModel
 * @see com.liferay.portlet.asset.model.impl.AssetLinkImpl
 * @see com.liferay.portlet.asset.model.impl.AssetLinkModelImpl
 * @generated
 */
public interface AssetLink extends AssetLinkModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.asset.model.impl.AssetLinkImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetLink, Long> LINK_ID_ACCESSOR = new Accessor<AssetLink, Long>() {
			public Long get(AssetLink assetLink) {
				return assetLink.getLinkId();
			}
		};

	public static final Accessor<AssetLink, Long> ENTRY_ID2_ACCESSOR = new Accessor<AssetLink, Long>() {
			public Long get(AssetLink assetLink) {
				return assetLink.getEntryId2();
			}
		};
}