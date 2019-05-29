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
import com.liferay.document.library.display.context.DLDisplayContextProvider;
import com.liferay.document.library.display.context.DLEditFileEntryDisplayContext;
import com.liferay.document.library.display.context.DLMimeTypeDisplayContext;
import com.liferay.document.library.display.context.DLViewFileEntryHistoryDisplayContext;
import com.liferay.document.library.display.context.DLViewFileVersionDisplayContext;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.document.library.kernel.versioning.VersioningStrategy;
import com.liferay.document.library.preview.DLPreviewRendererProvider;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.document.library.web.internal.util.DLTrashUtil;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
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
public class DLDisplayContextProviderImpl implements DLDisplayContextProvider {

	@Override
	public DLEditFileEntryDisplayContext getDLEditFileEntryDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		DLFileEntryType dlFileEntryType) {

		DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext =
			new DefaultDLEditFileEntryDisplayContext(
				httpServletRequest, httpServletResponse, dlFileEntryType,
				_dlValidator, _storageEngine);

		for (DLDisplayContextFactory dlDisplayContextFactory :
				_dlDisplayContextFactories) {

			dlEditFileEntryDisplayContext =
				dlDisplayContextFactory.getDLEditFileEntryDisplayContext(
					dlEditFileEntryDisplayContext, httpServletRequest,
					httpServletResponse, dlFileEntryType);
		}

		return dlEditFileEntryDisplayContext;
	}

	@Override
	public DLEditFileEntryDisplayContext getDLEditFileEntryDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileEntry fileEntry) {

		DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext =
			new DefaultDLEditFileEntryDisplayContext(
				httpServletRequest, httpServletResponse, _dlValidator,
				fileEntry, _storageEngine);

		for (DLDisplayContextFactory dlDisplayContextFactory :
				_dlDisplayContextFactories) {

			dlEditFileEntryDisplayContext =
				dlDisplayContextFactory.getDLEditFileEntryDisplayContext(
					dlEditFileEntryDisplayContext, httpServletRequest,
					httpServletResponse, fileEntry);
		}

		return dlEditFileEntryDisplayContext;
	}

	@Override
	public DLViewFileEntryHistoryDisplayContext
		getDLViewFileEntryHistoryDisplayContext(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FileVersion fileVersion) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(themeDisplay.getLocale());

		DLViewFileEntryHistoryDisplayContext
			dlViewFileEntryHistoryDisplayContext =
				new DefaultDLViewFileEntryHistoryDisplayContext(
					httpServletRequest, fileVersion, resourceBundle,
					_dlTrashUtil, _versioningStrategy, _dlURLHelper);

		if (fileVersion == null) {
			return dlViewFileEntryHistoryDisplayContext;
		}

		for (DLDisplayContextFactory dlDisplayContextFactory :
				_dlDisplayContextFactories) {

			dlViewFileEntryHistoryDisplayContext =
				dlDisplayContextFactory.getDLViewFileEntryHistoryDisplayContext(
					dlViewFileEntryHistoryDisplayContext, httpServletRequest,
					httpServletResponse, fileVersion);
		}

		return dlViewFileEntryHistoryDisplayContext;
	}

	@Override
	public DLViewFileVersionDisplayContext getDLViewFileVersionDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileShortcut fileShortcut) {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			ResourceBundle resourceBundle =
				_resourceBundleLoader.loadResourceBundle(
					themeDisplay.getLocale());

			FileVersion fileVersion = fileShortcut.getFileVersion();

			DLPreviewRendererProvider dlPreviewRendererProvider =
				_dlPreviewRendererProviders.getService(
					fileVersion.getMimeType());

			DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext =
				new DefaultDLViewFileVersionDisplayContext(
					httpServletRequest, httpServletResponse, fileShortcut,
					_dlMimeTypeDisplayContext, resourceBundle, _storageEngine,
					_dlTrashUtil, dlPreviewRendererProvider,
					_versioningStrategy, _dlURLHelper);

			for (DLDisplayContextFactory dlDisplayContextFactory :
					_dlDisplayContextFactories) {

				dlViewFileVersionDisplayContext =
					dlDisplayContextFactory.getDLViewFileVersionDisplayContext(
						dlViewFileVersionDisplayContext, httpServletRequest,
						httpServletResponse, fileShortcut);
			}

			return dlViewFileVersionDisplayContext;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	@Override
	public DLViewFileVersionDisplayContext getDLViewFileVersionDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileVersion fileVersion) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(themeDisplay.getLocale());

		DLPreviewRendererProvider dlPreviewRendererProvider =
			_dlPreviewRendererProviders.getService(fileVersion.getMimeType());

		DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext =
			new DefaultDLViewFileVersionDisplayContext(
				httpServletRequest, httpServletResponse, fileVersion,
				_dlMimeTypeDisplayContext, resourceBundle, _storageEngine,
				_dlTrashUtil, dlPreviewRendererProvider, _versioningStrategy,
				_dlURLHelper);

		for (DLDisplayContextFactory dlDisplayContextFactory :
				_dlDisplayContextFactories) {

			dlViewFileVersionDisplayContext =
				dlDisplayContextFactory.getDLViewFileVersionDisplayContext(
					dlViewFileVersionDisplayContext, httpServletRequest,
					httpServletResponse, fileVersion);
		}

		return dlViewFileVersionDisplayContext;
	}

	@Reference(unbind = "-")
	public void setStorageEngine(StorageEngine storageEngine) {
		_storageEngine = storageEngine;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_dlDisplayContextFactories = ServiceTrackerListFactory.open(
			bundleContext, DLDisplayContextFactory.class);

		_dlPreviewRendererProviders =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DLPreviewRendererProvider.class, "content.type");
	}

	@Deactivate
	protected void deactivate() {
		_dlDisplayContextFactories.close();
		_dlPreviewRendererProviders.close();
	}

	private ServiceTrackerList<DLDisplayContextFactory, DLDisplayContextFactory>
		_dlDisplayContextFactories;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile DLMimeTypeDisplayContext _dlMimeTypeDisplayContext;

	private ServiceTrackerMap<String, DLPreviewRendererProvider>
		_dlPreviewRendererProviders;

	@Reference
	private DLTrashUtil _dlTrashUtil;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private DLValidator _dlValidator;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.document.library.web)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

	private StorageEngine _storageEngine;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile VersioningStrategy _versioningStrategy;

}