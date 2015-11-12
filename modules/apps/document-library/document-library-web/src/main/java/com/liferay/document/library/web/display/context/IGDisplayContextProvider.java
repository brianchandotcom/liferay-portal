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

package com.liferay.document.library.web.display.context;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.imagegallerydisplay.display.context.IGDisplayContextFactory;
import com.liferay.portlet.imagegallerydisplay.display.context.IGViewFileVersionDisplayContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera
 */
@Component(service = IGDisplayContextProvider.class)
public class IGDisplayContextProvider {

	public IGViewFileVersionDisplayContext
		getIGViewFileVersionActionsDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileShortcut fileShortcut) {

		try {
			IGViewFileVersionDisplayContext igViewFileVersionDisplayContext =
				new DefaultIGViewFileVersionDisplayContext(
					request, response, fileShortcut);

			if (fileShortcut == null) {
				return igViewFileVersionDisplayContext;
			}

			for (IGDisplayContextFactory igDisplayContextFactory :
					_igDisplayContextFactories) {

				igViewFileVersionDisplayContext =
					igDisplayContextFactory.getIGViewFileVersionDisplayContext(
						igViewFileVersionDisplayContext, request, response,
						fileShortcut);
			}

			return igViewFileVersionDisplayContext;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public IGViewFileVersionDisplayContext
		getIGViewFileVersionActionsDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion) {

		try {
			IGViewFileVersionDisplayContext igViewFileVersionDisplayContext =
				new DefaultIGViewFileVersionDisplayContext(
					request, response, fileVersion);

			if (fileVersion == null) {
				return igViewFileVersionDisplayContext;
			}

			for (IGDisplayContextFactory igDisplayContextFactory :
					_igDisplayContextFactories) {

				igViewFileVersionDisplayContext =
					igDisplayContextFactory.getIGViewFileVersionDisplayContext(
						igViewFileVersionDisplayContext, request, response,
						fileVersion);
			}

			return igViewFileVersionDisplayContext;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_igDisplayContextFactories = ServiceTrackerListFactory.open(
			bundleContext, IGDisplayContextFactory.class);
	}

	private ServiceTrackerList<IGDisplayContextFactory, IGDisplayContextFactory>
		_igDisplayContextFactories;

}