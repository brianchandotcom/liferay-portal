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

package com.liferay.asset.tags.selector.web.internal.search;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Eudaldo Alonso
 */
public class EntriesChecker extends EmptyOnClickRowChecker {

	public EntriesChecker(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		super(portletResponse);

		_portletRequest = portletRequest;
	}

	@Override
	public boolean isChecked(Object obj) {
		String[] selectedTagNames = StringUtil.split(
			ParamUtil.getString(_portletRequest, "selectedTagNames"));

		if (ArrayUtil.isEmpty(selectedTagNames)) {
			return false;
		}

		AssetTag tag = (AssetTag)obj;

		if (!ArrayUtil.contains(selectedTagNames, tag.getName())) {
			return false;
		}

		return true;
	}

	private final PortletRequest _portletRequest;

}