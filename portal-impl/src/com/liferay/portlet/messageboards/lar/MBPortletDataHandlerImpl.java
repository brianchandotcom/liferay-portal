/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadFlagLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.persistence.MBBanUtil;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessageUtil;
import com.liferay.portlet.messageboards.service.persistence.MBThreadFlagUtil;
import com.liferay.portlet.messageboards.service.persistence.MBThreadUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class MBPortletDataHandlerImpl extends BasePortletDataHandler {

	@Override
	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_categoriesAndMessages, _threadFlags, _userBans
		};
	}

	@Override
	public PortletDataHandlerControl[] getExportMetadataControls() {
		return new PortletDataHandlerControl[] {
			new PortletDataHandlerBoolean(
				_NAMESPACE, "message-board-messages", true, _metadataControls)
		};
	}

	@Override
	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_categoriesAndMessages, _threadFlags, _userBans
		};
	}

	@Override
	public PortletDataHandlerControl[] getImportMetadataControls() {
		return new PortletDataHandlerControl[] {
			new PortletDataHandlerBoolean(
				_NAMESPACE, "message-board-messages", true, _metadataControls)
		};
	}

	@Override
	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	@Override
	public boolean isPublishToLiveByDefault() {
		return PropsValues.MESSAGE_BOARDS_PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (!portletDataContext.addPrimaryKey(
				MBPortletDataHandlerImpl.class, "deleteData")) {

			MBCategoryLocalServiceUtil.deleteCategories(
				portletDataContext.getScopeGroupId());

			MBThreadLocalServiceUtil.deleteThreads(
				portletDataContext.getScopeGroupId(),
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
		}

		return null;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.messageboards",
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("message-boards-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Element categoriesElement = rootElement.addElement("categories");
		Element messagesElement = rootElement.addElement("messages");
		Element threadFlagsElement = rootElement.addElement("thread-flags");
		Element userBansElement = rootElement.addElement("user-bans");

		List<MBCategory> categories = MBCategoryUtil.findByGroupId(
			portletDataContext.getScopeGroupId());

		for (MBCategory category : categories) {
			exportCategory(
				portletDataContext, categoriesElement, messagesElement,
				threadFlagsElement, category);
		}

		List<MBMessage> messages = MBMessageUtil.findByG_C(
			portletDataContext.getScopeGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		for (MBMessage message : messages) {
			exportMessage(
				portletDataContext, categoriesElement, messagesElement,
				threadFlagsElement, message);
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "user-bans")) {
			List<MBBan> bans = MBBanUtil.findByGroupId(
				portletDataContext.getScopeGroupId());

			for (MBBan ban : bans) {
				exportBan(portletDataContext, userBansElement, ban);
			}
		}

		return document.formattedString();
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.messageboards",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element categoriesElement = rootElement.element("categories");

		for (Element categoryElement : categoriesElement.elements("category")) {
			String path = categoryElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			MBCategory category =
				(MBCategory)portletDataContext.getZipEntryAsObject(path);

			importCategory(portletDataContext, path, category);
		}

		Element messagesElement = rootElement.element("messages");

		for (Element messageElement : messagesElement.elements("message")) {
			String path = messageElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			MBMessage message =
				(MBMessage)portletDataContext.getZipEntryAsObject(path);

			importMessage(portletDataContext, messageElement, message);
		}

		if (portletDataContext.getBooleanParameter(
				_NAMESPACE, "thread-flags")) {

			Element threadFlagsElement = rootElement.element("thread-flags");

			for (Element threadFlagElement :
					threadFlagsElement.elements("thread-flag")) {

				String path = threadFlagElement.attributeValue("path");

				if (!portletDataContext.isPathNotProcessed(path)) {
					continue;
				}

				MBThreadFlag threadFlag =
					(MBThreadFlag)portletDataContext.getZipEntryAsObject(path);

				importThreadFlag(
					portletDataContext, threadFlagElement, threadFlag);
			}
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "user-bans")) {
			Element userBansElement = rootElement.element("user-bans");

			for (Element userBanElement :
					userBansElement.elements("user-ban")) {

				String path = userBanElement.attributeValue("path");

				if (!portletDataContext.isPathNotProcessed(path)) {
					continue;
				}

				MBBan ban = (MBBan)portletDataContext.getZipEntryAsObject(path);

				importBan(portletDataContext, userBanElement, ban);
			}
		}

		return null;
	}

	protected void exportBan(
			PortletDataContext portletDataContext, Element userBansElement,
			MBBan ban)
		throws Exception {

		if (!portletDataContext.isWithinDateRange(ban.getModifiedDate())) {
			return;
		}

		String path = getUserBanPath(portletDataContext, ban);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element userBanElement = userBansElement.addElement("user-ban");

		ban.setBanUserUuid(ban.getBanUserUuid());

		portletDataContext.addClassedModel(
			userBanElement, path, ban, _NAMESPACE);
	}

	protected void exportCategory(
			PortletDataContext portletDataContext, Element categoriesElement,
			Element messagesElement, Element threadFlagsElement,
			MBCategory category)
		throws Exception {

		if (portletDataContext.isWithinDateRange(category.getModifiedDate())) {
			exportParentCategory(
				portletDataContext, categoriesElement,
				category.getParentCategoryId());

			String path = getCategoryPath(portletDataContext, category);

			if (portletDataContext.isPathNotProcessed(path)) {
				Element categoryElement = categoriesElement.addElement(
					"category");

				portletDataContext.addClassedModel(
					categoryElement, path, category, _NAMESPACE);
			}
		}

		List<MBMessage> messages = MBMessageUtil.findByG_C(
			category.getGroupId(), category.getCategoryId());

		for (MBMessage message : messages) {
			exportMessage(
				portletDataContext, categoriesElement, messagesElement,
				threadFlagsElement, message);
		}
	}

	protected void exportMessage(
			PortletDataContext portletDataContext, Element categoriesElement,
			Element messagesElement, Element threadFlagsElement,
			MBMessage message)
		throws Exception {

		if (!portletDataContext.isWithinDateRange(message.getModifiedDate())) {
			return;
		}

		if (message.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			return;
		}

		exportParentCategory(
			portletDataContext, categoriesElement, message.getCategoryId());

		String path = getMessagePath(portletDataContext, message);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element messageElement = messagesElement.addElement("message");

		message.setPriority(message.getPriority());

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "attachments") &&
			message.isAttachments()) {

			for (String attachment : message.getAttachmentsFiles()) {
				int pos = attachment.lastIndexOf(CharPool.FORWARD_SLASH);

				String name = attachment.substring(pos + 1);
				String binPath = getMessageAttachementBinPath(
					portletDataContext, message, name);

				Element attachmentElement = messageElement.addElement(
					"attachment");

				attachmentElement.addAttribute("name", name);
				attachmentElement.addAttribute("bin-path", binPath);

				byte[] bytes = DLStoreUtil.getFileAsBytes(
					portletDataContext.getCompanyId(), CompanyConstants.SYSTEM,
					attachment);

				portletDataContext.addZipEntry(binPath, bytes);
			}

			message.setAttachmentsDir(message.getAttachmentsDir());
		}

		if (portletDataContext.getBooleanParameter(
				_NAMESPACE, "thread-flags")) {

			List<MBThreadFlag> threadFlags = MBThreadFlagUtil.findByThreadId(
				message.getThreadId());

			for (MBThreadFlag threadFlag : threadFlags) {
				exportThreadFlag(
					portletDataContext, threadFlagsElement, threadFlag);
			}
		}

		portletDataContext.addClassedModel(
			messageElement, path, message, _NAMESPACE);
	}

	protected void exportParentCategory(
			PortletDataContext portletDataContext, Element categoriesElement,
			long categoryId)
		throws Exception {

		if ((!portletDataContext.hasDateRange()) ||
			(categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(categoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return;
		}

		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);

		exportParentCategory(
			portletDataContext, categoriesElement,
			category.getParentCategoryId());

		String path = getCategoryPath(portletDataContext, category);

		if (portletDataContext.isPathNotProcessed(path)) {
			Element categoryElement = categoriesElement.addElement("category");

			portletDataContext.addClassedModel(
				categoryElement, path, category, _NAMESPACE);
		}
	}

	protected void exportThreadFlag(
			PortletDataContext portletDataContext, Element threadFlagsElement,
			MBThreadFlag threadFlag)
		throws Exception {

		String path = getThreadFlagPath(portletDataContext, threadFlag);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element threadFlagElement = threadFlagsElement.addElement(
			"thread-flag");

		MBThread thread = MBThreadLocalServiceUtil.getThread(
			threadFlag.getThreadId());

		MBMessage rootMessage = MBMessageLocalServiceUtil.getMessage(
			thread.getRootMessageId());

		threadFlagElement.addAttribute(
			"root-message-uuid", rootMessage.getUuid());

		portletDataContext.addClassedModel(
			threadFlagElement, path, threadFlag, _NAMESPACE);
	}

	protected List<ObjectValuePair<String, InputStream>> getAttachments(
		PortletDataContext portletDataContext, Element messageElement,
		MBMessage message) {

		if (!message.isAttachments() &&
			portletDataContext.getBooleanParameter(_NAMESPACE, "attachments")) {

			return Collections.emptyList();
		}

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<ObjectValuePair<String, InputStream>>();

		List<Element> attachmentElements = messageElement.elements(
			"attachment");

		for (Element attachmentElement : attachmentElements) {
			String name = attachmentElement.attributeValue("name");
			String binPath = attachmentElement.attributeValue("bin-path");

			InputStream inputStream =
				portletDataContext.getZipEntryAsInputStream(binPath);

			ObjectValuePair<String, InputStream> inputStreamOVP =
				new ObjectValuePair<String, InputStream>(name, inputStream);

			inputStreamOVPs.add(inputStreamOVP);
		}

		if (inputStreamOVPs.isEmpty()) {
			_log.error(
				"Could not find attachments for message " +
					message.getMessageId());
		}

		return inputStreamOVPs;
	}

	protected long getCategoryId(
			PortletDataContext portletDataContext, MBMessage message,
			Map<Long, Long> categoryPKs, long categoryId)
		throws Exception {

		if ((categoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(categoryId != MBCategoryConstants.DISCUSSION_CATEGORY_ID) &&
			(categoryId == message.getCategoryId())) {

			String path = getImportCategoryPath(portletDataContext, categoryId);

			MBCategory category =
				(MBCategory)portletDataContext.getZipEntryAsObject(path);

			importCategory(portletDataContext, path, category);

			categoryId = MapUtil.getLong(
				categoryPKs, message.getCategoryId(), message.getCategoryId());
		}

		return categoryId;
	}

	protected String getCategoryPath(
		PortletDataContext portletDataContext, MBCategory category) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/categories/");
		sb.append(category.getCategoryId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getImportCategoryPath(
		PortletDataContext portletDataContext, long categoryId) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getSourcePortletPath(
				PortletKeys.MESSAGE_BOARDS));
		sb.append("/categories/");
		sb.append(categoryId);
		sb.append(".xml");

		return sb.toString();
	}

	protected String getMessageAttachementBinPath(
		PortletDataContext portletDataContext, MBMessage message,
		String attachment) {

		StringBundler sb = new StringBundler(5);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/bin/");
		sb.append(message.getMessageId());
		sb.append(StringPool.SLASH);
		sb.append(PortalUUIDUtil.generate());

		return sb.toString();
	}

	protected String getMessagePath(
		PortletDataContext portletDataContext, MBMessage message) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/messages/");
		sb.append(message.getMessageId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getThreadFlagPath(
		PortletDataContext portletDataContext, MBThreadFlag threadFlag) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/thread-flags/");
		sb.append(threadFlag.getThreadFlagId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getUserBanPath(
		PortletDataContext portletDataContext, MBBan ban) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/user-bans/");
		sb.append(ban.getBanId());
		sb.append(".xml");

		return sb.toString();
	}

	protected void importBan(
			PortletDataContext portletDataContext, Element userBanElement,
			MBBan ban)
		throws Exception {

		long userId = portletDataContext.getUserId(ban.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			userBanElement, ban, _NAMESPACE);

		List<User> users = UserUtil.findByUuid(ban.getBanUserUuid());

		Iterator<User> itr = users.iterator();

		if (itr.hasNext()) {
			User user = itr.next();

			MBBanLocalServiceUtil.addBan(
				userId, user.getUserId(), serviceContext);
		}
		else {
			_log.error(
				"Could not find banned user with uuid " + ban.getBanUserUuid());
		}
	}

	protected void importCategory(
			PortletDataContext portletDataContext, String categoryPath,
			MBCategory category)
		throws Exception {

		long userId = portletDataContext.getUserId(category.getUserUuid());

		Map<Long, Long> categoryPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBCategory.class);

		long parentCategoryId = MapUtil.getLong(
			categoryPKs, category.getParentCategoryId(),
			category.getParentCategoryId());

		String emailAddress = null;
		String inProtocol = null;
		String inServerName = null;
		int inServerPort = 0;
		boolean inUseSSL = false;
		String inUserName = null;
		String inPassword = null;
		int inReadInterval = 0;
		String outEmailAddress = null;
		boolean outCustom = false;
		String outServerName = null;
		int outServerPort = 0;
		boolean outUseSSL = false;
		String outUserName = null;
		String outPassword = null;
		boolean allowAnonymous = false;
		boolean mailingListActive = false;

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			categoryPath, category, _NAMESPACE);

		if ((parentCategoryId !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(parentCategoryId != MBCategoryConstants.DISCUSSION_CATEGORY_ID) &&
			(parentCategoryId == category.getParentCategoryId())) {

			String path = getImportCategoryPath(
				portletDataContext, parentCategoryId);

			MBCategory parentCategory =
				(MBCategory)portletDataContext.getZipEntryAsObject(path);

			importCategory(portletDataContext, path, parentCategory);

			parentCategoryId = MapUtil.getLong(
				categoryPKs, category.getParentCategoryId(),
				category.getParentCategoryId());
		}

		MBCategory importedCategory = null;

		if (portletDataContext.isDataStrategyMirror()) {
			MBCategory existingCategory = MBCategoryUtil.fetchByUUID_G(
				category.getUuid(), portletDataContext.getScopeGroupId());

			if (existingCategory == null) {
				serviceContext.setUuid(category.getUuid());

				importedCategory = MBCategoryLocalServiceUtil.addCategory(
					userId, parentCategoryId, category.getName(),
					category.getDescription(), category.getDisplayStyle(),
					emailAddress, inProtocol, inServerName, inServerPort,
					inUseSSL, inUserName, inPassword, inReadInterval,
					outEmailAddress, outCustom, outServerName, outServerPort,
					outUseSSL, outUserName, outPassword, allowAnonymous,
					mailingListActive, serviceContext);
			}
			else {
				importedCategory = MBCategoryLocalServiceUtil.updateCategory(
					existingCategory.getCategoryId(), parentCategoryId,
					category.getName(), category.getDescription(),
					category.getDisplayStyle(), emailAddress, inProtocol,
					inServerName, inServerPort, inUseSSL, inUserName,
					inPassword, inReadInterval, outEmailAddress, outCustom,
					outServerName, outServerPort, outUseSSL, outUserName,
					outPassword, allowAnonymous, mailingListActive, false,
					serviceContext);
			}
		}
		else {
			importedCategory = MBCategoryLocalServiceUtil.addCategory(
				userId, parentCategoryId, category.getName(),
				category.getDescription(), category.getDisplayStyle(),
				emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
				inUserName, inPassword, inReadInterval, outEmailAddress,
				outCustom, outServerName, outServerPort, outUseSSL, outUserName,
				outPassword, allowAnonymous, mailingListActive, serviceContext);
		}

		portletDataContext.importClassedModel(
			category, importedCategory, _NAMESPACE);
	}

	protected void importMessage(
			PortletDataContext portletDataContext, Element messageElement,
			MBMessage message)
		throws Exception {

		long userId = portletDataContext.getUserId(message.getUserUuid());
		String userName = message.getUserName();

		Map<Long, Long> categoryPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBCategory.class);

		long categoryId = MapUtil.getLong(
			categoryPKs, message.getCategoryId(), message.getCategoryId());

		Map<Long, Long> threadPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBThread.class);

		long threadId = MapUtil.getLong(threadPKs, message.getThreadId(), 0);

		Map<Long, Long> messagePKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBMessage.class);

		long parentMessageId = MapUtil.getLong(
			messagePKs, message.getParentMessageId(),
			message.getParentMessageId());

		List<String> existingFiles = new ArrayList<String>();

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			getAttachments(portletDataContext, messageElement, message);

		try {
			ServiceContext serviceContext =
				portletDataContext.createServiceContext(
					messageElement, message, _NAMESPACE);

			if (message.getStatus() != WorkflowConstants.STATUS_APPROVED) {
				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_SAVE_DRAFT);
			}

			categoryId = getCategoryId(
				portletDataContext, message, categoryPKs, categoryId);

			MBMessage importedMessage = null;

			if (portletDataContext.isDataStrategyMirror()) {
				MBMessage existingMessage = MBMessageUtil.fetchByUUID_G(
					message.getUuid(), portletDataContext.getScopeGroupId());

				if (existingMessage == null) {
					serviceContext.setUuid(message.getUuid());

					importedMessage = MBMessageLocalServiceUtil.addMessage(
						userId, userName, portletDataContext.getScopeGroupId(),
						categoryId, threadId, parentMessageId,
						message.getSubject(), message.getBody(),
						message.getFormat(), inputStreamOVPs,
						message.getAnonymous(), message.getPriority(),
						message.getAllowPingbacks(), serviceContext);
				}
				else {
					importedMessage = MBMessageLocalServiceUtil.updateMessage(
						userId, existingMessage.getMessageId(),
						message.getSubject(), message.getBody(),
						inputStreamOVPs, existingFiles, message.getPriority(),
						message.getAllowPingbacks(), serviceContext);
				}
			}
			else {
				importedMessage = MBMessageLocalServiceUtil.addMessage(
					userId, userName, portletDataContext.getScopeGroupId(),
					categoryId, threadId, parentMessageId, message.getSubject(),
					message.getBody(), message.getFormat(), inputStreamOVPs,
					message.getAnonymous(), message.getPriority(),
					message.getAllowPingbacks(), serviceContext);
			}

			threadPKs.put(message.getThreadId(), importedMessage.getThreadId());

			portletDataContext.importClassedModel(
				message, importedMessage, _NAMESPACE);
		}
		finally {
			for (ObjectValuePair<String, InputStream> inputStreamOVP :
					inputStreamOVPs) {

				InputStream inputStream = inputStreamOVP.getValue();

				StreamUtil.cleanUp(inputStream);
			}
		}
	}

	protected void importThreadFlag(
			PortletDataContext portletDataContext, Element threadFlagElement,
			MBThreadFlag threadFlag)
		throws Exception {

		long userId = portletDataContext.getUserId(threadFlag.getUserUuid());

		Map<Long, Long> messagePKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBMessage.class);

		long threadId = MapUtil.getLong(
			messagePKs, threadFlag.getThreadId(), threadFlag.getThreadId());

		MBThread thread = MBThreadUtil.fetchByPrimaryKey(threadId);

		if (thread == null) {
			String rootMessageUuid = threadFlagElement.attributeValue(
				"root-message-uuid");

			MBMessage rootMessage = MBMessageUtil.fetchByUUID_G(
				rootMessageUuid, portletDataContext.getScopeGroupId());

			if (rootMessage != null) {
				thread = rootMessage.getThread();
			}
		}

		if (thread == null) {
			return;
		}

		MBThreadFlagLocalServiceUtil.addThreadFlag(userId, thread);
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final String _NAMESPACE = "message_board";

	private static Log _log = LogFactoryUtil.getLog(
		MBPortletDataHandlerImpl.class);

	private static PortletDataHandlerBoolean _categoriesAndMessages =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "categories-and-messages", true, true);

	private static PortletDataHandlerControl[] _metadataControls =
		new PortletDataHandlerControl[] {
			new PortletDataHandlerBoolean(_NAMESPACE, "attachments"),
			new PortletDataHandlerBoolean(_NAMESPACE, "ratings"),
			new PortletDataHandlerBoolean(_NAMESPACE, "tags")
		};

	private static PortletDataHandlerBoolean _threadFlags =
		new PortletDataHandlerBoolean(_NAMESPACE, "thread-flags");

	private static PortletDataHandlerBoolean _userBans =
		new PortletDataHandlerBoolean(_NAMESPACE, "user-bans");

}