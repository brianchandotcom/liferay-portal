/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.util.LinkUtil;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.io.Serializable;

import java.text.Format;

import java.util.ArrayList;
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
		ObjectDefinition objectDefinition,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectEntryManager objectEntryManager,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		Portal portal, ServletContext servletContext) {

		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_dlAppService = dlAppService;
		_dlFileEntryLocalService = dlFileEntryLocalService;
		_dlURLHelper = dlURLHelper;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_objectDefinition = objectDefinition;
		_objectEntryLocalService = objectEntryLocalService;
		_objectEntryManager = objectEntryManager;
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
			httpServletRequest.setAttribute(
				ObjectWebKeys.OBJECT_DEFINITION, _objectDefinition);
			httpServletRequest.setAttribute(
				ObjectWebKeys.OBJECT_ENTRY, objectEntry);
			httpServletRequest.setAttribute(
				ObjectWebKeys.OBJECT_ENTRY_VALUES,
				_getValues(httpServletRequest, objectEntry));

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
			HttpServletRequest httpServletRequest,
			ObjectEntry serviceBuilderObjectEntry)
		throws Exception {

		Map<String, Serializable> sortedValues = new TreeMap<>();

		Map<String, ?> values = null;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (_objectDefinition.isDefaultStorageType()) {
			values = serviceBuilderObjectEntry.getValues();
		}
		else {
			com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry =
				_objectEntryManager.getObjectEntry(
					themeDisplay.getCompanyId(),
					new DefaultDTOConverterContext(
						false, null, null, null, null, themeDisplay.getLocale(),
						null, themeDisplay.getUser()),
					serviceBuilderObjectEntry.getExternalReferenceCode(),
					_objectDefinition, null);

			values = objectEntry.getProperties();
		}

		List<ObjectField> objectFields =
			_objectFieldLocalService.getActiveObjectFields(
				_objectFieldLocalService.getObjectFields(
					_objectDefinition.getObjectDefinitionId(), false));

		for (ObjectField objectField : objectFields) {
			Object value = values.get(objectField.getName());

			if (objectField.getListTypeDefinitionId() != 0) {
				if (Objects.equals(
						objectField.getBusinessType(),
						ObjectFieldConstants.
							BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

					List<String> listTypeEntryKeys = new ArrayList<>();

					if (value instanceof List) {
						for (ListEntry listEntry : (List<ListEntry>)value) {
							listTypeEntryKeys.add(listEntry.getKey());
						}
					}
					else {
						listTypeEntryKeys = ListUtil.fromString(
							(String)value, StringPool.COMMA_AND_SPACE);
					}

					List<String> validListTypeEntriesNames = new ArrayList<>(
						listTypeEntryKeys.size());

					for (String key : listTypeEntryKeys) {
						ListTypeEntry listTypeEntry =
							_listTypeEntryLocalService.fetchListTypeEntry(
								objectField.getListTypeDefinitionId(), key);

						if (listTypeEntry == null) {
							continue;
						}

						validListTypeEntriesNames.add(
							listTypeEntry.getName(themeDisplay.getLocale()));
					}

					sortedValues.put(
						objectField.getName(),
						StringUtil.merge(
							validListTypeEntriesNames,
							StringPool.COMMA_AND_SPACE));

					continue;
				}

				String listTypeEntryKey = null;

				if (value instanceof ListEntry) {
					listTypeEntryKey = ((ListEntry)value).getKey();
				}
				else {
					listTypeEntryKey = (String)value;
				}

				ListTypeEntry listTypeEntry =
					_listTypeEntryLocalService.fetchListTypeEntry(
						objectField.getListTypeDefinitionId(),
						listTypeEntryKey);

				if (listTypeEntry == null) {
					sortedValues.put(objectField.getName(), StringPool.BLANK);

					continue;
				}

				sortedValues.put(
					objectField.getName(),
					listTypeEntry.getName(themeDisplay.getLocale()));

				continue;
			}

			if (Objects.equals(
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
					objectField.getBusinessType())) {

				long dlFileEntryId = GetterUtil.getLong(
					values.get(objectField.getName()));

				if (dlFileEntryId == GetterUtil.DEFAULT_LONG) {
					sortedValues.put(objectField.getName(), StringPool.BLANK);

					continue;
				}

				DLFileEntry dlFileEntry =
					_dlFileEntryLocalService.fetchDLFileEntry(dlFileEntryId);

				if (dlFileEntry == null) {
					sortedValues.put(objectField.getName(), StringPool.BLANK);

					continue;
				}

				sortedValues.put(
					objectField.getName(),
					LinkUtil.toLink(
						_dlAppService, dlFileEntry, _dlURLHelper,
						_objectDefinition.getExternalReferenceCode(),
						serviceBuilderObjectEntry.getExternalReferenceCode(),
						_portal));

				continue;
			}

			if (Objects.equals(
					ObjectFieldConstants.DB_TYPE_DATE,
					objectField.getDBType())) {

				Object dateValue = values.get(objectField.getName());

				if (Validator.isNull(dateValue)) {
					continue;
				}

				if (dateValue instanceof String) {
					dateValue = DateUtil.parseDate(
						"yyyy-MM-dd", (String)dateValue,
						themeDisplay.getLocale());
				}

				Format dateFormat = FastDateFormatFactoryUtil.getDate(
					themeDisplay.getLocale());

				sortedValues.put(
					objectField.getName(), dateFormat.format(dateValue));

				continue;
			}

			if (Validator.isNotNull(objectField.getRelationshipType())) {
				if (GetterUtil.getLong(value) <= 0) {
					sortedValues.put(objectField.getName(), StringPool.BLANK);

					continue;
				}

				try {
					ObjectRelationship objectRelationship =
						_objectRelationshipLocalService.
							fetchObjectRelationshipByObjectFieldId2(
								objectField.getObjectFieldId());

					sortedValues.put(
						objectField.getName(),
						_objectEntryLocalService.getTitleValue(
							objectRelationship.getObjectDefinitionId1(),
							(Long)values.get(objectField.getName())));

					continue;
				}
				catch (PortalException portalException) {
					throw new RuntimeException(portalException);
				}
			}

			if (value != null) {
				sortedValues.put(objectField.getName(), (Serializable)value);

				continue;
			}

			sortedValues.put(objectField.getName(), StringPool.BLANK);
		}

		return sortedValues;
	}

	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final DLAppService _dlAppService;
	private final DLFileEntryLocalService _dlFileEntryLocalService;
	private final DLURLHelper _dlURLHelper;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectEntryManager _objectEntryManager;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final Portal _portal;
	private final ServletContext _servletContext;

}