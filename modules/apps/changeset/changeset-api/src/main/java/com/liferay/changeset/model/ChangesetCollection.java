/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.changeset.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ChangesetCollection service. Represents a row in the &quot;ChangesetCollection&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetCollectionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.changeset.model.impl.ChangesetCollectionImpl"
)
@ProviderType
public interface ChangesetCollection
	extends ChangesetCollectionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.changeset.model.impl.ChangesetCollectionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ChangesetCollection, Long>
		CHANGESET_COLLECTION_ID_ACCESSOR =
			new Accessor<ChangesetCollection, Long>() {

				@Override
				public Long get(ChangesetCollection changesetCollection) {
					return changesetCollection.getChangesetCollectionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<ChangesetCollection> getTypeClass() {
					return ChangesetCollection.class;
				}

			};

}