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

package com.liferay.dynamic.data.mapping.data.provider.web.internal.exportimport.staged.model.repository;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance",
	service = StagedModelRepository.class
)
public class DDMDataProviderInstanceStagedModelRepository
	implements StagedModelRepository<DDMDataProviderInstance> {

	@Override
	public DDMDataProviderInstance addStagedModel(
			PortletDataContext portletDataContext,
			DDMDataProviderInstance dataProviderInstance)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			dataProviderInstance.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			dataProviderInstance);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(dataProviderInstance.getUuid());
		}

		DDMForm ddmForm = getDataProviderSettingsDDMForm(
			dataProviderInstance.getType());

		DDMFormValues ddmFormValues = deserialize(
			dataProviderInstance.getDefinition(), ddmForm);

		return _ddmDataProviderInstanceLocalService.addDataProviderInstance(
			userId, dataProviderInstance.getGroupId(),
			dataProviderInstance.getNameMap(),
			dataProviderInstance.getDescriptionMap(), ddmFormValues,
			dataProviderInstance.getType(), serviceContext);
	}

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
			_ddmDataProviderInstanceLocalService.
				fetchDDMDataProviderInstanceByUuidAndGroupId(uuid, groupId);

		if (dataProviderInstance != null) {
			deleteStagedModel(dataProviderInstance);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_ddmDataProviderInstanceLocalService.deleteDataProviderInstances(
			portletDataContext.getCompanyId(),
			portletDataContext.getScopeGroupId());
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
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _ddmDataProviderInstanceLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public DDMDataProviderInstance getStagedModel(long dataProviderInstanceId)
		throws PortalException {

		return _ddmDataProviderInstanceLocalService.getDDMDataProviderInstance(
			dataProviderInstanceId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			DDMDataProviderInstance dataProviderInstance)
		throws PortletDataException {
	}

	@Override
	public DDMDataProviderInstance saveStagedModel(
			DDMDataProviderInstance dataProviderInstance)
		throws PortalException {

		return _ddmDataProviderInstanceLocalService.
			updateDDMDataProviderInstance(dataProviderInstance);
	}

	@Override
	public DDMDataProviderInstance updateStagedModel(
			PortletDataContext portletDataContext,
			DDMDataProviderInstance dataProviderInstance)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			dataProviderInstance.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			dataProviderInstance);

		DDMForm ddmForm = getDataProviderSettingsDDMForm(
			dataProviderInstance.getType());

		DDMFormValues ddmFormValues = deserialize(
			dataProviderInstance.getDefinition(), ddmForm);

		return _ddmDataProviderInstanceLocalService.updateDataProviderInstance(
			userId, dataProviderInstance.getDataProviderInstanceId(),
			dataProviderInstance.getNameMap(),
			dataProviderInstance.getDescriptionMap(), ddmFormValues,
			serviceContext);
	}

	protected DDMFormValues deserialize(String content, DDMForm ddmForm) {
		DDMFormValuesDeserializer ddmFormValuesDeserializer =
			_ddmFormValuesDeserializerTracker.getDDMFormValuesDeserializer(
				"json");

		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				content, ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				ddmFormValuesDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
	}

	protected DDMForm getDataProviderSettingsDDMForm(String type) {
		DDMDataProvider ddmDataProvider =
			_ddmDataProviderTracker.getDDMDataProvider(type);

		if (ddmDataProvider == null) {
			throw new IllegalStateException(
				"No data provider found for type " + type);
		}

		return DDMFormFactory.create(ddmDataProvider.getSettings());
	}

	@Reference
	private DDMDataProviderInstanceLocalService
		_ddmDataProviderInstanceLocalService;

	@Reference
	private DDMDataProviderTracker _ddmDataProviderTracker;

	@Reference
	private DDMFormValuesDeserializerTracker _ddmFormValuesDeserializerTracker;

}