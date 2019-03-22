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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class UnmodifiableJSONObjectImpl extends JSONObjectImpl {

	@Override
	public Iterator<String> keys() {
		List<String> list = Collections.emptyList();

		return list.iterator();
	}

	@Override
	public Set<String> keySet() {
		return Collections.emptySet();
	}

	@Override
	public JSONObject put(String key, boolean value) {
		if (_log.isWarnEnabled()) {
			_log.warn("Modifications are unsupported");
		}

		return this;
	}

	@Override
	public JSONObject put(String key, Date value) {
		if (_log.isWarnEnabled()) {
			_log.warn("Modifications are unsupported");
		}

		return this;
	}

	@Override
	public JSONObject put(String key, double value) {
		if (_log.isWarnEnabled()) {
			_log.warn("Modifications are unsupported");
		}

		return this;
	}

	@Override
	public JSONObject put(String key, int value) {
		if (_log.isWarnEnabled()) {
			_log.warn("Modifications are unsupported");
		}

		return this;
	}

	@Override
	public JSONObject put(String key, JSONArray value) {
		if (_log.isWarnEnabled()) {
			_log.warn("Modifications are unsupported");
		}

		return this;
	}

	@Override
	public JSONObject put(String key, JSONObject value) {
		if (_log.isWarnEnabled()) {
			_log.warn("Modifications are unsupported");
		}

		return this;
	}

	@Override
	public JSONObject put(String key, long value) {
		if (_log.isWarnEnabled()) {
			_log.warn("Modifications are unsupported");
		}

		return this;
	}

	@Override
	public JSONObject put(String key, String value) {
		if (_log.isWarnEnabled()) {
			_log.warn("Modifications are unsupported");
		}

		return this;
	}

	@Override
	public JSONObject putException(Exception exception) {
		if (_log.isWarnEnabled()) {
			_log.warn("Modifications are unsupported");
		}

		return this;
	}

	@Override
	public Object remove(String key) {
		if (_log.isWarnEnabled()) {
			_log.warn("Modifications are unsupported");
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UnmodifiableJSONObjectImpl.class);

}