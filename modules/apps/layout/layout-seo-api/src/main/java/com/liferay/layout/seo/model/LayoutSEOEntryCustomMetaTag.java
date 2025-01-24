/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the LayoutSEOEntryCustomMetaTag service. Represents a row in the &quot;LayoutSEOEntryCustomMetaTag&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOEntryCustomMetaTagModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.layout.seo.model.impl.LayoutSEOEntryCustomMetaTagImpl"
)
@ProviderType
public interface LayoutSEOEntryCustomMetaTag
	extends LayoutSEOEntryCustomMetaTagModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.layout.seo.model.impl.LayoutSEOEntryCustomMetaTagImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<LayoutSEOEntryCustomMetaTag, Long>
		LAYOUT_SEO_ENTRY_CUSTOM_META_TAG_ID_ACCESSOR =
			new Accessor<LayoutSEOEntryCustomMetaTag, Long>() {

				@Override
				public Long get(
					LayoutSEOEntryCustomMetaTag layoutSEOEntryCustomMetaTag) {

					return layoutSEOEntryCustomMetaTag.
						getLayoutSEOEntryCustomMetaTagId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<LayoutSEOEntryCustomMetaTag> getTypeClass() {
					return LayoutSEOEntryCustomMetaTag.class;
				}

			};

}