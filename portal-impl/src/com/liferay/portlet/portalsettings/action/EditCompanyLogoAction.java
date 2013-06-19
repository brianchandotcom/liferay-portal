/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.portalsettings.action;

import com.liferay.portal.ImageTypeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.TempFileUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.CompanyServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

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
 */
public class EditCompanyLogoAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

			if (cmd.equals(Constants.ADD_TEMP)) {
				addTempImageFile(actionRequest);
			}
			else {
				saveTempImageFile(actionRequest);

				sendRedirect(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.portal_settings.error");
			}
			else if (e instanceof FileSizeException ||
					 e instanceof ImageTypeException) {

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.putException(e);

				writeJSON(actionRequest, actionResponse, jsonObject);
			}
			else if (e instanceof NoSuchFileException ||
					 e instanceof UploadException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return actionMapping.findForward(
			getForward(
				renderRequest, "portlet.portal_settings.edit_company_logo"));
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

				serveTempImageFile(
					resourceResponse, tempFileEntry.getContentStream());
			}
		}
		catch (NoSuchFileEntryException nsfee) {
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	protected void addTempImageFile(PortletRequest portletRequest)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(portletRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String contentType = uploadPortletRequest.getContentType("fileName");

		if (!MimeTypesUtil.isWebImage(contentType)) {
			throw new ImageTypeException();
		}

		try {
			TempFileUtil.deleteTempFile(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				getTempImageFileName(portletRequest), getTempImageFolderName());
		}
		catch (Exception e) {
		}

		InputStream inputStream = null;

		try {
			inputStream = uploadPortletRequest.getFileAsStream("fileName");

			TempFileUtil.addTempFile(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				getTempImageFileName(portletRequest), getTempImageFolderName(),
				inputStream, contentType);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	protected RenderedImage getCroppedRenderedImage(
		RenderedImage renderedImage, int height, int width, int x, int y) {

		Rectangle rectangle = new Rectangle(width, height);

		Rectangle croppedRectangle = rectangle.intersection(
			new Rectangle(renderedImage.getWidth(), renderedImage.getHeight()));

		BufferedImage bufferedImage = ImageToolUtil.getBufferedImage(
			renderedImage);

		return bufferedImage.getSubimage(
			x, y, croppedRectangle.width, croppedRectangle.height);
	}

	protected FileEntry getTempImageFileEntry(PortletRequest portletRequest)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return TempFileUtil.getTempFile(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
			getTempImageFileName(portletRequest), getTempImageFolderName());
	}

	protected String getTempImageFileName(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return String.valueOf(themeDisplay.getCompanyId());
	}

	protected String getTempImageFolderName() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	protected void saveTempImageFile(ActionRequest actionRequest)
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

				renderedImage = getCroppedRenderedImage(
					renderedImage, height, width, x, y);
			}

			byte[] bytes = ImageToolUtil.getBytes(
				renderedImage, imageBag.getType());

			saveTempImageFile(actionRequest, bytes);
		}
		catch (NoSuchFileEntryException nsfee) {
			throw new UploadException(nsfee);
		}
		finally {
			StreamUtil.cleanUp(tempImageStream);

			if (tempFileEntry != null) {
				TempFileUtil.deleteTempFile(tempFileEntry.getFileEntryId());
			}
		}
	}

	protected void saveTempImageFile(
			PortletRequest portletRequest, byte[] bytes)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = CompanyServiceUtil.updateLogo(
			themeDisplay.getCompanyId(), bytes);

		themeDisplay.setCompany(company);
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

	private static Log _log = LogFactoryUtil.getLog(
		EditCompanyLogoAction.class);

}