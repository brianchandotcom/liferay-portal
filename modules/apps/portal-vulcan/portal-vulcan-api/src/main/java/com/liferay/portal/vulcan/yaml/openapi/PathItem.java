/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.yaml.openapi;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;
import java.util.function.Function;

/**
 * @author Peter Shin
 */
public class PathItem {

	public Operation get(Method method) {
		Function<PathItem, Operation> function = _functions.get(method);

		return function.apply(this);
	}

	public Delete getDelete() {
		return _delete;
	}

	public String getDescription() {
		return _description;
	}

	public Get getGet() {
		return _get;
	}

	public Head getHead() {
		return _head;
	}

	public Options getOptions() {
		return _options;
	}

	public Patch getPatch() {
		return _patch;
	}

	public Post getPost() {
		return _post;
	}

	public Put getPut() {
		return _put;
	}

	public void setDelete(Delete delete) {
		_delete = delete;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setGet(Get get) {
		_get = get;
	}

	public void setHead(Head head) {
		_head = head;
	}

	public void setOptions(Options options) {
		_options = options;
	}

	public void setPatch(Patch patch) {
		_patch = patch;
	}

	public void setPost(Post post) {
		_post = post;
	}

	public void setPut(Put put) {
		_put = put;
	}

	private static final Map<Method, Function<PathItem, Operation>> _functions =
		HashMapBuilder.<Method, Function<PathItem, Operation>>put(
			Method.DELETE, PathItem::getDelete
		).put(
			Method.GET, PathItem::getGet
		).put(
			Method.HEAD, PathItem::getHead
		).put(
			Method.OPTIONS, PathItem::getOptions
		).put(
			Method.PATCH, PathItem::getPatch
		).put(
			Method.POST, PathItem::getPost
		).put(
			Method.PUT, PathItem::getPut
		).build();

	private Delete _delete;
	private String _description;
	private Get _get;
	private Head _head;
	private Options _options;
	private Patch _patch;
	private Post _post;
	private Put _put;

}