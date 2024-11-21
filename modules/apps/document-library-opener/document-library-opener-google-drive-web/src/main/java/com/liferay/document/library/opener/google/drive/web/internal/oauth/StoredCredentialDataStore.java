/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.google.drive.web.internal.oauth;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;

import java.io.IOException;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * @author Marco Galluzzi
 */
public class StoredCredentialDataStore implements DataStore<StoredCredential> {

	public StoredCredentialDataStore(
		long companyId, DataStoreFactory dataStoreFactory, String id) {

		_companyId = companyId;
		_dataStoreFactory = dataStoreFactory;
		_id = id;
	}

	@Override
	public DataStore<StoredCredential> clear() throws IOException {
		StoredCredentialStoreUtil.clear(_companyId);

		return this;
	}

	@Override
	public boolean containsKey(String key) throws IOException {
		if (key == null) {
			return false;
		}

		return StoredCredentialStoreUtil.containsKey(_companyId, key);
	}

	@Override
	public boolean containsValue(StoredCredential value) throws IOException {
		if (value == null) {
			return false;
		}

		return StoredCredentialStoreUtil.containsValue(_companyId, value);
	}

	@Override
	public DataStore<StoredCredential> delete(String key) throws IOException {
		if (key != null) {
			StoredCredentialStoreUtil.delete(_companyId, key);
		}

		return this;
	}

	@Override
	public StoredCredential get(String key) throws IOException {
		return StoredCredentialStoreUtil.get(_companyId, key);
	}

	@Override
	public DataStoreFactory getDataStoreFactory() {
		return _dataStoreFactory;
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public boolean isEmpty() throws IOException {
		return StoredCredentialStoreUtil.isEmpty(_companyId);
	}

	@Override
	public Set<String> keySet() throws IOException {
		return Collections.unmodifiableSet(
			StoredCredentialStoreUtil.keySet(_companyId));
	}

	@Override
	public DataStore<StoredCredential> set(String key, StoredCredential value)
		throws IOException {

		StoredCredentialStoreUtil.add(_companyId, key, value);

		return this;
	}

	@Override
	public int size() throws IOException {
		return StoredCredentialStoreUtil.size(_companyId);
	}

	@Override
	public Collection<StoredCredential> values() throws IOException {
		return Collections.unmodifiableCollection(
			StoredCredentialStoreUtil.values(_companyId));
	}

	private final long _companyId;
	private final DataStoreFactory _dataStoreFactory;
	private final String _id;

}