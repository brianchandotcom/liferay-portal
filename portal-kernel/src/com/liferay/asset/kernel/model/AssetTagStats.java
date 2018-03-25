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
 * The extended model interface for the AssetTagStats service. Represents a row in the &quot;AssetTagStats&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagStatsModel
 * @see com.liferay.portlet.asset.model.impl.AssetTagStatsImpl
 * @see com.liferay.portlet.asset.model.impl.AssetTagStatsModelImpl
 * @deprecated As of 7.0.0, replaced by {@link
com.liferay.asset.tag.stats.model.impl.AssetTagStatsImpl}
 * @generated
 */
@Deprecated
@ImplementationClassName("com.liferay.portlet.asset.model.impl.AssetTagStatsImpl")
@ProviderType
public interface AssetTagStats extends AssetTagStatsModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.asset.model.impl.AssetTagStatsImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetTagStats, Long> TAG_STATS_ID_ACCESSOR = new Accessor<AssetTagStats, Long>() {
			@Override
			public Long get(AssetTagStats assetTagStats) {
				return assetTagStats.getTagStatsId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AssetTagStats> getTypeClass() {
				return AssetTagStats.class;
			}
		};
}