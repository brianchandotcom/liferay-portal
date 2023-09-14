/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.updater;

import com.liferay.info.exception.InfoFormException;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.updater.InfoItemFieldValuesUpdater;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.web.internal.info.item.handler.ObjectEntryInfoItemExceptionRequestHandler;
import com.liferay.object.web.internal.util.ObjectEntryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

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
			ObjectEntry objectEntry, InfoItemFieldValues infoItemFieldValues,
			int status)
		throws InfoFormException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				_objectDefinition.getStorageType());

		int objectEntryStatus = status;

		try {
			return ObjectEntryUtil.toObjectEntry(
				objectEntry.getObjectDefinitionId(),
				objectEntryManager.partialUpdateObjectEntry(
					objectEntry.getCompanyId(),
					new DefaultDTOConverterContext(
						false, null, null, null, null, themeDisplay.getLocale(),
						null, themeDisplay.getUser()),
					objectEntry.getExternalReferenceCode(), _objectDefinition,
					new com.liferay.object.rest.dto.v1_0.ObjectEntry() {
						{
							keywords = serviceContext.getAssetTagNames();
							properties = ObjectEntryUtil.toProperties(
								infoItemFieldValues);
							taxonomyCategoryIds = ArrayUtil.toLongArray(
								serviceContext.getAssetCategoryIds());

							status = new Status() {
								{
									code = objectEntryStatus;
								}
							};
						}
					},
					ObjectEntryUtil.getScopeKey(
						objectEntry.getGroupId(), _objectDefinition,
						_objectScopeProviderRegistry)));
		}
		catch (Exception exception) {
			ObjectEntryInfoItemExceptionRequestHandler.handleInfoFormException(
				exception, objectEntry.getGroupId(), _infoItemFormProvider,
				_objectDefinition);
		}

		return null;
	}

	private final InfoItemFormProvider<ObjectEntry> _infoItemFormProvider;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryManagerRegistry _objectEntryManagerRegistry;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;

}