/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mentions.internal.service;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.service.BlogsEntryLocalServiceWrapper;
import com.liferay.mentions.configuration.MentionsGroupServiceConfiguration;
import com.liferay.mentions.util.MentionsNotifier;
import com.liferay.mentions.util.MentionsUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
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
@Component(service = ServiceWrapper.class)
public class MentionsBlogsEntryServiceWrapper
	extends BlogsEntryLocalServiceWrapper {

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
			(oldStatus == WorkflowConstants.STATUS_IN_TRASH) ||
			!MentionsUtil.isMentionsEnabled(
				_portal.getSiteGroupId(entry.getGroupId()))) {

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

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private MentionsNotifier _mentionsNotifier;

	@Reference
	private Portal _portal;

}