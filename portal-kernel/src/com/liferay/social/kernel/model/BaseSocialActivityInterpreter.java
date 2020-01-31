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

package com.liferay.social.kernel.model;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.social.kernel.service.SocialActivityLocalServiceUtil;
import com.liferay.social.kernel.service.SocialActivitySetLocalServiceUtil;
import com.liferay.social.kernel.service.persistence.SocialActivityUtil;
import com.liferay.trash.kernel.util.TrashUtil;

import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 */
public abstract class BaseSocialActivityInterpreter
	implements SocialActivityInterpreter {

	@Override
	public String getSelector() {
		return StringPool.BLANK;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ServiceContext serviceContext)
		throws Exception {

		return hasPermissions(
			permissionChecker, activity, actionId, serviceContext);
	}

	@Override
	public SocialActivityFeedEntry interpret(
		SocialActivity activity, ServiceContext serviceContext) {

		try {
			return doInterpret(activity, serviceContext);
		}
		catch (Exception exception) {
			_log.error("Unable to interpret activity", exception);
		}

		return null;
	}

	@Override
	public SocialActivityFeedEntry interpret(
		SocialActivitySet activitySet, ServiceContext serviceContext) {

		try {
			return doInterpret(activitySet, serviceContext);
		}
		catch (Exception exception) {
			_log.error("Unable to interpret activity set", exception);
		}

		return null;
	}

	@Override
	public void updateActivitySet(long activityId) throws PortalException {
		SocialActivity activity = SocialActivityUtil.fetchByPrimaryKey(
			activityId);

		if ((activity == null) || (activity.getActivitySetId() > 0)) {
			return;
		}

		long activitySetId = getActivitySetId(activityId);

		if (activitySetId > 0) {
			SocialActivitySetLocalServiceUtil.incrementActivityCount(
				activitySetId, activityId);
		}
		else {
			SocialActivitySetLocalServiceUtil.addActivitySet(activityId);
		}
	}

	protected String addNoSuchEntryRedirect(
			String url, String className, long classPK,
			ServiceContext serviceContext)
		throws Exception {

		String viewEntryURL = getViewEntryURL(
			className, classPK, serviceContext);

		if (Validator.isNull(viewEntryURL)) {
			return url;
		}

		return HttpUtil.setParameter(url, "noSuchEntryRedirect", viewEntryURL);
	}

	protected String buildLink(String link, String text) {
		StringBundler sb = new StringBundler(5);

		sb.append("<a href=\"");
		sb.append(link);
		sb.append("\">");
		sb.append(text);
		sb.append("</a>");

		return sb.toString();
	}

	protected SocialActivityFeedEntry doInterpret(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		if (!hasPermissions(
				themeDisplay.getPermissionChecker(), activity, ActionKeys.VIEW,
				serviceContext)) {

			return null;
		}

		String link = getLink(activity, serviceContext);

		String title = getTitle(activity, serviceContext);

		if (Validator.isNull(title)) {
			return null;
		}

		String body = getBody(activity, serviceContext);

		return new SocialActivityFeedEntry(link, title, body);
	}

	protected SocialActivityFeedEntry doInterpret(
			SocialActivitySet activitySet, ServiceContext serviceContext)
		throws Exception {

		List<SocialActivity> activities =
			SocialActivityLocalServiceUtil.getActivitySetActivities(
				activitySet.getActivitySetId(), 0, 1);

		if (!activities.isEmpty()) {
			SocialActivity activity = activities.get(0);

			return doInterpret(activity, serviceContext);
		}

		return null;
	}

	protected long getActivitySetId(long activityId) {
		return 0;
	}

	protected String getBody(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		return StringPool.BLANK;
	}

	protected String getEntryTitle(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		return activity.getExtraDataValue("title", serviceContext.getLocale());
	}

	protected String getGroupName(long groupId, ServiceContext serviceContext) {
		try {
			if (groupId <= 0) {
				return StringPool.BLANK;
			}

			Group group = GroupLocalServiceUtil.getGroup(groupId);

			String groupName = group.getDescriptiveName();

			if (group.getGroupId() == serviceContext.getScopeGroupId()) {
				return HtmlUtil.escape(groupName);
			}

			String groupDisplayURL = StringPool.BLANK;

			if (group.hasPublicLayouts()) {
				groupDisplayURL = group.getDisplayURL(
					serviceContext.getThemeDisplay(), false);
			}
			else if (group.hasPrivateLayouts()) {
				groupDisplayURL = group.getDisplayURL(
					serviceContext.getThemeDisplay(), true);
			}
			else {
				return HtmlUtil.escape(groupName);
			}

			groupName = StringBundler.concat(
				"<a class=\"group\" href=\"", groupDisplayURL, "\">",
				HtmlUtil.escape(groupName), "</a>");

			return groupName;
		}
		catch (Exception exception) {
			return StringPool.BLANK;
		}
	}

	protected String getJSONValue(String json, String key) {
		return getJSONValue(json, key, StringPool.BLANK);
	}

	protected String getJSONValue(
		String json, String key, String defaultValue) {

		if (Validator.isNull(json)) {
			return defaultValue;
		}

		try {
			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(
				json);

			String value = extraDataJSONObject.getString(key);

			if (Validator.isNotNull(value)) {
				return value;
			}
		}
		catch (JSONException jsonException) {
			_log.error(
				"Unable to create a JSON object from " + json, jsonException);
		}

		return defaultValue;
	}

	protected String getLink(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		String className = activity.getClassName();
		long classPK = activity.getClassPK();

		String viewEntryInTrashURL = getViewEntryInTrashURL(
			className, classPK, serviceContext);

		if (viewEntryInTrashURL != null) {
			return viewEntryInTrashURL;
		}

		String path = null;

		try {
			path = getPath(activity, serviceContext);

			if (Validator.isNull(path)) {
				return null;
			}
		}
		catch (NullPointerException npe) {
			return null;
		}

		path = addNoSuchEntryRedirect(path, className, classPK, serviceContext);

		if (!path.startsWith(StringPool.SLASH)) {
			return path;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(serviceContext.getPortalURL());

		if (!path.startsWith(PortalUtil.getPathContext())) {
			sb.append(PortalUtil.getPathContext());
		}

		if (!path.startsWith(serviceContext.getPathMain())) {
			sb.append(serviceContext.getPathMain());
		}

		sb.append(path);

		return sb.toString();
	}

	protected String getPath(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		return StringPool.BLANK;
	}

	protected abstract ResourceBundleLoader getResourceBundleLoader();

	protected String getTitle(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		String groupName = StringPool.BLANK;

		if (activity.getGroupId() != serviceContext.getScopeGroupId()) {
			groupName = getGroupName(activity.getGroupId(), serviceContext);
		}

		String titlePattern = getTitlePattern(groupName, activity);

		if (Validator.isNull(titlePattern)) {
			return null;
		}

		String link = getLink(activity, serviceContext);

		String entryTitle = getEntryTitle(activity, serviceContext);

		Object[] titleArguments = getTitleArguments(
			groupName, activity, link, entryTitle, serviceContext);

		ResourceBundleLoader resourceBundleLoader = getResourceBundleLoader();

		if (resourceBundleLoader == null) {
			return serviceContext.translate(titlePattern, titleArguments);
		}

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			serviceContext.getLocale());

		return LanguageUtil.format(
			resourceBundle, titlePattern, titleArguments);
	}

	protected Object[] getTitleArguments(
			String groupName, SocialActivity activity, String link,
			String title, ServiceContext serviceContext)
		throws Exception {

		String userName = getUserName(activity.getUserId(), serviceContext);

		return new Object[] {groupName, userName, wrapLink(link, title)};
	}

	protected String getTitlePattern(String groupName, SocialActivity activity)
		throws Exception {

		return StringPool.BLANK;
	}

	protected String getUserName(long userId, ServiceContext serviceContext) {
		try {
			if (userId <= 0) {
				return StringPool.BLANK;
			}

			User user = UserLocalServiceUtil.getUserById(userId);

			if (user.getUserId() == serviceContext.getUserId()) {
				return HtmlUtil.escape(user.getFirstName());
			}

			String userName = user.getFullName();

			Group group = user.getGroup();

			if (group.getGroupId() == serviceContext.getScopeGroupId()) {
				return HtmlUtil.escape(userName);
			}

			String userDisplayURL = user.getDisplayURL(
				serviceContext.getThemeDisplay());

			userName = StringBundler.concat(
				"<a class=\"user\" href=\"", userDisplayURL, "\">",
				HtmlUtil.escape(userName), "</a>");

			return userName;
		}
		catch (Exception exception) {
			return StringPool.BLANK;
		}
	}

	protected String getViewEntryInTrashURL(
			String className, long classPK, ServiceContext serviceContext)
		throws Exception {

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		if ((trashHandler != null) && trashHandler.isInTrash(classPK)) {
			PortletURL portletURL = TrashUtil.getViewContentURL(
				serviceContext.getRequest(), className, classPK);

			if (portletURL == null) {
				return null;
			}

			return portletURL.toString();
		}

		return null;
	}

	protected String getViewEntryURL(
			String className, long classPK, ServiceContext serviceContext)
		throws Exception {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if (assetRendererFactory == null) {
			return null;
		}

		LiferayPortletResponse liferayPortletResponse =
			serviceContext.getLiferayPortletResponse();

		if (liferayPortletResponse == null) {
			return null;
		}

		if (classPK == 0) {
			PortletURL portletURL = assetRendererFactory.getURLView(
				liferayPortletResponse, WindowState.MAXIMIZED);

			return portletURL.toString();
		}

		AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(
			classPK);

		if (assetRenderer == null) {
			return null;
		}

		return assetRenderer.getURLView(
			liferayPortletResponse, WindowState.MAXIMIZED);
	}

	protected boolean hasPermissions(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ServiceContext serviceContext)
		throws Exception {

		return false;
	}

	protected String wrapLink(String link, String title) {
		title = HtmlUtil.escape(title);

		if (link == null) {
			return title;
		}

		return buildLink(link, title);
	}

	protected String wrapLink(
		String link, String key, ServiceContext serviceContext) {

		ResourceBundleLoader resourceBundleLoader = getResourceBundleLoader();

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			serviceContext.getLocale());

		String title = LanguageUtil.get(resourceBundle, key);

		return wrapLink(link, title);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSocialActivityInterpreter.class);

}