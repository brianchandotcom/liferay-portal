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

package com.liferay.image.uploader.web.internal.portlet.action;

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.image.uploader.web.internal.constants.ImageUploaderPortletKeys;
import com.liferay.image.uploader.web.internal.util.UploadImageUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.ImageTypeException;
import com.liferay.portal.kernel.exception.NoSuchRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelper;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.InputStream;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Levente Hudák
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"javax.portlet.name=" + ImageUploaderPortletKeys.IMAGE_UPLOADER,
		"mvc.command.name=/image_uploader/view"
	},
	service = MVCActionCommand.class
)
public class UploadImageMVCActionCommand extends BaseMVCActionCommand {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_dlConfiguration = ConfigurableUtil.createConfigurable(
			DLConfiguration.class, properties);
	}

	protected FileEntry addTempImageFileEntry(PortletRequest portletRequest)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String contentType = uploadPortletRequest.getContentType("fileName");

		String fileName = uploadPortletRequest.getFileName("fileName");

		File file = uploadPortletRequest.getFile("fileName");

		String mimeType = MimeTypesUtil.getContentType(file, fileName);

		if (!StringUtil.equalsIgnoreCase(
				ContentTypes.APPLICATION_OCTET_STREAM, mimeType)) {

			contentType = mimeType;
		}

		if (!MimeTypesUtil.isWebImage(contentType)) {
			throw new ImageTypeException();
		}

		try {
			TempFileEntryUtil.deleteTempFileEntry(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				UploadImageUtil.getTempImageFolderName(), fileName);
		}
		catch (Exception e) {
		}

		return TempFileEntryUtil.addTempFileEntry(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			UploadImageUtil.getTempImageFolderName(), fileName, file,
			contentType);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		hideDefaultSuccessMessage(actionRequest);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long maxFileSize = ParamUtil.getLong(actionRequest, "maxFileSize");

		try {
			UploadException uploadException =
				(UploadException)actionRequest.getAttribute(
					WebKeys.UPLOAD_EXCEPTION);

			if (uploadException != null) {
				Throwable cause = uploadException.getCause();

				if (uploadException.isExceededFileSizeLimit()) {
					throw new FileSizeException(cause);
				}

				if (uploadException.isExceededUploadRequestSizeLimit()) {
					throw new UploadRequestSizeException(cause);
				}

				throw new PortalException(cause);
			}
			else if (cmd.equals(Constants.ADD_TEMP)) {
				FileEntry tempImageFileEntry = addTempImageFileEntry(
					actionRequest);

				JSONObject jsonObject = JSONUtil.put(
					"tempImageFileName", tempImageFileEntry.getTitle());

				JSONPortletResponseUtil.writeJSON(
					actionRequest, actionResponse, jsonObject);
			}
			else {
				FileEntry fileEntry = null;

				boolean imageUploaded = ParamUtil.getBoolean(
					actionRequest, "imageUploaded");

				if (imageUploaded) {
					fileEntry = saveTempImageFileEntry(actionRequest);

					if (fileEntry.getSize() > maxFileSize) {
						throw new FileSizeException();
					}
				}

				SessionMessages.add(actionRequest, "imageUploaded", fileEntry);

				sendRedirect(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			handleUploadException(
				actionRequest, actionResponse, cmd, maxFileSize, e);
		}
	}

	protected String getTempImageFileName(PortletRequest portletRequest) {
		return ParamUtil.getString(portletRequest, "tempImageFileName");
	}

	protected void handleUploadException(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String cmd, long maxFileSize, Exception e)
		throws Exception {

		if (e instanceof PrincipalException) {
			SessionErrors.add(actionRequest, e.getClass());

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");
		}
		else if (e instanceof AntivirusScannerException ||
				 e instanceof FileExtensionException ||
				 e instanceof FileSizeException ||
				 e instanceof ImageTypeException ||
				 e instanceof NoSuchFileException ||
				 e instanceof UploadException ||
				 e instanceof UploadRequestSizeException) {

			if (cmd.equals(Constants.ADD_TEMP)) {
				hideDefaultErrorMessage(actionRequest);

				ThemeDisplay themeDisplay =
					(ThemeDisplay)actionRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				String errorMessage = StringPool.BLANK;

				if (e instanceof AntivirusScannerException) {
					AntivirusScannerException ase =
						(AntivirusScannerException)e;

					errorMessage = themeDisplay.translate(ase.getMessageKey());
				}
				else if (e instanceof FileExtensionException) {
					errorMessage = themeDisplay.translate(
						"please-enter-a-file-with-a-valid-extension-x",
						StringUtil.merge(_dlConfiguration.fileExtensions()));
				}
				else if (e instanceof FileSizeException) {
					if (maxFileSize == 0) {
						maxFileSize =
							_uploadServletRequestConfigurationHelper.
								getMaxSize();
					}

					errorMessage = themeDisplay.translate(
						"please-enter-a-file-with-a-valid-file-size-no-" +
							"larger-than-x",
						TextFormatter.formatStorageSize(
							maxFileSize, themeDisplay.getLocale()));
				}
				else if (e instanceof ImageTypeException) {
					errorMessage = themeDisplay.translate(
						"please-enter-a-file-with-a-valid-file-type");
				}
				else if (e instanceof NoSuchFileException ||
						 e instanceof UploadException) {

					errorMessage = themeDisplay.translate(
						"an-unexpected-error-occurred-while-uploading-your-" +
							"file");
				}
				else if (e instanceof UploadRequestSizeException) {
					errorMessage = themeDisplay.translate(
						"request-is-larger-than-x-and-could-not-be-processed",
						TextFormatter.formatStorageSize(
							_uploadServletRequestConfigurationHelper.
								getMaxSize(),
							themeDisplay.getLocale()));
				}

				JSONObject jsonObject = JSONUtil.put(
					"errorMessage", errorMessage);

				JSONPortletResponseUtil.writeJSON(
					actionRequest, actionResponse, jsonObject);
			}
			else {
				SessionErrors.add(actionRequest, e.getClass(), e);
			}
		}
		else {
			throw e;
		}
	}

	protected FileEntry saveTempImageFileEntry(ActionRequest actionRequest)
		throws Exception {

		try {
			FileEntry tempFileEntry = UploadImageUtil.getTempImageFileEntry(
				actionRequest);

			try (InputStream tempImageStream =
					tempFileEntry.getContentStream()) {

				ImageBag imageBag = ImageToolUtil.read(tempImageStream);

				RenderedImage renderedImage = imageBag.getRenderedImage();

				String cropRegionJSON = ParamUtil.getString(
					actionRequest, "cropRegion");

				if (Validator.isNotNull(cropRegionJSON)) {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
						cropRegionJSON);

					int height = jsonObject.getInt("height");
					int width = jsonObject.getInt("width");
					int x = jsonObject.getInt("x");
					int y = jsonObject.getInt("y");

					if ((x == 0) && (y == 0) &&
						(renderedImage.getHeight() == height) &&
						(renderedImage.getWidth() == width)) {

						return tempFileEntry;
					}

					if ((height + y) > renderedImage.getHeight()) {
						height = renderedImage.getHeight() - y;
					}

					if ((width + x) > renderedImage.getWidth()) {
						width = renderedImage.getWidth() - x;
					}

					renderedImage = ImageToolUtil.crop(
						renderedImage, height, width, x, y);
				}

				byte[] bytes = ImageToolUtil.getBytes(
					renderedImage, imageBag.getType());

				ThemeDisplay themeDisplay =
					(ThemeDisplay)actionRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				File file = FileUtil.createTempFile(bytes);

				try {
					TempFileEntryUtil.deleteTempFileEntry(
						themeDisplay.getScopeGroupId(),
						themeDisplay.getUserId(),
						UploadImageUtil.getTempImageFolderName(),
						getTempImageFileName(actionRequest));
				}
				catch (Exception e) {
				}

				return TempFileEntryUtil.addTempFileEntry(
					themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
					UploadImageUtil.getTempImageFolderName(),
					getTempImageFileName(actionRequest), file,
					tempFileEntry.getMimeType());
			}
		}
		catch (NoSuchFileEntryException nsfee) {
			throw new UploadException(nsfee);
		}
		catch (NoSuchRepositoryException nsre) {
			throw new UploadException(nsre);
		}
	}

	private volatile DLConfiguration _dlConfiguration;

	@Reference
	private Portal _portal;

	@Reference
	private UploadServletRequestConfigurationHelper
		_uploadServletRequestConfigurationHelper;

}