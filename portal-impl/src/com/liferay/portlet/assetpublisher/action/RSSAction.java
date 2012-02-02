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
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.struts.PortletAction;
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

import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class RSSAction extends PortletAction {

	@Override
	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		resourceResponse.setContentType(ContentTypes.TEXT_XML_UTF8);

		OutputStream outputStream = resourceResponse.getPortletOutputStream();

		try {
			byte[] bytes = getRSS(resourceRequest, resourceResponse);

			outputStream.write(bytes);
		}
		finally {
			outputStream.close();
		}
	}

	protected String exportToRSS(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String name, String description, String type, double version,
			String displayStyle, String linkBehavior,
			List<AssetEntry> assetEntries)
		throws Exception {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setFeedType(RSSUtil.getFeedType(type, version));
		syndFeed.setTitle(name);
		syndFeed.setLink(getFeedURL(portletRequest));
		syndFeed.setDescription(GetterUtil.getString(description, name));

		List<SyndEntry> syndEntries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(syndEntries);

		for (AssetEntry assetEntry : assetEntries) {
			String link = getEntryURL(
				portletRequest, portletResponse, linkBehavior, assetEntry);

			String author = HtmlUtil.escape(
				PortalUtil.getUserName(
					assetEntry.getUserId(), assetEntry.getUserName()));

			String value = null;

			if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
				value = StringPool.BLANK;
			}
			else {
				value = assetEntry.getSummary();
			}

			SyndEntry syndEntry = new SyndEntryImpl();

			syndEntry.setAuthor(author);

			String languageId = LanguageUtil.getLanguageId(portletRequest);

			syndEntry.setTitle(assetEntry.getTitle(languageId, true));

			syndEntry.setLink(link);
			syndEntry.setUri(syndEntry.getLink());
			syndEntry.setPublishedDate(assetEntry.getCreateDate());
			syndEntry.setUpdatedDate(assetEntry.getModifiedDate());

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.ENTRY_TYPE_DEFAULT);
			syndContent.setValue(value);

			syndEntry.setDescription(syndContent);

			syndEntries.add(syndEntry);
		}

		return RSSUtil.export(syndFeed);
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

		if (!viewInContextURL.startsWith(Http.HTTP_WITH_SLASH) &&
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

	protected byte[] getRSS(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		PortletPreferences preferences = portletRequest.getPreferences();

		String selectionStyle = preferences.getValue(
			"selectionStyle", "dynamic");

		if (!selectionStyle.equals("dynamic")) {
			return new byte[0];
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] groupIds = AssetPublisherUtil.getGroupIds(
			preferences, themeDisplay.getScopeGroupId(),
			themeDisplay.getLayout());

		boolean anyAssetType = GetterUtil.getBoolean(
			preferences.getValue("anyAssetType", Boolean.TRUE.toString()));
		String assetLinkBehavior = preferences.getValue(
			"assetLinkBehavior", "showFullContent");
		boolean excludeZeroViewCount = GetterUtil.getBoolean(
			preferences.getValue("excludeZeroViewCount", "0"));

		int rssDelta = GetterUtil.getInteger(
			preferences.getValue("rssDelta", "20"));
		String rssDisplayStyle = preferences.getValue(
			"rssDisplayStyle", RSSUtil.DISPLAY_STYLE_ABSTRACT);
		String rssFormat = preferences.getValue("rssFormat", "atom10");
		String rssName = preferences.getValue("rssName", null);

		String rssFormatType = RSSUtil.getFormatType(rssFormat);
		double rssFormatVersion = RSSUtil.getFormatVersion(rssFormat);

		AssetEntryQuery assetEntryQuery = AssetPublisherUtil.getAssetEntryQuery(
			preferences, new long[] {themeDisplay.getScopeGroupId()});

		if (!anyAssetType) {
			long[] availableClassNameIds =
				AssetRendererFactoryRegistryUtil.getClassNameIds();

			long[] classNameIds = AssetPublisherUtil.getClassNameIds(
				preferences, availableClassNameIds);

			assetEntryQuery.setClassNameIds(classNameIds);
		}

		assetEntryQuery.setEnd(rssDelta);
		assetEntryQuery.setExcludeZeroViewCount(excludeZeroViewCount);
		assetEntryQuery.setGroupIds(groupIds);
		assetEntryQuery.setStart(0);

		List<AssetEntry> assetEntries = AssetEntryServiceUtil.getEntries(
			assetEntryQuery);

		String rss = exportToRSS(
			portletRequest, portletResponse, rssName, null, rssFormatType,
			rssFormatVersion, rssDisplayStyle, assetLinkBehavior, assetEntries);

		return rss.getBytes(StringPool.UTF8);
	}

}