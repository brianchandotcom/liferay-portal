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

package com.liferay.user.associated.data.web.internal.search;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.user.associated.data.web.internal.display.UADApplicationExportDisplay;

import javax.portlet.PortletResponse;

/**
 * @author Pei-Jung Lan
 */
public class UADApplicationExportDisplayChecker extends EmptyOnClickRowChecker {

	public UADApplicationExportDisplayChecker(PortletResponse portletResponse) {
		super(portletResponse);
	}

	@Override
	public boolean isDisabled(Object obj) {
		UADApplicationExportDisplay uadApplicationExportDisplay =
			(UADApplicationExportDisplay)obj;

		if (!uadApplicationExportDisplay.isExportSupported() ||
			(uadApplicationExportDisplay.getDataCount() == 0)) {

			return true;
		}

		return false;
	}

}