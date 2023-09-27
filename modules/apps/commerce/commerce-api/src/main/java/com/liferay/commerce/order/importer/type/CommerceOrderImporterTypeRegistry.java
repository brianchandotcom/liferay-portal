/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.importer.type;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public interface CommerceOrderImporterTypeRegistry {

	public CommerceOrderImporterType getCommerceOrderImporterType(String key);

	public List<CommerceOrderImporterType> getCommerceOrderImporterTypes(
			CommerceOrder commerceOrder)
		throws PortalException;

}