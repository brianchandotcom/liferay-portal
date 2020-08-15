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
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
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
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

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

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)requestContext.get("request");

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_getLayoutDisplayPageProvider(friendlyURL);

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_getLayoutDisplayPageObjectProvider(
				layoutDisplayPageProvider, groupId, friendlyURL);

		Object infoItem = _getInfoItem(
			friendlyURL, layoutDisplayPageObjectProvider);

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
			layoutDisplayPageObjectProvider);

		portal.setPageDescription(
			HtmlUtil.unescape(
				HtmlUtil.stripHtml(
					_getMappedField(
						layoutDisplayPageObjectProvider, locale,
						layout.getTypeSettingsProperty("mapped-description"),
						layoutDisplayPageObjectProvider::getDescription))),
			httpServletRequest);

		portal.setPageKeywords(
			layoutDisplayPageObjectProvider.getKeywords(locale),
			httpServletRequest);
		portal.setPageTitle(
			_getMappedField(
				layoutDisplayPageObjectProvider, locale,
				layout.getTypeSettingsProperty("mapped-title"),
				layoutDisplayPageObjectProvider::getTitle),
			httpServletRequest);

		return portal.getLayoutActualURL(layout, mainPath);
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
			layoutDisplayPageObjectProvider);

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
			String friendlyURL,
			LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider)
		throws NoSuchInfoItemException {

		long classPK = _getVersionClassPK(friendlyURL);

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
					infoItemIdentifier.getInfoServiceFilter());

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
		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider) {

		AssetDisplayPageEntry assetDisplayPageEntry =
			assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				layoutDisplayPageObjectProvider.getGroupId(),
				layoutDisplayPageObjectProvider.getClassNameId(),
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
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryService.fetchDefaultLayoutPageTemplateEntry(
				layoutDisplayPageObjectProvider.getGroupId(),
				layoutDisplayPageObjectProvider.getClassNameId(),
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

	private String _getMappedField(
		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider,
		Locale locale, String mappedFieldName,
		Function<Locale, String> defaultValueFunction) {

		if (layoutDisplayPageObjectProvider != null) {
			InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
				infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFieldValuesProvider.class,
					portal.getClassName(
						layoutDisplayPageObjectProvider.getClassNameId()));

			InfoFieldValue<Object> infoFieldValue =
				infoItemFieldValuesProvider.getInfoItemFieldValue(
					layoutDisplayPageObjectProvider.getDisplayObject(),
					mappedFieldName);

			if (infoFieldValue != null) {
				return String.valueOf(infoFieldValue.getValue(locale));
			}
		}

		return defaultValueFunction.apply(locale);
	}

	private String _getURLSeparator(String friendlyURL) {
		List<String> paths = StringUtil.split(friendlyURL, CharPool.SLASH);

		return CharPool.SLASH + paths.get(0) + CharPool.SLASH;
	}

	private String _getUrlTitle(String friendlyURL) {
		String urlSeparator = _getURLSeparator(friendlyURL);

		String urlTitle = friendlyURL.substring(urlSeparator.length());

		long versionClassPK = _getVersionClassPK(friendlyURL);

		if (versionClassPK > 0) {
			String versionClassPKValue = String.valueOf(versionClassPK);

			urlTitle = friendlyURL.substring(
				urlSeparator.length(),
				friendlyURL.length() - versionClassPKValue.length() - 1);
		}

		return urlTitle;
	}

	private long _getVersionClassPK(String friendlyURL) {
		List<String> paths = StringUtil.split(friendlyURL, CharPool.SLASH);

		if (paths.size() < 3) {
			return 0;
		}

		return GetterUtil.getLong(paths.get(paths.size() - 1));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAssetDisplayPageFriendlyURLResolver.class);

}