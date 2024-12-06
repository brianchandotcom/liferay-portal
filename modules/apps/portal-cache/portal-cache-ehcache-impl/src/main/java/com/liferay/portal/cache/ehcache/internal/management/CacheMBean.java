/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal.management;

/**
 * @author Dante Wang
 */
public interface CacheMBean {

	public void clear();

	public String getHeapSize();

	public String getKeyType();

	public String getName();

	public long getTimeToIdle();

	public String getValueType();

}