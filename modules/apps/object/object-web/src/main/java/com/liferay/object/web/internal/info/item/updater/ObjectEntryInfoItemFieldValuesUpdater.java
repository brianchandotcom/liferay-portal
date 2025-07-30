/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.updater;

import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.updater.InfoItemFieldValuesUpdater;
import com.liferay.object.info.item.util.ObjectEntryInfoItemUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.web.internal.info.item.handler.ObjectEntryInfoItemExceptionRequestHandler;
import com.liferay.object.web.internal.util.ObjectEntryUtil;
import com.liferay.portal.kernel.exception.InfoFormException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.text.DateFormat;

import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class ObjectEntryInfoItemFieldValuesUpdater
	implements InfoItemFieldValuesUpdater<ObjectEntry> {

	public ObjectEntryInfoItemFieldValuesUpdater(
		InfoItemFormProvider<ObjectEntry> infoItemFormProvider,
		ObjectDefinition objectDefinition,
		ObjectEntryManagerRegistry objectEntryManagerRegistry,
		ObjectScopeProviderRegistry objectScopeProviderRegistry) {

		_infoItemFormProvider = infoItemFormProvider;
		_objectDefinition = objectDefinition;
		_objectEntryManagerRegistry = objectEntryManagerRegistry;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
	}

	@Override
	public ObjectEntry updateFromInfoItemFieldValues(
			ObjectEntry objectEntry, InfoItemFieldValues infoItemFieldValues)
		throws Exception {

		return updateFromInfoItemFieldValues(
			objectEntry, infoItemFieldValues,
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public ObjectEntry updateFromInfoItemFieldValues(
			ObjectEntry originalObjectEntry,
			InfoItemFieldValues infoItemFieldValues, int statusInt)
		throws InfoFormException {

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				_objectDefinition.getStorageType());

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		Map<String, Object> curProperties = ObjectEntryUtil.toProperties(
			themeDisplay.getCompanyId(), infoItemFieldValues);

		try {
			String scopeKey = ObjectEntryInfoItemUtil.getScopeKey(
				originalObjectEntry.getGroupId(), _objectDefinition,
				_objectScopeProviderRegistry);

			com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry =
				objectEntryManager.partialUpdateObjectEntry(
					originalObjectEntry.getCompanyId(),
					new DefaultDTOConverterContext(
						false, null, null, null, null, themeDisplay.getLocale(),
						null, themeDisplay.getUser()),
					originalObjectEntry.getExternalReferenceCode(),
					_objectDefinition,
					new com.liferay.object.rest.dto.v1_0.ObjectEntry() {
						{
							setFriendlyUrlPath(
								() -> GetterUtil.getString(
									curProperties.get("objectEntryFriendlyURL"),
									null));
							setFriendlyUrlPath_i18n(
								() -> (Map<String, String>)curProperties.get(
									"objectEntryFriendlyURL_i18n"));
							setKeywords(serviceContext::getAssetTagNames);
							setProperties(() -> curProperties);
							setStatus(
								() -> new Status() {
									{
										setCode(() -> statusInt);
									}
								});
							setTaxonomyCategoryIds(
								() -> ArrayUtil.toLongArray(
									serviceContext.getAssetCategoryIds()));
						}
					},
					scopeKey);

			if (curProperties.containsKey("reviewDate") ||
				curProperties.containsKey("expirationDate")) {

				objectEntry.setExpirationDate(
					() -> {
						if (curProperties.containsKey("expirationDate")) {
							return GetterUtil.getDate(
								curProperties.get("expirationDate"),
								_dateTimeFormatter, null);
						}

						return originalObjectEntry.getExpirationDate();
					});

				objectEntry.setReviewDate(
					() -> {
						if (curProperties.containsKey("reviewDate")) {
							return GetterUtil.getDate(
								curProperties.get("reviewDate"),
								_dateTimeFormatter, null);
						}

						return originalObjectEntry.getReviewDate();
					});

				objectEntry = objectEntryManager.updateObjectEntry(
					originalObjectEntry.getCompanyId(),
					new DefaultDTOConverterContext(
						false, null, null, null, null, themeDisplay.getLocale(),
						null, themeDisplay.getUser()),
					originalObjectEntry.getExternalReferenceCode(),
					_objectDefinition, objectEntry, scopeKey);
			}

			return ObjectEntryUtil.toObjectEntry(
				originalObjectEntry.getObjectDefinitionId(), objectEntry);
		}
		catch (Exception exception) {
			ObjectEntryInfoItemExceptionRequestHandler.handleInfoFormException(
				exception, originalObjectEntry.getGroupId(),
				_infoItemFormProvider, _objectDefinition);
		}

		return null;
	}

	private static final DateFormat _dateTimeFormatter =
		DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd HH:mm");

	private final InfoItemFormProvider<ObjectEntry> _infoItemFormProvider;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryManagerRegistry _objectEntryManagerRegistry;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;

}