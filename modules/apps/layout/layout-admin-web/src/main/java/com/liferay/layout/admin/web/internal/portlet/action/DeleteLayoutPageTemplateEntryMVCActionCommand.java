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

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.exception.RequiredLayoutPageTemplateEntryException;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout/delete_layout_page_template_entry"
	},
	service = MVCActionCommand.class
)
public class DeleteLayoutPageTemplateEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] deleteLayoutPageTemplateEntryIds = null;

		long layoutPageTemplateEntryId = ParamUtil.getLong(
			actionRequest, "layoutPageTemplateEntryId");

		if (layoutPageTemplateEntryId > 0) {
			deleteLayoutPageTemplateEntryIds = new long[] {
				layoutPageTemplateEntryId
			};
		}
		else {
			deleteLayoutPageTemplateEntryIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");
		}

		List<Long> deleteLayoutPageTemplateIdsList = new ArrayList<>();

		for (long deleteLayoutPageTemplateEntryId :
				deleteLayoutPageTemplateEntryIds) {

			int assetDisplayPageEntriesCount =
				_assetDisplayPageEntryLocalService.
					getAssetDisplayPageEntriesCountByLayoutPageTemplateEntryId(
						deleteLayoutPageTemplateEntryId);

			try {
				if (assetDisplayPageEntriesCount > 0) {
					deleteLayoutPageTemplateIdsList.add(
						deleteLayoutPageTemplateEntryId);

					SessionErrors.add(
						actionRequest,
						RequiredLayoutPageTemplateEntryException.class);
				}
				else {
					_layoutPageTemplateEntryService.
						deleteLayoutPageTemplateEntry(
							deleteLayoutPageTemplateEntryId);
				}
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}

				deleteLayoutPageTemplateIdsList.add(
					deleteLayoutPageTemplateEntryId);
			}
		}

		if (!deleteLayoutPageTemplateIdsList.isEmpty()) {
			SessionErrors.add(actionRequest, PortalException.class);

			hideDefaultErrorMessage(actionRequest);

			sendRedirect(actionRequest, actionResponse);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteLayoutPageTemplateEntryMVCActionCommand.class);

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

}