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

package com.liferay.document.library.preview.video.internal;

import com.liferay.document.library.kernel.model.DLProcessorConstants;
import com.liferay.document.library.kernel.util.DLProcessorRegistry;
import com.liferay.document.library.kernel.util.VideoProcessor;
import com.liferay.document.library.preview.DLPreviewRendererProvider;
import com.liferay.document.library.service.DLFileVersionPreviewLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;
import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = VideoDLPreviewRendererProviderFactory.class)
public class VideoDLPreviewRendererProviderFactory {

	@Activate
	protected void activate(BundleContext bundleContext) {
		VideoProcessor videoProcessor =
			(VideoProcessor)_dlProcessorRegistry.getDLProcessor(
				DLProcessorConstants.VIDEO_PROCESSOR);

		if (videoProcessor == null) {
			return;
		}

		Dictionary<String, Object[]> properties = new HashMapDictionary<>();

		Set<String> videoMimeTypes = videoProcessor.getVideoMimeTypes();

		properties.put("content.type", videoMimeTypes.toArray());

		_dlPreviewRendererProviderServiceRegistration =
			bundleContext.registerService(
				DLPreviewRendererProvider.class,
				new VideoDLPreviewRendererProvider(
					_dlFileVersionPreviewLocalService, _dlURLHelper,
					_servletContext),
				properties);
	}

	@Deactivate
	protected void deactivate() {
		if (_dlPreviewRendererProviderServiceRegistration != null) {
			_dlPreviewRendererProviderServiceRegistration.unregister();
		}
	}

	@Reference
	private DLFileVersionPreviewLocalService _dlFileVersionPreviewLocalService;

	private ServiceRegistration<DLPreviewRendererProvider>
		_dlPreviewRendererProviderServiceRegistration;

	@Reference
	private DLProcessorRegistry _dlProcessorRegistry;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.preview.video)"
	)
	private ServletContext _servletContext;

}