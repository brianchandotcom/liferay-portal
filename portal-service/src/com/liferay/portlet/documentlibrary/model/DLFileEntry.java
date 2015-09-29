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

package com.liferay.portlet.documentlibrary.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.model.TreeModel;

/**
 * The extended model interface for the DLFileEntry service. Represents a row in the &quot;DLFileEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryModel
 * @see com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl
 * @see com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl
 * @generated
 */
@ProviderType
public interface DLFileEntry extends DLFileEntryModel, PersistedModel, TreeModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DLFileEntry, Long> FILE_ENTRY_ID_ACCESSOR = new Accessor<DLFileEntry, Long>() {
			@Override
			public Long get(DLFileEntry dlFileEntry) {
				return dlFileEntry.getFileEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DLFileEntry> getTypeClass() {
				return DLFileEntry.class;
			}
		};

	@Override
	public java.lang.String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.io.InputStream getContentStream()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.io.InputStream getContentStream(java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getDataRepositoryId();

	public java.util.Map<java.lang.String, com.liferay.portlet.dynamicdatamapping.DDMFormValues> getDDMFormValuesMap(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.documentlibrary.model.DLFileEntryType getDLFileEntryType()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.util.UnicodeProperties getExtraSettingsProperties();

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts();

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion(
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> getFileVersions(
		int status);

	public int getFileVersionsCount(int status);

	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getIcon();

	public java.lang.String getIconCssClass();

	public com.liferay.portlet.documentlibrary.model.DLFileVersion getLatestFileVersion(
		boolean trusted)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.lock.Lock getLock();

	public java.lang.String getLuceneProperties();

	/**
	* @deprecated As of 6.2.0, replaced by {@link DLFileVersion#getUserId()}
	*/
	@java.lang.Deprecated()
	public long getVersionUserId();

	/**
	* @deprecated As of 6.2.0, replaced by {@link DLFileVersion#getUserName()}
	*/
	@java.lang.Deprecated()
	public java.lang.String getVersionUserName();

	/**
	* @deprecated As of 6.2.0, replaced by {@link DLFileVersion#getUserUuid()}
	*/
	@java.lang.Deprecated()
	public java.lang.String getVersionUserUuid();

	public boolean hasLock();

	public boolean isCheckedOut();

	public boolean isInHiddenFolder();

	public void setExtraSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties extraSettingsProperties);
}