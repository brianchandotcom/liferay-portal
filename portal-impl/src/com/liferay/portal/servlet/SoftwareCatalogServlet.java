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

package com.liferay.portal.servlet;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceUtil;

import java.io.IOException;

import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jorge Ferrer
 */
public class SoftwareCatalogServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			long groupId = getGroupId(request);
			String version = getVersion(request);
			String baseImageURL = getBaseImageURL(request);
			Date oldestDate = getOldestDate(request);
			int maxNumOfVersions = ParamUtil.getInteger(
				request, "maxNumOfVersions");
			Properties repoSettings = getRepoSettings(request);

			if (_log.isDebugEnabled()) {
				_log.debug("Group ID " + groupId);
				_log.debug("Base image URL " + baseImageURL);
				_log.debug("Oldtest date " + oldestDate);
				_log.debug("Maximum number of versions " + maxNumOfVersions);
			}

			String repositoryXML =
				SCProductEntryLocalServiceUtil.getRepositoryXML(
					groupId, version, baseImageURL, oldestDate,
					maxNumOfVersions, repoSettings);

			ServletResponseUtil.sendFile(
				request, response, null,
				repositoryXML.getBytes(StringPool.UTF8),
				ContentTypes.TEXT_XML_UTF8);
		}
		catch (NoSuchGroupException nsge) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND, nsge, request, response);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			PortalUtil.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e, request,
				response);
		}
	}

	protected String getBaseImageURL(HttpServletRequest request) {
		String portalURL = PortalUtil.getPortalURL(request);

		String pathImage = PortalUtil.getPathImage();

		if (pathImage.startsWith(Http.HTTP_WITH_SLASH) ||
			pathImage.startsWith(Http.HTTPS_WITH_SLASH)) {

			return pathImage + "/software_catalog";
		}
		else {
			return portalURL + pathImage + "/software_catalog";
		}
	}

	protected long getGroupId(HttpServletRequest request)
		throws PortalException {

		long groupId = ParamUtil.getLong(request, "groupId");

		if (groupId <= 0) {
			String path = GetterUtil.getString(request.getPathInfo());

			path = StringUtil.replace(
				path, StringPool.DOUBLE_SLASH, StringPool.SLASH);

			if (Validator.isNotNull(path)) {
				int pos = path.indexOf(CharPool.SLASH, 1);

				if (pos == -1) {
					pos = path.length();
				}

				groupId = GetterUtil.getLong(path.substring(1, pos));
			}
		}

		if (groupId <= 0) {
			long companyId = PortalInstances.getCompanyId(request);

			Group guestGroup = GroupLocalServiceUtil.getGroup(
				companyId, GroupConstants.GUEST);

			groupId = guestGroup.getGroupId();
		}

		return groupId;
	}

	protected Date getOldestDate(HttpServletRequest request) {
		Date oldestDate = null;

		oldestDate = ParamUtil.getDate(
			request, "oldestDate",
			DateFormatFactoryUtil.getSimpleDateFormat("yyyy.MM.dd"), null);

		if (oldestDate == null) {
			int daysOld = ParamUtil.getInteger(request, "maxAge", -1);

			if (daysOld != -1) {
				Calendar cal = Calendar.getInstance();

				cal.add(Calendar.DATE, (0 - daysOld));

				oldestDate = cal.getTime();
			}
		}

		return oldestDate;
	}

	protected Properties getRepoSettings(HttpServletRequest request) {
		Properties repoSettings = new Properties();

		String prefix = "setting_";

		Enumeration<String> enu = request.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith(prefix)) {
				String settingName = name.substring(prefix.length());

				String value = ParamUtil.getString(request, name);

				if (Validator.isNotNull(value)) {
					repoSettings.setProperty(settingName, value);
				}
			}
		}

		return repoSettings;
	}

	protected String getVersion(HttpServletRequest request) {
		String version = ParamUtil.getString(request, "version");

		String prefix =
			PluginPackage.REPOSITORY_XML_FILENAME_PREFIX + StringPool.DASH;
		String extension =
			StringPool.PERIOD + PluginPackage.REPOSITORY_XML_FILENAME_EXTENSION;

		if (Validator.isNull(version)) {
			String path = GetterUtil.getString(request.getPathInfo());

			if (Validator.isNotNull(path)) {
				int x = path.indexOf(prefix);

				if (x != -1) {
					version = path.substring(
						x + prefix.length(), path.indexOf(extension, x));
				}
			}
		}

		if (_log.isDebugEnabled()) {
			if (Validator.isNull(version)) {
				_log.debug("Serving repository for all versions");
			}
			else {
				_log.debug("Serving repository for version " + version);
			}
		}

		return version;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SoftwareCatalogServlet.class);

}