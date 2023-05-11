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

package com.liferay.object.internal.uad.display;

import com.liferay.object.internal.uad.constants.ObjectUADConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.user.associated.data.display.BaseModelUADDisplay;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletRequest;

/**
 * @author Carolina Barbosa
 */
public class ObjectEntryUADDisplay extends BaseModelUADDisplay<ObjectEntry> {

	public ObjectEntryUADDisplay(
		GroupLocalService groupLocalService, ObjectDefinition objectDefinition,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectScopeProviderRegistry objectScopeProviderRegistry,
		Portal portal) {

		_groupLocalService = groupLocalService;
		_objectDefinition = objectDefinition;
		_objectEntryLocalService = objectEntryLocalService;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
		_portal = portal;
	}

	@Override
	public ObjectEntry get(Serializable primaryKey) throws Exception {
		return _objectEntryLocalService.getObjectEntry(
			Long.valueOf(primaryKey.toString()));
	}

	@Override
	public String[] getDisplayFieldNames() {
		return new String[] {"externalReferenceCode", "objectEntryId"};
	}

	@Override
	public String getEditURL(
		ObjectEntry objectEntry, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				liferayPortletRequest,
				_groupLocalService.fetchGroup(objectEntry.getGroupId()),
				_objectDefinition.getPortletId(), 0, 0,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/object_entries/edit_object_entry"
		).setBackURL(
			_portal.getCurrentURL(liferayPortletRequest)
		).setParameter(
			"externalReferenceCode", objectEntry.getExternalReferenceCode()
		).buildString();
	}

	@Override
	public Class<ObjectEntry> getTypeClass() {
		return ObjectEntry.class;
	}

	@Override
	public String getTypeKey() {
		return _objectDefinition.getClassName();
	}

	@Override
	public String getTypeName(Locale locale) {
		return _objectDefinition.getShortName();
	}

	@Override
	public boolean isSiteScoped() {
		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				_objectDefinition.getScope());

		if (objectScopeProvider.isGroupAware()) {
			return true;
		}

		return false;
	}

	@Override
	protected long doCount(DynamicQuery dynamicQuery) {
		return _objectEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	protected DynamicQuery doGetDynamicQuery() {
		DynamicQuery dynamicQuery = _objectEntryLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"objectDefinitionId",
				_objectDefinition.getObjectDefinitionId()));

		return dynamicQuery;
	}

	@Override
	protected List<ObjectEntry> doGetRange(
		DynamicQuery dynamicQuery, int start, int end) {

		return _objectEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return ObjectUADConstants.USER_ID_FIELD_NAMES_OBJECT_ENTRY;
	}

	@Override
	protected DynamicQuery getSearchDynamicQuery(
		long userId, long[] groupIds, String keywords, String orderByField,
		String orderByType) {

		DynamicQuery dynamicQuery = getDynamicQuery(userId);

		if (ArrayUtil.isNotEmpty(groupIds)) {
			dynamicQuery.add(
				RestrictionsFactoryUtil.in(
					"groupId", ArrayUtil.toLongArray(groupIds)));
		}
		else {
			dynamicQuery.add(RestrictionsFactoryUtil.eq("groupId", 0L));
		}

		if (Validator.isNotNull(keywords)) {
			dynamicQuery.add(
				RestrictionsFactoryUtil.or(
					RestrictionsFactoryUtil.ilike(
						"externalReferenceCode",
						StringUtil.quote(keywords, CharPool.PERCENT)),
					RestrictionsFactoryUtil.eq(
						"objectEntryId", GetterUtil.getLong(keywords))));
		}

		if (orderByField != null) {
			if (Objects.equals(orderByType, "desc")) {
				dynamicQuery.addOrder(OrderFactoryUtil.desc(orderByField));
			}
			else {
				dynamicQuery.addOrder(OrderFactoryUtil.asc(orderByField));
			}
		}

		return dynamicQuery;
	}

	private final GroupLocalService _groupLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;
	private final Portal _portal;

}