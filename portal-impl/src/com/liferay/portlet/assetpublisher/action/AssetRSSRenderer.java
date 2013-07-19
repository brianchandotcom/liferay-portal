/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.rss.DefaultRSSRenderer;
import com.liferay.util.RSSUtil;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
public class AssetRSSRenderer extends DefaultRSSRenderer {
	
	
	@Override
	public void populateFeedEntries(List<? super SyndEntry> syndEntries)
		throws PortalException, SystemException {

		List<AssetEntry> assetEntries;
		assetEntries = getAssetEntries();

		for (AssetEntry assetEntry : assetEntries) {
			try {
				SyndEntry syndEntry = new SyndEntryImpl();

				String author = PortalUtil.getUserName(assetEntry);

				syndEntry.setAuthor(author);

				SyndContent syndContent = new SyndContentImpl();

				syndContent.setType(RSSUtil.ENTRY_TYPE_DEFAULT);

				String value = null;

				String languageId = LanguageUtil.getLanguageId(_portletRequest);

				if (rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
					value = StringPool.BLANK;
				}
				else {
					value = assetEntry.getSummary(languageId, true);
				}

				syndContent.setValue(value);

				syndEntry.setDescription(syndContent);

				String link;
				link = getEntryURL(assetLinkBehavior, assetEntry);

				syndEntry.setLink(link);

				syndEntry.setPublishedDate(assetEntry.getPublishDate());
				syndEntry.setTitle(assetEntry.getTitle(languageId, true));
				syndEntry.setUpdatedDate(assetEntry.getModifiedDate());
				syndEntry.setUri(syndEntry.getLink());

				syndEntries.add(syndEntry);
			}
			catch (Exception e) {
				if (_log.isErrorEnabled()) {
					_log.error("Could not process entry " + assetEntry, e);
				}
			}
		}
	}

	protected List<AssetEntry> getAssetEntries(
		PortletRequest portletRequest, PortletPreferences portletPreferences)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);

		int rssDelta =
			GetterUtil.getInteger(portletPreferences.getValue(
				"rssDelta", "20"));

		return AssetPublisherUtil.getAssetEntries(
			portletPreferences, themeDisplay.getLayout(),
			themeDisplay.getScopeGroupId(), rssDelta, true);
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


	
}