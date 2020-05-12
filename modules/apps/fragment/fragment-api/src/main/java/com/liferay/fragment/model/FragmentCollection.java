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

package com.liferay.fragment.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the FragmentCollection service. Represents a row in the &quot;FragmentCollection&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentCollectionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.fragment.model.impl.FragmentCollectionImpl"
)
@ProviderType
public interface FragmentCollection
	extends FragmentCollectionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.fragment.model.impl.FragmentCollectionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<FragmentCollection, Long>
		FRAGMENT_COLLECTION_ID_ACCESSOR =
			new Accessor<FragmentCollection, Long>() {

				@Override
				public Long get(FragmentCollection fragmentCollection) {
					return fragmentCollection.getFragmentCollectionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<FragmentCollection> getTypeClass() {
					return FragmentCollection.class;
				}

			};

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getResources()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getResourcesFolderId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getResourcesFolderId(boolean createIfAbsent)
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean hasResources()
		throws com.liferay.portal.kernel.exception.PortalException;

	public void populateZipWriter(
			com.liferay.portal.kernel.zip.ZipWriter zipWriter)
		throws Exception;

	public void populateZipWriter(
			com.liferay.portal.kernel.zip.ZipWriter zipWriter, String path)
		throws Exception;

}