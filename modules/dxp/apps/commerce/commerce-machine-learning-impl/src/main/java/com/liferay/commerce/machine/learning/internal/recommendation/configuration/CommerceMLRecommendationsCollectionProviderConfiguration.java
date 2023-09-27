/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.recommendation.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Riccardo Ferrari
 */
@ExtendedObjectClassDefinition(
	category = "assets", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.commerce.machine.learning.internal.recommendation.configuration.CommerceMLRecommendationsCollectionProviderConfiguration",
	localization = "content/Language",
	name = "commerce-ml-recommendations-collection-provider-configuration-name"
)
public interface CommerceMLRecommendationsCollectionProviderConfiguration {

	@Meta.AD(
		deflt = "false",
		name = "also-bought-product-recommendations-collection-provider-enabled",
		required = false
	)
	public boolean alsoBoughtProductRecommendationsCollectionProviderEnabled();

	@Meta.AD(
		deflt = "false",
		name = "user-personalized-recommendations-collection-provider-enabled",
		required = false
	)
	public boolean userPersonalizedRecommendationsCollectionProviderEnabled();

	@Meta.AD(
		deflt = "false",
		name = "content-based-product-recommendations-collection-provider-enabled",
		required = false
	)
	public boolean
		contentBasedProductRecommendationsCollectionProviderEnabled();

	@Meta.AD(
		deflt = "false",
		name = "you-may-also-like-product-recommendations-collection-provider-enabled",
		required = false
	)
	public boolean
		youMayAlsoLikeProductRecommendationsCollectionProviderEnabled();

}