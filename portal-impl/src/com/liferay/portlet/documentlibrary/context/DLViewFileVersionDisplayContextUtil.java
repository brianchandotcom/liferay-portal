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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class DLViewFileVersionDisplayContextUtil {

	public static DLViewFileVersionDisplayContext
		getDLFileVersionActionsDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion)
		throws PortalException {

		DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext =
			new DefaultDLViewFileVersionDisplayContext(
				request, response, fileVersion);

		if (fileVersion != null) {
			dlViewFileVersionDisplayContext =
				_chainDLFileVersionActionsDisplayContexts(
					request, response, fileVersion,
					dlViewFileVersionDisplayContext);
		}

		return dlViewFileVersionDisplayContext;
	}

	public static DLViewFileVersionDisplayContext
		getIGFileVersionActionsDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion)
		throws PortalException {

		DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext =
			new DefaultIGViewFileVersionDisplayContext(
				request, response, fileVersion);

		if (fileVersion != null) {
			dlViewFileVersionDisplayContext =
				_chainDLFileVersionActionsDisplayContexts(
					request, response, fileVersion,
					dlViewFileVersionDisplayContext);
		}

		return dlViewFileVersionDisplayContext;
	}

	private static DLViewFileVersionDisplayContext
		_chainDLFileVersionActionsDisplayContexts(
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion,
			DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext) {

		Collection<DLViewFileVersionDisplayContextFactory>
			dlFileVersionActionsDisplayContextFactories =
				_getDLFileVersionActionsDisplayContextFactories();

		for (DLViewFileVersionDisplayContextFactory
			dlViewFileVersionDisplayContextFactory :
					dlFileVersionActionsDisplayContextFactories) {

			dlViewFileVersionDisplayContext =
				dlViewFileVersionDisplayContextFactory.
					getDLFileVersionActionsDisplayContext(
						dlViewFileVersionDisplayContext, request, response,
						fileVersion);
		}

		return dlViewFileVersionDisplayContext;
	}

	private static Collection<DLViewFileVersionDisplayContextFactory>
		_getDLFileVersionActionsDisplayContextFactories() {

		try {
			Registry registry = RegistryUtil.getRegistry();

			return registry.getServices(
				DLViewFileVersionDisplayContextFactory.class, null);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

}