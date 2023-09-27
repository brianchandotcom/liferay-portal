/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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