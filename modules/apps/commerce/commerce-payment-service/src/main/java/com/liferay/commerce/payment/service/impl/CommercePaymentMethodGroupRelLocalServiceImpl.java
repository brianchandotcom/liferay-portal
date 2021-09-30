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

package com.liferay.commerce.payment.service.impl;

import com.liferay.commerce.model.CommerceAddressRestriction;
import com.liferay.commerce.payment.exception.CommercePaymentMethodGroupRelEngineKeyException;
import com.liferay.commerce.payment.exception.CommercePaymentMethodGroupRelNameException;
import com.liferay.commerce.payment.exception.NoSuchPaymentMethodGroupRelException;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.base.CommercePaymentMethodGroupRelLocalServiceBaseImpl;
import com.liferay.commerce.service.CommerceAddressRestrictionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Luca Pellizzon
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommercePaymentMethodGroupRelLocalServiceImpl
	extends CommercePaymentMethodGroupRelLocalServiceBaseImpl {

	@Override
	public CommerceAddressRestriction addCommerceAddressRestriction(
			long userId, long groupId, long commercePaymentMethodGroupRelId,
			long countryId)
		throws PortalException {

		return _commerceAddressRestrictionLocalService.
			addCommerceAddressRestriction(
				userId, groupId, CommercePaymentMethodGroupRel.class.getName(),
				commercePaymentMethodGroupRelId, countryId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommerceAddressRestriction addCommerceAddressRestriction(
			long commercePaymentMethodGroupRelId, long countryId,
			ServiceContext serviceContext)
		throws PortalException {

		return commercePaymentMethodGroupRelLocalService.
			addCommerceAddressRestriction(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				commercePaymentMethodGroupRelId, countryId);
	}

	@Override
	public CommercePaymentMethodGroupRel addCommercePaymentMethodGroupRel(
			long userId, long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, File imageFile,
			String engineKey, double priority, boolean active)
		throws PortalException {

		// Commerce payment method

		if ((imageFile != null) && !imageFile.exists()) {
			imageFile = null;
		}

		validate(nameMap, engineKey);

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			commercePaymentMethodGroupRelPersistence.create(
				counterLocalService.increment());

		commercePaymentMethodGroupRel.setGroupId(groupId);

		User user = userLocalService.getUser(userId);

		commercePaymentMethodGroupRel.setCompanyId(user.getCompanyId());
		commercePaymentMethodGroupRel.setUserId(user.getUserId());
		commercePaymentMethodGroupRel.setUserName(user.getFullName());

		commercePaymentMethodGroupRel.setNameMap(nameMap);
		commercePaymentMethodGroupRel.setDescriptionMap(descriptionMap);

		if (imageFile != null) {
			commercePaymentMethodGroupRel.setImageId(
				counterLocalService.increment());
		}

		commercePaymentMethodGroupRel.setEngineKey(engineKey);
		commercePaymentMethodGroupRel.setPriority(priority);
		commercePaymentMethodGroupRel.setActive(active);

		commercePaymentMethodGroupRel =
			commercePaymentMethodGroupRelPersistence.update(
				commercePaymentMethodGroupRel);

		// Image

		if (imageFile != null) {
			_imageLocalService.updateImage(
				commercePaymentMethodGroupRel.getCompanyId(),
				commercePaymentMethodGroupRel.getImageId(), imageFile);
		}

		return commercePaymentMethodGroupRel;
	}

	@Override
	public void deleteCommerceAddressRestriction(
			long commerceAddressRestrictionId)
		throws PortalException {

		_commerceAddressRestrictionLocalService.
			deleteCommerceAddressRestriction(commerceAddressRestrictionId);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommercePaymentMethodGroupRel deleteCommercePaymentMethodGroupRel(
			CommercePaymentMethodGroupRel commercePaymentMethodGroupRel)
		throws PortalException {

		// Commerce payment method

		commercePaymentMethodGroupRelPersistence.remove(
			commercePaymentMethodGroupRel);

		// Image

		if (commercePaymentMethodGroupRel.getImageId() > 0) {
			_imageLocalService.deleteImage(
				commercePaymentMethodGroupRel.getImageId());
		}

		// Commerce address restrictions

		_commerceAddressRestrictionLocalService.
			deleteCommerceAddressRestrictions(
				CommercePaymentMethodGroupRel.class.getName(),
				commercePaymentMethodGroupRel.
					getCommercePaymentMethodGroupRelId());

		return commercePaymentMethodGroupRel;
	}

	@Override
	public CommercePaymentMethodGroupRel deleteCommercePaymentMethodGroupRel(
			long commercePaymentMethodGroupRelId)
		throws PortalException {

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			commercePaymentMethodGroupRelPersistence.findByPrimaryKey(
				commercePaymentMethodGroupRelId);

		return commercePaymentMethodGroupRelLocalService.
			deleteCommercePaymentMethodGroupRel(commercePaymentMethodGroupRel);
	}

	@Override
	public void deleteCommercePaymentMethodGroupRels(long groupId)
		throws PortalException {

		List<CommercePaymentMethodGroupRel> commercePaymentMethodGroupRels =
			commercePaymentMethodGroupRelPersistence.findByGroupId(groupId);

		for (CommercePaymentMethodGroupRel commercePaymentMethodGroupRel :
				commercePaymentMethodGroupRels) {

			commercePaymentMethodGroupRelLocalService.
				deleteCommercePaymentMethodGroupRel(
					commercePaymentMethodGroupRel);
		}
	}

	@Override
	public CommercePaymentMethodGroupRel fetchCommercePaymentMethodGroupRel(
		long groupId, String engineKey) {

		return commercePaymentMethodGroupRelPersistence.fetchByG_E(
			groupId, engineKey);
	}

	@Override
	public List<CommerceAddressRestriction> getCommerceAddressRestrictions(
		long commercePaymentMethodGroupRelId, int start, int end,
		OrderByComparator<CommerceAddressRestriction> orderByComparator) {

		return _commerceAddressRestrictionLocalService.
			getCommerceAddressRestrictions(
				CommercePaymentMethodGroupRel.class.getName(),
				commercePaymentMethodGroupRelId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceAddressRestrictionsCount(
		long commercePaymentMethodGroupRelId) {

		return _commerceAddressRestrictionLocalService.
			getCommerceAddressRestrictionsCount(
				CommercePaymentMethodGroupRel.class.getName(),
				commercePaymentMethodGroupRelId);
	}

	@Override
	public CommercePaymentMethodGroupRel getCommercePaymentMethodGroupRel(
			long groupId, String engineKey)
		throws NoSuchPaymentMethodGroupRelException {

		return commercePaymentMethodGroupRelPersistence.findByG_E(
			groupId, engineKey);
	}

	@Override
	public List<CommercePaymentMethodGroupRel>
		getCommercePaymentMethodGroupRels(long groupId) {

		return commercePaymentMethodGroupRelPersistence.findByGroupId(groupId);
	}

	@Override
	public List<CommercePaymentMethodGroupRel>
		getCommercePaymentMethodGroupRels(long groupId, boolean active) {

		return commercePaymentMethodGroupRelPersistence.findByG_A(
			groupId, active);
	}

	@Override
	public List<CommercePaymentMethodGroupRel>
		getCommercePaymentMethodGroupRels(
			long groupId, boolean active, int start, int end) {

		return commercePaymentMethodGroupRelPersistence.findByG_A(
			groupId, active, start, end);
	}

	@Override
	public List<CommercePaymentMethodGroupRel>
		getCommercePaymentMethodGroupRels(
			long groupId, boolean active, int start, int end,
			OrderByComparator<CommercePaymentMethodGroupRel>
				orderByComparator) {

		return commercePaymentMethodGroupRelPersistence.findByG_A(
			groupId, active, start, end, orderByComparator);
	}

	@Override
	public List<CommercePaymentMethodGroupRel>
		getCommercePaymentMethodGroupRels(
			long groupId, int start, int end,
			OrderByComparator<CommercePaymentMethodGroupRel>
				orderByComparator) {

		return commercePaymentMethodGroupRelPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<CommercePaymentMethodGroupRel>
		getCommercePaymentMethodGroupRels(
			long groupId, long countryId, boolean active) {

		List<CommercePaymentMethodGroupRel>
			filteredCommercePaymentMethodGroupRels = new ArrayList<>();

		List<CommercePaymentMethodGroupRel> commercePaymentMethodGroupRels =
			commercePaymentMethodGroupRelPersistence.findByG_A(groupId, active);

		for (CommercePaymentMethodGroupRel commercePaymentMethodGroupRel :
				commercePaymentMethodGroupRels) {

			boolean restricted =
				_commerceAddressRestrictionLocalService.
					isCommerceAddressRestricted(
						CommercePaymentMethodGroupRel.class.getName(),
						commercePaymentMethodGroupRel.
							getCommercePaymentMethodGroupRelId(),
						countryId);

			if (!restricted) {
				filteredCommercePaymentMethodGroupRels.add(
					commercePaymentMethodGroupRel);
			}
		}

		return filteredCommercePaymentMethodGroupRels;
	}

	@Override
	public int getCommercePaymentMethodGroupRelsCount(long groupId) {
		return commercePaymentMethodGroupRelPersistence.countByGroupId(groupId);
	}

	@Override
	public int getCommercePaymentMethodGroupRelsCount(
		long groupId, boolean active) {

		return commercePaymentMethodGroupRelPersistence.countByG_A(
			groupId, active);
	}

	@Override
	public CommercePaymentMethodGroupRel setActive(
			long commercePaymentMethodGroupRelId, boolean active)
		throws PortalException {

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			commercePaymentMethodGroupRelPersistence.findByPrimaryKey(
				commercePaymentMethodGroupRelId);

		commercePaymentMethodGroupRel.setActive(active);

		return commercePaymentMethodGroupRelPersistence.update(
			commercePaymentMethodGroupRel);
	}

	@Override
	public CommercePaymentMethodGroupRel updateCommercePaymentMethodGroupRel(
			long commercePaymentMethodGroupRelId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, File imageFile, double priority,
			boolean active)
		throws PortalException {

		// Commerce payment method

		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel =
			commercePaymentMethodGroupRelPersistence.findByPrimaryKey(
				commercePaymentMethodGroupRelId);

		if ((imageFile != null) && !imageFile.exists()) {
			imageFile = null;
		}

		commercePaymentMethodGroupRel.setNameMap(nameMap);
		commercePaymentMethodGroupRel.setDescriptionMap(descriptionMap);

		if ((imageFile != null) &&
			(commercePaymentMethodGroupRel.getImageId() <= 0)) {

			commercePaymentMethodGroupRel.setImageId(
				counterLocalService.increment());
		}

		commercePaymentMethodGroupRel.setPriority(priority);
		commercePaymentMethodGroupRel.setActive(active);

		commercePaymentMethodGroupRel =
			commercePaymentMethodGroupRelPersistence.update(
				commercePaymentMethodGroupRel);

		// Image

		if (imageFile != null) {
			_imageLocalService.updateImage(
				commercePaymentMethodGroupRel.getImageId(), imageFile);
		}

		return commercePaymentMethodGroupRel;
	}

	protected void validate(Map<Locale, String> nameMap, String engineKey)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		String name = nameMap.get(locale);

		if (Validator.isNull(name)) {
			throw new CommercePaymentMethodGroupRelNameException();
		}

		if (Validator.isNull(engineKey)) {
			throw new CommercePaymentMethodGroupRelEngineKeyException();
		}
	}

	@ServiceReference(type = CommerceAddressRestrictionLocalService.class)
	private CommerceAddressRestrictionLocalService
		_commerceAddressRestrictionLocalService;

	@ServiceReference(type = ImageLocalService.class)
	private ImageLocalService _imageLocalService;

}