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

package com.liferay.commerce.product.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CPRuleUserSegmentRel service. Represents a row in the &quot;CPRuleUserSegmentRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPRuleUserSegmentRelModel
 * @see com.liferay.commerce.product.model.impl.CPRuleUserSegmentRelImpl
 * @see com.liferay.commerce.product.model.impl.CPRuleUserSegmentRelModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.product.model.impl.CPRuleUserSegmentRelImpl")
@ProviderType
public interface CPRuleUserSegmentRel extends CPRuleUserSegmentRelModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.product.model.impl.CPRuleUserSegmentRelImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPRuleUserSegmentRel, Long> CP_RULE_USER_SEGMENT_REL_ID_ACCESSOR =
		new Accessor<CPRuleUserSegmentRel, Long>() {
			@Override
			public Long get(CPRuleUserSegmentRel cpRuleUserSegmentRel) {
				return cpRuleUserSegmentRel.getCPRuleUserSegmentRelId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CPRuleUserSegmentRel> getTypeClass() {
				return CPRuleUserSegmentRel.class;
			}
		};

	public com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry getCommerceUserSegmentEntry()
		throws com.liferay.portal.kernel.exception.PortalException;
}