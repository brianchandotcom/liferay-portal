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

package com.liferay.layout.content.page.editor.web.internal.display.context;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorWebKeys;
import com.liferay.layout.content.page.editor.sidebar.panel.ContentPageEditorSidebarPanel;
import com.liferay.layout.content.page.editor.web.internal.configuration.FFLayoutContentPageEditorConfiguration;
import com.liferay.layout.content.page.editor.web.internal.configuration.PageEditorConfiguration;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.staging.StagingGroupHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	configurationPid = {
		"com.liferay.layout.content.page.editor.web.internal.configuration.FFLayoutContentPageEditorConfiguration",
		"com.liferay.layout.content.page.editor.web.internal.configuration.PageEditorConfiguration"
	},
	immediate = true, service = ContentPageEditorDisplayContextProvider.class
)
public class ContentPageEditorDisplayContextProvider {

	public ContentPageEditorDisplayContext getContentPageEditorDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse,
		PortletRequest portletRequest) {

		String className = (String)httpServletRequest.getAttribute(
			ContentPageEditorWebKeys.CLASS_NAME);

		if (Objects.equals(className, Layout.class.getName())) {
			return new ContentPageLayoutEditorDisplayContext(
				_commentManager, _getContentPageEditorSidebarPanels(),
				_ffLayoutContentPageEditorConfiguration,
				_fragmentCollectionContributorTracker,
				_fragmentEntryConfigurationParser, _fragmentRendererController,
				_fragmentRendererTracker, _frontendTokenDefinitionRegistry,
				httpServletRequest, _infoItemServiceTracker, _itemSelector,
				_pageEditorConfiguration, portletRequest, renderResponse,
				_stagingGroupHelper);
		}

		long classPK = GetterUtil.getLong(
			httpServletRequest.getAttribute(ContentPageEditorWebKeys.CLASS_PK));

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				classPK);

		boolean pageIsDisplayPage = false;

		if ((layoutPageTemplateEntry != null) &&
			(layoutPageTemplateEntry.getType() ==
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE)) {

			pageIsDisplayPage = true;
		}

		return new ContentPageEditorLayoutPageTemplateDisplayContext(
			_commentManager, _getContentPageEditorSidebarPanels(),
			_ffLayoutContentPageEditorConfiguration,
			_fragmentCollectionContributorTracker,
			_fragmentEntryConfigurationParser, _fragmentRendererController,
			_fragmentRendererTracker, _frontendTokenDefinitionRegistry,
			httpServletRequest, _infoItemServiceTracker, _itemSelector,
			_pageEditorConfiguration, pageIsDisplayPage, portletRequest,
			renderResponse);
	}

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_ffLayoutContentPageEditorConfiguration =
			ConfigurableUtil.createConfigurable(
				FFLayoutContentPageEditorConfiguration.class, properties);
		_pageEditorConfiguration = ConfigurableUtil.createConfigurable(
			PageEditorConfiguration.class, properties);
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, ContentPageEditorSidebarPanel.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private List<ContentPageEditorSidebarPanel>
		_getContentPageEditorSidebarPanels() {

		List<ContentPageEditorSidebarPanel> contentPageEditorSidebarPanels =
			new ArrayList<>(_serviceTrackerList.size());

		for (ContentPageEditorSidebarPanel contentPageEditorSidebarPanel :
				_serviceTrackerList) {

			contentPageEditorSidebarPanels.add(contentPageEditorSidebarPanel);
		}

		return contentPageEditorSidebarPanels;
	}

	@Reference
	private CommentManager _commentManager;

	private volatile FFLayoutContentPageEditorConfiguration
		_ffLayoutContentPageEditorConfiguration;

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

	@Reference
	private FrontendTokenDefinitionRegistry _frontendTokenDefinitionRegistry;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	private volatile PageEditorConfiguration _pageEditorConfiguration;
	private volatile ServiceTrackerList<ContentPageEditorSidebarPanel>
		_serviceTrackerList;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}