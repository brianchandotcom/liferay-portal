/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.benchmarks.http;

import com.liferay.portal.kernel.util.ListUtil;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Dante Wang
 */
public class SimpleCookieStore implements CookieStore {

	@Override
	public void add(URI uri, HttpCookie httpCookie) {
		if (httpCookie == null) {
			throw new NullPointerException("cookie is null");
		}

		if (httpCookie.getMaxAge() == 0) {
			_cookieJar.remove(httpCookie);
		}
		else {
			_cookieJar.add(httpCookie);

			_addIndex(_domainIndex, httpCookie.getDomain(), httpCookie);
			_addIndex(_uriIndex, _getEffectiveURI(uri), httpCookie);
		}
	}

	@Override
	public List<HttpCookie> get(URI uri) {
		if (uri == null) {
			throw new NullPointerException("uri is null");
		}

		List<HttpCookie> cookies = new ArrayList<>();

		_getInternal(
			cookies, _domainIndex, new DomainComparator(uri.getHost()));
		_getInternal(cookies, _uriIndex, _getEffectiveURI(uri));

		return cookies;
	}

	@Override
	public List<HttpCookie> getCookies() {
		_cookieJar.removeIf(HttpCookie::hasExpired);

		return new ArrayList<>(_cookieJar);
	}

	@Override
	public List<URI> getURIs() {
		Set<URI> uriSet = _uriIndex.keySet();

		Iterator<URI> iterator = uriSet.iterator();

		while (iterator.hasNext()) {
			URI uri = iterator.next();

			List<HttpCookie> cookies = _uriIndex.get(uri);

			if (ListUtil.isEmpty(cookies)) {
				iterator.remove();
			}
		}

		return new ArrayList<>(_uriIndex.keySet());
	}

	@Override
	public boolean remove(URI uri, HttpCookie httpCookie) {
		if (httpCookie == null) {
			throw new NullPointerException("cookie is null");
		}

		return _cookieJar.remove(httpCookie);
	}

	@Override
	public boolean removeAll() {
		_cookieJar.clear();
		_domainIndex.clear();
		_uriIndex.clear();

		return true;
	}

	private <T> void _addIndex(
		Map<T, List<HttpCookie>> indexStore, T index, HttpCookie cookie) {

		if (index == null) {
			return;
		}

		List<HttpCookie> cookies = indexStore.get(index);

		if (cookies != null) {
			cookies.remove(cookie);

			cookies.add(cookie);
		}
		else {
			cookies = new ArrayList<>();

			cookies.add(cookie);

			indexStore.put(index, cookies);
		}
	}

	private URI _getEffectiveURI(URI uri) {
		try {
			return new URI(
				uri.getScheme(), uri.getAuthority(), null, null, null);
		}
		catch (URISyntaxException uriSyntaxException) {
			return uri;
		}
	}

	private <T> void _getInternal(
		List<HttpCookie> cookies, Map<T, List<HttpCookie>> cookieIndex,
		Comparable<T> comparator) {

		for (Map.Entry<T, List<HttpCookie>> entry : cookieIndex.entrySet()) {
			if (comparator.compareTo(entry.getKey()) == 0) {
				List<HttpCookie> indexedCookies = entry.getValue();

				if (indexedCookies != null) {
					Iterator<HttpCookie> iterator = indexedCookies.iterator();

					while (iterator.hasNext()) {
						HttpCookie httpCookie = iterator.next();

						if (_cookieJar.contains(httpCookie)) {
							if (!httpCookie.hasExpired()) {
								if (!cookies.contains(httpCookie)) {
									cookies.add(httpCookie);
								}
							}
							else {
								iterator.remove();

								_cookieJar.remove(httpCookie);
							}
						}
						else {
							iterator.remove();
						}
					}
				}
			}
		}
	}

	private final Set<HttpCookie> _cookieJar = new HashSet<>();
	private final Map<String, List<HttpCookie>> _domainIndex = new HashMap<>();
	private final Map<URI, List<HttpCookie>> _uriIndex = new HashMap<>();

	private static class DomainComparator implements Comparable<String> {

		public DomainComparator(String host) {
			_host = host;
		}

		@Override
		public int compareTo(String domain) {
			if (HttpCookie.domainMatches(domain, _host)) {
				return 0;
			}

			return -1;
		}

		private final String _host;

	}

}