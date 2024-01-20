/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.benchmark.http;

import com.liferay.petra.lang.CentralizedThreadLocal;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;

import java.util.List;

/**
 * @author Dante Wang
 */
public class ThreadLocalCookieStore implements CookieStore {

	public static void removeCookieStore() {
		_threadLocalCookieStore.remove();
	}

	public static void setCookieStore(CookieStore cookieStore) {
		_threadLocalCookieStore.set(cookieStore);
	}

	@Override
	public void add(URI uri, HttpCookie cookie) {
		CookieStore cookieStore = _threadLocalCookieStore.get();

		cookieStore.add(uri, cookie);
	}

	@Override
	public List<HttpCookie> get(URI uri) {
		CookieStore cookieStore = _threadLocalCookieStore.get();

		return cookieStore.get(uri);
	}

	@Override
	public List<HttpCookie> getCookies() {
		CookieStore cookieStore = _threadLocalCookieStore.get();

		return cookieStore.getCookies();
	}

	@Override
	public List<URI> getURIs() {
		CookieStore cookieStore = _threadLocalCookieStore.get();

		return cookieStore.getURIs();
	}

	@Override
	public boolean remove(URI uri, HttpCookie cookie) {
		CookieStore cookieStore = _threadLocalCookieStore.get();

		return cookieStore.remove(uri, cookie);
	}

	@Override
	public boolean removeAll() {
		CookieStore cookieStore = _threadLocalCookieStore.get();

		return cookieStore.removeAll();
	}

	private static final ThreadLocal<CookieStore> _threadLocalCookieStore =
		CentralizedThreadLocal.withInitial(SimpleCookieStore::new);

}