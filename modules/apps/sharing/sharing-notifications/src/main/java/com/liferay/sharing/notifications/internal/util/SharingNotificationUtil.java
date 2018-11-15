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

package com.liferay.sharing.notifications.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.interpreter.SharingEntryInterpreterProvider;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = SharingNotificationUtil.class)
public class SharingNotificationUtil {

	public String getNotificationEmailBody(
			SharingEntry sharingEntry, PortletRequest portletRequest)
		throws Exception {

		Class<?> clazz = getClass();

		String templateId =
			"/com/liferay/sharing/notifications/dependencies" +
				"/sharing_entry_added_email_body.ftl";

		URLTemplateResource templateResource = new URLTemplateResource(
			templateId, clazz.getResource(templateId));

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, templateResource, false);

		User toUser = _userLocalService.fetchUser(sharingEntry.getToUserId());

		Locale locale = _getLocale(toUser);

		template.put("actionTitle", _getEmailActionTitle(sharingEntry, locale));

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		User fromUser = _userLocalService.fetchUser(
			sharingEntry.getFromUserId());

		template.put(
			"content",
			_getContent(
				sharingEntry, portletRequest, fromUser, resourceBundle));

		template.put("fromUserName", _getUserName(fromUser, resourceBundle));

		if (portletRequest != null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (fromUser != null) {
				template.put(
					"fromUserPortraitURL",
					fromUser.getPortraitURL(themeDisplay));
			}

			template.put(
				"sharingEntryURL",
				getNotificationURL(sharingEntry, portletRequest));
		}

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		return unsyncStringWriter.toString();
	}

	public String getNotificationMessage(
		SharingEntry sharingEntry, Locale locale) {

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return ResourceBundleUtil.getString(
			resourceBundle, "x-has-shared-x-with-you-for-x",
			_getUserName(sharingEntry, resourceBundle),
			_getSharingEntryObjectTitle(sharingEntry, resourceBundle),
			_getActionName(sharingEntry, resourceBundle));
	}

	public String getNotificationURL(
			SharingEntry sharingEntry, PortletRequest portletRequest)
		throws PortalException {

		if (portletRequest != null) {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				portletRequest, SharingEntry.class.getName(),
				PortletProvider.Action.PREVIEW);

			portletURL.setParameter(
				"sharingEntryId",
				String.valueOf(sharingEntry.getSharingEntryId()));

			return portletURL.toString();
		}

		return null;
	}

	private String _getActionName(
		SharingEntry sharingEntry, ResourceBundle resourceBundle) {

		if (_sharingEntryLocalService.hasSharingPermission(
				sharingEntry, SharingEntryAction.UPDATE)) {

			return ResourceBundleUtil.getString(resourceBundle, "updating");
		}
		else if (_sharingEntryLocalService.hasSharingPermission(
					sharingEntry, SharingEntryAction.ADD_DISCUSSION)) {

			return ResourceBundleUtil.getString(resourceBundle, "commenting");
		}
		else if (_sharingEntryLocalService.hasSharingPermission(
					sharingEntry, SharingEntryAction.VIEW)) {

			return ResourceBundleUtil.getString(resourceBundle, "viewing");
		}

		return ResourceBundleUtil.getString(resourceBundle, "nothing");
	}

	private String _getContent(
			SharingEntry sharingEntry, PortletRequest portletRequest,
			User fromUser, ResourceBundle resourceBundle)
		throws PortalException {

		if (portletRequest != null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return ResourceBundleUtil.getString(
				resourceBundle, "x-has-shared-x-with-you-for-x",
				_getUserLink(fromUser, resourceBundle, themeDisplay),
				_getSharingEntryObjectLink(
					sharingEntry, portletRequest, resourceBundle),
				_getActionName(sharingEntry, resourceBundle));
		}

		return ResourceBundleUtil.getString(
			resourceBundle, "x-has-shared-x-with-you-for-x",
			_getUserName(fromUser, resourceBundle),
			_getSharingEntryObjectTitle(sharingEntry, resourceBundle),
			_getActionName(sharingEntry, resourceBundle));
	}

	private String _getEmailActionTitle(
			SharingEntry sharingEntry, Locale locale)
		throws PortalException {

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		SharingEntryInterpreter sharingEntryInterpreter =
			_getSharingEntryInterpreter(sharingEntry);

		if (sharingEntryInterpreter != null) {
			return ResourceBundleUtil.getString(
				resourceBundle, "view-x",
				sharingEntryInterpreter.getAssetTypeTitle(
					sharingEntry, locale));
		}

		return ResourceBundleUtil.getString(resourceBundle, "view");
	}

	private Locale _getLocale(User toUser) {
		if (toUser != null) {
			return toUser.getLocale();
		}

		return Locale.getDefault();
	}

	private SharingEntryInterpreter _getSharingEntryInterpreter(
		SharingEntry sharingEntry) {

		return _sharingEntryInterpreterProvider.getSharingEntryInterpreter(
			sharingEntry);
	}

	private String _getSharingEntryObjectLink(
			SharingEntry sharingEntry, PortletRequest portletRequest,
			ResourceBundle resourceBundle)
		throws PortalException {

		return StringBundler.concat(
			"<a href=\"", getNotificationURL(sharingEntry, portletRequest),
			"\">",
			HtmlUtil.escape(
				_getSharingEntryObjectTitle(sharingEntry, resourceBundle)),
			"</a>");
	}

	private String _getSharingEntryObjectTitle(
		SharingEntry sharingEntry, ResourceBundle resourceBundle) {

		SharingEntryInterpreter sharingEntryInterpreter =
			_getSharingEntryInterpreter(sharingEntry);

		if (sharingEntryInterpreter != null) {
			return sharingEntryInterpreter.getTitle(sharingEntry);
		}

		return ResourceBundleUtil.getString(resourceBundle, "something");
	}

	private String _getUserLink(
			User user, ResourceBundle resourceBundle, ThemeDisplay themeDisplay)
		throws PortalException {

		if (user == null) {
			return _getUserName(user, resourceBundle);
		}

		return StringBundler.concat(
			"<a href=\"", user.getDisplayURL(themeDisplay), "\">",
			HtmlUtil.escape(_getUserName(user, resourceBundle)), "</a>");
	}

	private String _getUserName(
		SharingEntry sharingEntry, ResourceBundle resourceBundle) {

		return _getUserName(
			_userLocalService.fetchUserById(sharingEntry.getFromUserId()),
			resourceBundle);
	}

	private String _getUserName(User user, ResourceBundle resourceBundle) {
		if (user != null) {
			return HtmlUtil.escape(user.getFullName());
		}

		return ResourceBundleUtil.getString(resourceBundle, "someone");
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.sharing.notifications)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference
	private SharingEntryInterpreterProvider _sharingEntryInterpreterProvider;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}