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

package com.liferay.blogs.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the BlogsStatsUser service. Represents a row in the &quot;BlogsStatsUser&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsStatsUserModel
 * @see com.liferay.blogs.model.impl.BlogsStatsUserImpl
 * @see com.liferay.blogs.model.impl.BlogsStatsUserModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.blogs.model.impl.BlogsStatsUserImpl")
@ProviderType
public interface BlogsStatsUser extends BlogsStatsUserModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.blogs.model.impl.BlogsStatsUserImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<BlogsStatsUser, Long> STATS_USER_ID_ACCESSOR = new Accessor<BlogsStatsUser, Long>() {
			@Override
			public Long get(BlogsStatsUser blogsStatsUser) {
				return blogsStatsUser.getStatsUserId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<BlogsStatsUser> getTypeClass() {
				return BlogsStatsUser.class;
			}
		};
}