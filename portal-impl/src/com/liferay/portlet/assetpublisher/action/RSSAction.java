/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.assetpublisher.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.service.AssetEntryServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.feed.synd.SyndLinkImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class RSSAction extends com.liferay.portal.struts.RSSAction {

	protected String exportToRSS(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String name, String description, String format, double version,
			String displayStyle, String linkBehavior,
			List<AssetEntry> assetEntries)
		throws Exception {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setDescription(GetterUtil.getString(description, name));

		List<SyndEntry> syndEntries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(syndEntries);

		for (AssetEntry assetEntry : assetEntries) {
			SyndEntry syndEntry = new SyndEntryImpl();

			String author = PortalUtil.getUserName(assetEntry);

			syndEntry.setAuthor(author);

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.ENTRY_TYPE_DEFAULT);

			String value = null;

			String languageId = LanguageUtil.getLanguageId(portletRequest);

			if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
				value = StringPool.BLANK;
			}
			else {
				value = assetEntry.getSummary(languageId, true);
			}

			syndContent.setValue(value);

			syndEntry.setDescription(syndContent);

			String link = getEntryURL(
				portletRequest, portletResponse, linkBehavior, assetEntry);

			syndEntry.setLink(link);

			syndEntry.setPublishedDate(assetEntry.getCreateDate());
			syndEntry.setTitle(assetEntry.getTitle(languageId, true));
			syndEntry.setUpdatedDate(assetEntry.getModifiedDate());
			syndEntry.setUri(syndEntry.getLink());

			syndEntries.add(syndEntry);
		}

		syndFeed.setFeedType(RSSUtil.getFeedType(format, version));

		List<SyndLink> syndLinks = new ArrayList<SyndLink>();

		syndFeed.setLinks(syndLinks);

		SyndLink selfSyndLink = new SyndLinkImpl();

		syndLinks.add(selfSyndLink);

		String feedURL = getFeedURL(portletRequest);

		selfSyndLink.setHref(feedURL);

		selfSyndLink.setRel("self");

		syndFeed.setPublishedDate(new Date());
		syndFeed.setTitle(name);
		syndFeed.setUri(feedURL);

		return RSSUtil.export(syndFeed);
	}

	protected List<AssetEntry> getAssetEntries(
			PortletRequest portletRequest, PortletPreferences preferences)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetEntryQuery assetEntryQuery = AssetPublisherUtil.getAssetEntryQuery(
			preferences, new long[] {themeDisplay.getScopeGroupId()});

		boolean anyAssetType = GetterUtil.getBoolean(
			preferences.getValue("anyAssetType", null), true);

		if (!anyAssetType) {
			long[] availableClassNameIds =
				AssetRendererFactoryRegistryUtil.getClassNameIds();

			long[] classNameIds = AssetPublisherUtil.getClassNameIds(
				preferences, availableClassNameIds);

			assetEntryQuery.setClassNameIds(classNameIds);
		}

		long[] classTypeIds = GetterUtil.getLongValues(
			preferences.getValues("classTypeIds", null));

		assetEntryQuery.setClassTypeIds(classTypeIds);

		boolean enablePermissions = GetterUtil.getBoolean(
			preferences.getValue("enablePermissions", null));

		assetEntryQuery.setEnablePermissions(enablePermissions);

		int rssDelta = GetterUtil.getInteger(
			preferences.getValue("rssDelta", "20"));

		assetEntryQuery.setEnd(rssDelta);

		boolean excludeZeroViewCount = GetterUtil.getBoolean(
			preferences.getValue("excludeZeroViewCount", null));

		assetEntryQuery.setExcludeZeroViewCount(excludeZeroViewCount);

		long[] groupIds = AssetPublisherUtil.getGroupIds(
			preferences, themeDisplay.getScopeGroupId(),
			themeDisplay.getLayout());

		assetEntryQuery.setGroupIds(groupIds);

		boolean showOnlyLayoutAssets = GetterUtil.getBoolean(
			preferences.getValue("showOnlyLayoutAssets", null));

		if (showOnlyLayoutAssets) {
			assetEntryQuery.setLayout(themeDisplay.getLayout());
		}

		String orderByColumn1 = GetterUtil.getString(
			preferences.getValue("orderByColumn1", "modifiedDate"));

		assetEntryQuery.setOrderByCol1(orderByColumn1);

		String orderByColumn2 = GetterUtil.getString(
			preferences.getValue("orderByColumn2", "title"));

		assetEntryQuery.setOrderByCol2(orderByColumn2);

		String orderByType1 = GetterUtil.getString(
			preferences.getValue("orderByType1", "DESC"));

		assetEntryQuery.setOrderByType1(orderByType1);

		String orderByType2 = GetterUtil.getString(
			preferences.getValue("orderByType2", "ASC"));

		assetEntryQuery.setOrderByType2(orderByType2);

		assetEntryQuery.setStart(0);

		return AssetEntryServiceUtil.getEntries(assetEntryQuery);
	}

	protected String getAssetPublisherURL(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		StringBundler sb = new StringBundler(7);

		String layoutFriendlyURL = GetterUtil.getString(
			PortalUtil.getLayoutFriendlyURL(layout, themeDisplay));

		if (!layoutFriendlyURL.startsWith(Http.HTTP_WITH_SLASH) &&
			!layoutFriendlyURL.startsWith(Http.HTTPS_WITH_SLASH)) {

			sb.append(themeDisplay.getPortalURL());
		}

		sb.append(layoutFriendlyURL);
		sb.append(Portal.FRIENDLY_URL_SEPARATOR);
		sb.append("asset_publisher/");
		sb.append(portletDisplay.getInstanceId());
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected String getEntryURL(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String linkBehavior, AssetEntry assetEntry)
		throws Exception {

		if (linkBehavior.equals("viewInPortlet")) {
			return getEntryURLViewInContext(
				portletRequest, portletResponse, assetEntry);
		}
		else {
			return getEntryURLAssetPublisher(
				portletRequest, portletResponse, assetEntry);
		}
	}

	protected String getEntryURLAssetPublisher(
			PortletRequest portletRequest, PortletResponse portletResponse,
			AssetEntry assetEntry)
		throws Exception {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetEntry.getClassName());

		StringBundler sb = new StringBundler(4);

		sb.append(getAssetPublisherURL(portletRequest));
		sb.append(assetRendererFactory.getType());
		sb.append("/id/");
		sb.append(assetEntry.getEntryId());

		return sb.toString();
	}

	protected String getEntryURLViewInContext(
			PortletRequest portletRequest, PortletResponse portletResponse,
			AssetEntry assetEntry)
		throws Exception {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetEntry.getClassName());

		AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
			assetEntry.getClassPK());

		String viewInContextURL = assetRenderer.getURLViewInContext(
			(LiferayPortletRequest)portletRequest,
			(LiferayPortletResponse)portletResponse, null);

		if (Validator.isNotNull(viewInContextURL) &&
			!viewInContextURL.startsWith(Http.HTTP_WITH_SLASH) &&
			!viewInContextURL.startsWith(Http.HTTPS_WITH_SLASH)) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			viewInContextURL = themeDisplay.getPortalURL() + viewInContextURL;
		}

		return viewInContextURL;
	}

	protected String getFeedURL(PortletRequest portletRequest)
		throws Exception {

		String feedURL = getAssetPublisherURL(portletRequest);

		return feedURL.concat("rss");
	}

	@Override
	protected byte[] getRSS(
			ResourceRequest portletRequest, ResourceResponse portletResponse)
		throws Exception {

		PortletPreferences preferences = portletRequest.getPreferences();

		String selectionStyle = preferences.getValue(
			"selectionStyle", "dynamic");

		if (!selectionStyle.equals("dynamic")) {
			return new byte[0];
		}

		String assetLinkBehavior = preferences.getValue(
			"assetLinkBehavior", "showFullContent");
		String rssDisplayStyle = preferences.getValue(
			"rssDisplayStyle", RSSUtil.DISPLAY_STYLE_ABSTRACT);
		String rssFeedType = preferences.getValue(
			"rssFeedType", RSSUtil.FEED_TYPE_DEFAULT);
		String rssName = preferences.getValue("rssName", null);

		String format = RSSUtil.getFeedTypeFormat(rssFeedType);
		double version = RSSUtil.getFeedTypeVersion(rssFeedType);

		String rss = exportToRSS(
			portletRequest, portletResponse, rssName, null, format, version,
			rssDisplayStyle, assetLinkBehavior,
			getAssetEntries(portletRequest, preferences));

		return rss.getBytes(StringPool.UTF8);
	}

}