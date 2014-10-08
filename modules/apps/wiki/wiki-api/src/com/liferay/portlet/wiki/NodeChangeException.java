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

package com.liferay.portlet.wiki;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class NodeChangeException extends PortalException {

	public static final int DUPLICATE_PAGE = 1;

	public static final int REDIRECT_PAGE = 2;

	public NodeChangeException(String nodeName, String pageTitle, int type) {
		_nodeName = nodeName;
		_pageTitle = pageTitle;
		_type = type;
	}

	public String getNodeName() {
		return _nodeName;
	}

	public String getPageTitle() {
		return _pageTitle;
	}

	public int getType() {
		return _type;
	}

	private final String _nodeName;
	private final String _pageTitle;
	private final int _type;

}