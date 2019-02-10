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

package com.liferay.portal.vulcan.yaml.openapi;

/**
 * @author Peter Shin
 */
public class PathItem {

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

	private Delete _delete;
	private String _description;
	private Get _get;
	private Head _head;
	private Options _options;
	private Patch _patch;
	private Post _post;
	private Put _put;

}