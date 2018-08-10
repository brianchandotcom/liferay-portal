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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.exception.DataProviderInstanceNameException;
import com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceException;
import com.liferay.dynamic.data.mapping.exception.RequiredDataProviderInstanceException;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.base.DDMDataProviderInstanceLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidator;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDMDataProviderInstanceLocalServiceImpl
	extends DDMDataProviderInstanceLocalServiceBaseImpl {

	@Override
	public DDMDataProviderInstance addDataProviderInstance(
			long userId, long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMFormValues ddmFormValues,
			String type, ServiceContext serviceContext)
		throws PortalException {

		// Data provider instance

		User user = userLocalService.getUser(userId);

		validate(nameMap, ddmFormValues);

		long dataProviderInstanceId = counterLocalService.increment();

		DDMDataProviderInstance dataProviderInstance =
			ddmDataProviderInstancePersistence.create(dataProviderInstanceId);

		dataProviderInstance.setUuid(serviceContext.getUuid());
		dataProviderInstance.setGroupId(groupId);
		dataProviderInstance.setCompanyId(user.getCompanyId());
		dataProviderInstance.setUserId(user.getUserId());
		dataProviderInstance.setUserName(user.getFullName());
		dataProviderInstance.setNameMap(nameMap);
		dataProviderInstance.setDescriptionMap(descriptionMap);
		dataProviderInstance.setDefinition(serialize(ddmFormValues));
		dataProviderInstance.setType(type);

		ddmDataProviderInstancePersistence.update(dataProviderInstance);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addDataProviderInstanceResources(
				dataProviderInstance, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addDataProviderInstanceResources(
				dataProviderInstance, serviceContext.getModelPermissions());
		}

		return dataProviderInstance;
	}

	@Override
	public void deleteDataProviderInstance(
			DDMDataProviderInstance dataProviderInstance)
		throws PortalException {

		if (!GroupThreadLocal.isDeleteInProcess()) {
			if (ddmDataProviderInstanceLinkPersistence.
					countByDataProviderInstanceId(
						dataProviderInstance.getDataProviderInstanceId()) > 0) {

				throw new RequiredDataProviderInstanceException.
					MustNotDeleteDataProviderInstanceReferencedByDataProviderInstanceLinks(
						dataProviderInstance.getDataProviderInstanceId());
			}
		}

		// Data provider instance

		ddmDataProviderInstancePersistence.remove(dataProviderInstance);

		// Resources

		resourceLocalService.deleteResource(
			dataProviderInstance.getCompanyId(),
			DDMDataProviderInstance.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			dataProviderInstance.getDataProviderInstanceId());
	}

	@Override
	public void deleteDataProviderInstance(long dataProviderInstanceId)
		throws PortalException {

		DDMDataProviderInstance dataProviderInstance =
			ddmDataProviderInstancePersistence.findByPrimaryKey(
				dataProviderInstanceId);

		ddmDataProviderInstanceLocalService.deleteDataProviderInstance(
			dataProviderInstance);
	}

	@Override
	public void deleteDataProviderInstances(long companyId, final long groupId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			ddmDataProviderInstanceLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property groupIdProperty = PropertyFactoryUtil.forName(
						"groupId");

					dynamicQuery.add(groupIdProperty.eq(groupId));
				}

			});
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.
				PerformActionMethod<DDMDataProviderInstance>() {

				@Override
				public void performAction(
						DDMDataProviderInstance ddmDataProviderInstance)
					throws PortalException {

					deleteDataProviderInstance(ddmDataProviderInstance);
				}

			});

		actionableDynamicQuery.setCompanyId(companyId);

		actionableDynamicQuery.performActions();
	}

	@Override
	public DDMDataProviderInstance fetchDataProviderInstance(
		long dataProviderInstanceId) {

		return ddmDataProviderInstancePersistence.fetchByPrimaryKey(
			dataProviderInstanceId);
	}

	@Override
	public DDMDataProviderInstance fetchDataProviderInstanceByUuid(
		String uuid) {

		List<DDMDataProviderInstance> ddmDataProviderInstances =
			ddmDataProviderInstancePersistence.findByUuid(uuid);

		if (ddmDataProviderInstances.isEmpty()) {
			return null;
		}

		return ddmDataProviderInstances.get(0);
	}

	@Override
	public DDMDataProviderInstance getDataProviderInstance(
			long dataProviderInstanceId)
		throws PortalException {

		return ddmDataProviderInstancePersistence.findByPrimaryKey(
			dataProviderInstanceId);
	}

	@Override
	public DDMDataProviderInstance getDataProviderInstanceByUuid(String uuid)
		throws PortalException {

		List<DDMDataProviderInstance> ddmDataProviderInstances =
			ddmDataProviderInstancePersistence.findByUuid(uuid);

		if (ddmDataProviderInstances.isEmpty()) {
			throw new NoSuchDataProviderInstanceException(
				"No DataProviderInstance found with uuid: " + uuid);
		}

		return ddmDataProviderInstances.get(0);
	}

	@Override
	public List<DDMDataProviderInstance> getDataProviderInstances(
		long[] groupIds) {

		return ddmDataProviderInstancePersistence.findByGroupId(groupIds);
	}

	@Override
	public List<DDMDataProviderInstance> getDataProviderInstances(
		long[] groupIds, int start, int end) {

		return ddmDataProviderInstancePersistence.findByGroupId(
			groupIds, start, end);
	}

	@Override
	public List<DDMDataProviderInstance> getDataProviderInstances(
		long[] groupIds, int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {

		return ddmDataProviderInstancePersistence.findByGroupId(
			groupIds, start, end, orderByComparator);
	}

	@Override
	public List<DDMDataProviderInstance> search(
		long companyId, long[] groupIds, String keywords, int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {

		return ddmDataProviderInstanceFinder.findByKeywords(
			companyId, groupIds, keywords, start, end, orderByComparator);
	}

	@Override
	public List<DDMDataProviderInstance> search(
		long companyId, long[] groupIds, String name, String description,
		boolean andOperator, int start, int end,
		OrderByComparator<DDMDataProviderInstance> orderByComparator) {

		return ddmDataProviderInstanceFinder.findByC_G_N_D(
			companyId, groupIds, name, description, andOperator, start, end,
			orderByComparator);
	}

	@Override
	public int searchCount(long companyId, long[] groupIds, String keywords) {
		return ddmDataProviderInstanceFinder.countByKeywords(
			companyId, groupIds, keywords);
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, String name, String description,
		boolean andOperator) {

		return ddmDataProviderInstanceFinder.countByC_G_N_D(
			companyId, groupIds, name, description, andOperator);
	}

	@Override
	public DDMDataProviderInstance updateDataProviderInstance(
			long userId, long dataProviderInstanceId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		validate(nameMap, ddmFormValues);

		DDMDataProviderInstance dataProviderInstance =
			ddmDataProviderInstancePersistence.findByPrimaryKey(
				dataProviderInstanceId);

		dataProviderInstance.setUserId(user.getUserId());
		dataProviderInstance.setUserName(user.getFullName());
		dataProviderInstance.setNameMap(nameMap);
		dataProviderInstance.setDescriptionMap(descriptionMap);
		dataProviderInstance.setDefinition(serialize(ddmFormValues));

		ddmDataProviderInstancePersistence.update(dataProviderInstance);

		return dataProviderInstance;
	}

	protected void addDataProviderInstanceResources(
			DDMDataProviderInstance dataProviderInstance,
			boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			dataProviderInstance.getCompanyId(),
			dataProviderInstance.getGroupId(), dataProviderInstance.getUserId(),
			DDMDataProviderInstance.class.getName(),
			dataProviderInstance.getDataProviderInstanceId(), false,
			addGroupPermissions, addGuestPermissions);
	}

	protected void addDataProviderInstanceResources(
			DDMDataProviderInstance dataProviderInstance,
			ModelPermissions modelPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			dataProviderInstance.getCompanyId(),
			dataProviderInstance.getGroupId(), dataProviderInstance.getUserId(),
			DDMDataProviderInstance.class.getName(),
			dataProviderInstance.getDataProviderInstanceId(), modelPermissions);
	}

	protected String serialize(DDMFormValues ddmFormValues) {
		DDMFormValuesSerializer ddmFormValuesSerializer =
			ddmFormValuesSerializerTracker.getDDMFormValuesSerializer("json");

		DDMFormValuesSerializerSerializeRequest.Builder builder =
			DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormValues);

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				ddmFormValuesSerializer.serialize(builder.build());

		return ddmFormValuesSerializerSerializeResponse.getContent();
	}

	protected void validate(
			Map<Locale, String> nameMap, DDMFormValues ddmFormValues)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		String name = nameMap.get(locale);

		if (Validator.isNull(name)) {
			throw new DataProviderInstanceNameException(
				"Name is null for locale " + locale.getDisplayName());
		}

		ddmFormValuesValidator.validate(ddmFormValues);
	}

	@ServiceReference(type = DDMFormValuesSerializerTracker.class)
	protected DDMFormValuesSerializerTracker ddmFormValuesSerializerTracker;

	@ServiceReference(type = DDMFormValuesValidator.class)
	protected DDMFormValuesValidator ddmFormValuesValidator;

}