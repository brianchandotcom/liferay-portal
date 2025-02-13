/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.internal.data.control.tasks;

import com.liferay.analytics.settings.data.control.tasks.UsersDataControlTasks;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcos Martins
 */
@Component(service = UsersDataControlTasks.class)
public class UsersDataControlTasksImpl implements UsersDataControlTasks {

	public void addEmailAddress(long companyId, String emailAddress) {
		_emailAddresses.putIfAbsent(
			companyId, Collections.synchronizedSet(new HashSet<>()));

		Set<String> emailAddresses = _emailAddresses.get(companyId);

		emailAddresses.add(emailAddress);
	}

	public void clean(long companyId) {
		_emailAddresses.remove(companyId);
	}

	public Set<String> getEmailAddresses(long companyId) {
		return new HashSet<>(
			_emailAddresses.getOrDefault(companyId, Collections.emptySet()));
	}

	private final Map<Long, Set<String>> _emailAddresses =
		new ConcurrentHashMap<>();

}