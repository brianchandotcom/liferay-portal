/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.internal.util.CPDefinitionLocalServiceCircularDependencyUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.service.base.CPDefinitionSpecificationOptionValueLocalServiceBaseImpl;
import com.liferay.commerce.product.service.persistence.CPDefinitionPersistence;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue",
	service = AopService.class
)
public class CPDefinitionSpecificationOptionValueLocalServiceImpl
	extends CPDefinitionSpecificationOptionValueLocalServiceBaseImpl {

	@Override
	public CPDefinitionSpecificationOptionValue
			addCPDefinitionSpecificationOptionValue(
				long cpDefinitionId, long cpSpecificationOptionId,
				long cpOptionCategoryId, Map<Locale, String> valueMap,
				double priority, ServiceContext serviceContext)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionPersistence.findByPrimaryKey(
			cpDefinitionId);
		User user = _userLocalService.getUser(serviceContext.getUserId());

		long cpDefinitionSpecificationOptionValueId =
			counterLocalService.increment();

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				cpDefinitionSpecificationOptionValuePersistence.create(
					cpDefinitionSpecificationOptionValueId);

		if (CPDefinitionLocalServiceCircularDependencyUtil.isVersionable(
				cpDefinitionId)) {

			cpDefinition =
				CPDefinitionLocalServiceCircularDependencyUtil.copyCPDefinition(
					cpDefinitionId);

			cpDefinitionId = cpDefinition.getCPDefinitionId();
		}

		cpDefinitionSpecificationOptionValue.setGroupId(
			cpDefinition.getGroupId());
		cpDefinitionSpecificationOptionValue.setCompanyId(user.getCompanyId());
		cpDefinitionSpecificationOptionValue.setUserId(user.getUserId());
		cpDefinitionSpecificationOptionValue.setUserName(user.getFullName());
		cpDefinitionSpecificationOptionValue.setCPDefinitionId(
			cpDefinition.getCPDefinitionId());
		cpDefinitionSpecificationOptionValue.setCPSpecificationOptionId(
			cpSpecificationOptionId);
		cpDefinitionSpecificationOptionValue.setCPOptionCategoryId(
			cpOptionCategoryId);
		cpDefinitionSpecificationOptionValue.setValueMap(valueMap);
		cpDefinitionSpecificationOptionValue.setPriority(priority);
		cpDefinitionSpecificationOptionValue.setExpandoBridgeAttributes(
			serviceContext);

		cpDefinitionSpecificationOptionValue =
			cpDefinitionSpecificationOptionValuePersistence.update(
				cpDefinitionSpecificationOptionValue);

		// Commerce product definition

		_reindexCPDefinition(cpDefinitionId);

		return cpDefinitionSpecificationOptionValue;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPDefinitionSpecificationOptionValue
			deleteCPDefinitionSpecificationOptionValue(
				CPDefinitionSpecificationOptionValue
					cpDefinitionSpecificationOptionValue)
		throws PortalException {

		return cpDefinitionSpecificationOptionValueLocalService.
			deleteCPDefinitionSpecificationOptionValue(
				cpDefinitionSpecificationOptionValue, true);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPDefinitionSpecificationOptionValue
			deleteCPDefinitionSpecificationOptionValue(
				CPDefinitionSpecificationOptionValue
					cpDefinitionSpecificationOptionValue,
				boolean makeCopy)
		throws PortalException {

		if (makeCopy &&
			CPDefinitionLocalServiceCircularDependencyUtil.isVersionable(
				cpDefinitionSpecificationOptionValue.getCPDefinitionId())) {

			try {
				CPDefinition newCPDefinition =
					CPDefinitionLocalServiceCircularDependencyUtil.
						copyCPDefinition(
							cpDefinitionSpecificationOptionValue.
								getCPDefinitionId());

				cpDefinitionSpecificationOptionValue =
					cpDefinitionSpecificationOptionValuePersistence.
						findByC_CSOVI(
							newCPDefinition.getCPDefinitionId(),
							cpDefinitionSpecificationOptionValue.
								getCPDefinitionSpecificationOptionValueId());
			}
			catch (PortalException portalException) {
				throw new SystemException(portalException);
			}
		}

		// Commerce product definition specification option value

		cpDefinitionSpecificationOptionValuePersistence.remove(
			cpDefinitionSpecificationOptionValue);

		// Expando

		_expandoRowLocalService.deleteRows(
			cpDefinitionSpecificationOptionValue.
				getCPDefinitionSpecificationOptionValueId());

		_reindexCPDefinition(
			cpDefinitionSpecificationOptionValue.getCPDefinitionId());

		return cpDefinitionSpecificationOptionValue;
	}

	@Override
	public CPDefinitionSpecificationOptionValue
			deleteCPDefinitionSpecificationOptionValue(
				long cpDefinitionSpecificationOptionValueId)
		throws PortalException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				cpDefinitionSpecificationOptionValuePersistence.
					findByPrimaryKey(cpDefinitionSpecificationOptionValueId);

		return cpDefinitionSpecificationOptionValueLocalService.
			deleteCPDefinitionSpecificationOptionValue(
				cpDefinitionSpecificationOptionValue);
	}

	@Override
	public void deleteCPDefinitionSpecificationOptionValues(long cpDefinitionId)
		throws PortalException {

		cpDefinitionSpecificationOptionValueLocalService.
			deleteCPDefinitionSpecificationOptionValues(cpDefinitionId, true);
	}

	public void deleteCPDefinitionSpecificationOptionValues(
			long cpDefinitionId, boolean makeCopy)
		throws PortalException {

		List<CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues =
				getCPDefinitionSpecificationOptionValues(
					cpDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		// Commerce product definition specification option value

		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					cpDefinitionSpecificationOptionValues) {

			cpDefinitionSpecificationOptionValueLocalService.
				deleteCPDefinitionSpecificationOptionValue(
					cpDefinitionSpecificationOptionValue, makeCopy);
		}

		// Commerce product definition

		_reindexCPDefinition(cpDefinitionId);
	}

	@Override
	public void deleteCPSpecificationOptionDefinitionValues(
			long cpSpecificationOptionId)
		throws PortalException {

		List<CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues =
				getCPDefinitionSpecificationOptionValues(
					cpSpecificationOptionId);

		// Commerce product definition specification option value

		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					cpDefinitionSpecificationOptionValues) {

			cpDefinitionSpecificationOptionValueLocalService.
				deleteCPDefinitionSpecificationOptionValue(
					cpDefinitionSpecificationOptionValue);

			// Commerce product definition

			_reindexCPDefinition(
				cpDefinitionSpecificationOptionValue.getCPDefinitionId());
		}
	}

	@Override
	public CPDefinitionSpecificationOptionValue
		fetchCPDefinitionSpecificationOptionValue(
			long cpDefinitionId, long cpDefinitionSpecificationOptionValueId) {

		return cpDefinitionSpecificationOptionValuePersistence.fetchByC_CSOVI(
			cpDefinitionId, cpDefinitionSpecificationOptionValueId);
	}

	@Override
	public List<CPDefinitionSpecificationOptionValue>
		getCPDefinitionSpecificationOptionValues(long cpSpecificationOptionId) {

		return cpDefinitionSpecificationOptionValuePersistence.
			findByCPSpecificationOptionId(cpSpecificationOptionId);
	}

	@Override
	public List<CPDefinitionSpecificationOptionValue>
		getCPDefinitionSpecificationOptionValues(
			long cpSpecificationOptionId, int start, int end) {

		return cpDefinitionSpecificationOptionValuePersistence.
			findByCPSpecificationOptionId(cpSpecificationOptionId, start, end);
	}

	@Override
	public List<CPDefinitionSpecificationOptionValue>
		getCPDefinitionSpecificationOptionValues(
			long cpDefinitionId, int start, int end,
			OrderByComparator<CPDefinitionSpecificationOptionValue>
				orderByComparator) {

		return cpDefinitionSpecificationOptionValuePersistence.
			findByCPDefinitionId(cpDefinitionId, start, end, orderByComparator);
	}

	@Override
	public List<CPDefinitionSpecificationOptionValue>
		getCPDefinitionSpecificationOptionValues(
			long cpDefinitionId, long cpOptionCategoryId) {

		return cpDefinitionSpecificationOptionValuePersistence.findByC_COC(
			cpDefinitionId, cpOptionCategoryId);
	}

	@Override
	public List<CPDefinitionSpecificationOptionValue>
		getCPDefinitionSpecificationOptionValuesByC_CSO(
			long cpDefinitionId, long cpSpecificationOptionId) {

		return cpDefinitionSpecificationOptionValuePersistence.findByC_CSO(
			cpDefinitionId, cpSpecificationOptionId);
	}

	@Override
	public int getCPDefinitionSpecificationOptionValuesCount(
		long cpDefinitionId) {

		return cpDefinitionSpecificationOptionValuePersistence.
			countByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public int getCPSpecificationOptionDefinitionValuesCount(
		long cpSpecificationOptionId) {

		return cpDefinitionSpecificationOptionValuePersistence.
			countByCPSpecificationOptionId(cpSpecificationOptionId);
	}

	@Override
	public CPDefinitionSpecificationOptionValue
			updateCPDefinitionSpecificationOptionValue(
				long cpDefinitionSpecificationOptionValueId,
				long cpOptionCategoryId, Map<Locale, String> valueMap,
				double priority, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product definition specification option value

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				cpDefinitionSpecificationOptionValuePersistence.
					findByPrimaryKey(cpDefinitionSpecificationOptionValueId);

		if (CPDefinitionLocalServiceCircularDependencyUtil.isVersionable(
				cpDefinitionSpecificationOptionValue.getCPDefinitionId())) {

			CPDefinition newCPDefinition =
				CPDefinitionLocalServiceCircularDependencyUtil.copyCPDefinition(
					cpDefinitionSpecificationOptionValue.getCPDefinitionId());

			cpDefinitionSpecificationOptionValue =
				cpDefinitionSpecificationOptionValuePersistence.findByC_CSOVI(
					newCPDefinition.getCPDefinitionId(),
					cpDefinitionSpecificationOptionValue.
						getCPDefinitionSpecificationOptionValueId());
		}

		cpDefinitionSpecificationOptionValue.setCPOptionCategoryId(
			cpOptionCategoryId);
		cpDefinitionSpecificationOptionValue.setValueMap(valueMap);
		cpDefinitionSpecificationOptionValue.setPriority(priority);
		cpDefinitionSpecificationOptionValue.setExpandoBridgeAttributes(
			serviceContext);

		cpDefinitionSpecificationOptionValue =
			cpDefinitionSpecificationOptionValuePersistence.update(
				cpDefinitionSpecificationOptionValue);

		// Commerce product definition

		_reindexCPDefinition(
			cpDefinitionSpecificationOptionValue.getCPDefinitionId());

		return cpDefinitionSpecificationOptionValue;
	}

	@Override
	public CPDefinitionSpecificationOptionValue updateCPOptionCategoryId(
			long cpDefinitionSpecificationOptionValueId,
			long cpOptionCategoryId)
		throws PortalException {

		// Commerce product definition specification option value

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				cpDefinitionSpecificationOptionValuePersistence.
					findByPrimaryKey(cpDefinitionSpecificationOptionValueId);

		if (CPDefinitionLocalServiceCircularDependencyUtil.isVersionable(
				cpDefinitionSpecificationOptionValue.getCPDefinitionId())) {

			CPDefinition newCPDefinition =
				CPDefinitionLocalServiceCircularDependencyUtil.copyCPDefinition(
					cpDefinitionSpecificationOptionValue.getCPDefinitionId());

			cpDefinitionSpecificationOptionValue =
				cpDefinitionSpecificationOptionValuePersistence.findByC_CSOVI(
					newCPDefinition.getCPDefinitionId(),
					cpDefinitionSpecificationOptionValue.
						getCPDefinitionSpecificationOptionValueId());
		}

		cpDefinitionSpecificationOptionValue.setCPOptionCategoryId(
			cpOptionCategoryId);

		cpDefinitionSpecificationOptionValue =
			cpDefinitionSpecificationOptionValuePersistence.update(
				cpDefinitionSpecificationOptionValue);

		// Commerce product definition

		_reindexCPDefinition(
			cpDefinitionSpecificationOptionValue.getCPDefinitionId());

		return cpDefinitionSpecificationOptionValue;
	}

	private void _reindexCPDefinition(long cpDefinitionId)
		throws PortalException {

		Indexer<CPDefinition> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPDefinition.class);

		indexer.reindex(CPDefinition.class.getName(), cpDefinitionId);
	}

	@Reference
	private CPDefinitionPersistence _cpDefinitionPersistence;

	@Reference
	private ExpandoRowLocalService _expandoRowLocalService;

	@Reference
	private UserLocalService _userLocalService;

}