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

package com.liferay.portlet.documentlibrary.context;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.registry.RegistryUtil;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class DLFileEntryActionsDisplayContextUtil {

	public static DLFileEntryActionsDisplayContext
		getDLFileEntryActionsDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileEntry fileEntry, FileVersion fileVersion) {

		DLFileEntryActionsDisplayContext dlFileEntryActionsDisplayContext =
			new DefaultDLFileEntryActionsDisplayContext(
				request, response, fileEntry, fileVersion);

		dlFileEntryActionsDisplayContext =
			_chainExtendedDLFileEntryDisplayContexts(
				request, response, fileEntry, fileVersion,
				dlFileEntryActionsDisplayContext);

		return dlFileEntryActionsDisplayContext;
	}

	private static DLFileEntryActionsDisplayContext
		_chainExtendedDLFileEntryDisplayContexts(
			HttpServletRequest request, HttpServletResponse response,
			FileEntry fileEntry, FileVersion fileVersion,
			DLFileEntryActionsDisplayContext dlFileEntryActionsDisplayContext) {

		Collection<DLFileEntryActionsDisplayContextFactory>
			dlFileEntryActionsDisplayContextFactories =
				_getDLFileEntryActionsDisplayContextFactories();

		for (DLFileEntryActionsDisplayContextFactory
				dlFileEntryActionsDisplayContextFactory :
					dlFileEntryActionsDisplayContextFactories) {

			dlFileEntryActionsDisplayContext =
				dlFileEntryActionsDisplayContextFactory.
					getDLFileEntryActionsDisplayContext(
						dlFileEntryActionsDisplayContext, request, response,
						fileEntry, fileVersion);
		}

		return dlFileEntryActionsDisplayContext;
	}

	private static Collection<DLFileEntryActionsDisplayContextFactory>
		_getDLFileEntryActionsDisplayContextFactories() {

		try {
			return RegistryUtil.getRegistry().getServices(
				DLFileEntryActionsDisplayContextFactory.class, null);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

}