/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;

/**
 * @author Tina Tian
 */
public class StartSidecarProcessCallable implements ProcessCallable<String> {

	public StartSidecarProcessCallable(byte[] sidecarServerArgs) {
		_sidecarServerArgs = sidecarServerArgs;
	}

	@Override
	public String call() throws ProcessException {
		ElasticsearchServerUtil.start(_sidecarServerArgs);

		return ElasticsearchServerUtil.getAddress();
	}

	private static final long serialVersionUID = 1L;

	private final byte[] _sidecarServerArgs;

}