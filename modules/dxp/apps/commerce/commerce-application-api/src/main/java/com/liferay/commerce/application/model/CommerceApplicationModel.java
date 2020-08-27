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
 * The extended model interface for the CommerceApplicationModel service. Represents a row in the &quot;CommerceApplicationModel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Luca Pellizzon
 * @see CommerceApplicationModelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.application.model.impl.CommerceApplicationModelImpl"
)
@ProviderType
public interface CommerceApplicationModel
	extends CommerceApplicationModelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.application.model.impl.CommerceApplicationModelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceApplicationModel, Long>
		COMMERCE_APPLICATION_MODEL_ID_ACCESSOR =
			new Accessor<CommerceApplicationModel, Long>() {

				@Override
				public Long get(
					CommerceApplicationModel commerceApplicationModel) {

					return commerceApplicationModel.
						getCommerceApplicationModelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceApplicationModel> getTypeClass() {
					return CommerceApplicationModel.class;
				}

			};

}