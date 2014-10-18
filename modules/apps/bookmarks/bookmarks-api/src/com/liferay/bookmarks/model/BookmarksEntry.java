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

package com.liferay.bookmarks.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.PermissionedModel;
import com.liferay.portal.model.TreeModel;

/**
 * The extended model interface for the BookmarksEntry service. Represents a row in the &quot;BookmarksEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksEntryModel
 * @see com.liferay.bookmarks.model.impl.BookmarksEntryImpl
 * @see com.liferay.bookmarks.model.impl.BookmarksEntryModelImpl
 * @generated
 */
@ProviderType
public interface BookmarksEntry extends BookmarksEntryModel, PermissionedModel,
	TreeModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.bookmarks.model.impl.BookmarksEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public java.lang.String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.bookmarks.model.BookmarksFolder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException;
}