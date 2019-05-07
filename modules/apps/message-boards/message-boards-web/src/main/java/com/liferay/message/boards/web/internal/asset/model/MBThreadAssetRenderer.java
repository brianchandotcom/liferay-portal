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

package com.liferay.message.boards.web.internal.asset.model;

import com.liferay.asset.kernel.model.BaseAssetRenderer;
import com.liferay.message.boards.model.MBThread;
import com.liferay.petra.string.StringPool;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class MBThreadAssetRenderer extends BaseAssetRenderer<MBThread> {

	public MBThreadAssetRenderer(MBThread mbThread) {
		_mbThread = mbThread;
	}

	@Override
	public MBThread getAssetObject() {
		return _mbThread;
	}

	@Override
	public String getClassName() {
		return MBThread.class.getName();
	}

	@Override
	public long getClassPK() {
		return _mbThread.getThreadId();
	}

	@Override
	public long getGroupId() {
		return _mbThread.getGroupId();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return _mbThread.getTitle();
	}

	@Override
	public long getUserId() {
		return _mbThread.getUserId();
	}

	@Override
	public String getUserName() {
		return _mbThread.getUserName();
	}

	@Override
	public String getUuid() {
		return _mbThread.getUuid();
	}

	@Override
	public boolean include(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String template) {

		return false;
	}

	@Override
	public boolean isDisplayable() {
		return false;
	}

	private final MBThread _mbThread;

}