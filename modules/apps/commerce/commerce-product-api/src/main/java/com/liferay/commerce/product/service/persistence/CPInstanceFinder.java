/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Marco Leo
 * @generated
 */
@ProviderType
public interface CPInstanceFinder {

	public java.util.List<com.liferay.commerce.product.model.CPInstance>
		findByExpirationDate(
			java.util.Date expirationDate,
			com.liferay.portal.kernel.dao.orm.QueryDefinition
				<com.liferay.commerce.product.model.CPInstance>
					queryDefinition);

}