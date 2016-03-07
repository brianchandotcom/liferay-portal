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

package com.liferay.social.activities.web.portlet.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.social.activities.web.portlet.display.context.util.SocialActivitiesRequestHelper;
import com.liferay.social.activities.web.util.SocialActivityQueryHelper;
import com.liferay.social.kernel.model.SocialActivitySet;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;

/**
 * @author Adolfo Pérez
 */
public class DefaultSocialActivitiesDisplayContext
	implements SocialActivitiesDisplayContext {

	public DefaultSocialActivitiesDisplayContext(
		SocialActivitiesRequestHelper socialActivitiesRequestHelper,
		SocialActivityQueryHelper socialActivityQueryHelper) {

		_socialActivitiesRequestHelper = socialActivitiesRequestHelper;
		_socialActivityQueryHelper = socialActivityQueryHelper;
	}

	@Override
	public int getMax() {
		return _socialActivitiesRequestHelper.getMax();
	}

	@Override
	public int getRSSDelta() {
		return _socialActivitiesRequestHelper.getRSSDelta();
	}

	@Override
	public String getRSSDisplayStyle() {
		return _socialActivitiesRequestHelper.getRSSDisplayStyle();
	}

	@Override
	public String getRSSFeedType() {
		return _socialActivitiesRequestHelper.getRSSFeedType();
	}

	@Override
	public ResourceURL getRSSResourceURL() throws PortalException {
		Group group = _socialActivitiesRequestHelper.getScopeGroup();

		String groupDescriptiveName = HtmlUtil.escape(
			group.getDescriptiveName(
				_socialActivitiesRequestHelper.getLocale()));

		String feedTitle = LanguageUtil.format(
			getResourceBundle(), "x's-activities", groupDescriptiveName, false);

		LiferayPortletResponse liferayPortletResponse =
			_socialActivitiesRequestHelper.getLiferayPortletResponse();

		ResourceURL rssURL = liferayPortletResponse.createResourceURL();

		rssURL.setParameter("feedTitle", feedTitle);
		rssURL.setResourceID("rss");

		return rssURL;
	}

	@Override
	public String getSelectedTabName() {
		return _socialActivitiesRequestHelper.getTabs1();
	}

	@Override
	public List<SocialActivitySet> getSocialActivitySets() {
		Group group = _socialActivitiesRequestHelper.getScopeGroup();
		Layout layout = _socialActivitiesRequestHelper.getLayout();

		SocialActivityQueryHelper.Scope scope =
			SocialActivityQueryHelper.Scope.fromValue(getSelectedTabName());

		return _socialActivityQueryHelper.getSocialActivitySets(
			group, layout, scope, 0, _socialActivitiesRequestHelper.getMax());
	}

	@Override
	public String getTabsNames() {
		return "all,connections,following,my-sites,me";
	}

	@Override
	public String getTabsURL() {
		LiferayPortletResponse liferayPortletResponse =
			_socialActivitiesRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("tabs1", getSelectedTabName());

		return portletURL.toString();
	}

	@Override
	public String getTaglibFeedTitle() throws PortalException {
		Group group = _socialActivitiesRequestHelper.getScopeGroup();
		Locale locale = _socialActivitiesRequestHelper.getLocale();

		String groupDescriptiveName = HtmlUtil.escape(
			group.getDescriptiveName(locale));

		return LanguageUtil.format(
			getResourceBundle(), "subscribe-to-x's-activities",
			groupDescriptiveName, false);
	}

	@Override
	public boolean isRSSEnabled() {
		return _socialActivitiesRequestHelper.isRSSEnabled();
	}

	@Override
	public boolean isTabsVisible() {
		Group group = _socialActivitiesRequestHelper.getScopeGroup();

		Layout layout = _socialActivitiesRequestHelper.getLayout();

		if (group.isUser() && layout.isPrivateLayout()) {
			return true;
		}

		return false;
	}

	protected ResourceBundle getResourceBundle() {
		if (_resourceBundle != null) {
			return _resourceBundle;
		}

		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleLoaderUtil.
				getResourceBundleLoaderByBundleSymbolicName(
					"com.liferay.social.activities.web");

		resourceBundleLoader = new AggregateResourceBundleLoader(
			resourceBundleLoader, LanguageUtil.getPortalResourceBundleLoader());

		_resourceBundle = resourceBundleLoader.loadResourceBundle(
			LanguageUtil.getLanguageId(
				_socialActivitiesRequestHelper.getLocale()));

		return _resourceBundle;
	}

	private ResourceBundle _resourceBundle;
	private final SocialActivitiesRequestHelper _socialActivitiesRequestHelper;
	private final SocialActivityQueryHelper _socialActivityQueryHelper;

}