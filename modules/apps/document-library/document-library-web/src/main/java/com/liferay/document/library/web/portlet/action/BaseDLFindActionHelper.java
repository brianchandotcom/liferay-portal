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

package com.liferay.document.library.web.portlet.action;

import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.portal.struts.BaseFindActionHelper;
import com.liferay.portal.struts.BasePortletPageFinder;
import com.liferay.portal.struts.PortletPageFinder;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseDLFindActionHelper extends BaseFindActionHelper {

	@Override
	public PortletURL processPortletURL(
			HttpServletRequest request, PortletURL portletURL)
		throws Exception {

		return portletURL;
	}

	@Override
	protected PortletPageFinder getPortletPageFinder() {
		return new DLPortletPageFinder();
	}

	private static final String[] _PORTLET_IDS = {
		DLPortletKeys.DOCUMENT_LIBRARY, DLPortletKeys.MEDIA_GALLERY_DISPLAY
	};

	private static class DLPortletPageFinder extends BasePortletPageFinder {

		@Override
		protected String[] getPortletIds() {
			return _PORTLET_IDS;
		}

	}

}