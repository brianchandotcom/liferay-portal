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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.exception.CommerceShippingMethodEngineKeyException;
import com.liferay.commerce.exception.CommerceShippingMethodNameException;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.service.base.CommerceShippingMethodLocalServiceBaseImpl;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.File;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingMethodLocalServiceImpl
	extends CommerceShippingMethodLocalServiceBaseImpl {

	@Override
	public CommerceShippingMethod addCommerceShippingMethod(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			File imageFile, String engineKey, double priority, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce shipping method

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		if ((imageFile != null) && !imageFile.exists()) {
			imageFile = null;
		}

		validate(nameMap, engineKey);

		long commerceShippingMethodId = counterLocalService.increment();

		CommerceShippingMethod commerceShippingMethod =
			commerceShippingMethodPersistence.create(commerceShippingMethodId);

		commerceShippingMethod.setGroupId(groupId);
		commerceShippingMethod.setCompanyId(user.getCompanyId());
		commerceShippingMethod.setUserId(user.getUserId());
		commerceShippingMethod.setUserName(user.getFullName());
		commerceShippingMethod.setNameMap(nameMap);
		commerceShippingMethod.setDescriptionMap(descriptionMap);

		if (imageFile != null) {
			commerceShippingMethod.setImageId(counterLocalService.increment());
		}

		commerceShippingMethod.setEngineKey(engineKey);
		commerceShippingMethod.setPriority(priority);
		commerceShippingMethod.setActive(active);

		commerceShippingMethodPersistence.update(commerceShippingMethod);

		// Image

		if (imageFile != null) {
			imageLocalService.updateImage(
				commerceShippingMethod.getImageId(), imageFile);
		}

		return commerceShippingMethod;
	}

	@Override
	public CommerceShippingMethod deleteCommerceShippingMethod(
			CommerceShippingMethod commerceShippingMethod)
		throws PortalException {

		// Commerce shipping method

		commerceShippingMethodPersistence.remove(commerceShippingMethod);

		// Image

		if (commerceShippingMethod.getImageId() > 0) {
			imageLocalService.deleteImage(commerceShippingMethod.getImageId());
		}

		return commerceShippingMethod;
	}

	@Override
	public CommerceShippingMethod deleteCommerceShippingMethod(
			long commerceShippingMethodId)
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			commerceShippingMethodPersistence.findByPrimaryKey(
				commerceShippingMethodId);

		return commerceShippingMethodLocalService.deleteCommerceShippingMethod(
			commerceShippingMethod);
	}

	@Override
	public void deleteCommerceShippingMethods(long groupId)
		throws PortalException {

		List<CommerceShippingMethod> commerceShippingMethods =
			commerceShippingMethodPersistence.findByGroupId(groupId);

		for (CommerceShippingMethod commerceShippingMethod :
				commerceShippingMethods) {

			commerceShippingMethodLocalService.deleteCommerceShippingMethod(
				commerceShippingMethod);
		}
	}

	@Override
	public CommerceShippingMethod fetchCommerceShippingMethod(
		long groupId, String engineKey) {

		return commerceShippingMethodPersistence.fetchByG_E(groupId, engineKey);
	}

	@Override
	public List<CommerceShippingMethod> getCommerceShippingMethods(
		long groupId) {

		return commerceShippingMethodPersistence.findByGroupId(groupId);
	}

	@Override
	public List<CommerceShippingMethod> getCommerceShippingMethods(
		long groupId, boolean active) {

		return commerceShippingMethodPersistence.findByG_A(groupId, active);
	}

	@Override
	public int getCommerceShippingMethodsCount(long groupId, boolean active) {
		return commerceShippingMethodPersistence.countByG_A(groupId, active);
	}

	@Override
	public CommerceShippingMethod setActive(
			long commerceShippingMethodId, boolean active)
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			commerceShippingMethodPersistence.findByPrimaryKey(
				commerceShippingMethodId);

		commerceShippingMethod.setActive(active);

		commerceShippingMethodPersistence.update(commerceShippingMethod);

		return commerceShippingMethod;
	}

	@Override
	public CommerceShippingMethod updateCommerceShippingMethod(
			long commerceShippingMethodId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, File imageFile, double priority,
			boolean active)
		throws PortalException {

		// Commerce shipping method

		CommerceShippingMethod commerceShippingMethod =
			commerceShippingMethodPersistence.findByPrimaryKey(
				commerceShippingMethodId);

		if ((imageFile != null) && !imageFile.exists()) {
			imageFile = null;
		}

		commerceShippingMethod.setNameMap(nameMap);
		commerceShippingMethod.setDescriptionMap(descriptionMap);

		if ((imageFile != null) && (commerceShippingMethod.getImageId() <= 0)) {
			commerceShippingMethod.setImageId(counterLocalService.increment());
		}

		commerceShippingMethod.setPriority(priority);
		commerceShippingMethod.setActive(active);

		commerceShippingMethodPersistence.update(commerceShippingMethod);

		// Image

		if (imageFile != null) {
			imageLocalService.updateImage(
				commerceShippingMethod.getImageId(), imageFile);
		}

		return commerceShippingMethod;
	}

	protected void validate(Map<Locale, String> nameMap, String engineKey)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		String name = nameMap.get(locale);

		if (Validator.isNull(name)) {
			throw new CommerceShippingMethodNameException();
		}

		if (Validator.isNull(engineKey)) {
			throw new CommerceShippingMethodEngineKeyException();
		}
	}

	@ServiceReference(type = CommerceShippingEngineRegistry.class)
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

}