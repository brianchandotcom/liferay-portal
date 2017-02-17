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

package com.liferay.portal.kernel.service;

import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class ServiceContextFactory {

	public static ServiceContext getInstance(HttpServletRequest request)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		// Theme display

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			serviceContext.setCompanyId(themeDisplay.getCompanyId());
			serviceContext.setLanguageId(themeDisplay.getLanguageId());

			String layoutURL = PortalUtil.getLayoutURL(themeDisplay);

			String canonicalURL = PortalUtil.getCanonicalURL(
				layoutURL, themeDisplay, themeDisplay.getLayout(), true);

			String fullCanonicalURL = canonicalURL;

			if (!HttpUtil.hasProtocol(layoutURL)) {
				fullCanonicalURL = PortalUtil.getCanonicalURL(
					PortalUtil.getPortalURL(themeDisplay) + layoutURL,
					themeDisplay, themeDisplay.getLayout(), true);
			}

			serviceContext.setLayoutFullURL(fullCanonicalURL);
			serviceContext.setLayoutURL(canonicalURL);
			serviceContext.setPlid(themeDisplay.getPlid());
			serviceContext.setScopeGroupId(themeDisplay.getScopeGroupId());
			serviceContext.setSignedIn(themeDisplay.isSignedIn());
			serviceContext.setTimeZone(themeDisplay.getTimeZone());

			User user = themeDisplay.getUser();

			serviceContext.setUserDisplayURL(user.getDisplayURL(themeDisplay));
			serviceContext.setUserId(user.getUserId());
		}
		else {
			serviceContext.setCompanyId(PortalUtil.getCompanyId(request));

			Group guestGroup = GroupLocalServiceUtil.getGroup(
				serviceContext.getCompanyId(), GroupConstants.GUEST);

			serviceContext.setScopeGroupId(guestGroup.getGroupId());

			User user = null;

			try {
				user = PortalUtil.getUser(request);
			}
			catch (NoSuchUserException nsue) {

				// LPS-24160

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nsue, nsue);
				}
			}

			if (user != null) {
				serviceContext.setSignedIn(!user.isDefaultUser());
				serviceContext.setUserId(user.getUserId());
			}
			else {
				serviceContext.setSignedIn(false);
			}
		}

		serviceContext.setPortalURL(PortalUtil.getPortalURL(request));
		serviceContext.setPathMain(PortalUtil.getPathMain());
		serviceContext.setPathFriendlyURLPrivateGroup(
			PortalUtil.getPathFriendlyURLPrivateGroup());
		serviceContext.setPathFriendlyURLPrivateUser(
			PortalUtil.getPathFriendlyURLPrivateUser());
		serviceContext.setPathFriendlyURLPublic(
			PortalUtil.getPathFriendlyURLPublic());

		// Attributes

		Map<String, Serializable> attributes = new HashMap<>();

		Map<String, String[]> parameters = request.getParameterMap();

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();

			if (ArrayUtil.isNotEmpty(values)) {
				if (values.length == 1) {
					attributes.put(name, values[0]);
				}
				else {
					attributes.put(name, values);
				}
			}
		}

		serviceContext.setAttributes(attributes);

		// Command

		serviceContext.setCommand(ParamUtil.getString(request, Constants.CMD));

		// Current URL

		serviceContext.setCurrentURL(PortalUtil.getCurrentURL(request));

		// Form date

		long formDateLong = ParamUtil.getLong(request, "formDate");

		if (formDateLong > 0) {
			serviceContext.setFormDate(new Date(formDateLong));
		}

		// Permissions

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			request);

		if (!modelPermissions.isEmpty()) {
			serviceContext.setModelPermissions(modelPermissions);
		}
		else {
			serviceContext.setAddGroupPermissions(
				ParamUtil.getBoolean(request, "addGroupPermissions"));
			serviceContext.setAddGuestPermissions(
				ParamUtil.getBoolean(request, "addGuestPermissions"));
			serviceContext.setGroupPermissions(
				PortalUtil.getGroupPermissions(request));
			serviceContext.setGuestPermissions(
				PortalUtil.getGuestPermissions(request));
		}

		// Portlet preferences ids

		String portletId = PortalUtil.getPortletId(request);

		if (Validator.isNotNull(portletId)) {
			serviceContext.setPortletId(portletId);
		}

		// Request

		Map<String, String> headerMap = new HashMap<>();

		Enumeration<String> enu = request.getHeaderNames();

		while (enu.hasMoreElements()) {
			String header = enu.nextElement();

			String value = request.getHeader(header);

			headerMap.put(header, value);
		}

		serviceContext.setHeaders(headerMap);

		serviceContext.setRemoteAddr(request.getRemoteAddr());
		serviceContext.setRemoteHost(request.getRemoteHost());
		serviceContext.setRequest(request);

		// Asset

		Map<String, String[]> parameterMap = request.getParameterMap();

		List<Long> assetCategoryIdsList = new ArrayList<>();

		boolean updateAssetCategoryIds = false;

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String name = entry.getKey();

			if (name.startsWith("assetCategoryIds")) {
				updateAssetCategoryIds = true;

				long[] assetVocabularyAssetCategoryIds = StringUtil.split(
					ParamUtil.getString(request, name), 0L);

				for (long assetCategoryId : assetVocabularyAssetCategoryIds) {
					assetCategoryIdsList.add(assetCategoryId);
				}
			}
		}

		if (updateAssetCategoryIds) {
			serviceContext.setAssetCategoryIds(
				ArrayUtil.toArray(
					assetCategoryIdsList.toArray(
						new Long[assetCategoryIdsList.size()])));
		}

		serviceContext.setAssetEntryVisible(
			ParamUtil.getBoolean(request, "assetEntryVisible", true));

		String assetLinkEntryIdsString = request.getParameter(
			"assetLinksSearchContainerPrimaryKeys");

		if (assetLinkEntryIdsString != null) {
			serviceContext.setAssetLinkEntryIds(
				StringUtil.split(assetLinkEntryIdsString, 0L));
		}

		serviceContext.setAssetPriority(
			ParamUtil.getDouble(request, "assetPriority"));

		String assetTagNamesString = request.getParameter("assetTagNames");

		if (assetTagNamesString != null) {
			serviceContext.setAssetTagNames(
				StringUtil.split(assetTagNamesString));
		}

		// Workflow

		serviceContext.setWorkflowAction(
			ParamUtil.getInteger(
				request, "workflowAction", WorkflowConstants.ACTION_PUBLISH));

		return serviceContext;
	}

	public static ServiceContext getInstance(PortletRequest portletRequest)
		throws PortalException {

		// Theme display

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (serviceContext != null) {
			serviceContext = (ServiceContext)serviceContext.clone();
		}
		else {
			serviceContext = new ServiceContext();

			serviceContext.setCompanyId(themeDisplay.getCompanyId());
			serviceContext.setLanguageId(themeDisplay.getLanguageId());
			serviceContext.setLayoutFullURL(
				PortalUtil.getLayoutFullURL(themeDisplay));
			serviceContext.setLayoutURL(PortalUtil.getLayoutURL(themeDisplay));
			serviceContext.setPathFriendlyURLPrivateGroup(
				PortalUtil.getPathFriendlyURLPrivateGroup());
			serviceContext.setPathFriendlyURLPrivateUser(
				PortalUtil.getPathFriendlyURLPrivateUser());
			serviceContext.setPathFriendlyURLPublic(
				PortalUtil.getPathFriendlyURLPublic());
			serviceContext.setPathMain(PortalUtil.getPathMain());
			serviceContext.setPlid(themeDisplay.getPlid());
			serviceContext.setPortalURL(
				PortalUtil.getPortalURL(portletRequest));
			serviceContext.setSignedIn(themeDisplay.isSignedIn());
			serviceContext.setTimeZone(themeDisplay.getTimeZone());

			User user = themeDisplay.getUser();

			serviceContext.setUserDisplayURL(user.getDisplayURL(themeDisplay));
			serviceContext.setUserId(user.getUserId());
		}

		serviceContext.setScopeGroupId(themeDisplay.getScopeGroupId());

		// Attributes

		Map<String, Serializable> attributes = new HashMap<>();

		Enumeration<String> enu = portletRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String param = enu.nextElement();

			String[] values = portletRequest.getParameterValues(param);

			if (ArrayUtil.isNotEmpty(values)) {
				if (values.length == 1) {
					attributes.put(param, values[0]);
				}
				else {
					attributes.put(param, values);
				}
			}
		}

		serviceContext.setAttributes(attributes);

		// Command

		String cmd = ParamUtil.getString(portletRequest, Constants.CMD);

		serviceContext.setCommand(cmd);

		// Current URL

		String currentURL = PortalUtil.getCurrentURL(portletRequest);

		serviceContext.setCurrentURL(currentURL);

		// Form date

		long formDateLong = ParamUtil.getLong(portletRequest, "formDate");

		if (formDateLong > 0) {
			Date formDate = new Date(formDateLong);

			serviceContext.setFormDate(formDate);
		}

		// Permissions

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			portletRequest);

		if (!modelPermissions.isEmpty()) {
			serviceContext.setModelPermissions(modelPermissions);
		}
		else {
			boolean addGroupPermissions = ParamUtil.getBoolean(
				portletRequest, "addGroupPermissions");
			boolean addGuestPermissions = ParamUtil.getBoolean(
				portletRequest, "addGuestPermissions");
			String[] groupPermissions = PortalUtil.getGroupPermissions(
				portletRequest);
			String[] guestPermissions = PortalUtil.getGuestPermissions(
				portletRequest);

			serviceContext.setAddGroupPermissions(addGroupPermissions);
			serviceContext.setAddGuestPermissions(addGuestPermissions);
			serviceContext.setGroupPermissions(groupPermissions);
			serviceContext.setGuestPermissions(guestPermissions);
		}

		// Portlet preferences ids

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		serviceContext.setPortletId(PortalUtil.getPortletId(portletRequest));

		// Request

		Map<String, String> headerMap = new HashMap<>();

		enu = request.getHeaderNames();

		while (enu.hasMoreElements()) {
			String header = enu.nextElement();

			String value = request.getHeader(header);

			headerMap.put(header, value);
		}

		serviceContext.setHeaders(headerMap);

		serviceContext.setRemoteAddr(request.getRemoteAddr());
		serviceContext.setRemoteHost(request.getRemoteHost());
		serviceContext.setRequest(request);

		// Asset

		Map<String, String[]> parameterMap = portletRequest.getParameterMap();

		List<Long> assetCategoryIdsList = new ArrayList<>();

		boolean updateAssetCategoryIds = false;

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String name = entry.getKey();

			if (name.startsWith("assetCategoryIds")) {
				updateAssetCategoryIds = true;

				long[] assetVocabularyAssetCategoryIds = StringUtil.split(
					ParamUtil.getString(portletRequest, name), 0L);

				for (long assetCategoryId : assetVocabularyAssetCategoryIds) {
					assetCategoryIdsList.add(assetCategoryId);
				}
			}
		}

		if (updateAssetCategoryIds) {
			long[] assetCategoryIds = ArrayUtil.toArray(
				assetCategoryIdsList.toArray(
					new Long[assetCategoryIdsList.size()]));

			serviceContext.setAssetCategoryIds(assetCategoryIds);
		}

		boolean assetEntryVisible = ParamUtil.getBoolean(
			portletRequest, "assetEntryVisible", true);

		serviceContext.setAssetEntryVisible(assetEntryVisible);

		String assetLinkEntryIdsString = request.getParameter(
			"assetLinksSearchContainerPrimaryKeys");

		if (assetLinkEntryIdsString != null) {
			long[] assetLinkEntryIds = StringUtil.split(
				assetLinkEntryIdsString, 0L);

			serviceContext.setAssetLinkEntryIds(assetLinkEntryIds);
		}

		Double assetPriority = ParamUtil.getDouble(request, "assetPriority");

		serviceContext.setAssetPriority(assetPriority);

		String assetTagNamesString = request.getParameter("assetTagNames");

		if (assetTagNamesString != null) {
			String[] assetTagNames = StringUtil.split(assetTagNamesString);

			serviceContext.setAssetTagNames(assetTagNames);
		}

		// Workflow

		int workflowAction = ParamUtil.getInteger(
			portletRequest, "workflowAction", WorkflowConstants.ACTION_PUBLISH);

		serviceContext.setWorkflowAction(workflowAction);

		return serviceContext;
	}

	public static ServiceContext getInstance(
			String className, HttpServletRequest request)
		throws PortalException {

		ServiceContext serviceContext = getInstance(request);

		// Permissions

		String[] groupPermissions = PortalUtil.getGroupPermissions(
			request, className);
		String[] guestPermissions = PortalUtil.getGuestPermissions(
			request, className);

		if (groupPermissions != null) {
			serviceContext.setGroupPermissions(groupPermissions);
		}

		if (guestPermissions != null) {
			serviceContext.setGuestPermissions(guestPermissions);
		}

		// Expando

		Map<String, Serializable> expandoBridgeAttributes =
			PortalUtil.getExpandoBridgeAttributes(
				ExpandoBridgeFactoryUtil.getExpandoBridge(
					serviceContext.getCompanyId(), className),
				request);

		serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);

		return serviceContext;
	}

	public static ServiceContext getInstance(
			String className, PortletRequest portletRequest)
		throws PortalException {

		ServiceContext serviceContext = getInstance(portletRequest);

		// Permissions

		String[] groupPermissions = PortalUtil.getGroupPermissions(
			portletRequest, className);
		String[] guestPermissions = PortalUtil.getGuestPermissions(
			portletRequest, className);

		if (groupPermissions != null) {
			serviceContext.setGroupPermissions(groupPermissions);
		}

		if (guestPermissions != null) {
			serviceContext.setGuestPermissions(guestPermissions);
		}

		// Expando

		Map<String, Serializable> expandoBridgeAttributes =
			PortalUtil.getExpandoBridgeAttributes(
				ExpandoBridgeFactoryUtil.getExpandoBridge(
					serviceContext.getCompanyId(), className),
				portletRequest);

		serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);

		return serviceContext;
	}

	public static ServiceContext getInstance(
			String className, UploadPortletRequest uploadPortletRequest)
		throws PortalException {

		ServiceContext serviceContext = getInstance(uploadPortletRequest);

		// Permissions

		String[] groupPermissions = PortalUtil.getGroupPermissions(
			uploadPortletRequest, className);
		String[] guestPermissions = PortalUtil.getGuestPermissions(
			uploadPortletRequest, className);

		if (groupPermissions != null) {
			serviceContext.setGroupPermissions(groupPermissions);
		}

		if (guestPermissions != null) {
			serviceContext.setGuestPermissions(guestPermissions);
		}

		// Expando

		Map<String, Serializable> expandoBridgeAttributes =
			PortalUtil.getExpandoBridgeAttributes(
				ExpandoBridgeFactoryUtil.getExpandoBridge(
					serviceContext.getCompanyId(), className),
				uploadPortletRequest);

		serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);

		return serviceContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceContextFactory.class);

}