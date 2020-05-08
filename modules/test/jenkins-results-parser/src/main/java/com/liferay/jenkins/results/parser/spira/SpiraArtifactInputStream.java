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

package com.liferay.jenkins.results.parser.spira;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * @author Michael Hashimoto
 */
public class SpiraArtifactInputStream extends ObjectInputStream {

	public SpiraArtifactInputStream(InputStream inputStream)
		throws IOException {

		super(inputStream);
	}

	public SpiraArtifact readSpiraArtifact()
		throws ClassNotFoundException, IOException {

		Object object = readObject();

		if (!(object instanceof SpiraArtifact)) {
			return null;
		}

		SpiraArtifact spiraArtifact = (SpiraArtifact)object;

		spiraArtifact.deserialize();

		return spiraArtifact;
	}

}