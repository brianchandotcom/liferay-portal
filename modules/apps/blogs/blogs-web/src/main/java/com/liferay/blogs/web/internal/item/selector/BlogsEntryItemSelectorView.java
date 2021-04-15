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

package com.liferay.blogs.web.internal.item.selector;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.blogs.web.internal.util.BlogsEntryUtil;
import com.liferay.blogs.web.internal.util.BlogsUtil;
import com.liferay.info.item.selector.InfoItemSelectorView;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
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
public class BlogsEntryItemSelectorView
	implements InfoItemSelectorView,
			   ItemSelectorView<InfoItemItemSelectorCriterion> {

	@Override
	public String getClassName() {
		return BlogsEntry.class.getName();
	}

	@Override
	public Class<InfoItemItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return InfoItemItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "blogs");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			InfoItemItemSelectorCriterion infoItemItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse, infoItemItemSelectorCriterion,
			portletURL, itemSelectedEventName, search,
			new BlogsItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest, portletURL));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new InfoItemItemSelectorReturnType());

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private ItemSelectorViewDescriptorRenderer<InfoItemItemSelectorCriterion>
		_itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	private class BlogsEntryItemDescriptor
		implements ItemSelectorViewDescriptor.ItemDescriptor {

		public BlogsEntryItemDescriptor(
			BlogsEntry blogsEntry, HttpServletRequest httpServletRequest) {

			_blogsEntry = blogsEntry;
			_httpServletRequest = httpServletRequest;

			_resourceBundle = ResourceBundleUtil.getBundle(
				httpServletRequest.getLocale(), getClass());
		}

		@Override
		public String getIcon() {
			return "blogs";
		}

		@Override
		public String getImageURL() {
			try {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				String coverImageURL = _blogsEntry.getCoverImageURL(
					themeDisplay);

				if (Validator.isNull(coverImageURL)) {
					return _blogsEntry.getSmallImageURL(themeDisplay);
				}

				return coverImageURL;
			}
			catch (PortalException portalException) {
				return ReflectionUtil.throwException(portalException);
			}
		}

		@Override
		public Date getModifiedDate() {
			return _blogsEntry.getModifiedDate();
		}

		@Override
		public String getPayload() {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return JSONUtil.put(
				"className", BlogsEntry.class.getName()
			).put(
				"classNameId",
				_portal.getClassNameId(BlogsEntry.class.getName())
			).put(
				"classPK", _blogsEntry.getEntryId()
			).put(
				"title",
				BlogsEntryUtil.getDisplayTitle(_resourceBundle, _blogsEntry)
			).put(
				"type",
				ResourceActionsUtil.getModelResource(
					themeDisplay.getLocale(), BlogsEntry.class.getName())
			).toString();
		}

		@Override
		public String getSubtitle(Locale locale) {
			Date modifiedDate = _blogsEntry.getModifiedDate();

			String modifiedDateDescription = _language.getTimeDescription(
				locale, System.currentTimeMillis() - modifiedDate.getTime(),
				true);

			return _language.format(
				locale, "x-ago-by-x",
				new Object[] {
					modifiedDateDescription,
					HtmlUtil.escape(_blogsEntry.getUserName())
				});
		}

		@Override
		public String getTitle(Locale locale) {
			return BlogsEntryUtil.getDisplayTitle(_resourceBundle, _blogsEntry);
		}

		@Override
		public long getUserId() {
			return _blogsEntry.getUserId();
		}

		@Override
		public String getUserName() {
			return _blogsEntry.getUserName();
		}

		private final BlogsEntry _blogsEntry;
		private HttpServletRequest _httpServletRequest;
		private final ResourceBundle _resourceBundle;

	}

	private class BlogsItemSelectorViewDescriptor
		implements ItemSelectorViewDescriptor<BlogsEntry> {

		public BlogsItemSelectorViewDescriptor(
			HttpServletRequest httpServletRequest, PortletURL portletURL) {

			_httpServletRequest = httpServletRequest;
			_portletURL = portletURL;
		}

		@Override
		public ItemSelectorViewDescriptor.ItemDescriptor getItemDescriptor(
			BlogsEntry blogsEntry) {

			return new BlogsEntryItemDescriptor(
				blogsEntry, _httpServletRequest);
		}

		@Override
		public ItemSelectorReturnType getItemSelectorReturnType() {
			return new InfoItemItemSelectorReturnType();
		}

		@Override
		public String[] getOrderByKeys() {
			return new String[] {"title", "display-date"};
		}

		@Override
		public SearchContainer<BlogsEntry> getSearchContainer() {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			SearchContainer<BlogsEntry> entriesSearchContainer =
				new SearchContainer<>(
					(PortletRequest)_httpServletRequest.getAttribute(
						JavaConstants.JAVAX_PORTLET_REQUEST),
					_portletURL, null, "no-entries-were-found");

			String orderByCol = ParamUtil.getString(
				_httpServletRequest, "orderByCol", "title");

			entriesSearchContainer.setOrderByCol(orderByCol);

			String orderByType = ParamUtil.getString(
				_httpServletRequest, "orderByType", "asc");

			entriesSearchContainer.setOrderByType(orderByType);

			entriesSearchContainer.setOrderByComparator(
				BlogsUtil.getOrderByComparator(
					entriesSearchContainer.getOrderByCol(),
					entriesSearchContainer.getOrderByType()));

			entriesSearchContainer.setTotal(
				_blogsEntryService.getGroupEntriesCount(
					themeDisplay.getScopeGroupId(),
					WorkflowConstants.STATUS_APPROVED));

			List<BlogsEntry> entriesResults =
				_blogsEntryService.getGroupEntries(
					themeDisplay.getScopeGroupId(),
					WorkflowConstants.STATUS_APPROVED,
					entriesSearchContainer.getStart(),
					entriesSearchContainer.getEnd(),
					entriesSearchContainer.getOrderByComparator());

			entriesSearchContainer.setResults(entriesResults);

			return entriesSearchContainer;
		}

		private HttpServletRequest _httpServletRequest;
		private final PortletURL _portletURL;

	}

}