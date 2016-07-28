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

package com.liferay.blogs.verify;

import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.verify.VerifyProcess;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {"verify.process.name=com.liferay.blogs.service"},
	service = VerifyProcess.class
)
public class BlogsServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		updateEntryAssets();
		verifyStatus();
	}

	@Reference(unbind = "-")
	protected void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	protected void updateEntryAssets() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<BlogsEntry> entries =
				_blogsEntryLocalService.getNoAssetEntries();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Processing " + entries.size() + " entries with no asset");
			}

			for (BlogsEntry entry : entries) {
				try {
					_blogsEntryLocalService.updateAsset(
						entry.getUserId(), entry, null, null, null, null);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to update asset for entry " +
								entry.getEntryId() + ": " + e.getMessage());
					}
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Assets verified for entries");
			}
		}
	}

	protected void verifyStatus() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL(
				"update BlogsEntry set status = " +
					WorkflowConstants.STATUS_APPROVED +
						" where status is null");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsServiceVerifyProcess.class);

	private BlogsEntryLocalService _blogsEntryLocalService;

}