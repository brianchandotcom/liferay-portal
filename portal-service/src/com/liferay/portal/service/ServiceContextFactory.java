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

package com.liferay.portal.service;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

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
			serviceContext.setLayoutFullURL(
				PortalUtil.getCanonicalURL(
					PortalUtil.getLayoutFullURL(themeDisplay), themeDisplay,
					themeDisplay.getLayout(), true));
			serviceContext.setLayoutURL(
				PortalUtil.getCanonicalURL(
					PortalUtil.getLayoutURL(themeDisplay), themeDisplay,
					themeDisplay.getLayout(), true));
			serviceContext.setPlid(themeDisplay.getPlid());
			serviceContext.setScopeGroupId(themeDisplay.getScopeGroupId());
			serviceContext.setSignedIn(themeDisplay.isSignedIn());
			serviceContext.setTimeZone(themeDisplay.getTimeZone());

			User user = themeDisplay.getUser();

			serviceContext.setUserDisplayURL(user.getDisplayURL(themeDisplay));
			serviceContext.setUserId(user.getUserId());
		}
		else {
			long companyId = PortalUtil.getCompanyId(request);

			serviceContext.setCompanyId(companyId);

			Group guestGroup = GroupLocalServiceUtil.getGroup(
				serviceContext.getCompanyId(), GroupConstants.GUEST);

			serviceContext.setScopeGroupId(guestGroup.getGroupId());

			long plid = LayoutLocalServiceUtil.getDefaultPlid(
				serviceContext.getScopeGroupId());

			serviceContext.setPlid(plid);

			User user = null;

			try {
				user = PortalUtil.getUser(request);
			}
			catch (NoSuchUserException nsue) {

				// LPS-24160

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

		String cmd = ParamUtil.getString(request, Constants.CMD);

		serviceContext.setCommand(cmd);

		// Current URL

		String currentURL = PortalUtil.getCurrentURL(request);

		serviceContext.setCurrentURL(currentURL);

		// Form date

		long formDateLong = ParamUtil.getLong(request, "formDate");

		if (formDateLong > 0) {
			Date formDate = new Date(formDateLong);

			serviceContext.setFormDate(formDate);
		}

		// Permissions

		boolean addGroupPermissions = ParamUtil.getBoolean(
			request, "addGroupPermissions");
		boolean addGuestPermissions = ParamUtil.getBoolean(
			request, "addGuestPermissions");
		String[] groupPermissions = PortalUtil.getGroupPermissions(request);
		String[] guestPermissions = PortalUtil.getGuestPermissions(request);

		serviceContext.setAddGroupPermissions(addGroupPermissions);
		serviceContext.setAddGuestPermissions(addGuestPermissions);
		serviceContext.setGroupPermissions(groupPermissions);
		serviceContext.setGuestPermissions(guestPermissions);

		// Portlet preferences ids

		String portletId = PortalUtil.getPortletId(request);

		if (Validator.isNotNull(portletId)) {
			PortletPreferencesIds portletPreferencesIds =
				PortletPreferencesFactoryUtil.getPortletPreferencesIds(
					request, portletId);

			serviceContext.setPortletPreferencesIds(portletPreferencesIds);
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
			long[] assetCategoryIds = ArrayUtil.toArray(
				assetCategoryIdsList.toArray(
					new Long[assetCategoryIdsList.size()]));

			serviceContext.setAssetCategoryIds(assetCategoryIds);
		}

		boolean assetEntryVisible = ParamUtil.getBoolean(
			request, "assetEntryVisible", true);

		serviceContext.setAssetEntryVisible(assetEntryVisible);

		String assetLinkEntryIdsString = request.getParameter(
			"assetLinksSearchContainerPrimaryKeys");

		if (assetLinkEntryIdsString != null) {
			long[] assetLinkEntryIds = StringUtil.split(
				assetLinkEntryIdsString, 0L);

			serviceContext.setAssetLinkEntryIds(assetLinkEntryIds);
		}

		String assetTagNamesString = request.getParameter("assetTagNames");

		if (assetTagNamesString != null) {
			String[] assetTagNames = StringUtil.split(assetTagNamesString);

			serviceContext.setAssetTagNames(assetTagNames);
		}

		// Workflow

		int workflowAction = ParamUtil.getInteger(
			request, "workflowAction", WorkflowConstants.ACTION_PUBLISH);

		serviceContext.setWorkflowAction(workflowAction);

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

		// Portlet preferences ids

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		String portletId = PortalUtil.getPortletId(portletRequest);

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				request, portletId);

		serviceContext.setPortletPreferencesIds(portletPreferencesIds);

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

}