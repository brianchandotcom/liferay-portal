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
import com.liferay.document.library.web.internal.helper.DLTrashHelper;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslator;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Iván Zaera
 */
@Component(service = DLDisplayContextProvider.class)
public class DLDisplayContextProviderImpl implements DLDisplayContextProvider {

	@Override
	public DLEditFileEntryDisplayContext getDLEditFileEntryDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		DLFileEntryType dlFileEntryType) {

		DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext =
			new DefaultDLEditFileEntryDisplayContext(
				_ddmBeanTranslator, _ddmFormValuesFactory, dlFileEntryType,
				_dlValidator, httpServletRequest, httpServletResponse,
				_storageEngine);

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
				_ddmBeanTranslator, _ddmFormValuesFactory, _dlValidator,
				fileEntry, httpServletRequest, httpServletResponse,
				_storageEngine);

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

		DLViewFileEntryHistoryDisplayContext
			dlViewFileEntryHistoryDisplayContext =
				new DefaultDLViewFileEntryHistoryDisplayContext(
					_dlTrashHelper, _dlURLHelper, fileVersion,
					httpServletRequest, _versioningStrategy);

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
			DLMimeTypeDisplayContext dlMimeTypeDisplayContext =
				_dlMimeTypeDisplayContextSnapshot.get();

			FileVersion fileVersion = fileShortcut.getFileVersion();

			DLPreviewRendererProvider dlPreviewRendererProvider =
				_serviceTrackerMap.getService(fileVersion.getMimeType());

			DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext =
				new DefaultDLViewFileVersionDisplayContext(
					dlMimeTypeDisplayContext, dlPreviewRendererProvider,
					_dlTrashHelper, _dlURLHelper, fileShortcut,
					httpServletRequest, _storageEngine, _versioningStrategy);

			for (DLDisplayContextFactory dlDisplayContextFactory :
					_dlDisplayContextFactories) {

				dlViewFileVersionDisplayContext =
					dlDisplayContextFactory.getDLViewFileVersionDisplayContext(
						dlViewFileVersionDisplayContext, httpServletRequest,
						httpServletResponse, fileShortcut);
			}

			return dlViewFileVersionDisplayContext;
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	@Override
	public DLViewFileVersionDisplayContext getDLViewFileVersionDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, FileVersion fileVersion) {

		DLMimeTypeDisplayContext dlMimeTypeDisplayContext =
			_dlMimeTypeDisplayContextSnapshot.get();

		DLPreviewRendererProvider dlPreviewRendererProvider =
			_serviceTrackerMap.getService(fileVersion.getMimeType());

		DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext =
			new DefaultDLViewFileVersionDisplayContext(
				dlMimeTypeDisplayContext, dlPreviewRendererProvider,
				_dlTrashHelper, _dlURLHelper, fileVersion, httpServletRequest,
				_storageEngine, _versioningStrategy);

		for (DLDisplayContextFactory dlDisplayContextFactory :
				_dlDisplayContextFactories) {

			dlViewFileVersionDisplayContext =
				dlDisplayContextFactory.getDLViewFileVersionDisplayContext(
					dlViewFileVersionDisplayContext, httpServletRequest,
					httpServletResponse, fileVersion);
		}

		return dlViewFileVersionDisplayContext;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_dlDisplayContextFactories = ServiceTrackerListFactory.open(
			bundleContext, DLDisplayContextFactory.class);

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DLPreviewRendererProvider.class, null,
			(serviceReference, emitter) -> {
				DLPreviewRendererProvider dlPreviewRendererProvider =
					bundleContext.getService(serviceReference);

				for (String mimeType :
						dlPreviewRendererProvider.getMimeTypes()) {

					emitter.emit(mimeType);
				}

				bundleContext.ungetService(serviceReference);
			});
	}

	@Deactivate
	protected void deactivate() {
		_dlDisplayContextFactories.close();
		_serviceTrackerMap.close();
	}

	private static final Snapshot<DLMimeTypeDisplayContext>
		_dlMimeTypeDisplayContextSnapshot = new Snapshot<>(
			DLDisplayContextProviderImpl.class, DLMimeTypeDisplayContext.class,
			null, true);

	@Reference
	private DDMBeanTranslator _ddmBeanTranslator;

	@Reference
	private DDMFormValuesFactory _ddmFormValuesFactory;

	private ServiceTrackerList<DLDisplayContextFactory>
		_dlDisplayContextFactories;

	@Reference
	private DLTrashHelper _dlTrashHelper;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private DLValidator _dlValidator;

	private ServiceTrackerMap<String, DLPreviewRendererProvider>
		_serviceTrackerMap;

	@Reference
	private StorageEngine _storageEngine;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile VersioningStrategy _versioningStrategy;

}