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

package com.liferay.commerce.frontend.clay.data.set;

/**
 * @author Marco Leo
 */
public class ClayHeadlessDataSetActionTemplate {

	public ClayHeadlessDataSetActionTemplate(
		String href, String icon, String id, String label, String method,
		String permissionKey, String target) {

		_href = href;
		_icon = icon;
		_id = id;
		_label = label;
		_method = method;
		_permissionKey = permissionKey;
		_target = target;
	}

	public String getHref() {
		return _href;
	}

	public String getIcon() {
		return _icon;
	}

	public String getId() {
		return _id;
	}

	public String getLabel() {
		return _label;
	}

	public String getMethod() {
		return _method;
	}

	public String getPermissionKey() {
		return _permissionKey;
	}

	public String getTarget() {
		return _target;
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setMethod(String method) {
		_method = method;
	}

	public void setPermissionKey(String permissionKey) {
		_permissionKey = permissionKey;
	}

	public void setTarget(String target) {
		_target = target;
	}

	private String _href;
	private String _icon;
	private String _id;
	private String _label;
	private String _method;
	private String _permissionKey;
	private String _target;

}