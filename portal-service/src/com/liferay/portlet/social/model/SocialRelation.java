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

package com.liferay.portlet.social.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the SocialRelation service. Represents a row in the &quot;SocialRelation&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SocialRelationModel
 * @see com.liferay.portlet.social.model.impl.SocialRelationImpl
 * @see com.liferay.portlet.social.model.impl.SocialRelationModelImpl
 * @generated
 */
@ProviderType
public interface SocialRelation extends SocialRelationModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.social.model.impl.SocialRelationImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SocialRelation, Long> RELATION_ID_ACCESSOR = new Accessor<SocialRelation, Long>() {
			@Override
			public Long get(SocialRelation socialRelation) {
				return socialRelation.getRelationId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<SocialRelation> getTypeClass() {
				return SocialRelation.class;
			}
		};
}