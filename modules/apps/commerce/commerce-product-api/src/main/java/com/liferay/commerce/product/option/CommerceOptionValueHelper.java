/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.option;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;

import java.util.List;

/**
 * @author Igor Beslic
 */
public interface CommerceOptionValueHelper {

	public List<CommerceOptionValue> getCPDefinitionCommerceOptionValues(
			long cpDefinitionId, String json)
		throws PortalException;

	public CommerceOptionValue toCommerceOptionValue(String json)
		throws PortalException;

	public List<CommerceOptionValue> toCommerceOptionValues(String json)
		throws JSONException;

}