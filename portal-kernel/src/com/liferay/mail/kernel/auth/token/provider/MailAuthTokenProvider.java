/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mail.kernel.auth.token.provider;

/**
 * @author Rafael Praxedes
 */
public interface MailAuthTokenProvider {

	public String getAccessToken(long companyId);

	public boolean isProtocolSupported(long companyId, String protocol);

}