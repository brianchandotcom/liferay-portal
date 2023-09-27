/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.freemarker.osgi.bridge.internal;

import freemarker.cache.TemplateLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;

import org.osgi.framework.Bundle;

/**
 * @author Carlos Sierra Andrés
 */
public class BundleTemplateLoader implements TemplateLoader {

	public BundleTemplateLoader(Bundle bundle) {
		if (bundle == null) {
			throw new IllegalArgumentException("Bundle is null");
		}

		_bundle = bundle;
	}

	@Override
	public void closeTemplateSource(Object templateSource) throws IOException {
		BundleTemplateSource bundleTemplateSource =
			(BundleTemplateSource)templateSource;

		InputStream inputStream = bundleTemplateSource.getInputStream();

		inputStream.close();
	}

	@Override
	public Object findTemplateSource(String name) throws IOException {
		URL url = _bundle.getEntry(name);

		if (url == null) {
			return null;
		}

		return new BundleTemplateSource(url);
	}

	@Override
	public long getLastModified(Object templateSource) {
		return _bundle.getLastModified();
	}

	@Override
	public Reader getReader(Object templateSource, String encoding) {
		BundleTemplateSource bundleTemplateSource =
			(BundleTemplateSource)templateSource;

		return new InputStreamReader(bundleTemplateSource.getInputStream());
	}

	private final Bundle _bundle;

	private static class BundleTemplateSource {

		public BundleTemplateSource(URL url) throws IOException {
			_inputStream = url.openStream();
		}

		public InputStream getInputStream() {
			return _inputStream;
		}

		private final InputStream _inputStream;

	}

}