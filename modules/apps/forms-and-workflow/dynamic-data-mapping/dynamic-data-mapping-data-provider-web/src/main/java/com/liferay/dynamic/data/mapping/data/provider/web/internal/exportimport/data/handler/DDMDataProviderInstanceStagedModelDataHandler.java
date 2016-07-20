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

package com.liferay.dynamic.data.mapping.data.provider.web.internal.exportimport.data.handler;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.data.provider.web.internal.constants.DDMDataProviderPortletKeys;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.exportimport.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + DDMDataProviderPortletKeys.DYNAMIC_DATA_MAPPING_DATA_PROVIDER},
	service = StagedModelDataHandler.class
)
public class DDMDataProviderInstanceStagedModelDataHandler
	extends BaseStagedModelDataHandler<DDMDataProviderInstance> {

	public static final String[] CLASS_NAMES =
		{DDMDataProviderInstance.class.getName()};

	@Override
	public void deleteStagedModel(DDMDataProviderInstance dataProviderInstance)
		throws PortalException {

		_ddmDataProviderInstanceLocalService.deleteDataProviderInstance(
			dataProviderInstance);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		DDMDataProviderInstance dataProviderInstance =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (dataProviderInstance != null) {
			deleteStagedModel(dataProviderInstance);
		}
	}

	@Override
	public DDMDataProviderInstance fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _ddmDataProviderInstanceLocalService.
			fetchDDMDataProviderInstanceByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<DDMDataProviderInstance> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _ddmDataProviderInstanceLocalService.
			getDDMDataProviderInstancesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator
					<DDMDataProviderInstance>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(DDMDataProviderInstance dataProviderInstance) {
		return dataProviderInstance.getNameCurrentValue();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			DDMDataProviderInstance dataProviderInstance)
		throws Exception {

		Element dataProviderInstanceElement =
			portletDataContext.getExportDataElement(dataProviderInstance);

		portletDataContext.addClassedModel(
			dataProviderInstanceElement,
			ExportImportPathUtil.getModelPath(dataProviderInstance),
			dataProviderInstance);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			DDMDataProviderInstance dataProviderInstance)
		throws Exception {

		DDMDataProvider ddmDataProvider =
			_ddmDataProviderTracker.getDDMDataProvider(
				dataProviderInstance.getType());

		if (ddmDataProvider == null) {
			throw new IllegalStateException(
				"No such DataProvider of type " +
					dataProviderInstance.getType());
		}

		long userId = portletDataContext.getUserId(
			dataProviderInstance.getUserUuid());

		DDMDataProviderInstance importedDataProviderInstance =
			(DDMDataProviderInstance)dataProviderInstance.clone();

		importedDataProviderInstance.setGroupId(
			portletDataContext.getScopeGroupId());

		DDMDataProviderInstance existingDataProviderInstance =
			_ddmDataProviderInstanceLocalService.
				fetchDDMDataProviderInstanceByUuidAndGroupId(
					dataProviderInstance.getUuid(),
					portletDataContext.getScopeGroupId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			dataProviderInstance);

		DDMForm ddmForm = DDMFormFactory.create(ddmDataProvider.getSettings());

		DDMFormValues ddmFormValues =
			_ddmFormValuesJSONDeserializer.deserialize(
				ddmForm, dataProviderInstance.getDefinition());

		if (existingDataProviderInstance == null) {
			serviceContext.setUuid(dataProviderInstance.getUuid());

			importedDataProviderInstance =
				_ddmDataProviderInstanceLocalService.addDataProviderInstance(
					userId, portletDataContext.getScopeGroupId(),
					dataProviderInstance.getNameMap(),
					dataProviderInstance.getDescriptionMap(), ddmFormValues,
					dataProviderInstance.getType(), serviceContext);
		}
		else {
			importedDataProviderInstance.setDataProviderInstanceId(
				existingDataProviderInstance.getDataProviderInstanceId());

			importedDataProviderInstance =
				_ddmDataProviderInstanceLocalService.updateDataProviderInstance(
					userId,
					existingDataProviderInstance.getDataProviderInstanceId(),
					dataProviderInstance.getNameMap(),
					dataProviderInstance.getDescriptionMap(), ddmFormValues,
					serviceContext);
		}

		portletDataContext.importClassedModel(
			dataProviderInstance, importedDataProviderInstance);
	}

	@Reference
	private DDMDataProviderInstanceLocalService
		_ddmDataProviderInstanceLocalService;

	@Reference
	private DDMDataProviderTracker _ddmDataProviderTracker;

	@Reference
	private DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;

}