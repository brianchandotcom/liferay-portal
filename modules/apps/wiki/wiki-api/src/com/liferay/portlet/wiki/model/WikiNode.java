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

package com.liferay.portlet.wiki.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the WikiNode service. Represents a row in the &quot;WikiNode&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see WikiNodeModel
 * @see com.liferay.portlet.wiki.model.impl.WikiNodeImpl
 * @see com.liferay.portlet.wiki.model.impl.WikiNodeModelImpl
 * @generated
 */
@ProviderType
public interface WikiNode extends WikiNodeModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.wiki.model.impl.WikiNodeImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.portal.kernel.repository.model.Folder addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getAttachmentsFolderId();

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getDeletedAttachmentsFiles();
}