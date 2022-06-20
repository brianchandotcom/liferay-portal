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

package com.liferay.object.web.internal.info.item.creator;

import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.info.exception.InfoFormException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.creator.InfoItemCreator;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.util.GroupUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 */
public class ObjectEntryInfoItemCreator
	implements InfoItemCreator<ObjectEntry> {

	public ObjectEntryInfoItemCreator(
		DepotEntryLocalService depotEntryLocalService,
		GroupLocalService groupLocalService, ObjectDefinition objectDefinition,
		ObjectEntryService objectEntryService,
		ObjectScopeProviderRegistry objectScopeProviderRegistry) {

		_depotEntryLocalService = depotEntryLocalService;
		_groupLocalService = groupLocalService;
		_objectDefinition = objectDefinition;
		_objectEntryService = objectEntryService;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
	}

	@Override
	public ObjectEntry createFromInfoItemFieldValues(
			long groupId, InfoItemFieldValues infoItemFieldValues)
		throws InfoFormException {

		try {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Map<String, Serializable> values = new HashMap<>();

			for (InfoFieldValue<Object> infoFieldValue :
					infoItemFieldValues.getInfoFieldValues()) {

				InfoField<?> infoField = infoFieldValue.getInfoField();

				values.put(
					infoField.getName(),
					(Serializable)infoFieldValue.getValue());
			}

			return _objectEntryService.addObjectEntry(
				_getGroupId(_objectDefinition, String.valueOf(groupId)),
				_objectDefinition.getObjectDefinitionId(), values,
				serviceContext);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			throw new InfoFormException();
		}
	}

	private long _getGroupId(
		ObjectDefinition objectDefinition, String scopeKey) {

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		if (!objectScopeProvider.isGroupAware()) {
			return 0;
		}

		long groupId = 0;

		if (Objects.equals(
				ObjectDefinitionConstants.SCOPE_SITE,
				objectDefinition.getScope())) {

			groupId = GroupUtil.getGroupId(
				objectDefinition.getCompanyId(), scopeKey, _groupLocalService);
		}
		else {
			groupId = GroupUtil.getDepotGroupId(
				scopeKey, objectDefinition.getCompanyId(),
				_depotEntryLocalService, _groupLocalService);
		}

		return GetterUtil.getLong(groupId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryInfoItemCreator.class);

	private final DepotEntryLocalService _depotEntryLocalService;
	private final GroupLocalService _groupLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryService _objectEntryService;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;

}