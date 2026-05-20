/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.validator;

import com.liferay.account.internal.validator.comparator.AccountEntryValidatorServiceWrapperPriorityComparator;
import com.liferay.account.validator.AccountEntryValidator;
import com.liferay.account.validator.AccountEntryValidatorRegistry;
import com.liferay.account.validator.AccountEntryValidatorResult;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Tancredi Covioli
 */
@Component(service = AccountEntryValidatorRegistry.class)
public class AccountEntryValidatorRegistryImpl
	implements AccountEntryValidatorRegistry {

	@Override
	public AccountEntryValidator getAccountEntryValidator(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		ServiceWrapper<AccountEntryValidator>
			accountEntryValidatorServiceWrapper = _serviceTrackerMap.getService(
				key);

		if (accountEntryValidatorServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No account entry validator registered with key " + key);
			}

			return null;
		}

		return accountEntryValidatorServiceWrapper.getService();
	}

	@Override
	public List<AccountEntryValidator> getAccountEntryValidators() {
		List<AccountEntryValidator> accountEntryValidators = new ArrayList<>();

		List<ServiceWrapper<AccountEntryValidator>>
			accountEntryValidatorServiceWrappers = ListUtil.fromCollection(
				_serviceTrackerMap.values());

		Collections.sort(
			accountEntryValidatorServiceWrappers,
			_accountEntryValidatorServiceWrapperPriorityComparator);

		for (ServiceWrapper<AccountEntryValidator>
				accountEntryValidatorServiceWrapper :
					accountEntryValidatorServiceWrappers) {

			accountEntryValidators.add(
				accountEntryValidatorServiceWrapper.getService());
		}

		return Collections.unmodifiableList(accountEntryValidators);
	}

	@Override
	public boolean isValid(Locale locale, long accountEntryId)
		throws PortalException {

		List<AccountEntryValidatorResult> accountEntryValidatorResults =
			validate(locale, accountEntryId);

		return accountEntryValidatorResults.isEmpty();
	}

	@Override
	public boolean isValid(
			Locale locale, long accountEntryId, long commerceOrderId)
		throws PortalException {

		List<AccountEntryValidatorResult> accountEntryValidatorResults =
			validate(locale, accountEntryId, commerceOrderId);

		return accountEntryValidatorResults.isEmpty();
	}

	@Override
	public List<AccountEntryValidatorResult> validate(
			Locale locale, long accountEntryId)
		throws PortalException {

		return TransformUtil.transform(
			getAccountEntryValidators(),
			accountEntryValidator -> {
				AccountEntryValidatorResult accountEntryValidatorResult =
					accountEntryValidator.validate(locale, accountEntryId);

				if (!accountEntryValidatorResult.isValid()) {
					return accountEntryValidatorResult;
				}

				return null;
			});
	}

	@Override
	public List<AccountEntryValidatorResult> validate(
			Locale locale, long accountEntryId, long commerceOrderId)
		throws PortalException {

		return TransformUtil.transform(
			getAccountEntryValidators(),
			accountEntryValidator -> {
				AccountEntryValidatorResult accountEntryValidatorResult =
					accountEntryValidator.validate(
						locale, accountEntryId, commerceOrderId);

				if (!accountEntryValidatorResult.isValid()) {
					return accountEntryValidatorResult;
				}

				return null;
			});
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, AccountEntryValidator.class,
			"account.entry.validator.key",
			ServiceTrackerCustomizerFactory.
				<AccountEntryValidator>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEntryValidatorRegistryImpl.class);

	private static final Comparator<ServiceWrapper<AccountEntryValidator>>
		_accountEntryValidatorServiceWrapperPriorityComparator =
			new AccountEntryValidatorServiceWrapperPriorityComparator();

	private ServiceTrackerMap<String, ServiceWrapper<AccountEntryValidator>>
		_serviceTrackerMap;

}