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

package com.liferay.commerce.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Marco Leo
 */
@ExtendedObjectClassDefinition(category = "commerce")
@Meta.OCD(
	id = "com.liferay.commerce.configuration.CommerceOrderConfiguration",
	localization = "content/Language", name = "orders-configuration-name"
)
public interface CommerceOrderConfiguration {

	@Meta.AD(deflt = "15", name = "order-check-interval", required = false)
	public int checkInterval();

	@Meta.AD(deflt = "43200", name = "order-delete-interval", required = false)
	public int deleteInterval();

	@Meta.AD(deflt = "10000", name = "guest-cart-max-allowed", required = false)
	public int guestCartMaxAllowed();

	@Meta.AD(
		deflt = "1000", name = "guest-cart-item-max-allowed", required = false
	)
	public int guestCartItemMaxAllowed();

}