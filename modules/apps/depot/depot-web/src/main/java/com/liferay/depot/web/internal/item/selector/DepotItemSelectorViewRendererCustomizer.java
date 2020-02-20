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

import com.liferay.depot.web.internal.application.controller.DepotApplicationController;
import com.liferay.depot.web.internal.constants.DepotAdminWebKeys;
import com.liferay.depot.web.internal.display.context.DepotApplicationDisplayContext;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewRenderer;
import com.liferay.item.selector.ItemSelectorViewRendererCustomizer;
import com.liferay.item.selector.PortletItemSelectorView;
import com.liferay.item.selector.criteria.audio.criterion.AudioItemSelectorCriterion;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.item.selector.criteria.video.criterion.VideoItemSelectorCriterion;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.PortalIncludeUtil;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

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
@Component(service = ItemSelectorViewRendererCustomizer.class)
public class DepotItemSelectorViewRendererCustomizer
	implements ItemSelectorViewRendererCustomizer {

	@Override
	public ItemSelectorViewRenderer customizeItemSelectorViewRenderer(
		ItemSelectorViewRenderer itemSelectorViewRenderer) {

		ItemSelectorView<ItemSelectorCriterion> itemSelectorView =
			itemSelectorViewRenderer.getItemSelectorView();

		if (!(itemSelectorView instanceof PortletItemSelectorView)) {
			return itemSelectorViewRenderer;
		}

		PortletItemSelectorView<? extends ItemSelectorCriterion>
			portletItemSelectorView =
				(PortletItemSelectorView<?>)itemSelectorView;

		List<String> portletIds = portletItemSelectorView.getPortletIds();

		if (ListUtil.isEmpty(portletIds)) {
			return itemSelectorViewRenderer;
		}

		return new ItemSelectorViewRenderer() {

			@Override
			public String getItemSelectedEventName() {
				return itemSelectorViewRenderer.getItemSelectedEventName();
			}

			@Override
			public ItemSelectorCriterion getItemSelectorCriterion() {
				return itemSelectorViewRenderer.getItemSelectorCriterion();
			}

			@Override
			public ItemSelectorView<ItemSelectorCriterion>
				getItemSelectorView() {

				return itemSelectorViewRenderer.getItemSelectorView();
			}

			@Override
			public PortletURL getPortletURL() {
				return itemSelectorViewRenderer.getPortletURL();
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
							itemSelectorViewRenderer.renderHTML(pageContext);

							return;
						}

						String portletId = _getPortletId(
							scopeGroup.getGroupId());

						if (Validator.isNotNull(portletId)) {
							itemSelectorViewRenderer.renderHTML(pageContext);

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
							portletIds.get(0));
						depotApplicationDisplayContext.setPortletURL(
							itemSelectorViewRenderer.getPortletURL());

						httpServletRequest.setAttribute(
							DepotAdminWebKeys.DEPOT_APPLICATION_DISPLAY_CONTEXT,
							depotApplicationDisplayContext);

						requestDispatcher.include(
							httpServletRequest, httpServletResponse);
					});
			}

			private String _getPortletId(long groupId) {
				Stream<String> stream = portletIds.stream();

				return stream.filter(
					portletId -> _depotApplicationController.isEnabled(
						portletId, groupId)
				).findFirst(
				).orElse(
					StringPool.BLANK
				);
			}

		};
	}

	@Override
	public Collection<Class<? extends ItemSelectorCriterion>>
		getSupportedItemSelectorCriterionClasses() {

		return _supportedItemSelectorCriterionClasses;
	}

	private static final List<Class<? extends ItemSelectorCriterion>>
		_supportedItemSelectorCriterionClasses = Arrays.asList(
			AudioItemSelectorCriterion.class, FileItemSelectorCriterion.class,
			ImageItemSelectorCriterion.class,
			InfoItemItemSelectorCriterion.class,
			LayoutItemSelectorCriterion.class,
			VideoItemSelectorCriterion.class);

	@Reference
	private DepotApplicationController _depotApplicationController;

	@Reference
	private Portal _portal;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.depot.web)")
	private ServletContext _servletContext;

}