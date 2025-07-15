/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.internal.resource.v1_0;

import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.tools.rest.builder.test.dto.v1_0.BatchTestEntity;
import com.liferay.portal.tools.rest.builder.test.resource.v1_0.BatchTestEntityResource;
import com.liferay.portal.vulcan.fields.NestedFieldsSupplier;
import com.liferay.portal.vulcan.pagination.Page;

import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alejandro Tardín
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/batch-test-entity.properties",
	scope = ServiceScope.PROTOTYPE, service = BatchTestEntityResource.class
)
public class BatchTestEntityResourceImpl
	extends BaseBatchTestEntityResourceImpl
	implements ExportImportVulcanBatchEngineTaskItemDelegate<BatchTestEntity> {

	@Override
	public Response deleteBatchTestEntityByExternalReferenceCode(
		String externalReferenceCode) {

		BatchTestEntity batchTestEntity = _fetchBatchTestEntity(
			externalReferenceCode);

		_batchTestEntities.remove(batchTestEntity);

		return Response.status(
			204
		).build();
	}

	@Override
	public Page<BatchTestEntity> getBatchTestEntitiesPage() {
		List<BatchTestEntity> batchTestEntities = new ArrayList<>();

		for (BatchTestEntity batchTestEntity : _batchTestEntities) {
			BatchTestEntity getBatchTestEntity = new BatchTestEntity();

			getBatchTestEntity.setExternalReferenceCode(
				batchTestEntity.getExternalReferenceCode());
			getBatchTestEntity.setId(batchTestEntity.getId());
			getBatchTestEntity.setName(batchTestEntity.getName());
			getBatchTestEntity.setNestedField(
				() -> NestedFieldsSupplier.supply(
					"nestedField",
					nestedField -> batchTestEntity.getNestedField()));

			batchTestEntities.add(getBatchTestEntity);
		}

		return Page.of(batchTestEntities);
	}

	@Override
	public BatchTestEntity getBatchTestEntity(Long batchTestEntityId)
		throws NoSuchModelException {

		BatchTestEntity batchTestEntity = _fetchBatchTestEntity(
			batchTestEntityId);

		if (batchTestEntity == null) {
			throw new NoSuchModelException();
		}

		BatchTestEntity getBatchTestEntity = new BatchTestEntity();

		getBatchTestEntity.setExternalReferenceCode(
			batchTestEntity.getExternalReferenceCode());
		getBatchTestEntity.setId(batchTestEntity.getId());
		getBatchTestEntity.setName(batchTestEntity.getName());
		getBatchTestEntity.setNestedField(
			() -> NestedFieldsSupplier.supply(
				"nestedField",
				nestedField -> batchTestEntity.getNestedField()));

		return getBatchTestEntity;
	}

	@Override
	public BatchTestEntity getBatchTestEntityByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		BatchTestEntity batchTestEntity = _fetchBatchTestEntity(
			externalReferenceCode);

		if (batchTestEntity == null) {
			throw new NoSuchModelException();
		}

		return batchTestEntity;
	}

	@Override
	public String getPortletId() {
		return "com_liferay_portal_tools_rest_builder_test_portlet_" +
			"BatchTestEntityPortlet";
	}

	@Override
	public Scope getScope() {
		return Scope.COMPANY;
	}

	@Override
	public BatchTestEntity postBatchTestEntity(
		BatchTestEntity batchTestEntity) {

		batchTestEntity.setId(Long.valueOf(_batchTestEntities.size()));

		_batchTestEntities.add(batchTestEntity);

		return batchTestEntity;
	}

	@Override
	public BatchTestEntity putBatchTestEntityByExternalReferenceCode(
		String externalReferenceCode, BatchTestEntity batchTestEntity) {

		batchTestEntity.setExternalReferenceCode(externalReferenceCode);

		BatchTestEntity existingBatchTestEntity = _fetchBatchTestEntity(
			externalReferenceCode);

		if (existingBatchTestEntity == null) {
			return postBatchTestEntity(batchTestEntity);
		}

		batchTestEntity.setId(existingBatchTestEntity.getId());
		batchTestEntity.setName(batchTestEntity.getName());

		return batchTestEntity;
	}

	private BatchTestEntity _fetchBatchTestEntity(long id) {
		if (_batchTestEntities.size() > id) {
			return _batchTestEntities.get(Math.toIntExact(id));
		}

		return null;
	}

	private BatchTestEntity _fetchBatchTestEntity(
		String externalReferenceCode) {

		for (BatchTestEntity batchTestEntity : _batchTestEntities) {
			if (Objects.equals(
					externalReferenceCode,
					batchTestEntity.getExternalReferenceCode())) {

				return batchTestEntity;
			}
		}

		return null;
	}

	private static final List<BatchTestEntity> _batchTestEntities =
		new ArrayList<>();

}