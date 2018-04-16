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

package com.liferay.commerce.user.segment.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceUserSegmentCriterion service. Represents a row in the &quot;CommerceUserSegmentCriterion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceUserSegmentCriterionModel
 * @see com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentCriterionImpl
 * @see com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentCriterionModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentCriterionImpl")
@ProviderType
public interface CommerceUserSegmentCriterion
	extends CommerceUserSegmentCriterionModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentCriterionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceUserSegmentCriterion, Long> COMMERCE_USER_SEGMENT_CRITERION_ID_ACCESSOR =
		new Accessor<CommerceUserSegmentCriterion, Long>() {
			@Override
			public Long get(
				CommerceUserSegmentCriterion commerceUserSegmentCriterion) {
				return commerceUserSegmentCriterion.getCommerceUserSegmentCriterionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceUserSegmentCriterion> getTypeClass() {
				return CommerceUserSegmentCriterion.class;
			}
		};
}