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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.display.context.DLDisplayContextFactory;
import com.liferay.portlet.documentlibrary.display.context.DLEditFileEntryDisplayContext;
import com.liferay.portlet.documentlibrary.display.context.DLViewFileVersionDisplayContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

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

		Collection<DLDisplayContextFactory> dlDisplayContextFactories =
			_dlDisplayContextFactories.values();

		DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext =
			new DefaultDLEditFileEntryDisplayContext(
				request, response, dlFileEntryType);

		for (DLDisplayContextFactory dlDisplayContextFactory :
				dlDisplayContextFactories) {

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

		Collection<DLDisplayContextFactory> dlDisplayContextFactories =
			_dlDisplayContextFactories.values();

		DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext =
			new DefaultDLEditFileEntryDisplayContext(
				request, response, fileEntry);

		for (DLDisplayContextFactory dlDisplayContextFactory :
				dlDisplayContextFactories) {

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
			Collection<DLDisplayContextFactory> dlDisplayContextFactories =
				_dlDisplayContextFactories.values();

			DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext =
				new DefaultDLViewFileVersionDisplayContext(
					request, response, fileShortcut, _dlConfiguration);

			if (fileShortcut == null) {
				return dlViewFileVersionDisplayContext;
			}

			for (DLDisplayContextFactory dlDisplayContextFactory :
					dlDisplayContextFactories) {

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

		Collection<DLDisplayContextFactory> dlDisplayContextFactories =
			_dlDisplayContextFactories.values();

		for (DLDisplayContextFactory dlDisplayContextFactory :
				dlDisplayContextFactories) {

			dlViewFileVersionDisplayContext =
				dlDisplayContextFactory.getDLViewFileVersionDisplayContext(
					dlViewFileVersionDisplayContext, request, response,
					fileVersion);
		}

		return dlViewFileVersionDisplayContext;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_dlConfiguration = Configurable.createConfigurable(
			DLConfiguration.class, properties);

		for (Map.Entry<ServiceReference<DLDisplayContextFactory>,
				DLDisplayContextFactory> entry :
					_dlDisplayContextFactories.entrySet()) {

			if (entry.getValue() != null) {
				continue;
			}

			ServiceReference<DLDisplayContextFactory> serviceReference =
				entry.getKey();

			DLDisplayContextFactory dlDisplayContextFactory =
				_bundleContext.getService(serviceReference);

			_dlDisplayContextFactories.put(
				serviceReference, dlDisplayContextFactory);
		}
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		_dlConfiguration = Configurable.createConfigurable(
			DLConfiguration.class, properties);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.RELUCTANT,
		service = DLDisplayContextFactory.class
	)
	protected void setDLDisplayContextFactory(
		ServiceReference<DLDisplayContextFactory> serviceReference) {

		DLDisplayContextFactory dlDisplayContextFactory = null;

		if (_bundleContext != null) {
			dlDisplayContextFactory = _bundleContext.getService(
				serviceReference);
		}

		_dlDisplayContextFactories.put(
			serviceReference, dlDisplayContextFactory);
	}

	protected void unsetDLDisplayContextFactory(
		ServiceReference<DLDisplayContextFactory> serviceReference) {

		_dlDisplayContextFactories.remove(serviceReference);
	}

	private BundleContext _bundleContext;
	private volatile DLConfiguration _dlConfiguration;
	private final Map<ServiceReference<DLDisplayContextFactory>,
		DLDisplayContextFactory> _dlDisplayContextFactories =
			new ConcurrentSkipListMap<>();

}