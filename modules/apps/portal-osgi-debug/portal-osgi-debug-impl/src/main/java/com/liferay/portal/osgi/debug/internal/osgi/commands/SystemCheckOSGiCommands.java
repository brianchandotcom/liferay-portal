/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.debug.internal.osgi.commands;

import com.liferay.portal.kernel.util.SystemCheckerUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tina Tian
 */
@Component(
	property = {"osgi.command.function=check", "osgi.command.scope=system"},
	service = SystemCheckOSGiCommands.class
)
public class SystemCheckOSGiCommands {

	public void check() {
		SystemCheckerUtil.runSystemCheckers(
			System.out::println, System.out::println);
	}

}