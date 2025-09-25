/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.petra.process.ProcessCallable;

import java.io.Serializable;

/**
 * @author Tina Tian
 */
public class StopSidecarProcessCallable
	implements ProcessCallable<Serializable> {

	@Override
	public Serializable call() {
		ElasticsearchServerUtil.shutdown();

		return null;
	}

	private static final long serialVersionUID = 1L;

}