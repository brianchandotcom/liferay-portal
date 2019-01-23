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

package com.liferay.fragment.contributor;

import com.liferay.petra.lang.HashUtil;

import java.util.Objects;

/**
 * @author Jürgen Kappler
 */
public class DefaultFragmentEntry {

	public DefaultFragmentEntry(
		String fragmentCollectionKey, String name, String css, String html,
		String js, int type) {

		_fragmentCollectionKey = fragmentCollectionKey;
		_name = name;
		_css = css;
		_html = html;
		_js = js;
		_type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DefaultFragmentEntry)) {
			return false;
		}

		DefaultFragmentEntry defaultFragmentEntry = (DefaultFragmentEntry)obj;

		if (Objects.equals(
				_fragmentCollectionKey,
				defaultFragmentEntry._fragmentCollectionKey) &&
			Objects.equals(_name, defaultFragmentEntry._name) &&
			(_type == defaultFragmentEntry._type)) {

			return true;
		}

		return false;
	}

	public String getCss() {
		return _css;
	}

	public String getFragmentCollectionKey() {
		return _fragmentCollectionKey;
	}

	public String getHtml() {
		return _html;
	}

	public String getJs() {
		return _js;
	}

	public String getName() {
		return _name;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _fragmentCollectionKey);

		hash = HashUtil.hash(hash, _name);

		return HashUtil.hash(hash, _type);
	}

	private final String _css;
	private final String _fragmentCollectionKey;
	private final String _html;
	private final String _js;
	private final String _name;
	private final int _type;

}