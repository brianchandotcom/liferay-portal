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

package com.liferay.asset.publisher.layout.prototype.internal.instance.lifecycle;

import com.liferay.asset.categories.navigation.web.constants.AssetCategoriesNavigationPortletKeys;
import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.asset.tags.navigation.web.constants.AssetTagsNavigationPortletKeys;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.DefaultLayoutPrototypesUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.language.LanguageResources;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the implementation of <code>PortalInstanceLifecycleListener</code>
 * (in the <code>com.liferay.portal.instance.lifecycle</code> module), which
 * adds a default layout prototype for the content display page when a new
 * portal instance is registered.
 *
 * @author Juergen Kappler
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class AddLayoutPrototypePortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		long defaultUserId = _userLocalService.getDefaultUserId(
			company.getCompanyId());

		List<LayoutPrototype> layoutPrototypes =
			_layoutPrototypeLocalService.search(
				company.getCompanyId(), null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		addWebContentPage(
			company.getCompanyId(), defaultUserId, layoutPrototypes);
	}

	protected void addWebContentPage(
			long companyId, long defaultUserId,
			List<LayoutPrototype> layoutPrototypes)
		throws Exception {

		ResourceBundleLoader resourceBundleLoader =
			new AggregateResourceBundleLoader(
				ResourceBundleUtil.getResourceBundleLoader(
					"content.Language", getClassLoader()),
				LanguageResources.RESOURCE_BUNDLE_LOADER);

		Map<Locale, String> nameMap = ResourceBundleUtil.getLocalizationMap(
			resourceBundleLoader, "layout-prototype-web-content-title");
		Map<Locale, String> descriptionMap =
			ResourceBundleUtil.getLocalizationMap(
				resourceBundleLoader,
				"layout-prototype-web-content-description");

		Layout layout = DefaultLayoutPrototypesUtil.addLayoutPrototype(
			companyId, defaultUserId, nameMap, descriptionMap, "2_columns_ii",
			layoutPrototypes);

		if (layout == null) {
			return;
		}

		DefaultLayoutPrototypesUtil.addPortletId(
			layout, AssetTagsNavigationPortletKeys.ASSET_TAGS_NAVIGATION,
			"column-1");

		DefaultLayoutPrototypesUtil.addPortletId(
			layout,
			AssetCategoriesNavigationPortletKeys.ASSET_CATEGORIES_NAVIGATION,
			"column-1");

		String portletId = DefaultLayoutPrototypesUtil.addPortletId(
			layout, AssetPublisherPortletKeys.ASSET_PUBLISHER, "column-2");

		Map<String, String> preferences = new HashMap<>();

		preferences.put("showOnlyLayoutAssets", Boolean.TRUE.toString());

		DefaultLayoutPrototypesUtil.updatePortletSetup(
			layout, portletId, preferences);

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID,
			portletId);

		_layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	@Reference(
		target = "(javax.portlet.name=" + AssetCategoriesNavigationPortletKeys.ASSET_CATEGORIES_NAVIGATION + ")",
		unbind = "-"
	)
	protected void setAssetCategoriesNavigationPortlet(Portlet portlet) {
	}

	@Reference(
		target = "(javax.portlet.name=" + AssetPublisherPortletKeys.ASSET_PUBLISHER + ")",
		unbind = "-"
	)
	protected void setAssetPublisherPortlet(Portlet portlet) {
	}

	@Reference(
		target = "(javax.portlet.name=" + AssetTagsNavigationPortletKeys.ASSET_TAGS_NAVIGATION + ")",
		unbind = "-"
	)
	protected void setAssetTagsNavigationPortlet(Portlet portlet) {
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutPrototypeLocalService(
		LayoutPrototypeLocalService layoutPrototypeLocalService) {

		_layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private LayoutLocalService _layoutLocalService;
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;
	private UserLocalService _userLocalService;

}