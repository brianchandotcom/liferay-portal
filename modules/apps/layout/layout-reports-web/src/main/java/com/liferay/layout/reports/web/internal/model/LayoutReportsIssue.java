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

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/**
 * @author Cristina González
 */
public class LayoutReportsIssue {

	public LayoutReportsIssue(List<Detail> details, Key key, long total) {
		if (key == null) {
			throw new IllegalArgumentException("Key should not be null");
		}

		if (details == null) {
			_details = Collections.emptyList();
		}
		else {
			_details = Collections.unmodifiableList(details);
		}

		_key = key;
		_total = total;
	}

	@Override
	public boolean equals(Object object) {
		LayoutReportsIssue layoutReportsIssue = (LayoutReportsIssue)object;

		if (Objects.equals(layoutReportsIssue._details, _details) &&
			Objects.equals(layoutReportsIssue._key, _key) &&
			(layoutReportsIssue._total == _total)) {

			return true;
		}

		return false;
	}

	public List<Detail> getDetails() {
		return _details;
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
		Stream<Detail> stream = _details.stream();

		return JSONUtil.put(
			"details",
			JSONUtil.putAll(
				stream.filter(
					detail -> detail.getTotal() > 0
				).map(
					detail -> detail.toJSONObject(resourceBundle)
				).toArray())
		).put(
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
		JSONObject jsonObject = toJSONObject(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE);

		return jsonObject.toString();
	}

	public static class Detail {

		public Detail(String key, long total) {
			if (key == null) {
				throw new IllegalArgumentException("Key should not be null");
			}

			_key = key;
			_total = total;
		}

		@Override
		public boolean equals(Object object) {
			Detail detail = (Detail)object;

			if ((detail._key == _key) && (detail._total == _total)) {
				return true;
			}

			return false;
		}

		public String getKey() {
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
				"key", _key
			).put(
				"title", ResourceBundleUtil.getString(resourceBundle, _key)
			).put(
				"total", _total
			);
		}

		@Override
		public String toString() {
			JSONObject jsonObject = toJSONObject(
				ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE);

			return jsonObject.toString();
		}

		private final String _key;
		private final long _total;

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

	private final List<Detail> _details;
	private final Key _key;
	private final long _total;

}