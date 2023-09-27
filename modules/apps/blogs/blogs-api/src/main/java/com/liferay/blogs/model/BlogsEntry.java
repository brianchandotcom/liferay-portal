/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the BlogsEntry service. Represents a row in the &quot;BlogsEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.blogs.model.impl.BlogsEntryImpl")
@ProviderType
public interface BlogsEntry extends BlogsEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.blogs.model.impl.BlogsEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<BlogsEntry, Long> ENTRY_ID_ACCESSOR =
		new Accessor<BlogsEntry, Long>() {

			@Override
			public Long get(BlogsEntry blogsEntry) {
				return blogsEntry.getEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<BlogsEntry> getTypeClass() {
				return BlogsEntry.class;
			}

		};

	public String getCoverImageAlt()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getCoverImageURL(
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getSmallImageAlt()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getSmallImageURL(
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isVisible();

	public void setSmallImageType(String smallImageType);

}