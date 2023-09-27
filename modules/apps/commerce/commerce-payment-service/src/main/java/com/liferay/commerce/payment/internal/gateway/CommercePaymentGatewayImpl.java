/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.gateway;

import com.liferay.commerce.constants.CommercePaymentEntryConstants;
import com.liferay.commerce.payment.audit.CommercePaymentEntryAuditType;
import com.liferay.commerce.payment.audit.CommercePaymentEntryAuditTypeRegistry;
import com.liferay.commerce.payment.configuration.CommercePaymentEntryAuditConfiguration;
import com.liferay.commerce.payment.constants.CommercePaymentEntryAuditConstants;
import com.liferay.commerce.payment.gateway.CommercePaymentGateway;
import com.liferay.commerce.payment.integration.CommercePaymentIntegration;
import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.commerce.payment.service.CommercePaymentEntryAuditLocalService;
import com.liferay.commerce.payment.service.CommercePaymentEntryLocalService;
import com.liferay.commerce.payment.util.CommercePaymentHelper;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.HashMapBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(service = CommercePaymentGateway.class)
public class CommercePaymentGatewayImpl implements CommercePaymentGateway {

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, readOnly = false,
		rollbackFor = Exception.class
	)
	public CommercePaymentEntry authorize(
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled("COMMERCE-11181")) {
			throw new UnsupportedOperationException();
		}

		CommercePaymentIntegration commercePaymentIntegration =
			_commercePaymentHelper.getCommercePaymentIntegration(
				commercePaymentEntry.getCommerceChannelId(),
				commercePaymentEntry.getPaymentIntegrationKey());

		CommercePaymentEntry authorizedCommercePaymentEntry =
			commercePaymentIntegration.authorize(commercePaymentEntry);

		commercePaymentEntry =
			_commercePaymentEntryLocalService.updateCommercePaymentEntry(
				commercePaymentEntry.getCommercePaymentEntryId(),
				CommercePaymentEntryConstants.STATUS_AUTHORIZED,
				authorizedCommercePaymentEntry.getTransactionCode());

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
	@Transactional(
		propagation = Propagation.REQUIRED, readOnly = false,
		rollbackFor = Exception.class
	)
	public CommercePaymentEntry cancel(
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled("COMMERCE-11181")) {
			throw new UnsupportedOperationException();
		}

		CommercePaymentIntegration commercePaymentIntegration =
			_commercePaymentHelper.getCommercePaymentIntegration(
				commercePaymentEntry.getCommerceChannelId(),
				commercePaymentEntry.getPaymentIntegrationKey());

		CommercePaymentEntry cancelledCommercePaymentEntry =
			commercePaymentIntegration.cancel(commercePaymentEntry);

		commercePaymentEntry =
			_commercePaymentEntryLocalService.updateCommercePaymentEntry(
				commercePaymentEntry.getCommercePaymentEntryId(),
				CommercePaymentEntryConstants.STATUS_CANCELLED,
				cancelledCommercePaymentEntry.getTransactionCode());

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
	@Transactional(
		propagation = Propagation.REQUIRED, readOnly = false,
		rollbackFor = Exception.class
	)
	public CommercePaymentEntry capture(
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled("COMMERCE-11181")) {
			throw new UnsupportedOperationException();
		}

		CommercePaymentIntegration commercePaymentIntegration =
			_commercePaymentHelper.getCommercePaymentIntegration(
				commercePaymentEntry.getCommerceChannelId(),
				commercePaymentEntry.getPaymentIntegrationKey());

		CommercePaymentEntry capturedCommercePaymentEntry =
			commercePaymentIntegration.capture(commercePaymentEntry);

		commercePaymentEntry =
			_commercePaymentEntryLocalService.updateCommercePaymentEntry(
				commercePaymentEntry.getCommercePaymentEntryId(),
				CommercePaymentEntryConstants.STATUS_COMPLETED,
				capturedCommercePaymentEntry.getTransactionCode());

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
	@Transactional(
		propagation = Propagation.REQUIRED, readOnly = false,
		rollbackFor = Exception.class
	)
	public CommercePaymentEntry refund(
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled("COMMERCE-11181")) {
			throw new UnsupportedOperationException();
		}

		CommercePaymentIntegration commercePaymentIntegration =
			_commercePaymentHelper.getCommercePaymentIntegration(
				commercePaymentEntry.getCommerceChannelId(),
				commercePaymentEntry.getPaymentIntegrationKey());

		CommercePaymentEntry refundedCommercePaymentEntry =
			commercePaymentIntegration.refund(commercePaymentEntry);

		commercePaymentEntry =
			_commercePaymentEntryLocalService.updateCommercePaymentEntry(
				commercePaymentEntry.getCommercePaymentEntryId(),
				CommercePaymentEntryConstants.STATUS_REFUND,
				refundedCommercePaymentEntry.getTransactionCode());

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
	private CommercePaymentHelper _commercePaymentHelper;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private UserService _userService;

}