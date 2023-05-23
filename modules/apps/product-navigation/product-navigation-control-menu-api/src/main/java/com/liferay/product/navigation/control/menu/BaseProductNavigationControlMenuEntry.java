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

package com.liferay.product.navigation.control.menu;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.SessionClicks;

import java.io.IOException;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 */
public abstract class BaseProductNavigationControlMenuEntry
	implements ProductNavigationControlMenuEntry {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ProductNavigationControlMenuEntry)) {
			return false;
		}

		ProductNavigationControlMenuEntry productNavigationControlMenuEntry =
			(ProductNavigationControlMenuEntry)object;

		if (Objects.equals(
				getKey(), productNavigationControlMenuEntry.getKey())) {

			return true;
		}

		return false;
	}

	@Override
	public Map<String, Object> getData(HttpServletRequest httpServletRequest) {
		return Collections.emptyMap();
	}

	@Override
	public String getIcon(HttpServletRequest httpServletRequest) {
		return StringPool.BLANK;
	}

	@Override
	public String getIconCssClass(HttpServletRequest httpServletRequest) {
		return StringPool.BLANK;
	}

	@Override
	public String getKey() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	@Override
	public String getLinkCssClass(HttpServletRequest httpServletRequest) {
		return StringPool.BLANK;
	}

	@Override
	public String getMarkupView(HttpServletRequest httpServletRequest) {
		return null;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getKey());
	}

	@Override
	public boolean includeBody(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		return false;
	}

	@Override
	public boolean includeIcon(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		return false;
	}

	@Override
	public boolean isPanelStateOpen(
		HttpServletRequest httpServletRequest, String key) {

		String panelState = SessionClicks.get(
			httpServletRequest, key, "closed");

		if (Objects.equals(panelState, "open")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest)
		throws PortalException {

		return true;
	}

	@Override
	public boolean isUseDialog() {
		return false;
	}

	@Override
	public void setPanelState(
		HttpServletRequest httpServletRequest, String key, String panelState) {

		SessionClicks.put(httpServletRequest, key, panelState);
	}

}