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

package com.liferay.social.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the SocialActivityLimit service. Represents a row in the &quot;SocialActivityLimit&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityLimitModel
 * @see com.liferay.portlet.social.model.impl.SocialActivityLimitImpl
 * @see com.liferay.portlet.social.model.impl.SocialActivityLimitModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.portlet.social.model.impl.SocialActivityLimitImpl")
@ProviderType
public interface SocialActivityLimit extends SocialActivityLimitModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.social.model.impl.SocialActivityLimitImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SocialActivityLimit, Long> ACTIVITY_LIMIT_ID_ACCESSOR =
		new Accessor<SocialActivityLimit, Long>() {
			@Override
			public Long get(SocialActivityLimit socialActivityLimit) {
				return socialActivityLimit.getActivityLimitId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<SocialActivityLimit> getTypeClass() {
				return SocialActivityLimit.class;
			}
		};

	public int getCount();

	public int getCount(int limitPeriod);

	public void setCount(int limitPeriod, int count);
}