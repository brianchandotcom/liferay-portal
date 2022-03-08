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

package com.liferay.content.dashboard.web.internal.portlet;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardPortletKeys;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardWebKeys;
import com.liferay.content.dashboard.web.internal.dao.search.ContentDashboardItemSearchContainerFactory;
import com.liferay.content.dashboard.web.internal.data.provider.ContentDashboardDataProvider;
import com.liferay.content.dashboard.web.internal.display.context.ContentDashboardAdminDisplayContext;
import com.liferay.content.dashboard.web.internal.display.context.ContentDashboardAdminManagementToolbarDisplayContext;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryTracker;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtypeFactoryTracker;
import com.liferay.content.dashboard.web.internal.provider.AssetVocabulariesProvider;
import com.liferay.content.dashboard.web.internal.search.request.ContentDashboardItemSearchClassMapperTracker;
import com.liferay.content.dashboard.web.internal.search.request.ContentDashboardSearchContextBuilder;
import com.liferay.content.dashboard.web.internal.searcher.ContentDashboardSearchRequestBuilderFactory;
import com.liferay.content.dashboard.web.internal.servlet.taglib.util.ContentDashboardDropdownItemsProvider;
import com.liferay.content.dashboard.web.internal.util.ContentDashboardUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.constants.LanguageConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.Searcher;

import java.io.IOException;

import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-content-dashboard-admin",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.preferences-company-wide=false",
		"com.liferay.portlet.preferences-owned-by-group=false",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Content Dashboard",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ContentDashboardPortletKeys.CONTENT_DASHBOARD_ADMIN,
		"javax.portlet.portlet-mode=text/html",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class ContentDashboardAdminPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_portal.getLocale(renderRequest), getClass());

		ContentDashboardDataProvider contentDashboardDataProvider =
			new ContentDashboardDataProvider(
				_aggregations,
				new ContentDashboardSearchContextBuilder(
					_portal.getHttpServletRequest(renderRequest),
					_assetCategoryLocalService, _assetVocabularyLocalService),
				_contentDashboardSearchRequestBuilderFactory,
				_portal.getLocale(renderRequest), _queries, resourceBundle,
				_searcher);

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(renderRequest);
		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(renderResponse);

		ContentDashboardItemSearchContainerFactory
			contentDashboardItemSearchContainerFactory =
				ContentDashboardItemSearchContainerFactory.getInstance(
					_assetCategoryLocalService, _assetVocabularyLocalService,
					_contentDashboardItemFactoryTracker,
					_contentDashboardItemSearchClassMapperTracker,
					_contentDashboardSearchRequestBuilderFactory, _portal,
					renderRequest, renderResponse, _searcher);

		SearchContainer<ContentDashboardItem<?>> searchContainer =
			contentDashboardItemSearchContainerFactory.create();

		List<AssetVocabulary> assetVocabularies =
			_assetVocabulariesProvider.getAssetVocabularies(
				ContentDashboardUtil.getAssetVocabularyIds(renderRequest));

		ContentDashboardAdminDisplayContext
			contentDashboardAdminDisplayContext =
				new ContentDashboardAdminDisplayContext(
					assetVocabularies,
					contentDashboardDataProvider.getAssetVocabularyMetric(
						assetVocabularies),
					new ContentDashboardDropdownItemsProvider(
						_http, _language, liferayPortletRequest,
						liferayPortletResponse, _portal),
					_contentDashboardItemSubtypeFactoryTracker, _itemSelector,
					_language.get(
						_portal.getLocale(liferayPortletRequest),
						LanguageConstants.KEY_DIR),
					liferayPortletRequest, liferayPortletResponse, _portal,
					resourceBundle, searchContainer);

		renderRequest.setAttribute(
			ContentDashboardWebKeys.CONTENT_DASHBOARD_ADMIN_DISPLAY_CONTEXT,
			contentDashboardAdminDisplayContext);

		ContentDashboardAdminManagementToolbarDisplayContext
			contentDashboardAdminManagementToolbarDisplayContext =
				new ContentDashboardAdminManagementToolbarDisplayContext(
					_assetCategoryLocalService, _assetVocabularyLocalService,
					contentDashboardAdminDisplayContext, _groupLocalService,
					_portal.getHttpServletRequest(renderRequest),
					liferayPortletRequest, liferayPortletResponse,
					_portal.getLocale(renderRequest), _userLocalService);

		renderRequest.setAttribute(
			ContentDashboardWebKeys.
				CONTENT_DASHBOARD_ADMIN_MANAGEMENT_TOOLBAR_DISPLAY_CONTEXT,
			contentDashboardAdminManagementToolbarDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Reference
	private Aggregations _aggregations;

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabulariesProvider _assetVocabulariesProvider;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private ContentDashboardItemFactoryTracker
		_contentDashboardItemFactoryTracker;

	@Reference
	private ContentDashboardItemSearchClassMapperTracker
		_contentDashboardItemSearchClassMapperTracker;

	@Reference
	private ContentDashboardItemSubtypeFactoryTracker
		_contentDashboardItemSubtypeFactoryTracker;

	@Reference
	private ContentDashboardSearchRequestBuilderFactory
		_contentDashboardSearchRequestBuilderFactory;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private Queries _queries;

	@Reference
	private Searcher _searcher;

	@Reference
	private UserLocalService _userLocalService;

}