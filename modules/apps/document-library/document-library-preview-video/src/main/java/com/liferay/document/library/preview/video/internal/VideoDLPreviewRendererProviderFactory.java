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
import com.liferay.document.library.kernel.util.DLProcessor;
import com.liferay.document.library.kernel.util.VideoProcessor;
import com.liferay.document.library.preview.DLPreviewRendererProvider;
import com.liferay.document.library.service.DLFileVersionPreviewLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = {})
public class VideoDLPreviewRendererProviderFactory {

	@Activate
	protected void activate(BundleContext bundleContext) {
		VideoProcessor videoProcessor = (VideoProcessor)_dlProcessor;

		Set<String> videoMimeTypes = videoProcessor.getVideoMimeTypes();

		_serviceRegistration = bundleContext.registerService(
			DLPreviewRendererProvider.class,
			new VideoDLPreviewRendererProvider(
				videoProcessor, _dlFileVersionPreviewLocalService, _dlURLHelper,
				_servletContext),
			MapUtil.singletonDictionary(
				"content.type", videoMimeTypes.toArray(new String[0])));
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference
	private DLFileVersionPreviewLocalService _dlFileVersionPreviewLocalService;

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(type=" + DLProcessorConstants.VIDEO_PROCESSOR + ")"
	)
	private DLProcessor _dlProcessor;

	@Reference
	private DLURLHelper _dlURLHelper;

	private ServiceRegistration<DLPreviewRendererProvider> _serviceRegistration;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.preview.video)"
	)
	private ServletContext _servletContext;

}