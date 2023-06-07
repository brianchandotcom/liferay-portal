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

package com.liferay.blogs.web.internal.portlet.action;

import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.exportimport.changeset.Changeset;
import com.liferay.exportimport.changeset.portlet.action.ExportImportChangesetMVCActionCommandHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_ADMIN,
		"mvc.command.name=/blogs/publish_entry"
	},
	service = MVCActionCommand.class
)
public class PublishEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		Changeset.Builder builder = Changeset.create();

		Changeset changeset = builder.addStagedModel(
			() -> _fetchEntry(entryId)
		).build();

		_exportImportChangesetMVCActionCommandHelper.publish(
			actionRequest, actionResponse, changeset);

		SessionMessages.add(actionRequest, "blogsEntryPublished");
	}

	private BlogsEntry _fetchEntry(long entryId) {
		try {
			return _blogsEntryService.getEntry(entryId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get blogs entry " + entryId, portalException);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PublishEntryMVCActionCommand.class);

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private ExportImportChangesetMVCActionCommandHelper
		_exportImportChangesetMVCActionCommandHelper;

}