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

package com.liferay.portal.editor.fckeditor.receiver.impl;

import com.liferay.portal.editor.fckeditor.command.CommandArgument;
import com.liferay.portal.editor.fckeditor.exception.FCKException;
import com.liferay.portal.editor.fckeditor.receiver.CommandReceiver;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.io.ByteArrayFileInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.upload.LiferayFileItem;
import com.liferay.portal.upload.LiferayFileItemFactory;
import com.liferay.portal.upload.LiferayFileUpload;
import com.liferay.portal.upload.LiferayServletRequest;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Ivica Cardic
 * @author Raymond Augé
 */
public abstract class BaseCommandReceiver implements CommandReceiver {

	@Override
	public void createFolder(
		CommandArgument commandArgument, HttpServletRequest request,
		HttpServletResponse response) {

		Document document = _createDocument();

		Node rootNode = _createRoot(
			document, commandArgument.getCommand(), commandArgument.getType(),
			commandArgument.getCurrentFolder(), StringPool.BLANK);

		Element errorElement = document.createElement("Error");

		rootNode.appendChild(errorElement);

		String returnValue = "0";

		try {
			returnValue = createFolder(commandArgument);
		}
		catch (FCKException fcke) {
			Throwable cause = fcke.getCause();

			returnValue = "110";

			if (cause != null) {
				String causeString = GetterUtil.getString(cause.toString());

				if (causeString.contains("DuplicateFolderNameException")) {
					returnValue = "101";
				}
				else if (causeString.contains("FolderNameException")) {
					returnValue = "102";
				}
				else if (causeString.contains("NoSuchGroupException") ||
						 causeString.contains("PrincipalException")) {

					returnValue = "103";
				}
				else {
					throw fcke;
				}
			}
		}

		errorElement.setAttribute("number", returnValue);

		_writeDocument(document, response);
	}

	@Override
	public void fileUpload(
		CommandArgument commandArgument, HttpServletRequest request,
		HttpServletResponse response) {

		InputStream inputStream = null;

		String returnValue = null;

		try {
			ServletFileUpload servletFileUpload = new LiferayFileUpload(
				new LiferayFileItemFactory(
					UploadServletRequestImpl.getTempDir()), request);

			servletFileUpload.setFileSizeMax(
				PrefsPropsUtil.getLong(
					PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE));

			LiferayServletRequest liferayServletRequest =
				new LiferayServletRequest(request);

			List<FileItem> fileItems = servletFileUpload.parseRequest(
				liferayServletRequest);

			Map<String, Object> fields = new HashMap<String, Object>();

			for (FileItem fileItem : fileItems) {
				if (fileItem.isFormField()) {
					fields.put(fileItem.getFieldName(), fileItem.getString());
				}
				else {
					fields.put(fileItem.getFieldName(), fileItem);
				}
			}

			DiskFileItem diskFileItem = (DiskFileItem)fields.get("NewFile");

			String fileName = StringUtil.replace(
				diskFileItem.getName(), CharPool.BACK_SLASH, CharPool.SLASH);
			String[] fileNameArray = StringUtil.split(fileName, '/');
			fileName = fileNameArray[fileNameArray.length - 1];

			String contentType = diskFileItem.getContentType();

			if (Validator.isNull(contentType) ||
				contentType.equals(ContentTypes.APPLICATION_OCTET_STREAM)) {

				contentType = MimeTypesUtil.getContentType(
					diskFileItem.getStoreLocation());
			}

			if (diskFileItem.isInMemory()) {
				inputStream = diskFileItem.getInputStream();
			}
			else {
				inputStream = new ByteArrayFileInputStream(
					diskFileItem.getStoreLocation(),
					LiferayFileItem.THRESHOLD_SIZE);
			}

			long size = diskFileItem.getSize();

			returnValue = fileUpload(
				commandArgument, fileName, inputStream, contentType, size);
		}
		catch (Exception e) {
			FCKException fcke = null;

			if (e instanceof FCKException) {
				fcke = (FCKException)e;
			}
			else {
				fcke = new FCKException(e);
			}

			Throwable cause = fcke.getCause();

			returnValue = "203";

			if (cause != null) {
				String causeString = GetterUtil.getString(cause.toString());

				if (causeString.contains("DuplicateFileException")) {
					returnValue = "201";
				}
				else if (causeString.contains("NoSuchFolderException")||
						 causeString.contains("NoSuchGroupException")) {

					returnValue = "204";
				}
				else if (causeString.contains("ImageNameException")) {
					returnValue = "205";
				}
				else if (causeString.contains("FileExtensionException") ||
						 causeString.contains("FileNameException")) {

					returnValue = "206";
				}
				else if (causeString.contains("PrincipalException")) {
					returnValue = "207";
				}
				else if (causeString.contains("ImageSizeException") ||
						 causeString.contains("FileSizeException")) {

					returnValue = "208";
				}
				else if (causeString.contains("SystemException")) {
					returnValue = "209";
				}
				else {
					throw fcke;
				}
			}

			_writeUploadResponse(returnValue, response);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}

		_writeUploadResponse(returnValue, response);
	}

	@Override
	public void getFolders(
		CommandArgument commandArgument, HttpServletRequest request,
		HttpServletResponse response) {

		Document document = _createDocument();

		Node rootNode = _createRoot(
			document, commandArgument.getCommand(), commandArgument.getType(),
			commandArgument.getCurrentFolder(), getPath(commandArgument));

		getFolders(commandArgument, document, rootNode);

		_writeDocument(document, response);
	}

	@Override
	public void getFoldersAndFiles(
		CommandArgument commandArgument, HttpServletRequest request,
		HttpServletResponse response) {

		Document document = _createDocument();

		Node rootNode = _createRoot(
			document, commandArgument.getCommand(), commandArgument.getType(),
			commandArgument.getCurrentFolder(), getPath(commandArgument));

		getFoldersAndFiles(commandArgument, document, rootNode);

		_writeDocument(document, response);
	}

	protected abstract String createFolder(CommandArgument commandArgument);

	protected abstract String fileUpload(
		CommandArgument commandArgument, String fileName,
		InputStream inputStream, String contentType, long size);

	protected abstract void getFolders(
		CommandArgument commandArgument, Document document, Node rootNode);

	protected abstract void getFoldersAndFiles(
		CommandArgument commandArgument, Document document, Node rootNode);

	protected String getPath(CommandArgument commandArgument) {
		return StringPool.BLANK;
	}

	protected void getRootFolders(
			CommandArgument commandArgument, Document document,
			Element foldersElement)
		throws Exception {

		List<Group> groups = new UniqueList<Group>();

		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		groupParams.put("usersGroups", new Long(commandArgument.getUserId()));

		groups.addAll(
			GroupLocalServiceUtil.search(
				commandArgument.getCompanyId(), null, null, groupParams,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		List<Organization> userOrgs =
			OrganizationLocalServiceUtil.getUserOrganizations(
				commandArgument.getUserId());

		for (Organization organization : userOrgs) {
			groups.add(0, organization.getGroup());
		}

		if (PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED ||
			PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED) {

			Group userGroup = GroupLocalServiceUtil.getUserGroup(
				commandArgument.getCompanyId(), commandArgument.getUserId());

			groups.add(0, userGroup);
		}

		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			commandArgument.getCompanyId());

		groups.add(0, companyGroup);

		ThemeDisplay themeDisplay = commandArgument.getThemeDisplay();

		long scopeGroupId = themeDisplay.getScopeGroupId();

		HttpServletRequest request = commandArgument.getHttpServletRequest();

		String portletId = ParamUtil.getString(request, "p_p_id");

		for (Group group : groups) {
			Element folderElement = document.createElement("Folder");

			foldersElement.appendChild(folderElement);

			boolean setNameAttribute = false;

			if (group.hasStagingGroup()) {
				Group stagingGroup = group.getStagingGroup();

				if ((stagingGroup.getGroupId() == scopeGroupId) &&
					group.isStagedPortlet(portletId) &&
					!group.isStagedRemotely() && isStagedData(group)) {

					folderElement.setAttribute(
						"name",
						stagingGroup.getGroupId() + " - " +
							HtmlUtil.escape(stagingGroup.getDescriptiveName()));

					setNameAttribute = true;
				}
			}

			if (!setNameAttribute) {
				folderElement.setAttribute(
					"name",
					group.getGroupId() + " - " +
						HtmlUtil.escape(group.getDescriptiveName()));
			}
		}
	}

	protected String getSize() {
		return getSize(0);
	}

	protected String getSize(long size) {
		return String.valueOf(Math.ceil(size / 1000));
	}

	protected boolean isStagedData(Group group) {
		return true;
	}

	private Document _createDocument() {
		try {
			DocumentBuilderFactory documentBuilderFactory =
				DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder =
				documentBuilderFactory.newDocumentBuilder();

			return documentBuilder.newDocument();
		}
		catch (ParserConfigurationException pce) {
			throw new FCKException(pce);
		}
	}

	private Node _createRoot(
		Document document, String command, String resourceType, String path,
		String url) {

		Element rootElement = document.createElement("Connector");

		document.appendChild(rootElement);

		rootElement.setAttribute("command", command);
		rootElement.setAttribute("resourceType", resourceType);

		Element currentFolderElement = document.createElement("CurrentFolder");

		rootElement.appendChild(currentFolderElement);

		currentFolderElement.setAttribute("path", path);
		currentFolderElement.setAttribute("url", url);

		return rootElement;
	}

	private void _writeDocument(
		Document document, HttpServletResponse response) {

		try {
			Element documentElement = document.getDocumentElement();

			documentElement.normalize();

			TransformerFactory transformerFactory =
				TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();

			DOMSource domSource = new DOMSource(document);

			if (_log.isDebugEnabled()) {
				StreamResult streamResult = new StreamResult(System.out);

				transformer.transform(domSource, streamResult);
			}

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter printWriter = response.getWriter();

			StreamResult streamResult = new StreamResult(printWriter);

			transformer.transform(domSource, streamResult);

			printWriter.flush();
			printWriter.close();
		}
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	private void _writeUploadResponse(
		String returnValue, HttpServletResponse response) {

		try {
			StringBundler sb = new StringBundler(7);

			String newName = StringPool.BLANK;

			sb.append("<script type=\"text/javascript\">");
			sb.append("window.parent.frames['frmUpload'].OnUploadCompleted(");
			sb.append(returnValue);
			sb.append(",'");
			sb.append(newName);
			sb.append("');");
			sb.append("</script>");

			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter printWriter = null;

			printWriter = response.getWriter();

			printWriter.print(sb.toString());

			printWriter.flush();
			printWriter.close();
		}
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BaseCommandReceiver.class);

}