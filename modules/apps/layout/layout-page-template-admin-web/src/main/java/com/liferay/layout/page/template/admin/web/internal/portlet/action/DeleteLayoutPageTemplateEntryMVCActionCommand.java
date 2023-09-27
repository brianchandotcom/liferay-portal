/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.portlet.action;

import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.exception.RequiredLayoutPageTemplateEntryException;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

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
	property = {
		"javax.portlet.name=" + LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
		"mvc.command.name=/layout_page_template_admin/delete_layout_page_template_entry"
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
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}

				deleteLayoutPageTemplateIdsList.add(
					deleteLayoutPageTemplateEntryId);
			}
		}

		if (!deleteLayoutPageTemplateIdsList.isEmpty()) {
			SessionErrors.add(actionRequest, PortalException.class);

			hideDefaultErrorMessage(actionRequest);
		}
		else {
			int total =
				deleteLayoutPageTemplateEntryIds.length -
					deleteLayoutPageTemplateIdsList.size();

			if (total > 0) {
				hideDefaultSuccessMessage(actionRequest);

				MultiSessionMessages.add(
					actionRequest, "displayPageTemplateDeleted",
					_language.format(
						_portal.getHttpServletRequest(actionRequest),
						"you-successfully-deleted-x-display-page-templates",
						new Object[] {total}));
			}
		}

		sendRedirect(actionRequest, actionResponse);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteLayoutPageTemplateEntryMVCActionCommand.class);

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private Language _language;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private Portal _portal;

}