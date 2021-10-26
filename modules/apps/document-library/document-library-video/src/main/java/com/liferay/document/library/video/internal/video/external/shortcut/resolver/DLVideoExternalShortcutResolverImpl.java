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

package com.liferay.document.library.video.internal.video.external.shortcut.resolver;

import com.liferay.document.library.video.external.shortcut.DLVideoExternalShortcut;
import com.liferay.document.library.video.external.shortcut.provider.DLVideoExternalShortcutProvider;
import com.liferay.document.library.video.external.shortcut.resolver.DLVideoExternalShortcutResolver;
import com.liferay.document.library.video.internal.constants.DLVideoConstants;
import com.liferay.document.library.video.internal.helper.DLVideoExternalShortcutMetadataHelper;
import com.liferay.document.library.video.internal.helper.DLVideoExternalShortcutMetadataHelperFactory;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.repository.model.FileVersion;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = DLVideoExternalShortcutResolver.class)
public class DLVideoExternalShortcutResolverImpl
	implements DLVideoExternalShortcutResolver {

	@Override
	public DLVideoExternalShortcut resolve(FileVersion fileVersion) {
		DLVideoExternalShortcutMetadataHelper
			dlVideoExternalShortcutMetadataHelper =
				_dlVideoExternalShortcutMetadataHelperFactory.
					getDLVideoExternalShortcutMetadataHelper(fileVersion);

		if (dlVideoExternalShortcutMetadataHelper != null) {
			return _getDLVideoExternalShortcut(
				dlVideoExternalShortcutMetadataHelper);
		}

		return null;
	}

	@Override
	public DLVideoExternalShortcut resolve(String url) {
		for (DLVideoExternalShortcutProvider dlVideoExternalShortcutProvider :
				_dlVideoExternalShortcutProviders) {

			DLVideoExternalShortcut dlVideoExternalShortcut =
				dlVideoExternalShortcutProvider.getDLVideoExternalShortcut(url);

			if (dlVideoExternalShortcut != null) {
				return dlVideoExternalShortcut;
			}
		}

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_dlVideoExternalShortcutProviders = ServiceTrackerListFactory.open(
			bundleContext, DLVideoExternalShortcutProvider.class);
	}

	@Deactivate
	protected void deactivate() {
		_dlVideoExternalShortcutProviders.close();
	}

	private DLVideoExternalShortcut _getDLVideoExternalShortcut(
		DLVideoExternalShortcutMetadataHelper
			dlVideoExternalShortcutMetadataHelper) {

		return new DLVideoExternalShortcut() {

			@Override
			public String getDescription() {
				return dlVideoExternalShortcutMetadataHelper.getFieldValue(
					DLVideoConstants.DDM_FIELD_NAME_DESCRIPTION);
			}

			@Override
			public String getThumbnailURL() {
				return dlVideoExternalShortcutMetadataHelper.getFieldValue(
					DLVideoConstants.DDM_FIELD_NAME_THUMBNAIL_URL);
			}

			@Override
			public String getTitle() {
				return dlVideoExternalShortcutMetadataHelper.getFieldValue(
					DLVideoConstants.DDM_FIELD_NAME_TITLE);
			}

			@Override
			public String getURL() {
				return dlVideoExternalShortcutMetadataHelper.getFieldValue(
					DLVideoConstants.DDM_FIELD_NAME_URL);
			}

			@Override
			public String renderHTML(HttpServletRequest httpServletRequest) {
				return dlVideoExternalShortcutMetadataHelper.getFieldValue(
					DLVideoConstants.DDM_FIELD_NAME_HTML);
			}

		};
	}

	@Reference
	private DLVideoExternalShortcutMetadataHelperFactory
		_dlVideoExternalShortcutMetadataHelperFactory;

	private ServiceTrackerList<DLVideoExternalShortcutProvider>
		_dlVideoExternalShortcutProviders;

}