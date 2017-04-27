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
 * The extended model interface for the CPDefinitionOptionValueRel service. Represents a row in the &quot;CPDefinitionOptionValueRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPDefinitionOptionValueRelModel
 * @see com.liferay.commerce.product.model.impl.CPDefinitionOptionValueRelImpl
 * @see com.liferay.commerce.product.model.impl.CPDefinitionOptionValueRelModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.product.model.impl.CPDefinitionOptionValueRelImpl")
@ProviderType
public interface CPDefinitionOptionValueRel
	extends CPDefinitionOptionValueRelModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.product.model.impl.CPDefinitionOptionValueRelImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPDefinitionOptionValueRel, Long> CP_DEFINITION_OPTION_VALUE_REL_ID_ACCESSOR =
		new Accessor<CPDefinitionOptionValueRel, Long>() {
			@Override
			public Long get(
				CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {
				return cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CPDefinitionOptionValueRel> getTypeClass() {
				return CPDefinitionOptionValueRel.class;
			}
		};
}