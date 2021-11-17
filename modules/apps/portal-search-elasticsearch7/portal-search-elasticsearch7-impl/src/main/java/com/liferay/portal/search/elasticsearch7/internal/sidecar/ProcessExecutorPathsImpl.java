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

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author André de Oliveira
 */
public class ProcessExecutorPathsImpl implements ProcessExecutorPaths {

	public ProcessExecutorPathsImpl(Props props) {
		_props = props;
	}

	@Override
	public Path getLibPath() {
		return Paths.get(
			_props.get(PropsKeys.LIFERAY_SHIELDED_CONTAINER_LIB_PORTAL_DIR));
	}

	private final Props _props;

}