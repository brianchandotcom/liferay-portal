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

package com.liferay.document.library.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DLFileVersion service. Represents a row in the &quot;DLFileVersion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl"
)
@ProviderType
public interface DLFileVersion
	extends DLFileVersionModel, PersistedModel, TreeModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DLFileVersion, Long> FILE_VERSION_ID_ACCESSOR =
		new Accessor<DLFileVersion, Long>() {

			@Override
			public Long get(DLFileVersion dlFileVersion) {
				return dlFileVersion.getFileVersionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DLFileVersion> getTypeClass() {
				return DLFileVersion.class;
			}

		};

	@Override
	public String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.io.InputStream getContentStream(boolean incrementCounter)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.dynamic.data.mapping.kernel.DDMStructure>
			getDDMStructures()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DLFileEntryType getDLFileEntryType()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.expando.kernel.model.ExpandoBridge getExpandoBridge();

	public com.liferay.portal.kernel.util.UnicodeProperties
		getExtraSettingsProperties();

	public DLFileEntry getFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DLFolder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getIcon();

	public void setExtraSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			extraSettingsUnicodeProperties);

}