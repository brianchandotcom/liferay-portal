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

package com.liferay.portal.fabric.netty.agent;

import java.io.File;
import java.io.Serializable;

import java.nio.file.Path;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricAgentConfig implements Serializable {

	public NettyFabricAgentConfig(File repositoryFolder) {
		if (repositoryFolder == null) {
			throw new NullPointerException("Repository folder is null");
		}

		_repositoryFolder = repositoryFolder;
	}

	public Path getRepositoryPath() {
		return _repositoryFolder.toPath();
	}

	private static final long serialVersionUID = 1L;

	private final File _repositoryFolder;

}