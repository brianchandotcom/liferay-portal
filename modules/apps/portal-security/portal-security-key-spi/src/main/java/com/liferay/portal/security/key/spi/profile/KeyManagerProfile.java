/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.spi.profile;

/**
 * @author Tomas Polesovsky
 * @author Christopher Kian
 */
public interface KeyManagerProfile {

	public String getCompanyDEKProviderId();

	public String getCompanyKEKProviderId();

	public String getCompanySecretProviderId();

	public String getProfileId();

	public String getSystemDEKProviderId();

	public String getSystemKEKProviderId();

	public String getSystemSecretProviderId();

	public void initialize() throws Exception;

}