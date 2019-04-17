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

import com.liferay.asset.display.page.portlet.BaseAssetDisplayPageFriendlyURLResolver;
import com.liferay.asset.display.page.util.AssetDisplayPageHelper;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
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
public class DefaultAssetDisplayPageFriendlyURLResolver
	extends BaseAssetDisplayPageFriendlyURLResolver {

	@Override
	public String getActualURL(
			long companyId, long groupId, boolean privateLayout,
			String mainPath, String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		JournalArticle journalArticle = _getJournalArticle(
			groupId, friendlyURL);

		InfoDisplayObjectProvider infoDisplayObjectProvider =
			_getInfoDisplayObjectProvider(journalArticle);

		if (Validator.isNull(journalArticle.getLayoutUuid()) &&
			AssetDisplayPageHelper.hasAssetDisplayPage(
				groupId, infoDisplayObjectProvider.getClassNameId(),
				infoDisplayObjectProvider.getClassPK(),
				infoDisplayObjectProvider.getClassTypeId())) {

			return super.getActualURL(
				companyId, groupId, privateLayout, mainPath, friendlyURL,
				params, requestContext);
		}

		HttpServletRequest request = (HttpServletRequest)requestContext.get(
			"request");

		Locale locale = _portal.getLocale(request);

		return _getBasicLayoutURL(
			groupId, privateLayout, mainPath, friendlyURL, params,
			requestContext, journalArticle.getUrlTitle(locale), journalArticle);
	}

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long companyId, long groupId, boolean privateLayout,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		JournalArticle journalArticle = _getJournalArticle(
			groupId, friendlyURL);

		InfoDisplayObjectProvider infoDisplayObjectProvider =
			_getInfoDisplayObjectProvider(journalArticle);

		if (Validator.isNull(journalArticle.getLayoutUuid()) &&
			AssetDisplayPageHelper.hasAssetDisplayPage(
				groupId, infoDisplayObjectProvider.getClassNameId(),
				infoDisplayObjectProvider.getClassPK(),
				infoDisplayObjectProvider.getClassTypeId())) {

			return super.getLayoutFriendlyURLComposite(
				companyId, groupId, privateLayout, friendlyURL, params,
				requestContext);
		}

		Layout layout = _layoutLocalService.getLayoutByUuidAndGroupId(
			journalArticle.getLayoutUuid(), groupId, privateLayout);

		return new LayoutFriendlyURLComposite(layout, friendlyURL);
	}

	@Override
	public String getURLSeparator() {
		return JournalArticleConstants.CANONICAL_URL_SEPARATOR;
	}

	private String _getBasicLayoutURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext, String urlTitle,
			JournalArticle journalArticle)
		throws PortalException {

		Layout layout = _layoutLocalService.getLayoutByUuidAndGroupId(
			journalArticle.getLayoutUuid(), groupId, privateLayout);

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

		String ddmTemplateKey = _getDDMTemplateKey(friendlyURL);

		if (Validator.isNotNull(ddmTemplateKey)) {
			actualParams.put(
				namespace + "ddmTemplateKey", new String[] {ddmTemplateKey});
		}

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

		InfoDisplayObjectProvider infoDisplayObjectProvider =
			_getInfoDisplayObjectProvider(journalArticle);

		String keywords = infoDisplayObjectProvider.getKeywords(locale);

		if (Validator.isNotNull(keywords)) {
			_portal.addPageKeywords(keywords, request);
		}

		return layoutActualURL;
	}

	private String _getDDMTemplateKey(String friendlyURL) {
		List<String> paths = StringUtil.split(friendlyURL, CharPool.SLASH);

		if (paths.size() <= 2) {
			return StringPool.BLANK;
		}

		return paths.get(2);
	}

	private InfoDisplayObjectProvider _getInfoDisplayObjectProvider(
			JournalArticle journalArticle)
		throws PortalException {

		InfoDisplayContributor infoDisplayContributor =
			infoDisplayContributorTracker.getInfoDisplayContributor(
				JournalArticle.class.getName());

		return infoDisplayContributor.getInfoDisplayObjectProvider(
			journalArticle.getResourcePrimKey());
	}

	private JournalArticle _getJournalArticle(long groupId, String friendlyURL)
		throws PortalException {

		String normalizedUrlTitle =
			FriendlyURLNormalizerUtil.normalizeWithEncoding(
				_getURLTitle(friendlyURL));

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
					"{groupId=", groupId, ", urlTitle=",
					_getURLTitle(friendlyURL), ", status=",
					WorkflowConstants.STATUS_ANY, "}"));
		}

		return journalArticle;
	}

	private String _getURLTitle(String friendlyURL) {
		List<String> paths = StringUtil.split(friendlyURL, CharPool.SLASH);

		return paths.get(1);
	}

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