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

package com.liferay.commerce.tax.service.impl;

import com.liferay.commerce.tax.exception.CommerceTaxMethodEngineKeyException;
import com.liferay.commerce.tax.exception.CommerceTaxMethodNameException;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.tax.service.base.CommerceTaxMethodLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.tax.model.CommerceTaxMethod",
	service = AopService.class
)
public class CommerceTaxMethodLocalServiceImpl
	extends CommerceTaxMethodLocalServiceBaseImpl {

	@Override
	public CommerceTaxMethod addCommerceTaxMethod(
			long userId, long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String engineKey,
			boolean percentage, boolean active)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		validate(nameMap, engineKey);

		long commerceTaxMethodId = counterLocalService.increment();

		CommerceTaxMethod commerceTaxMethod =
			commerceTaxMethodPersistence.create(commerceTaxMethodId);

		commerceTaxMethod.setGroupId(groupId);
		commerceTaxMethod.setCompanyId(user.getCompanyId());
		commerceTaxMethod.setUserId(user.getUserId());
		commerceTaxMethod.setUserName(user.getFullName());
		commerceTaxMethod.setNameMap(nameMap);
		commerceTaxMethod.setDescriptionMap(descriptionMap);
		commerceTaxMethod.setEngineKey(engineKey);
		commerceTaxMethod.setPercentage(percentage);
		commerceTaxMethod.setActive(active);

		return commerceTaxMethodPersistence.update(commerceTaxMethod);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommerceTaxMethod addCommerceTaxMethod(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String engineKey, boolean percentage, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		return commerceTaxMethodLocalService.addCommerceTaxMethod(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			nameMap, descriptionMap, engineKey, percentage, active);
	}

	@Override
	public void deleteCommerceTaxMethods(long groupId) throws PortalException {
		List<CommerceTaxMethod> commerceTaxMethods =
			commerceTaxMethodPersistence.findByGroupId(groupId);

		for (CommerceTaxMethod commerceTaxMethod : commerceTaxMethods) {
			commerceTaxMethodLocalService.deleteCommerceTaxMethod(
				commerceTaxMethod);
		}
	}

	@Override
	public CommerceTaxMethod fetchCommerceTaxMethod(
		long groupId, String engineKey) {

		return commerceTaxMethodPersistence.fetchByG_E(groupId, engineKey);
	}

	@Override
	public List<CommerceTaxMethod> getCommerceTaxMethods(long groupId) {
		return commerceTaxMethodPersistence.findByGroupId(groupId);
	}

	@Override
	public List<CommerceTaxMethod> getCommerceTaxMethods(
		long groupId, boolean active) {

		return commerceTaxMethodPersistence.findByG_A(groupId, active);
	}

	@Override
	public int getCommerceTaxMethodsCount(long groupId, boolean active) {
		return commerceTaxMethodPersistence.countByG_A(groupId, active);
	}

	@Override
	public CommerceTaxMethod setActive(long commerceTaxMethodId, boolean active)
		throws PortalException {

		CommerceTaxMethod commerceTaxMethod =
			commerceTaxMethodPersistence.findByPrimaryKey(commerceTaxMethodId);

		commerceTaxMethod.setActive(active);

		return commerceTaxMethodPersistence.update(commerceTaxMethod);
	}

	@Override
	public CommerceTaxMethod updateCommerceTaxMethod(
			long commerceTaxMethodId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, boolean percentage,
			boolean active)
		throws PortalException {

		CommerceTaxMethod commerceTaxMethod =
			commerceTaxMethodPersistence.findByPrimaryKey(commerceTaxMethodId);

		validate(nameMap, commerceTaxMethod.getEngineKey());

		commerceTaxMethod.setNameMap(nameMap);
		commerceTaxMethod.setDescriptionMap(descriptionMap);
		commerceTaxMethod.setPercentage(percentage);
		commerceTaxMethod.setActive(active);

		return commerceTaxMethodPersistence.update(commerceTaxMethod);
	}

	protected void validate(Map<Locale, String> nameMap, String engineKey)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		String name = nameMap.get(locale);

		if (Validator.isNull(name)) {
			throw new CommerceTaxMethodNameException();
		}

		if (Validator.isNull(engineKey)) {
			throw new CommerceTaxMethodEngineKeyException();
		}
	}

}