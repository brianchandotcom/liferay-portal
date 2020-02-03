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

package com.liferay.depot.web.internal.item.selector.provider;

import com.liferay.depot.web.internal.application.controller.DepotApplicationController;
import com.liferay.depot.web.internal.constants.DepotAdminWebKeys;
import com.liferay.depot.web.internal.display.context.DepotApplicationDisplayContext;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewRenderer;
import com.liferay.item.selector.criteria.audio.criterion.AudioItemSelectorCriterion;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.criteria.video.criterion.VideoItemSelectorCriterion;
import com.liferay.item.selector.provider.ItemSelectorViewRendererProvider;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.PortalIncludeUtil;

import java.io.IOException;

import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "service.ranking:Integer=100",
	service = ItemSelectorViewRendererProvider.class
)
public class DepotItemSelectorViewRendererProvider
	implements ItemSelectorViewRendererProvider {

	@Override
	public ItemSelectorViewRenderer getItemSelectorViewRenderer(
		ItemSelectorView<ItemSelectorCriterion> itemSelectorView,
		ItemSelectorCriterion itemSelectorCriterion, PortletURL portletURL,
		String itemSelectedEventName, boolean search) {

		String portletId = _itemSelectorCriterionMap.get(
			itemSelectorCriterion.getClass());

		ItemSelectorViewRenderer itemSelectorViewRenderer =
			_itemSelectorViewRendererProvider.getItemSelectorViewRenderer(
				itemSelectorView, itemSelectorCriterion, portletURL,
				itemSelectedEventName, search);

		if (Validator.isNotNull(portletId)) {
			return new ItemSelectorViewRenderer() {

				@Override
				public String getItemSelectedEventName() {
					return itemSelectedEventName;
				}

				@Override
				public ItemSelectorCriterion getItemSelectorCriterion() {
					return itemSelectorCriterion;
				}

				@Override
				public ItemSelectorView<ItemSelectorCriterion>
					getItemSelectorView() {

					return itemSelectorView;
				}

				@Override
				public PortletURL getPortletURL() {
					return portletURL;
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

							if ((scopeGroup.getType() !=
									GroupConstants.TYPE_DEPOT) ||
								_depotApplicationController.isEnabled(
									portletId, scopeGroup.getGroupId())) {

								itemSelectorViewRenderer.renderHTML(
									pageContext);

								return;
							}

							RequestDispatcher requestDispatcher =
								_servletContext.getRequestDispatcher(
									"/item/selector/application_disabled.jsp");

							DepotApplicationDisplayContext
								depotApplicationDisplayContext =
									new DepotApplicationDisplayContext(
										httpServletRequest, _portal);

							depotApplicationDisplayContext.setPortletId(
								portletId);
							depotApplicationDisplayContext.setPortletURL(
								portletURL);

							httpServletRequest.setAttribute(
								DepotAdminWebKeys.
									DEPOT_APPLICATION_DISPLAY_CONTEXT,
								depotApplicationDisplayContext);

							requestDispatcher.include(
								httpServletRequest, httpServletResponse);
						});
				}

			};
		}

		return itemSelectorViewRenderer;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.depot.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Reference
	private DepotApplicationController _depotApplicationController;

	private final Map<Class<? extends ItemSelectorCriterion>, String>
		_itemSelectorCriterionMap =
			HashMapBuilder.<Class<? extends ItemSelectorCriterion>, String>put(
				AudioItemSelectorCriterion.class,
				DLPortletKeys.DOCUMENT_LIBRARY_ADMIN
			).put(
				FileItemSelectorCriterion.class,
				DLPortletKeys.DOCUMENT_LIBRARY_ADMIN
			).put(
				ImageItemSelectorCriterion.class,
				DLPortletKeys.DOCUMENT_LIBRARY_ADMIN
			).put(
				LayoutItemSelectorCriterion.class, JournalPortletKeys.JOURNAL
			).put(
				VideoItemSelectorCriterion.class,
				DLPortletKeys.DOCUMENT_LIBRARY_ADMIN
			).build();

	@Reference(
		target = "(!(component.name=com.liferay.depot.web.internal.item.selector.provider.DepotItemSelectorViewRendererProvider))"
	)
	private ItemSelectorViewRendererProvider _itemSelectorViewRendererProvider;

	@Reference
	private Portal _portal;

	private ServletContext _servletContext;

}