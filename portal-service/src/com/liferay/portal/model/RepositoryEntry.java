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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the RepositoryEntry service. Represents a row in the &quot;RepositoryEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryEntryModel
 * @see com.liferay.portal.model.impl.RepositoryEntryImpl
 * @see com.liferay.portal.model.impl.RepositoryEntryModelImpl
 * @generated
 */
@ProviderType
public interface RepositoryEntry extends RepositoryEntryModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.RepositoryEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<RepositoryEntry, Long> REPOSITORY_ENTRY_ID_ACCESSOR =
		new Accessor<RepositoryEntry, Long>() {
			@Override
			public Long get(RepositoryEntry repositoryEntry) {
				return repositoryEntry.getRepositoryEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<RepositoryEntry> getTypeClass() {
				return RepositoryEntry.class;
			}
		};
}