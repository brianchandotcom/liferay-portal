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

package com.liferay.social.privatemessaging.web.internal.portlet;

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.social.privatemessaging.configuration.PrivateMessagingConfiguration;
import com.liferay.social.privatemessaging.constants.PrivateMessagingPortletKeys;
import com.liferay.social.privatemessaging.service.UserThreadLocalService;
import com.liferay.social.privatemessaging.web.internal.util.PrivateMessagingUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.MimeResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Scott Lee
 * @author Eudaldo Alonso
 * @author Peter Fellwock
 */
@Component(
	configurationPid = {
		"com.liferay.document.library.configuration.DLConfiguration",
		"com.liferay.social.privatemessaging.configuration.PrivateMessagingConfiguration"
	},
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=private-messaging-portlet",
		"com.liferay.portlet.display-category=category.collaboration",
		"com.liferay.portlet.footer-portlet-javascript=/js/main.js",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/private_messaging.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.remoteable=true",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Private Messaging",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.info.keywords=Private Messaging",
		"javax.portlet.info.short-title=Private Messaging",
		"javax.portlet.info.title=Private Messaging",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + PrivateMessagingPortletKeys.PRIVATE_MESSAGING,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user"
	},
	service = Portlet.class
)
public class PrivateMessagingPortlet extends MVCPortlet {

	public void deleteMessages(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] mbThreadIds = ParamUtil.getLongValues(
			actionRequest, "mbThreadIds");

		for (long mbThreadId : mbThreadIds) {
			_userThreadLocalService.deleteUserThread(
				themeDisplay.getUserId(), mbThreadId);
		}
	}

	public void getMessageAttachment(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long messageId = ParamUtil.getLong(resourceRequest, "messageId");
		String fileName = ParamUtil.getString(resourceRequest, "attachment");

		MBMessage message = _mBMessageLocalService.getMessage(messageId);

		if (!PrivateMessagingUtil.isUserPartOfThread(
				themeDisplay.getUserId(), message.getThreadId())) {

			throw new PrincipalException();
		}

		FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
			message.getGroupId(), message.getAttachmentsFolderId(), fileName);

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse, fileName,
			fileEntry.getContentStream(), (int)fileEntry.getSize(),
			fileEntry.getMimeType());
	}

	public void markMessagesAsRead(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] mbThreadIds = ParamUtil.getLongValues(
			actionRequest, "mbThreadIds");

		for (long mbThreadId : mbThreadIds) {
			_userThreadLocalService.markUserThreadAsRead(
				themeDisplay.getUserId(), mbThreadId);
		}
	}

	public void markMessagesAsUnread(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] mbThreadIds = ParamUtil.getLongValues(
			actionRequest, "mbThreadIds");

		for (long mbThreadId : mbThreadIds) {
			_userThreadLocalService.markUserThreadAsUnread(
				themeDisplay.getUserId(), mbThreadId);
		}
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return;
		}

		try {
			String actionName = ParamUtil.getString(
				actionRequest, ActionRequest.ACTION_NAME);

			if (actionName.equals("sendMessage")) {
				sendMessage(actionRequest, actionResponse);
			}
			else {
				super.processAction(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void sendMessage(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long mbThreadId = ParamUtil.getLong(uploadPortletRequest, "mbThreadId");
		String to = ParamUtil.getString(uploadPortletRequest, "to");
		String subject = ParamUtil.getString(uploadPortletRequest, "subject");
		String body = ParamUtil.getString(uploadPortletRequest, "body");
		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<>();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			for (int i = 1; i <= 3; i++) {
				String fileName = uploadPortletRequest.getFileName(
					"msgFile" + i);
				InputStream inputStream = uploadPortletRequest.getFileAsStream(
					"msgFile" + i);

				if (inputStream == null) {
					continue;
				}

				validateAttachment(fileName, inputStream);

				try {
					ObjectValuePair<String, InputStream> inputStreamOVP =
						new ObjectValuePair<>(fileName, inputStream);

					inputStreamOVPs.add(inputStreamOVP);
				}
				catch (Exception e) {
					_log.error(
						translate(actionRequest, "unable to attach file ") +
							fileName,
						e);
				}
			}

			_userThreadLocalService.addPrivateMessage(
				themeDisplay.getUserId(), mbThreadId, to, subject, body,
				inputStreamOVPs, themeDisplay);

			jsonObject.put("success", Boolean.TRUE);
		}
		catch (Exception e) {
			jsonObject.put("message", getMessage(actionRequest, e));

			jsonObject.put("success", Boolean.FALSE);
		}
		finally {
			for (ObjectValuePair<String, InputStream> inputStreamOVP :
					inputStreamOVPs) {

				try (InputStream inputStream = inputStreamOVP.getValue()) {
				}
				catch (IOException ioe) {
					if (_log.isWarnEnabled()) {
						_log.warn(ioe, ioe);
					}
				}
			}
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			String resourceID = GetterUtil.getString(
				resourceRequest.getResourceID());

			if (resourceID.equals("getMessageAttachment")) {
				getMessageAttachment(resourceRequest, resourceResponse);
			}
			else if (resourceID.equals("getUsers")) {
				getUsers(resourceRequest, resourceResponse);
			}
			else {
				super.serveResource(resourceRequest, resourceResponse);
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_dlConfiguration = ConfigurableUtil.createConfigurable(
			DLConfiguration.class, properties);

		_privateMessagingConfiguration = ConfigurableUtil.createConfigurable(
			PrivateMessagingConfiguration.class, properties);
	}

	protected String getMessage(PortletRequest portletRequest, Exception key)
		throws Exception {

		String message = null;

		if (key instanceof FileExtensionException) {
			message = translate(
				portletRequest,
				"document-names-must-end-with-one-of-the-following-extensions");

			String fileExtensions = StringUtil.merge(
				_dlConfiguration.fileExtensions(), StringPool.COMMA_AND_SPACE);

			message += CharPool.SPACE + fileExtensions;
		}
		else if (key instanceof FileNameException) {
			message = translate(
				portletRequest, "please-enter-a-file-with-a-valid-file-name");
		}
		else if (key instanceof FileSizeException) {
			message = translate(
				portletRequest,
				"please-enter-a-file-with-a-valid-file-size-no-larger-than-x",
				_dlValidator.getMaxAllowableSize() / 1024);
		}
		else if (key instanceof UserScreenNameException) {
			message = translate(
				portletRequest, "the-following-users-were-not-found");

			message += CharPool.SPACE + key.getMessage();
		}
		else {
			message = translate(
				portletRequest, "your-request-failed-to-complete");
		}

		return message;
	}

	protected void getUsers(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String keywords = ParamUtil.getString(resourceRequest, "keywords");

		JSONObject resultsJSONObject = JSONFactoryUtil.createJSONObject();

		JSONObject jsonObject = PrivateMessagingUtil.getJSONRecipients(
			themeDisplay.getUserId(),
			_privateMessagingConfiguration.autocompleteRecipientType(),
			keywords, 0,
			_privateMessagingConfiguration .autocompleteRecipientMax());

		resultsJSONObject.put("results", jsonObject);

		writeJSON(resourceRequest, resourceResponse, resultsJSONObject);
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.social.privatemessaging.web)(release.schema.version=1.0.1))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	protected void validateAttachment(String fileName, InputStream inputStream)
		throws Exception {

		_dlValidator.validateFileSize(fileName, inputStream);

		_dlValidator.validateFileName(fileName);

		_dlValidator.validateFileExtension(fileName);
	}

	protected void validateTo(String to, ThemeDisplay themeDisplay)
		throws Exception {

		if (Validator.isNull(to)) {
			return;
		}

		String[] recipients = StringUtil.split(to);

		List<String> failedRecipients = new ArrayList<>();

		for (String recipient : recipients) {
			recipient = recipient.trim();

			int x = recipient.indexOf(CharPool.LESS_THAN);
			int y = recipient.indexOf(CharPool.GREATER_THAN);

			try {
				if ((x != -1) && (y != -1)) {
					recipient = recipient.substring(x + 1, y);
				}

				_userLocalService.getUserByScreenName(
					themeDisplay.getCompanyId(), recipient);
			}
			catch (NoSuchUserException nsue) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nsue, nsue);
				}

				failedRecipients.add(recipient);
			}
		}

		if (!failedRecipients.isEmpty()) {
			StringBundler sb = new StringBundler(3);

			sb.append(StringPool.APOSTROPHE);
			sb.append(StringUtil.merge(failedRecipients, "', '"));
			sb.append(StringPool.APOSTROPHE);

			throw new UserScreenNameException(sb.toString());
		}
	}

	@Override
	protected void writeJSON(
			PortletRequest portletRequest, MimeResponse mimeResponse,
			Object jsonObj)
		throws IOException {

		mimeResponse.setContentType(ContentTypes.TEXT_HTML);

		PortletResponseUtil.write(mimeResponse, jsonObj.toString());

		mimeResponse.flushBuffer();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PrivateMessagingPortlet.class);

	private volatile DLConfiguration _dlConfiguration;

	@Reference
	private DLValidator _dlValidator;

	@Reference
	private MBMessageLocalService _mBMessageLocalService;

	@Reference
	private Portal _portal;

	private PrivateMessagingConfiguration _privateMessagingConfiguration;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

	@Reference
	private UserThreadLocalService _userThreadLocalService;

}