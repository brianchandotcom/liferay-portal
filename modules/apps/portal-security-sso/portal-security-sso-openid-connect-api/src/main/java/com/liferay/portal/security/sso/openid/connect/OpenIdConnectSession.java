/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author     Jesse Rao
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Deprecated
@ProviderType
public interface OpenIdConnectSession {

	public String getAccessTokenValue();

	public long getLoginTime();

	public long getLoginUserId();

	public String getNonceValue();

	public OpenIdConnectFlowState getOpenIdConnectFlowState();

	public String getOpenIdProviderName();

	public String getRefreshTokenValue();

	public String getStateValue();

	public void setOpenIdConnectFlowState(
		OpenIdConnectFlowState openIdConnectFlowState);

}