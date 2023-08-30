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
 * The extended model interface for the CPDefinitionOptionRel service. Represents a row in the &quot;CPDefinitionOptionRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPDefinitionOptionRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CPDefinitionOptionRelImpl"
)
@ProviderType
public interface CPDefinitionOptionRel
	extends CPDefinitionOptionRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CPDefinitionOptionRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPDefinitionOptionRel, Long>
		CP_DEFINITION_OPTION_REL_ID_ACCESSOR =
			new Accessor<CPDefinitionOptionRel, Long>() {

				@Override
				public Long get(CPDefinitionOptionRel cpDefinitionOptionRel) {
					return cpDefinitionOptionRel.getCPDefinitionOptionRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CPDefinitionOptionRel> getTypeClass() {
					return CPDefinitionOptionRel.class;
				}

			};

	public CPDefinitionOptionValueRel
		fetchPreselectedCPDefinitionOptionValueRel();

	public CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<CPDefinitionOptionValueRel>
		getCPDefinitionOptionValueRels();

	public int getCPDefinitionOptionValueRelsCount();

	public CPOption getCPOption()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.util.UnicodeProperties
		getTypeSettingsUnicodeProperties();

	public boolean isPriceContributor();

	public boolean isPriceTypeDynamic();

	public boolean isPriceTypeStatic();

}