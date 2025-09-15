/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;

import java.io.Serializable;

/**
 * @author Tina Tian
 */
public class StartSidecarProcessCallable
	implements ProcessCallable<Serializable> {

	public StartSidecarProcessCallable(byte[] sidecarServerArgs) {
		_sidecarServerArgs = sidecarServerArgs;
	}

	@Override
	public Serializable call() throws ProcessException {
		ElasticsearchServerUtil.start(_sidecarServerArgs);

		return null;
	}

	private static final long serialVersionUID = 1L;

	private final byte[] _sidecarServerArgs;

}