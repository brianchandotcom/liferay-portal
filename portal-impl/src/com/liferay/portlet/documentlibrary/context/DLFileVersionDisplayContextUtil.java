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
public class DLFileVersionDisplayContextUtil {

	public static DLFileVersionDisplayContext
		getDLFileVersionActionsDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion)
		throws PortalException {

		DLFileVersionDisplayContext dlFileVersionDisplayContext =
			new DefaultDLFileVersionDisplayContext(
				request, response, fileVersion);

		if (fileVersion != null) {
			dlFileVersionDisplayContext =
				_chainDLFileVersionActionsDisplayContexts(
					request, response, fileVersion,
					dlFileVersionDisplayContext);
		}

		return dlFileVersionDisplayContext;
	}

	private static DLFileVersionDisplayContext
		_chainDLFileVersionActionsDisplayContexts(
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion, DLFileVersionDisplayContext
		dlFileVersionDisplayContext) {

		Collection<DLFileVersionDisplayContextFactory>
			dlFileVersionActionsDisplayContextFactories =
				_getDLFileVersionActionsDisplayContextFactories();

		for (DLFileVersionDisplayContextFactory
			dlFileVersionDisplayContextFactory :
					dlFileVersionActionsDisplayContextFactories) {

			dlFileVersionDisplayContext =
				dlFileVersionDisplayContextFactory.
					getDLFileVersionActionsDisplayContext(
						dlFileVersionDisplayContext, request, response,
						fileVersion);
		}

		return dlFileVersionDisplayContext;
	}

	private static Collection<DLFileVersionDisplayContextFactory>
		_getDLFileVersionActionsDisplayContextFactories() {

		try {
			Registry registry = RegistryUtil.getRegistry();

			return registry.getServices(
				DLFileVersionDisplayContextFactory.class, null);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

}