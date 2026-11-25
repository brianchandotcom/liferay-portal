/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.util.comparator;

import com.liferay.launch.details.LaunchDetails;

import java.util.Comparator;

/**
 * @author Lourdes Fernández Besada
 */
public class LaunchDetailsNameComparator
	implements Comparator<LaunchDetails> {

	public static final Comparator<LaunchDetails> INSTANCE =
		new LaunchDetailsNameComparator();

	@Override
	public int compare(
		LaunchDetails launchDetails1,
		LaunchDetails launchDetails2) {

		String name1 = launchDetails1.getName();
		String name2 = launchDetails2.getName();

		return name1.compareToIgnoreCase(name2);
	}

	private LaunchDetailsNameComparator() {
	}

}