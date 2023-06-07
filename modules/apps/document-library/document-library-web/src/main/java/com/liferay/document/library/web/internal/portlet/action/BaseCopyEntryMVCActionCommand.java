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

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseCopyEntryMVCActionCommand
	extends BaseMVCActionCommand {

	protected abstract void doCopyEntry(ActionRequest actionRequest)
		throws PortalException;

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		try {
			_copyEntry(actionRequest, actionResponse);
		}
		catch (IOException ioException) {
			_log.error(ioException);

			throw new PortalException(ioException);
		}
	}

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected Language language;

	private void _checkDestinationRepositoryId(ActionRequest actionRequest)
		throws PortalException {

		long destinationRepositoryId = ParamUtil.getLong(
			actionRequest, "destinationRepositoryId");

		Group group = groupLocalService.fetchGroup(destinationRepositoryId);

		if ((group != null) && group.isStaged() && !group.isStagingGroup()) {
			throw new PortalException(
				"cannot-copy-entries-to-the-live-version-of-a-group");
		}
	}

	private void _copyEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		try {
			_checkDestinationRepositoryId(actionRequest);

			doCopyEntry(actionRequest);

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonFactory.createJSONObject());
		}
		catch (PortalException portalException) {
			String errorMessage = language.get(
				actionRequest.getLocale(), portalException.getMessage());

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put("errorMessage", errorMessage));

			hideDefaultSuccessMessage(actionRequest);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseCopyEntryMVCActionCommand.class);

}