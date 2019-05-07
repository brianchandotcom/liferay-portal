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

package com.liferay.message.boards.web.internal.display.context;

import com.liferay.message.boards.display.context.MBHomeDisplayContext;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.web.internal.display.context.util.MBRequestHelper;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class DefaultMBHomeDisplayContext implements MBHomeDisplayContext {

	public DefaultMBHomeDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		_mbRequestHelper = new MBRequestHelper(httpServletRequest);
	}

	@Override
	public String getTitle() {
		MBCategory category = _mbRequestHelper.getCategory();

		if (category == null) {
			return "add-category";
		}

		return LanguageUtil.format(
			_mbRequestHelper.getRequest(), "edit-x", category.getName(), false);
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	private static final UUID _UUID = UUID.fromString(
		"478C53D5-EB19-4387-A95F-4475746D3E17");

	private final MBRequestHelper _mbRequestHelper;

}