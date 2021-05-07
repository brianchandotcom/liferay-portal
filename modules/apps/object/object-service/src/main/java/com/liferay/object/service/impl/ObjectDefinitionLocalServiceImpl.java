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

package com.liferay.object.service.impl;

import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.exception.DuplicateObjectDefinitionException;
import com.liferay.object.exception.ObjectDefinitionNameException;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.base.ObjectDefinitionLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectEntryPersistence;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectDefinition",
	service = AopService.class
)
public class ObjectDefinitionLocalServiceImpl
	extends ObjectDefinitionLocalServiceBaseImpl {

	@Override
	public ObjectDefinition addObjectDefinition(
			long userId, String name, List<ObjectField> objectFields)
		throws PortalException {

		User user = _userLocalService.getUser(userId);
		name = StringUtil.trim(name);

		_validateName(user.getCompanyId(), name);

		long objectDefinitionId = counterLocalService.increment();

		ObjectDefinition objectDefinition = objectDefinitionPersistence.create(
			objectDefinitionId);

		objectDefinition.setCompanyId(user.getCompanyId());
		objectDefinition.setUserId(user.getUserId());
		objectDefinition.setUserName(user.getFullName());
		objectDefinition.setName(name);

		ObjectDefinition updatedObjectDefinition =
			objectDefinitionPersistence.update(objectDefinition);

		for (ObjectField objectField : objectFields) {
			_objectFieldLocalService.addObjectField(
				userId, objectDefinitionId, objectField.getName(),
				objectField.getType());
		}

		objectFields = _objectFieldPersistence.findByObjectDefinitionId(
			objectDefinitionId);

		_createTable(updatedObjectDefinition, objectFields);

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				objectDefinitionLocalService.deployObjectDefinition(
					updatedObjectDefinition);

				return null;
			});

		return updatedObjectDefinition;
	}

	@Override
	public ObjectDefinition deleteObjectDefinition(long objectDefinitionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		return deleteObjectDefinition(objectDefinition);
	}

	@Override
	public ObjectDefinition deleteObjectDefinition(
			ObjectDefinition objectDefinition)
		throws PortalException {

		long objectDefinitionId = objectDefinition.getObjectDefinitionId();

		List<ObjectEntry> objectEntries =
			_objectEntryPersistence.findByObjectDefinitionId(
				objectDefinitionId);

		for (ObjectEntry objectEntry : objectEntries) {
			_objectEntryLocalService.deleteObjectEntry(objectEntry);
		}

		_objectFieldPersistence.removeByObjectDefinitionId(objectDefinitionId);

		objectDefinition = objectDefinitionPersistence.remove(objectDefinition);

		_dropTable(objectDefinition);

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				objectDefinitionLocalService.undeployObjectDefinition(
					objectDefinitionId);

				return null;
			});

		return objectDefinition;
	}

	@Clusterable
	@Override
	public void deployObjectDefinition(ObjectDefinition objectDefinition) {
		for (Map.Entry
				<ObjectDefinitionDeployer,
				 Map<Long, List<ServiceRegistration<?>>>> entry :
					_serviceRegistrationsMaps.entrySet()) {

			ObjectDefinitionDeployer objectDefinitionDeployer = entry.getKey();
			Map<Long, List<ServiceRegistration<?>>> serviceRegistrationsMap =
				entry.getValue();

			serviceRegistrationsMap.computeIfAbsent(
				objectDefinition.getObjectDefinitionId(),
				objectDefinitionId -> objectDefinitionDeployer.deploy(
					objectDefinition));
		}
	}

	@Override
	public ObjectDefinition getObjectDefinition(long objectDefinitionId)
		throws PortalException {

		return objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);
	}

	@Override
	public int getObjectDefinitionsCount(long companyId)
		throws PortalException {

		return objectDefinitionPersistence.countByCompanyId(companyId);
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		super.setAopProxy(aopProxy);

		_objectDefinitionDeployerServiceTracker = new ServiceTracker<>(
			_bundleContext, ObjectDefinitionDeployer.class,
			new ServiceTrackerCustomizer
				<ObjectDefinitionDeployer, ObjectDefinitionDeployer>() {

				@Override
				public ObjectDefinitionDeployer addingService(
					ServiceReference<ObjectDefinitionDeployer>
						serviceReference) {

					ObjectDefinitionDeployer objectDefinitionDeployer =
						_bundleContext.getService(serviceReference);

					Map<Long, List<ServiceRegistration<?>>>
						serviceRegistrationsMap = new ConcurrentHashMap<>();

					List<ObjectDefinition> objectDefinitions =
						objectDefinitionLocalService.getObjectDefinitions(
							QueryUtil.ALL_POS, QueryUtil.ALL_POS);

					for (ObjectDefinition objectDefinition :
							objectDefinitions) {

						serviceRegistrationsMap.put(
							objectDefinition.getObjectDefinitionId(),
							objectDefinitionDeployer.deploy(objectDefinition));
					}

					_serviceRegistrationsMaps.put(
						objectDefinitionDeployer, serviceRegistrationsMap);

					return objectDefinitionDeployer;
				}

				@Override
				public void modifiedService(
					ServiceReference<ObjectDefinitionDeployer> serviceReference,
					ObjectDefinitionDeployer objectDefinitionDeployer) {
				}

				@Override
				public void removedService(
					ServiceReference<ObjectDefinitionDeployer> serviceReference,
					ObjectDefinitionDeployer objectDefinitionDeployer) {

					Map<Long, List<ServiceRegistration<?>>>
						serviceRegistrationsMap =
							_serviceRegistrationsMaps.remove(
								objectDefinitionDeployer);

					for (List<ServiceRegistration<?>> serviceRegistrations :
							serviceRegistrationsMap.values()) {

						for (ServiceRegistration<?> serviceRegistration :
								serviceRegistrations) {

							serviceRegistration.unregister();
						}
					}

					_bundleContext.ungetService(serviceReference);
				}

			});

		_objectDefinitionDeployerServiceTracker.open();
	}

	@Clusterable
	@Override
	public void undeployObjectDefinition(long objectDefinitionId) {
		for (Map<Long, List<ServiceRegistration<?>>> serviceRegistrationsMap :
				_serviceRegistrationsMaps.values()) {

			List<ServiceRegistration<?>> serviceRegistrations =
				serviceRegistrationsMap.remove(objectDefinitionId);

			if (serviceRegistrations != null) {
				for (ServiceRegistration<?> serviceRegistration :
						serviceRegistrations) {

					serviceRegistration.unregister();
				}
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	@Override
	protected void deactivate() {
		super.deactivate();

		if (_objectDefinitionDeployerServiceTracker != null) {
			_objectDefinitionDeployerServiceTracker.close();
		}
	}

	private void _createTable(
		ObjectDefinition objectDefinition, List<ObjectField> objectFields) {

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			new DynamicObjectDefinitionTable(objectDefinition, objectFields);

		runSQL(dynamicObjectDefinitionTable.getCreateTableSQL());
	}

	private void _dropTable(ObjectDefinition objectDefinition) {
		String sql = "drop table " + objectDefinition.getDBTableName();

		if (_log.isDebugEnabled()) {
			_log.debug("SQL: " + sql);
		}

		runSQL(sql);
	}

	private void _validateName(long companyId, String name)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new ObjectDefinitionNameException("Name is null");
		}

		char[] nameCharArray = name.toCharArray();

		for (char c : nameCharArray) {
			if (!Validator.isChar(c) && !Validator.isDigit(c)) {
				throw new ObjectDefinitionNameException(
					"Name must only contain letters and digits");
			}
		}

		if (!Character.isUpperCase(nameCharArray[0])) {
			throw new ObjectDefinitionNameException(
				"The first character of a name must be an upper case letter");
		}

		if (nameCharArray.length > 41) {
			throw new ObjectDefinitionNameException(
				"Names must be less than 41 characters");
		}

		if (objectDefinitionPersistence.fetchByC_N(companyId, name) != null) {
			throw new DuplicateObjectDefinitionException(
				"Duplicate name " + name);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDefinitionLocalServiceImpl.class);

	private BundleContext _bundleContext;
	private ServiceTracker<ObjectDefinitionDeployer, ObjectDefinitionDeployer>
		_objectDefinitionDeployerServiceTracker;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectEntryPersistence _objectEntryPersistence;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectFieldPersistence _objectFieldPersistence;

	private final Map
		<ObjectDefinitionDeployer, Map<Long, List<ServiceRegistration<?>>>>
			_serviceRegistrationsMaps = new ConcurrentHashMap<>();

	@Reference
	private UserLocalService _userLocalService;

}