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

package com.liferay.document.library.web.internal.selection;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;

/**
 * @author Alejandro Tardín
 */
public class SelectionParserImpl implements SelectionParser<DLFileEntry> {

	@Override
	public Selection<DLFileEntry> parse(PortletRequest actionRequest) {
		long[] fileEntryIds = ParamUtil.getLongValues(
			actionRequest, "rowIdsFileEntry");

		return () -> {
			List<DLFileEntry> dlFileEntries = new ArrayList<>();

			for (long fileEntryId : fileEntryIds) {
				try {
					dlFileEntries.add(
						DLFileEntryLocalServiceUtil.getFileEntry(fileEntryId));
				}
				catch (PortalException pe) {
					throw new SystemException(pe);
				}
			}

			return dlFileEntries.stream();
		};
	}

}