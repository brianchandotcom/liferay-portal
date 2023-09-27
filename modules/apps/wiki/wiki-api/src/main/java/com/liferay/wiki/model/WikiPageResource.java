/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the WikiPageResource service. Represents a row in the &quot;WikiPageResource&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageResourceModel
 * @generated
 */
@ImplementationClassName("com.liferay.wiki.model.impl.WikiPageResourceImpl")
@ProviderType
public interface WikiPageResource
	extends PersistedModel, WikiPageResourceModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.wiki.model.impl.WikiPageResourceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<WikiPageResource, Long>
		RESOURCE_PRIM_KEY_ACCESSOR = new Accessor<WikiPageResource, Long>() {

			@Override
			public Long get(WikiPageResource wikiPageResource) {
				return wikiPageResource.getResourcePrimKey();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<WikiPageResource> getTypeClass() {
				return WikiPageResource.class;
			}

		};

}