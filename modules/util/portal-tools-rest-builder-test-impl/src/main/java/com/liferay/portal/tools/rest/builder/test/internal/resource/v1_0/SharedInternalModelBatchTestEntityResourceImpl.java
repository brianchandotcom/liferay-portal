/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.internal.resource.v1_0;

import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.rest.builder.test.dto.v1_0.SharedInternalModelBatchTestEntity;
import com.liferay.portal.tools.rest.builder.test.resource.v1_0.SharedInternalModelBatchTestEntityResource;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alejandro Tardín
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/shared-internal-model-batch-test-entity.properties",
	property = "export.import.vulcan.batch.engine.task.item.delegate=true",
	scope = ServiceScope.PROTOTYPE,
	service = SharedInternalModelBatchTestEntityResource.class
)
public class SharedInternalModelBatchTestEntityResourceImpl
	extends BaseSharedInternalModelBatchTestEntityResourceImpl
	implements ExportImportVulcanBatchEngineTaskItemDelegate
		<SharedInternalModelBatchTestEntity> {

	@Override
	public void deleteSharedInternalModelBatchTestEntityByExternalReferenceCode(
		String externalReferenceCode) {

		SharedInternalModelBatchTestEntity sharedInternalModelBatchTestEntity =
			_fetchSharedInternalModelBatchTestEntity(externalReferenceCode);

		if (sharedInternalModelBatchTestEntity != null) {
			_sharedInternalModelBatchTestEntities.remove(
				sharedInternalModelBatchTestEntity.getExternalReferenceCode());
		}
	}

	@Override
	public ExportImportDescriptor getExportImportDescriptor() {
		return new ExportImportDescriptor() {

			@Override
			public String getKey() {
				return SharedInternalModelBatchTestEntityResourceImpl.class.
					getName();
			}

			@Override
			public String getLabelLanguageKey() {
				return "shared-internal-model-batch-test-entity";
			}

			@Override
			public String getModelClassName() {
				return "com_liferay_portal_tools_rest_builder_test_portlet_" +
					"BatchTestEntityPortlet";
			}

			@Override
			public String getPortletId() {
				return "com_liferay_portal_tools_rest_builder_test_portlet_" +
					"BatchTestEntityPortlet";
			}

			@Override
			public ExportImportVulcanBatchEngineTaskItemDelegate.Scope
				getScope() {

				return ExportImportVulcanBatchEngineTaskItemDelegate.Scope.
					COMPANY;
			}

			@Override
			public boolean isApplicableExternalReferenceCode(
				String externalReferenceCode) {

				SharedInternalModelBatchTestEntity
					sharedInternalModelBatchTestEntity =
						_fetchSharedInternalModelBatchTestEntity(
							externalReferenceCode);

				if (sharedInternalModelBatchTestEntity != null) {
					return true;
				}

				return false;
			}

		};
	}

	@Override
	public Page<SharedInternalModelBatchTestEntity>
		getSharedInternalModelBatchTestEntitiesPage() {

		return Page.of(_sharedInternalModelBatchTestEntities.values());
	}

	@Override
	public SharedInternalModelBatchTestEntity
			getSharedInternalModelBatchTestEntityByExternalReferenceCode(
				String externalReferenceCode)
		throws Exception {

		SharedInternalModelBatchTestEntity sharedInternalModelBatchTestEntity =
			_fetchSharedInternalModelBatchTestEntity(externalReferenceCode);

		if (sharedInternalModelBatchTestEntity == null) {
			throw new NoSuchModelException();
		}

		return sharedInternalModelBatchTestEntity;
	}

	@Override
	public SharedInternalModelBatchTestEntity
		postSharedInternalModelBatchTestEntity(
			SharedInternalModelBatchTestEntity
				sharedInternalModelBatchTestEntity) {

		if (Validator.isNull(
				sharedInternalModelBatchTestEntity.
					getExternalReferenceCode())) {

			sharedInternalModelBatchTestEntity.setExternalReferenceCode(
				StringUtil.randomString());
		}

		_sharedInternalModelBatchTestEntities.put(
			sharedInternalModelBatchTestEntity.getExternalReferenceCode(),
			sharedInternalModelBatchTestEntity);

		return sharedInternalModelBatchTestEntity;
	}

	@Override
	public SharedInternalModelBatchTestEntity
		putSharedInternalModelBatchTestEntityByExternalReferenceCode(
			String externalReferenceCode,
			SharedInternalModelBatchTestEntity
				sharedInternalModelBatchTestEntity) {

		SharedInternalModelBatchTestEntity
			existingSharedInternalModelBatchTestEntity =
				_fetchSharedInternalModelBatchTestEntity(externalReferenceCode);

		if (existingSharedInternalModelBatchTestEntity == null) {
			return postSharedInternalModelBatchTestEntity(
				sharedInternalModelBatchTestEntity);
		}

		sharedInternalModelBatchTestEntity.setExternalReferenceCode(
			externalReferenceCode);
		sharedInternalModelBatchTestEntity.setName(
			sharedInternalModelBatchTestEntity.getName());

		_sharedInternalModelBatchTestEntities.put(
			sharedInternalModelBatchTestEntity.getExternalReferenceCode(),
			sharedInternalModelBatchTestEntity);

		return sharedInternalModelBatchTestEntity;
	}

	private SharedInternalModelBatchTestEntity
		_fetchSharedInternalModelBatchTestEntity(String externalReferenceCode) {

		for (SharedInternalModelBatchTestEntity
				sharedInternalModelBatchTestEntity :
					_sharedInternalModelBatchTestEntities.values()) {

			if (Objects.equals(
					externalReferenceCode,
					sharedInternalModelBatchTestEntity.
						getExternalReferenceCode())) {

				return sharedInternalModelBatchTestEntity;
			}
		}

		return null;
	}

	private static final Map<String, SharedInternalModelBatchTestEntity>
		_sharedInternalModelBatchTestEntities = new TreeMap<>();

}