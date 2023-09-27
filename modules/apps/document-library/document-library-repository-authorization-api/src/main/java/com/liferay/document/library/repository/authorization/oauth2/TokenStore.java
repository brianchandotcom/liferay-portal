/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.authorization.oauth2;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Adolfo Pérez
 */
public interface TokenStore {

	public void delete(String configurationId, long userId)
		throws PortalException;

	public Token get(String configurationId, long userId)
		throws PortalException;

	public Token getFresh(String configurationId, long userId)
		throws PortalException;

	public void save(String configurationId, long userId, Token token)
		throws PortalException;

}