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

package com.liferay.portlet.softwarecatalog.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.softwarecatalog.ProductEntryScreenshotsException;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryServiceUtil;
import com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalServiceUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author Jorge Ferrer
 * @author Philip Jones
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.SOFTWARE_CATALOG,
		"mvc.command.name=/software_catalog/edit_product_entry"
	},
	service = MVCActionCommand.class
)
public class EditProductEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
			updateProductEntry(actionRequest);
		}
		else if (cmd.equals(Constants.DELETE)) {
			deleteProductEntry(actionRequest);
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");
		sendRedirect(actionRequest, actionResponse, redirect);
	}

	protected void deleteProductEntry(ActionRequest actionRequest)
		throws Exception {

		long productEntryId = ParamUtil.getLong(
			actionRequest, "productEntryId");

		SCProductEntryServiceUtil.deleteProductEntry(productEntryId);
	}

	protected List<byte[]> getFullImages(
			UploadPortletRequest uploadPortletRequest)
		throws Exception {

		return getImages(uploadPortletRequest, "fullImage");
	}

	protected List<byte[]> getImages(
			UploadPortletRequest uploadPortletRequest, String imagePrefix)
		throws Exception {

		List<byte[]> images = new ArrayList<>();

		for (String name :
				getSortedParameterNames(uploadPortletRequest, imagePrefix)) {

			String contentType = uploadPortletRequest.getContentType(name);

			if (!MimeTypesUtil.isWebImage(contentType)) {
				throw new ProductEntryScreenshotsException();
			}

			int priority = GetterUtil.getInteger(
				name.substring(imagePrefix.length()));

			boolean preserveScreenshot = ParamUtil.getBoolean(
				uploadPortletRequest, "preserveScreenshot" + priority);

			byte[] bytes = null;

			if (preserveScreenshot) {
				SCProductScreenshot productScreenshot = getProductScreenshot(
					uploadPortletRequest, priority);

				Image image = null;

				if (imagePrefix.equals("fullImage")) {
					image = ImageLocalServiceUtil.getImage(
						productScreenshot.getFullImageId());
				}
				else {
					image = ImageLocalServiceUtil.getImage(
						productScreenshot.getThumbnailId());
				}

				bytes = image.getTextObj();
			}
			else {
				InputStream inputStream = uploadPortletRequest.getFileAsStream(
					name);

				if (inputStream != null) {
					bytes = FileUtil.getBytes(inputStream);
				}
			}

			if (ArrayUtil.isNotEmpty(bytes)) {
				images.add(bytes);
			}
			else {
				throw new ProductEntryScreenshotsException();
			}
		}

		return images;
	}

	protected SCProductScreenshot getProductScreenshot(
			UploadPortletRequest uploadPortletRequest, int priority)
		throws Exception {

		long productEntryId = ParamUtil.getLong(
			uploadPortletRequest, "productEntryId");

		try {
			return SCProductScreenshotLocalServiceUtil.getProductScreenshot(
				productEntryId, priority);
		}
		catch (Exception e) {
			throw new ProductEntryScreenshotsException();
		}
	}

	protected List<String> getSortedParameterNames(
			UploadPortletRequest uploadPortletRequest, String imagePrefix)
		throws Exception {

		List<String> parameterNames = new ArrayList<>();

		Enumeration<String> enu = uploadPortletRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith(imagePrefix)) {
				parameterNames.add(name);
			}
		}

		return ListUtil.sort(parameterNames);
	}

	protected List<byte[]> getThumbnails(
			UploadPortletRequest uploadPortletRequest)
		throws Exception {

		return getImages(uploadPortletRequest, "thumbnail");
	}

	protected void updateProductEntry(ActionRequest actionRequest)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			PortalUtil.getUploadPortletRequest(actionRequest);

		long productEntryId = ParamUtil.getLong(
			actionRequest, "productEntryId");

		String name = ParamUtil.getString(actionRequest, "name");
		String type = ParamUtil.getString(actionRequest, "type");
		String tags = ParamUtil.getString(actionRequest, "tags");
		String shortDescription = ParamUtil.getString(
			actionRequest, "shortDescription");
		String longDescription = ParamUtil.getString(
			actionRequest, "longDescription");
		String pageURL = ParamUtil.getString(actionRequest, "pageURL");
		String author = ParamUtil.getString(actionRequest, "author");
		String repoGroupId = ParamUtil.getString(actionRequest, "repoGroupId");
		String repoArtifactId = ParamUtil.getString(
			actionRequest, "repoArtifactId");

		long[] licenseIds = ParamUtil.getLongValues(actionRequest, "licenses");

		List<byte[]> thumbnails = getThumbnails(uploadPortletRequest);
		List<byte[]> fullImages = getFullImages(uploadPortletRequest);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			SCProductEntry.class.getName(), actionRequest);

		if (productEntryId <= 0) {

			// Add product entry

			SCProductEntryServiceUtil.addProductEntry(
				name, type, tags, shortDescription, longDescription, pageURL,
				author, repoGroupId, repoArtifactId, licenseIds, thumbnails,
				fullImages, serviceContext);
		}
		else {

			// Update product entry

			SCProductEntryServiceUtil.updateProductEntry(
				productEntryId, name, type, tags, shortDescription,
				longDescription, pageURL, author, repoGroupId, repoArtifactId,
				licenseIds, thumbnails, fullImages);
		}
	}

}