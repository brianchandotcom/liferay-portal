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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.CacheMode;

/**
 * @author Brian Wing Shun Chan
 */
public class CacheModeTranslator {

	public static org.hibernate.CacheMode translate(CacheMode cacheMode) {
		if (cacheMode == CacheMode.GET) {
			return org.hibernate.CacheMode.GET;
		}
		else if (cacheMode == CacheMode.IGNORE) {
			return org.hibernate.CacheMode.IGNORE;
		}
		else if (cacheMode == CacheMode.NORMAL) {
			return org.hibernate.CacheMode.NORMAL;
		}
		else if (cacheMode == CacheMode.PUT) {
			return org.hibernate.CacheMode.PUT;
		}
		else if (cacheMode == CacheMode.REFRESH) {
			return org.hibernate.CacheMode.REFRESH;
		}

		return org.hibernate.CacheMode.parse(cacheMode.getName());
	}

}