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

package com.liferay.html.preview.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the HtmlPreviewEntry service. Represents a row in the &quot;HtmlPreviewEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see HtmlPreviewEntryModel
 * @see com.liferay.html.preview.model.impl.HtmlPreviewEntryImpl
 * @see com.liferay.html.preview.model.impl.HtmlPreviewEntryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.html.preview.model.impl.HtmlPreviewEntryImpl")
@ProviderType
public interface HtmlPreviewEntry extends HtmlPreviewEntryModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.html.preview.model.impl.HtmlPreviewEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<HtmlPreviewEntry, Long> HTML_PREVIEW_ENTRY_ID_ACCESSOR =
		new Accessor<HtmlPreviewEntry, Long>() {
			@Override
			public Long get(HtmlPreviewEntry htmlPreviewEntry) {
				return htmlPreviewEntry.getHtmlPreviewEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<HtmlPreviewEntry> getTypeClass() {
				return HtmlPreviewEntry.class;
			}
		};

	public java.lang.String getImagePreviewURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay);
}