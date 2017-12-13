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

import com.liferay.commerce.exception.CommercePaymentMethodEngineKeyException;
import com.liferay.commerce.exception.CommercePaymentMethodNameException;
import com.liferay.commerce.model.CommercePaymentEngine;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.service.base.CommercePaymentMethodLocalServiceBaseImpl;
import com.liferay.commerce.util.CommercePaymentEngineRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommercePaymentMethodLocalServiceImpl
	extends CommercePaymentMethodLocalServiceBaseImpl {

	@Override
	public CommercePaymentMethod addCommercePaymentMethod(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			File imageFile, String engineKey,
			Map<String, String> engineParameterMap, double priority,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		// Commerce payment method

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		validate(nameMap, engineKey);

		long commercePaymentMethodId = counterLocalService.increment();

		CommercePaymentMethod commercePaymentMethod =
			commercePaymentMethodPersistence.create(commercePaymentMethodId);

		commercePaymentMethod.setGroupId(groupId);
		commercePaymentMethod.setCompanyId(user.getCompanyId());
		commercePaymentMethod.setUserId(user.getUserId());
		commercePaymentMethod.setUserName(user.getFullName());
		commercePaymentMethod.setNameMap(nameMap);
		commercePaymentMethod.setDescriptionMap(descriptionMap);

		if (imageFile != null) {
			commercePaymentMethod.setImageId(counterLocalService.increment());
		}

		commercePaymentMethod.setEngineKey(engineKey);
		commercePaymentMethod.setPriority(priority);
		commercePaymentMethod.setActive(active);

		commercePaymentMethodPersistence.update(commercePaymentMethod);

		// Image

		if (imageFile != null) {
			imageLocalService.updateImage(
				commercePaymentMethod.getImageId(), imageFile);
		}

		// Commerce payment engine

		updateCommercePaymentEngineConfiguration(
			engineKey, engineParameterMap, serviceContext);

		return commercePaymentMethod;
	}

	@Override
	public CommercePaymentMethod deleteCommercePaymentMethod(
			CommercePaymentMethod commercePaymentMethod)
		throws PortalException {

		// Commerce payment method

		commercePaymentMethodPersistence.remove(commercePaymentMethod);

		// Image

		if (commercePaymentMethod.getImageId() > 0) {
			imageLocalService.deleteImage(commercePaymentMethod.getImageId());
		}

		return commercePaymentMethod;
	}

	@Override
	public CommercePaymentMethod deleteCommercePaymentMethod(
			long commercePaymentMethodId)
		throws PortalException {

		CommercePaymentMethod commercePaymentMethod =
			commercePaymentMethodPersistence.findByPrimaryKey(
				commercePaymentMethodId);

		return commercePaymentMethodLocalService.deleteCommercePaymentMethod(
			commercePaymentMethod);
	}

	@Override
	public void deleteCommercePaymentMethods(long groupId)
		throws PortalException {

		List<CommercePaymentMethod> commercePaymentMethods =
			commercePaymentMethodPersistence.findByGroupId(groupId);

		for (CommercePaymentMethod commercePaymentMethod :
				commercePaymentMethods) {

			commercePaymentMethodLocalService.deleteCommercePaymentMethod(
				commercePaymentMethod);
		}
	}

	@Override
	public List<CommercePaymentMethod> getCommercePaymentMethods(long groupId) {
		return commercePaymentMethodPersistence.findByGroupId(groupId);
	}

	@Override
	public List<CommercePaymentMethod> getCommercePaymentMethods(
		long groupId, boolean active) {

		return commercePaymentMethodPersistence.findByG_A(groupId, active);
	}

	@Override
	public int getCommercePaymentMethodsCount(long groupId, boolean active) {
		return commercePaymentMethodPersistence.countByG_A(groupId, active);
	}

	@Override
	public CommercePaymentMethod updateCommercePaymentMethod(
			long commercePaymentMethodId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, File imageFile,
			Map<String, String> engineParameterMap, double priority,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		byte[] imageBytes = null;

		try {
			imageBytes = FileUtil.getBytes(imageFile);
		}
		catch (IOException ioe) {
		}

		// Commerce payment method

		CommercePaymentMethod commercePaymentMethod =
			commercePaymentMethodPersistence.findByPrimaryKey(
				commercePaymentMethodId);

		commercePaymentMethod.setNameMap(nameMap);
		commercePaymentMethod.setDescriptionMap(descriptionMap);

		if ((imageFile != null) && (imageBytes != null)) {
			commercePaymentMethod.setImageId(counterLocalService.increment());
		}

		commercePaymentMethod.setPriority(priority);
		commercePaymentMethod.setActive(active);

		commercePaymentMethodPersistence.update(commercePaymentMethod);

		// Image

		if ((imageFile != null) && (imageBytes != null)) {
			imageLocalService.updateImage(
				commercePaymentMethod.getImageId(), imageBytes);
		}

		// Commerce payment engine

		updateCommercePaymentEngineConfiguration(
			commercePaymentMethod.getEngineKey(), engineParameterMap,
			serviceContext);

		return commercePaymentMethod;
	}

	protected void updateCommercePaymentEngineConfiguration(
			String key, Map<String, String> parameterMap,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePaymentEngine commercePaymentEngine =
			_commercePaymentEngineRegistry.getCommercePaymentEngine(key);

		try {
			commercePaymentEngine.updateConfiguration(
				parameterMap, serviceContext);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void validate(Map<Locale, String> nameMap, String engineKey)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		String name = nameMap.get(locale);

		if (Validator.isNull(name)) {
			throw new CommercePaymentMethodNameException();
		}

		if (Validator.isNull(engineKey)) {
			throw new CommercePaymentMethodEngineKeyException();
		}
	}

	@ServiceReference(type = CommercePaymentEngineRegistry.class)
	private CommercePaymentEngineRegistry _commercePaymentEngineRegistry;

}