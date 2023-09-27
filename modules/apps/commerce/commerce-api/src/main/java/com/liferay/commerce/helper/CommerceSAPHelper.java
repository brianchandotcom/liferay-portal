/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.helper;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Luca Pellizzon
 */
@ProviderType
public interface CommerceSAPHelper {

	public void addCommerceDefaultSAPEntries(long companyId, long userId)
		throws PortalException;

	public void removeCommerceDefaultSAPEntries(long companyId)
		throws PortalException;

}