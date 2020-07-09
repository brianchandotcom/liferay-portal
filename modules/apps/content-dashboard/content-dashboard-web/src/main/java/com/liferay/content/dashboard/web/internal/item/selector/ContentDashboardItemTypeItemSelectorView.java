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

package com.liferay.content.dashboard.web.internal.item.selector;

import com.liferay.content.dashboard.web.internal.item.selector.criteria.content.dashboard.type.criterion.ContentDashboardItemTypeItemSelectorCriterion;
import com.liferay.content.dashboard.web.internal.item.selector.provider.ContentDashboardItemTypeItemSelectorProvider;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = ItemSelectorView.class)
public class ContentDashboardItemTypeItemSelectorView
	implements ItemSelectorView<ContentDashboardItemTypeItemSelectorCriterion> {

	@Override
	public Class<ContentDashboardItemTypeItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return ContentDashboardItemTypeItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return ResourceBundleUtil.getString(resourceBundle, "subtype");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			ContentDashboardItemTypeItemSelectorCriterion
				contentDashboardItemTypeItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse,
			contentDashboardItemTypeItemSelectorCriterion, portletURL,
			itemSelectedEventName, search,
			new ContentDashboardItemTypeItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest, portletURL));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new UUIDItemSelectorReturnType());

	@Reference
	private ContentDashboardItemTypeItemSelectorProvider
		_contentDashboardItemTypeItemSelectorProvider;

	@Reference
	private ItemSelectorViewDescriptorRenderer
		<ContentDashboardItemTypeItemSelectorCriterion>
			_itemSelectorViewDescriptorRenderer;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

	private class ContentDashboardItemTypeItemDescriptor
		implements ItemSelectorViewDescriptor.ItemDescriptor {

		public ContentDashboardItemTypeItemDescriptor(
			ContentDashboardItemType contentDashboardItemType,
			HttpServletRequest httpServletRequest) {

			_contentDashboardItemType = contentDashboardItemType;

			_resourceBundle = ResourceBundleUtil.getBundle(
				httpServletRequest.getLocale(), getClass());
		}

		@Override
		public String getIcon() {
			return "books";
		}

		@Override
		public String getImageURL() {
			return null;
		}

		@Override
		public Date getModifiedDate() {
			return _contentDashboardItemType.getModifiedDate();
		}

		@Override
		public String getPayload() {
			return JSONUtil.put(
				"className", DDMStructure.class.getName()
			).put(
				"classNameId",
				_portal.getClassNameId(DDMStructure.class.getName())
			).put(
				"classPK", _contentDashboardItemType.getClassPK()
			).put(
				"title", getTitle(_resourceBundle.getLocale())
			).toString();
		}

		@Override
		public String getSubtitle(Locale locale) {
			return StringPool.BLANK;
		}

		@Override
		public String getTitle(Locale locale) {
			return _contentDashboardItemType.getFullLabel(locale);
		}

		@Override
		public long getUserId() {
			return _contentDashboardItemType.getUserId();
		}

		@Override
		public String getUserName() {
			return Optional.ofNullable(
				_userLocalService.fetchUser(
					_contentDashboardItemType.getUserId())
			).map(
				User::getFullName
			).orElse(
				StringPool.BLANK
			);
		}

		@Override
		public boolean isCompact() {
			return true;
		}

		private final ContentDashboardItemType _contentDashboardItemType;
		private final ResourceBundle _resourceBundle;

	}

	private class ContentDashboardItemTypeItemSelectorViewDescriptor
		implements ItemSelectorViewDescriptor<ContentDashboardItemType> {

		public ContentDashboardItemTypeItemSelectorViewDescriptor(
			HttpServletRequest httpServletRequest, PortletURL portletURL) {

			_httpServletRequest = httpServletRequest;
			_portletURL = portletURL;
		}

		@Override
		public ItemDescriptor getItemDescriptor(
			ContentDashboardItemType contentDashboardItemType) {

			return new ContentDashboardItemTypeItemDescriptor(
				contentDashboardItemType, _httpServletRequest);
		}

		@Override
		public ItemSelectorReturnType getItemSelectorReturnType() {
			return new UUIDItemSelectorReturnType();
		}

		@Override
		public String[] getOrderByKeys() {
			return new String[] {"title", "display-date"};
		}

		@Override
		public SearchContainer<ContentDashboardItemType> getSearchContainer() {
			PortletRequest portletRequest =
				(PortletRequest)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);

			return _contentDashboardItemTypeItemSelectorProvider.
				getSearchContainer(portletRequest, _portletURL);
		}

		@Override
		public boolean isShowBreadcrumb() {
			return false;
		}

		@Override
		public boolean isShowSearch() {
			return true;
		}

		private final HttpServletRequest _httpServletRequest;
		private final PortletURL _portletURL;

	}

}