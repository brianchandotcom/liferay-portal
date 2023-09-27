/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import org.osgi.framework.BundleContext;

/**
 * @author Stian Sigvartsen
 */
public class SetupAdminAutoLoginStartupAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		_bundleContext.registerService(
			AutoLogin.class, new SetupAdminAutoLogin(),
			HashMapDictionaryBuilder.put(
				"component.name", SetupAdminAutoLogin.class.getName()
			).build());
	}

	private final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

}