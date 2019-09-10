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

package com.liferay.layout.seo.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the LayoutSEOEntry service. Represents a row in the &quot;LayoutSEOEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.layout.seo.model.impl.LayoutSEOEntryImpl")
@ProviderType
public interface LayoutSEOEntry extends LayoutSEOEntryModel, PersistedModel {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.layout.seo.model.impl.LayoutSEOEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<LayoutSEOEntry, Long>
		LAYOUT_SEO_ENTRY_ID_ACCESSOR = new Accessor<LayoutSEOEntry, Long>() {

			@Override
			public Long get(LayoutSEOEntry layoutSEOEntry) {
				return layoutSEOEntry.getLayoutSEOEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<LayoutSEOEntry> getTypeClass() {
				return LayoutSEOEntry.class;
			}

		};

}