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

package com.liferay.portlet.imagegallerydisplay.context;

import com.liferay.portal.kernel.context.BaseDisplayContextProvider;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivan Zaera
 */
public class IGDisplayContextProviderImpl
	extends BaseDisplayContextProvider<IGDisplayContextFactory>
	implements IGDisplayContextProvider {

	public IGDisplayContextProviderImpl() {
		super(IGDisplayContextFactory.class);
	}

	@Override
	public IGViewFileVersionDisplayContext
		getIGFileVersionActionsDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			DLFileShortcut dlFileShortcut) {

		IGViewFileVersionDisplayContext igViewFileVersionDisplayContext =
			new DefaultIGViewFileVersionDisplayContext(
				request, response, dlFileShortcut);

		if (dlFileShortcut == null) {
			return igViewFileVersionDisplayContext;
		}

		for (IGDisplayContextFactory igDisplayContextFactory :
				getDisplayContextFactories()) {

			igViewFileVersionDisplayContext =
				igDisplayContextFactory.getIGViewFileVersionDisplayContext(
					igViewFileVersionDisplayContext, request, response,
					dlFileShortcut);
		}

		return igViewFileVersionDisplayContext;
	}

	@Override
	public IGViewFileVersionDisplayContext
		getIGFileVersionActionsDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion) {

		IGViewFileVersionDisplayContext igViewFileVersionDisplayContext =
			new DefaultIGViewFileVersionDisplayContext(
				request, response, fileVersion);

		if (fileVersion == null) {
			return igViewFileVersionDisplayContext;
		}

		for (IGDisplayContextFactory igDisplayContextFactory :
				getDisplayContextFactories()) {

			igViewFileVersionDisplayContext =
				igDisplayContextFactory.getIGViewFileVersionDisplayContext(
					igViewFileVersionDisplayContext, request, response,
					fileVersion);
		}

		return igViewFileVersionDisplayContext;
	}

}