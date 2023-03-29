/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.util;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Shinn Lok
 */
public class FaroThreadLocal {

	public static Object getCache() {
		return _cache.get();
	}

	public static FaroRequestAudit getFaroRequestAudit() {
		return _faroRequestAudit.get();
	}

	public static boolean isCacheEnabled() {
		Boolean cacheEnabled = _cacheEnabled.get();

		if (cacheEnabled == null) {
			return true;
		}

		return cacheEnabled;
	}

	public static void setCache(Object cache) {
		_cache.set(cache);
	}

	public static void setCacheEnabled(boolean enabled) {
		_cacheEnabled.set(enabled);
	}

	public static void setFaroRequestAudit(FaroRequestAudit faroRequestAudit) {
		_faroRequestAudit.set(faroRequestAudit);
	}

	private static final ThreadLocal<Object> _cache =
		new CentralizedThreadLocal<>(FaroThreadLocal.class + "._cache");
	private static final ThreadLocal<Boolean> _cacheEnabled =
		new CentralizedThreadLocal<>(FaroThreadLocal.class + "._cacheEnabled");
	private static final ThreadLocal<FaroRequestAudit> _faroRequestAudit =
		new CentralizedThreadLocal<>(
			FaroThreadLocal.class + "._faroRequestAudit");

}