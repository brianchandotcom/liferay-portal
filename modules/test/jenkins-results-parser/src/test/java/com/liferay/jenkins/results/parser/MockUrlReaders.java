/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.Arrays;
import java.util.List;

/**
 * @author Calum Ragan
 */
public class MockUrlReaders {

	public MockUrlReaders(
		JSONArrayUrlReader jsonArrayUrlReader,
		JSONObjectUrlReader jsonObjectUrlReader,
		StreamUrlReader streamUrlReader, TextUrlReader textUrlReader) {

		_streamUrlReader = streamUrlReader;

		_urlReaders = Arrays.asList(
			jsonArrayUrlReader, jsonObjectUrlReader, streamUrlReader,
			textUrlReader);
	}

	public StreamUrlReader getStreamUrlReader() {
		return _streamUrlReader;
	}

	public List<UrlReader<?>> getUrlReaders() {
		return _urlReaders;
	}

	private final StreamUrlReader _streamUrlReader;
	private final List<UrlReader<?>> _urlReaders;

}