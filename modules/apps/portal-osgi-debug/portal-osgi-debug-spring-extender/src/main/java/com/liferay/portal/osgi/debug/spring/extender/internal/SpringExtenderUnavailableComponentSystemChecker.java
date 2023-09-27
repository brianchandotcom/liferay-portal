/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.debug.spring.extender.internal;

import com.liferay.portal.osgi.debug.SystemChecker;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tina Tian
 */
@Component(service = SystemChecker.class)
public class SpringExtenderUnavailableComponentSystemChecker
	implements SystemChecker {

	@Override
	public String check() {
		return UnavailableComponentUtil.scanUnavailableComponents();
	}

	@Override
	public String getName() {
		return "Spring Extender Unavailable Component Checker";
	}

	@Override
	public String getOSGiCommand() {
		return "dm na";
	}

	@Override
	public String toString() {
		return getName();
	}

}