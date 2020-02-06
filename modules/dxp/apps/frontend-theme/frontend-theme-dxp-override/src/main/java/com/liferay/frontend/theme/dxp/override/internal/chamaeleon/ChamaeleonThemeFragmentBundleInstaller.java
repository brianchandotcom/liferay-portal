/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.frontend.theme.dxp.override.internal.chameleon;

import com.liferay.frontend.theme.dxp.override.internal.BaseThemeFragmentBundleInstaller;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(enabled = false, immediate = true, service = {})
public class ChameleonThemeFragmentBundleInstaller
	extends BaseThemeFragmentBundleInstaller {

	@Override
	protected String getHostBundleSymbolicName() {
		return "chameleon-theme";
	}

	@Override
	protected String[] getResources() {
		return new String[] {"favicon.ico", "screenshot.png", "thumbnail.png"};
	}

}