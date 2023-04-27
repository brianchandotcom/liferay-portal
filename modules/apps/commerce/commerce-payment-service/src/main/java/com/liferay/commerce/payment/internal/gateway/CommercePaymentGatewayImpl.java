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

package com.liferay.commerce.payment.internal.gateway;

import com.liferay.commerce.constants.CommercePaymentEntryConstants;
import com.liferay.commerce.payment.audit.CommercePaymentEntryAuditType;
import com.liferay.commerce.payment.audit.CommercePaymentEntryAuditTypeRegistry;
import com.liferay.commerce.payment.configuration.CommercePaymentEntryAuditConfiguration;
import com.liferay.commerce.payment.constants.CommercePaymentEntryAuditConstants;
import com.liferay.commerce.payment.gateway.CommercePaymentGateway;
import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.commerce.payment.service.CommercePaymentEntryAuditLocalService;
import com.liferay.commerce.payment.service.CommercePaymentEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.HashMapBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(service = CommercePaymentGateway.class)
public class CommercePaymentGatewayImpl implements CommercePaymentGateway {

	@Override
	public CommercePaymentEntry authorize(
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled("COMMERCE-11181")) {
			throw new UnsupportedOperationException();
		}

		commercePaymentEntry =
			_commercePaymentEntryLocalService.updateCommercePaymentEntry(
				commercePaymentEntry.getCommercePaymentEntryId(),
				CommercePaymentEntryConstants.STATUS_AUTHORIZED, null);

		CommercePaymentEntryAuditConfiguration
			commercePaymentEntryAuditConfiguration =
				_getCommercePaymentEntryAuditConfiguration(
					commercePaymentEntry.getCompanyId());

		if (commercePaymentEntryAuditConfiguration.enabled()) {
			CommercePaymentEntryAuditType commercePaymentEntryAuditType =
				_commercePaymentEntryAuditTypeRegistry.
					getCommercePaymentEntryAuditType(
						CommercePaymentEntryAuditConstants.
							TYPE_AUTHORIZE_PAYMENT);

			User currentUser = _userService.getCurrentUser();

			_commercePaymentEntryAuditLocalService.addCommercePaymentEntryAudit(
				currentUser.getUserId(),
				commercePaymentEntry.getCommercePaymentEntryId(),
				commercePaymentEntry.getAmount(),
				commercePaymentEntry.getCurrencyCode(),
				commercePaymentEntryAuditType.getType(),
				commercePaymentEntryAuditType.getLog(
					HashMapBuilder.<String, Object>put(
						CommercePaymentEntryAuditConstants.FIELD_CLASS_NAME_ID,
						commercePaymentEntry.getClassNameId()
					).put(
						CommercePaymentEntryAuditConstants.FIELD_CLASS_PK,
						String.valueOf(commercePaymentEntry.getClassPK())
					).build()),
				_createServiceContext(currentUser));
		}

		return commercePaymentEntry;
	}

	@Override
	public CommercePaymentEntry cancel(
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled("COMMERCE-11181")) {
			throw new UnsupportedOperationException();
		}

		commercePaymentEntry =
			_commercePaymentEntryLocalService.updateCommercePaymentEntry(
				commercePaymentEntry.getCommercePaymentEntryId(),
				CommercePaymentEntryConstants.STATUS_CANCELLED, null);

		CommercePaymentEntryAuditConfiguration
			commercePaymentEntryAuditConfiguration =
				_getCommercePaymentEntryAuditConfiguration(
					commercePaymentEntry.getCompanyId());

		if (commercePaymentEntryAuditConfiguration.enabled()) {
			CommercePaymentEntryAuditType commercePaymentEntryAuditType =
				_commercePaymentEntryAuditTypeRegistry.
					getCommercePaymentEntryAuditType(
						CommercePaymentEntryAuditConstants.TYPE_CANCEL_PAYMENT);

			User currentUser = _userService.getCurrentUser();

			_commercePaymentEntryAuditLocalService.addCommercePaymentEntryAudit(
				currentUser.getUserId(),
				commercePaymentEntry.getCommercePaymentEntryId(),
				commercePaymentEntry.getAmount(),
				commercePaymentEntry.getCurrencyCode(),
				commercePaymentEntryAuditType.getType(),
				commercePaymentEntryAuditType.getLog(
					HashMapBuilder.<String, Object>put(
						CommercePaymentEntryAuditConstants.FIELD_CLASS_NAME_ID,
						commercePaymentEntry.getClassNameId()
					).put(
						CommercePaymentEntryAuditConstants.FIELD_CLASS_PK,
						String.valueOf(commercePaymentEntry.getClassPK())
					).build()),
				_createServiceContext(currentUser));
		}

		return commercePaymentEntry;
	}

	@Override
	public CommercePaymentEntry capture(
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled("COMMERCE-11181")) {
			throw new UnsupportedOperationException();
		}

		commercePaymentEntry =
			_commercePaymentEntryLocalService.updateCommercePaymentEntry(
				commercePaymentEntry.getCommercePaymentEntryId(),
				CommercePaymentEntryConstants.STATUS_COMPLETED, null);

		CommercePaymentEntryAuditConfiguration
			commercePaymentEntryAuditConfiguration =
				_getCommercePaymentEntryAuditConfiguration(
					commercePaymentEntry.getCompanyId());

		if (commercePaymentEntryAuditConfiguration.enabled()) {
			CommercePaymentEntryAuditType commercePaymentEntryAuditType =
				_commercePaymentEntryAuditTypeRegistry.
					getCommercePaymentEntryAuditType(
						CommercePaymentEntryAuditConstants.
							TYPE_CAPTURE_PAYMENT);

			User currentUser = _userService.getCurrentUser();

			_commercePaymentEntryAuditLocalService.addCommercePaymentEntryAudit(
				currentUser.getUserId(),
				commercePaymentEntry.getCommercePaymentEntryId(),
				commercePaymentEntry.getAmount(),
				commercePaymentEntry.getCurrencyCode(),
				commercePaymentEntryAuditType.getType(),
				commercePaymentEntryAuditType.getLog(
					HashMapBuilder.<String, Object>put(
						CommercePaymentEntryAuditConstants.FIELD_CLASS_NAME_ID,
						commercePaymentEntry.getClassNameId()
					).put(
						CommercePaymentEntryAuditConstants.FIELD_CLASS_PK,
						String.valueOf(commercePaymentEntry.getClassPK())
					).build()),
				_createServiceContext(currentUser));
		}

		return commercePaymentEntry;
	}

	@Override
	public CommercePaymentEntry refund(
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled("COMMERCE-11181")) {
			throw new UnsupportedOperationException();
		}

		commercePaymentEntry =
			_commercePaymentEntryLocalService.updateCommercePaymentEntry(
				commercePaymentEntry.getCommercePaymentEntryId(),
				CommercePaymentEntryConstants.STATUS_REFUND, null);

		CommercePaymentEntryAuditConfiguration
			commercePaymentEntryAuditConfiguration =
				_getCommercePaymentEntryAuditConfiguration(
					commercePaymentEntry.getCompanyId());

		if (commercePaymentEntryAuditConfiguration.enabled()) {
			CommercePaymentEntryAuditType commercePaymentEntryAuditType =
				_commercePaymentEntryAuditTypeRegistry.
					getCommercePaymentEntryAuditType(
						CommercePaymentEntryAuditConstants.TYPE_REFUND_PAYMENT);

			User currentUser = _userService.getCurrentUser();

			_commercePaymentEntryAuditLocalService.addCommercePaymentEntryAudit(
				currentUser.getUserId(),
				commercePaymentEntry.getCommercePaymentEntryId(),
				commercePaymentEntry.getAmount(),
				commercePaymentEntry.getCurrencyCode(),
				commercePaymentEntryAuditType.getType(),
				commercePaymentEntryAuditType.getLog(
					HashMapBuilder.<String, Object>put(
						CommercePaymentEntryAuditConstants.FIELD_CLASS_NAME_ID,
						commercePaymentEntry.getClassNameId()
					).put(
						CommercePaymentEntryAuditConstants.FIELD_CLASS_PK,
						String.valueOf(commercePaymentEntry.getClassPK())
					).build()),
				_createServiceContext(currentUser));
		}

		return commercePaymentEntry;
	}

	private ServiceContext _createServiceContext(User user) {
		return new ServiceContext() {
			{
				setCompanyId(user.getCompanyId());
				setUserId(user.getUserId());
			}
		};
	}

	private CommercePaymentEntryAuditConfiguration
			_getCommercePaymentEntryAuditConfiguration(long companyId)
		throws ConfigurationException {

		return (CommercePaymentEntryAuditConfiguration)
			_configurationProvider.getCompanyConfiguration(
				CommercePaymentEntryAuditConfiguration.class, companyId);
	}

	@Reference
	private CommercePaymentEntryAuditLocalService
		_commercePaymentEntryAuditLocalService;

	@Reference
	private CommercePaymentEntryAuditTypeRegistry
		_commercePaymentEntryAuditTypeRegistry;

	@Reference
	private CommercePaymentEntryLocalService _commercePaymentEntryLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private UserService _userService;

}