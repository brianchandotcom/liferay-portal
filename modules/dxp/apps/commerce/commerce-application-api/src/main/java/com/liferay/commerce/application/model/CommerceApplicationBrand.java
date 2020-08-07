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

package com.liferay.commerce.application.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceApplicationBrand service. Represents a row in the &quot;CommerceApplicationBrand&quot; database table, with each column mapped to a property of this class.
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationBrandModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.application.model.impl.CommerceApplicationBrandImpl"
)
@ProviderType
public interface CommerceApplicationBrand
	extends CommerceApplicationBrandModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.application.model.impl.CommerceApplicationBrandImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceApplicationBrand, Long>
		COMMERCE_APPLICATION_BRAND_ID_ACCESSOR =
			new Accessor<CommerceApplicationBrand, Long>() {

				@Override
				public Long get(
					CommerceApplicationBrand commerceApplicationBrand) {

					return commerceApplicationBrand.
						getCommerceApplicationBrandId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceApplicationBrand> getTypeClass() {
					return CommerceApplicationBrand.class;
				}

			};

}