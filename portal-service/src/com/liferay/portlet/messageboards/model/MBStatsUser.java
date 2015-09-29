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

package com.liferay.portlet.messageboards.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the MBStatsUser service. Represents a row in the &quot;MBStatsUser&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see MBStatsUserModel
 * @see com.liferay.portlet.messageboards.model.impl.MBStatsUserImpl
 * @see com.liferay.portlet.messageboards.model.impl.MBStatsUserModelImpl
 * @generated
 */
@ProviderType
public interface MBStatsUser extends MBStatsUserModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.messageboards.model.impl.MBStatsUserImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MBStatsUser, Long> STATS_USER_ID_ACCESSOR = new Accessor<MBStatsUser, Long>() {
			@Override
			public Long get(MBStatsUser mbStatsUser) {
				return mbStatsUser.getStatsUserId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<MBStatsUser> getTypeClass() {
				return MBStatsUser.class;
			}
		};
}