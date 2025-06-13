/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.internal.resource.v1_0;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.tools.rest.builder.test.dto.v1_0.MultipleScopedTestEntity;
import com.liferay.portal.tools.rest.builder.test.resource.v1_0.MultipleScopedTestEntityResource;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alejandro Tardín
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/multiple-scoped-test-entity.properties",
	scope = ServiceScope.PROTOTYPE,
	service = MultipleScopedTestEntityResource.class
)
public class MultipleScopedTestEntityResourceImpl
	extends BaseMultipleScopedTestEntityResourceImpl {

	@Override
	public void
			deleteAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
				Long assetLibraryId, String externalReferenceCode)
		throws Exception {

		_deleteMultipleScopedTestEntity(
			String.valueOf(assetLibraryId), externalReferenceCode, 0L);
	}

	@Override
	public void deleteMultipleScopedTestEntityByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		_deleteMultipleScopedTestEntity(null, externalReferenceCode, 0L);
	}

	@Override
	public void deleteSiteMultipleScopedTestEntityByExternalReferenceCode(
			Long siteId, String externalReferenceCode)
		throws Exception {

		_deleteMultipleScopedTestEntity(null, externalReferenceCode, siteId);
	}

	@Override
	public Page<MultipleScopedTestEntity>
			getAssetLibraryMultipleScopedTestEntitiesPage(Long assetLibraryId)
		throws Exception {

		List<MultipleScopedTestEntity> multipleScopedTestEntities =
			new ArrayList<>();

		for (MultipleScopedTestEntity multipleScopedTestEntity :
				_multipleScopedTestEntities) {

			if (Objects.equals(
					String.valueOf(assetLibraryId),
					multipleScopedTestEntity.getAssetLibraryKey())) {

				multipleScopedTestEntities.add(multipleScopedTestEntity);
			}
		}

		return Page.of(
			HashMapBuilder.<String, Map<String, String>>put(
				"createBatch",
				HashMapBuilder.put(
					"href",
					"http://localhost:8080/o/test/v1.0/asset-libraries/" +
						assetLibraryId + "/multiple-scoped-test-entities/batch"
				).put(
					"method", "POST"
				).build()
			).build(),
			multipleScopedTestEntities);
	}

	@Override
	public MultipleScopedTestEntity
			getAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
				Long assetLibraryId, String externalReferenceCode)
		throws Exception {

		MultipleScopedTestEntity multipleScopedTestEntity =
			_fetchMultipleScopedTestEntityByExternalReferenceCode(
				String.valueOf(assetLibraryId), externalReferenceCode, 0L);

		if (multipleScopedTestEntity == null) {
			throw new NoSuchModelException();
		}

		return multipleScopedTestEntity;
	}

	@Override
	public Page<MultipleScopedTestEntity> getMultipleScopedTestEntitiesPage()
		throws Exception {

		List<MultipleScopedTestEntity> multipleScopedTestEntities =
			new ArrayList<>();

		for (MultipleScopedTestEntity multipleScopedTestEntity :
				_multipleScopedTestEntities) {

			if ((multipleScopedTestEntity.getAssetLibraryKey() == null) &&
				(multipleScopedTestEntity.getSiteId() == 0L)) {

				multipleScopedTestEntities.add(multipleScopedTestEntity);
			}
		}

		return Page.of(
			HashMapBuilder.<String, Map<String, String>>put(
				"createBatch",
				HashMapBuilder.put(
					"href",
					"http://localhost:8080/o/test/v1.0/multiple-scoped-test-" +
						"entities/batch"
				).put(
					"method", "POST"
				).build()
			).build(),
			multipleScopedTestEntities);
	}

	@Override
	public MultipleScopedTestEntity
			getMultipleScopedTestEntityByExternalReferenceCode(
				String externalReferenceCode)
		throws Exception {

		MultipleScopedTestEntity multipleScopedTestEntity =
			_fetchMultipleScopedTestEntityByExternalReferenceCode(
				null, externalReferenceCode, 0L);

		if (multipleScopedTestEntity == null) {
			throw new NoSuchModelException();
		}

		return multipleScopedTestEntity;
	}

	@Override
	public Page<MultipleScopedTestEntity> getSiteMultipleScopedTestEntitiesPage(
			Long siteId)
		throws Exception {

		List<MultipleScopedTestEntity> multipleScopedTestEntities =
			new ArrayList<>();

		for (MultipleScopedTestEntity multipleScopedTestEntity :
				_multipleScopedTestEntities) {

			if (Objects.equals(siteId, multipleScopedTestEntity.getSiteId())) {
				multipleScopedTestEntities.add(multipleScopedTestEntity);
			}
		}

		return Page.of(
			HashMapBuilder.<String, Map<String, String>>put(
				"createBatch",
				HashMapBuilder.put(
					"href",
					"http://localhost:8080/o/test/v1.0/sites/" + siteId +
						"/multiple-scoped-test-entities/batch"
				).put(
					"method", "POST"
				).build()
			).build(),
			multipleScopedTestEntities);
	}

	@Override
	public MultipleScopedTestEntity
			getSiteMultipleScopedTestEntityByExternalReferenceCode(
				Long siteId, String externalReferenceCode)
		throws Exception {

		MultipleScopedTestEntity multipleScopedTestEntity =
			_fetchMultipleScopedTestEntityByExternalReferenceCode(
				null, externalReferenceCode, siteId);

		if (multipleScopedTestEntity == null) {
			throw new NoSuchModelException();
		}

		return multipleScopedTestEntity;
	}

	@Override
	public MultipleScopedTestEntity
			patchAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
				Long assetLibraryId, String externalReferenceCode,
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		MultipleScopedTestEntity existingMultipleScopedTestEntity =
			getAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
				assetLibraryId, externalReferenceCode);

		_patchProperties(
			multipleScopedTestEntity, existingMultipleScopedTestEntity);

		externalReferenceCode =
			multipleScopedTestEntity.getExternalReferenceCode();

		preparePatch(
			multipleScopedTestEntity, existingMultipleScopedTestEntity);

		return putAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
			assetLibraryId, externalReferenceCode,
			existingMultipleScopedTestEntity);
	}

	@Override
	public MultipleScopedTestEntity
			patchMultipleScopedTestEntityByExternalReferenceCode(
				String externalReferenceCode,
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		MultipleScopedTestEntity existingMultipleScopedTestEntity =
			getMultipleScopedTestEntityByExternalReferenceCode(
				externalReferenceCode);

		_patchProperties(
			multipleScopedTestEntity, existingMultipleScopedTestEntity);

		externalReferenceCode =
			multipleScopedTestEntity.getExternalReferenceCode();

		preparePatch(
			multipleScopedTestEntity, existingMultipleScopedTestEntity);

		return putMultipleScopedTestEntityByExternalReferenceCode(
			externalReferenceCode, existingMultipleScopedTestEntity);
	}

	@Override
	public MultipleScopedTestEntity
			patchSiteMultipleScopedTestEntityByExternalReferenceCode(
				Long siteId, String externalReferenceCode,
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		MultipleScopedTestEntity existingMultipleScopedTestEntity =
			getSiteMultipleScopedTestEntityByExternalReferenceCode(
				siteId, externalReferenceCode);

		_patchProperties(
			multipleScopedTestEntity, existingMultipleScopedTestEntity);

		externalReferenceCode =
			multipleScopedTestEntity.getExternalReferenceCode();

		preparePatch(
			multipleScopedTestEntity, existingMultipleScopedTestEntity);

		return putSiteMultipleScopedTestEntityByExternalReferenceCode(
			siteId, externalReferenceCode, existingMultipleScopedTestEntity);
	}

	@Override
	public MultipleScopedTestEntity
			postAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
				Long assetLibraryId, String externalReferenceCode,
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		MultipleScopedTestEntity existingMultipleScopedTestEntity =
			_fetchMultipleScopedTestEntityByExternalReferenceCode(
				String.valueOf(assetLibraryId), externalReferenceCode, 0L);

		if (existingMultipleScopedTestEntity != null) {
			throw new DuplicateExternalReferenceCodeException();
		}

		multipleScopedTestEntity.setAssetLibraryKey(
			String.valueOf(assetLibraryId));
		multipleScopedTestEntity.setSiteId(0L);

		_multipleScopedTestEntities.add(multipleScopedTestEntity);

		return multipleScopedTestEntity;
	}

	@Override
	public MultipleScopedTestEntity
			postMultipleScopedTestEntityByExternalReferenceCode(
				String externalReferenceCode,
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		MultipleScopedTestEntity existingMultipleScopedTestEntity =
			_fetchMultipleScopedTestEntityByExternalReferenceCode(
				null, externalReferenceCode, 0L);

		if (existingMultipleScopedTestEntity != null) {
			throw new DuplicateExternalReferenceCodeException();
		}

		multipleScopedTestEntity.setAssetLibraryKey((String)null);
		multipleScopedTestEntity.setSiteId(0L);

		_multipleScopedTestEntities.add(multipleScopedTestEntity);

		return multipleScopedTestEntity;
	}

	@Override
	public MultipleScopedTestEntity
			postSiteMultipleScopedTestEntityByExternalReferenceCode(
				Long siteId, String externalReferenceCode,
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		MultipleScopedTestEntity existingMultipleScopedTestEntity =
			_fetchMultipleScopedTestEntityByExternalReferenceCode(
				null, externalReferenceCode, siteId);

		if (existingMultipleScopedTestEntity != null) {
			throw new DuplicateExternalReferenceCodeException();
		}

		multipleScopedTestEntity.setAssetLibraryKey((String)null);
		multipleScopedTestEntity.setSiteId(siteId);

		_multipleScopedTestEntities.add(multipleScopedTestEntity);

		return multipleScopedTestEntity;
	}

	@Override
	public MultipleScopedTestEntity
			putAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
				Long assetLibraryId, String externalReferenceCode,
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		MultipleScopedTestEntity existingMultipleScopedTestEntity =
			_fetchMultipleScopedTestEntityByExternalReferenceCode(
				String.valueOf(assetLibraryId), externalReferenceCode, 0L);

		if (existingMultipleScopedTestEntity != null) {
			return postAssetLibraryMultipleScopedTestEntityByExternalReferenceCode(
				assetLibraryId, externalReferenceCode,
				multipleScopedTestEntity);
		}

		multipleScopedTestEntity.setAssetLibraryKey(
			String.valueOf(assetLibraryId));
		multipleScopedTestEntity.setSiteId(0L);

		_putMultipleScopedTestEntity(
			multipleScopedTestEntity,
			existingMultipleScopedTestEntity.getExternalReferenceCode());

		return multipleScopedTestEntity;
	}

	@Override
	public MultipleScopedTestEntity
			putMultipleScopedTestEntityByExternalReferenceCode(
				String externalReferenceCode,
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		MultipleScopedTestEntity existingMultipleScopedTestEntity =
			_fetchMultipleScopedTestEntityByExternalReferenceCode(
				null, externalReferenceCode, 0L);

		if (existingMultipleScopedTestEntity != null) {
			return postMultipleScopedTestEntityByExternalReferenceCode(
				null, multipleScopedTestEntity);
		}

		multipleScopedTestEntity.setAssetLibraryKey((String)null);
		multipleScopedTestEntity.setSiteId(0L);

		_putMultipleScopedTestEntity(
			multipleScopedTestEntity,
			existingMultipleScopedTestEntity.getExternalReferenceCode());

		return multipleScopedTestEntity;
	}

	@Override
	public MultipleScopedTestEntity
			putSiteMultipleScopedTestEntityByExternalReferenceCode(
				Long siteId, String externalReferenceCode,
				MultipleScopedTestEntity multipleScopedTestEntity)
		throws Exception {

		MultipleScopedTestEntity existingMultipleScopedTestEntity =
			_fetchMultipleScopedTestEntityByExternalReferenceCode(
				null, externalReferenceCode, siteId);

		if (existingMultipleScopedTestEntity != null) {
			return postSiteMultipleScopedTestEntityByExternalReferenceCode(
				siteId, externalReferenceCode, multipleScopedTestEntity);
		}

		multipleScopedTestEntity.setAssetLibraryKey((String)null);
		multipleScopedTestEntity.setSiteId(siteId);

		_putMultipleScopedTestEntity(
			multipleScopedTestEntity,
			existingMultipleScopedTestEntity.getExternalReferenceCode());

		return multipleScopedTestEntity;
	}

	private void _deleteMultipleScopedTestEntity(
		String assetLibraryKey,
		String existingMultipleScopedTestEntityExternalReferenceCode,
		Long siteId) {

		ListIterator<MultipleScopedTestEntity> iterator =
			_multipleScopedTestEntities.listIterator();

		while (iterator.hasNext()) {
			MultipleScopedTestEntity existingMultipleScopedTestEntity =
				iterator.next();

			if (Objects.equals(
					existingMultipleScopedTestEntity.getExternalReferenceCode(),
					existingMultipleScopedTestEntityExternalReferenceCode) &&
				Objects.equals(
					assetLibraryKey,
					existingMultipleScopedTestEntity.getAssetLibraryKey()) &&
				Objects.equals(
					siteId, existingMultipleScopedTestEntity.getSiteId())) {

				iterator.remove();
			}
		}
	}

	private MultipleScopedTestEntity
			_fetchMultipleScopedTestEntityByExternalReferenceCode(
				String assetLibraryKey, String externalReferenceCode,
				Long siteId)
		throws Exception {

		for (MultipleScopedTestEntity multipleScopedTestEntity :
				_multipleScopedTestEntities) {

			if (Objects.equals(
					assetLibraryKey,
					multipleScopedTestEntity.getAssetLibraryKey()) &&
				Objects.equals(
					externalReferenceCode,
					multipleScopedTestEntity.getExternalReferenceCode()) &&
				Objects.equals(siteId, multipleScopedTestEntity.getSiteId())) {

				return multipleScopedTestEntity;
			}
		}

		return null;
	}

	private void _patchProperties(
		MultipleScopedTestEntity multipleScopedTestEntity,
		MultipleScopedTestEntity existingMultipleScopedTestEntity) {

		if (multipleScopedTestEntity.getDateCreated() != null) {
			existingMultipleScopedTestEntity.setDateCreated(
				multipleScopedTestEntity.getDateCreated());
		}

		if (multipleScopedTestEntity.getDateModified() != null) {
			existingMultipleScopedTestEntity.setDateModified(
				multipleScopedTestEntity.getDateModified());
		}

		if (multipleScopedTestEntity.getDescription() != null) {
			existingMultipleScopedTestEntity.setDescription(
				multipleScopedTestEntity.getDescription());
		}

		if (multipleScopedTestEntity.getExternalReferenceCode() != null) {
			existingMultipleScopedTestEntity.setExternalReferenceCode(
				multipleScopedTestEntity.getExternalReferenceCode());
		}

		if (multipleScopedTestEntity.getPermissions() != null) {
			existingMultipleScopedTestEntity.setPermissions(
				multipleScopedTestEntity.getPermissions());
		}
	}

	private void _putMultipleScopedTestEntity(
		MultipleScopedTestEntity multipleScopedTestEntity,
		String existingMultipleScopedTestEntityExternalReferenceCode) {

		ListIterator<MultipleScopedTestEntity> iterator =
			_multipleScopedTestEntities.listIterator();

		while (iterator.hasNext()) {
			MultipleScopedTestEntity existingMultipleScopedTestEntity =
				iterator.next();

			if (Objects.equals(
					existingMultipleScopedTestEntity.getExternalReferenceCode(),
					existingMultipleScopedTestEntityExternalReferenceCode)) {

				iterator.set(multipleScopedTestEntity);
			}
		}
	}

	private static final List<MultipleScopedTestEntity>
		_multipleScopedTestEntities = new ArrayList<>();

}