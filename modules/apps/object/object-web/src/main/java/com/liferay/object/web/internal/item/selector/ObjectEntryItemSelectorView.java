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

package com.liferay.object.web.internal.item.selector;

import com.liferay.info.item.selector.InfoItemSelectorView;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Guilherme Camacho
 */
public class ObjectEntryItemSelectorView
	implements InfoItemSelectorView,
			   ItemSelectorView<InfoItemItemSelectorCriterion> {

	public ObjectEntryItemSelectorView(
		ItemSelectorViewDescriptorRenderer<InfoItemItemSelectorCriterion>
			itemSelectorViewDescriptorRenderer,
		ObjectDefinition objectDefinition,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectScopeProviderRegistry objectScopeProviderRegistry,
		Portal portal) {

		_itemSelectorViewDescriptorRenderer =
			itemSelectorViewDescriptorRenderer;
		_objectDefinition = objectDefinition;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
		_portal = portal;
	}

	@Override
	public String getClassName() {
		return ObjectEntry.class.getName();
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
		return _objectDefinition.getPluralLabel(locale);
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
			new ObjectItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest, _objectDefinition,
				_objectScopeProviderRegistry, portletURL));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new InfoItemItemSelectorReturnType());

	private final ItemSelectorViewDescriptorRenderer
		<InfoItemItemSelectorCriterion> _itemSelectorViewDescriptorRenderer;
	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;
	private final Portal _portal;

	private class ObjectEntryItemDescriptor
		implements ItemSelectorViewDescriptor.ItemDescriptor {

		public ObjectEntryItemDescriptor(
			ObjectEntry objectEntry, HttpServletRequest httpServletRequest) {

			_objectEntry = objectEntry;
			_httpServletRequest = httpServletRequest;

			try {
				_objectDefinition =
					_objectDefinitionLocalService.getObjectDefinition(
						objectEntry.getObjectDefinitionId());
			}
			catch (PortalException portalException) {
				throw new RuntimeException(portalException);
			}
		}

		@Override
		public String getIcon() {
			return null;
		}

		@Override
		public String getImageURL() {
			return null;
		}

		@Override
		public Date getModifiedDate() {
			return _objectEntry.getModifiedDate();
		}

		@Override
		public String getPayload() {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return JSONUtil.put(
				"className", ObjectEntry.class.getName()
			).put(
				"classNameId",
				_portal.getClassNameId(ObjectEntry.class.getName())
			).put(
				"classPK", _objectEntry.getObjectEntryId()
			).put(
				"classTypeId", _objectEntry.getObjectDefinitionId()
			).put(
				"subtype", _objectDefinition.getLabel(themeDisplay.getLocale())
			).put(
				"title",
				StringBundler.concat(
					_objectDefinition.getLabel(themeDisplay.getLocale()),
					StringPool.SPACE, _objectEntry.getObjectEntryId())
			).toString();
		}

		@Override
		public String getSubtitle(Locale locale) {
			return _objectDefinition.getLabel(locale);
		}

		@Override
		public String getTitle(Locale locale) {
			return String.valueOf(_objectEntry.getObjectEntryId());
		}

		@Override
		public long getUserId() {
			return _objectEntry.getUserId();
		}

		@Override
		public String getUserName() {
			return _objectEntry.getUserName();
		}

		private HttpServletRequest _httpServletRequest;
		private final ObjectDefinition _objectDefinition;
		private final ObjectEntry _objectEntry;

	}

	private class ObjectItemSelectorViewDescriptor
		implements ItemSelectorViewDescriptor<ObjectEntry> {

		public ObjectItemSelectorViewDescriptor(
			HttpServletRequest httpServletRequest,
			ObjectDefinition objectDefinition,
			ObjectScopeProviderRegistry objectScopeProviderRegistry,
			PortletURL portletURL) {

			_httpServletRequest = httpServletRequest;
			_objectDefinition = objectDefinition;
			_objectScopeProviderRegistry = objectScopeProviderRegistry;
			_portletURL = portletURL;

			_portletRequest = (PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		}

		@Override
		public String getDefaultDisplayStyle() {
			return "descriptive";
		}

		@Override
		public ItemDescriptor getItemDescriptor(ObjectEntry objectEntry) {
			return new ObjectEntryItemDescriptor(
				objectEntry, _httpServletRequest);
		}

		@Override
		public ItemSelectorReturnType getItemSelectorReturnType() {
			return new InfoItemItemSelectorReturnType();
		}

		@Override
		public SearchContainer<ObjectEntry> getSearchContainer()
			throws PortalException {

			SearchContainer<ObjectEntry> searchContainer =
				new SearchContainer<>(
					_portletRequest, _portletURL, null,
					"no-entries-were-found");

			List<ObjectEntry> objectEntries =
				_objectEntryLocalService.getObjectEntries(
					_getGroupId(), _objectDefinition.getObjectDefinitionId(),
					searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(objectEntries);

			searchContainer.setTotal(
				_objectEntryLocalService.getObjectEntriesCount());

			return searchContainer;
		}

		private long _getGroupId() throws PortalException {
			ObjectScopeProvider objectScopeProvider =
				_objectScopeProviderRegistry.getObjectScopeProvider(
					_objectDefinition.getScope());

			if (!objectScopeProvider.isGroupAware()) {
				return 0;
			}

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			return objectScopeProvider.getGroupId(serviceContext.getRequest());
		}

		private final HttpServletRequest _httpServletRequest;
		private final ObjectDefinition _objectDefinition;
		private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;
		private final PortletRequest _portletRequest;
		private final PortletURL _portletURL;

	}

}