/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.mobile.device;

import java.io.Serializable;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Milen Dyankov
 * @author Michael C. Han
 */
@ProviderType
public interface Device extends Serializable {

	public String getBrand();

	public String getBrowser();

	public String getBrowserVersion();

	public String getModel();

	public String getOS();

	public String getOSVersion();

	public String getPointingMethod();

	public Dimensions getScreenPhysicalSize();

	public Dimensions getScreenResolution();

	public boolean hasQwertyKeyboard();

	public boolean isTablet();

}