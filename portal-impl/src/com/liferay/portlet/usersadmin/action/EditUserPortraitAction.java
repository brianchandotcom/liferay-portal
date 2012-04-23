/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.usersadmin.action;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.UserPortraitSizeException;
import com.liferay.portal.UserPortraitTypeException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TempFileUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.portlet.PortletRequestUtil;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class EditUserPortraitAction extends PortletAction {

	public static final String TEMP_FOLDER_NAME =
		EditUserPortraitAction.class.getName();

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			String command = ParamUtil.getString(
				actionRequest, Constants.CMD, StringPool.BLANK);

			if (command.equals(Constants.ADD_TEMP)) {
				saveTempPortraitFile(actionRequest);
			}
			else {
				File tempPortraitFile = getTempPortraitFile(actionRequest);

				InputStream inputStream = new FileInputStream(tempPortraitFile);

				byte[] bytes = FileUtil.getBytes(inputStream);

				updatePortrait(actionRequest, actionResponse, bytes);

				FileUtil.delete(tempPortraitFile);

				sendRedirect(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchUserException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.users_admin.error");
			}
			else if (e instanceof UploadException ||
					 e instanceof UserPortraitSizeException ||
					 e instanceof UserPortraitTypeException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		return mapping.findForward(getForward(
			renderRequest, "portlet.users_admin.edit_user_portrait"));
	}

	@Override
	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		try {
			String command = ParamUtil.getString(
				resourceRequest, Constants.CMD, StringPool.BLANK);

			if (command.equals(Constants.GET_TEMP)) {
				HttpServletResponse response =
					PortalUtil.getHttpServletResponse(resourceResponse);

				File tempPortraitFile = getTempPortraitFile(resourceRequest);

				writeImage(response, tempPortraitFile);
			}
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	protected RenderedImage cropImage(
		RenderedImage renderedImage, int x, int y, int width, int height) {

		Rectangle goal = new Rectangle(width, height);

		Rectangle clip = goal.intersection(new Rectangle(
			renderedImage.getWidth(), renderedImage.getHeight()));

		BufferedImage bufferedImage = ImageToolUtil.getBufferedImage(
			renderedImage);

		BufferedImage clippedImage = bufferedImage.getSubimage(
			x, y, clip.width, clip.height);

		return clippedImage;
	}

	protected File getTempPortraitFile(PortletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		File tempFile = TempFileUtil.getTempFile(
			themeDisplay.getUserId(), StringPool.BLANK, TEMP_FOLDER_NAME);

		return new File(tempFile.getParentFile(), "test.jpg");
	}

	protected void saveTempPortraitFile(ActionRequest actionRequest)
		throws Exception {

		if (_log.isDebugEnabled()) {
			PortletRequestUtil.testMultipartWithCommonsFileUpload(
				actionRequest);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		TempFileUtil.addTempFile(
			themeDisplay.getUserId(), StringPool.BLANK, TEMP_FOLDER_NAME,
			uploadPortletRequest.getFileAsStream("fileName"));

		File source = TempFileUtil.getTempFile(
			themeDisplay.getUserId(), StringPool.BLANK, TEMP_FOLDER_NAME);

		File destination = new File(source.getParent(), "test.jpg");

		FileUtil.move(source, destination);
	}

	protected void updatePortrait(
			ActionRequest actionRequest, ActionResponse actionResponse,
			byte[] bytes)
		throws Exception {

		String cropRegionJson = ParamUtil.getString(
			actionRequest, "cropRegion");

		if (Validator.isNull(cropRegionJson)) {
			return;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			cropRegionJson);

		int width = jsonObject.getInt("width");
		int height = jsonObject.getInt("height");
		int x = jsonObject.getInt("x");
		int y = jsonObject.getInt("y");

		ImageBag imageBag = ImageToolUtil.read(bytes);

		RenderedImage renderedImage = imageBag.getRenderedImage();

		if (renderedImage == null) {
			throw new UserPortraitTypeException();
		}

		RenderedImage croppedImage = cropImage(
			renderedImage, x, y, width, height);

		User user = PortalUtil.getSelectedUser(actionRequest);

		UserServiceUtil.updatePortrait(
			user.getUserId(), croppedImage, imageBag.getType());
	}

	protected void writeImage(HttpServletResponse response, File imageFile)
		throws IOException {

		ImageBag imageBag = ImageToolUtil.read(imageFile);

		byte[] bytes = ImageToolUtil.getBytes(
			imageBag.getRenderedImage(), imageBag.getType());

		String contentType = MimeTypesUtil.getContentType(
			"A." + imageBag.getType());

		response.setContentType(contentType);

		ServletResponseUtil.write(response, bytes);
	}

	private static Log _log = LogFactoryUtil.getLog(
		EditUserPortraitAction.class);

}