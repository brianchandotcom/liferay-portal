/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.google.drive.web.internal.oauth;

import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author Marco Galluzzi
 */
public class StoredCredentialDataStoreFactory implements DataStoreFactory {

	public StoredCredentialDataStoreFactory(long companyId) {
		_companyId = companyId;
	}

	@Override
	public <V extends Serializable> DataStore<V> getDataStore(String id)
		throws IOException {

		return (DataStore<V>)new StoredCredentialDataStore(
			_companyId, this, id);
	}

	private final long _companyId;

}