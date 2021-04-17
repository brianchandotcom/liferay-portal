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

package com.liferay.commerce.product.content.web.internal.layout.display.page.portlet;

import com.liferay.asset.display.page.portlet.BaseAssetDisplayPageFriendlyURLResolver;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.service.CPDisplayLayoutLocalService;
import com.liferay.commerce.product.url.CPFriendlyURL;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.InheritableMap;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alec Sloan
 */
@Component(enabled = false, service = FriendlyURLResolver.class)
public class AssetCategoryDisplayPageFriendlyURLResolver
	extends BaseAssetDisplayPageFriendlyURLResolver {

	@Override
	public String getActualURL(
			long companyId, long groupId, boolean privateLayout,
			String mainPath, String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		Group companyGroup = _groupLocalService.getCompanyGroup(companyId);

		String urlTitle = friendlyURL.substring(_getURLSeparatorLength());

		urlTitle = FriendlyURLNormalizerUtil.normalizeWithEncoding(urlTitle);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				companyGroup.getGroupId(),
				_portal.getClassNameId(AssetCategory.class), urlTitle);

		if (friendlyURLEntry == null) {
			return null;
		}

		AssetCategory assetCategory = _assetCategoryService.fetchCategory(
			friendlyURLEntry.getClassPK());

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_getLayoutDisplayPageObjectProvider(assetCategory);

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutLocalService.fetchCPDisplayLayout(
				groupId, AssetCategory.class, assetCategory.getCategoryId());

		if ((layoutDisplayPageObjectProvider != null) &&
			(cpDisplayLayout != null)) {

			HttpServletRequest httpServletRequest =
				(HttpServletRequest)requestContext.get("request");

			LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
				getLayoutDisplayPageProvider(friendlyURL);

			Object infoItem = getInfoItem(
				layoutDisplayPageObjectProvider, params);

			httpServletRequest.setAttribute(
				InfoDisplayWebKeys.INFO_ITEM, infoItem);

			String infoItemClassName = portal.getClassName(
				layoutDisplayPageObjectProvider.getClassNameId());

			InfoItemDetailsProvider infoItemDetailsProvider =
				infoItemServiceTracker.getFirstInfoItemService(
					InfoItemDetailsProvider.class, infoItemClassName);

			httpServletRequest.setAttribute(
				InfoDisplayWebKeys.INFO_ITEM_DETAILS,
				infoItemDetailsProvider.getInfoItemDetails(infoItem));

			httpServletRequest.setAttribute(
				InfoDisplayWebKeys.INFO_ITEM_FIELD_VALUES_PROVIDER,
				infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFieldValuesProvider.class, infoItemClassName));
			httpServletRequest.setAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER,
				layoutDisplayPageObjectProvider);
			httpServletRequest.setAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER,
				layoutDisplayPageProvider);
			httpServletRequest.setAttribute(
				WebKeys.ASSET_CATEGORY, assetCategory);
			httpServletRequest.setAttribute(
				WebKeys.LAYOUT_ASSET_ENTRY,
				getAssetEntry(layoutDisplayPageObjectProvider));

			Layout layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
				cpDisplayLayout.getLayoutUuid(), groupId, privateLayout);

			if (layout != null) {
				Locale locale = portal.getLocale(httpServletRequest);

				InfoItemFieldValuesProvider<Object>
					infoItemFieldValuesProvider =
					infoItemServiceTracker.getFirstInfoItemService(
						InfoItemFieldValuesProvider.class,
						infoItemClassName);

				InfoItemFieldValues infoItemFieldValues =
					infoItemFieldValuesProvider.getInfoItemFieldValues(
						layoutDisplayPageObjectProvider.getDisplayObject());

				Optional<String> descriptionOptional = getMappedValueOptional(
					layout.getTypeSettingsProperty("mapped-description"),
					infoItemFieldValues, locale);

				portal.setPageDescription(
					HtmlUtil.unescape(
						HtmlUtil.stripHtml(
							descriptionOptional.orElseGet(
								() -> layoutDisplayPageObjectProvider.getDescription(
									locale)))),
					httpServletRequest);

				portal.setPageKeywords(
					layoutDisplayPageObjectProvider.getKeywords(locale),
					httpServletRequest);

				Optional<String> titleOptional = getMappedValueOptional(
					layout.getTypeSettingsProperty("mapped-title"), infoItemFieldValues,
					locale);

				portal.setPageTitle(
					titleOptional.orElseGet(
						() -> layoutDisplayPageObjectProvider.getTitle(locale)),
					httpServletRequest);

				return portal.getLayoutActualURL(layout, mainPath);
			}
		}

		return _getBasicLayoutURL(
			groupId, privateLayout, mainPath, params, requestContext,
			assetCategory);
	}

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long companyId, long groupId, boolean privateLayout,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		Group companyGroup = _groupLocalService.getCompanyGroup(companyId);

		String urlTitle = friendlyURL.substring(_getURLSeparatorLength());

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				companyGroup.getGroupId(),
				_portal.getClassNameId(AssetCategory.class), urlTitle);

		if (friendlyURLEntry == null) {
			return null;
		}

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)requestContext.get("request");

		String languageId = LanguageUtil.getLanguageId(
			_portal.getLocale(httpServletRequest));

		if (Validator.isBlank(friendlyURLEntry.getUrlTitle(languageId))) {
			return null;
		}

		AssetCategory assetCategory = _assetCategoryService.fetchCategory(
			friendlyURLEntry.getClassPK());

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_getLayoutDisplayPageObjectProvider(assetCategory);

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutLocalService.fetchCPDisplayLayout(
				groupId, AssetCategory.class, assetCategory.getCategoryId());

		if ((layoutDisplayPageObjectProvider != null) &&
			(cpDisplayLayout != null)) {

			return super.getLayoutFriendlyURLComposite(
				companyId, groupId, privateLayout, friendlyURL, params,
				requestContext);
		}

		Layout layout = getAssetCategoryLayout(
			groupId, privateLayout, assetCategory.getCategoryId());

		return new LayoutFriendlyURLComposite(
			layout,
			getURLSeparator() + friendlyURLEntry.getUrlTitle(languageId));
	}

	@Override
	public String getURLSeparator() {
		return _cpFriendlyURL.getAssetCategoryURLSeparator(
			CompanyThreadLocal.getCompanyId());
	}

	protected Layout getAssetCategoryLayout(
			long groupId, boolean privateLayout, long categoryId)
		throws PortalException {

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutLocalService.fetchCPDisplayLayout(
				groupId, AssetCategory.class, categoryId);

		if ((cpDisplayLayout == null) ||
			Validator.isNull(cpDisplayLayout.getLayoutUuid())) {

			long plid = _getPlidFromPortletId(
				groupId, privateLayout, CPPortletKeys.CP_CATEGORY_CONTENT_WEB);

			return _layoutLocalService.getLayout(plid);
		}

		return _layoutLocalService.getLayoutByUuidAndGroupId(
			cpDisplayLayout.getLayoutUuid(), groupId, privateLayout);
	}

	private String _getBasicLayoutURL(
			long groupId, boolean privateLayout, String mainPath,
			Map<String, String[]> params, Map<String, Object> requestContext,
			AssetCategory assetCategory)
		throws PortalException {

		Layout layout = getAssetCategoryLayout(
			groupId, privateLayout, assetCategory.getCategoryId());

		String layoutActualURL = _portal.getLayoutActualURL(layout, mainPath);

		InheritableMap<String, String[]> actualParams = new InheritableMap<>();

		if (params != null) {
			actualParams.setParentMap(params);
		}

		actualParams.put(
			"p_p_id", new String[] {CPPortletKeys.CP_CATEGORY_CONTENT_WEB});
		actualParams.put("p_p_lifecycle", new String[] {"0"});
		actualParams.put("p_p_mode", new String[] {"view"});

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)requestContext.get("request");

		httpServletRequest.setAttribute(WebKeys.ASSET_CATEGORY, assetCategory);

		String queryString = _http.parameterMapToString(actualParams, false);

		if (layoutActualURL.contains(StringPool.QUESTION)) {
			layoutActualURL =
				layoutActualURL + StringPool.AMPERSAND + queryString;
		}
		else {
			layoutActualURL =
				layoutActualURL + StringPool.QUESTION + queryString;
		}

		String languageId = LanguageUtil.getLanguageId(
			_portal.getLocale(httpServletRequest));

		_portal.addPageSubtitle(
			assetCategory.getTitle(languageId), httpServletRequest);
		_portal.addPageDescription(
			assetCategory.getDescription(languageId), httpServletRequest);

		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			AssetCategory.class.getName(), assetCategory.getPrimaryKey());

		if (!assetTags.isEmpty()) {
			_portal.addPageKeywords(
				ListUtil.toString(assetTags, AssetTag.NAME_ACCESSOR),
				httpServletRequest);
		}

		return layoutActualURL;
	}

	private LayoutDisplayPageObjectProvider<?>
			_getLayoutDisplayPageObjectProvider(AssetCategory assetCategory)
		throws PortalException {

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			layoutDisplayPageProviderTracker.
				getLayoutDisplayPageProviderByClassName(
					AssetCategory.class.getName());

		InfoItemReference infoItemReference = new InfoItemReference(
			AssetCategory.class.getName(), assetCategory.getCategoryId());

		return layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
			infoItemReference);
	}

	private long _getPlidFromPortletId(
		List<Layout> layouts, String portletId, long scopeGroupId) {

		for (Layout layout : layouts) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			if (_hasNonstaticPortletId(layoutTypePortlet, portletId) &&
				(_portal.getScopeGroupId(layout, portletId) == scopeGroupId)) {

				return layout.getPlid();
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	private long _getPlidFromPortletId(
		long groupId, boolean privateLayout, String portletId) {

		long scopeGroupId = groupId;

		try {
			Group group = _groupLocalService.getGroup(groupId);

			if (group.isLayout()) {
				Layout scopeLayout = _layoutLocalService.getLayout(
					group.getClassPK());

				groupId = scopeLayout.getGroupId();
			}

			String[] validLayoutTypes = {
				LayoutConstants.TYPE_PORTLET,
				LayoutConstants.TYPE_FULL_PAGE_APPLICATION,
				LayoutConstants.TYPE_PANEL
			};

			for (String layoutType : validLayoutTypes) {
				List<Layout> layouts = _layoutLocalService.getLayouts(
					groupId, privateLayout, layoutType);

				long plid = _getPlidFromPortletId(
					layouts, portletId, scopeGroupId);

				if (plid != LayoutConstants.DEFAULT_PLID) {
					return plid;
				}
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	private int _getURLSeparatorLength() {
		String urlSeparator = getURLSeparator();

		return urlSeparator.length();
	}

	private boolean _hasNonstaticPortletId(
		LayoutTypePortlet layoutTypePortlet, String portletId) {

		LayoutTemplate layoutTemplate = layoutTypePortlet.getLayoutTemplate();

		for (String columnId : layoutTemplate.getColumns()) {
			String[] columnValues = StringUtil.split(
				layoutTypePortlet.getTypeSettingsProperty(columnId));

			for (String nonstaticPortletId : columnValues) {
				if (nonstaticPortletId.equals(portletId)) {
					return true;
				}

				String decodedNonstaticPortletName =
					PortletIdCodec.decodePortletName(nonstaticPortletId);

				if (decodedNonstaticPortletName.equals(portletId)) {
					return true;
				}
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoryDisplayPageFriendlyURLResolver.class);

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CPDisplayLayoutLocalService _cpDisplayLayoutLocalService;

	@Reference
	private CPFriendlyURL _cpFriendlyURL;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}