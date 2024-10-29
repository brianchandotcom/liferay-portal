/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.exception.DuplicateCPSOListTypeDefinitionRelException;
import com.liferay.commerce.product.model.CPSOListTypeDefinitionRel;
import com.liferay.commerce.product.service.base.CPSOListTypeDefinitionRelLocalServiceBaseImpl;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPSOListTypeDefinitionRel",
	service = AopService.class
)
public class CPSOListTypeDefinitionRelLocalServiceImpl
	extends CPSOListTypeDefinitionRelLocalServiceBaseImpl {

	@Override
	public CPSOListTypeDefinitionRel addCPSOListTypeDefinitionRel(
			long cpSpecificationOptionId, long listTypeDefinitionId)
		throws PortalException {

		if (hasCPSOListTypeDefinitionRel(
				cpSpecificationOptionId, listTypeDefinitionId)) {

			throw new DuplicateCPSOListTypeDefinitionRelException();
		}

		_listTypeDefinitionLocalService.getListTypeDefinition(
			listTypeDefinitionId);

		CPSOListTypeDefinitionRel cpsoListTypeDefinitionRel =
			createCPSOListTypeDefinitionRel(counterLocalService.increment());

		cpsoListTypeDefinitionRel.setCPSpecificationOptionId(
			cpSpecificationOptionId);
		cpsoListTypeDefinitionRel.setListTypeDefinitionId(listTypeDefinitionId);

		return updateCPSOListTypeDefinitionRel(cpsoListTypeDefinitionRel);
	}

	@Override
	public void deleteCPSOListTypeDefinitionRel(
			long cpSpecificationOptionId, long listTypeDefinitionId)
		throws PortalException {

		cpsoListTypeDefinitionRelPersistence.removeByC_L(
			cpSpecificationOptionId, listTypeDefinitionId);
	}

	@Override
	public void deleteCPSOListTypeDefinitionRels(long cpSpecificationOptionId) {
		cpsoListTypeDefinitionRelPersistence.removeByCPSpecificationOptionId(
			cpSpecificationOptionId);
	}

	@Override
	public CPSOListTypeDefinitionRel fetchCPSOListTypeDefinitionRel(
		long cpSpecificationOptionId, long listTypeDefinitionId) {

		return cpsoListTypeDefinitionRelPersistence.fetchByC_L(
			cpSpecificationOptionId, listTypeDefinitionId);
	}

	@Override
	public List<CPSOListTypeDefinitionRel> getCPSOListTypeDefinitionRels(
		long cpSpecificationOptionId) {

		return cpsoListTypeDefinitionRelPersistence.
			findByCPSpecificationOptionId(cpSpecificationOptionId);
	}

	@Override
	public int getCPSOListTypeDefinitionRelsCount(long listTypeDefinitionId) {
		return cpsoListTypeDefinitionRelPersistence.countByListTypeDefinitionId(
			listTypeDefinitionId);
	}

	@Override
	public boolean hasCPSOListTypeDefinitionRel(
		long cpSpecificationOptionId, long listTypeDefinitionId) {

		CPSOListTypeDefinitionRel cpsoListTypeDefinitionRel =
			cpsoListTypeDefinitionRelPersistence.fetchByC_L(
				cpSpecificationOptionId, listTypeDefinitionId);

		if (cpsoListTypeDefinitionRel != null) {
			return true;
		}

		return false;
	}

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

}