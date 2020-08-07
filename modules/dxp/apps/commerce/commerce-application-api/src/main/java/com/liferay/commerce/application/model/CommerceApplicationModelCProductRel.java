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
 * The extended model interface for the CommerceApplicationModelCProductRel service. Represents a row in the &quot;CAModelCProductRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationModelCProductRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.application.model.impl.CommerceApplicationModelCProductRelImpl"
)
@ProviderType
public interface CommerceApplicationModelCProductRel
	extends CommerceApplicationModelCProductRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.application.model.impl.CommerceApplicationModelCProductRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceApplicationModelCProductRel, Long>
		COMMERCE_APPLICATION_MODEL_C_PRODUCT_REL_ID_ACCESSOR =
			new Accessor<CommerceApplicationModelCProductRel, Long>() {

				@Override
				public Long get(
					CommerceApplicationModelCProductRel
						commerceApplicationModelCProductRel) {

					return commerceApplicationModelCProductRel.
						getCommerceApplicationModelCProductRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceApplicationModelCProductRel>
					getTypeClass() {

					return CommerceApplicationModelCProductRel.class;
				}

			};

}