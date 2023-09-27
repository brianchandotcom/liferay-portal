/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.anonymizer;

import com.liferay.portal.kernel.model.User;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Erick Monteiro
 */
@ProviderType
public interface UADAnonymousUserProvider {

	public User getAnonymousUser(long companyId) throws Exception;

	public boolean isAnonymousUser(User user);

}