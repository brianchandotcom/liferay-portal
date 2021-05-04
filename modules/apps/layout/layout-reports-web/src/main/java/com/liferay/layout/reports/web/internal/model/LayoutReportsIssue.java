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

package com.liferay.layout.reports.web.internal.model;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Cristina González
 */
public class LayoutReportsIssue {

	public LayoutReportsIssue(Key key, long total) {
		if (key == null) {
			throw new IllegalArgumentException("Key should not be null");
		}

		_key = key;
		_total = total;
	}

	@Override
	public boolean equals(Object object) {
		LayoutReportsIssue layoutReportsIssue = (LayoutReportsIssue)object;

		if (Objects.equals(layoutReportsIssue._key, _key) &&
			(layoutReportsIssue._total == _total)) {

			return true;
		}

		return false;
	}

	public Key getKey() {
		return _key;
	}

	public long getTotal() {
		return _total;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, _key);

		return HashUtil.hash(hashCode, _total);
	}

	public JSONObject toJSONObject(ResourceBundle resourceBundle) {
		return JSONUtil.put(
			"key", _key.toString()
		).put(
			"title",
			ResourceBundleUtil.getString(resourceBundle, _key.toString())
		).put(
			"total", _total
		);
	}

	@Override
	public String toString() {
		JSONObject jsonObject =
			toJSONObject(ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE);

		return jsonObject.toString();
	}

	public enum Key {

		ACCESSIBILITY {

			@Override
			public String toString() {
				return "accessibility";
			}

		},
		SEO {

			@Override
			public String toString() {
				return "seo";
			}

		},

	}

	private final Key _key;
	private final long _total;

}