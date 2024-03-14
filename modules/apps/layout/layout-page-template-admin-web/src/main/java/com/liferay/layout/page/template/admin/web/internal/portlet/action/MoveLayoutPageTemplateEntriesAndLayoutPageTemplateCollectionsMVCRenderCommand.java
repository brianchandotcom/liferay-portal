/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.portlet.action;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.item.selector.criterion.LayoutPageTemplateCollectionTreeNodeItemSelectorCriterion;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.constants.MVCRenderConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bárbara Cabrera
 */
@Component(
	property = {
		"javax.portlet.name=" + LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
		"mvc.command.name=/layout_page_template_admin/move_layout_page_template_entries_and_layout_page_template_collections"
	},
	service = MVCRenderCommand.class
)
public class
	MoveLayoutPageTemplateEntriesAndLayoutPageTemplateCollectionsMVCRenderCommand
		implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		LayoutPageTemplateCollectionTreeNodeItemSelectorCriterion
			layoutPageTemplateCollectionTreeNodeItemSelectorCriterion =
				new LayoutPageTemplateCollectionTreeNodeItemSelectorCriterion();

		layoutPageTemplateCollectionTreeNodeItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(new UUIDItemSelectorReturnType());

		long[] selectedLayoutPageTemplateCollections = ParamUtil.getLongValues(
			renderRequest, "selectedFolders");

		layoutPageTemplateCollectionTreeNodeItemSelectorCriterion.
			setLayoutPageTemplateCollectionIds(
				selectedLayoutPageTemplateCollections);

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(renderRequest);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		try {
			httpServletResponse.sendRedirect(
				String.valueOf(
					_itemSelector.getItemSelectorURL(
						requestBackedPortletURLFactory,
						renderResponse.getNamespace() + "selectFolder",
						layoutPageTemplateCollectionTreeNodeItemSelectorCriterion)));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
	}

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Portal _portal;

}