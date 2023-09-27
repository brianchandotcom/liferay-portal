/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.webcache;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public interface WebCacheItem extends Serializable {

	public Object convert(String key) throws WebCacheException;

	public long getRefreshTime();

}