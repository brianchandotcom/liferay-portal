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

package com.liferay.portal.lpkg.deployer.internal;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.url.AbstractURLStreamHandlerService;
import org.osgi.service.url.URLConstants;
import org.osgi.service.url.URLStreamHandlerService;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = BytesURLProtocolSupport.class)
public class BytesURLProtocolSupport {

	@Activate
	public void activate(BundleContext bundleContext) {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			URLConstants.URL_HANDLER_PROTOCOL, new String[] {"bytes"});

		bundleContext.registerService(
			URLStreamHandlerService.class.getName(),
			new BytesURLStreamHandlerService(), properties);
	}

	public URL putData(String id, byte[] data) {
		try {
			URL url = new URL("bytes://localhost/".concat(id));

			_dataMap.put(url, data);

			return url;
		}
		catch (MalformedURLException murle) {
			return ReflectionUtil.throwException(murle);
		}
	}

	public byte[] removeData(URL url) {
		return _dataMap.remove(url);
	}

	private final Map<URL, byte[]> _dataMap = new ConcurrentHashMap<>();

	private class BytesURLConnection extends URLConnection {

		@Override
		public void connect() {
		}

		@Override
		public InputStream getInputStream() throws IOException {
			byte[] data = _dataMap.get(url);

			if (data == null) {
				throw new IOException("Unable to find data for url " + url);
			}

			return new UnsyncByteArrayInputStream(data);
		}

		private BytesURLConnection(URL url) {
			super(url);
		}

	}

	private class BytesURLStreamHandlerService
		extends AbstractURLStreamHandlerService {

		@Override
		public URLConnection openConnection(URL url) {
			return new BytesURLConnection(url);
		}

	}

}