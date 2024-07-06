/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.model.listener;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
@Component(service = ModelListener.class)
public class DataSetObjectFieldModelListener
	extends BaseModelListener<ObjectField> {

	@Override
	public void onAfterCreate(ObjectField objectField)
		throws ModelListenerException {

		if ((objectField == null) ||
			!FeatureFlagManagerUtil.isEnabled("LPS-164563")) {

			return;
		}

		try {
			ObjectDefinition objectDefinition =
				objectField.getObjectDefinition();

			if (objectDefinition == null) {
				return;
			}

			String objectDefinitionERC =
				objectDefinition.getExternalReferenceCode();

			String[] localizedFieldsERC = _localizedFieldsERC.get(
				objectDefinitionERC);

			if (localizedFieldsERC == null) {
				return;
			}

			if (objectField.isSystem() && objectField.isLocalized() &&
				Objects.equals(
					objectDefinitionERC,
					_OBJECT_DEFINITIONS_ERC
						[_OBJECT_DEFINITIONS_ERC.length - 1]) &&
				Objects.equals(
					objectField.getExternalReferenceCode(),
					localizedFieldsERC[localizedFieldsERC.length - 1])) {

				TransactionCommitCallbackUtil.registerCallback(
					() -> {
						_publishSystemObjectDefinitions(
							objectDefinition.getCompanyId(),
							objectDefinition.getUserId());

						return null;
					});
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Override
	public void onBeforeCreate(ObjectField objectField) {
		if ((objectField == null) ||
			!FeatureFlagManagerUtil.isEnabled("LPS-164563")) {

			return;
		}

		try {
			ObjectDefinition objectDefinition =
				objectField.getObjectDefinition();

			if (!objectField.isSystem() && objectField.isLocalized() &&
				ArrayUtil.contains(
					_localizedFieldsERC.get(
						objectDefinition.getExternalReferenceCode()),
					objectField.getExternalReferenceCode())) {

				objectField.setSystem(true);
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _publishSystemObjectDefinitions(long companyId, long userId) {
		try {
			for (String objectDefinitionERC : _OBJECT_DEFINITIONS_ERC) {
				ObjectDefinition objectDefinition =
					_objectDefinitionLocalService.
						fetchObjectDefinitionByExternalReferenceCode(
							objectDefinitionERC, companyId);

				if (objectDefinition.getStatus() !=
						WorkflowConstants.STATUS_APPROVED) {

					_objectDefinitionLocalService.publishSystemObjectDefinition(
						userId, objectDefinition.getObjectDefinitionId());
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final String[] _OBJECT_DEFINITIONS_ERC = {
		"L_DATA_SET", "L_DATA_SET_ACTION", "L_DATA_SET_CARDS_SECTION",
		"L_DATA_SET_CLIENT_EXTENSION_FILTER", "L_DATA_SET_DATE_FILTER",
		"L_DATA_SET_LIST_SECTION", "L_DATA_SET_SELECTION_FILTER",
		"L_DATA_SET_SORT", "L_DATA_SET_TABLE_SECTION"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		DataSetObjectFieldModelListener.class);

	private static final Map<String, String[]> _localizedFieldsERC =
		HashMapBuilder.put(
			"L_DATA_SET_ACTION",
			new String[] {
				"CONFIRMATION_MESSAGE", "ERROR_MESSAGE", "LABEL",
				"SUCCESS_MESSAGE", "TITLE"
			}
		).put(
			"L_DATA_SET_CLIENT_EXTENSION_FILTER", new String[] {"LABEL"}
		).put(
			"L_DATA_SET_DATE_FILTER", new String[] {"LABEL"}
		).put(
			"L_DATA_SET_SELECTION_FILTER", new String[] {"LABEL"}
		).put(
			"L_DATA_SET_SORT", new String[] {"LABEL"}
		).put(
			"L_DATA_SET_TABLE_SECTION", new String[] {"LABEL"}
		).build();

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}