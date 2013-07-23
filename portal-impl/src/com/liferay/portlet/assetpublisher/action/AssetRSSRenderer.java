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
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
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
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Carlos Sierra Andr�s
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 */
public class AssetRSSRenderer extends DefaultRSSRenderer {

	public AssetRSSRenderer(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		super(resourceRequest);

		_resourceRequest = resourceRequest;
		_resourceResponse = resourceResponse;

		_portletPreferences = resourceRequest.getPreferences();
	}

	@Override
	public String getFeedURL() throws PortalException, SystemException {
		String feedURL = getAssetPublisherURL();

		return feedURL.concat("rss");
	}

	public String getRRSFeedType() {
		return _portletPreferences.getValue(
			"rssFeedType", RSSUtil.FEED_TYPE_DEFAULT);
	}

	@Override
	public String getRSSName() {
		return RSSUtil.getFeedTypeFormat(getRRSFeedType());
	}

	@Override
	public void populateFeedEntries(List<? super SyndEntry> syndEntries)
		throws PortalException, SystemException {

		String assetLinkBehavior = _portletPreferences.getValue(
			"assetLinkBehavior", "showFullContent");
		String rssDisplayStyle = _portletPreferences.getValue(
			"rssDisplayStyle", RSSUtil.DISPLAY_STYLE_ABSTRACT);

		for (AssetEntry assetEntry : getAssetEntries()) {
			SyndEntry syndEntry = new SyndEntryImpl();

			String author = PortalUtil.getUserName(assetEntry);

			syndEntry.setAuthor(author);

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.ENTRY_TYPE_DEFAULT);

			String value = null;

			String languageId = LanguageUtil.getLanguageId(request);

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
	}

	protected List<AssetEntry> getAssetEntries()
		throws PortalException, SystemException {

		int rssDelta = GetterUtil.getInteger(
			_portletPreferences.getValue("rssDelta", "20"));

		return AssetPublisherUtil.getAssetEntries(
			_portletPreferences, themeDisplay.getLayout(),
			themeDisplay.getScopeGroupId(), rssDelta, true);
	}

	protected String getAssetPublisherURL()
		throws PortalException, SystemException {

		StringBundler sb = new StringBundler(6);

		String layoutFriendlyURL = GetterUtil.getString(
			PortalUtil.getLayoutFriendlyURL(
				themeDisplay.getLayout(), themeDisplay));

		if (!layoutFriendlyURL.startsWith(Http.HTTP_WITH_SLASH) &&
			!layoutFriendlyURL.startsWith(Http.HTTPS_WITH_SLASH)) {

			sb.append(themeDisplay.getPortalURL());
		}

		sb.append(layoutFriendlyURL);
		sb.append(Portal.FRIENDLY_URL_SEPARATOR);
		sb.append("asset_publisher/");

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		sb.append(portletDisplay.getInstanceId());

		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected String getEntryURLAssetPublisher(AssetEntry assetEntry)
		throws Exception {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetEntry.getClassName());

		StringBundler sb = new StringBundler(4);

		sb.append(getAssetPublisherURL());
		sb.append(assetRendererFactory.getType());
		sb.append("/id/");
		sb.append(assetEntry.getEntryId());

		return sb.toString();
	}

	protected String getEntryURLViewInContext(AssetEntry assetEntry)
		throws PortalException, SystemException {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetEntry.getClassName());

		AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
			assetEntry.getClassPK());

		String viewInContextURL = null;

		try {
			viewInContextURL = assetRenderer.getURLViewInContext(
				(LiferayPortletRequest)_resourceRequest,
				(LiferayPortletResponse)_resourceResponse, null);
		}
		catch (Exception e) {
			throw new PortalException(e);
		}

		if (Validator.isNotNull(viewInContextURL) &&
			!viewInContextURL.startsWith(Http.HTTP_WITH_SLASH) &&
			!viewInContextURL.startsWith(Http.HTTPS_WITH_SLASH)) {

			viewInContextURL = themeDisplay.getPortalURL() + viewInContextURL;
		}

		return viewInContextURL;
	}

	private String getEntryURL(String linkBehavior, AssetEntry assetEntry)
		throws PortalException, SystemException {

		if (linkBehavior.equals("viewInPortlet")) {
			return getEntryURLViewInContext(assetEntry);
		}

		try {
			return getEntryURLAssetPublisher(assetEntry);
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	private PortletPreferences _portletPreferences;
	private ResourceRequest _resourceRequest;
	private ResourceResponse _resourceResponse;

}