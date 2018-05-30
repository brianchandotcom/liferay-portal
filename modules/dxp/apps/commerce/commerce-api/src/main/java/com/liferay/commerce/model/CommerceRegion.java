/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceRegion service. Represents a row in the &quot;CommerceRegion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceRegionModel
 * @see com.liferay.commerce.model.impl.CommerceRegionImpl
 * @see com.liferay.commerce.model.impl.CommerceRegionModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.model.impl.CommerceRegionImpl")
@ProviderType
public interface CommerceRegion extends CommerceRegionModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.model.impl.CommerceRegionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceRegion, Long> COMMERCE_REGION_ID_ACCESSOR =
		new Accessor<CommerceRegion, Long>() {
			@Override
			public Long get(CommerceRegion commerceRegion) {
				return commerceRegion.getCommerceRegionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceRegion> getTypeClass() {
				return CommerceRegion.class;
			}
		};

	public CommerceCountry getCommerceCountry()
		throws com.liferay.portal.kernel.exception.PortalException;
}