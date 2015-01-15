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

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivan Zaera
 */
public class DLDisplayContextFactoryProviderImpl
	implements DLDisplayContextFactoryProvider {

	public DLDisplayContextFactoryProviderImpl() {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(objectClass=" + DLDisplayContextFactory.class.getName() + ")");

		_serviceTracker = registry.trackServices(
			filter, new DLDisplayContextFactoryServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Override
	public DLEditFileEntryDisplayContext getDLEditFileEntryDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		DLFileEntryType dlFileEntryType) {

		DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext =
			new DefaultDLEditFileEntryDisplayContext(
				request, response, dlFileEntryType);

		for (DLDisplayContextFactoryReference dlDisplayContextFactoryReference :
				_dlDisplayContextFactoryReferences) {

			DLDisplayContextFactory dlDisplayContextFactory =
				dlDisplayContextFactoryReference.getDLDisplayContextFactory();

			dlEditFileEntryDisplayContext =
				dlDisplayContextFactory.getDLEditFileEntryDisplayContext(
					dlEditFileEntryDisplayContext, request, response,
					dlFileEntryType);
		}

		return dlEditFileEntryDisplayContext;
	}

	@Override
	public DLEditFileEntryDisplayContext getDLEditFileEntryDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		FileEntry fileEntry) {

		DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext =
			new DefaultDLEditFileEntryDisplayContext(
				request, response, fileEntry);

		for (DLDisplayContextFactoryReference dlDisplayContextFactoryReference :
				_dlDisplayContextFactoryReferences) {

			DLDisplayContextFactory dlDisplayContextFactory =
				dlDisplayContextFactoryReference.getDLDisplayContextFactory();

			dlEditFileEntryDisplayContext =
				dlDisplayContextFactory.getDLEditFileEntryDisplayContext(
					dlEditFileEntryDisplayContext, request, response,
					fileEntry);
		}

		return dlEditFileEntryDisplayContext;
	}

	@Override
	public DLViewFileVersionDisplayContext
		getDLViewFileVersionDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			DLFileShortcut dlFileShortcut) {

		DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext =
			new DefaultDLViewFileVersionDisplayContext(
				request, response, dlFileShortcut);

		if (dlFileShortcut == null) {
			return dlViewFileVersionDisplayContext;
		}

		for (
			DLDisplayContextFactoryReference dlDisplayContextFactoryReference :
			_dlDisplayContextFactoryReferences) {

			DLDisplayContextFactory dlDisplayContextFactory =
				dlDisplayContextFactoryReference.getDLDisplayContextFactory();

			dlViewFileVersionDisplayContext =
				dlDisplayContextFactory.getDLViewFileVersionDisplayContext(
					dlViewFileVersionDisplayContext, request, response,
					dlFileShortcut);
		}

		return dlViewFileVersionDisplayContext;
	}

	public DLViewFileVersionDisplayContext
		getDLViewFileVersionDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion) {

		DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext =
			new DefaultDLViewFileVersionDisplayContext(
				request, response, fileVersion);

		if (fileVersion == null) {
			return dlViewFileVersionDisplayContext;
		}

		for (
			DLDisplayContextFactoryReference dlDisplayContextFactoryReference :
			_dlDisplayContextFactoryReferences) {

			DLDisplayContextFactory dlDisplayContextFactory =
				dlDisplayContextFactoryReference.getDLDisplayContextFactory();

			dlViewFileVersionDisplayContext =
				dlDisplayContextFactory.getDLViewFileVersionDisplayContext(
					dlViewFileVersionDisplayContext, request, response,
					fileVersion);
		}

		return dlViewFileVersionDisplayContext;
	}

	@Override
	public DLViewFileVersionDisplayContext
		getIGFileVersionActionsDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			DLFileShortcut dlFileShortcut) {

		DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext =
			new DefaultIGViewFileVersionDisplayContext(
				request, response, dlFileShortcut);

		if (dlFileShortcut == null) {
			return dlViewFileVersionDisplayContext;
		}

		for (
			DLDisplayContextFactoryReference dlDisplayContextFactoryReference :
			_dlDisplayContextFactoryReferences) {

			DLDisplayContextFactory dlDisplayContextFactory =
				dlDisplayContextFactoryReference.getDLDisplayContextFactory();

			dlViewFileVersionDisplayContext =
				dlDisplayContextFactory.getIGFileVersionActionsDisplayContext(
					dlViewFileVersionDisplayContext, request, response,
					dlFileShortcut);
		}

		return dlViewFileVersionDisplayContext;
	}

	@Override
	public DLViewFileVersionDisplayContext
		getIGFileVersionActionsDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion) {

		DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext =
			new DefaultIGViewFileVersionDisplayContext(
				request, response, fileVersion);

		if (fileVersion == null) {
			return dlViewFileVersionDisplayContext;
		}

		for (DLDisplayContextFactoryReference dlDisplayContextFactoryReference :
				_dlDisplayContextFactoryReferences) {

			DLDisplayContextFactory dlDisplayContextFactory =
				dlDisplayContextFactoryReference.getDLDisplayContextFactory();

			dlViewFileVersionDisplayContext =
				dlDisplayContextFactory.getIGFileVersionActionsDisplayContext(
					dlViewFileVersionDisplayContext, request, response,
					fileVersion);
		}

		return dlViewFileVersionDisplayContext;
	}

	private final SortedSet<DLDisplayContextFactoryReference>
		_dlDisplayContextFactoryReferences = new ConcurrentSkipListSet<>();
	private final ConcurrentMap<
		DLDisplayContextFactory, DLDisplayContextFactoryReference>
		_dlDisplayContextFactoryReferencesMap = new ConcurrentHashMap<>();
	private final
		ServiceTracker<DLDisplayContextFactory, DLDisplayContextFactory>
			_serviceTracker;

	private class DLDisplayContextFactoryServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<
		DLDisplayContextFactory, DLDisplayContextFactory> {

		@Override
		public DLDisplayContextFactory addingService(
			ServiceReference<DLDisplayContextFactory> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			DLDisplayContextFactory dlDisplayContextFactory =
				registry.getService(serviceReference);

			DLDisplayContextFactoryReference dlDisplayContextFactoryReference =
				new DLDisplayContextFactoryReference(
					dlDisplayContextFactory, serviceReference);

			_dlDisplayContextFactoryReferences.add(
				dlDisplayContextFactoryReference);

			_dlDisplayContextFactoryReferencesMap.put(
				dlDisplayContextFactory, dlDisplayContextFactoryReference);

			return dlDisplayContextFactory;
		}

		@Override
		public void modifiedService(
			ServiceReference<DLDisplayContextFactory> serviceReference,
			DLDisplayContextFactory dlDisplayContextFactory) {

			DLDisplayContextFactoryReference dlDisplayContextFactoryReference =
				_dlDisplayContextFactoryReferencesMap.get(
					dlDisplayContextFactory);

			removedService(
				dlDisplayContextFactoryReference.getServiceReference(),
				dlDisplayContextFactoryReference.getDLDisplayContextFactory());

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<DLDisplayContextFactory> serviceReference,
			DLDisplayContextFactory dlDisplayContextFactory) {

			DLDisplayContextFactoryReference dlDisplayContextFactoryReference =
				_dlDisplayContextFactoryReferencesMap.remove(
					dlDisplayContextFactory);

			_dlDisplayContextFactoryReferences.remove(
				dlDisplayContextFactoryReference);
		}

	}

}