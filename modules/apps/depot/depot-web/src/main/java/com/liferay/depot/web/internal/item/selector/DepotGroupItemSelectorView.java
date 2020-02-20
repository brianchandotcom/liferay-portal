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

package com.liferay.depot.web.internal.item.selector;

import com.liferay.depot.web.internal.item.selector.criteria.depot.group.criterion.DepotGroupItemSelectorCriterion;
import com.liferay.depot.web.internal.util.DepotAdminGroupSearchProvider;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.criteria.GroupItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
 * @author Eudaldo Alonso
 */
@Component(
	property = "item.selector.view.order:Integer=200",
	service = ItemSelectorView.class
)
public class DepotGroupItemSelectorView
	implements ItemSelectorView<DepotGroupItemSelectorCriterion> {

	@Override
	public Class<DepotGroupItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return DepotGroupItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return ResourceBundleUtil.getString(
			_portal.getResourceBundle(locale), "repositories");
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		return true;
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			DepotGroupItemSelectorCriterion depotGroupItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse, depotGroupItemSelectorCriterion,
			portletURL, itemSelectedEventName, search,
			new DepotGroupSelectorViewDescriptor(
				(HttpServletRequest)servletRequest, portletURL));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DepotGroupItemSelectorView.class);

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new GroupItemSelectorReturnType());

	@Reference
	private DepotAdminGroupSearchProvider _depotAdminGroupSearchProvider;

	@Reference
	private ItemSelectorViewDescriptorRenderer<DepotGroupItemSelectorCriterion>
		_itemSelectorViewDescriptorRenderer;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

	private class DepotGroupItemDescriptor
		implements ItemSelectorViewDescriptor.ItemDescriptor {

		public DepotGroupItemDescriptor(
			Group group, HttpServletRequest httpServletRequest) {

			_group = group;
			_httpServletRequest = httpServletRequest;

			_resourceBundle = ResourceBundleUtil.getBundle(
				httpServletRequest.getLocale(), getClass());
		}

		@Override
		public String getIcon() {
			return "repositories";
		}

		@Override
		public String getImageURL() {
			return null;
		}

		@Override
		public Date getModifiedDate() {
			return null;
		}

		@Override
		public String getPayload() {
			return JSONUtil.put(
				"className", Group.class.getName()
			).put(
				"classNameId", _portal.getClassNameId(Group.class.getName())
			).put(
				"classPK", _group.getGroupId()
			).put(
				"title", _getTitle(_resourceBundle.getLocale())
			).toString();
		}

		@Override
		public String getSubtitle(Locale locale) {
			return StringPool.BLANK;
		}

		@Override
		public String getTitle(Locale locale) {
			return _getTitle(locale);
		}

		@Override
		public long getUserId() {
			return _group.getCreatorUserId();
		}

		@Override
		public String getUserName() {
			try {
				User user = _userLocalService.getUser(
					_group.getCreatorUserId());

				return user.getFullName();
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(portalException, portalException);
				}
			}

			return StringPool.BLANK;
		}

		private String _getTitle(Locale locale) {
			try {
				return HtmlUtil.escape(_group.getDescriptiveName(locale));
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}

			return HtmlUtil.escape(_group.getName(locale));
		}

		private final Group _group;
		private HttpServletRequest _httpServletRequest;
		private final ResourceBundle _resourceBundle;

	}

	private class DepotGroupSelectorViewDescriptor
		implements ItemSelectorViewDescriptor<Group> {

		public DepotGroupSelectorViewDescriptor(
			HttpServletRequest httpServletRequest, PortletURL portletURL) {

			_httpServletRequest = httpServletRequest;
			_portletURL = portletURL;
		}

		@Override
		public ItemDescriptor getItemDescriptor(Group group) {
			return new DepotGroupItemDescriptor(group, _httpServletRequest);
		}

		@Override
		public ItemSelectorReturnType getItemSelectorReturnType() {
			return new GroupItemSelectorReturnType();
		}

		@Override
		public String[] getOrderByKeys() {
			return new String[] {"title", "display-date"};
		}

		@Override
		public SearchContainer getSearchContainer() {
			PortletRequest portletRequest =
				(PortletRequest)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);

			return _depotAdminGroupSearchProvider.getGroupSearch(
				portletRequest, _portletURL);
		}

		private HttpServletRequest _httpServletRequest;
		private final PortletURL _portletURL;

	}

}