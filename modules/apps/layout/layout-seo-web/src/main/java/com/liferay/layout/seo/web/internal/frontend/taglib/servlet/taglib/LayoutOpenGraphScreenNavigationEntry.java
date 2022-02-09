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

package com.liferay.layout.seo.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.layout.seo.open.graph.OpenGraphConfiguration;
import com.liferay.layout.seo.web.internal.constants.LayoutSEOScreenNavigationEntryConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "screen.navigation.entry.order:Integer=2",
	service = ScreenNavigationEntry.class
)
public class LayoutOpenGraphScreenNavigationEntry
	extends BaseLayoutScreenNavigationEntry {

	@Override
	public String getEntryKey() {
		return LayoutSEOScreenNavigationEntryConstants.ENTRY_KEY_OPEN_GRAPH;
	}

	@Override
	public boolean isVisible(User user, Layout layout) {
		try {
			if (!_openGraphConfiguration.isOpenGraphEnabled(
					layout.getGroup())) {

				return false;
			}

			if (layout.isTypeAssetDisplay()) {
				return true;
			}

			return super.isVisible(user, layout);
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return false;
		}
	}

	@Override
	protected String getJspPath() {
		return "/layout/screen/navigation/entries/open_graph.jsp";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutOpenGraphScreenNavigationEntry.class);

	@Reference
	private OpenGraphConfiguration _openGraphConfiguration;

}