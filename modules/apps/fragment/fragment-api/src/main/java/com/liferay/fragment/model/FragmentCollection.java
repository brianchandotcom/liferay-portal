/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public com.liferay.portal.kernel.repository.model.FileEntry getResource(
		String path);

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getResources()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getResourcesFolderId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getResourcesFolderId(boolean createIfAbsent)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.Map
		<String, com.liferay.portal.kernel.repository.model.FileEntry>
				getResourcesMap()
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