/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.keymanager.spi.profile;

/**
 * @author Tomas Polesovsky
 * @author Christopher Kian
 */
public interface KeyManagerProfile {

	public void bootstrap() throws Exception;

	public String getCompanyDekProviderId();

	public String getCompanyKekProviderId();

	public String getCompanySecretProviderId();

	public String getProfileId();

	public String getSystemDekProviderId();

	public String getSystemKekProviderId();

	public String getSystemSecretProviderId();

}