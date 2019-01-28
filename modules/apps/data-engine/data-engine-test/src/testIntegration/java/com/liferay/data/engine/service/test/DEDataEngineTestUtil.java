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

package com.liferay.data.engine.service.test;

import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.data.engine.service.DEDataDefinitionRequestBuilder;
import com.liferay.data.engine.service.DEDataDefinitionSaveRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.service.DEDataRecordCollectionDeleteModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionDeletePermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionRequestBuilder;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DEDataEngineTestUtil {

	public static void deleteDEDataRecordCollectionModelPermissions(
			long companyId, User user, long groupId,
			long deDataRecordCollectionId, String[] roleNames,
			String[] actionIds,
			DEDataRecordCollectionService deDataRecordCollectionService)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionDeleteModelPermissionsRequest.Builder
				builder =
					DEDataRecordCollectionRequestBuilder.
						deleteModelPermissionsBuilder(
							companyId, groupId, deDataRecordCollectionId,
							roleNames, actionIds);

			DEDataRecordCollectionDeleteModelPermissionsRequest
				deDataRecordCollectionDeleteModelPermissionsRequest =
					builder.build();

			deDataRecordCollectionService.execute(
				deDataRecordCollectionDeleteModelPermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	public static void deleteDEDataRecordCollectionPermissions(
			long companyId, User user, long groupId, String roleName,
			DEDataRecordCollectionService deDataRecordCollectionService)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionDeletePermissionsRequest.Builder builder =
				DEDataRecordCollectionRequestBuilder.deletePermissionsBuilder(
					companyId, groupId, roleName
				);

			DEDataRecordCollectionDeletePermissionsRequest
				deDataRecordCollectionDeletePermissionsRequest =
					builder.build();

			deDataRecordCollectionService.execute(
				deDataRecordCollectionDeletePermissionsRequest);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	public static DEDataDefinition insertDEDataDefinition(
			User user, Group group,
			DEDataDefinitionService deDataDefinitionService)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			Map<String, String> nameLabels = new HashMap() {
				{
					put("pt_BR", "Nome");
					put("en_US", "Name");
				}
			};

			DEDataDefinitionField deDataDefinitionField1 =
				new DEDataDefinitionField("name", "string");

			deDataDefinitionField1.addLabels(nameLabels);

			Map<String, String> emailLabels = new HashMap() {
				{
					put("pt_BR", "Endereço de Email");
					put("en_US", "Email Address");
				}
			};

			DEDataDefinitionField deDataDefinitionField2 =
				new DEDataDefinitionField("email", "string");

			deDataDefinitionField2.addLabels(emailLabels);

			DEDataDefinition deDataDefinition = new DEDataDefinition();

			deDataDefinition.addDescription(
				LocaleUtil.US, "Contact description");
			deDataDefinition.addDescription(
				LocaleUtil.BRAZIL, "Descrição do contato");
			deDataDefinition.addName(LocaleUtil.US, "Contact");
			deDataDefinition.addName(LocaleUtil.BRAZIL, "Contato");
			deDataDefinition.setDEDataDefinitionFields(
				Arrays.asList(deDataDefinitionField1, deDataDefinitionField2));
			deDataDefinition.setStorageType("json");

			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					deDataDefinition
				).onBehalfOf(
					user.getUserId()
				).inGroup(
					group.getGroupId()
				).build();

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				deDataDefinitionService.execute(deDataDefinitionSaveRequest);

			deDataDefinition.setDEDataDefinitionId(
				deDataDefinitionSaveResponse.getDEDataDefinitionId());

			return deDataDefinition;
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	public static DEDataDefinition insertDEDataDefinition(
			User user, Group group, String description, String name,
			DEDataDefinitionService deDataDefinitionService)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			Map<String, String> field1Labels = new HashMap() {
				{
					put("en_US", "Field Default");
				}
			};

			DEDataDefinitionField deDataDefinitionField =
				new DEDataDefinitionField("fieldDefault", "string");

			deDataDefinitionField.addLabels(field1Labels);

			DEDataDefinition deDataDefinition = new DEDataDefinition();

			deDataDefinition.addDescription(LocaleUtil.US, description);
			deDataDefinition.addName(LocaleUtil.US, name);
			deDataDefinition.setDEDataDefinitionFields(
				Arrays.asList(deDataDefinitionField));
			deDataDefinition.setStorageType("json");

			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					deDataDefinition
				).inGroup(
					group.getGroupId()
				).onBehalfOf(
					user.getUserId()
				).build();

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				deDataDefinitionService.execute(deDataDefinitionSaveRequest);

			deDataDefinition.setDEDataDefinitionId(
				deDataDefinitionSaveResponse.getDEDataDefinitionId());

			return deDataDefinition;
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	public static DEDataRecordCollection insertDEDataRecordCollection(
			User user, Group group, DEDataDefinition deDataDefinition,
			DEDataRecordCollectionService deDataRecordCollectionService)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollection deDataRecordCollection =
				new DEDataRecordCollection();

			deDataRecordCollection.addName(LocaleUtil.US, "Data record list");
			deDataRecordCollection.addDescription(
				LocaleUtil.BRAZIL, "Lista de registro de dados");
			deDataRecordCollection.addDescription(
				LocaleUtil.US, "Data record list description");
			deDataRecordCollection.addDescription(
				LocaleUtil.BRAZIL, "Descrição da lista de registro de dados");
			deDataRecordCollection.setDEDataDefinition(deDataDefinition);

			DEDataRecordCollectionSaveRequest
				deDataRecordCollectionSaveRequest =
					DEDataRecordCollectionRequestBuilder.saveBuilder(
						deDataRecordCollection
					).onBehalfOf(
						user.getUserId()
					).inGroup(
						group.getGroupId()
					).build();

			DEDataRecordCollectionSaveResponse
				deDataRecordCollectionSaveResponse =
					deDataRecordCollectionService.execute(
						deDataRecordCollectionSaveRequest);

			deDataRecordCollection.setDEDataRecordCollectionId(
				deDataRecordCollectionSaveResponse.
					getDEDataRecordCollectionId());

			return deDataRecordCollection;
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	public static DEDataRecordCollection insertDEDataRecordCollection(
			User user, Group group,
			DEDataDefinitionService deDataDefinitionService,
			DEDataRecordCollectionService deDataRecordCollectionService)
		throws Exception {

		DEDataDefinition deDataDefinition = insertDEDataDefinition(
			user, group, deDataDefinitionService);

		return insertDEDataRecordCollection(
			user, group, deDataDefinition, deDataRecordCollectionService);
	}

	public static DEDataDefinition updateDEDataDefinition(
			User user, Group group, DEDataDefinition deDataDefinition,
			DEDataDefinitionService deDataDefinitionService)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					deDataDefinition
				).onBehalfOf(
					user.getUserId()
				).inGroup(
					group.getGroupId()
				).build();

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				deDataDefinitionService.execute(deDataDefinitionSaveRequest);

			deDataDefinition.setDEDataDefinitionId(
				deDataDefinitionSaveResponse.getDEDataDefinitionId());

			return deDataDefinition;
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	public static DEDataRecordCollection updateDEDataRecordCollection(
			User user, Group group,
			DEDataRecordCollection deDataRecordCollection,
			DEDataRecordCollectionService deDataRecordCollectionService)
		throws Exception {

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataRecordCollectionSaveRequest
				deDataRecordCollectionSaveRequest =
					DEDataRecordCollectionRequestBuilder.saveBuilder(
						deDataRecordCollection
					).onBehalfOf(
						user.getUserId()
					).inGroup(
						group.getGroupId()
					).build();

			DEDataRecordCollectionSaveResponse
				deDataRecordCollectionSaveResponse =
					deDataRecordCollectionService.execute(
						deDataRecordCollectionSaveRequest);

			deDataRecordCollection.setDEDataRecordCollectionId(
				deDataRecordCollectionSaveResponse.
					getDEDataRecordCollectionId());

			return deDataRecordCollection;
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

}