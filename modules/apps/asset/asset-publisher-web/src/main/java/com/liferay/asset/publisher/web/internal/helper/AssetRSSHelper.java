/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.helper;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.publisher.constants.AssetPublisherWebKeys;
import com.liferay.asset.publisher.util.AssetEntryResult;
import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.asset.publisher.web.internal.configuration.AssetPublisherSelectionStyleConfigurationUtil;
import com.liferay.asset.publisher.web.internal.constants.AssetPublisherSelectionStyleConstants;
import com.liferay.asset.publisher.web.internal.display.context.AssetPublisherDisplayContext;
import com.liferay.asset.util.AssetHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.rss.export.RSSExporter;
import com.liferay.rss.model.SyndContent;
import com.liferay.rss.model.SyndEntry;
import com.liferay.rss.model.SyndFeed;
import com.liferay.rss.model.SyndLink;
import com.liferay.rss.model.SyndModelFactory;
import com.liferay.rss.util.RSSUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
@Component(service = AssetRSSHelper.class)
public class AssetRSSHelper {

	public byte[] getRSS(
			ResourceRequest portletRequest, ResourceResponse portletResponse)
		throws Exception {

		PortletPreferences portletPreferences = portletRequest.getPreferences();

		String selectionStyle = portletPreferences.getValue(
			"selectionStyle",
			AssetPublisherSelectionStyleConfigurationUtil.
				defaultSelectionStyle());

		if (!selectionStyle.equals(
				AssetPublisherSelectionStyleConstants.TYPE_DYNAMIC)) {

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

		String rss = _exportToRSS(
			portletRequest, portletResponse, rssName, format, version,
			rssDisplayStyle, assetLinkBehavior,
			_getAssetEntries(portletRequest, portletPreferences));

		return rss.getBytes(StringPool.UTF8);
	}

	private String _exportToRSS(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String name, String format, double version, String displayStyle,
			String linkBehavior, List<AssetEntry> assetEntries)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		SyndFeed syndFeed = _syndModelFactory.createSyndFeed();

		syndFeed.setDescription(name);

		List<SyndEntry> syndEntries = new ArrayList<>();

		syndFeed.setEntries(syndEntries);

		for (AssetEntry assetEntry : assetEntries) {
			SyndEntry syndEntry = _syndModelFactory.createSyndEntry();

			syndEntry.setAuthor(_portal.getUserName(assetEntry));

			SyndContent syndContent = _syndModelFactory.createSyndContent();

			syndContent.setType(RSSUtil.ENTRY_TYPE_DEFAULT);

			String value = null;

			String languageId = _language.getLanguageId(portletRequest);

			if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
				value = StringPool.BLANK;
			}
			else {
				value = assetEntry.getSummary(languageId, true);
			}

			syndContent.setValue(value);

			syndEntry.setDescription(syndContent);

			String link = _getEntryURL(
				portletRequest, portletResponse, linkBehavior, assetEntry);

			syndEntry.setLink(link);

			syndEntry.setPublishedDate(assetEntry.getPublishDate());
			syndEntry.setTitle(assetEntry.getTitle(languageId, true));
			syndEntry.setUpdatedDate(assetEntry.getModifiedDate());
			syndEntry.setUri(link);

			syndEntries.add(syndEntry);
		}

		syndFeed.setFeedType(RSSUtil.getFeedType(format, version));

		List<SyndLink> syndLinks = new ArrayList<>();

		syndFeed.setLinks(syndLinks);

		SyndLink selfSyndLink = _syndModelFactory.createSyndLink();

		syndLinks.add(selfSyndLink);

		String feedURL = _getFeedURL(portletRequest);

		selfSyndLink.setHref(feedURL);

		selfSyndLink.setRel("self");

		SyndLink alternateSyndLink = _syndModelFactory.createSyndLink();

		syndLinks.add(alternateSyndLink);

		alternateSyndLink.setHref(_portal.getLayoutFullURL(themeDisplay));
		alternateSyndLink.setRel("alternate");

		syndFeed.setPublishedDate(new Date());
		syndFeed.setTitle(name);
		syndFeed.setUri(feedURL);

		return _rssExporter.export(syndFeed);
	}

	private List<AssetEntry> _getAssetEntries(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<AssetEntry> assetEntries = new ArrayList<>();

		SearchContainer<AssetEntry> searchContainer = new SearchContainer();

		AssetPublisherDisplayContext assetPublisherDisplayContext =
			(AssetPublisherDisplayContext)portletRequest.getAttribute(
				AssetPublisherWebKeys.ASSET_PUBLISHER_DISPLAY_CONTEXT);

		searchContainer.setDelta(assetPublisherDisplayContext.getRSSDelta());

		Map<String, Serializable> attributes =
			assetPublisherDisplayContext.getAttributes();

		attributes.put("filterExpired", Boolean.TRUE);

		List<AssetEntryResult> assetEntryResults =
			_assetPublisherHelper.getAssetEntryResults(
				searchContainer,
				assetPublisherDisplayContext.getAssetEntryQuery(),
				themeDisplay.getLayout(), portletPreferences,
				assetPublisherDisplayContext.getPortletName(),
				themeDisplay.getLocale(), themeDisplay.getTimeZone(),
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				themeDisplay.getUserId(),
				assetPublisherDisplayContext.getClassNameIds(), attributes);

		for (AssetEntryResult assetEntryResult : assetEntryResults) {
			assetEntries.addAll(assetEntryResult.getAssetEntries());
		}

		return assetEntries;
	}

	private String _getAssetPublisherURL(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		StringBundler sb = new StringBundler(6);

		String layoutFriendlyURL = GetterUtil.getString(
			_portal.getLayoutFriendlyURL(layout, themeDisplay));

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

	private String _getEntryURL(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String linkBehavior, AssetEntry assetEntry)
		throws Exception {

		if (linkBehavior.equals("viewInPortlet")) {
			return _getEntryURLViewInContext(
				portletRequest, portletResponse, assetEntry);
		}

		return _getEntryURLAssetPublisher(portletRequest, assetEntry);
	}

	private String _getEntryURLAssetPublisher(
			PortletRequest portletRequest, AssetEntry assetEntry)
		throws Exception {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetEntry.getClassName());

		return StringBundler.concat(
			_getAssetPublisherURL(portletRequest),
			assetRendererFactory.getType(), "/id/", assetEntry.getEntryId());
	}

	private String _getEntryURLViewInContext(
		PortletRequest portletRequest, PortletResponse portletResponse,
		AssetEntry assetEntry) {

		String assetViewURL = _assetPublisherHelper.getAssetViewURL(
			_portal.getLiferayPortletRequest(portletRequest),
			_portal.getLiferayPortletResponse(portletResponse), assetEntry,
			true);

		if (Validator.isNotNull(assetViewURL) &&
			!assetViewURL.startsWith(Http.HTTP_WITH_SLASH) &&
			!assetViewURL.startsWith(Http.HTTPS_WITH_SLASH)) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			assetViewURL = themeDisplay.getPortalURL() + assetViewURL;
		}

		return assetViewURL;
	}

	private String _getFeedURL(PortletRequest portletRequest) throws Exception {
		String feedURL = _getAssetPublisherURL(portletRequest);

		return feedURL.concat("rss");
	}

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private AssetPublisherHelper _assetPublisherHelper;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private RSSExporter _rssExporter;

	@Reference
	private SyndModelFactory _syndModelFactory;

}