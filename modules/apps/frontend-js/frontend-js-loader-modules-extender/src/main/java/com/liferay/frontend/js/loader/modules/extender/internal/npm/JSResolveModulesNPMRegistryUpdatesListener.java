/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.internal.npm;

import com.liferay.frontend.js.loader.modules.extender.internal.servlet.util.JSLoaderModulesUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistryUpdatesListener;

import org.osgi.service.component.annotations.Component;

/**
 * @author Joao Victor Alves
 */
@Component(service = NPMRegistryUpdatesListener.class)
public class JSResolveModulesNPMRegistryUpdatesListener
	implements NPMRegistryUpdatesListener {

	@Override
	public void onAfterUpdate() {
		JSLoaderModulesUtil.resetJSLoaderProps();
	}

}