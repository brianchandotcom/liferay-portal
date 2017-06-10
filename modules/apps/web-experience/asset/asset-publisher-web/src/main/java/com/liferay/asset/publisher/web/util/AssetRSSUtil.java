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

package com.liferay.asset.publisher.web.util;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.publisher.web.constants.AssetPublisherWebKeys;
import com.liferay.asset.publisher.web.display.context.AssetEntryResult;
import com.liferay.asset.publisher.web.display.context.AssetPublisherDisplayContext;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.rss.util.RSSUtil;

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
public class AssetRSSUtil {

	public static byte[] getRSS(
			ResourceRequest portletRequest, ResourceResponse portletResponse)
		throws Exception {

		PortletPreferences portletPreferences = portletRequest.getPreferences();

		String selectionStyle = portletPreferences.getValue(
			"selectionStyle", "dynamic");

		if (!selectionStyle.equals("dynamic")) {
			return new byte[0];
		}

		String assetLinkBehavior = portletPreferences.getValue(
			"assetLinkBehavior", "showFullContent");
		String rssDisplayStyle = portletPreferences.getValue(
			"rssDisplayStyle", RSSUtil.DISPLAY_STYLE_ABSTRACT);
		String rssFeedType = portletPreferences.getValue(
			"rssFeedType", RSSUtil.FEED_TYPE_DEFAULT);
		String rssName = portletPreferences.getValue("rssName", null);

		String format = RSSUtil.getFeedTypeFormat(rssFeedType);
		double version = RSSUtil.getFeedTypeVersion(rssFeedType);

		String rss = exportToRSS(
			portletRequest, portletResponse, rssName, null, format, version,
			rssDisplayStyle, assetLinkBehavior,
			getAssetEntries(
				portletRequest, portletResponse, portletPreferences));

		return rss.getBytes(StringPool.UTF8);
	}

	protected static String exportToRSS(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String name, String description, String format, double version,
			String displayStyle, String linkBehavior,
			List<AssetEntry> assetEntries)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setDescription(GetterUtil.getString(description, name));

		List<SyndEntry> syndEntries = new ArrayList<>();

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

			syndEntry.setPublishedDate(assetEntry.getPublishDate());
			syndEntry.setTitle(assetEntry.getTitle(languageId, true));
			syndEntry.setUpdatedDate(assetEntry.getModifiedDate());
			syndEntry.setUri(syndEntry.getLink());

			syndEntries.add(syndEntry);
		}

		syndFeed.setFeedType(RSSUtil.getFeedType(format, version));

		List<SyndLink> syndLinks = new ArrayList<>();

		syndFeed.setLinks(syndLinks);

		SyndLink selfSyndLink = new SyndLinkImpl();

		syndLinks.add(selfSyndLink);

		String feedURL = getFeedURL(portletRequest);

		selfSyndLink.setHref(feedURL);

		selfSyndLink.setRel("self");

		SyndLink alternateSyndLink = new SyndLinkImpl();

		syndLinks.add(alternateSyndLink);

		alternateSyndLink.setHref(PortalUtil.getLayoutFullURL(themeDisplay));
		alternateSyndLink.setRel("alternate");

		syndFeed.setPublishedDate(new Date());
		syndFeed.setTitle(name);
		syndFeed.setUri(feedURL);

		return RSSUtil.export(syndFeed);
	}

	/**
	 * @deprecated As of 2.0.0, replaced by {@link
	 *             #getAssetEntries(PortletRequest, PortletResponse,
	 *             PortletPreferences)}
	 */
	@Deprecated
	protected static List<AssetEntry> getAssetEntries(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method is deprecated and replaced by #getAssetEntries(" +
				"PortletRequest, PortletResponse, PortletPreferences)");
	}

	protected static List<AssetEntry> getAssetEntries(
			PortletRequest portletRequest, PortletResponse portletResponse,
			PortletPreferences portletPreferences)
		throws Exception {

		List<AssetEntry> assetEntries = new ArrayList<>();

		SearchContainer searchContainer = new SearchContainer();

		AssetPublisherCustomizer assetPublisherCustomizer =
			(AssetPublisherCustomizer)portletRequest.getAttribute(
				AssetPublisherWebKeys.ASSET_PUBLISHER_CUSTOMIZER);

		AssetPublisherDisplayContext assetPublisherDisplayContext =
			new AssetPublisherDisplayContext(
				assetPublisherCustomizer, portletRequest, portletResponse,
				portletPreferences);

		searchContainer.setDelta(assetPublisherDisplayContext.getRSSDelta());

		List<AssetEntryResult> assetEntryResults =
			AssetPublisherUtil.getAssetEntryResults(
				assetPublisherDisplayContext, searchContainer,
				portletPreferences);

		for (AssetEntryResult assetEntryResult : assetEntryResults) {
			assetEntries.addAll(assetEntryResult.getAssetEntries());
		}

		return assetEntries;
	}

	protected static String getAssetPublisherURL(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		StringBundler sb = new StringBundler(6);

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

	protected static String getEntryURL(
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

	protected static String getEntryURLAssetPublisher(
			PortletRequest portletRequest, PortletResponse portletResponse,
			AssetEntry assetEntry)
		throws Exception {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetEntry.getClassName());

		StringBundler sb = new StringBundler(4);

		sb.append(getAssetPublisherURL(portletRequest));
		sb.append(assetRendererFactory.getType());
		sb.append("/id/");
		sb.append(assetEntry.getEntryId());

		return sb.toString();
	}

	protected static String getEntryURLViewInContext(
			PortletRequest portletRequest, PortletResponse portletResponse,
			AssetEntry assetEntry)
		throws Exception {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetEntry.getClassName());

		AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(
			assetEntry.getClassPK());

		String viewInContextURL = assetRenderer.getURLViewInContext(
			PortalUtil.getLiferayPortletRequest(portletRequest),
			PortalUtil.getLiferayPortletResponse(portletResponse), null);

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

	protected static String getFeedURL(PortletRequest portletRequest)
		throws Exception {

		String feedURL = getAssetPublisherURL(portletRequest);

		return feedURL.concat("rss");
	}

}