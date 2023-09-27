/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache;

import com.liferay.portal.kernel.cache.PortalCacheListener;

import java.io.Serializable;

import java.util.Properties;

/**
 * @author Tina Tian
 */
public interface PortalCacheListenerFactory {

	public <K extends Serializable, V> PortalCacheListener<K, V> create(
		Properties properties);

}