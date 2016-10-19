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

package com.liferay.blogs.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the BlogsEntry service. Represents a row in the &quot;BlogsEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see BlogsEntryModel
 * @see com.liferay.blogs.model.impl.BlogsEntryImpl
 * @see com.liferay.blogs.model.impl.BlogsEntryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.blogs.model.impl.BlogsEntryImpl")
@ProviderType
public interface BlogsEntry extends BlogsEntryModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.blogs.model.impl.BlogsEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<BlogsEntry, Long> ENTRY_ID_ACCESSOR = new Accessor<BlogsEntry, Long>() {
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

	public java.lang.String getCoverImageURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* @deprecated As of 1.1.0, replaced by {@link
	#getSmallImageURL(ThemeDisplay)}
	*/
	@java.lang.Deprecated()
	public java.lang.String getEntryImageURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay);

	public java.lang.String getSmallImageType()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getSmallImageURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isVisible();

	public void setSmallImageType(java.lang.String smallImageType);
}