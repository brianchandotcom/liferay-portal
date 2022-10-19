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

package com.liferay.object.web.internal.object.entries.display.context;

import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.model.FDSSortItemBuilder;
import com.liferay.frontend.data.set.model.FDSSortItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectView;
import com.liferay.object.model.ObjectViewSortColumn;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectViewLocalService;
import com.liferay.object.web.internal.constants.ObjectWebKeys;
import com.liferay.object.web.internal.display.context.helper.ObjectRequestHelper;
import com.liferay.object.web.internal.object.entries.frontend.data.set.filter.factory.ObjectFieldFDSFilterFactory;
import com.liferay.object.web.internal.object.entries.frontend.data.set.filter.factory.ObjectFieldFDSFilterFactoryTracker;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class ViewObjectEntriesDisplayContext {

	public ViewObjectEntriesDisplayContext(
		HttpServletRequest httpServletRequest,
		ObjectFieldFDSFilterFactoryTracker objectFieldFDSFilterFactoryTracker,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectScopeProvider objectScopeProvider,
		ObjectViewLocalService objectViewLocalService,
		PortletResourcePermission portletResourcePermission,
		String restContextPath) {

		_httpServletRequest = httpServletRequest;
		_objectFieldFDSFilterFactoryTracker =
			objectFieldFDSFilterFactoryTracker;
		_objectFieldLocalService = objectFieldLocalService;
		_objectScopeProvider = objectScopeProvider;
		_objectViewLocalService = objectViewLocalService;
		_portletResourcePermission = portletResourcePermission;

		_apiURL = _getAPIURL(restContextPath);
		_objectRequestHelper = new ObjectRequestHelper(httpServletRequest);
	}

	public String getAPIURL() {
		return _apiURL + _getQueryString();
	}

	public CreationMenu getCreationMenu() throws Exception {
		CreationMenu creationMenu = new CreationMenu();

		if (!_portletResourcePermission.contains(
				_objectRequestHelper.getPermissionChecker(),
				_objectScopeProvider.getGroupId(
					_objectRequestHelper.getRequest()),
				ObjectActionKeys.ADD_OBJECT_ENTRY)) {

			return creationMenu;
		}

		ObjectDefinition objectDefinition = getObjectDefinition();

		creationMenu.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.create(
						getPortletURL()
					).setMVCRenderCommandName(
						"/object_entries/edit_object_entry"
					).buildString());
				dropdownItem.setLabel(
					LanguageUtil.format(
						_objectRequestHelper.getRequest(), "add-x",
						objectDefinition.getLabel(
							_objectRequestHelper.getLocale())));
			});

		return creationMenu;
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws Exception {

		return Arrays.asList(
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					getPortletURL()
				).setMVCRenderCommandName(
					"/object_entries/edit_object_entry"
				).setParameter(
					"externalReferenceCode", "{externalReferenceCode}"
				).buildString(),
				"view", "view",
				LanguageUtil.get(_objectRequestHelper.getRequest(), "view"),
				"get", null, null),
			new FDSActionDropdownItem(
				LanguageUtil.get(
					_objectRequestHelper.getRequest(),
					"are-you-sure-you-want-to-delete-this-entry"),
				_apiURL + "/by-external-reference-code/{externalReferenceCode}",
				"trash", "delete",
				LanguageUtil.get(_objectRequestHelper.getRequest(), "delete"),
				"delete", "delete", "async"),
			new FDSActionDropdownItem(
				_getPermissionsURL(), null, "permissions",
				LanguageUtil.get(
					_objectRequestHelper.getRequest(), "permissions"),
				"get", "permissions", "modal-permissions"));
	}

	public List<FDSFilter> getFDSFilters() {
		ObjectView objectView = _objectViewLocalService.fetchDefaultObjectView(
			_objectDefinition.getObjectDefinitionId());

		if (objectView == null) {
			return Collections.emptyList();
		}

		return TransformUtil.transform(
			objectView.getObjectViewFilterColumns(),
			objectViewFilterColumn -> {
				ObjectFieldFDSFilterFactory objectFieldFDSFilterFactory =
					_objectFieldFDSFilterFactoryTracker.
						getObjectFieldFDSFilterFactory(
							objectView.getObjectDefinitionId(),
							objectViewFilterColumn);

				return objectFieldFDSFilterFactory.create(
					_objectRequestHelper.getLocale(),
					_objectDefinition.getObjectDefinitionId(),
					objectViewFilterColumn);
			});
	}

	public String getFDSId() {
		return _objectRequestHelper.getPortletId();
	}

	public FDSSortItemList getFDSSortItemList() {
		ObjectView objectView = _objectViewLocalService.fetchDefaultObjectView(
			_objectDefinition.getObjectDefinitionId());

		FDSSortItemList fdsSortItemList = new FDSSortItemList();

		if (objectView == null) {
			return fdsSortItemList;
		}

		for (ObjectViewSortColumn objectViewSortColumn :
				objectView.getObjectViewSortColumns()) {

			fdsSortItemList.add(
				FDSSortItemBuilder.setDirection(
					objectViewSortColumn.getSortOrder()
				).setKey(
					objectViewSortColumn.getObjectFieldName()
				).build());
		}

		return fdsSortItemList;
	}

	public ObjectDefinition getObjectDefinition() {
		if (_objectDefinition != null) {
			return _objectDefinition;
		}

		HttpServletRequest httpServletRequest =
			_objectRequestHelper.getRequest();

		_objectDefinition = (ObjectDefinition)httpServletRequest.getAttribute(
			ObjectWebKeys.OBJECT_DEFINITION);

		return _objectDefinition;
	}

	public PortletURL getPortletURL() throws PortletException {
		return PortletURLUtil.clone(
			PortletURLUtil.getCurrent(
				_objectRequestHelper.getLiferayPortletRequest(),
				_objectRequestHelper.getLiferayPortletResponse()),
			_objectRequestHelper.getLiferayPortletResponse());
	}

	private String _getAPIURL(String restContextPath) {
		String apiURL = "/o" + restContextPath;

		try {
			long groupId = _objectScopeProvider.getGroupId(_httpServletRequest);

			if (!_objectScopeProvider.isGroupAware() ||
				!_objectScopeProvider.isValidGroupId(groupId)) {

				return apiURL;
			}

			return StringBundler.concat(apiURL, "/scopes/", groupId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return apiURL;
		}
	}

	private String _getNestedFieldsQueryString() {
		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				_objectDefinition.getObjectDefinitionId());

		Stream<ObjectField> stream = objectFields.stream();

		String queryString = stream.filter(
			objectField -> Objects.equals(
				objectField.getRelationshipType(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY)
		).map(
			objectField -> {
				String fieldName = objectField.getName();

				return StringUtil.replaceLast(
					fieldName.substring(
						fieldName.lastIndexOf(StringPool.UNDERLINE) + 1),
					"Id", "");
			}
		).distinct(
		).collect(
			Collectors.joining(StringPool.COMMA)
		);

		if (Validator.isNull(queryString)) {
			return StringPool.BLANK;
		}

		return "nestedFields=" + queryString;
	}

	private String _getPermissionsURL() throws Exception {
		ObjectDefinition objectDefinition = getObjectDefinition();

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_objectRequestHelper.getRequest(),
				"com_liferay_portlet_configuration_web_portlet_" +
					"PortletConfigurationPortlet",
				ActionRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_permissions.jsp"
		).setRedirect(
			_objectRequestHelper.getCurrentURL()
		).setParameter(
			"modelResource", objectDefinition.getClassName()
		).setParameter(
			"modelResourceDescription",
			objectDefinition.getLabel(_objectRequestHelper.getLocale())
		).setParameter(
			"resourcePrimKey", "{id}"
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	private String _getQueryString() {
		List<String> queryStrings = new ArrayList<>();

		String nestedFieldsQueryString = _getNestedFieldsQueryString();

		if (Validator.isNotNull(nestedFieldsQueryString)) {
			queryStrings.add(nestedFieldsQueryString);
		}

		String searchByObjectViewQueryString =
			_getSearchByObjectViewQueryString();

		if (Validator.isNotNull(searchByObjectViewQueryString)) {
			queryStrings.add(searchByObjectViewQueryString);
		}

		if (ListUtil.isEmpty(queryStrings)) {
			return StringPool.BLANK;
		}

		return StringPool.QUESTION +
			StringUtil.merge(queryStrings, StringPool.AMPERSAND);
	}

	private String _getSearchByObjectViewQueryString() {
		ObjectView objectView = _objectViewLocalService.fetchDefaultObjectView(
			_objectDefinition.getObjectDefinitionId());

		if (objectView == null) {
			return StringPool.BLANK;
		}

		return "searchByObjectView";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewObjectEntriesDisplayContext.class);

	private final String _apiURL;
	private final HttpServletRequest _httpServletRequest;
	private ObjectDefinition _objectDefinition;
	private final ObjectFieldFDSFilterFactoryTracker
		_objectFieldFDSFilterFactoryTracker;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRequestHelper _objectRequestHelper;
	private final ObjectScopeProvider _objectScopeProvider;
	private final ObjectViewLocalService _objectViewLocalService;
	private final PortletResourcePermission _portletResourcePermission;

}