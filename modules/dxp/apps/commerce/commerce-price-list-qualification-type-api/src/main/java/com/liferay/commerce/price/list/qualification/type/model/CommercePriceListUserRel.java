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

package com.liferay.commerce.price.list.qualification.type.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommercePriceListUserRel service. Represents a row in the &quot;CommercePriceListUserRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserRelModel
 * @see com.liferay.commerce.price.list.qualification.type.model.impl.CommercePriceListUserRelImpl
 * @see com.liferay.commerce.price.list.qualification.type.model.impl.CommercePriceListUserRelModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.price.list.qualification.type.model.impl.CommercePriceListUserRelImpl")
@ProviderType
public interface CommercePriceListUserRel extends CommercePriceListUserRelModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.price.list.qualification.type.model.impl.CommercePriceListUserRelImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommercePriceListUserRel, Long> COMMERCE_PRICE_LIST_USER_REL_ID_ACCESSOR =
		new Accessor<CommercePriceListUserRel, Long>() {
			@Override
			public Long get(CommercePriceListUserRel commercePriceListUserRel) {
				return commercePriceListUserRel.getCommercePriceListUserRelId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommercePriceListUserRel> getTypeClass() {
				return CommercePriceListUserRel.class;
			}
		};

	public com.liferay.commerce.model.CommercePriceListQualificationTypeRel getCommercePriceListQualificationTypeRel()
		throws com.liferay.portal.kernel.exception.PortalException;
}