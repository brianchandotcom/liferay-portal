/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.fragment.web.internal.portlet.action;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.Portal;

import jakarta.portlet.PortletURL;
import jakarta.portlet.ResourceRequest;
import jakarta.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Returns a portlet URL pointing at the Info Item selector together with the
 * event name the selector fires when an item is picked. The Data Set fragment
 * configuration UI uses both to open the selector via openSelectionModal.
 *
 * @author Daniel Sanz
 */
@Component(
	property = {
		"jakarta.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/frontend_data_set_fragment/get_info_item_selector_url"
	},
	service = MVCResourceCommand.class
)
public class GetInfoItemSelectorURLMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		String portletNamespace = _portal.getPortletNamespace(
			ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET);

		String eventName = portletNamespace + "selectInfoItem";

		InfoItemItemSelectorCriterion itemSelectorCriterion =
			new InfoItemItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new InfoItemItemSelectorReturnType());

		PortletURL infoItemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(resourceRequest),
			eventName, itemSelectorCriterion);

		if (infoItemSelectorURL == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Item Selector returned a null URL for InfoItem criterion");
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, JSONUtil.put("url", ""));

			return;
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"eventName", eventName
			).put(
				"url", infoItemSelectorURL.toString()
			));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetInfoItemSelectorURLMVCResourceCommand.class);

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Portal _portal;

}