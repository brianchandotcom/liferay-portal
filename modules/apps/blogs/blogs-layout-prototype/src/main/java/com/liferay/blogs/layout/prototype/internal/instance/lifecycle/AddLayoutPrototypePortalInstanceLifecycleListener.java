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

package com.liferay.blogs.layout.prototype.internal.instance.lifecycle;

import com.liferay.asset.tags.navigation.constants.AssetTagsNavigationPortletKeys;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.recent.bloggers.constants.RecentBloggersPortletKeys;
import com.liferay.layout.page.template.util.LayoutPrototypeHelperUtil;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.DefaultLayoutPrototypesUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.language.LanguageResources;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
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

		addBlogPage(company.getCompanyId(), defaultUserId, layoutPrototypes);
	}

	protected void addBlogPage(
			long companyId, long defaultUserId,
			List<LayoutPrototype> layoutPrototypes)
		throws Exception {

		ResourceBundleLoader resourceBundleLoader =
			new AggregateResourceBundleLoader(
				ResourceBundleUtil.getResourceBundleLoader(
					"content.Language", getClassLoader()),
				LanguageResources.RESOURCE_BUNDLE_LOADER);

		Map<Locale, String> descriptionMap =
			ResourceBundleUtil.getLocalizationMap(
				resourceBundleLoader, "layout-prototype-blog-description");
		Map<Locale, String> nameMap = ResourceBundleUtil.getLocalizationMap(
			resourceBundleLoader, "layout-prototype-blog-title");

		Layout layout = LayoutPrototypeHelperUtil.addLayoutPrototype(
			_layoutPrototypeLocalService, companyId, defaultUserId, nameMap,
			descriptionMap, "2_columns_iii", layoutPrototypes);

		if (layout == null) {
			return;
		}

		DefaultLayoutPrototypesUtil.addPortletId(
			layout, BlogsPortletKeys.BLOGS, "column-1");

		String portletId = DefaultLayoutPrototypesUtil.addPortletId(
			layout, AssetTagsNavigationPortletKeys.ASSET_TAGS_CLOUD,
			"column-2");

		Map<String, String> preferences = new HashMap<>();

		preferences.put(
			"classNameId",
			String.valueOf(_portal.getClassNameId(BlogsEntry.class)));
		preferences.put("showAssetCount", Boolean.TRUE.toString());

		DefaultLayoutPrototypesUtil.updatePortletSetup(
			layout, portletId, preferences);

		DefaultLayoutPrototypesUtil.addPortletId(
			layout, RecentBloggersPortletKeys.RECENT_BLOGGERS, "column-2");
	}

	@Reference(
		target = "(javax.portlet.name=" + AssetTagsNavigationPortletKeys.ASSET_TAGS_CLOUD + ")"
	)
	private Portlet _assetTagsCloudPortlet;

	@Reference(target = "(javax.portlet.name=" + BlogsPortletKeys.BLOGS + ")")
	private Portlet _blogsPortlet;

	@Reference
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(javax.portlet.name=" + RecentBloggersPortletKeys.RECENT_BLOGGERS + ")"
	)
	private Portlet _recentBloggersPortlet;

	@Reference
	private UserLocalService _userLocalService;

}