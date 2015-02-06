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

package com.liferay.portal.settings;

import com.liferay.portal.kernel.resource.ResourceRetriever;
import com.liferay.portal.kernel.resource.manager.ResourceManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

/**
* @author Ivan Zaera
*/
public class MockResourceManager implements ResourceManager, ResourceRetriever {

	public MockResourceManager(String contents) {
		_contents = contents;
	}

	@Override
	public InputStream getInputStream() {
		return new ByteArrayInputStream(_contents.getBytes());
	}

	public List<String> getRequestedLocations() {
		return _requestedLocations;
	}

	@Override
	public ResourceRetriever getResourceRetriever(String location) {
		_requestedLocations.add(location);

		return this;
	}

	private final String _contents;
	private final List<String> _requestedLocations = new ArrayList<>();

}