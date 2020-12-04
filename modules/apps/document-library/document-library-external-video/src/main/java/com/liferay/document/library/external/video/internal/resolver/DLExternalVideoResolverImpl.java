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

package com.liferay.document.library.external.video.internal.resolver;

import com.liferay.document.library.external.video.DLExternalVideo;
import com.liferay.document.library.external.video.internal.constants.DLExternalVideoConstants;
import com.liferay.document.library.external.video.internal.helper.DLExternalVideoMetadataHelper;
import com.liferay.document.library.external.video.internal.helper.DLExternalVideoMetadataHelperFactory;
import com.liferay.document.library.external.video.provider.DLExternalVideoProvider;
import com.liferay.document.library.external.video.resolver.DLExternalVideoResolver;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.repository.model.FileEntry;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = DLExternalVideoResolver.class)
public class DLExternalVideoResolverImpl implements DLExternalVideoResolver {

	@Override
	public DLExternalVideo resolve(FileEntry fileEntry) {
		DLExternalVideoMetadataHelper dlExternalVideoMetadataHelper =
			_dlExternalVideoMetadataHelperFactory.
				getDLExternalVideoMetadataHelper(fileEntry);

		if (dlExternalVideoMetadataHelper != null) {
			return _getDLExternalVideo(dlExternalVideoMetadataHelper);
		}

		return null;
	}

	@Override
	public DLExternalVideo resolve(String url) {
		for (DLExternalVideoProvider dlExternalVideoProvider :
				_dlExternalVideoProviders) {

			DLExternalVideo dlExternalVideo =
				dlExternalVideoProvider.getDLExternalVideo(url);

			if (dlExternalVideo != null) {
				return dlExternalVideo;
			}
		}

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_dlExternalVideoProviders = ServiceTrackerListFactory.open(
			bundleContext, DLExternalVideoProvider.class);
	}

	@Deactivate
	protected void deactivate() {
		_dlExternalVideoProviders.close();
	}

	private DLExternalVideo _getDLExternalVideo(
		DLExternalVideoMetadataHelper dlExternalVideoMetadataHelper) {

		return new DLExternalVideo() {

			@Override
			public String getDescription() {
				return dlExternalVideoMetadataHelper.getFieldValue(
					DLExternalVideoConstants.DDM_FIELD_NAME_DESCRIPTION);
			}

			@Override
			public String getEmbeddableHTML() {
				return dlExternalVideoMetadataHelper.getFieldValue(
					DLExternalVideoConstants.DDM_FIELD_NAME_HTML);
			}

			@Override
			public String getThumbnailURL() {
				return dlExternalVideoMetadataHelper.getFieldValue(
					DLExternalVideoConstants.DDM_FIELD_NAME_THUMBNAIL_URL);
			}

			@Override
			public String getTitle() {
				return dlExternalVideoMetadataHelper.getFieldValue(
					DLExternalVideoConstants.DDM_FIELD_NAME_TITLE);
			}

			@Override
			public String getURL() {
				return dlExternalVideoMetadataHelper.getFieldValue(
					DLExternalVideoConstants.DDM_FIELD_NAME_URL);
			}

		};
	}

	@Reference
	private DLExternalVideoMetadataHelperFactory
		_dlExternalVideoMetadataHelperFactory;

	private ServiceTrackerList<DLExternalVideoProvider, DLExternalVideoProvider>
		_dlExternalVideoProviders;

}