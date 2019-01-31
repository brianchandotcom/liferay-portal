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

package com.liferay.asset.publisher.web.internal.portlet;

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.display.contributor.AssetDisplayContributorTracker;
import com.liferay.asset.display.contributor.constants.AssetDisplayWebKeys;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.display.page.util.AssetDisplayPageHelper;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.util.AssetHelper;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
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
import com.liferay.portal.kernel.workflow.permission.WorkflowPermissionUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
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
			_journalArticleLocalService.fetchLatestArticleByUrlTitle(
				groupId, normalizedUrlTitle, WorkflowConstants.STATUS_APPROVED);

		if (journalArticle == null) {
			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			journalArticle =
				_journalArticleLocalService.getLatestArticleByUrlTitle(
					groupId, normalizedUrlTitle,
					WorkflowConstants.STATUS_PENDING);

			if (!WorkflowPermissionUtil.hasPermission(
					permissionChecker, groupId,
					"com.liferay.journal.model.JournalArticle",
					journalArticle.getId(), ActionKeys.VIEW)) {

				throw new PrincipalException();
			}
		}

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
			AssetDisplayPageHelper.hasAssetDisplayPage(groupId, assetEntry)) {

			return _getDisplayPageURL(
				groupId, assetEntry, mainPath, requestContext, urlTitle);
		}

		HttpServletRequest request = (HttpServletRequest)requestContext.get(
			"request");

		Locale locale = _portal.getLocale(request);

		urlTitle = journalArticle.getUrlTitle(locale);

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
			_journalArticleLocalService.fetchLatestArticleByUrlTitle(
				groupId, normalizedUrlTitle, WorkflowConstants.STATUS_APPROVED);

		if (journalArticle == null) {
			journalArticle =
				_journalArticleLocalService.getLatestArticleByUrlTitle(
					groupId, normalizedUrlTitle, WorkflowConstants.STATUS_ANY);
		}

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

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		try {
			Layout layout = _getAssetDisplayPageEntryLayout(
				groupId, assetEntry);

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

	private Layout _getAssetDisplayPageEntryLayout(
			long groupId, AssetEntry assetEntry)
		throws PortalException {

		if (!AssetDisplayPageHelper.hasAssetDisplayPage(groupId, assetEntry)) {
			return null;
		}

		AssetDisplayPageEntry assetDisplayPageEntry =
			_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				groupId, assetEntry.getClassNameId(), assetEntry.getClassPK());

		return _layoutLocalService.getLayout(assetDisplayPageEntry.getPlid());
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

		Locale locale = _portal.getLocale(request);

		actualParams.put(namespace + "urlTitle", new String[] {urlTitle});

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntryLocalization(
				groupId,
				_classNameLocalService.getClassNameId(JournalArticle.class),
				urlTitle);

		if (friendlyURLEntryLocalization != null) {
			locale = LocaleUtil.fromLanguageId(
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
			long groupId, AssetEntry assetEntry, String mainPath,
			Map<String, Object> requestContext, String urlTitle)
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
		request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntryLocalization(
				groupId,
				_classNameLocalService.getClassNameId(JournalArticle.class),
				urlTitle);
		Locale locale = _portal.getLocale(request);

		if (friendlyURLEntryLocalization != null) {
			request.setAttribute(
				AssetDisplayWebKeys.CURRENT_I18N_LANGUAGE_ID,
				LocaleUtil.toLanguageId(locale));
			request.setAttribute(
				WebKeys.I18N_LANGUAGE_ID,
				friendlyURLEntryLocalization.getLanguageId());

			locale = LocaleUtil.fromLanguageId(
				friendlyURLEntryLocalization.getLanguageId());
		}

		_portal.setPageTitle(assetEntry.getTitle(locale), request);
		_portal.setPageDescription(assetEntry.getDescription(locale), request);

		_portal.setPageKeywords(
			_assetHelper.getAssetKeywords(
				assetEntry.getClassName(), assetEntry.getClassPK()),
			request);

		Layout layout = _getAssetDisplayPageEntryLayout(groupId, assetEntry);

		return _portal.getLayoutActualURL(layout, mainPath);
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

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private Http _http;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}