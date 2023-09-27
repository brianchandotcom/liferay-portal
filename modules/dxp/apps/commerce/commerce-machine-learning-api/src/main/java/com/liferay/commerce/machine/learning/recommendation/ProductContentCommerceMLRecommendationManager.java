/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.recommendation;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Riccardo Ferrai
 */
public interface ProductContentCommerceMLRecommendationManager {

	public ProductContentCommerceMLRecommendation
			addProductContentCommerceMLRecommendation(
				ProductContentCommerceMLRecommendation
					productContentCommerceMLRecommendation)
		throws PortalException;

	public ProductContentCommerceMLRecommendation create();

	public List<ProductContentCommerceMLRecommendation>
			getProductContentCommerceMLRecommendations(
				long companyId, long cpDefinition)
		throws PortalException;

}