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

package com.liferay.document.library.preview.audio.internal;

import com.liferay.document.library.kernel.util.AudioProcessorUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.preview.DLPreviewRenderer;
import com.liferay.document.library.preview.DLPreviewRendererProvider;
import com.liferay.document.library.preview.audio.internal.constants.DLPreviewAudioWebKeys;
import com.liferay.document.library.preview.exception.DLPreviewGenerationInProcessException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.util.Dictionary;
import java.util.Optional;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = AudioDLPreviewRendererFactory.class)
public class AudioDLPreviewRendererFactory
	implements DLPreviewRendererProvider {

	@Override
	public Optional<DLPreviewRenderer> getPreviewDLPreviewRendererOptional(
		FileVersion fileVersion) {

		if (!AudioProcessorUtil.isAudioSupported(fileVersion)) {
			return Optional.empty();
		}

		return Optional.of(
			(request, response) -> {
				if (!AudioProcessorUtil.hasAudio(fileVersion)) {
					throw new DLPreviewGenerationInProcessException();
				}

				request.setAttribute(
					WebKeys.DOCUMENT_LIBRARY_FILE_VERSION, fileVersion);

				request.setAttribute(
					DLPreviewAudioWebKeys.PREVIEW_FILE_URLS,
					_getPreviewFileURLs(fileVersion, request));

				RequestDispatcher requestDispatcher =
					_servletContext.getRequestDispatcher("/preview/view.jsp");

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

		Set<String> audioMimeTypes = AudioProcessorUtil.getAudioMimeTypes();

		properties.put("content.type", audioMimeTypes.toArray());

		_dlPreviewRendererProviderServiceRegistration =
			bundleContext.registerService(
				DLPreviewRendererProvider.class, this, properties);
	}

	@Deactivate
	protected void deactivate() {
		_dlPreviewRendererProviderServiceRegistration.unregister();
	}

	private String[] _getPreviewFileURLs(
			FileVersion fileVersion, HttpServletRequest request)
		throws PortalException {

		int status = ParamUtil.getInteger(
			request, "status", WorkflowConstants.STATUS_ANY);

		boolean emptyPreview = false;
		String[] previewFileURLs = null;

		String previewQueryString = "&audioPreview=1";

		if (status != WorkflowConstants.STATUS_ANY) {
			previewQueryString += "&status=" + status;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		emptyPreview = true;

		String[] dlFileEntryPreviewAudioContainers =
			PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS;

		previewFileURLs = new String[dlFileEntryPreviewAudioContainers.length];

		try {
			for (int i = 0; i < dlFileEntryPreviewAudioContainers.length; i++) {
				if (AudioProcessorUtil.getPreviewFileSize(
						fileVersion,
						dlFileEntryPreviewAudioContainers[i]) > 0) {

					emptyPreview = false;

					previewFileURLs[i] = DLUtil.getPreviewURL(
						fileVersion.getFileEntry(), fileVersion, themeDisplay,
						previewQueryString + "&type=" +
							dlFileEntryPreviewAudioContainers[i]);
				}
			}
		}
		catch (Exception e) {
			throw new PortalException(e);
		}

		if (emptyPreview) {
			throw new PortalException(
				"No preview available for " + fileVersion.getTitle());
		}

		return previewFileURLs;
	}

	private ServiceRegistration<DLPreviewRendererProvider>
		_dlPreviewRendererProviderServiceRegistration;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.preview.audio)"
	)
	private ServletContext _servletContext;

}