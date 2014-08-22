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

import javax.servlet.jsp.PageContext;

/**
 * @author Iván Zaera
 */
public class DLFileVersionActionsDisplayContextUtil {

	public static DLFileVersionActionsDisplayContext
		getDLFileVersionActionsDisplayContext(
			PageContext pageContext, FileVersion fileVersion)
		throws PortalException {

		DLFileVersionActionsDisplayContext dlFileVersionActionsDisplayContext =
			new DefaultDLFileVersionActionsDisplayContext(
				pageContext, fileVersion);

		if (fileVersion != null) {
			dlFileVersionActionsDisplayContext =
				_chainDLFileVersionActionsDisplayContexts(
					pageContext, fileVersion,
					dlFileVersionActionsDisplayContext);
		}

		return dlFileVersionActionsDisplayContext;
	}

	private static DLFileVersionActionsDisplayContext
		_chainDLFileVersionActionsDisplayContexts(
			PageContext pageContext, FileVersion fileVersion,
			DLFileVersionActionsDisplayContext
				dlFileVersionActionsDisplayContext) {

		Collection<DLFileVersionActionsDisplayContextFactory>
			dlFileVersionActionsDisplayContextFactories =
				_getDLFileVersionActionsDisplayContextFactories();

		for (DLFileVersionActionsDisplayContextFactory
				dlFileVersionActionsDisplayContextFactory :
					dlFileVersionActionsDisplayContextFactories) {

			dlFileVersionActionsDisplayContext =
				dlFileVersionActionsDisplayContextFactory.
					getDLFileVersionActionsDisplayContext(
						dlFileVersionActionsDisplayContext, pageContext,
						fileVersion);
		}

		return dlFileVersionActionsDisplayContext;
	}

	private static Collection<DLFileVersionActionsDisplayContextFactory>
		_getDLFileVersionActionsDisplayContextFactories() {

		try {
			Registry registry = RegistryUtil.getRegistry();

			return registry.getServices(
				DLFileVersionActionsDisplayContextFactory.class, null);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

}