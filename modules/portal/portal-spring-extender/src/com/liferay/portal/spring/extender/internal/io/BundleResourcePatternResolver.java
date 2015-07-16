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

package com.liferay.portal.spring.extender.internal.io;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.osgi.framework.Bundle;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @author Miguel Pastor
 */
public class BundleResourcePatternResolver
	extends PathMatchingResourcePatternResolver {

	public BundleResourcePatternResolver(Bundle bundle) {
		_bundle = bundle;
	}

	@Override
	public Resource[] getResources(String location) {
		Enumeration<URL> enumeration = _bundle.findEntries(
			location, "*.xml", true);

		List<Resource> resources = new ArrayList<>();

		while (enumeration.hasMoreElements()) {
			resources.add(new UrlResource(enumeration.nextElement()));
		}

		return resources.toArray(new Resource[resources.size()]);
	}

	private final Bundle _bundle;

}