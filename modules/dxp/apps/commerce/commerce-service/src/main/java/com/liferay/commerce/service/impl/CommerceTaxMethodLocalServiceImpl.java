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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.exception.CommerceTaxMethodEngineKeyException;
import com.liferay.commerce.exception.CommerceTaxMethodNameException;
import com.liferay.commerce.model.CommerceTaxMethod;
import com.liferay.commerce.service.base.CommerceTaxMethodLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxMethodLocalServiceImpl
	extends CommerceTaxMethodLocalServiceBaseImpl {

	@Override
	public CommerceTaxMethod addCommerceTaxMethod(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String engineKey, boolean percentage, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

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

		commerceTaxMethodPersistence.update(commerceTaxMethod);

		return commerceTaxMethod;
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

		commerceTaxMethodPersistence.update(commerceTaxMethod);

		return commerceTaxMethod;
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

		commerceTaxMethodPersistence.update(commerceTaxMethod);

		return commerceTaxMethod;
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