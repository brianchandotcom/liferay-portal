/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CPInstanceUnitOfMeasure service. Represents a row in the &quot;CPInstanceUOM&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPInstanceUnitOfMeasureModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CPInstanceUnitOfMeasureImpl"
)
@ProviderType
public interface CPInstanceUnitOfMeasure
	extends CPInstanceUnitOfMeasureModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CPInstanceUnitOfMeasureImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPInstanceUnitOfMeasure, Long>
		CP_INSTANCE_UNIT_OF_MEASURE_ID_ACCESSOR =
			new Accessor<CPInstanceUnitOfMeasure, Long>() {

				@Override
				public Long get(
					CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure) {

					return cpInstanceUnitOfMeasure.
						getCPInstanceUnitOfMeasureId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CPInstanceUnitOfMeasure> getTypeClass() {
					return CPInstanceUnitOfMeasure.class;
				}

			};

}