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

package com.liferay.portal.kernel.servlet.taglib.ui;

/**
 * @author Iván Zaera
 */
public class URLToolbarItem extends ToolbarItem implements URLUIItem {

	@Override
	public String getTarget() {
		return _target;
	}

	@Override
	public String getURL() {
		return _url;
	}

	@Override
	public void setTarget(String target) {
		_target = target;
	}

	@Override
	public void setURL(String url) {
		_url = url;
	}

	private static final String _TARGET_DEFAULT = "_self";

	private String _target = _TARGET_DEFAULT;
	private String _url;

}