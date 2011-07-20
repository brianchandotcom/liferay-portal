/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallerydisplay.lar;

import com.liferay.portal.kernel.image.ImageProcessor;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.imagegallery.DuplicateImageNameException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGFolderConstants;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGFolderUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGImageUtil;
import com.liferay.util.PwdGenerator;

import java.io.File;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Juan Fernández
 */
public class IGPortletDataHandlerImpl extends BasePortletDataHandler {

	public static void exportImage(
			PortletDataContext portletDataContext, Element foldersElement,
			Element imagesElement, IGImage image, boolean checkDateRange)
		throws Exception {

		if (checkDateRange &&
			!portletDataContext.isWithinDateRange(image.getModifiedDate())) {

			return;
		}

		if (foldersElement != null) {
			exportParentFolder(
				portletDataContext, foldersElement, image.getFolderId());
		}

		String path = getImagePath(portletDataContext, image);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element imageElement = imagesElement.addElement("image");

		Image largeImage = ImageUtil.findByPrimaryKey(image.getLargeImageId());

		image.setImageType(largeImage.getType());

		portletDataContext.addPermissions(IGImage.class, image.getImageId());

		if (!portletDataContext.isPerformDirectBinaryImport()) {
			String binPath = getImageBinPath(portletDataContext, image);

			imageElement.addAttribute("bin-path", binPath);

			portletDataContext.addZipEntry(binPath, largeImage.getTextObj());
		}

		portletDataContext.addClassedModel(
			imageElement, path, image, _NAMESPACE);
	}

	public static String getImagePath(
		PortletDataContext portletDataContext, IGImage image) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(
				PortletKeys.IMAGE_GALLERY_DISPLAY));
		sb.append("/images/");
		sb.append(image.getImageId());
		sb.append(".xml");

		return sb.toString();
	}

	public static void importFolder(
			PortletDataContext portletDataContext, Element folderElement)
		throws Exception {

		String path = folderElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		IGFolder folder = (IGFolder)portletDataContext.getZipEntryAsObject(
			path);

		importFolder(portletDataContext, path, folder);
	}

	public static void importImage(
			PortletDataContext portletDataContext, Element imageElement)
		throws Exception {

		String path = imageElement.attributeValue("path");

		String binPath = imageElement.attributeValue("bin-path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		IGImage image = (IGImage)portletDataContext.getZipEntryAsObject(path);

		importImage(portletDataContext, imageElement, image, binPath);
	}

	@Override
	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndImages, _categories, _ratings, _tags
		};
	}

	@Override
	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndImages, _categories, _ratings, _tags
		};
	}

	@Override
	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	@Override
	public boolean isPublishToLiveByDefault() {
		return PropsValues.IG_PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	protected static void importFolder(
			PortletDataContext portletDataContext, String folderPath,
			IGFolder folder)
		throws Exception {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				IGFolder.class);

		long parentFolderId = MapUtil.getLong(
			folderPKs, folder.getParentFolderId(), folder.getParentFolderId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			folderPath, folder, _NAMESPACE);

		if ((parentFolderId != IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(parentFolderId == folder.getParentFolderId())) {

			String path = getImportFolderPath(
				portletDataContext, parentFolderId);

			IGFolder parentFolder =
				(IGFolder)portletDataContext.getZipEntryAsObject(path);

			importFolder(portletDataContext, path, parentFolder);

			parentFolderId = MapUtil.getLong(
				folderPKs, folder.getParentFolderId(),
				folder.getParentFolderId());
		}

		IGFolder importedFolder = null;

		if (portletDataContext.isDataStrategyMirror()) {
			IGFolder existingFolder = IGFolderUtil.fetchByUUID_G(
				folder.getUuid(), portletDataContext.getScopeGroupId());

			if (existingFolder == null) {
				String name = getFolderName(
					null, portletDataContext.getCompanyId(),
					portletDataContext.getScopeGroupId(), parentFolderId,
					folder.getName(), 2);

				serviceContext.setUuid(folder.getUuid());

				importedFolder = IGFolderLocalServiceUtil.addFolder(
					userId, parentFolderId, name, folder.getDescription(),
					serviceContext);
			}
			else {
				String name = getFolderName(
					folder.getUuid(), portletDataContext.getCompanyId(),
					portletDataContext.getScopeGroupId(), parentFolderId,
					folder.getName(), 2);

				importedFolder = IGFolderLocalServiceUtil.updateFolder(
					existingFolder.getFolderId(), parentFolderId, name,
					folder.getDescription(), false,
					serviceContext);
			}
		}
		else {
			String name = getFolderName(
				null, portletDataContext.getCompanyId(),
				portletDataContext.getScopeGroupId(), parentFolderId,
				folder.getName(), 2);

			importedFolder = IGFolderLocalServiceUtil.addFolder(
				userId, parentFolderId, name, folder.getDescription(),
				serviceContext);
		}

		portletDataContext.importClassedModel(
			folder, importedFolder, _NAMESPACE);
	}

	protected static void importImage(
			PortletDataContext portletDataContext, Element imageElement,
			IGImage image, String binPath)
		throws Exception {

		long userId = portletDataContext.getUserId(image.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				IGFolder.class);

		long folderId = MapUtil.getLong(
			folderPKs, image.getFolderId(), image.getFolderId());

		byte[] bytes = null;

		if (Validator.isNull(binPath) &&
			portletDataContext.isPerformDirectBinaryImport()) {

			Image largeImage = ImageUtil.findByPrimaryKey(
				image.getLargeImageId());

			bytes = largeImage.getTextObj();
		}
		else {
			bytes = portletDataContext.getZipEntryAsByteArray(binPath);
		}

		if (bytes == null) {
			_log.error(
				"Could not find image file for image " + image.getImageId());

			return;
		}

		File imageFile = File.createTempFile(
			String.valueOf(image.getPrimaryKey()),
			StringPool.PERIOD + image.getImageType());

		FileUtil.write(imageFile, bytes);

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			imageElement, image, _NAMESPACE);

		if ((folderId != IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId == image.getFolderId())) {

			String path = getImportFolderPath(portletDataContext, folderId);

			IGFolder folder = (IGFolder)portletDataContext.getZipEntryAsObject(
				path);

			importFolder(portletDataContext, path, folder);

			folderId = MapUtil.getLong(
				folderPKs, image.getFolderId(), image.getFolderId());
		}

		IGImage importedImage = null;

		if (portletDataContext.isDataStrategyMirror()) {
			IGImage existingImage = IGImageUtil.fetchByUUID_G(
				image.getUuid(), portletDataContext.getScopeGroupId());

			if (existingImage == null) {
				String imageName = image.getName();

				IGImage existingNameImage = getImage(
					portletDataContext.getScopeGroupId(), image.getFolderId(),
					imageName, image.getImageType());

				if (existingNameImage != null) {
					if (portletDataContext.
							isDataStrategyMirrorWithOverwritting()) {

						IGImageLocalServiceUtil.deleteIGImage(
							existingNameImage);
					}
					else {
						String originalName = imageName;

						for (int i = 1;; i++) {
							imageName = originalName + StringPool.SPACE + i;

							existingNameImage = getImage(
								portletDataContext.getScopeGroupId(),
								image.getFolderId(), imageName,
								image.getImageType());

							if (existingNameImage == null) {
								break;
							}
						}
					}
				}

				serviceContext.setUuid(image.getUuid());

				importedImage = IGImageLocalServiceUtil.addImage(
					userId, portletDataContext.getScopeGroupId(), folderId,
					imageName, image.getDescription(), imageFile,
					image.getImageType(), serviceContext);
			}
			else {
				importedImage = IGImageLocalServiceUtil.updateImage(
					userId, existingImage.getImageId(),
					portletDataContext.getScopeGroupId(), folderId,
					image.getName(), image.getDescription(), imageFile,
					image.getImageType(), serviceContext);
			}
		}
		else {
			String name = image.getName();

			try {
				importedImage = IGImageLocalServiceUtil.addImage(
					userId, portletDataContext.getScopeGroupId(), folderId,
					name, image.getDescription(), imageFile,
					image.getImageType(), serviceContext);
			}
			catch (DuplicateImageNameException dine) {
				String[] nameParts = name.split("\\.", 2);

				name = nameParts[0] + PwdGenerator.getPassword();

				if (nameParts.length > 1) {
					name += StringPool.PERIOD + nameParts[1];
				}

				importedImage = IGImageLocalServiceUtil.addImage(
					userId, portletDataContext.getScopeGroupId(), folderId,
					name, image.getDescription(), imageFile,
					image.getImageType(), serviceContext);
			}
		}

		portletDataContext.importClassedModel(image, importedImage, _NAMESPACE);
	}

	protected static void exportFolder(
			PortletDataContext portletDataContext, Element foldersElement,
			Element imagesElement, IGFolder folder)
		throws Exception {

		if (portletDataContext.isWithinDateRange(folder.getModifiedDate())) {
			exportParentFolder(
				portletDataContext, foldersElement, folder.getParentFolderId());

			String path = getFolderPath(
				portletDataContext, folder.getFolderId());

			if (portletDataContext.isPathNotProcessed(path)) {
				Element folderElement = foldersElement.addElement("folder");

				portletDataContext.addClassedModel(
					folderElement, path, folder, _NAMESPACE);
			}
		}

		List<IGImage> images = IGImageUtil.findByG_F(
			folder.getGroupId(), folder.getFolderId());

		for (IGImage image : images) {
			exportImage(
				portletDataContext, foldersElement, imagesElement, image, true);
		}
	}

	protected static void exportParentFolder(
			PortletDataContext portletDataContext, Element foldersElement,
			long folderId)
		throws Exception {

		if (folderId == IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		IGFolder folder = IGFolderUtil.findByPrimaryKey(folderId);

		exportParentFolder(
			portletDataContext, foldersElement, folder.getParentFolderId());

		String path = getFolderPath(portletDataContext, folder.getFolderId());

		if (portletDataContext.isPathNotProcessed(path)) {
			Element folderElement = foldersElement.addElement("folder");

			portletDataContext.addClassedModel(
				folderElement, path, folder, _NAMESPACE);
		}
	}

	protected static String getFolderName(
			String uuid, long companyId, long groupId, long parentFolderId,
			String name, int count)
		throws Exception {

		IGFolder folder = IGFolderUtil.fetchByG_P_N(
			groupId, parentFolderId, name);

		if (folder == null) {
			return name;
		}

		if (Validator.isNotNull(uuid) && uuid.equals(folder.getUuid())) {
			return name;
		}

		if (Pattern.matches(".* \\(\\d+\\)", name)) {
			int pos = name.lastIndexOf(" (");

			name = name.substring(0, pos);
		}

		StringBundler sb = new StringBundler(5);

		sb.append(name);
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(count);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		name = sb.toString();

		return getFolderName(
			uuid, companyId, groupId, parentFolderId, name, ++count);
	}

	protected static String getFolderPath(
		PortletDataContext portletDataContext, long folderId) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(
				PortletKeys.IMAGE_GALLERY_DISPLAY));
		sb.append("/folders/");
		sb.append(folderId);
		sb.append(".xml");

		return sb.toString();
	}

	protected static IGImage getImage(
			long groupId, long folderId, String name, String imageType)
		throws Exception{

		List<IGImage> images = IGImageUtil.findByG_F_N(
			groupId, folderId, name);

		if (imageType.equals("jpeg")) {
			imageType = ImageProcessor.TYPE_JPEG;
		}
		else if (imageType.equals("tif")) {
			imageType = ImageProcessor.TYPE_TIFF;
		}

		for (IGImage image : images) {
			if (imageType.equals(image.getImageType())) {
				return image;
			}
		}

		return null;
	}

	protected static String getImageBinPath(
		PortletDataContext portletDataContext, IGImage image) {

		StringBundler sb = new StringBundler(5);

		sb.append(
			portletDataContext.getPortletPath(
				PortletKeys.IMAGE_GALLERY_DISPLAY));
		sb.append("/bin/");
		sb.append(image.getImageId());
		sb.append(StringPool.PERIOD);
		sb.append(image.getImageType());

		return sb.toString();
	}

	protected static String getImportFolderPath(
		PortletDataContext portletDataContext, long folderId) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getSourcePortletPath(
				PortletKeys.IMAGE_GALLERY_DISPLAY));
		sb.append("/folders/");
		sb.append(folderId);
		sb.append(".xml");

		return sb.toString();
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (!portletDataContext.addPrimaryKey(
				IGPortletDataHandlerImpl.class, "deleteData")) {

			IGFolderLocalServiceUtil.deleteFolders(
				portletDataContext.getScopeGroupId());

			IGImageLocalServiceUtil.deleteImages(
				portletDataContext.getScopeGroupId(),
				IGFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		}

		return null;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.imagegallery",
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("image-gallery");

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Element foldersElement = rootElement.addElement("folders");
		Element imagesElement = rootElement.addElement("images");

		List<IGFolder> folders = IGFolderUtil.findByGroupId(
			portletDataContext.getScopeGroupId());

		for (IGFolder folder : folders) {
			exportFolder(
				portletDataContext, foldersElement, imagesElement, folder);
		}

		List<IGImage> images = IGImageUtil.findByG_F(
			portletDataContext.getScopeGroupId(),
			IGFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (IGImage image : images) {
			exportImage(portletDataContext, null, imagesElement, image, true);
		}

		return document.formattedString();
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.imagegallery",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element foldersElement = rootElement.element("folders");

		List<Element> folderElements = foldersElement.elements("folder");

		for (Element folderElement : folderElements) {
			String path = folderElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			IGFolder folder = (IGFolder)portletDataContext.getZipEntryAsObject(
				path);

			importFolder(portletDataContext, path, folder);
		}

		Element imagesElement = rootElement.element("images");

		List<Element> imageElements = imagesElement.elements("image");

		for (Element imageElement : imageElements) {
			String path = imageElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			IGImage image = (IGImage)portletDataContext.getZipEntryAsObject(
				path);

			String binPath = imageElement.attributeValue("bin-path");

			importImage(portletDataContext, imageElement, image, binPath);
		}

		return null;
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final String _NAMESPACE = "image_gallery";

	private static Log _log = LogFactoryUtil.getLog(
		IGPortletDataHandlerImpl.class);

	private static PortletDataHandlerBoolean _categories =
		new PortletDataHandlerBoolean(_NAMESPACE, "categories");

	private static PortletDataHandlerBoolean _foldersAndImages =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "folders-and-images", true, true);

	private static PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

}