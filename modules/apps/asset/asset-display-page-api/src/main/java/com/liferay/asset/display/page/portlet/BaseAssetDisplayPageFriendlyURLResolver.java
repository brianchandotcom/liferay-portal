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
import com.liferay.asset.display.page.constants.AssetDisplayPageWebKeys;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.asset.util.AssetHelper;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.display.url.provider.InfoEditURLProvider;
import com.liferay.info.display.url.provider.InfoEditURLProviderTracker;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
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
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
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

		InfoDisplayContributor<?> infoDisplayContributor =
			_getInfoDisplayContributor(friendlyURL);

		httpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR,
			infoDisplayContributor);

		InfoDisplayObjectProvider<?> infoDisplayObjectProvider =
			_getInfoDisplayObjectProvider(
				infoDisplayContributor, groupId, friendlyURL);

		httpServletRequest.setAttribute(
			AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER,
			infoDisplayObjectProvider);

		String infoItemClassName = portal.getClassName(
			infoDisplayObjectProvider.getClassNameId());

		InfoEditURLProvider<?> infoEditURLProvider =
			infoEditURLProviderTracker.getInfoEditURLProvider(
				infoItemClassName);

		httpServletRequest.setAttribute(
			AssetDisplayPageWebKeys.INFO_EDIT_URL_PROVIDER,
			infoEditURLProvider);

		Object infoItem = _getInfoItem(friendlyURL, infoDisplayObjectProvider);

		httpServletRequest.setAttribute(InfoDisplayWebKeys.INFO_ITEM, infoItem);

		InfoItemDetailsProvider infoItemDetailsProvider =
			infoItemServiceTracker.getFirstInfoItemService(
				InfoItemDetailsProvider.class, infoItemClassName);

		httpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_ITEM_DETAILS,
			infoItemDetailsProvider.getInfoItemDetails(infoItem));

		httpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_ITEM_SERVICE_TRACKER,
			infoItemServiceTracker);

		InfoItemFieldValuesProvider<?> infoItemFieldValuesProvider =
			infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, infoItemClassName);

		httpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_ITEM_FIELD_VALUES_PROVIDER,
			infoItemFieldValuesProvider);

		Locale locale = portal.getLocale(httpServletRequest);
		Layout layout = _getInfoDisplayObjectProviderLayout(
			infoDisplayObjectProvider);

		portal.setPageDescription(
			HtmlUtil.unescape(
				HtmlUtil.stripHtml(
					_getMappedField(
						infoDisplayObjectProvider, locale,
						layout.getTypeSettingsProperty("mapped-description"),
						infoDisplayObjectProvider::getDescription))),
			httpServletRequest);

		portal.setPageKeywords(
			infoDisplayObjectProvider.getKeywords(locale), httpServletRequest);
		portal.setPageTitle(
			_getMappedField(
				infoDisplayObjectProvider, locale,
				layout.getTypeSettingsProperty("mapped-title"),
				infoDisplayObjectProvider::getTitle),
			httpServletRequest);

		AssetEntry assetEntry = _getAssetEntry(infoDisplayObjectProvider);

		httpServletRequest.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);

		return portal.getLayoutActualURL(layout, mainPath);
	}

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long companyId, long groupId, boolean privateLayout,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		InfoDisplayContributor<?> infoDisplayContributor =
			_getInfoDisplayContributor(friendlyURL);

		InfoDisplayObjectProvider<?> infoDisplayObjectProvider =
			_getInfoDisplayObjectProvider(
				infoDisplayContributor, groupId, friendlyURL);

		if (infoDisplayObjectProvider == null) {
			throw new PortalException();
		}

		Layout layout = _getInfoDisplayObjectProviderLayout(
			infoDisplayObjectProvider);

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)requestContext.get("request");

		HttpSession httpSession = httpServletRequest.getSession();

		Locale locale = (Locale)httpSession.getAttribute(WebKeys.LOCALE);

		if (locale != null) {
			String urlTitle = infoDisplayObjectProvider.getURLTitle(locale);

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
	protected AssetHelper assetHelper;

	@Reference
	protected InfoDisplayContributorTracker infoDisplayContributorTracker;

	@Reference
	protected InfoEditURLProviderTracker infoEditURLProviderTracker;

	@Reference
	protected InfoItemServiceTracker infoItemServiceTracker;

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected LayoutPageTemplateEntryService layoutPageTemplateEntryService;

	@Reference
	protected Portal portal;

	private AssetEntry _getAssetEntry(
		InfoDisplayObjectProvider<?> infoDisplayObjectProvider) {

		String classNameId = PortalUtil.getClassName(
			infoDisplayObjectProvider.getClassNameId());

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				classNameId);

		if (assetRendererFactory == null) {
			return null;
		}

		long classPK = infoDisplayObjectProvider.getClassPK();

		try {
			AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
				classNameId, classPK);

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

	private InfoDisplayContributor<?> _getInfoDisplayContributor(
			String friendlyURL)
		throws PortalException {

		String infoURLSeparator = _getInfoURLSeparator(friendlyURL);

		InfoDisplayContributor<?> infoDisplayContributor =
			infoDisplayContributorTracker.
				getInfoDisplayContributorByURLSeparator(infoURLSeparator);

		if (infoDisplayContributor == null) {
			throw new PortalException(
				"Info display contributor is not available for " +
					infoURLSeparator);
		}

		return infoDisplayContributor;
	}

	private InfoDisplayObjectProvider<?> _getInfoDisplayObjectProvider(
			InfoDisplayContributor<?> infoDisplayContributor, long groupId,
			String friendlyURL)
		throws PortalException {

		return infoDisplayContributor.getInfoDisplayObjectProvider(
			groupId, _getUrlTitle(friendlyURL));
	}

	private Layout _getInfoDisplayObjectProviderLayout(
		InfoDisplayObjectProvider<?> infoDisplayObjectProvider) {

		AssetDisplayPageEntry assetDisplayPageEntry =
			assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				infoDisplayObjectProvider.getGroupId(),
				infoDisplayObjectProvider.getClassNameId(),
				infoDisplayObjectProvider.getClassPK());

		if (assetDisplayPageEntry == null) {
			return null;
		}

		if (assetDisplayPageEntry.getType() !=
				AssetDisplayPageConstants.TYPE_DEFAULT) {

			return layoutLocalService.fetchLayout(
				assetDisplayPageEntry.getPlid());
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryService.fetchDefaultLayoutPageTemplateEntry(
				infoDisplayObjectProvider.getGroupId(),
				infoDisplayObjectProvider.getClassNameId(),
				infoDisplayObjectProvider.getClassTypeId());

		if (layoutPageTemplateEntry != null) {
			return layoutLocalService.fetchLayout(
				layoutPageTemplateEntry.getPlid());
		}

		return null;
	}

	private Object _getInfoItem(
			String friendlyURL,
			InfoDisplayObjectProvider<?> infoDisplayObjectProvider)
		throws NoSuchInfoItemException {

		long classPK = _getVersionClassPK(friendlyURL);

		if (classPK <= 0) {
			return infoDisplayObjectProvider.getDisplayObject();
		}

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			(InfoItemObjectProvider<Object>)
				infoItemServiceTracker.getFirstInfoItemService(
					InfoItemObjectProvider.class,
					portal.getClassName(
						infoDisplayObjectProvider.getClassNameId()));

		InfoItemReference infoItemReference = new InfoItemReference(
			infoDisplayObjectProvider.getClassPK());

		infoItemReference.setVersion(InfoItemReference.VERSION_LATEST);

		return infoItemObjectProvider.getInfoItem(infoItemReference);
	}

	private String _getInfoURLSeparator(String friendlyURL) {
		List<String> paths = StringUtil.split(friendlyURL, CharPool.SLASH);

		return CharPool.SLASH + paths.get(0) + CharPool.SLASH;
	}

	private String _getMappedField(
		InfoDisplayObjectProvider<?> infoDisplayObjectProvider, Locale locale,
		String mappedFieldName, Function<Locale, String> defaultValueFunction) {

		if (infoDisplayObjectProvider != null) {
			InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
				infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFieldValuesProvider.class,
					portal.getClassName(
						infoDisplayObjectProvider.getClassNameId()));

			InfoFieldValue<Object> infoFieldValue =
				infoItemFieldValuesProvider.getInfoItemFieldValue(
					infoDisplayObjectProvider.getDisplayObject(),
					mappedFieldName);

			if (infoFieldValue != null) {
				return String.valueOf(infoFieldValue.getValue(locale));
			}
		}

		return defaultValueFunction.apply(locale);
	}

	private String _getUrlTitle(String friendlyURL) {
		String infoURLSeparator = _getInfoURLSeparator(friendlyURL);

		String urlTitle = friendlyURL.substring(infoURLSeparator.length());

		long versionClassPK = _getVersionClassPK(friendlyURL);

		if (versionClassPK > 0) {
			String versionClassPKValue = String.valueOf(versionClassPK);

			urlTitle = friendlyURL.substring(
				infoURLSeparator.length(),
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