/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.settings;

import com.liferay.portal.kernel.resource.ResourceRetriever;
import com.liferay.portal.kernel.resource.manager.ResourceManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Iván Zaera
 */
public class MockResourceManager implements ResourceManager, ResourceRetriever {

	public MockResourceManager(String content) {
		_content = content;
	}

	public String getContent() {
		return _content;
	}

	@Override
	public InputStream getInputStream() {
		return new ByteArrayInputStream(_content.getBytes());
	}

	public List<String> getRequestedLocations() {
		return _requestedLocations;
	}

	@Override
	public ResourceRetriever getResourceRetriever(String location) {
		_requestedLocations.add(location);

		return this;
	}

	private final String _content;
	private final List<String> _requestedLocations = new ArrayList<>();

}