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

package com.liferay.asset.publisher.web.portlet;

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.display.contributor.AssetDisplayContributorTracker;
import com.liferay.asset.display.contributor.constants.AssetDisplayWebKeys;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.InheritableMap;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(service = FriendlyURLResolver.class)
public class DisplayPageFriendlyURLResolver implements FriendlyURLResolver {

	@Override
	public String getActualURL(
			long companyId, long groupId, boolean privateLayout,
			String mainPath, String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		String urlTitle = friendlyURL.substring(
			JournalArticleConstants.CANONICAL_URL_SEPARATOR.length());

		String normalizedUrlTitle =
			FriendlyURLNormalizerUtil.normalizeWithEncoding(urlTitle);

		JournalArticle journalArticle =
			_journalArticleLocalService.getLatestArticleByUrlTitle(
				groupId, normalizedUrlTitle, WorkflowConstants.STATUS_APPROVED);

		Map<Locale, String> friendlyURLMap = journalArticle.getFriendlyURLMap();

		if (!friendlyURLMap.containsValue(normalizedUrlTitle)) {
			throw new NoSuchArticleException(
				StringBundler.concat(
					"No latest version of a JournalArticle exists with the key",
					"{groupId=", groupId, ", urlTitle=", urlTitle, ", status=",
					WorkflowConstants.STATUS_ANY, "}"));
		}

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		if (Validator.isNull(journalArticle.getLayoutUuid()) &&
			_isShowDisplayPageEntry(assetEntry)) {

			return _getDisplayPageURL(assetEntry, mainPath, requestContext);
		}

		return _getBasicLayoutURL(
			groupId, privateLayout, mainPath, friendlyURL, params,
			requestContext, urlTitle, journalArticle);
	}

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long companyId, long groupId, boolean privateLayout,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		Layout layout = getJournalArticleLayout(
			groupId, privateLayout, friendlyURL);

		return new LayoutFriendlyURLComposite(layout, friendlyURL);
	}

	@Override
	public String getURLSeparator() {
		return JournalArticleConstants.CANONICAL_URL_SEPARATOR;
	}

	protected Layout getJournalArticleLayout(
			long groupId, boolean privateLayout, String friendlyURL)
		throws PortalException {

		String urlTitle = friendlyURL.substring(
			JournalArticleConstants.CANONICAL_URL_SEPARATOR.length());

		String normalizedUrlTitle =
			FriendlyURLNormalizerUtil.normalizeWithEncoding(urlTitle);

		JournalArticle journalArticle =
			_journalArticleLocalService.getLatestArticleByUrlTitle(
				groupId, normalizedUrlTitle, WorkflowConstants.STATUS_APPROVED);

		Map<Locale, String> friendlyURLMap = journalArticle.getFriendlyURLMap();

		if (!friendlyURLMap.containsValue(normalizedUrlTitle)) {
			throw new NoSuchArticleException(
				StringBundler.concat(
					"No latest version of a JournalArticle exists with the key",
					"{groupId=", groupId, ", urlTitle=", urlTitle, ", status=",
					WorkflowConstants.STATUS_ANY, "}"));
		}

		if (Validator.isNotNull(journalArticle.getLayoutUuid())) {
			return _layoutLocalService.getLayoutByUuidAndGroupId(
				journalArticle.getLayoutUuid(), groupId, privateLayout);
		}

		if (Validator.isNotNull(journalArticle.getLayoutUuid())) {
			return _layoutLocalService.getLayoutByUuidAndGroupId(
				journalArticle.getLayoutUuid(), groupId, privateLayout);
		}

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		try {
			Layout layout = _getAssetDisplayPageEntryLayout(assetEntry);

			if (layout != null) {
				return layout;
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return _layoutLocalService.getLayoutByUuidAndGroupId(
			journalArticle.getLayoutUuid(), groupId, privateLayout);
	}

	@Reference(unbind = "-")
	protected void setAssetTagLocalService(
		AssetTagLocalService assetTagLocalService) {

		_assetTagLocalService = assetTagLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	private Layout _createAssetDisplayLayout(long groupId)
		throws PortalException {

		Group group = _groupLocalService.fetchGroup(groupId);

		long defaultUserId = _userLocalService.getDefaultUserId(
			group.getCompanyId());

		Locale locale = LocaleUtil.getSiteDefault();

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(locale, "Asset Display Page");

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.put("visible", Boolean.FALSE.toString());

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);

		return _layoutLocalService.addLayout(
			defaultUserId, groupId, false, 0, nameMap, null, null, null, null,
			"asset_display", typeSettingsProperties.toString(), true,
			new HashMap<>(), serviceContext);
	}

	private Layout _getAssetDisplayLayout(long groupId) throws PortalException {
		List<Layout> layouts = _layoutLocalService.getLayouts(
			groupId, false, "asset_display");

		if (!ListUtil.isEmpty(layouts)) {
			return layouts.get(0);
		}

		return _createAssetDisplayLayout(groupId);
	}

	private Layout _getAssetDisplayPageEntryLayout(AssetEntry assetEntry)
		throws PortalException {

		if (_isShowDisplayPageEntry(assetEntry)) {
			return _getAssetDisplayLayout(assetEntry.getGroupId());
		}

		return null;
	}

	private String _getBasicLayoutURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext, String urlTitle,
			JournalArticle journalArticle)
		throws PortalException {

		Layout layout = getJournalArticleLayout(
			groupId, privateLayout, friendlyURL);

		String layoutActualURL = _portal.getLayoutActualURL(layout, mainPath);

		InheritableMap<String, String[]> actualParams = new InheritableMap<>();

		if (params != null) {
			actualParams.setParentMap(params);
		}

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		String defaultAssetPublisherPortletId = typeSettingsProperties.get(
			LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID);

		String currentDefaultAssetPublisherPortletId =
			defaultAssetPublisherPortletId;

		if (Validator.isNull(defaultAssetPublisherPortletId)) {
			defaultAssetPublisherPortletId = PortletIdCodec.encode(
				AssetPublisherPortletKeys.ASSET_PUBLISHER);
		}

		HttpServletRequest request = (HttpServletRequest)requestContext.get(
			"request");

		if (Validator.isNull(currentDefaultAssetPublisherPortletId)) {
			String actualPortletAuthenticationToken = AuthTokenUtil.getToken(
				request, layout.getPlid(), defaultAssetPublisherPortletId);

			actualParams.put(
				"p_p_auth", new String[] {actualPortletAuthenticationToken});
		}

		actualParams.put(
			"p_p_id", new String[] {defaultAssetPublisherPortletId});
		actualParams.put("p_p_lifecycle", new String[] {"0"});

		if (Validator.isNull(currentDefaultAssetPublisherPortletId)) {
			actualParams.put(
				"p_p_state", new String[] {WindowState.MAXIMIZED.toString()});
		}

		actualParams.put("p_p_mode", new String[] {"view"});
		actualParams.put(
			"p_j_a_id", new String[] {String.valueOf(journalArticle.getId())});

		String namespace = _portal.getPortletNamespace(
			defaultAssetPublisherPortletId);

		actualParams.put(
			namespace + "mvcPath", new String[] {"/view_content.jsp"});

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				JournalArticle.class.getName());

		actualParams.put(
			namespace + "type", new String[] {assetRendererFactory.getType()});

		actualParams.put(
			namespace + "urlTitle",
			new String[] {journalArticle.getUrlTitle()});

		Locale locale = _portal.getLocale(request);

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntryLocalization(
				groupId,
				_classNameLocalService.getClassNameId(JournalArticle.class),
				urlTitle);

		if (friendlyURLEntryLocalization != null) {
			locale = LocaleUtil.fromLanguageId(
				friendlyURLEntryLocalization.getLanguageId());

			request.setAttribute(
				WebKeys.I18N_LANGUAGE_ID,
				friendlyURLEntryLocalization.getLanguageId());

			actualParams.put(
				namespace + "languageId",
				new String[] {friendlyURLEntryLocalization.getLanguageId()});
		}

		String queryString = _http.parameterMapToString(actualParams, false);

		if (layoutActualURL.contains(StringPool.QUESTION)) {
			layoutActualURL =
				layoutActualURL + StringPool.AMPERSAND + queryString;
		}
		else {
			layoutActualURL =
				layoutActualURL + StringPool.QUESTION + queryString;
		}

		_portal.addPageSubtitle(journalArticle.getTitle(locale), request);
		_portal.addPageDescription(
			journalArticle.getDescription(locale), request);

		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			JournalArticle.class.getName(), journalArticle.getPrimaryKey());

		if (!assetTags.isEmpty()) {
			_portal.addPageKeywords(
				ListUtil.toString(assetTags, AssetTag.NAME_ACCESSOR), request);
		}

		return layoutActualURL;
	}

	private String _getDisplayPageURL(
			AssetEntry assetEntry, String mainPath,
			Map<String, Object> requestContext)
		throws PortalException {

		AssetDisplayContributor assetDisplayContributor =
			_assetDisplayContributorTracker.getAssetDisplayContributor(
				assetEntry.getClassName());

		if (assetDisplayContributor == null) {
			throw new PortalException();
		}

		HttpServletRequest request = (HttpServletRequest)requestContext.get(
			"request");

		request.setAttribute(
			AssetDisplayWebKeys.ASSET_DISPLAY_CONTRIBUTOR,
			assetDisplayContributor);
		request.setAttribute(AssetDisplayWebKeys.ASSET_ENTRY, assetEntry);

		Locale locale = _portal.getLocale(request);

		_portal.addPageSubtitle(assetEntry.getTitle(locale), request);
		_portal.addPageDescription(assetEntry.getDescription(locale), request);

		Layout layout = _getAssetDisplayPageEntryLayout(assetEntry);

		return _portal.getLayoutActualURL(layout, mainPath);
	}

	private boolean _isShowDisplayPageEntry(AssetEntry assetEntry) {
		AssetDisplayPageEntry assetDisplayPageEntry =
			_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				assetEntry.getGroupId(), assetEntry.getClassNameId(),
				assetEntry.getClassPK());

		if ((assetDisplayPageEntry == null) ||
			(assetDisplayPageEntry.getType() ==
				AssetDisplayPageConstants.TYPE_NONE)) {

			return false;
		}

		if (assetDisplayPageEntry.getType() ==
				AssetDisplayPageConstants.TYPE_SPECIFIC) {

			return true;
		}

		LayoutPageTemplateEntry defaultLayoutPageTemplateEntry =
			_layoutPageTemplateEntryService.fetchDefaultLayoutPageTemplateEntry(
				assetEntry.getGroupId(), assetEntry.getClassNameId(),
				assetEntry.getClassTypeId());

		if (defaultLayoutPageTemplateEntry != null) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DisplayPageFriendlyURLResolver.class);

	@Reference
	private AssetDisplayContributorTracker _assetDisplayContributorTracker;

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	private JournalArticleLocalService _journalArticleLocalService;
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}