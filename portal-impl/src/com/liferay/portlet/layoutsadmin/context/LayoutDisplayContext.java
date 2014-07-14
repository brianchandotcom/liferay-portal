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

package com.liferay.portlet.layoutsadmin.context;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutDisplayContext {

	public LayoutDisplayContext(HttpServletRequest request) {
		_request = request;
	}

	public Group getLiveGroup() {
		if (_liveGroup != null) {
			return _liveGroup;
		}

		if (getSelGroup().isStagingGroup()) {
			_liveGroup = getSelGroup().getLiveGroup();
		}
		else {
			_liveGroup = getSelGroup();
		}

		return _liveGroup;
	}

	public Group getSelGroup() {
		if (_selGroup != null) {
			return _selGroup;
		}

		_selGroup = (Group)_request.getAttribute(WebKeys.GROUP);

		return _selGroup;
	}

	public Group getStagingGroup() {
		if (_stagingGroup != null) {
			return _stagingGroup;
		}

		Group selGroup = getSelGroup();

		if (selGroup.isStagingGroup()) {
			_stagingGroup = selGroup;
		}
		else {
			if (selGroup.isStaged()) {
				if (selGroup.isStagedRemotely()) {
					_stagingGroup = selGroup;
				}
				else {
					_stagingGroup = selGroup.getStagingGroup();
				}
			}
		}

		return _stagingGroup;
	}

	private Group _liveGroup;
	private HttpServletRequest _request;
	private Group _selGroup;
	private Group _stagingGroup;

}