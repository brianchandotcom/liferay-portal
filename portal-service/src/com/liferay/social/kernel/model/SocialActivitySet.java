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
 * The extended model interface for the SocialActivitySet service. Represents a row in the &quot;SocialActivitySet&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySetModel
 * @see com.liferay.portlet.social.model.impl.SocialActivitySetImpl
 * @see com.liferay.portlet.social.model.impl.SocialActivitySetModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.portlet.social.model.impl.SocialActivitySetImpl")
@ProviderType
public interface SocialActivitySet extends SocialActivitySetModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.social.model.impl.SocialActivitySetImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SocialActivitySet, Long> ACTIVITY_SET_ID_ACCESSOR =
		new Accessor<SocialActivitySet, Long>() {
			@Override
			public Long get(SocialActivitySet socialActivitySet) {
				return socialActivitySet.getActivitySetId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<SocialActivitySet> getTypeClass() {
				return SocialActivitySet.class;
			}
		};
}