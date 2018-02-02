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

package com.liferay.mentions.internal.service;

import com.liferay.mentions.configuration.MentionsGroupServiceConfiguration;
import com.liferay.mentions.util.MentionsNotifier;
import com.liferay.mentions.util.MentionsUtil;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBMessageLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class MentionsMessageServiceWrapper
	extends MBMessageLocalServiceWrapper {

	public MentionsMessageServiceWrapper() {
		super(null);
	}

	public MentionsMessageServiceWrapper(
		MBMessageLocalService mbMessageLocalService) {

		super(mbMessageLocalService);
	}

	@Override
	public MBMessage updateStatus(
			long userId, long messageId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		MBMessage message = _mbMessageLocalService.getMessage(messageId);

		int oldStatus = message.getStatus();

		message = super.updateStatus(
			userId, messageId, status, serviceContext, workflowContext);

		if ((status != WorkflowConstants.STATUS_APPROVED) ||
			(oldStatus == WorkflowConstants.STATUS_IN_TRASH)) {

			return message;
		}

		long siteGroupId = _portal.getSiteGroupId(message.getGroupId());

		if (!MentionsUtil.isMentionsEnabled(siteGroupId)) {
			return message;
		}

		String content = message.getBody();

		if (message.isFormatBBCode()) {
			content = BBCodeTranslatorUtil.getHTML(content);
		}

		String title = message.getSubject();

		if (message.isDiscussion()) {
			title = StringUtil.shorten(HtmlUtil.extractText(content), 100);
		}

		MentionsGroupServiceConfiguration mentionsGroupServiceConfiguration =
			_configurationProvider.getCompanyConfiguration(
				MentionsGroupServiceConfiguration.class,
				message.getCompanyId());

		LocalizedValuesMap subjectLocalizedValuesMap =
			mentionsGroupServiceConfiguration.commentMentionEmailSubject();
		LocalizedValuesMap bodyLocalizedValuesMap =
			mentionsGroupServiceConfiguration.commentMentionEmailBody();

		if (!message.isDiscussion()) {
			subjectLocalizedValuesMap =
				mentionsGroupServiceConfiguration.
					assetEntryMentionEmailSubject();
			bodyLocalizedValuesMap =
				mentionsGroupServiceConfiguration.assetEntryMentionEmailBody();
		}

		String contentURL = (String)serviceContext.getAttribute("contentURL");

		if (Validator.isNull(contentURL)) {
			serviceContext.setAttribute(
				"contentURL", workflowContext.get("url"));
		}

		_mentionsNotifier.notify(
			userId, message.getGroupId(), title, content,
			message.getModelClassName(), message.getMessageId(),
			subjectLocalizedValuesMap, bodyLocalizedValuesMap, serviceContext);

		return message;
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	@Reference(unbind = "-")
	protected void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	@Reference(unbind = "-")
	protected void setMentionsNotifier(MentionsNotifier mentionsNotifier) {
		_mentionsNotifier = mentionsNotifier;
	}

	private ConfigurationProvider _configurationProvider;
	private MBMessageLocalService _mbMessageLocalService;
	private MentionsNotifier _mentionsNotifier;

	@Reference
	private Portal _portal;

}