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

package com.liferay.document.library.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.spi.display.context.DisplayContext;
import com.liferay.document.library.constants.DLContentTypes;
import com.liferay.document.library.constants.DLFileVersionPreviewConstants;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.kernel.util.AudioProcessor;
import com.liferay.document.library.kernel.util.DLProcessorRegistryUtil;
import com.liferay.document.library.kernel.util.ImageProcessor;
import com.liferay.document.library.kernel.util.PDFProcessor;
import com.liferay.document.library.kernel.util.VideoProcessor;
import com.liferay.document.library.preview.DLPreviewRendererProvider;
import com.liferay.document.library.service.DLFileVersionPreviewLocalService;
import com.liferay.frontend.taglib.clay.servlet.taglib.LinkTag;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.store.StoreFactory;

import java.io.InputStream;

import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class DLFileVersionCTDisplayRenderer
	extends BaseCTDisplayRenderer<DLFileVersion> {

	@Override
	public DLFileVersion fetchLatestVersionedModel(
		DLFileVersion dlFileVersion) {

		return _dlFileVersionLocalService.fetchLatestFileVersion(
			dlFileVersion.getFileEntryId(), true);
	}

	@Override
	public InputStream getDownloadInputStream(
			DLFileVersion dlFileVersion, String key)
		throws PortalException {

		return getDownloadInputStream(
			_audioProcessor, _dlAppLocalService, dlFileVersion, _imageProcessor,
			key, _pdfProcessor, _videoProcessor);
	}

	@Override
	public Class<DLFileVersion> getModelClass() {
		return DLFileVersion.class;
	}

	@Override
	public String getTitle(Locale locale, DLFileVersion dlFileVersion) {
		return dlFileVersion.getTitle();
	}

	@Override
	public String getVersionName(DLFileVersion dlFileVersion) {
		return dlFileVersion.getVersion();
	}

	@Override
	public boolean isHideable(DLFileVersion dlFileVersion) {
		return true;
	}

	@Override
	public String renderPreview(DisplayContext<DLFileVersion> displayContext)
		throws Exception {

		DLFileVersion dlFileVersion = displayContext.getModel();

		FileVersion fileVersion = _dlAppLocalService.getFileVersion(
			dlFileVersion.getFileVersionId());

		String fileName = fileVersion.getFileName();
		String mimeType = fileVersion.getMimeType();

		if (_audioProcessor.isSupported(mimeType)) {
			if (!_audioProcessor.hasAudio(fileVersion) ||
				_dlFileVersionPreviewLocalService.hasDLFileVersionPreview(
					fileVersion.getFileEntryId(),
					fileVersion.getFileVersionId(),
					DLFileVersionPreviewConstants.STATUS_FAILURE)) {

				return null;
			}

			return StringBundler.concat(
				"<audio controls controlsList=\"nodownload\" style=\"",
				"max-width: ",
				String.valueOf(PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_WIDTH),
				"px;\"><source src=\"",
				displayContext.getDownloadURL(
					_AUDIO_PREVIEW + ",mp3",
					_audioProcessor.getPreviewFileSize(fileVersion, "mp3"),
					FileUtil.stripExtension(fileName) + ".mp3"),
				"\" type=\"audio/mp3\"/><source src=\"",
				displayContext.getDownloadURL(
					_AUDIO_PREVIEW + ",ogg",
					_audioProcessor.getPreviewFileSize(fileVersion, "ogg"),
					FileUtil.stripExtension(fileName) + ".ogg"),
				"\" type=\"audio/ogg\"/></audio>");
		}

		Set<String> documentMimeTypes =
			_dlPreviewRendererProvider.getMimeTypes();

		if (documentMimeTypes.contains(mimeType)) {
			if (!_pdfProcessor.isDocumentSupported(fileVersion) ||
				_dlFileVersionPreviewLocalService.hasDLFileVersionPreview(
					fileVersion.getFileEntryId(),
					fileVersion.getFileVersionId(),
					DLFileVersionPreviewConstants.STATUS_FAILURE)) {

				return null;
			}
			else if (!_pdfProcessor.hasImages(fileVersion)) {
				if (!DLProcessorRegistryUtil.isPreviewableSize(fileVersion)) {
					return null;
				}

				return StringBundler.concat(
					"<div class=\"alert alert-primary\" role=\"alert\">",
					_language.get(
						displayContext.getLocale(),
						"generating-preview-will-take-a-few-minutes"),
					"</div>");
			}

			fileName = StringBundler.concat(
				FileUtil.stripExtension(fileName), StringPool.PERIOD,
				PDFProcessor.PREVIEW_TYPE);

			return StringBundler.concat(
				"<img src=\"",
				displayContext.getDownloadURL(
					_PDF_PREVIEW,
					_pdfProcessor.getPreviewFileSize(fileVersion, 1), fileName),
				"\" style=\"margin: auto; max-height:624px; max-width:100%;",
				"\">");
		}

		if (_imageProcessor.isSupported(mimeType)) {
			if (!DLProcessorRegistryUtil.isPreviewableSize(fileVersion) ||
				!_imageProcessor.hasImages(fileVersion) ||
				_dlFileVersionPreviewLocalService.hasDLFileVersionPreview(
					fileVersion.getFileEntryId(),
					fileVersion.getFileVersionId(),
					DLFileVersionPreviewConstants.STATUS_FAILURE)) {

				return null;
			}

			fileName = StringBundler.concat(
				FileUtil.stripExtension(fileName), StringPool.PERIOD,
				_imageProcessor.getPreviewType(fileVersion));

			return StringBundler.concat(
				"<img src=\"",
				displayContext.getDownloadURL(
					_IMAGE_PREVIEW,
					_imageProcessor.getPreviewFileSize(fileVersion), fileName),
				"\" style=\"margin: auto; max-height:624px; max-width:100%;",
				"\">");
		}

		Set<String> videoMimeTypes = _videoProcessor.getVideoMimeTypes();

		if (videoMimeTypes.contains(mimeType) ||
			mimeType.equals(DLContentTypes.VIDEO_EXTERNAL_SHORTCUT)) {

			if (!_videoProcessor.hasVideo(fileVersion)) {
				return null;
			}

			return StringBundler.concat(
				"<video controls controlsList=\"nodownload\" style=\"",
				"background-color: #000; display: block; margin: auto; ",
				"max-height:624px; max-width:",
				String.valueOf(PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_WIDTH),
				"px;\"><source src=\"",
				displayContext.getDownloadURL(
					_VIDEO_PREVIEW + ",mp4",
					_audioProcessor.getPreviewFileSize(fileVersion, "mp4"),
					FileUtil.stripExtension(fileName) + ".mp4"),
				"\" type=\"video/mp4\"/><source src=\"",
				displayContext.getDownloadURL(
					_VIDEO_PREVIEW + ",ogv",
					_audioProcessor.getPreviewFileSize(fileVersion, "ogv"),
					FileUtil.stripExtension(fileName) + ".ogv"),
				"\" type=\"video/ogv\"/></audio>");
		}

		return null;
	}

	protected static InputStream getDownloadInputStream(
			AudioProcessor audioProcessor, DLAppLocalService dlAppLocalService,
			DLFileVersion dlFileVersion, ImageProcessor imageProcessor,
			String key, PDFProcessor pdfProcessor,
			VideoProcessor videoProcessor)
		throws PortalException {

		String[] parts = StringUtil.split(key, StringPool.COMMA);

		try {
			FileVersion fileVersion = dlAppLocalService.getFileVersion(
				dlFileVersion.getFileVersionId());

			if (_AUDIO_PREVIEW.equals(parts[0]) ||
				_VIDEO_PREVIEW.equals(parts[0])) {

				if (parts.length < 2) {
					return null;
				}

				String type = parts[1];

				if (_AUDIO_PREVIEW.equals(parts[0])) {
					return audioProcessor.getPreviewAsStream(fileVersion, type);
				}

				return videoProcessor.getPreviewAsStream(fileVersion, type);
			}
			else if (_IMAGE_PREVIEW.equals(parts[0])) {
				return imageProcessor.getPreviewAsStream(fileVersion);
			}
			else if (_PDF_PREVIEW.equals(parts[0])) {
				return pdfProcessor.getPreviewAsStream(fileVersion, 1);
			}
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}

		StoreFactory storeFactory = StoreFactory.getInstance();

		Store store = storeFactory.getStore();

		DLFileEntry dlFileEntry = dlFileVersion.getFileEntry();

		return store.getFileAsStream(
			dlFileVersion.getCompanyId(), dlFileEntry.getDataRepositoryId(),
			dlFileEntry.getName(), parts[0]);
	}

	protected static String getDownloadLink(
		DisplayBuilder<?> displayBuilder, DLFileVersion dlFileVersion) {

		DisplayContext<?> displayContext = displayBuilder.getDisplayContext();

		LinkTag linkTag = new LinkTag();

		linkTag.setDisplayType("primary");
		linkTag.setHref(
			displayContext.getDownloadURL(
				dlFileVersion.getVersion(), dlFileVersion.getSize(),
				dlFileVersion.getFileName()));
		linkTag.setIcon("download");
		linkTag.setLabel("download");
		linkTag.setSmall(true);
		linkTag.setType("button");

		try {
			return linkTag.doTagAsString(
				displayContext.getHttpServletRequest(),
				displayContext.getHttpServletResponse());
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	@Override
	protected void buildDisplay(DisplayBuilder<DLFileVersion> displayBuilder) {
		DLFileVersion dlFileVersion = displayBuilder.getModel();

		displayBuilder.display(
			"title", dlFileVersion.getTitle()
		).display(
			"description", dlFileVersion.getDescription()
		).display(
			"file-name", dlFileVersion.getFileName()
		).display(
			"extension", dlFileVersion.getExtension()
		).display(
			"mime-type", dlFileVersion.getMimeType()
		).display(
			"version", dlFileVersion.getVersion()
		).display(
			"size", dlFileVersion.getSize()
		).display(
			"download", getDownloadLink(displayBuilder, dlFileVersion), false
		);
	}

	private static final String _AUDIO_PREVIEW = "AUDIO_PREVIEW";

	private static final String _IMAGE_PREVIEW = "IMAGE_PREVIEW";

	private static final String _PDF_PREVIEW = "PDF_PREVIEW";

	private static final String _VIDEO_PREVIEW = "VIDEO_PREVIEW";

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private AudioProcessor _audioProcessor;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@Reference
	private DLFileVersionPreviewLocalService _dlFileVersionPreviewLocalService;

	@Reference(
		target = "(component.name=com.liferay.document.library.preview.document.internal.DocumentPreviewRendererProvider)"
	)
	private DLPreviewRendererProvider _dlPreviewRendererProvider;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private ImageProcessor _imageProcessor;

	@Reference
	private Language _language;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private PDFProcessor _pdfProcessor;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private VideoProcessor _videoProcessor;

}