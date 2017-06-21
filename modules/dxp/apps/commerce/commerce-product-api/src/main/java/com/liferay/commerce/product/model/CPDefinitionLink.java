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
 * The extended model interface for the CPDefinitionLink service. Represents a row in the &quot;CPDefinitionLink&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPDefinitionLinkModel
 * @see com.liferay.commerce.product.model.impl.CPDefinitionLinkImpl
 * @see com.liferay.commerce.product.model.impl.CPDefinitionLinkModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.product.model.impl.CPDefinitionLinkImpl")
@ProviderType
public interface CPDefinitionLink extends CPDefinitionLinkModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.product.model.impl.CPDefinitionLinkImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPDefinitionLink, Long> CP_DEFINITION_LINK_ID_ACCESSOR =
		new Accessor<CPDefinitionLink, Long>() {
			@Override
			public Long get(CPDefinitionLink cpDefinitionLink) {
				return cpDefinitionLink.getCPDefinitionLinkId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CPDefinitionLink> getTypeClass() {
				return CPDefinitionLink.class;
			}
		};

	public CPDefinition getCPDefinitionEntry();
}