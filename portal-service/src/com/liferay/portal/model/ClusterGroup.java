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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the ClusterGroup service. Represents a row in the &quot;ClusterGroup&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ClusterGroupModel
 * @see com.liferay.portal.model.impl.ClusterGroupImpl
 * @see com.liferay.portal.model.impl.ClusterGroupModelImpl
 * @generated
 */
@ProviderType
public interface ClusterGroup extends ClusterGroupModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.ClusterGroupImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ClusterGroup, Long> CLUSTER_GROUP_ID_ACCESSOR = new Accessor<ClusterGroup, Long>() {
			@Override
			public Long get(ClusterGroup clusterGroup) {
				return clusterGroup.getClusterGroupId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ClusterGroup> getTypeClass() {
				return ClusterGroup.class;
			}
		};

	public java.lang.String[] getClusterNodeIdsArray();
}