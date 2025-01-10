/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.internal.resource.v1_0;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.tools.rest.builder.test.dto.v1_0.CompanyTestEntity;
import com.liferay.portal.tools.rest.builder.test.resource.v1_0.CompanyTestEntityResource;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.permission.Permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alejandro Tardín
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/company-test-entity.properties",
	scope = ServiceScope.PROTOTYPE, service = CompanyTestEntityResource.class
)
public class CompanyTestEntityResourceImpl
	extends BaseCompanyTestEntityResourceImpl {

	@Override
	public Page<CompanyTestEntity> doGetCompanyTestEntitiesPage()
		throws Exception {

		return Page.of(
			HashMapBuilder.<String, Map<String, String>>put(
				"createBatch",
				HashMapBuilder.put(
					"href",
					"http://localhost:8080/o/test/v1.0/company-test-entities" +
						"/batch"
				).put(
					"method", "POST"
				).build()
			).build(),
			_companyTestEntities);
	}

	@Override
	public CompanyTestEntity doGetCompanyTestEntity(Long companyTestEntityId)
		throws Exception {

		CompanyTestEntity companyTestEntity = _fetchCompanyTestEntity(
			companyTestEntityId);

		if (companyTestEntity == null) {
			throw new NoSuchModelException();
		}

		return companyTestEntity;
	}

	@Override
	public CompanyTestEntity doGetCompanyTestEntityByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CompanyTestEntity companyTestEntity = _fetchCompanyTestEntity(
			externalReferenceCode);

		if (companyTestEntity == null) {
			throw new NoSuchModelException();
		}

		return companyTestEntity;
	}

	@Override
	public CompanyTestEntity doPostCompanyTestEntity(
			CompanyTestEntity companyTestEntity)
		throws Exception {

		CompanyTestEntity existingCompanyTestEntity = _fetchCompanyTestEntity(
			companyTestEntity.getExternalReferenceCode());

		if (existingCompanyTestEntity != null) {
			throw new DuplicateExternalReferenceCodeException();
		}

		companyTestEntity.setId(Long.valueOf(_companyTestEntities.size()));
		companyTestEntity.setPermissions((Permission[])null);

		_companyTestEntities.add(companyTestEntity);

		return companyTestEntity;
	}

	@Override
	public CompanyTestEntity doPutCompanyTestEntity(
			Long companyTestEntityId, CompanyTestEntity companyTestEntity)
		throws Exception {

		CompanyTestEntity existingCompanyTestEntity = _fetchCompanyTestEntity(
			companyTestEntity.getExternalReferenceCode());

		if ((existingCompanyTestEntity != null) &&
			!Objects.equals(
				existingCompanyTestEntity.getId(), companyTestEntityId)) {

			throw new DuplicateExternalReferenceCodeException();
		}

		existingCompanyTestEntity = _fetchCompanyTestEntity(
			companyTestEntityId);

		if (existingCompanyTestEntity == null) {
			throw new NoSuchModelException();
		}

		companyTestEntity.setExternalReferenceCode(
			existingCompanyTestEntity.getExternalReferenceCode());
		companyTestEntity.setId(companyTestEntityId);
		companyTestEntity.setPermissions((Permission[])null);

		_companyTestEntities.set(
			Math.toIntExact(companyTestEntityId), companyTestEntity);

		return companyTestEntity;
	}

	@Override
	public CompanyTestEntity doPutCompanyTestEntityByExternalReferenceCode(
			String externalReferenceCode, CompanyTestEntity companyTestEntity)
		throws Exception {

		companyTestEntity.setExternalReferenceCode(externalReferenceCode);

		CompanyTestEntity existingCompanyTestEntity = _fetchCompanyTestEntity(
			externalReferenceCode);

		if (existingCompanyTestEntity == null) {
			return postCompanyTestEntity(companyTestEntity);
		}

		return putCompanyTestEntity(
			existingCompanyTestEntity.getId(), companyTestEntity);
	}

	@Override
	public Page<Permission> getCompanyTestEntityPermissionsPage(
			Long companyTestEntityId, String roleNames)
		throws Exception {

		CompanyTestEntity companyTestEntity = doGetCompanyTestEntity(
			companyTestEntityId);

		if (!_permissions.containsKey(companyTestEntity.getId())) {
			_permissions.put(
				companyTestEntity.getId(),
				new Permission[] {
					new Permission() {
						{
							setActionIds(
								new String[] {
									"DELETE", "PERMISSIONS", "UPDATE", "VIEW"
								});
							setRoleName("Owner");
						}
					}
				});
		}

		return Page.of(
			Arrays.asList(_permissions.get(companyTestEntity.getId())));
	}

	@Override
	public Page<Permission> putCompanyTestEntityPermissionsPage(
			Long companyTestEntityId, Permission[] permissions)
		throws Exception {

		for (Permission permission : permissions) {
			_roleLocalService.getRole(
				CompanyThreadLocal.getCompanyId(), permission.getRoleName());
		}

		CompanyTestEntity companyTestEntity = getCompanyTestEntity(
			companyTestEntityId);

		_permissions.put(companyTestEntity.getId(), permissions);

		return getCompanyTestEntityPermissionsPage(companyTestEntityId, null);
	}

	private CompanyTestEntity _fetchCompanyTestEntity(long id)
		throws Exception {

		if (_companyTestEntities.size() > id) {
			return _companyTestEntities.get(Math.toIntExact(id));
		}

		return null;
	}

	private CompanyTestEntity _fetchCompanyTestEntity(
		String externalReferenceCode) {

		for (CompanyTestEntity companyTestEntity : _companyTestEntities) {
			if (Objects.equals(
					externalReferenceCode,
					companyTestEntity.getExternalReferenceCode())) {

				return companyTestEntity;
			}
		}

		return null;
	}

	private static final List<CompanyTestEntity> _companyTestEntities =
		new ArrayList<>();
	private static final Map<Long, Permission[]> _permissions = new HashMap<>();

	@Reference
	private RoleLocalService _roleLocalService;

}