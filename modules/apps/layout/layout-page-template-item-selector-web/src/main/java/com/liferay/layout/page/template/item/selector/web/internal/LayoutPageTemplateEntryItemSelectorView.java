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

package com.liferay.layout.page.template.item.selector.web.internal;

import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.item.selector.LayoutPageTemplateEntryItemSelectorReturnType;
import com.liferay.layout.page.template.item.selector.criterion.LayoutPageTemplateEntryItemSelectorCriterion;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateEntryNameComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

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
 * @author Lourdes Fernández Besada
 */
@Component(immediate = true, service = ItemSelectorView.class)
public class LayoutPageTemplateEntryItemSelectorView
	implements ItemSelectorView<LayoutPageTemplateEntryItemSelectorCriterion> {

	@Override
	public Class<LayoutPageTemplateEntryItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return LayoutPageTemplateEntryItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, LayoutPageTemplateEntryItemSelectorView.class);

		return ResourceBundleUtil.getString(resourceBundle, "page-template");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			LayoutPageTemplateEntryItemSelectorCriterion
				layoutPageTemplateEntryItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse,
			layoutPageTemplateEntryItemSelectorCriterion, portletURL,
			itemSelectedEventName, search,
			new LayoutPageTemplateEntryItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest,
				layoutPageTemplateEntryItemSelectorCriterion.getLayoutType(),
				portletURL));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateEntryItemSelectorView.class.getName());

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new LayoutPageTemplateEntryItemSelectorReturnType());

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private ItemSelectorViewDescriptorRenderer
		<LayoutPageTemplateEntryItemSelectorCriterion>
			_itemSelectorViewDescriptorRenderer;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.page.template.item.selector.web)"
	)
	private ServletContext _servletContext;

	private class LayoutPageTemplateEntryItemDescriptor
		implements ItemSelectorViewDescriptor.ItemDescriptor {

		public LayoutPageTemplateEntryItemDescriptor(
			HttpServletRequest httpServletRequest,
			LayoutPageTemplateEntry layoutPageTemplateEntry,
			ThemeDisplay themeDisplay) {

			_httpServletRequest = httpServletRequest;
			_layoutPageTemplateEntry = layoutPageTemplateEntry;
			_themeDisplay = themeDisplay;
		}

		@Override
		public String getIcon() {
			return "page";
		}

		@Override
		public String getImageURL() {
			return _layoutPageTemplateEntry.getImagePreviewURL(_themeDisplay);
		}

		@Override
		public Date getModifiedDate() {
			return _layoutPageTemplateEntry.getModifiedDate();
		}

		@Override
		public String getPayload() {
			return JSONUtil.put(
				"layoutPageTemplateEntryId",
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()
			).put(
				"name", _layoutPageTemplateEntry.getName()
			).put(
				"url",
				() -> {
					try {
						Layout layout = _layoutLocalService.getLayout(
							_layoutPageTemplateEntry.getPlid());

						return _portal.getLayoutFullURL(layout, _themeDisplay);
					}
					catch (PortalException portalException) {
						_log.error(
							portalException.getMessage(), portalException);
					}

					return StringPool.BLANK;
				}
			).put(
				"uuid", _layoutPageTemplateEntry.getUuid()
			).toString();
		}

		@Override
		public String getSubtitle(Locale locale) {
			if (Objects.equals(
					_layoutPageTemplateEntry.getType(),
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC)) {

				LayoutPageTemplateCollection layoutPageTemplateCollection =
					_layoutPageTemplateCollectionLocalService.
						fetchLayoutPageTemplateCollection(
							_layoutPageTemplateEntry.
								getLayoutPageTemplateCollectionId());

				if (layoutPageTemplateCollection == null) {
					return StringPool.BLANK;
				}

				return layoutPageTemplateCollection.getName();
			}
			else if (Objects.equals(
						_layoutPageTemplateEntry.getType(),
						LayoutPageTemplateEntryTypeConstants.
							TYPE_DISPLAY_PAGE)) {

				String typeLabel = _getTypeLabel();

				if (Validator.isNull(typeLabel)) {
					return StringPool.DASH;
				}

				String subtypeLabel = StringPool.BLANK;

				try {
					subtypeLabel = _getSubtypeLabel();
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception, exception);
					}
				}

				if (Validator.isNull(subtypeLabel)) {
					return typeLabel;
				}

				return typeLabel + " - " + subtypeLabel;
			}
			else if (Objects.equals(
						_layoutPageTemplateEntry.getType(),
						LayoutPageTemplateEntryTypeConstants.
							TYPE_MASTER_LAYOUT)) {

				int layoutsCount = _layoutLocalService.getMasterLayoutsCount(
					_layoutPageTemplateEntry.getGroupId(),
					_layoutPageTemplateEntry.getPlid());

				return LanguageUtil.format(
					_httpServletRequest, "x-usages", layoutsCount);
			}
			else if (Objects.equals(
						_layoutPageTemplateEntry.getType(),
						LayoutPageTemplateEntryTypeConstants.
							TYPE_WIDGET_PAGE)) {

				return LanguageUtil.get(
					_httpServletRequest, "widget-page-template");
			}

			return StringPool.BLANK;
		}

		@Override
		public String getTitle(Locale locale) {
			return HtmlUtil.escape(_layoutPageTemplateEntry.getName());
		}

		@Override
		public long getUserId() {
			return _layoutPageTemplateEntry.getUserId();
		}

		@Override
		public String getUserName() {
			return _layoutPageTemplateEntry.getUserName();
		}

		private String _getSubtypeLabel() {
			InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFormVariationsProvider.class,
					_layoutPageTemplateEntry.getClassName());

			if (infoItemFormVariationsProvider == null) {
				return StringPool.BLANK;
			}

			InfoItemFormVariation infoItemFormVariation =
				infoItemFormVariationsProvider.getInfoItemFormVariation(
					_layoutPageTemplateEntry.getGroupId(),
					String.valueOf(_layoutPageTemplateEntry.getClassTypeId()));

			if (infoItemFormVariation != null) {
				return infoItemFormVariation.getLabel(
					_themeDisplay.getLocale());
			}

			return StringPool.BLANK;
		}

		private String _getTypeLabel() {
			InfoItemDetailsProvider<?> infoItemDetailsProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemDetailsProvider.class,
					_layoutPageTemplateEntry.getClassName());

			if (infoItemDetailsProvider == null) {
				return StringPool.BLANK;
			}

			InfoItemClassDetails infoItemClassDetails =
				infoItemDetailsProvider.getInfoItemClassDetails();

			return infoItemClassDetails.getLabel(_themeDisplay.getLocale());
		}

		private final HttpServletRequest _httpServletRequest;
		private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
		private final ThemeDisplay _themeDisplay;

	}

	private class LayoutPageTemplateEntryItemSelectorViewDescriptor
		implements ItemSelectorViewDescriptor<LayoutPageTemplateEntry> {

		public LayoutPageTemplateEntryItemSelectorViewDescriptor(
			HttpServletRequest httpServletRequest, int layoutType,
			PortletURL portletURL) {

			_httpServletRequest = httpServletRequest;
			_layoutType = layoutType;
			_portletURL = portletURL;

			_portletRequest = (PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
			_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);
		}

		@Override
		public ItemDescriptor getItemDescriptor(
			LayoutPageTemplateEntry layoutPageTemplateEntry) {

			return new LayoutPageTemplateEntryItemSelectorView.
				LayoutPageTemplateEntryItemDescriptor(
					_httpServletRequest, layoutPageTemplateEntry,
					_themeDisplay);
		}

		@Override
		public ItemSelectorReturnType getItemSelectorReturnType() {
			return new LayoutPageTemplateEntryItemSelectorReturnType();
		}

		@Override
		public String[] getOrderByKeys() {
			return new String[] {"name"};
		}

		@Override
		public SearchContainer<LayoutPageTemplateEntry> getSearchContainer() {
			SearchContainer<LayoutPageTemplateEntry> searchContainer =
				new SearchContainer<>(
					_portletRequest, _portletURL, null,
					"no-entries-were-found");

			boolean orderByAsc = true;

			String orderByType = ParamUtil.getString(
				_httpServletRequest, "orderByType", "asc");

			if (orderByType.equals("desc")) {
				orderByAsc = false;
			}

			searchContainer.setOrderByComparator(
				new LayoutPageTemplateEntryNameComparator(orderByAsc));

			String orderByCol = ParamUtil.getString(
				_httpServletRequest, "orderByCol", "name");

			searchContainer.setOrderByCol(orderByCol);

			searchContainer.setOrderByType(orderByType);

			String keywords = ParamUtil.getString(
				_httpServletRequest, "keywords");

			if (Validator.isNull(keywords)) {
				searchContainer.setResults(
					_layoutPageTemplateEntryService.
						getLayoutPageTemplateEntries(
							_themeDisplay.getScopeGroupId(), _layoutType,
							searchContainer.getStart(),
							searchContainer.getEnd(),
							searchContainer.getOrderByComparator()));
				searchContainer.setTotal(
					_layoutPageTemplateEntryService.
						getLayoutPageTemplateEntriesCount(
							_themeDisplay.getScopeGroupId(), _layoutType));
			}
			else {
				searchContainer.setResults(
					_layoutPageTemplateEntryService.
						getLayoutPageTemplateEntries(
							_themeDisplay.getScopeGroupId(), keywords,
							_layoutType, WorkflowConstants.STATUS_ANY,
							searchContainer.getStart(),
							searchContainer.getEnd(),
							searchContainer.getOrderByComparator()));
				searchContainer.setTotal(
					_layoutPageTemplateEntryService.
						getLayoutPageTemplateEntriesCount(
							_themeDisplay.getScopeGroupId(), keywords,
							_layoutType, WorkflowConstants.STATUS_ANY));
			}

			return searchContainer;
		}

		@Override
		public boolean isShowBreadcrumb() {
			return false;
		}

		@Override
		public boolean isShowManagementToolbar() {
			return true;
		}

		@Override
		public boolean isShowSearch() {
			return true;
		}

		private final HttpServletRequest _httpServletRequest;
		private final int _layoutType;
		private final PortletRequest _portletRequest;
		private final PortletURL _portletURL;
		private final ThemeDisplay _themeDisplay;

	}

}