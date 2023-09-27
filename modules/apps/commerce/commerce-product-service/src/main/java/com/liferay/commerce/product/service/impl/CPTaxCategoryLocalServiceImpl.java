/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.exception.CPTaxCategoryNameException;
import com.liferay.commerce.product.exception.DuplicateCPTaxCategoryException;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.base.CPTaxCategoryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPTaxCategory",
	service = AopService.class
)
public class CPTaxCategoryLocalServiceImpl
	extends CPTaxCategoryLocalServiceBaseImpl {

	@Override
	public CPTaxCategory addCPTaxCategory(
			String externalReferenceCode, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(serviceContext.getUserId());

		_validate(user.getCompanyId(), 0, externalReferenceCode, nameMap);

		long cpTaxCategoryId = counterLocalService.increment();

		CPTaxCategory cpTaxCategory = cpTaxCategoryPersistence.create(
			cpTaxCategoryId);

		cpTaxCategory.setExternalReferenceCode(externalReferenceCode);
		cpTaxCategory.setCompanyId(user.getCompanyId());
		cpTaxCategory.setUserId(user.getUserId());
		cpTaxCategory.setUserName(user.getFullName());
		cpTaxCategory.setNameMap(nameMap);
		cpTaxCategory.setDescriptionMap(descriptionMap);

		return cpTaxCategoryPersistence.update(cpTaxCategory);
	}

	@Override
	public int countCPTaxCategoriesByCompanyId(long companyId, String keyword) {
		return cpTaxCategoryFinder.countCPTaxCategoriesByCompanyId(
			companyId, keyword);
	}

	@Override
	public void deleteCPTaxCategories(long companyId) {
		cpTaxCategoryPersistence.removeByCompanyId(companyId);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPTaxCategory deleteCPTaxCategory(CPTaxCategory cpTaxCategory)
		throws PortalException {

		// Commerce product tax category

		cpTaxCategory = cpTaxCategoryPersistence.remove(cpTaxCategory);

		// Commerce product definitions

		_cpDefinitionLocalService.updateCPDefinitionsByCPTaxCategoryId(
			cpTaxCategory.getCPTaxCategoryId());

		return cpTaxCategory;
	}

	@Override
	public CPTaxCategory deleteCPTaxCategory(long cpTaxCategoryId)
		throws PortalException {

		CPTaxCategory cpTaxCategory = cpTaxCategoryPersistence.findByPrimaryKey(
			cpTaxCategoryId);

		return cpTaxCategoryLocalService.deleteCPTaxCategory(cpTaxCategory);
	}

	@Override
	public List<CPTaxCategory> findCPTaxCategoriesByCompanyId(
		long companyId, String keyword, int start, int end) {

		return cpTaxCategoryFinder.findCPTaxCategoriesByCompanyId(
			companyId, keyword, start, end);
	}

	@Override
	public List<CPTaxCategory> getCPTaxCategories(long companyId) {
		return cpTaxCategoryPersistence.findByCompanyId(companyId);
	}

	@Override
	public List<CPTaxCategory> getCPTaxCategories(
		long companyId, int start, int end,
		OrderByComparator<CPTaxCategory> orderByComparator) {

		return cpTaxCategoryPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCPTaxCategoriesCount(long companyId) {
		return cpTaxCategoryPersistence.countByCompanyId(companyId);
	}

	@Override
	public CPTaxCategory updateCPTaxCategory(
			String externalReferenceCode, long cpTaxCategoryId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap)
		throws PortalException {

		CPTaxCategory cpTaxCategory = cpTaxCategoryPersistence.findByPrimaryKey(
			cpTaxCategoryId);

		_validate(
			cpTaxCategory.getCompanyId(), cpTaxCategoryId,
			externalReferenceCode, nameMap);

		cpTaxCategory.setExternalReferenceCode(externalReferenceCode);
		cpTaxCategory.setNameMap(nameMap);
		cpTaxCategory.setDescriptionMap(descriptionMap);

		return cpTaxCategoryPersistence.update(cpTaxCategory);
	}

	private void _validate(
			long companyId, long cpTaxCategoryId, String externalReferenceCode,
			Map<Locale, String> nameMap)
		throws PortalException {

		if (Validator.isNotNull(externalReferenceCode)) {
			CPTaxCategory cpTaxCategory = cpTaxCategoryPersistence.fetchByERC_C(
				externalReferenceCode, companyId);

			if ((cpTaxCategory != null) &&
				(cpTaxCategory.getCPTaxCategoryId() != cpTaxCategoryId)) {

				throw new DuplicateCPTaxCategoryException(
					"There is another commerce tax category with external " +
						"reference code " + externalReferenceCode);
			}
		}

		_validate(nameMap);
	}

	private void _validate(Map<Locale, String> nameMap) throws PortalException {
		Locale locale = LocaleUtil.getSiteDefault();

		String name = nameMap.get(locale);

		if (Validator.isNull(name)) {
			throw new CPTaxCategoryNameException();
		}
	}

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}