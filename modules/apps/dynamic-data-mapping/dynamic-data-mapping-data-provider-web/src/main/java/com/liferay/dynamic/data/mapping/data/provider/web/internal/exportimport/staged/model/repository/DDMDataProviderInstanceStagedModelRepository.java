/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.web.internal.exportimport.staged.model.repository;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRegistry;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
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

		DDMForm ddmForm = _getDataProviderSettingsDDMForm(
			dataProviderInstance.getType());

		DDMFormValues ddmFormValues = _deserialize(
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
	public DDMDataProviderInstance fetchMissingReference(
		String uuid, long groupId) {

		return _stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

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

		DDMForm ddmForm = _getDataProviderSettingsDDMForm(
			dataProviderInstance.getType());

		DDMFormValues ddmFormValues = _deserialize(
			dataProviderInstance.getDefinition(), ddmForm);

		return _ddmDataProviderInstanceLocalService.updateDataProviderInstance(
			userId, dataProviderInstance.getDataProviderInstanceId(),
			dataProviderInstance.getNameMap(),
			dataProviderInstance.getDescriptionMap(), ddmFormValues,
			serviceContext);
	}

	private DDMFormValues _deserialize(String content, DDMForm ddmForm) {
		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				content, ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				_jsonDDMFormValuesDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
	}

	private DDMForm _getDataProviderSettingsDDMForm(String type) {
		DDMDataProvider ddmDataProvider =
			_ddmDataProviderRegistry.getDDMDataProvider(type);

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
	private DDMDataProviderRegistry _ddmDataProviderRegistry;

	@Reference(target = "(ddm.form.values.deserializer.type=json)")
	private DDMFormValuesDeserializer _jsonDDMFormValuesDeserializer;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}