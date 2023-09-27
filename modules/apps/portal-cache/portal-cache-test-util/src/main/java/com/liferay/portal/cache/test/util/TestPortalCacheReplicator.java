/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.test.util;

import com.liferay.portal.cache.PortalCacheReplicator;

import java.io.Serializable;

/**
 * @author Tina Tian
 */
public class TestPortalCacheReplicator
	<K extends Serializable, V extends Serializable>
		extends TestPortalCacheListener<K, V>
		implements PortalCacheReplicator<K, V> {
}