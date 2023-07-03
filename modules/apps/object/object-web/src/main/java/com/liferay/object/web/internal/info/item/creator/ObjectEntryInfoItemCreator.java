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

import com.liferay.info.constants.InfoItemCreatorConstants;
import com.liferay.info.exception.InfoFormException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.creator.InfoItemCreator;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.web.internal.info.item.handler.ObjectEntryInfoItemExceptionRequestHandler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.text.Format;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 */
public class ObjectEntryInfoItemCreator
	implements InfoItemCreator<ObjectEntry> {

	public ObjectEntryInfoItemCreator(
		GroupLocalService groupLocalService,
		InfoItemFormProvider<ObjectEntry> infoItemFormProvider,
		ObjectDefinition objectDefinition,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectEntryManagerRegistry objectEntryManagerRegistry,
		ObjectScopeProviderRegistry objectScopeProviderRegistry) {

		_groupLocalService = groupLocalService;
		_infoItemFormProvider = infoItemFormProvider;
		_objectDefinition = objectDefinition;
		_objectEntryLocalService = objectEntryLocalService;
		_objectEntryManagerRegistry = objectEntryManagerRegistry;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
	}

	@Override
	public ObjectEntry createFromInfoItemFieldValues(
			long groupId, InfoItemFieldValues infoItemFieldValues)
		throws InfoFormException {

		try {
			ObjectEntryManager objectEntryManager =
				_objectEntryManagerRegistry.getObjectEntryManager(
					_objectDefinition.getStorageType());

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

			com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry =
				objectEntryManager.addObjectEntry(
					new DefaultDTOConverterContext(
						false, null, null, null, null, themeDisplay.getLocale(),
						null, themeDisplay.getUser()),
					_objectDefinition,
					new com.liferay.object.rest.dto.v1_0.ObjectEntry() {
						{
							keywords = serviceContext.getAssetTagNames();
							properties = _toProperties(infoItemFieldValues);
							taxonomyCategoryIds = ArrayUtil.toLongArray(
								serviceContext.getAssetCategoryIds());
						}
					},
					_getScopeKey(groupId));

			ObjectEntry serviceBuilderObjectEntry =
				_objectEntryLocalService.createObjectEntry(
					GetterUtil.getLong(objectEntry.getId()));

			serviceBuilderObjectEntry.setExternalReferenceCode(
				objectEntry.getExternalReferenceCode());
			serviceBuilderObjectEntry.setObjectDefinitionId(
				_objectDefinition.getObjectDefinitionId());

			return serviceBuilderObjectEntry;
		}
		catch (Exception exception) {
			ObjectEntryInfoItemExceptionRequestHandler.handleInfoFormException(
				exception, groupId, _infoItemFormProvider, _objectDefinition);
		}

		return null;
	}

	@Override
	public int getScope() {
		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				_objectDefinition.getScope());

		if (Objects.equals(
				objectScopeProvider.getKey(),
				ObjectDefinitionConstants.SCOPE_COMPANY)) {

			return InfoItemCreatorConstants.SCOPE_COMPANY;
		}

		return InfoItemCreatorConstants.SCOPE_SITE;
	}

	@Override
	public boolean supportsCategorization() {
		return _objectDefinition.isEnableCategorization();
	}

	private String _getScopeKey(long groupId) {
		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				_objectDefinition.getScope());

		if (!objectScopeProvider.isGroupAware()) {
			return null;
		}

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return null;
		}

		return group.getGroupKey();
	}

	private Map<String, Object> _toProperties(
		InfoItemFieldValues infoItemFieldValues) {

		Map<String, Object> properties = new HashMap<>();

		for (InfoFieldValue<Object> infoFieldValue :
				infoItemFieldValues.getInfoFieldValues()) {

			InfoField<?> infoField = infoFieldValue.getInfoField();

			Object value = infoFieldValue.getValue();

			if (Objects.equals(
					DateInfoFieldType.INSTANCE, infoField.getInfoFieldType()) &&
				(value instanceof Date)) {

				Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
					"yyyy-MM-dd");

				properties.put(infoField.getName(), format.format(value));
			}
			else {
				properties.put(infoField.getName(), value);
			}
		}

		return properties;
	}

	private final GroupLocalService _groupLocalService;
	private final InfoItemFormProvider<ObjectEntry> _infoItemFormProvider;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectEntryManagerRegistry _objectEntryManagerRegistry;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;

}