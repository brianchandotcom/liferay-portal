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

package com.liferay.message.boards.web.internal.portlet.action;

import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.captcha.configuration.CaptchaConfiguration;
import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.message.boards.kernel.exception.LockedThreadException;
import com.liferay.message.boards.kernel.exception.MessageBodyException;
import com.liferay.message.boards.kernel.exception.MessageSubjectException;
import com.liferay.message.boards.kernel.exception.NoSuchMessageException;
import com.liferay.message.boards.kernel.exception.RequiredMessageException;
import com.liferay.message.boards.kernel.model.MBCategory;
import com.liferay.message.boards.kernel.model.MBCategoryConstants;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.service.MBCategoryService;
import com.liferay.message.boards.kernel.service.MBMessageLocalService;
import com.liferay.message.boards.kernel.service.MBMessageService;
import com.liferay.message.boards.kernel.service.MBThreadLocalService;
import com.liferay.message.boards.kernel.service.MBThreadService;
import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.message.boards.web.internal.upload.format.MBMessageFormatUploadHandler;
import com.liferay.message.boards.web.internal.upload.format.MBMessageFormatUploadHandlerProvider;
import com.liferay.message.boards.web.internal.util.MBAttachmentFileEntryReference;
import com.liferay.message.boards.web.internal.util.MBAttachmentFileEntryUtil;
import com.liferay.portal.kernel.captcha.CaptchaConfigurationException;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.upload.LiferayFileItemException;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadRequestSizeException;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.messageboards.MBGroupServiceSettings;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Sanz
 * @author Shuyang Zhou
 */
@Component(
	property = {
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS,
		"javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN,
		"mvc.command.name=/message_boards/edit_message"
	},
	service = MVCActionCommand.class
)
public class EditMessageMVCActionCommand extends BaseMVCActionCommand {

	protected void addAnswer(ActionRequest actionRequest) throws Exception {
		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		_mbMessageService.updateAnswer(messageId, true, false);
	}

	protected void deleteAnswer(ActionRequest actionRequest) throws Exception {
		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		_mbMessageService.updateAnswer(messageId, false, false);
	}

	protected void deleteMessage(ActionRequest actionRequest) throws Exception {
		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		_mbMessageService.deleteMessage(messageId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		MBMessage message = null;

		try {
			UploadException uploadException =
				(UploadException)actionRequest.getAttribute(
					WebKeys.UPLOAD_EXCEPTION);

			if (uploadException != null) {
				Throwable cause = uploadException.getCause();

				if (uploadException.isExceededFileSizeLimit()) {
					throw new FileSizeException(cause);
				}

				if (uploadException.isExceededLiferayFileItemSizeLimit()) {
					throw new LiferayFileItemException(cause);
				}

				if (uploadException.isExceededUploadRequestSizeLimit()) {
					throw new UploadRequestSizeException(cause);
				}

				throw new PortalException(cause);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				message = TransactionInvokerUtil.invoke(
					_transactionConfig,
					() -> updateMessage(actionRequest, actionResponse));
			}
			else if (cmd.equals(Constants.ADD_ANSWER)) {
				addAnswer(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteMessage(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE_ANSWER)) {
				deleteAnswer(actionRequest);
			}
			else if (cmd.equals(Constants.LOCK)) {
				lockThreads(actionRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribeMessage(actionRequest);
			}
			else if (cmd.equals(Constants.UNLOCK)) {
				unlockThreads(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribeMessage(actionRequest);
			}

			if (Validator.isNotNull(cmd)) {
				WindowState windowState = actionRequest.getWindowState();

				if (!windowState.equals(LiferayWindowState.POP_UP)) {
					String redirect = getRedirect(
						actionRequest, actionResponse, message);

					sendRedirect(actionRequest, actionResponse, redirect);
				}
				else {
					String redirect = _portal.escapeRedirect(
						ParamUtil.getString(actionRequest, "redirect"));

					if (Validator.isNotNull(redirect)) {
						actionResponse.sendRedirect(redirect);
					}
				}
			}
		}
		catch (NoSuchMessageException | PrincipalException |
			   RequiredMessageException e) {

			SessionErrors.add(actionRequest, e.getClass());

			actionResponse.setRenderParameter(
				"mvcPath", "/message_boards/error.jsp");
		}
		catch (AntivirusScannerException | CaptchaConfigurationException |
			   CaptchaTextException | DuplicateFileEntryException |
			   FileExtensionException | FileNameException | FileSizeException |
			   LiferayFileItemException | LockedThreadException |
			   MessageBodyException | MessageSubjectException |
			   SanitizerException | UploadRequestSizeException e) {

			if (e instanceof AntivirusScannerException) {
				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else {
				SessionErrors.add(actionRequest, e.getClass());
			}
		}
		catch (AssetCategoryException | AssetTagException e) {
			SessionErrors.add(actionRequest, e.getClass(), e);
		}
		catch (Exception e) {
			Throwable cause = e.getCause();

			if (cause instanceof SanitizerException) {
				SessionErrors.add(actionRequest, SanitizerException.class);
			}
			else {
				throw e;
			}
		}
		catch (Throwable t) {
			_log.error("Unable to process action", t);

			actionResponse.setRenderParameter(
				"mvcPath", "/message_boards/error.jsp");

			hideDefaultSuccessMessage(actionRequest);
		}
	}

	protected CaptchaConfiguration getCaptchaConfiguration()
		throws CaptchaConfigurationException {

		try {
			return _configurationProvider.getSystemConfiguration(
				CaptchaConfiguration.class);
		}
		catch (Exception e) {
			throw new CaptchaConfigurationException(e);
		}
	}

	protected String getRedirect(
		ActionRequest actionRequest, ActionResponse actionResponse,
		MBMessage message) {

		if (message == null) {
			String redirect = ParamUtil.getString(actionRequest, "redirect");

			return redirect;
		}

		int workflowAction = ParamUtil.getInteger(
			actionRequest, "workflowAction", WorkflowConstants.ACTION_PUBLISH);

		if (workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT) {
			return getSaveAndContinueRedirect(
				actionRequest, actionResponse, message);
		}
		else if (message == null) {
			return ParamUtil.getString(actionRequest, "redirect");
		}

		ActionResponseImpl actionResponseImpl =
			(ActionResponseImpl)actionResponse;

		PortletURL portletURL = actionResponseImpl.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/message_boards/view_message");
		portletURL.setParameter(
			"messageId", String.valueOf(message.getMessageId()));

		return portletURL.toString();
	}

	protected String getSaveAndContinueRedirect(
		ActionRequest actionRequest, ActionResponse actionResponse,
		MBMessage message) {

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		boolean preview = ParamUtil.getBoolean(actionRequest, "preview");

		PortletURL portletURL =
			((ActionResponseImpl)actionResponse).createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/message_boards/edit_message");
		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter(
			"messageId", String.valueOf(message.getMessageId()));
		portletURL.setParameter("preview", String.valueOf(preview));

		return portletURL.toString();
	}

	protected void lockThreads(ActionRequest actionRequest) throws Exception {
		long threadId = ParamUtil.getLong(actionRequest, "threadId");

		if (threadId > 0) {
			_mbThreadService.lockThread(threadId);
		}
		else {
			long[] threadIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "threadIds"), 0L);

			for (int i = 0; i < threadIds.length; i++) {
				_mbThreadService.lockThread(threadIds[i]);
			}
		}
	}

	protected void subscribeMessage(ActionRequest actionRequest)
		throws Exception {

		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		_mbMessageService.subscribeMessage(messageId);
	}

	protected void unlockThreads(ActionRequest actionRequest) throws Exception {
		long threadId = ParamUtil.getLong(actionRequest, "threadId");

		if (threadId > 0) {
			_mbThreadService.unlockThread(threadId);
		}
		else {
			long[] threadIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "threadIds"), 0L);

			for (int i = 0; i < threadIds.length; i++) {
				_mbThreadService.unlockThread(threadIds[i]);
			}
		}
	}

	protected void unsubscribeMessage(ActionRequest actionRequest)
		throws Exception {

		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		_mbMessageService.unsubscribeMessage(messageId);
	}

	protected MBMessage updateMessage(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		long groupId = themeDisplay.getScopeGroupId();
		long categoryId = ParamUtil.getLong(actionRequest, "mbCategoryId");
		long threadId = ParamUtil.getLong(actionRequest, "threadId");
		long parentMessageId = ParamUtil.getLong(
			actionRequest, "parentMessageId");
		String subject = ParamUtil.getString(actionRequest, "subject");
		String body = ParamUtil.getString(actionRequest, "body");

		MBGroupServiceSettings mbGroupServiceSettings =
			MBGroupServiceSettings.getInstance(groupId);

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<>(5);

		try {
			UploadPortletRequest uploadPortletRequest =
				_portal.getUploadPortletRequest(actionRequest);

			for (int i = 1; i <= 5; i++) {
				String fileName = uploadPortletRequest.getFileName(
					"msgFile" + i);
				InputStream inputStream = uploadPortletRequest.getFileAsStream(
					"msgFile" + i);

				if ((inputStream == null) || Validator.isNull(fileName)) {
					continue;
				}

				ObjectValuePair<String, InputStream> inputStreamOVP =
					new ObjectValuePair<>(fileName, inputStream);

				inputStreamOVPs.add(inputStreamOVP);
			}

			boolean question = ParamUtil.getBoolean(actionRequest, "question");

			if (categoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
				MBCategory category = _mbCategoryService.getCategory(
					categoryId);

				String displayStyle = category.getDisplayStyle();

				if (displayStyle.equals("question")) {
					question = true;
				}
			}

			boolean anonymous = ParamUtil.getBoolean(
				actionRequest, "anonymous");
			double priority = ParamUtil.getDouble(actionRequest, "priority");
			boolean allowPingbacks = ParamUtil.getBoolean(
				actionRequest, "allowPingbacks");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				MBMessage.class.getName(), actionRequest);

			boolean preview = ParamUtil.getBoolean(actionRequest, "preview");

			serviceContext.setAttribute("preview", preview);

			MBMessage message = null;

			CaptchaConfiguration captchaConfiguration =
				getCaptchaConfiguration();

			if (messageId <= 0) {
				if (captchaConfiguration.
						messageBoardsEditMessageCaptchaEnabled()) {

					CaptchaUtil.check(actionRequest);
				}

				if (threadId <= 0) {

					// Post new thread

					message = _mbMessageService.addMessage(
						groupId, categoryId, subject, body,
						mbGroupServiceSettings.getMessageFormat(),
						inputStreamOVPs, anonymous, priority, allowPingbacks,
						serviceContext);

					if (question) {
						_mbThreadLocalService.updateQuestion(
							message.getThreadId(), true);
					}
				}
				else {

					// Post reply

					message = _mbMessageService.addMessage(
						parentMessageId, subject, body,
						mbGroupServiceSettings.getMessageFormat(),
						inputStreamOVPs, anonymous, priority, allowPingbacks,
						serviceContext);
				}

				MBMessageFormatUploadHandler formatHandler =
					_formatHandlerProvider.provide(message.getFormat());

				if (formatHandler != null) {
					body = _addBodyAttachmentTempFiles(
						themeDisplay, body, message, new ArrayList<String>(),
						formatHandler);

					_mbMessageLocalService.updateMessage(
						themeDisplay.getUserId(), message.getMessageId(),
						message.getSubject(), body, null, null,
						message.getPriority(), message.getAllowPingbacks(),
						serviceContext);
				}
			}
			else {
				List<String> existingFiles = new ArrayList<>();

				for (int i = 1; i <= 5; i++) {
					String path = ParamUtil.getString(
						actionRequest, "existingPath" + i);

					if (Validator.isNotNull(path)) {
						existingFiles.add(path);
					}
				}

				message = _mbMessageService.getMessage(messageId);

				MBMessageFormatUploadHandler formatHandler =
					_formatHandlerProvider.provide(message.getFormat());

				if (formatHandler != null) {
					body = _addBodyAttachmentTempFiles(
						themeDisplay, body, message, existingFiles,
						formatHandler);
				}

				// Update message

				message = _mbMessageService.updateMessage(
					messageId, subject, body, inputStreamOVPs, existingFiles,
					priority, allowPingbacks, serviceContext);

				if (message.isRoot()) {
					_mbThreadLocalService.updateQuestion(
						message.getThreadId(), question);
				}
			}

			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			boolean subscribe = ParamUtil.getBoolean(
				actionRequest, "subscribe");

			if (!preview && subscribe &&
				MBMessagePermission.contains(
					permissionChecker, message, ActionKeys.SUBSCRIBE)) {

				_mbMessageService.subscribeMessage(message.getMessageId());
			}

			return message;
		}
		finally {
			for (ObjectValuePair<String, InputStream> inputStreamOVP :
					inputStreamOVPs) {

				InputStream inputStream = inputStreamOVP.getValue();

				StreamUtil.cleanUp(inputStream);
			}
		}
	}

	private String _addBodyAttachmentTempFiles(
			ThemeDisplay themeDisplay, String body, MBMessage message,
			List<String> existingFiles,
			MBMessageFormatUploadHandler formatHandler)
		throws PortalException {

		List<FileEntry> tempMBAttachmentFileEntries =
			MBAttachmentFileEntryUtil.getTempMBAttachmentFileEntries(body);

		if (!tempMBAttachmentFileEntries.isEmpty()) {
			Folder folder = message.addAttachmentsFolder();

			List<MBAttachmentFileEntryReference>
				mbAttachmentFileEntryReferences =
					MBAttachmentFileEntryUtil.addMBAttachmentFileEntries(
						message.getGroupId(), themeDisplay.getUserId(),
						message.getMessageId(), folder.getFolderId(),
						tempMBAttachmentFileEntries);

			for (MBAttachmentFileEntryReference mbAttachmentFileEntryReference :
					mbAttachmentFileEntryReferences) {

				FileEntry mbAttachmentFileEntry =
					mbAttachmentFileEntryReference.getMBAttachmentFileEntry();

				existingFiles.add(
					String.valueOf(mbAttachmentFileEntry.getFileEntryId()));
			}

			body = formatHandler.replaceImageReferences(
				body, mbAttachmentFileEntryReferences);

			for (FileEntry tempMBAttachment : tempMBAttachmentFileEntries) {
				PortletFileRepositoryUtil.deletePortletFileEntry(
					tempMBAttachment.getFileEntryId());
			}
		}

		return body;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditMessageMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private MBMessageFormatUploadHandlerProvider _formatHandlerProvider;

	@Reference
	private MBCategoryService _mbCategoryService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBMessageService _mbMessageService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private MBThreadService _mbThreadService;

	@Reference
	private Portal _portal;

}