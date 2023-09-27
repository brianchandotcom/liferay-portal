/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cluster;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public interface ClusterInvokeAcceptor {

	public boolean accept(Map<String, Serializable> context);

}