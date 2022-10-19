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
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
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
		ObjectEntryManager objectEntryManager,
		ObjectRelatedModelsProviderRegistry objectRelatedModelsProviderRegistry,
		ObjectScopeProviderRegistry objectScopeProviderRegistry,
		Portal portal) {

		_itemSelectorViewDescriptorRenderer =
			itemSelectorViewDescriptorRenderer;
		_objectDefinition = objectDefinition;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_objectEntryManager = objectEntryManager;
		_objectRelatedModelsProviderRegistry =
			objectRelatedModelsProviderRegistry;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
		_portal = portal;
	}

	@Override
	public String getClassName() {
		return _objectDefinition.getClassName();
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
				_objectEntryManager, _objectRelatedModelsProviderRegistry,
				portletURL));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryItemSelectorView.class);

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Arrays.asList(
			new InfoItemItemSelectorReturnType(),
			new ObjectEntryItemSelectorReturnType());

	private final ItemSelectorViewDescriptorRenderer
		<InfoItemItemSelectorCriterion> _itemSelectorViewDescriptorRenderer;
	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectEntryManager _objectEntryManager;
	private final ObjectRelatedModelsProviderRegistry
		_objectRelatedModelsProviderRegistry;
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
				"className", _objectDefinition.getClassName()
			).put(
				"classNameId",
				_portal.getClassNameId(_objectDefinition.getClassName())
			).put(
				"classPK", _objectEntry.getObjectEntryId()
			).put(
				"title",
				StringBundler.concat(
					_objectDefinition.getLabel(themeDisplay.getLocale()),
					StringPool.SPACE, _objectEntry.getObjectEntryId())
			).toString();
		}

		@Override
		public String getSubtitle(Locale locale) {
			return String.valueOf(_objectEntry.getObjectEntryId());
		}

		@Override
		public String getTitle(Locale locale) {
			try {
				return _objectEntry.getTitleValue();
			}
			catch (PortalException portalException) {
				throw new RuntimeException(portalException);
			}
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
			ObjectEntryManager objectEntryManager,
			ObjectRelatedModelsProviderRegistry
				objectRelatedModelsProviderRegistry,
			PortletURL portletURL) {

			_httpServletRequest = httpServletRequest;
			_objectDefinition = objectDefinition;
			_objectEntryManager = objectEntryManager;
			_objectRelatedModelsProviderRegistry =
				objectRelatedModelsProviderRegistry;
			_portletURL = portletURL;

			_portletRequest = (PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
			_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);
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

			try {
				searchContainer.setResultsAndTotal(
					_getObjectEntries(
						ParamUtil.getLong(
							_portletRequest, "objectDefinitionId")));
			}
			catch (Exception exception) {
				_log.error(exception);

				searchContainer.setResultsAndTotal(ArrayList::new, 0);
			}

			return searchContainer;
		}

		private DTOConverterContext _getDTOConverterContext() {
			return new DefaultDTOConverterContext(
				false, null, null, _httpServletRequest, null,
				_themeDisplay.getLocale(), null, _themeDisplay.getUser());
		}

		private List<ObjectEntry> _getObjectEntries(long objectDefinitionId)
			throws Exception {

			if (objectDefinitionId == 0) {
				Group scopeGroup = _themeDisplay.getScopeGroup();

				Page<com.liferay.object.rest.dto.v1_0.ObjectEntry> page =
					_objectEntryManager.getObjectEntries(
						_themeDisplay.getCompanyId(), _objectDefinition,
						scopeGroup.getGroupKey(), null,
						_getDTOConverterContext(), StringPool.BLANK, null, null,
						null);

				return TransformUtil.transform(
					page.getItems(),
					objectEntry -> _toObjectEntry(
						_objectDefinition.getObjectDefinitionId(),
						objectEntry));
			}

			ObjectRelatedModelsProvider objectRelatedModelsProvider =
				_objectRelatedModelsProviderRegistry.
					getObjectRelatedModelsProvider(
						_objectDefinition.getClassName(),
						ParamUtil.getString(
							_portletRequest, "objectRelationshipType"));

			return objectRelatedModelsProvider.getUnrelatedModels(
				_objectDefinition.getCompanyId(),
				ParamUtil.getLong(_portletRequest, "groupId"),
				_objectDefinition,
				ParamUtil.getLong(_portletRequest, "objectEntryId"),
				ParamUtil.getLong(_portletRequest, "objectRelationshipId"));
		}

		private ObjectEntry _toObjectEntry(
			long objectDefinitionId,
			com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry) {

			ObjectEntry serviceBuilderObjectEntry =
				_objectEntryLocalService.createObjectEntry(objectEntry.getId());

			serviceBuilderObjectEntry.setObjectDefinitionId(objectDefinitionId);

			return serviceBuilderObjectEntry;
		}

		private final HttpServletRequest _httpServletRequest;
		private final ObjectDefinition _objectDefinition;
		private final ObjectEntryManager _objectEntryManager;
		private final ObjectRelatedModelsProviderRegistry
			_objectRelatedModelsProviderRegistry;
		private final PortletRequest _portletRequest;
		private final PortletURL _portletURL;
		private final ThemeDisplay _themeDisplay;

	}

}