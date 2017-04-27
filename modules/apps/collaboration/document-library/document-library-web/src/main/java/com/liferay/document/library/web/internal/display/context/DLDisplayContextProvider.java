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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.display.context.DLDisplayContextFactory;
import com.liferay.document.library.display.context.DLEditFileEntryDisplayContext;
import com.liferay.document.library.display.context.DLMimeTypeDisplayContext;
import com.liferay.document.library.display.context.DLViewFileEntryHistoryDisplayContext;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.document.library.web.internal.util.DLTrashUtil;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Iván Zaera
 */
@Component(immediate = true, service = DLDisplayContextProvider.class)
public class DLDisplayContextProvider {

	public DLEditFileEntryDisplayContext getDLEditFileEntryDisplayContext(
		HttpServletRequest request, HttpServletResponse response,
		DLFileEntryType dlFileEntryType) {

		DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext =
			new DefaultDLEditFileEntryDisplayContext(
				request, response, dlFileEntryType, _dlValidator,
				_storageEngine);

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
				request, response, _dlValidator, fileEntry, _storageEngine);

		for (DLDisplayContextFactory dlDisplayContextFactory :
				_dlDisplayContextFactories) {

			dlEditFileEntryDisplayContext =
				dlDisplayContextFactory.getDLEditFileEntryDisplayContext(
					dlEditFileEntryDisplayContext, request, response,
					fileEntry);
		}

		return dlEditFileEntryDisplayContext;
	}

	public DLViewFileEntryHistoryDisplayContext
		getDLViewFileEntryHistoryDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(
				themeDisplay.getLanguageId());

		DLViewFileEntryHistoryDisplayContext
			dlViewFileEntryHistoryDisplayContext =
				new DefaultDLViewFileEntryHistoryDisplayContext(
					request, fileVersion, resourceBundle, _dlTrashUtil);

		if (fileVersion == null) {
			return dlViewFileEntryHistoryDisplayContext;
		}

		for (DLDisplayContextFactory dlDisplayContextFactory :
				_dlDisplayContextFactories) {

			dlViewFileEntryHistoryDisplayContext =
				dlDisplayContextFactory.getDLViewFileEntryHistoryDisplayContext(
					dlViewFileEntryHistoryDisplayContext, request, response,
					fileVersion);
		}

		return dlViewFileEntryHistoryDisplayContext;
	}

	public DLViewFileVersionDisplayContext
		getDLViewFileVersionDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			FileShortcut fileShortcut) {

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			ResourceBundle resourceBundle =
				_resourceBundleLoader.loadResourceBundle(
					themeDisplay.getLocale());

			DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext =
				new DefaultDLViewFileVersionDisplayContext(
					request, response, fileShortcut, _dlMimeTypeDisplayContext,
					resourceBundle, _storageEngine, _dlTrashUtil);

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

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(themeDisplay.getLocale());

		DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext =
			new DefaultDLViewFileVersionDisplayContext(
				request, response, fileVersion, _dlMimeTypeDisplayContext,
				resourceBundle, _storageEngine, _dlTrashUtil);

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

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	public void setDLMimeTypeDisplayContext(
		DLMimeTypeDisplayContext dlMimeTypeDisplayContext) {

		_dlMimeTypeDisplayContext = dlMimeTypeDisplayContext;
	}

	@Reference(unbind = "-")
	public void setStorageEngine(StorageEngine storageEngine) {
		_storageEngine = storageEngine;
	}

	public void unsetDLMimeTypeDisplayContext(
		DLMimeTypeDisplayContext dlMimeTypeDisplayContext) {

		_dlMimeTypeDisplayContext = null;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_dlDisplayContextFactories = ServiceTrackerListFactory.open(
			bundleContext, DLDisplayContextFactory.class);
	}

	@Deactivate
	protected void deactivate() {
		_dlDisplayContextFactories.close();
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.document.library.web)",
		unbind = "-"
	)
	protected void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = new AggregateResourceBundleLoader(
			resourceBundleLoader, LanguageUtil.getPortalResourceBundleLoader());
	}

	private ServiceTrackerList<DLDisplayContextFactory, DLDisplayContextFactory>
		_dlDisplayContextFactories;
	private DLMimeTypeDisplayContext _dlMimeTypeDisplayContext;

	@Reference
	private DLTrashUtil _dlTrashUtil;

	@Reference
	private DLValidator _dlValidator;

	private ResourceBundleLoader _resourceBundleLoader;
	private StorageEngine _storageEngine;

}