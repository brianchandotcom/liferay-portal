/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.upgrade.v1_0_0;

import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Daniel Sanz
 */
public class DataSetOrderValuesUpgradeProcess extends UpgradeProcess {

	public DataSetOrderValuesUpgradeProcess(
		CompanyLocalService companyLocalService,
		ObjectActionEngine objectActionEngine,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService) {

		_companyLocalService = companyLocalService;
		_objectActionEngine = objectActionEngine;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompanyId(this::_updateDataSetOrderValues);
	}

	private void _convertOrderValue(
		String propertyName, Map<String, Serializable> values) {

		String propertyValue = GetterUtil.getString(values.get(propertyName));

		if (Validator.isNull(propertyValue)) {
			return;
		}

		List<String> externalReferenceCodes = new ArrayList<>();

		for (String id : StringUtil.split(propertyValue)) {
			try {
				ObjectEntry objectEntry =
					_objectEntryLocalService.getObjectEntry(
						GetterUtil.getLong(id));

				externalReferenceCodes.add(
					objectEntry.getExternalReferenceCode());
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to find object entry with ID " + id, exception);
				}
			}
		}

		values.put(propertyName, StringUtil.merge(externalReferenceCodes));
	}

	private void _updateDataSetOrderValues(long companyId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DATA_SET", companyId);

		if (objectDefinition == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"L_DATA_SET object definition not found for company " +
						companyId);
			}

			return;
		}

		if (_objectActionEngine == null) {
			throw new PortalException("Object action engine is null");
		}

		for (ObjectEntry objectEntry :
				_objectEntryLocalService.getObjectEntries(
					0, objectDefinition.getObjectDefinitionId(),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			Map<String, Serializable> values = objectEntry.getValues();

			_convertOrderValue("creationActionsOrder", values);
			_convertOrderValue("filtersOrder", values);
			_convertOrderValue("itemActionsOrder", values);
			_convertOrderValue("sortsOrder", values);
			_convertOrderValue("tableSectionsOrder", values);

			_objectEntryLocalService.updateObjectEntry(
				objectEntry.getUserId(), objectEntry.getObjectEntryId(),
				objectEntry.getObjectEntryFolderId(), values,
				new ServiceContext());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataSetOrderValuesUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final ObjectActionEngine _objectActionEngine;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;

}