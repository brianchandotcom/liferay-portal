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

package com.liferay.asset.list.item.selector.web.internal;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalService;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.asset.list.util.AssetListPortletUtil;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoListItemSelectorCriterion;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ItemSelectorView.class)
public class AssetListItemSelectorView
	implements ItemSelectorView<InfoListItemSelectorCriterion> {

	@Override
	public Class<? extends InfoListItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return InfoListItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return _language.get(resourceBundle, "content-sets");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			InfoListItemSelectorCriterion infoListItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse, infoListItemSelectorCriterion,
			portletURL, itemSelectedEventName, search,
			new AssetListItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest,
				infoListItemSelectorCriterion, portletURL));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetListItemSelectorView.class);

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new InfoListItemSelectorReturnType());

	@Reference
	private AssetListEntrySegmentsEntryRelLocalService
		_assetListEntrySegmentsEntryRelLocalService;

	@Reference
	private AssetListEntryService _assetListEntryService;

	@Reference
	private ItemSelectorViewDescriptorRenderer<InfoListItemSelectorCriterion>
		_itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference(target = "(bundle.symbolic.name=com.liferay.asset.list.web)")
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.asset.list.item.selector.web)"
	)
	private ServletContext _servletContext;

	private class AssetListItemSelectorViewDescriptor
		implements ItemSelectorViewDescriptor<AssetListEntry> {

		public AssetListItemSelectorViewDescriptor(
			HttpServletRequest httpServletRequest,
			InfoListItemSelectorCriterion infoListItemSelectorCriterion,
			PortletURL portletURL) {

			_httpServletRequest = httpServletRequest;
			_infoListItemSelectorCriterion = infoListItemSelectorCriterion;
			_portletURL = portletURL;
		}

		@Override
		public ItemDescriptor getItemDescriptor(AssetListEntry assetListEntry) {
			return new ItemDescriptor() {

				@Override
				public String getIcon() {
					return "list";
				}

				@Override
				public String getImageURL() {
					return StringPool.BLANK;
				}

				@Override
				public Date getModifiedDate() {
					return assetListEntry.getModifiedDate();
				}

				@Override
				public String getPayload() {
					return JSONUtil.put(
						"classNameId",
						_portal.getClassNameId(AssetListEntry.class)
					).put(
						"classPK", assetListEntry.getAssetListEntryId()
					).put(
						"title", assetListEntry.getTitle()
					).toString();
				}

				@Override
				public String getSubtitle(Locale locale) {
					ResourceBundle resourceBundle =
						_resourceBundleLoader.loadResourceBundle(locale);

					return _language.get(
						resourceBundle, assetListEntry.getTypeLabel());
				}

				@Override
				public String getTitle(Locale locale) {
					try {
						return assetListEntry.getUnambiguousTitle(locale);
					}
					catch (PortalException portalException) {
						return ReflectionUtil.throwException(portalException);
					}
				}

				@Override
				public long getUserId() {
					return assetListEntry.getUserId();
				}

				@Override
				public String getUserName() {
					return assetListEntry.getUserName();
				}

			};
		}

		@Override
		public ItemSelectorReturnType getItemSelectorReturnType() {
			return new InfoListItemSelectorReturnType();
		}

		@Override
		public String[] getOrderByKeys() {
			return new String[] {"title", "create-date"};
		}

		@Override
		public SearchContainer getSearchContainer() throws PortalException {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			PortletRequest portletRequest =
				(PortletRequest)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);

			ResourceBundle resourceBundle =
				_resourceBundleLoader.loadResourceBundle(
					themeDisplay.getLocale());

			SearchContainer<AssetListEntry> searchContainer =
				new SearchContainer<>(
					portletRequest, _portletURL, null,
					_language.get(resourceBundle, "there-are-no-content-sets"));

			String orderByCol = ParamUtil.getString(
				_httpServletRequest, "orderByCol", "create-date");

			String orderByType = ParamUtil.getString(
				_httpServletRequest, "orderByType", "asc");

			OrderByComparator<AssetListEntry> orderByComparator =
				AssetListPortletUtil.getAssetListEntryOrderByComparator(
					orderByCol, orderByType);

			searchContainer.setOrderByCol(orderByCol);
			searchContainer.setOrderByComparator(orderByComparator);
			searchContainer.setOrderByType(orderByType);

			String keywords = ParamUtil.getString(
				_httpServletRequest, "keywords");

			if (Validator.isNotNull(
					_infoListItemSelectorCriterion.getItemType())) {

				return _getFilteredSearchContainer(
					keywords, themeDisplay.getScopeGroupId(), searchContainer);
			}

			return _getSearchContainer(
				keywords, themeDisplay.getScopeGroupId(), searchContainer);
		}

		@Override
		public boolean isShowBreadcrumb() {
			return true;
		}

		@Override
		public boolean isShowManagementToolbar() {
			return true;
		}

		@Override
		public boolean isShowSearch() {
			return true;
		}

		private String _getClassName(AssetListEntry assetListEntry) {
			if (assetListEntry.getType() ==
					AssetListEntryTypeConstants.TYPE_MANUAL) {

				return AssetEntry.class.getName();
			}

			try {
				String className = StringPool.BLANK;

				List<AssetListEntrySegmentsEntryRel>
					assetListEntrySegmentsEntryRels =
						_assetListEntrySegmentsEntryRelLocalService.
							getAssetListEntrySegmentsEntryRels(
								assetListEntry.getAssetListEntryId(),
								QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (AssetListEntrySegmentsEntryRel
						assetListEntrySegmentsEntryRel :
							assetListEntrySegmentsEntryRels) {

					UnicodeProperties unicodeProperties =
						new UnicodeProperties();

					unicodeProperties.load(
						assetListEntrySegmentsEntryRel.getTypeSettings());

					long defaultClassNameId = GetterUtil.getLong(
						unicodeProperties.getProperty("anyAssetType", null));

					if (defaultClassNameId <= 0) {
						return AssetEntry.class.getName();
					}

					if (Validator.isNull(className)) {
						className = _portal.getClassName(defaultClassNameId);
					}
					else if (!Objects.equals(
								className,
								_portal.getClassName(defaultClassNameId))) {

						return AssetEntry.class.getName();
					}
				}

				return className;
			}
			catch (IOException ioException) {
				if (_log.isDebugEnabled()) {
					_log.debug(ioException, ioException);
				}
			}

			return AssetEntry.class.getName();
		}

		private SearchContainer _getFilteredSearchContainer(
				String keywords, long groupId, SearchContainer searchContainer)
			throws PortalException {

			List<AssetListEntry> assetListEntries = null;

			if (Validator.isNotNull(keywords)) {
				assetListEntries = _assetListEntryService.getAssetListEntries(
					PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId),
					keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					searchContainer.getOrderByComparator());
			}
			else {
				assetListEntries = _assetListEntryService.getAssetListEntries(
					PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					searchContainer.getOrderByComparator());
			}

			Stream<AssetListEntry> assetListEntriesStream =
				assetListEntries.stream();

			assetListEntries = assetListEntriesStream.filter(
				assetListEntry -> {
					if (Objects.equals(
							_infoListItemSelectorCriterion.getItemType(),
							_getClassName(assetListEntry))) {

						return true;
					}

					return false;
				}
			).collect(
				Collectors.toList()
			);

			searchContainer.setResults(
				ListUtil.subList(
					assetListEntries, searchContainer.getStart(),
					searchContainer.getEnd()));
			searchContainer.setTotal(assetListEntries.size());

			return searchContainer;
		}

		private SearchContainer _getSearchContainer(
				String keywords, long groupId, SearchContainer searchContainer)
			throws PortalException {

			List<AssetListEntry> assetListEntries = null;
			int assetListEntriesCount = 0;

			if (Validator.isNotNull(keywords)) {
				assetListEntries = _assetListEntryService.getAssetListEntries(
					PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId),
					keywords, searchContainer.getStart(),
					searchContainer.getEnd(),
					searchContainer.getOrderByComparator());

				assetListEntriesCount =
					_assetListEntryService.getAssetListEntriesCount(
						PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId),
						keywords);
			}
			else {
				assetListEntries = _assetListEntryService.getAssetListEntries(
					PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId),
					searchContainer.getStart(), searchContainer.getEnd(),
					searchContainer.getOrderByComparator());

				assetListEntriesCount =
					_assetListEntryService.getAssetListEntriesCount(
						PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId));
			}

			searchContainer.setResults(assetListEntries);
			searchContainer.setTotal(assetListEntriesCount);

			return searchContainer;
		}

		private final HttpServletRequest _httpServletRequest;
		private final InfoListItemSelectorCriterion
			_infoListItemSelectorCriterion;
		private final PortletURL _portletURL;

	}

}