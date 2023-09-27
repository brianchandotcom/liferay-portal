/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.item.selector;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.web.internal.util.ObjectEntryUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Guilherme Camacho
 */
public class ObjectEntryItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<ObjectEntry> {

	public ObjectEntryItemSelectorViewDescriptor(
		HttpServletRequest httpServletRequest,
		InfoItemItemSelectorCriterion infoItemItemSelectorCriterion,
		ObjectDefinition objectDefinition,
		ObjectEntryManager objectEntryManager,
		ObjectRelatedModelsProviderRegistry objectRelatedModelsProviderRegistry,
		Portal portal, PortletURL portletURL) {

		_httpServletRequest = httpServletRequest;
		_infoItemItemSelectorCriterion = infoItemItemSelectorCriterion;
		_objectDefinition = objectDefinition;
		_objectEntryManager = objectEntryManager;
		_objectRelatedModelsProviderRegistry =
			objectRelatedModelsProviderRegistry;
		_portal = portal;
		_portletURL = portletURL;

		_portletRequest = (PortletRequest)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getDefaultDisplayStyle() {
		return "descriptive";
	}

	@Override
	public ItemDescriptor getItemDescriptor(ObjectEntry objectEntry) {
		return new ObjectEntryItemDescriptor(
			_httpServletRequest, _objectDefinition, objectEntry, _portal);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new InfoItemItemSelectorReturnType();
	}

	@Override
	public SearchContainer<ObjectEntry> getSearchContainer()
		throws PortalException {

		SearchContainer<ObjectEntry> searchContainer = new SearchContainer<>(
			_portletRequest, _portletURL, null, "no-entries-were-found");

		try {
			searchContainer.setResultsAndTotal(
				_getObjectEntries(
					ParamUtil.getLong(_portletRequest, "objectDefinitionId"),
					searchContainer.getCur(), searchContainer.getDelta()));
		}
		catch (Exception exception) {
			_log.error(exception);

			searchContainer.setResultsAndTotal(ArrayList::new, 0);
		}

		return searchContainer;
	}

	@Override
	public boolean isMultipleSelection() {
		return _infoItemItemSelectorCriterion.isMultiSelection();
	}

	@Override
	public boolean isShowBreadcrumb() {
		if (StringUtil.equals(
				_objectDefinition.getScope(),
				ObjectDefinitionConstants.SCOPE_SITE)) {

			return true;
		}

		return false;
	}

	private DTOConverterContext _getDTOConverterContext() {
		return new DefaultDTOConverterContext(
			false, null, null, _httpServletRequest, null,
			_themeDisplay.getLocale(), null, _themeDisplay.getUser());
	}

	private List<ObjectEntry> _getObjectEntries(
			long objectDefinitionId, int curPage, int pageSize)
		throws Exception {

		if (objectDefinitionId == 0) {
			Group scopeGroup = _themeDisplay.getScopeGroup();

			Page<com.liferay.object.rest.dto.v1_0.ObjectEntry> page =
				_objectEntryManager.getObjectEntries(
					_themeDisplay.getCompanyId(), _objectDefinition,
					scopeGroup.getGroupKey(), null, _getDTOConverterContext(),
					StringPool.BLANK, Pagination.of(curPage, pageSize), null,
					null);

			return TransformUtil.transform(
				page.getItems(),
				objectEntry -> ObjectEntryUtil.toObjectEntry(
					_objectDefinition.getObjectDefinitionId(), objectEntry));
		}

		ObjectRelatedModelsProvider objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				_objectDefinition.getClassName(),
				_objectDefinition.getCompanyId(),
				ParamUtil.getString(_portletRequest, "objectRelationshipType"));

		return objectRelatedModelsProvider.getUnrelatedModels(
			_objectDefinition.getCompanyId(),
			ParamUtil.getLong(_portletRequest, "groupId"), _objectDefinition,
			ParamUtil.getLong(_portletRequest, "objectEntryId"),
			ParamUtil.getLong(_portletRequest, "objectRelationshipId"));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryItemSelectorViewDescriptor.class);

	private final HttpServletRequest _httpServletRequest;
	private final InfoItemItemSelectorCriterion _infoItemItemSelectorCriterion;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryManager _objectEntryManager;
	private final ObjectRelatedModelsProviderRegistry
		_objectRelatedModelsProviderRegistry;
	private final Portal _portal;
	private final PortletRequest _portletRequest;
	private final PortletURL _portletURL;
	private final ThemeDisplay _themeDisplay;

}