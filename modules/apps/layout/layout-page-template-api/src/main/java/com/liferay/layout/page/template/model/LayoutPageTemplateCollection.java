/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the LayoutPageTemplateCollection service. Represents a row in the &quot;LayoutPageTemplateCollection&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateCollectionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.layout.page.template.model.impl.LayoutPageTemplateCollectionImpl"
)
@ProviderType
public interface LayoutPageTemplateCollection
	extends LayoutPageTemplateCollectionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateCollectionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<LayoutPageTemplateCollection, Long>
		LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_ACCESSOR =
			new Accessor<LayoutPageTemplateCollection, Long>() {

				@Override
				public Long get(
					LayoutPageTemplateCollection layoutPageTemplateCollection) {

					return layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<LayoutPageTemplateCollection> getTypeClass() {
					return LayoutPageTemplateCollection.class;
				}

			};

	public LayoutPageTemplateCollection getAncestor();

	public java.util.List<LayoutPageTemplateCollection> getAncestors();

}