/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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