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
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;

import java.util.Objects;
import java.util.concurrent.Callable;

import javax.persistence.OptimisticLockException;

import javax.servlet.http.HttpServletRequest;

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
			HttpServletRequest httpServletRequest,
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		try {
			return TransactionInvokerUtil.invoke(
				_transactionConfig,
				new AuthorizeCallable(
					httpServletRequest, commercePaymentEntry));
		}
		catch (Throwable throwable) {
			throw new PortalException(throwable);
		}
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, readOnly = false,
		rollbackFor = Exception.class
	)
	public CommercePaymentEntry cancel(
			HttpServletRequest httpServletRequest,
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		try {
			return TransactionInvokerUtil.invoke(
				_transactionConfig,
				new CancelCallable(httpServletRequest, commercePaymentEntry));
		}
		catch (Throwable throwable) {
			throw new PortalException(throwable);
		}
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, readOnly = false,
		rollbackFor = Exception.class
	)
	public CommercePaymentEntry capture(
			HttpServletRequest httpServletRequest,
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		try {
			return TransactionInvokerUtil.invoke(
				_transactionConfig,
				new CaptureCallable(httpServletRequest, commercePaymentEntry));
		}
		catch (Throwable throwable) {
			throw new PortalException(throwable);
		}
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, readOnly = false,
		rollbackFor = Exception.class
	)
	public CommercePaymentEntry refund(
			HttpServletRequest httpServletRequest,
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		try {
			return TransactionInvokerUtil.invoke(
				_transactionConfig,
				new RefundCallable(httpServletRequest, commercePaymentEntry));
		}
		catch (Throwable throwable) {
			throw new PortalException(throwable);
		}
	}

	@Override
	public CommercePaymentEntry setUpPayment(
			HttpServletRequest httpServletRequest,
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		try {
			return TransactionInvokerUtil.invoke(
				_transactionConfig,
				new SetUpPaymentCallable(
					httpServletRequest, commercePaymentEntry));
		}
		catch (Throwable throwable) {
			throw new PortalException(throwable);
		}
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
		throws Exception {

		return _configurationProvider.getCompanyConfiguration(
			CommercePaymentEntryAuditConfiguration.class, companyId);
	}

	private CommercePaymentEntry _updateCommercePaymentEntry(
			CommercePaymentEntry originalCommercePaymentEntry,
			CommercePaymentEntry commercePaymentEntry)
		throws PortalException {

		return _commercePaymentEntryLocalService.updateCommercePaymentEntry(
			originalCommercePaymentEntry.getExternalReferenceCode(),
			originalCommercePaymentEntry.getCommercePaymentEntryId(),
			originalCommercePaymentEntry.getCommerceChannelId(),
			originalCommercePaymentEntry.getAmount(),
			originalCommercePaymentEntry.getCallbackURL(),
			originalCommercePaymentEntry.getCancelURL(),
			originalCommercePaymentEntry.getCurrencyCode(),
			commercePaymentEntry.getErrorMessages(),
			originalCommercePaymentEntry.getLanguageId(),
			originalCommercePaymentEntry.getNote(),
			commercePaymentEntry.getPayload(),
			originalCommercePaymentEntry.getPaymentIntegrationKey(),
			originalCommercePaymentEntry.getPaymentIntegrationType(),
			commercePaymentEntry.getPaymentStatus(),
			originalCommercePaymentEntry.getReasonKey(),
			commercePaymentEntry.getRedirectURL(),
			commercePaymentEntry.getTransactionCode(),
			originalCommercePaymentEntry.getType());
	}

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

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
	private PermissionCheckerFactory _defaultPermissionCheckerFactory;

	@Reference
	private Portal _portal;

	private class AuthorizeCallable implements Callable<CommercePaymentEntry> {

		@Override
		public CommercePaymentEntry call() throws Exception {
			CommercePaymentIntegration commercePaymentIntegration =
				_commercePaymentHelper.getCommercePaymentIntegration(
					_commercePaymentEntry.getCommerceChannelId(),
					_commercePaymentEntry.getPaymentIntegrationKey());

			CommercePaymentEntry authorizedCommercePaymentEntry =
				commercePaymentIntegration.authorize(
					_httpServletRequest, _commercePaymentEntry);

			User currentUser = _portal.getUser(_httpServletRequest);

			PermissionThreadLocal.setPermissionChecker(
				_defaultPermissionCheckerFactory.create(currentUser));

			try {
				_commercePaymentEntry =
					_commercePaymentEntryLocalService.fetchCommercePaymentEntry(
						_commercePaymentEntry.getCommercePaymentEntryId());

				_commercePaymentEntry = _updateCommercePaymentEntry(
					_commercePaymentEntry, authorizedCommercePaymentEntry);
			}
			catch (Exception exception) {
				if (exception.getCause() instanceof SystemException) {
					Throwable throwable = exception.getCause();

					if (throwable instanceof ORMException) {
						throwable = throwable.getCause();

						if (throwable instanceof OptimisticLockException) {
							_commercePaymentEntry =
								_commercePaymentEntryLocalService.
									fetchCommercePaymentEntry(
										_commercePaymentEntry.
											getCommercePaymentEntryId());

							if (_commercePaymentEntry.getPaymentStatus() !=
									CommercePaymentEntryConstants.
										STATUS_COMPLETED) {

								_commercePaymentEntry =
									_updateCommercePaymentEntry(
										_commercePaymentEntry,
										authorizedCommercePaymentEntry);
							}
						}
					}
				}
			}

			CommercePaymentEntryAuditConfiguration
				commercePaymentEntryAuditConfiguration =
					_getCommercePaymentEntryAuditConfiguration(
						_commercePaymentEntry.getCompanyId());

			if (commercePaymentEntryAuditConfiguration.enabled()) {
				CommercePaymentEntryAuditType commercePaymentEntryAuditType =
					_commercePaymentEntryAuditTypeRegistry.
						getCommercePaymentEntryAuditType(
							CommercePaymentEntryAuditConstants.
								TYPE_AUTHORIZE_PAYMENT);

				_commercePaymentEntryAuditLocalService.
					addCommercePaymentEntryAudit(
						currentUser.getUserId(),
						_commercePaymentEntry.getCommercePaymentEntryId(),
						_commercePaymentEntry.getAmount(),
						_commercePaymentEntry.getCurrencyCode(),
						commercePaymentEntryAuditType.getType(),
						commercePaymentEntryAuditType.getLog(
							HashMapBuilder.<String, Object>put(
								CommercePaymentEntryAuditConstants.
									FIELD_CLASS_NAME_ID,
								_commercePaymentEntry.getClassNameId()
							).put(
								CommercePaymentEntryAuditConstants.
									FIELD_CLASS_PK,
								String.valueOf(
									_commercePaymentEntry.getClassPK())
							).build()),
						_createServiceContext(currentUser));
			}

			return _commercePaymentEntry;
		}

		private AuthorizeCallable(
			HttpServletRequest httpServletRequest,
			CommercePaymentEntry commercePaymentEntry) {

			_httpServletRequest = httpServletRequest;
			_commercePaymentEntry = commercePaymentEntry;
		}

		private CommercePaymentEntry _commercePaymentEntry;
		private final HttpServletRequest _httpServletRequest;

	}

	private class CancelCallable implements Callable<CommercePaymentEntry> {

		@Override
		public CommercePaymentEntry call() throws Exception {
			CommercePaymentIntegration commercePaymentIntegration =
				_commercePaymentHelper.getCommercePaymentIntegration(
					_commercePaymentEntry.getCommerceChannelId(),
					_commercePaymentEntry.getPaymentIntegrationKey());

			CommercePaymentEntry cancelledCommercePaymentEntry =
				commercePaymentIntegration.cancel(
					_httpServletRequest, _commercePaymentEntry);

			User currentUser = _portal.getUser(_httpServletRequest);

			PermissionThreadLocal.setPermissionChecker(
				_defaultPermissionCheckerFactory.create(currentUser));

			_commercePaymentEntry = _updateCommercePaymentEntry(
				_commercePaymentEntryLocalService.fetchCommercePaymentEntry(
					_commercePaymentEntry.getCommercePaymentEntryId()),
				cancelledCommercePaymentEntry);

			CommercePaymentEntryAuditConfiguration
				commercePaymentEntryAuditConfiguration =
					_getCommercePaymentEntryAuditConfiguration(
						_commercePaymentEntry.getCompanyId());

			if (commercePaymentEntryAuditConfiguration.enabled()) {
				CommercePaymentEntryAuditType commercePaymentEntryAuditType =
					_commercePaymentEntryAuditTypeRegistry.
						getCommercePaymentEntryAuditType(
							CommercePaymentEntryAuditConstants.
								TYPE_CANCEL_PAYMENT);

				_commercePaymentEntryAuditLocalService.
					addCommercePaymentEntryAudit(
						currentUser.getUserId(),
						_commercePaymentEntry.getCommercePaymentEntryId(),
						_commercePaymentEntry.getAmount(),
						_commercePaymentEntry.getCurrencyCode(),
						commercePaymentEntryAuditType.getType(),
						commercePaymentEntryAuditType.getLog(
							HashMapBuilder.<String, Object>put(
								CommercePaymentEntryAuditConstants.
									FIELD_CLASS_NAME_ID,
								_commercePaymentEntry.getClassNameId()
							).put(
								CommercePaymentEntryAuditConstants.
									FIELD_CLASS_PK,
								String.valueOf(
									_commercePaymentEntry.getClassPK())
							).build()),
						_createServiceContext(currentUser));
			}

			return _commercePaymentEntry;
		}

		private CancelCallable(
			HttpServletRequest httpServletRequest,
			CommercePaymentEntry commercePaymentEntry) {

			_httpServletRequest = httpServletRequest;
			_commercePaymentEntry = commercePaymentEntry;
		}

		private CommercePaymentEntry _commercePaymentEntry;
		private final HttpServletRequest _httpServletRequest;

	}

	private class CaptureCallable implements Callable<CommercePaymentEntry> {

		@Override
		public CommercePaymentEntry call() throws Exception {
			CommercePaymentIntegration commercePaymentIntegration =
				_commercePaymentHelper.getCommercePaymentIntegration(
					_commercePaymentEntry.getCommerceChannelId(),
					_commercePaymentEntry.getPaymentIntegrationKey());

			CommercePaymentEntry capturedCommercePaymentEntry =
				commercePaymentIntegration.capture(
					_httpServletRequest, _commercePaymentEntry);

			if (!Objects.equals(
					_commercePaymentEntry, capturedCommercePaymentEntry)) {

				return _commercePaymentEntry;
			}

			User currentUser = _portal.getUser(_httpServletRequest);

			PermissionThreadLocal.setPermissionChecker(
				_defaultPermissionCheckerFactory.create(currentUser));

			try {
				_commercePaymentEntry = _updateCommercePaymentEntry(
					_commercePaymentEntryLocalService.fetchCommercePaymentEntry(
						_commercePaymentEntry.getCommercePaymentEntryId()),
					capturedCommercePaymentEntry);
			}
			catch (Exception exception) {
				if (exception.getCause() instanceof SystemException) {
					Throwable throwable = exception.getCause();

					if (throwable instanceof ORMException) {
						throwable = throwable.getCause();

						if (throwable instanceof OptimisticLockException) {
							_commercePaymentEntry =
								_commercePaymentEntryLocalService.
									fetchCommercePaymentEntry(
										_commercePaymentEntry.
											getCommercePaymentEntryId());

							if (_commercePaymentEntry.getPaymentStatus() !=
									CommercePaymentEntryConstants.
										STATUS_COMPLETED) {

								_commercePaymentEntry =
									_updateCommercePaymentEntry(
										_commercePaymentEntry,
										capturedCommercePaymentEntry);
							}
						}
					}
				}
			}

			CommercePaymentEntryAuditConfiguration
				commercePaymentEntryAuditConfiguration =
					_getCommercePaymentEntryAuditConfiguration(
						_commercePaymentEntry.getCompanyId());

			if (commercePaymentEntryAuditConfiguration.enabled()) {
				CommercePaymentEntryAuditType commercePaymentEntryAuditType =
					_commercePaymentEntryAuditTypeRegistry.
						getCommercePaymentEntryAuditType(
							CommercePaymentEntryAuditConstants.
								TYPE_CAPTURE_PAYMENT);

				_commercePaymentEntryAuditLocalService.
					addCommercePaymentEntryAudit(
						currentUser.getUserId(),
						_commercePaymentEntry.getCommercePaymentEntryId(),
						_commercePaymentEntry.getAmount(),
						_commercePaymentEntry.getCurrencyCode(),
						commercePaymentEntryAuditType.getType(),
						commercePaymentEntryAuditType.getLog(
							HashMapBuilder.<String, Object>put(
								CommercePaymentEntryAuditConstants.
									FIELD_CLASS_NAME_ID,
								_commercePaymentEntry.getClassNameId()
							).put(
								CommercePaymentEntryAuditConstants.
									FIELD_CLASS_PK,
								String.valueOf(
									_commercePaymentEntry.getClassPK())
							).build()),
						_createServiceContext(currentUser));
			}

			return _commercePaymentEntry;
		}

		private CaptureCallable(
			HttpServletRequest httpServletRequest,
			CommercePaymentEntry commercePaymentEntry) {

			_httpServletRequest = httpServletRequest;
			_commercePaymentEntry = commercePaymentEntry;
		}

		private CommercePaymentEntry _commercePaymentEntry;
		private final HttpServletRequest _httpServletRequest;

	}

	private class RefundCallable implements Callable<CommercePaymentEntry> {

		@Override
		public CommercePaymentEntry call() throws Exception {
			CommercePaymentIntegration commercePaymentIntegration =
				_commercePaymentHelper.getCommercePaymentIntegration(
					_commercePaymentEntry.getCommerceChannelId(),
					_commercePaymentEntry.getPaymentIntegrationKey());

			CommercePaymentEntry refundedCommercePaymentEntry =
				commercePaymentIntegration.refund(
					_httpServletRequest, _commercePaymentEntry);

			User currentUser = _portal.getUser(_httpServletRequest);

			PermissionThreadLocal.setPermissionChecker(
				_defaultPermissionCheckerFactory.create(currentUser));

			_commercePaymentEntryLocalService.fetchCommercePaymentEntry(
				_commercePaymentEntry.getCommercePaymentEntryId());

			_commercePaymentEntry =
				_commercePaymentEntryLocalService.updateCommercePaymentEntry(
					_commercePaymentEntry.getExternalReferenceCode(),
					_commercePaymentEntry.getCommercePaymentEntryId(),
					_commercePaymentEntry.getCommerceChannelId(),
					_commercePaymentEntry.getAmount(),
					_commercePaymentEntry.getCallbackURL(),
					_commercePaymentEntry.getCancelURL(),
					_commercePaymentEntry.getCurrencyCode(),
					refundedCommercePaymentEntry.getErrorMessages(),
					_commercePaymentEntry.getLanguageId(),
					_commercePaymentEntry.getNote(),
					_commercePaymentEntry.getPayload(),
					_commercePaymentEntry.getPaymentIntegrationKey(),
					_commercePaymentEntry.getPaymentIntegrationType(),
					refundedCommercePaymentEntry.getPaymentStatus(),
					_commercePaymentEntry.getReasonKey(),
					refundedCommercePaymentEntry.getRedirectURL(),
					refundedCommercePaymentEntry.getTransactionCode(),
					_commercePaymentEntry.getType());

			CommercePaymentEntryAuditConfiguration
				commercePaymentEntryAuditConfiguration =
					_getCommercePaymentEntryAuditConfiguration(
						_commercePaymentEntry.getCompanyId());

			if (commercePaymentEntryAuditConfiguration.enabled()) {
				CommercePaymentEntryAuditType commercePaymentEntryAuditType =
					_commercePaymentEntryAuditTypeRegistry.
						getCommercePaymentEntryAuditType(
							CommercePaymentEntryAuditConstants.
								TYPE_REFUND_PAYMENT);

				_commercePaymentEntryAuditLocalService.
					addCommercePaymentEntryAudit(
						currentUser.getUserId(),
						_commercePaymentEntry.getCommercePaymentEntryId(),
						_commercePaymentEntry.getAmount(),
						_commercePaymentEntry.getCurrencyCode(),
						commercePaymentEntryAuditType.getType(),
						commercePaymentEntryAuditType.getLog(
							HashMapBuilder.<String, Object>put(
								CommercePaymentEntryAuditConstants.
									FIELD_CLASS_NAME_ID,
								_commercePaymentEntry.getClassNameId()
							).put(
								CommercePaymentEntryAuditConstants.
									FIELD_CLASS_PK,
								String.valueOf(
									_commercePaymentEntry.getClassPK())
							).build()),
						_createServiceContext(currentUser));
			}

			return _commercePaymentEntry;
		}

		private RefundCallable(
			HttpServletRequest httpServletRequest,
			CommercePaymentEntry commercePaymentEntry) {

			_httpServletRequest = httpServletRequest;
			_commercePaymentEntry = commercePaymentEntry;
		}

		private CommercePaymentEntry _commercePaymentEntry;
		private final HttpServletRequest _httpServletRequest;

	}

	private class SetUpPaymentCallable
		implements Callable<CommercePaymentEntry> {

		@Override
		public CommercePaymentEntry call() throws Exception {
			CommercePaymentIntegration commercePaymentIntegration =
				_commercePaymentHelper.getCommercePaymentIntegration(
					_commercePaymentEntry.getCommerceChannelId(),
					_commercePaymentEntry.getPaymentIntegrationKey());

			return commercePaymentIntegration.setUpPayment(
				_httpServletRequest, _commercePaymentEntry);
		}

		private SetUpPaymentCallable(
			HttpServletRequest httpServletRequest,
			CommercePaymentEntry commercePaymentEntry) {

			_httpServletRequest = httpServletRequest;
			_commercePaymentEntry = commercePaymentEntry;
		}

		private CommercePaymentEntry _commercePaymentEntry;
		private final HttpServletRequest _httpServletRequest;

	}

}