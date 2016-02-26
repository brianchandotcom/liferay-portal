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

package com.liferay.portal.kernel.trash;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.trash.kernel.util.TrashUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alexander Chow
 */
public abstract class BaseTrashRenderer implements TrashRenderer {

	@Override
	public String getIconCssClass() {
		return StringPool.BLANK;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String getIconPath(PortletRequest portletRequest) {
		return StringPool.BLANK;
	}

	@Override
	public String getNewName(String oldName, String token) {
		return TrashUtil.getNewName(oldName, token);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getSummary(PortletRequest,
	 *             javax.portlet.PortletResponse)}
	 */
	@Deprecated
	@Override
	public String getSummary(Locale locale) {
		return getSummary(null, null);
	}

	@Override
	public String renderActions(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		return null;
	}

}