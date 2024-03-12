/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.portlet.action;

import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bárbara Cabrera
 */
@Component(
	property = {
		"javax.portlet.name=" + LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
		"mvc.command.name=/layout_page_template_admin/move_layout_page_template_collection"
	},
	service = MVCActionCommand.class
)
public class MoveLayoutPageTemplateCollectionMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long layoutPageTemplateCollectionId = ParamUtil.getLong(
			actionRequest, "layoutPageTemplateCollectionId");
		long targetLayoutPageTemplateCollectionId = ParamUtil.getLong(
			actionRequest, "targetLayoutPageTemplateCollectionId");

		try {
			LayoutPageTemplateCollection layoutPageTemplateCollection =
				_layoutPageTemplateCollectionService.
					moveLayoutPageTemplateCollection(
						layoutPageTemplateCollectionId,
						targetLayoutPageTemplateCollectionId);

			sendRedirect(
				actionRequest, actionResponse,
				PortletURLBuilder.createRenderURL(
					_portal.getLiferayPortletResponse(actionResponse)
				).setTabs1(
					"page-templates"
				).setParameter(
					"layoutPageTemplateCollectionId",
					layoutPageTemplateCollection.
						getParentLayoutPageTemplateCollectionId()
				).buildString());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			SessionErrors.add(
				actionRequest, "moveLayoutPageTemplateCollectionErrorMessage",
				StringUtil.trim(portalException.getMessage()));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MoveLayoutPageTemplateCollectionMVCActionCommand.class);

	@Reference
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

	@Reference
	private Portal _portal;

}