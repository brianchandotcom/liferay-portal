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

package com.liferay.portal.osgi.web.wab.generator.internal.artifact;

import java.io.File;

import java.net.URL;

import org.apache.felix.fileinstall.ArtifactUrlTransformer;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 */
public class WarArtifactUrlTransformer implements ArtifactUrlTransformer {

	@Override
	public boolean canHandle(File artifact) {
		String name = artifact.getName();

		if (name.endsWith(".war")) {
			return true;
		}

		return false;
	}

	@Override
	public URL transform(URL artifact) throws Exception {
		return ArtifactURLUtil.transform(artifact);
	}

}