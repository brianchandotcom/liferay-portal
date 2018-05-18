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

package com.liferay.commerce.wish.list.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(category = "commerce")
@Meta.OCD(
	id = "com.liferay.commerce.wish.list.internal.configuration.CommerceWishListConfiguration",
	localization = "content/Language",
	name = "commerce-wish-list-configuration-name"
)
public interface CommerceWishListConfiguration {

	@Meta.AD(deflt = "15", name = "wish-list-check-interval", required = false)
	public int checkInterval();

	@Meta.AD(
		deflt = "43200", name = "wish-list-delete-interval", required = false
	)
	public int deleteInterval();

	@Meta.AD(
		deflt = "10000",
		name = "commerce-wish-list-guest-wish-list-max-allowed",
		required = false
	)
	public int guestWishListMaxAllowed();

	@Meta.AD(
		deflt = "1000",
		name = "commerce-wish-list-guest-wish-list-item-max-allowed",
		required = false
	)
	public int guestWishListItemMaxAllowed();

}