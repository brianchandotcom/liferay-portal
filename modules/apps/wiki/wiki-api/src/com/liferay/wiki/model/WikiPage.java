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

package com.liferay.wiki.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the WikiPage service. Represents a row in the &quot;WikiPage&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageModel
 * @see com.liferay.wiki.model.impl.WikiPageImpl
 * @see com.liferay.wiki.model.impl.WikiPageModelImpl
 * @generated
 */
@ProviderType
public interface WikiPage extends WikiPageModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.wiki.model.impl.WikiPageImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.portal.kernel.repository.model.Folder addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.wiki.model.WikiPage fetchParentPage();

	public com.liferay.wiki.model.WikiPage fetchRedirectPage();

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getAttachmentsFolderId();

	public java.util.List<com.liferay.wiki.model.WikiPage> getChildPages();

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getDeletedAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getDeletedAttachmentsFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getDeletedAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.wiki.model.WikiNode getNode();

	public long getNodeAttachmentsFolderId();

	public com.liferay.wiki.model.WikiPage getParentPage()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.wiki.model.WikiPage> getParentPages();

	public com.liferay.wiki.model.WikiPage getRedirectPage()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.wiki.model.WikiPage> getViewableChildPages();

	public com.liferay.wiki.model.WikiPage getViewableParentPage();

	public java.util.List<com.liferay.wiki.model.WikiPage> getViewableParentPages();

	public void setAttachmentsFolderId(long attachmentsFolderId);
}