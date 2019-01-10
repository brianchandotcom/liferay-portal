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

package com.liferay.document.library.preview.image.internal;

import com.liferay.document.library.kernel.util.DLProcessorRegistryUtil;
import com.liferay.document.library.kernel.util.ImageProcessorUtil;
import com.liferay.document.library.preview.DLPreviewRenderer;
import com.liferay.document.library.preview.DLPreviewRendererProvider;
import com.liferay.document.library.preview.exception.DLPreviewGenerationInProcessException;
import com.liferay.document.library.preview.exception.DLPreviewSizeException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Dictionary;
import java.util.Optional;
import java.util.Set;

import javax.servlet.RequestDispatcher;
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
@Component(immediate = true, service = ImageDLPreviewRendererProvider.class)
public class ImageDLPreviewRendererProvider
	implements DLPreviewRendererProvider {

	@Override
	public Optional<DLPreviewRenderer> getPreviewDLPreviewRendererOptional(
		FileVersion fileVersion) {

		if (!ImageProcessorUtil.isImageSupported(fileVersion)) {
			return Optional.empty();
		}

		return Optional.of(
			(request, response) -> {
				if (!ImageProcessorUtil.hasImages(fileVersion)) {
					if (!DLProcessorRegistryUtil.isPreviewableSize(
							fileVersion)) {

						throw new DLPreviewSizeException();
					}

					throw new DLPreviewGenerationInProcessException();
				}

				RequestDispatcher requestDispatcher =
					_servletContext.getRequestDispatcher("/preview/view.jsp");

				request.setAttribute(
					WebKeys.DOCUMENT_LIBRARY_FILE_VERSION, fileVersion);

				requestDispatcher.include(request, response);
			});
	}

	@Override
	public Optional<DLPreviewRenderer> getThumbnailDLPreviewRendererOptional(
		FileVersion fileVersion) {

		return Optional.empty();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		Dictionary<String, Object[]> properties = new HashMapDictionary<>();

		Set<String> imageMimeTypes = ImageProcessorUtil.getImageMimeTypes();

		properties.put("content.type", imageMimeTypes.toArray());

		_dlPreviewRendererProviderServiceRegistration =
			bundleContext.registerService(
				DLPreviewRendererProvider.class, this, properties);
	}

	@Deactivate
	protected void deactivate() {
		_dlPreviewRendererProviderServiceRegistration.unregister();
	}

	private ServiceRegistration<DLPreviewRendererProvider>
		_dlPreviewRendererProviderServiceRegistration;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.preview.image)"
	)
	private ServletContext _servletContext;

}