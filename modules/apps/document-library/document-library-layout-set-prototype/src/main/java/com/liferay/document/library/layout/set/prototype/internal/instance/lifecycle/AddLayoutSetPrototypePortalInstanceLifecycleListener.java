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

package com.liferay.document.library.layout.set.prototype.internal.instance.lifecycle;

import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.DefaultLayoutPrototypesUtil;
import com.liferay.portal.kernel.util.DefaultLayoutSetPrototypesUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.rss.constants.RSSPortletKeys;
import com.liferay.site.navigation.language.constants.SiteNavigationLanguagePortletKeys;
import com.liferay.social.activities.constants.SocialActivitiesPortletKeys;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = PortalInstanceLifecycleListener.class)
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

		addPrivateSite(
			company.getCompanyId(), defaultUserId, layoutSetPrototypes);
	}

	protected void addPrivateSite(
			long companyId, long defaultUserId,
			List<LayoutSetPrototype> layoutSetPrototypes)
		throws Exception {

		LayoutSet layoutSet =
			DefaultLayoutSetPrototypesUtil.addLayoutSetPrototype(
				companyId, defaultUserId,
				"layout-set-prototype-intranet-site-title",
				"layout-set-prototype-intranet-site-description",
				layoutSetPrototypes, getClassLoader());

		if (layoutSet == null) {
			return;
		}

		// Home layout

		Layout homeLayout = DefaultLayoutPrototypesUtil.addLayout(
			layoutSet, "home", "/home", "2_columns_i");

		DefaultLayoutPrototypesUtil.addPortletId(
			homeLayout, SocialActivitiesPortletKeys.SOCIAL_ACTIVITIES,
			"column-1");

		DefaultLayoutPrototypesUtil.addPortletId(
			homeLayout,
			SiteNavigationLanguagePortletKeys.SITE_NAVIGATION_LANGUAGE,
			"column-2");

		String portletId = DefaultLayoutPrototypesUtil.addPortletId(
			homeLayout, AssetPublisherPortletKeys.ASSET_PUBLISHER, "column-2");

		Map<String, String> preferences = HashMapBuilder.put(
			"portletSetupTitle_" + LocaleUtil.getDefault(), "Recent Content"
		).put(
			"portletSetupUseCustomTitle", Boolean.TRUE.toString()
		).build();

		DefaultLayoutPrototypesUtil.updatePortletSetup(
			homeLayout, portletId, preferences);

		// Documents layout

		Layout documentsLayout = DefaultLayoutPrototypesUtil.addLayout(
			layoutSet, "documents-and-media", "/documents", "1_column");

		portletId = DefaultLayoutPrototypesUtil.addPortletId(
			documentsLayout, DLPortletKeys.DOCUMENT_LIBRARY, "column-1");

		preferences = HashMapBuilder.put(
			"portletSetupPortletDecoratorId", "borderless"
		).build();

		DefaultLayoutPrototypesUtil.updatePortletSetup(
			documentsLayout, portletId, preferences);

		// News layout

		Layout newsLayout = DefaultLayoutPrototypesUtil.addLayout(
			layoutSet, "News", "/news", "2_columns_iii");

		portletId = DefaultLayoutPrototypesUtil.addPortletId(
			newsLayout, RSSPortletKeys.RSS, "column-1");

		preferences = HashMapBuilder.put(
			"expandedEntriesPerFeed", "3"
		).put(
			"portletSetupTitle_" + LocaleUtil.getDefault(), "Technology news"
		).put(
			"portletSetupUseCustomTitle", Boolean.TRUE.toString()
		).put(
			"urls",
			"http://www.nytimes.com/services/xml/rss/userland/Technology.xml"
		).build();

		DefaultLayoutPrototypesUtil.updatePortletSetup(
			newsLayout, portletId, preferences);

		portletId = DefaultLayoutPrototypesUtil.addPortletId(
			newsLayout, RSSPortletKeys.RSS, "column-2");

		preferences = HashMapBuilder.put(
			"expandedEntriesPerFeed", "0"
		).put(
			"portletSetupTitle_" + LocaleUtil.getDefault(), "Liferay news"
		).put(
			"portletSetupUseCustomTitle", Boolean.TRUE.toString()
		).put(
			"titles", "Liferay Press Releases"
		).put(
			"urls",
			"https://www.liferay.com/about-us/newsroom/press-releases/-" +
				"/asset_publisher/2oZC/rss"
		).build();

		DefaultLayoutPrototypesUtil.updatePortletSetup(
			newsLayout, portletId, preferences);
	}

	@Reference(
		target = "(javax.portlet.name=" + AssetPublisherPortletKeys.ASSET_PUBLISHER + ")",
		unbind = "-"
	)
	protected void setAssetPublisherPortlet(Portlet portlet) {
	}

	@Reference(
		target = "(javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY + ")",
		unbind = "-"
	)
	protected void setDLPortlet(Portlet portlet) {
	}

	@Reference(unbind = "-")
	protected void setLayoutSetPrototypeLocalService(
		LayoutSetPrototypeLocalService layoutSetPrototypeLocalService) {

		_layoutSetPrototypeLocalService = layoutSetPrototypeLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(
		target = "(javax.portlet.name=" + RSSPortletKeys.RSS + ")", unbind = "-"
	)
	protected void setRSSPortlet(Portlet portlet) {
	}

	@Reference(
		target = "(javax.portlet.name=" + SiteNavigationLanguagePortletKeys.SITE_NAVIGATION_LANGUAGE + ")",
		unbind = "-"
	)
	protected void setSiteNavigationLanguagePortlet(Portlet portlet) {
	}

	@Reference(
		target = "(javax.portlet.name=" + SocialActivitiesPortletKeys.SOCIAL_ACTIVITIES + ")",
		unbind = "-"
	)
	protected void setSocialActivitiesPortlet(Portlet portlet) {
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;
	private UserLocalService _userLocalService;

}