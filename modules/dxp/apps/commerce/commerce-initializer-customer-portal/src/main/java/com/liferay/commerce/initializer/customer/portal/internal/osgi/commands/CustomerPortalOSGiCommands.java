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

package com.liferay.commerce.initializer.customer.portal.internal.osgi.commands;

import com.liferay.commerce.initializer.customer.portal.internal.CustomerPortalForecastsInitializer;
import com.liferay.commerce.initializer.customer.portal.internal.CustomerPortalLayoutsInitializer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;
import java.util.concurrent.Callable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=initCustomerPortalForecasts",
		"osgi.command.function=initCustomerPortalLayouts",
		"osgi.command.scope=commerce"
	},
	service = CustomerPortalOSGiCommands.class
)
public class CustomerPortalOSGiCommands {

	public void initCustomerPortalForecasts(final long groupId)
		throws Throwable {

		TransactionInvokerUtil.invoke(
			_transactionConfig,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					_customerPortalForecastsInitializer.initialize(groupId);

					return null;
				}

			});
	}

	public void initCustomerPortalLayouts(final long groupId) throws Throwable {
		TransactionInvokerUtil.invoke(
			_transactionConfig,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					ServiceContext serviceContext = _getServiceContext(groupId);

					_customerPortalLayoutsInitializer.initialize(
						serviceContext);

					return null;
				}

			});
	}

	private ServiceContext _getServiceContext(long groupId)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		User user = _userLocalService.getUser(group.getCreatorUserId());

		Locale locale = LocaleUtil.getSiteDefault();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(group.getCompanyId());
		serviceContext.setLanguageId(LanguageUtil.getLanguageId(locale));
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(user.getUserId());
		serviceContext.setTimeZone(user.getTimeZone());

		return serviceContext;
	}

	private static final TransactionConfig _transactionConfig;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.REQUIRES_NEW);
		builder.setRollbackForClasses(Exception.class);

		_transactionConfig = builder.build();
	}

	@Reference
	private CustomerPortalForecastsInitializer
		_customerPortalForecastsInitializer;

	@Reference
	private CustomerPortalLayoutsInitializer _customerPortalLayoutsInitializer;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}