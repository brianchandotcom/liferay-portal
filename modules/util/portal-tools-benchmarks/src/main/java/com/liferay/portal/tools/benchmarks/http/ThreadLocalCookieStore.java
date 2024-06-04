/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.benchmarks.http;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;

import java.util.List;

/**
 * @author Dante Wang
 */
public class ThreadLocalCookieStore implements CookieStore {

	public static SafeCloseable withSafeCloseable() {
		_cookieStoreThreadLocal.set(_createInMemoryCookieStore());

		return _cookieStoreThreadLocal::remove;
	}

	@Override
	public void add(URI uri, HttpCookie httpCookie) {
		CookieStore cookieStore = _cookieStoreThreadLocal.get();

		cookieStore.add(uri, httpCookie);
	}

	@Override
	public List<HttpCookie> get(URI uri) {
		CookieStore cookieStore = _cookieStoreThreadLocal.get();

		return cookieStore.get(uri);
	}

	@Override
	public List<HttpCookie> getCookies() {
		CookieStore cookieStore = _cookieStoreThreadLocal.get();

		return cookieStore.getCookies();
	}

	@Override
	public List<URI> getURIs() {
		CookieStore cookieStore = _cookieStoreThreadLocal.get();

		return cookieStore.getURIs();
	}

	@Override
	public boolean remove(URI uri, HttpCookie cookie) {
		CookieStore cookieStore = _cookieStoreThreadLocal.get();

		return cookieStore.remove(uri, cookie);
	}

	@Override
	public boolean removeAll() {
		CookieStore cookieStore = _cookieStoreThreadLocal.get();

		return cookieStore.removeAll();
	}

	private static CookieStore _createInMemoryCookieStore() {

		// Create an instance of JDK's package-private InMemoryCookieStore
		// through creating CookieManager

		CookieManager cookieManager = new CookieManager();

		return cookieManager.getCookieStore();
	}

	private static final ThreadLocal<CookieStore> _cookieStoreThreadLocal =
		CentralizedThreadLocal.withInitial(
			ThreadLocalCookieStore::_createInMemoryCookieStore);

}