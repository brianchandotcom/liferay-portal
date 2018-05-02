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

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.service.BlogsEntryLocalServiceWrapper;
import com.liferay.mentions.configuration.MentionsGroupServiceConfiguration;
import com.liferay.mentions.util.MentionsNotifier;
import com.liferay.mentions.util.MentionsUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.Portal;
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
public class MentionsBlogsEntryServiceWrapper
	extends BlogsEntryLocalServiceWrapper {

	public MentionsBlogsEntryServiceWrapper() {
		super(null);
	}

	public MentionsBlogsEntryServiceWrapper(
		BlogsEntryLocalService blogsEntryLocalService) {

		super(blogsEntryLocalService);
	}

	@Override
	public BlogsEntry updateStatus(
			long userId, long entryId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		BlogsEntry entry = _blogsEntryLocalService.getEntry(entryId);

		int oldStatus = entry.getStatus();

		entry = super.updateStatus(
			userId, entryId, status, serviceContext, workflowContext);

		if ((status != WorkflowConstants.STATUS_APPROVED) ||
			(oldStatus == WorkflowConstants.STATUS_IN_TRASH)) {

			return entry;
		}

		long siteGroupId = _portal.getSiteGroupId(entry.getGroupId());

		if (!MentionsUtil.isMentionsEnabled(siteGroupId)) {
			return entry;
		}

		String contentURL = (String)serviceContext.getAttribute("contentURL");

		if (Validator.isNull(contentURL)) {
			serviceContext.setAttribute(
				"contentURL", workflowContext.get("url"));
		}

		MentionsGroupServiceConfiguration mentionsGroupServiceConfiguration =
			_configurationProvider.getCompanyConfiguration(
				MentionsGroupServiceConfiguration.class, entry.getCompanyId());

		_mentionsNotifier.notify(
			userId, entry.getGroupId(), entry.getTitle(), entry.getContent(),
			BlogsEntry.class.getName(), entry.getEntryId(),
			mentionsGroupServiceConfiguration.assetEntryMentionEmailSubject(),
			mentionsGroupServiceConfiguration.assetEntryMentionEmailBody(),
			serviceContext);

		return entry;
	}

	@Reference(unbind = "-")
	protected void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	@Reference(unbind = "-")
	protected void setMentionsNotifier(MentionsNotifier mentionsNotifier) {
		_mentionsNotifier = mentionsNotifier;
	}

	private BlogsEntryLocalService _blogsEntryLocalService;
	private ConfigurationProvider _configurationProvider;
	private MentionsNotifier _mentionsNotifier;

	@Reference
	private Portal _portal;

}