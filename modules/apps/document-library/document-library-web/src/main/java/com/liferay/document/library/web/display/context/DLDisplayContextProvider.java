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

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.document.library.web.configuration.DLConfiguration;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.display.context.DLDisplayContextFactory;
import com.liferay.portlet.documentlibrary.display.context.DLEditFileEntryDisplayContext;
import com.liferay.portlet.documentlibrary.display.context.DLViewFileVersionDisplayContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Iván Zaera
 */
@Component(
	configurationPid = "com.liferay.document.library.web.configuration.DLConfiguration",
	service = DLDisplayContextProvider.class
)
public class DLDisplayContextProvider {

	public DLEditFileEntryDisplayContext getDLEditFileEntryDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		DLFileEntryType dlFileEntryType) {

		DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext =
			new DefaultDLEditFileEntryDisplayContext(
				request, response, dlFileEntryType);

		for (DLDisplayContextFactory dlDisplayContextFactory :
				_dlDisplayContextFactories) {

			dlEditFileEntryDisplayContext =
				dlDisplayContextFactory.getDLEditFileEntryDisplayContext(
					dlEditFileEntryDisplayContext, request, response,
					dlFileEntryType);
		}

		return dlEditFileEntryDisplayContext;
	}

	public DLEditFileEntryDisplayContext getDLEditFileEntryDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		FileEntry fileEntry) {

		DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext =
			new DefaultDLEditFileEntryDisplayContext(
				request, response, fileEntry);

		for (DLDisplayContextFactory dlDisplayContextFactory :
				_dlDisplayContextFactories) {

			dlEditFileEntryDisplayContext =
				dlDisplayContextFactory.getDLEditFileEntryDisplayContext(
					dlEditFileEntryDisplayContext, request, response,
					fileEntry);
		}

		return dlEditFileEntryDisplayContext;
	}

	public DLViewFileVersionDisplayContext
		getDLViewFileVersionDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileShortcut fileShortcut) {

		try {
			DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext =
				new DefaultDLViewFileVersionDisplayContext(
					request, response, fileShortcut, _dlConfiguration);

			if (fileShortcut == null) {
				return dlViewFileVersionDisplayContext;
			}

			for (DLDisplayContextFactory dlDisplayContextFactory :
					_dlDisplayContextFactories) {

				dlViewFileVersionDisplayContext =
					dlDisplayContextFactory.getDLViewFileVersionDisplayContext(
						dlViewFileVersionDisplayContext, request, response,
						fileShortcut);
			}

			return dlViewFileVersionDisplayContext;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public DLViewFileVersionDisplayContext
		getDLViewFileVersionDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion) {

		DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext =
			new DefaultDLViewFileVersionDisplayContext(
				request, response, fileVersion, _dlConfiguration);

		if (fileVersion == null) {
			return dlViewFileVersionDisplayContext;
		}

		for (DLDisplayContextFactory dlDisplayContextFactory :
				_dlDisplayContextFactories) {

			dlViewFileVersionDisplayContext =
				dlDisplayContextFactory.getDLViewFileVersionDisplayContext(
					dlViewFileVersionDisplayContext, request, response,
					fileVersion);
		}

		return dlViewFileVersionDisplayContext;
	}

	@Activate
	protected void activate(
			BundleContext bundleContext, Map<String, Object> properties)
		throws InvalidSyntaxException {

		_dlConfiguration = Configurable.createConfigurable(
			DLConfiguration.class, properties);

		_dlDisplayContextFactories = ServiceTrackerListFactory.open(
			bundleContext, DLDisplayContextFactory.class);
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		_dlConfiguration = Configurable.createConfigurable(
			DLConfiguration.class, properties);
	}

	private volatile DLConfiguration _dlConfiguration;
	private ServiceTrackerList<DLDisplayContextFactory, DLDisplayContextFactory>
		_dlDisplayContextFactories;

}