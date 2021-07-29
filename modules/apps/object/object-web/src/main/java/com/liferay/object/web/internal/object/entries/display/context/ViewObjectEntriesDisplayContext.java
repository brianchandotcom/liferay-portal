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

package com.liferay.object.web.internal.object.entries.display.context;

import com.liferay.frontend.taglib.clay.data.set.servlet.taglib.util.ClayDataSetActionDropdownItem;
import com.liferay.object.web.internal.display.context.util.ObjectRequestHelper;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class ViewObjectEntriesDisplayContext {

	public ViewObjectEntriesDisplayContext(
		HttpServletRequest httpServletRequest, String restContextPath) {

		_apiURL = "/o/" + restContextPath;
		_objectRequestHelper = new ObjectRequestHelper(httpServletRequest);
	}

	public String getAPIURL() {
		return _apiURL;
	}

	public List<ClayDataSetActionDropdownItem>
		getClayDataSetActionDropdownItems() {

		return Collections.singletonList(
			new ClayDataSetActionDropdownItem(
				_apiURL + "/{id}", "trash", "delete",
				LanguageUtil.get(_objectRequestHelper.getRequest(), "delete"),
				"delete", "delete", "async"));
	}

	public String getClayHeadlessDataSetDisplayId() {
		return _objectRequestHelper.getPortletId();
	}

	private final String _apiURL;
	private final ObjectRequestHelper _objectRequestHelper;

}