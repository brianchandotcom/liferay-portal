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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.layoutprototypes.LayoutConfigurator;
import com.liferay.portlet.layoutprototypes.LayoutPrototypeCreator;
import com.liferay.portlet.wiki.model.WikiPage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergio González
 * @author Juan Fernández
 * @author Iván Zaera
 */
public class AddDefaultLayoutPrototypesAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		try {
			doRun(GetterUtil.getLong(ids[0]));
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void addBlogPage(LayoutPrototypeCreator layoutPrototypeCreator)
		throws Exception {

		LayoutPrototype layoutPrototype =
			layoutPrototypeCreator.addLayoutPrototype(
					"layout-prototype-blog-title",
					"layout-prototype-blog-description", "2_columns_iii");

		if (layoutPrototype == null) {
			return;
		}

		Layout layout = layoutPrototype.getLayout();

		LayoutConfigurator layoutConfigurator = new LayoutConfigurator(layout);

		layoutConfigurator.addPortlet("column-1", PortletKeys.BLOGS);

		layoutConfigurator.addPortlet(
			"column-2", PortletKeys.TAGS_CLOUD, _getTagsCloudPortletSetup());

		layoutConfigurator.addPortlet("column-2", PortletKeys.RECENT_BLOGGERS);
	}

	protected void addWebContentPage(
			LayoutPrototypeCreator layoutPrototypeCreator)
		throws Exception {

		LayoutPrototype layoutPrototype =
			layoutPrototypeCreator.addLayoutPrototype(
					"layout-prototype-web-content-title",
					"layout-prototype-web-content-description", "2_columns_ii");

		if (layoutPrototype == null) {
			return;
		}

		Layout layout = layoutPrototype.getLayout();

		LayoutConfigurator layoutConfigurator = new LayoutConfigurator(layout);

		layoutConfigurator.addPortlet(
			"column-1", PortletKeys.ASSET_TAGS_NAVIGATION);

		layoutConfigurator.addPortlet(
			"column-1", PortletKeys.ASSET_CATEGORIES_NAVIGATION);

		layoutConfigurator.addPortlet("column-2", PortletKeys.SEARCH);

		String assetPublisherPortletId = layoutConfigurator.addPortlet(
			"column-2", PortletKeys.ASSET_PUBLISHER);

		layoutConfigurator.setTypeSettingsProperty(
			LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID,
			assetPublisherPortletId);
	}

	protected void addWikiPage(LayoutPrototypeCreator layoutPrototypeCreator)
		throws Exception {

		LayoutPrototype layoutPrototype =
			layoutPrototypeCreator.addLayoutPrototype(
				"layout-prototype-wiki-title",
				"layout-prototype-wiki-description", "2_columns_iii");

		if (layoutPrototype == null) {
			return;
		}

		Layout layout = layoutPrototype.getLayout();

		LayoutConfigurator layoutConfigurator = new LayoutConfigurator(layout);

		layoutConfigurator.addPortlet("column-1", PortletKeys.WIKI);

		layoutConfigurator.addPortlet(
			"column-2", PortletKeys.ASSET_CATEGORIES_NAVIGATION);

		layoutConfigurator.addPortlet(
			"column-2", PortletKeys.ASSET_TAGS_NAVIGATION,
			_getAssetTagsNavigationPortletSetup());
	}

	protected void doRun(long companyId) throws Exception {
		LayoutPrototypeCreator layoutPrototypeCreator =
			new LayoutPrototypeCreator(companyId);

		addBlogPage(layoutPrototypeCreator);
		addWebContentPage(layoutPrototypeCreator);
		addWikiPage(layoutPrototypeCreator);
	}

	private Map<String, String> _getAssetTagsNavigationPortletSetup() {
		Map<String, String> preferences = new HashMap<String, String>();

		preferences.put(
			"classNameId",
			String.valueOf(PortalUtil.getClassNameId(WikiPage.class)));

		preferences.put("showAssetCount", Boolean.TRUE.toString());

		return preferences;
	}

	private Map<String, String> _getTagsCloudPortletSetup() {
		Map<String, String> preferences = new HashMap<String, String>();

		preferences.put(
			"classNameId",
			String.valueOf(PortalUtil.getClassNameId(BlogsEntry.class)));

		preferences.put("showAssetCount", Boolean.TRUE.toString());

		return preferences;
	}

}