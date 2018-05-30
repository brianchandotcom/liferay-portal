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

	public CPDefinition getCPDefinition1();

	public CPDefinition getCPDefinition2();
}