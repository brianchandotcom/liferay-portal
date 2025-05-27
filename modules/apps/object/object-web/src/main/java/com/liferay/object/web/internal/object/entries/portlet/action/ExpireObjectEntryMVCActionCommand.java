/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.entries.portlet.action;

import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.resource.v1_0.ObjectEntryResource;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import java.util.Objects;

/**
 * @author Jhosseph Gonzalez
 */
public class ExpireObjectEntryMVCActionCommand extends BaseMVCActionCommand {

	public ExpireObjectEntryMVCActionCommand(
		ObjectEntryLocalService objectEntryLocalService,
		ObjectEntryResource.Factory objectEntryResourceFactory) {

		_objectEntryLocalService = objectEntryLocalService;
		_objectEntryResourceFactory = objectEntryResourceFactory;
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		ObjectEntryResource.Builder builder =
			_objectEntryResourceFactory.create();

		long objectEntryId = ParamUtil.getLong(actionRequest, "objectEntryId");

		ObjectEntry serviceBuilderObjectEntry =
			_objectEntryLocalService.getObjectEntry(objectEntryId);

		if (!Objects.equals(
				serviceBuilderObjectEntry.getStatus(),
				WorkflowConstants.STATUS_DRAFT) &&
			!Objects.equals(
				serviceBuilderObjectEntry.getStatus(),
				WorkflowConstants.STATUS_PENDING)) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			ObjectEntryResource objectEntryResource = builder.user(
				themeDisplay.getUser()
			).preferredLocale(
				themeDisplay.getLocale()
			).build();

			objectEntryResource.patchExpireObjectEntry(objectEntryId);
		}
	}

	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectEntryResource.Factory _objectEntryResourceFactory;

}