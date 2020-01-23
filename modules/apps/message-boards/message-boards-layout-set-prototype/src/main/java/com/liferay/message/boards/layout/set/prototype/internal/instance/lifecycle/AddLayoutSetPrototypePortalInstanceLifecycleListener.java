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

package com.liferay.message.boards.layout.set.prototype.internal.instance.lifecycle;

import com.liferay.asset.categories.navigation.constants.AssetCategoriesNavigationPortletKeys;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.tags.navigation.constants.AssetTagsNavigationPortletKeys;
import com.liferay.layout.set.prototype.constants.LayoutSetPrototypePortletKeys;
import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.polls.constants.PollsPortletKeys;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.DefaultLayoutPrototypesUtil;
import com.liferay.portal.kernel.util.DefaultLayoutSetPrototypesUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.wiki.constants.WikiPortletKeys;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class AddLayoutSetPrototypePortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		long defaultUserId = _userLocalService.getDefaultUserId(
			company.getCompanyId());

		List<LayoutSetPrototype> layoutSetPrototypes =
			_layoutSetPrototypeLocalService.search(
				company.getCompanyId(), null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		addPublicSite(
			company.getCompanyId(), defaultUserId, layoutSetPrototypes);
	}

	protected void addPublicSite(
			long companyId, long defaultUserId,
			List<LayoutSetPrototype> layoutSetPrototypes)
		throws Exception {

		LayoutSet layoutSet =
			DefaultLayoutSetPrototypesUtil.addLayoutSetPrototype(
				companyId, defaultUserId,
				"layout-set-prototype-community-site-title",
				"layout-set-prototype-community-site-description",
				layoutSetPrototypes, getClassLoader());

		if (layoutSet == null) {
			return;
		}

		// Home layout

		Layout homeLayout = DefaultLayoutPrototypesUtil.addLayout(
			layoutSet, "home", "/home", "2_columns_iii");
		String portletId = PortletProviderUtil.getPortletId(
			MBMessage.class.getName(), PortletProvider.Action.EDIT);

		DefaultLayoutPrototypesUtil.addPortletId(
			homeLayout, portletId, "column-1");

		DefaultLayoutPrototypesUtil.addPortletId(
			homeLayout, PollsPortletKeys.POLLS_DISPLAY, "column-2");

		portletId = DefaultLayoutPrototypesUtil.addPortletId(
			homeLayout, AssetPublisherPortletKeys.ASSET_PUBLISHER, "column-2");

		Map<String, String> preferences = HashMapBuilder.put(
			"anyAssetType", Boolean.FALSE.toString()
		).put(
			"portletSetupTitle_" + LocaleUtil.getDefault(), "Recent Content"
		).put(
			"portletSetupUseCustomTitle", Boolean.TRUE.toString()
		).build();

		DefaultLayoutPrototypesUtil.updatePortletSetup(
			homeLayout, portletId, preferences);

		// Wiki layout

		Layout wikiLayout = DefaultLayoutPrototypesUtil.addLayout(
			layoutSet, "wiki", "/wiki", "2_columns_iii");

		DefaultLayoutPrototypesUtil.addPortletId(
			wikiLayout, WikiPortletKeys.WIKI, "column-1");

		DefaultLayoutPrototypesUtil.addPortletId(
			wikiLayout,
			AssetCategoriesNavigationPortletKeys.ASSET_CATEGORIES_NAVIGATION,
			"column-2");

		DefaultLayoutPrototypesUtil.addPortletId(
			wikiLayout, AssetTagsNavigationPortletKeys.ASSET_TAGS_CLOUD,
			"column-2");
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
		target = "(javax.portlet.name=" + AssetTagsNavigationPortletKeys.ASSET_TAGS_CLOUD + ")",
		unbind = "-"
	)
	protected void setAssetTagsNavigationPortlet(Portlet portlet) {
	}

	@Reference(unbind = "-")
	protected void setLayoutSetPrototypeLocalService(
		LayoutSetPrototypeLocalService layoutSetPrototypeLocalService) {

		_layoutSetPrototypeLocalService = layoutSetPrototypeLocalService;
	}

	@Reference(
		target = "(javax.portlet.name=" + LayoutSetPrototypePortletKeys.LAYOUT_SET_PROTOTYPE + ")",
		unbind = "-"
	)
	protected void setLayoutSetPrototypePortlet(Portlet portlet) {
	}

	@Reference(
		target = "(javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS + ")",
		unbind = "-"
	)
	protected void setMessageBoardsPortlet(Portlet portlet) {
	}

	@Reference(
		target = "(javax.portlet.name=" + PollsPortletKeys.POLLS_DISPLAY + ")",
		unbind = "-"
	)
	protected void setPollsPortlet(Portlet portlet) {
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Reference(
		target = "(javax.portlet.name=" + WikiPortletKeys.WIKI + ")",
		unbind = "-"
	)
	protected void setWikiPortlet(Portlet portlet) {
	}

	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	@Reference(
		target = "(&(original.bean=true)(bean.id=javax.servlet.ServletContext))"
	)
	private ServletContext _servletContext;

	private UserLocalService _userLocalService;

}