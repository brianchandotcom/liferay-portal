/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.validator.comparator;

import com.liferay.account.validator.AccountEntryValidator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Tancredi Covioli
 */
public class AccountEntryValidatorServiceWrapperPriorityComparator
	implements Comparator<ServiceWrapper<AccountEntryValidator>>, Serializable {

	public AccountEntryValidatorServiceWrapperPriorityComparator() {
		this(true);
	}

	public AccountEntryValidatorServiceWrapperPriorityComparator(
		boolean ascending) {

		_ascending = ascending;
	}

	@Override
	public int compare(
		ServiceWrapper<AccountEntryValidator> serviceWrapper1,
		ServiceWrapper<AccountEntryValidator> serviceWrapper2) {

		int priority1 = MapUtil.getInteger(
			serviceWrapper1.getProperties(), "account.entry.validator.priority",
			Integer.MAX_VALUE);
		int priority2 = MapUtil.getInteger(
			serviceWrapper2.getProperties(), "account.entry.validator.priority",
			Integer.MAX_VALUE);

		int value = Integer.compare(priority1, priority2);

		if (_ascending) {
			return value;
		}

		return Math.negateExact(value);
	}

	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}