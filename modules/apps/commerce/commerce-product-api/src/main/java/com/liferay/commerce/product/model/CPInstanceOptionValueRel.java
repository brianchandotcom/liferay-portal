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
 * The extended model interface for the CPInstanceOptionValueRel service. Represents a row in the &quot;CPInstanceOptionValueRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPInstanceOptionValueRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelImpl"
)
@ProviderType
public interface CPInstanceOptionValueRel
	extends CPInstanceOptionValueRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPInstanceOptionValueRel, Long>
		CP_INSTANCE_OPTION_VALUE_REL_ID_ACCESSOR =
			new Accessor<CPInstanceOptionValueRel, Long>() {

				@Override
				public Long get(
					CPInstanceOptionValueRel cpInstanceOptionValueRel) {

					return cpInstanceOptionValueRel.
						getCPInstanceOptionValueRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CPInstanceOptionValueRel> getTypeClass() {
					return CPInstanceOptionValueRel.class;
				}

			};

}