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
 * The extended model interface for the CPDisplayLayout service. Represents a row in the &quot;CPDisplayLayout&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPDisplayLayoutModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CPDisplayLayoutImpl"
)
@ProviderType
public interface CPDisplayLayout extends CPDisplayLayoutModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CPDisplayLayoutImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPDisplayLayout, Long>
		CP_DISPLAY_LAYOUT_ID_ACCESSOR = new Accessor<CPDisplayLayout, Long>() {

			@Override
			public Long get(CPDisplayLayout cpDisplayLayout) {
				return cpDisplayLayout.getCPDisplayLayoutId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CPDisplayLayout> getTypeClass() {
				return CPDisplayLayout.class;
			}

		};

	public com.liferay.asset.kernel.model.AssetCategory fetchAssetCategory();

	public CPDefinition fetchCPDefinition();

	public com.liferay.portal.kernel.model.Layout fetchLayout();

}