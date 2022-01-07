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
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.web.internal.constants.ObjectWebKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.text.Format;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectFieldLocalService objectFieldLocalService,
		ServletContext servletContext) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_objectFieldLocalService = objectFieldLocalService;
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
			httpServletRequest.setAttribute(
				ObjectWebKeys.OBJECT_DEFINITION,
				_objectDefinitionLocalService.getObjectDefinition(
					objectEntry.getObjectDefinitionId()));
			httpServletRequest.setAttribute(
				ObjectWebKeys.OBJECT_ENTRY, objectEntry);
			httpServletRequest.setAttribute(
				ObjectWebKeys.OBJECT_ENTRY_VALUES, _getValues(objectEntry));

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/info/item/renderer/object_entry.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private Map<String, Serializable> _getValues(ObjectEntry objectEntry)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Map<String, Serializable> values = _objectEntryLocalService.getValues(
			objectEntry);

		Set<Map.Entry<String, Serializable>> entries = values.entrySet();

		Stream<Map.Entry<String, Serializable>> entriesStream =
			entries.stream();

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectEntry.getObjectDefinitionId());

		Stream<ObjectField> objectFieldsStream = objectFields.stream();

		Map<String, ObjectField> objectFieldsMap = objectFieldsStream.collect(
			Collectors.toMap(ObjectField::getName, Function.identity()));

		return entriesStream.filter(
			entry -> objectFieldsMap.containsKey(entry.getKey())
		).sorted(
			Map.Entry.comparingByKey()
		).collect(
			Collectors.toMap(
				Map.Entry::getKey,
				entry -> {
					if (entry.getValue() == null) {
						return StringPool.BLANK;
					}

					ObjectField objectField = objectFieldsMap.get(
						entry.getKey());

					if (objectField.getListTypeDefinitionId() != 0) {
						ListTypeEntry listTypeEntry =
							_listTypeEntryLocalService.fetchListTypeEntry(
								objectField.getListTypeDefinitionId(),
								(String)entry.getValue());

						return listTypeEntry.getName(
							serviceContext.getLocale());
					}
					else if (Validator.isNull(
								objectField.getRelationshipType())) {

						if (Objects.equals(objectField.getDBType(), "Date")) {
							Format dateFormat =
								FastDateFormatFactoryUtil.getDate(
									serviceContext.getLocale());

							return dateFormat.format(entry.getValue());
						}

						return Optional.ofNullable(
							entry.getValue()
						).orElse(
							StringPool.BLANK
						);
					}

					ObjectEntry relatedObjectEntry =
						_objectEntryLocalService.fetchObjectEntry(
							(Long)values.get(objectField.getName()));

					if (relatedObjectEntry == null) {
						return StringPool.BLANK;
					}

					try {
						return relatedObjectEntry.getTitleValue();
					}
					catch (PortalException portalException) {
						throw new RuntimeException(portalException);
					}
				},
				(oldValue, newValue) -> oldValue, LinkedHashMap::new)
		);
	}

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ServletContext _servletContext;

}