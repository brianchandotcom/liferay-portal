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

package com.liferay.asset.display.page.portlet;

import com.liferay.asset.display.page.configuration.AssetDisplayPageConfiguration;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.seo.template.LayoutSEOTemplateProcessor;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.model.LayoutQueryStringComposite;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PublicRenderParameter;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapperThreadLocal;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.portlet.PortletQNameUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InheritableMap;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.QName;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
public abstract class BaseAssetDisplayPageFriendlyURLResolver
	implements FriendlyURLResolver {

	@Override
	public String getActualURL(
			long companyId, long groupId, boolean privateLayout,
			String mainPath, String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		LayoutQueryStringComposite layoutQueryStringComposite =
			_getPortletFriendlyURLMapperLayoutQueryStringComposite(
				friendlyURL, params, requestContext);

		friendlyURL = layoutQueryStringComposite.getFriendlyURL();

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)requestContext.get("request");

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_getLayoutDisplayPageProvider(friendlyURL);

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_getLayoutDisplayPageObjectProvider(
				layoutDisplayPageProvider, groupId, friendlyURL);

		Object infoItem = _getInfoItem(layoutDisplayPageObjectProvider, params);

		httpServletRequest.setAttribute(InfoDisplayWebKeys.INFO_ITEM, infoItem);

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
			WebKeys.LAYOUT_ASSET_ENTRY,
			_getAssetEntry(layoutDisplayPageObjectProvider));

		Locale locale = portal.getLocale(httpServletRequest);
		Layout layout = _getLayoutDisplayPageObjectProviderLayout(
			groupId, layoutDisplayPageObjectProvider,
			layoutDisplayPageProvider);

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class,
				portal.getClassName(
					layoutDisplayPageObjectProvider.getClassNameId()));

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(
				layoutDisplayPageObjectProvider.getDisplayObject());

		Optional<String> descriptionOptional = _getMappedValueOptional(
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

		Optional<String> titleOptional = _getMappedValueOptional(
			layout.getTypeSettingsProperty("mapped-title"), infoItemFieldValues,
			locale);

		portal.setPageTitle(
			titleOptional.orElseGet(
				() -> layoutDisplayPageObjectProvider.getTitle(locale)),
			httpServletRequest);

		return portal.getLayoutActualURL(layout, mainPath) +
			layoutQueryStringComposite.getQueryString();
	}

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long companyId, long groupId, boolean privateLayout,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_getLayoutDisplayPageProvider(friendlyURL);

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_getLayoutDisplayPageObjectProvider(
				layoutDisplayPageProvider, groupId, friendlyURL);

		if (layoutDisplayPageObjectProvider == null) {
			throw new PortalException();
		}

		Layout layout = _getLayoutDisplayPageObjectProviderLayout(
			groupId, layoutDisplayPageObjectProvider,
			layoutDisplayPageProvider);

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)requestContext.get("request");

		HttpSession httpSession = httpServletRequest.getSession();

		Locale locale = (Locale)httpSession.getAttribute(WebKeys.LOCALE);

		if (locale != null) {
			String urlTitle = layoutDisplayPageObjectProvider.getURLTitle(
				locale);

			if (Validator.isNotNull(urlTitle)) {
				friendlyURL = getURLSeparator() + urlTitle;
			}
		}

		return new LayoutFriendlyURLComposite(layout, friendlyURL);
	}

	@Reference
	protected AssetDisplayPageEntryLocalService
		assetDisplayPageEntryLocalService;

	@Reference
	protected AssetEntryService assetEntryLocalService;

	@Reference
	protected InfoItemServiceTracker infoItemServiceTracker;

	@Reference
	protected LayoutDisplayPageProviderTracker layoutDisplayPageProviderTracker;

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected LayoutPageTemplateEntryService layoutPageTemplateEntryService;

	@Reference
	protected LayoutSEOTemplateProcessor layoutSEOTemplateProcessor;

	@Reference
	protected Portal portal;

	private AssetEntry _getAssetEntry(
		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider) {

		long classNameId = layoutDisplayPageObjectProvider.getClassNameId();

		if (classNameId == portal.getClassNameId(FileEntry.class)) {
			classNameId = portal.getClassNameId(DLFileEntry.class);
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				portal.getClassName(classNameId));

		if (assetRendererFactory == null) {
			return null;
		}

		long classPK = layoutDisplayPageObjectProvider.getClassPK();

		try {
			AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
				portal.getClassName(classNameId), classPK);

			AssetDisplayPageConfiguration assetDisplayPageConfiguration =
				ConfigurationProviderUtil.getSystemConfiguration(
					AssetDisplayPageConfiguration.class);

			if ((assetEntry != null) &&
				assetDisplayPageConfiguration.enableViewCountIncrement()) {

				assetEntryLocalService.incrementViewCounter(assetEntry);
			}

			return assetEntry;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return null;
	}

	private Object _getInfoItem(
			LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider,
			Map<String, String[]> params)
		throws NoSuchInfoItemException {

		long classPK = _getVersionClassPK(params);

		if (classPK <= 0) {
			return layoutDisplayPageObjectProvider.getDisplayObject();
		}

		InfoItemIdentifier infoItemIdentifier = new ClassPKInfoItemIdentifier(
			layoutDisplayPageObjectProvider.getClassPK());

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			(InfoItemObjectProvider<Object>)
				infoItemServiceTracker.getFirstInfoItemService(
					InfoItemObjectProvider.class,
					portal.getClassName(
						layoutDisplayPageObjectProvider.getClassNameId()),
					infoItemIdentifier.getInfoItemServiceFilter());

		infoItemIdentifier.setVersion(InfoItemIdentifier.VERSION_LATEST);

		return infoItemObjectProvider.getInfoItem(infoItemIdentifier);
	}

	private LayoutDisplayPageObjectProvider<?>
		_getLayoutDisplayPageObjectProvider(
			LayoutDisplayPageProvider<?> layoutDisplayPageProvider,
			long groupId, String friendlyURL) {

		return layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
			groupId, _getUrlTitle(friendlyURL));
	}

	private Layout _getLayoutDisplayPageObjectProviderLayout(
		long groupId,
		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider,
		LayoutDisplayPageProvider<?> layoutDisplayPageProvider) {

		AssetDisplayPageEntry assetDisplayPageEntry =
			assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				groupId, layoutDisplayPageObjectProvider.getClassNameId(),
				layoutDisplayPageObjectProvider.getClassPK());

		if (assetDisplayPageEntry != null) {
			if (assetDisplayPageEntry.getType() ==
					AssetDisplayPageConstants.TYPE_NONE) {

				return null;
			}

			if (assetDisplayPageEntry.getType() ==
					AssetDisplayPageConstants.TYPE_SPECIFIC) {

				return layoutLocalService.fetchLayout(
					assetDisplayPageEntry.getPlid());
			}

			if (layoutDisplayPageProvider.inheritable() &&
				(assetDisplayPageEntry.getType() ==
					AssetDisplayPageConstants.TYPE_INHERITED)) {

				InfoItemReference infoItemReference = new InfoItemReference(
					portal.getClassName(
						layoutDisplayPageObjectProvider.getClassNameId()),
					layoutDisplayPageObjectProvider.getClassPK());

				LayoutDisplayPageObjectProvider<?>
					parentLayoutDisplayPageObjectProvider =
						layoutDisplayPageProvider.
							getParentLayoutDisplayPageObjectProvider(
								infoItemReference);

				if (parentLayoutDisplayPageObjectProvider != null) {
					return _getLayoutDisplayPageObjectProviderLayout(
						groupId, parentLayoutDisplayPageObjectProvider,
						layoutDisplayPageProvider);
				}
			}
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryService.fetchDefaultLayoutPageTemplateEntry(
				groupId, layoutDisplayPageObjectProvider.getClassNameId(),
				layoutDisplayPageObjectProvider.getClassTypeId());

		if (layoutPageTemplateEntry != null) {
			return layoutLocalService.fetchLayout(
				layoutPageTemplateEntry.getPlid());
		}

		return null;
	}

	private LayoutDisplayPageProvider<?> _getLayoutDisplayPageProvider(
			String friendlyURL)
		throws PortalException {

		String urlSeparator = _getURLSeparator(friendlyURL);

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			layoutDisplayPageProviderTracker.
				getLayoutDisplayPageProviderByURLSeparator(urlSeparator);

		if (layoutDisplayPageProvider == null) {
			throw new PortalException(
				"Info display contributor is not available for " +
					urlSeparator);
		}

		return layoutDisplayPageProvider;
	}

	private Optional<String> _getMappedValueOptional(
		String template, InfoItemFieldValues infoItemFieldValues,
		Locale locale) {

		if ((infoItemFieldValues == null) || Validator.isNull(template)) {
			return Optional.empty();
		}

		return Optional.ofNullable(
			layoutSEOTemplateProcessor.processTemplate(
				template, infoItemFieldValues, locale));
	}

	private LayoutQueryStringComposite
		_getPortletFriendlyURLMapperLayoutQueryStringComposite(
			String url, Map<String, String[]> params,
			Map<String, Object> requestContext) {

		boolean foundFriendlyURLMapper = false;

		String friendlyURL = url;
		String queryString = StringPool.BLANK;

		List<Portlet> portlets =
			PortletLocalServiceUtil.getFriendlyURLMapperPortlets();

		for (Portlet portlet : portlets) {
			FriendlyURLMapper friendlyURLMapper =
				portlet.getFriendlyURLMapperInstance();

			if (url.endsWith(
					StringPool.SLASH + friendlyURLMapper.getMapping())) {

				url += StringPool.SLASH;
			}

			int pos = -1;

			if (friendlyURLMapper.isCheckMappingWithPrefix()) {
				pos = url.indexOf(
					Portal.FRIENDLY_URL_SEPARATOR +
						friendlyURLMapper.getMapping() + StringPool.SLASH);
			}
			else {
				pos = url.indexOf(
					StringPool.SLASH + friendlyURLMapper.getMapping() +
						StringPool.SLASH);
			}

			if (pos == -1) {
				continue;
			}

			foundFriendlyURLMapper = true;

			friendlyURL = url.substring(0, pos);

			InheritableMap<String, String[]> actualParams =
				new InheritableMap<>();

			if (params != null) {
				actualParams.setParentMap(params);
			}

			Map<String, String> prpIdentifiers = new HashMap<>();

			Set<PublicRenderParameter> publicRenderParameters =
				portlet.getPublicRenderParameters();

			for (PublicRenderParameter publicRenderParameter :
					publicRenderParameters) {

				QName qName = publicRenderParameter.getQName();

				String publicRenderParameterIdentifier = qName.getLocalPart();

				prpIdentifiers.put(
					publicRenderParameterIdentifier,
					PortletQNameUtil.getPublicRenderParameterName(qName));
			}

			FriendlyURLMapperThreadLocal.setPRPIdentifiers(prpIdentifiers);

			if (friendlyURLMapper.isCheckMappingWithPrefix()) {
				friendlyURLMapper.populateParams(
					url.substring(pos + 2), actualParams, requestContext);
			}
			else {
				friendlyURLMapper.populateParams(
					url.substring(pos), actualParams, requestContext);
			}

			queryString =
				StringPool.AMPERSAND +
					HttpUtil.parameterMapToString(actualParams, false);

			break;
		}

		if (!foundFriendlyURLMapper) {
			int x = url.indexOf(Portal.FRIENDLY_URL_SEPARATOR);

			if (x != -1) {
				int y = url.indexOf(CharPool.SLASH, x + 3);

				if (y == -1) {
					y = url.length();
				}

				String ppid = url.substring(x + 3, y);

				if (Validator.isNotNull(ppid)) {
					friendlyURL = url.substring(0, x);

					Map<String, String[]> actualParams = null;

					if (params != null) {
						actualParams = new HashMap<>(params);
					}
					else {
						actualParams = new HashMap<>();
					}

					actualParams.put("p_p_id", new String[] {ppid});
					actualParams.put("p_p_lifecycle", new String[] {"0"});
					actualParams.put(
						"p_p_mode", new String[] {PortletMode.VIEW.toString()});
					actualParams.put(
						"p_p_state",
						new String[] {WindowState.MAXIMIZED.toString()});

					queryString =
						StringPool.AMPERSAND +
							HttpUtil.parameterMapToString(actualParams, false);
				}
			}
		}

		friendlyURL = com.liferay.portal.kernel.util.StringUtil.replace(
			friendlyURL, StringPool.DOUBLE_SLASH, StringPool.SLASH);

		if (friendlyURL.endsWith(StringPool.SLASH)) {
			friendlyURL = friendlyURL.substring(0, friendlyURL.length() - 1);
		}

		return new LayoutQueryStringComposite(null, friendlyURL, queryString);
	}

	private String _getURLSeparator(String friendlyURL) {
		List<String> paths = StringUtil.split(friendlyURL, CharPool.SLASH);

		return CharPool.SLASH + paths.get(0) + CharPool.SLASH;
	}

	private String _getUrlTitle(String friendlyURL) {
		String urlSeparator = _getURLSeparator(friendlyURL);

		LayoutQueryStringComposite layoutQueryStringComposite =
			_getPortletFriendlyURLMapperLayoutQueryStringComposite(
				friendlyURL, new HashMap<>(), new HashMap<>());

		String newFriendlyURL = layoutQueryStringComposite.getFriendlyURL();

		if (newFriendlyURL.startsWith(urlSeparator)) {
			return newFriendlyURL.substring(urlSeparator.length());
		}

		if (friendlyURL.startsWith(urlSeparator)) {
			return friendlyURL.substring(urlSeparator.length());
		}

		return StringPool.BLANK;
	}

	private long _getVersionClassPK(Map<String, String[]> params) {
		String[] versions = params.get("version");

		if (ArrayUtil.isEmpty(versions)) {
			return 0;
		}

		return GetterUtil.getLong(versions[0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAssetDisplayPageFriendlyURLResolver.class);

}