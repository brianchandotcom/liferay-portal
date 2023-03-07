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

package com.liferay.object.web.internal.info.item.renderer;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectWebKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.dto.v1_0.util.LinkUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.text.Format;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jorge Ferrer
 * @author Guilherme Camacho
 */
public class ObjectEntryRowInfoItemRenderer
	implements InfoItemRenderer<ObjectEntry> {

	public ObjectEntryRowInfoItemRenderer(
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		DLAppService dlAppService,
		DLFileEntryLocalService dlFileEntryLocalService,
		DLURLHelper dlURLHelper,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		Portal portal, ServletContext servletContext) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_dlAppService = dlAppService;
		_dlFileEntryLocalService = dlFileEntryLocalService;
		_dlURLHelper = dlURLHelper;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_objectFieldLocalService = objectFieldLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_portal = portal;
		_servletContext = servletContext;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "row");
	}

	@Override
	public void render(
		ObjectEntry objectEntry, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			httpServletRequest.setAttribute(
				AssetDisplayPageFriendlyURLProvider.class.getName(),
				_assetDisplayPageFriendlyURLProvider);

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectEntry.getObjectDefinitionId());

			httpServletRequest.setAttribute(
				ObjectWebKeys.OBJECT_DEFINITION, objectDefinition);

			httpServletRequest.setAttribute(
				ObjectWebKeys.OBJECT_ENTRY, objectEntry);
			httpServletRequest.setAttribute(
				ObjectWebKeys.OBJECT_ENTRY_VALUES,
				_getValues(objectDefinition, objectEntry));

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/info/item/renderer/object_entry.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private Map<String, Serializable> _getValues(
			ObjectDefinition objectDefinition, ObjectEntry objectEntry)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Map<String, Serializable> values = _objectEntryLocalService.getValues(
			objectEntry);

		Map<String, ObjectField> objectFieldsMap = new HashMap<>();

		for (ObjectField objectField :
				_objectFieldLocalService.getActiveObjectFields(
					_objectFieldLocalService.getObjectFields(
						objectEntry.getObjectDefinitionId()))) {

			objectFieldsMap.put(objectField.getName(), objectField);
		}

		List<Map.Entry<String, Serializable>> entries = TransformUtil.transform(
			values.entrySet(),
			entry -> {
				if (objectFieldsMap.containsKey(entry.getKey())) {
					return entry;
				}

				return null;
			});

		Map<String, Serializable> stringSerializableMap = new TreeMap<>();

		for (Map.Entry<String, Serializable> entry : entries) {
			if (entry.getValue() == null) {
				stringSerializableMap.put(entry.getKey(), StringPool.BLANK);

				continue;
			}

			ObjectField objectField = objectFieldsMap.get(entry.getKey());

			if (objectField.getListTypeDefinitionId() != 0) {
				ListTypeEntry listTypeEntry =
					_listTypeEntryLocalService.fetchListTypeEntry(
						objectField.getListTypeDefinitionId(),
						(String)entry.getValue());

				stringSerializableMap.put(
					entry.getKey(),
					listTypeEntry.getName(serviceContext.getLocale()));

				continue;
			}

			if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

				long dlFileEntryId = GetterUtil.getLong(
					values.get(objectField.getName()));

				if (dlFileEntryId == GetterUtil.DEFAULT_LONG) {
					stringSerializableMap.put(entry.getKey(), StringPool.BLANK);

					continue;
				}

				DLFileEntry dlFileEntry =
					_dlFileEntryLocalService.fetchDLFileEntry(dlFileEntryId);

				if (dlFileEntry == null) {
					stringSerializableMap.put(entry.getKey(), StringPool.BLANK);

					continue;
				}

				stringSerializableMap.put(
					entry.getKey(),
					LinkUtil.toLink(
						_dlAppService, dlFileEntry, _dlURLHelper,
						objectDefinition.getExternalReferenceCode(),
						objectEntry.getExternalReferenceCode(), _portal));

				continue;
			}

			if (Objects.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_DATE)) {

				Format dateFormat = FastDateFormatFactoryUtil.getDate(
					serviceContext.getLocale());

				stringSerializableMap.put(
					entry.getKey(), dateFormat.format(entry.getValue()));

				continue;
			}

			if (Validator.isNotNull(objectField.getRelationshipType())) {
				Object value = values.get(objectField.getName());

				if (GetterUtil.getLong(value) <= 0) {
					stringSerializableMap.put(entry.getKey(), StringPool.BLANK);

					continue;
				}

				try {
					ObjectRelationship objectRelationship =
						_objectRelationshipLocalService.
							fetchObjectRelationshipByObjectFieldId2(
								objectField.getObjectFieldId());

					stringSerializableMap.put(
						entry.getKey(),
						_objectEntryLocalService.getTitleValue(
							objectRelationship.getObjectDefinitionId1(),
							(Long)values.get(objectField.getName())));

					continue;
				}
				catch (PortalException portalException) {
					throw new RuntimeException(portalException);
				}
			}

			Object value = entry.getValue();

			if (value != null) {
				stringSerializableMap.put(entry.getKey(), (Serializable)value);

				continue;
			}

			stringSerializableMap.put(entry.getKey(), StringPool.BLANK);
		}

		return stringSerializableMap;
	}

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final DLAppService _dlAppService;
	private final DLFileEntryLocalService _dlFileEntryLocalService;
	private final DLURLHelper _dlURLHelper;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final Portal _portal;
	private final ServletContext _servletContext;

}