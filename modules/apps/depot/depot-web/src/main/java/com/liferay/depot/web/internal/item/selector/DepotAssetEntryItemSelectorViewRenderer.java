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

package com.liferay.depot.web.internal.item.selector;

import com.liferay.depot.application.DepotApplication;
import com.liferay.depot.web.internal.application.controller.DepotApplicationController;
import com.liferay.depot.web.internal.constants.DepotAdminWebKeys;
import com.liferay.depot.web.internal.display.context.DepotApplicationDisplayContext;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewRenderer;
import com.liferay.item.selector.criteria.asset.criterion.AssetEntryItemSelectorCriterion;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.PortalIncludeUtil;

import java.io.IOException;

import java.util.Map;
import java.util.Set;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alicia García
 */
@Component(service = ItemSelectorViewRenderer.class)
public class DepotAssetEntryItemSelectorViewRenderer
	implements ItemSelectorViewRenderer {

	public DepotAssetEntryItemSelectorViewRenderer(
		DepotApplicationController depotApplicationController,
		ItemSelectorViewRenderer itemSelectorViewRenderer, Portal portal,
		ServletContext servletContext) {

		_depotApplicationController = depotApplicationController;
		_itemSelectorViewRenderer = itemSelectorViewRenderer;
		_portal = portal;
		_servletContext = servletContext;
	}

	@Override
	public String getItemSelectedEventName() {
		return _itemSelectorViewRenderer.getItemSelectedEventName();
	}

	@Override
	public ItemSelectorCriterion getItemSelectorCriterion() {
		return _itemSelectorViewRenderer.getItemSelectorCriterion();
	}

	@Override
	public ItemSelectorView<ItemSelectorCriterion> getItemSelectorView() {
		return _itemSelectorViewRenderer.getItemSelectorView();
	}

	@Override
	public PortletURL getPortletURL() {
		return _itemSelectorViewRenderer.getPortletURL();
	}

	@Override
	public void renderHTML(PageContext pageContext)
		throws IOException, ServletException {

		PortalIncludeUtil.include(
			pageContext,
			(httpServletRequest, httpServletResponse) -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				Group scopeGroup = themeDisplay.getScopeGroup();

				if (scopeGroup.getType() != GroupConstants.TYPE_DEPOT) {
					_itemSelectorViewRenderer.renderHTML(pageContext);

					return;
				}

				String portletId = null;

				AssetEntryItemSelectorCriterion
					assetEntryItemSelectorCriterion =
						(AssetEntryItemSelectorCriterion)
							getItemSelectorCriterion();

				Set<String> typeSelections =
					_acceptedTypeSelectionPortletId.keySet();

				if (typeSelections.contains(
						assetEntryItemSelectorCriterion.getTypeSelection())) {

					for (DepotApplication depotApplication :
							_depotApplicationController.
								getCustomizableDepotApplications()) {

						String selectedPortletId =
							_acceptedTypeSelectionPortletId.get(
								assetEntryItemSelectorCriterion.
									getTypeSelection());

						if (selectedPortletId.equals(
								depotApplication.getPortletId())) {

							portletId = depotApplication.getPortletId();

							break;
						}
					}
				}

				if (Validator.isNotNull(portletId) &&
					_depotApplicationController.isEnabled(
						portletId, scopeGroup.getGroupId())) {

					_itemSelectorViewRenderer.renderHTML(pageContext);

					return;
				}

				RequestDispatcher requestDispatcher =
					_servletContext.getRequestDispatcher(
						"/item/selector/application_disabled.jsp");

				if (Validator.isNull(portletId)) {
					requestDispatcher = _servletContext.getRequestDispatcher(
						"/item/selector/application_not_supported.jsp");
				}

				DepotApplicationDisplayContext depotApplicationDisplayContext =
					new DepotApplicationDisplayContext(
						httpServletRequest, _portal);

				depotApplicationDisplayContext.setPortletId(portletId);
				depotApplicationDisplayContext.setPortletURL(
					_itemSelectorViewRenderer.getPortletURL());

				httpServletRequest.setAttribute(
					DepotAdminWebKeys.DEPOT_APPLICATION_DISPLAY_CONTEXT,
					depotApplicationDisplayContext);

				requestDispatcher.include(
					httpServletRequest, httpServletResponse);
			});
	}

	private static Map<String, String> _acceptedTypeSelectionPortletId =
		HashMapBuilder.put(
			FileEntry.class.getName(), DLPortletKeys.DOCUMENT_LIBRARY_ADMIN
		).put(
			Folder.class.getName(), DLPortletKeys.DOCUMENT_LIBRARY_ADMIN
		).put(
			JournalArticle.class.getName(), JournalPortletKeys.JOURNAL
		).put(
			JournalFolder.class.getName(), JournalPortletKeys.JOURNAL
		).build();

	private final DepotApplicationController _depotApplicationController;
	private final ItemSelectorViewRenderer _itemSelectorViewRenderer;
	private final Portal _portal;
	private final ServletContext _servletContext;

}