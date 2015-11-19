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

package com.liferay.portlet.imageuploader.action;

import com.liferay.portal.ImageTypeException;
import com.liferay.portal.NoSuchRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.flash.FlashMagicBytesUtil;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.RequestContentLengthException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.antivirus.AntivirusScannerException;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.InputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Tibor Lipusz
 */
public class UploadImageAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long maxFileSize = ParamUtil.getLong(actionRequest, "maxFileSize");

		try {
			UploadException uploadException =
				(UploadException)actionRequest.getAttribute(
					WebKeys.UPLOAD_EXCEPTION);

			if (uploadException != null) {
				if (uploadException.isExceededFileSizeLimit()) {
					throw new FileSizeException(uploadException.getCause());
				}
				else if (uploadException.
							isExceededRequestContentLengthLimit()) {

					throw new RequestContentLengthException(
						uploadException.getCause());
				}

				throw new PortalException(uploadException.getCause());
			}
			else if (cmd.equals(Constants.ADD_TEMP)) {
				FileEntry tempImageFileEntry = addTempImageFileEntry(
					actionRequest);

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put(
					"tempImageFileName", tempImageFileEntry.getTitle());

				writeJSON(actionRequest, actionResponse, jsonObject);
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

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.image_uploader.view"));
	}

	@Override
	public void serveResource(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);

			if (cmd.equals(Constants.GET_TEMP)) {
				FileEntry tempFileEntry = getTempImageFileEntry(
					resourceRequest);

				FlashMagicBytesUtil.Result flashMagicBytesUtilResult =
					FlashMagicBytesUtil.check(tempFileEntry.getContentStream());

				if (flashMagicBytesUtilResult.isFlash()) {
					return;
				}

				serveTempImageFile(
					resourceResponse,
					flashMagicBytesUtilResult.getInputStream());
			}
		}
		catch (NoSuchFileEntryException nsfee) {
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	protected FileEntry addTempImageFileEntry(PortletRequest portletRequest)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(portletRequest);

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
				getTempImageFolderName(), fileName);
		}
		catch (Exception e) {
		}

		return TempFileEntryUtil.addTempFileEntry(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			getTempImageFolderName(), fileName, file, contentType);
	}

	protected FileEntry getTempImageFileEntry(PortletRequest portletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return TempFileEntryUtil.getTempFileEntry(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			getTempImageFolderName(), getTempImageFileName(portletRequest));
	}

	protected String getTempImageFileName(PortletRequest portletRequest) {
		return ParamUtil.getString(portletRequest, "tempImageFileName");
	}

	protected String getTempImageFolderName() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	protected void handleUploadException(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String cmd, long maxFileSize, Exception e)
		throws Exception {

		if (e instanceof PrincipalException) {
			SessionErrors.add(actionRequest, e.getClass());

			setForward(actionRequest, "portal.error");
		}
		else if (e instanceof AntivirusScannerException ||
				 e instanceof FileExtensionException ||
				 e instanceof FileSizeException ||
				 e instanceof ImageTypeException ||
				 e instanceof NoSuchFileException ||
				 e instanceof RequestContentLengthException ||
				 e instanceof UploadException) {

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
						StringUtil.merge(
							PropsValues.DL_FILE_EXTENSIONS, StringPool.COMMA));
				}
				else if (e instanceof FileSizeException) {
					if (maxFileSize == 0) {
						maxFileSize = PrefsPropsUtil.getLong(
							PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
					}

					errorMessage = themeDisplay.translate(
						"please-enter-a-file-with-a-valid-file-size-no" +
							"-larger-than-x",
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
						"an-unexpected-error-occurred-while-uploading" +
							"-your-file");
				}

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("errorMessage", errorMessage);

				writeJSON(actionRequest, actionResponse, jsonObject);
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

		FileEntry tempFileEntry = null;

		InputStream tempImageStream = null;

		try {
			tempFileEntry = getTempImageFileEntry(actionRequest);

			tempImageStream = tempFileEntry.getContentStream();

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
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			File file = FileUtil.createTempFile(bytes);

			try {
				TempFileEntryUtil.deleteTempFileEntry(
					themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
					getTempImageFolderName(),
					getTempImageFileName(actionRequest));
			}
			catch (Exception e) {
			}

			return TempFileEntryUtil.addTempFileEntry(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				getTempImageFolderName(), getTempImageFileName(actionRequest),
				file, tempFileEntry.getMimeType());
		}
		catch (NoSuchFileEntryException nsfee) {
			throw new UploadException(nsfee);
		}
		catch (NoSuchRepositoryException nsre) {
			throw new UploadException(nsre);
		}
		finally {
			StreamUtil.cleanUp(tempImageStream);
		}
	}

	protected void serveTempImageFile(
			MimeResponse mimeResponse, InputStream tempImageStream)
		throws Exception {

		ImageBag imageBag = ImageToolUtil.read(tempImageStream);

		byte[] bytes = ImageToolUtil.getBytes(
			imageBag.getRenderedImage(), imageBag.getType());

		String contentType = MimeTypesUtil.getExtensionContentType(
			imageBag.getType());

		mimeResponse.setContentType(contentType);

		PortletResponseUtil.write(mimeResponse, bytes);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UploadImageAction.class);

}