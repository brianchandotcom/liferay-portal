/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.constants.CPOptionCategoryConstants;
import com.liferay.commerce.product.exception.CPOptionCategoryKeyException;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.base.CPOptionCategoryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPOptionCategoryLocalServiceImpl
	extends CPOptionCategoryLocalServiceBaseImpl {

	@Override
	public CPOptionCategory addCPOptionCategory(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			double priority, String key, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product option category

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		key = FriendlyURLNormalizerUtil.normalize(key);

		validate(0, groupId, key);

		long cpOptionCategoryId = counterLocalService.increment();

		CPOptionCategory cpOptionCategory = cpOptionCategoryPersistence.create(
			cpOptionCategoryId);

		cpOptionCategory.setUuid(serviceContext.getUuid());
		cpOptionCategory.setGroupId(groupId);
		cpOptionCategory.setCompanyId(user.getCompanyId());
		cpOptionCategory.setUserId(user.getUserId());
		cpOptionCategory.setUserName(user.getFullName());
		cpOptionCategory.setTitleMap(titleMap);
		cpOptionCategory.setDescriptionMap(descriptionMap);
		cpOptionCategory.setPriority(priority);
		cpOptionCategory.setKey(key);

		cpOptionCategoryPersistence.update(cpOptionCategory);

		// Resources

		resourceLocalService.addModelResources(
			cpOptionCategory, serviceContext);

		return cpOptionCategory;
	}

	@Override
	public void deleteCPOptionCategories(long groupId) throws PortalException {
		List<CPOptionCategory> cpOptionCategories =
			cpOptionCategoryPersistence.findByGroupId(groupId);

		for (CPOptionCategory cpOptionCategory : cpOptionCategories) {
			cpOptionCategoryLocalService.deleteCPOptionCategory(
				cpOptionCategory);
		}
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPOptionCategory deleteCPOptionCategory(
			CPOptionCategory cpOptionCategory)
		throws PortalException {

		// Commerce product option category

		cpOptionCategoryPersistence.remove(cpOptionCategory);

		// Commerce product specification options

		List<CPSpecificationOption> cpSpecificationOptions =
			cpSpecificationOptionPersistence.findByCPOptionCategoryId(
				cpOptionCategory.getCPOptionCategoryId());

		for (CPSpecificationOption cpSpecificationOption :
				cpSpecificationOptions) {

			cpSpecificationOptionLocalService.updateCPOptionCategoryId(
				cpSpecificationOption.getCPSpecificationOptionId(),
				CPOptionCategoryConstants.DEFAULT_CP_OPTION_CATEGORY_ID);
		}

		// Commerce product definition specification option values

		List<CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues =
				cpDefinitionSpecificationOptionValuePersistence.
					findByCPOptionCategoryId(
						cpOptionCategory.getCPOptionCategoryId());

		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					cpDefinitionSpecificationOptionValues) {

			cpDefinitionSpecificationOptionValueLocalService.
				updateCPOptionCategoryId(
					cpDefinitionSpecificationOptionValue.
						getCPDefinitionSpecificationOptionValueId(),
					CPOptionCategoryConstants.DEFAULT_CP_OPTION_CATEGORY_ID);
		}

		// Resources

		resourceLocalService.deleteResource(
			cpOptionCategory.getCompanyId(), CPOptionCategory.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			cpOptionCategory.getCPOptionCategoryId());

		return cpOptionCategory;
	}

	@Override
	public CPOptionCategory deleteCPOptionCategory(long cpOptionCategoryId)
		throws PortalException {

		CPOptionCategory cpOptionCategory =
			cpOptionCategoryPersistence.findByPrimaryKey(cpOptionCategoryId);

		return cpOptionCategoryLocalService.deleteCPOptionCategory(
			cpOptionCategory);
	}

	@Override
	public CPOptionCategory fetchCPOptionCategory(long groupId, String key) {
		return cpOptionCategoryPersistence.fetchByG_K(groupId, key);
	}

	@Override
	public List<CPOptionCategory> getCPOptionCategories(
		long groupId, int start, int end) {

		return cpOptionCategoryPersistence.findByGroupId(groupId, start, end);
	}

	@Override
	public List<CPOptionCategory> getCPOptionCategories(
		long groupId, int start, int end,
		OrderByComparator<CPOptionCategory> orderByComparator) {

		return cpOptionCategoryPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCPOptionCategoriesCount(long groupId) {
		return cpOptionCategoryPersistence.countByGroupId(groupId);
	}

	@Override
	public CPOptionCategory updateCPOptionCategory(
			long cpOptionCategoryId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, double priority, String key,
			ServiceContext serviceContext)
		throws PortalException {

		CPOptionCategory cpOptionCategory =
			cpOptionCategoryPersistence.findByPrimaryKey(cpOptionCategoryId);

		key = FriendlyURLNormalizerUtil.normalize(key);

		validate(
			cpOptionCategory.getCPOptionCategoryId(),
			cpOptionCategory.getGroupId(), key);

		cpOptionCategory.setTitleMap(titleMap);
		cpOptionCategory.setDescriptionMap(descriptionMap);
		cpOptionCategory.setPriority(priority);
		cpOptionCategory.setKey(key);

		cpOptionCategoryPersistence.update(cpOptionCategory);

		return cpOptionCategory;
	}

	protected void validate(long cpOptionCategoryId, long groupId, String key)
		throws PortalException {

		CPOptionCategory cpOptionCategory =
			cpOptionCategoryPersistence.fetchByG_K(groupId, key);

		if ((cpOptionCategory != null) &&
			(cpOptionCategory.getCPOptionCategoryId() != cpOptionCategoryId)) {

			throw new CPOptionCategoryKeyException();
		}
	}

}