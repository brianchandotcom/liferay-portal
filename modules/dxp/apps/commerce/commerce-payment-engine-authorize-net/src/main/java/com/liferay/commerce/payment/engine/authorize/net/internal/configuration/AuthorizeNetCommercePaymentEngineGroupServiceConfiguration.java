/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.payment.engine.authorize.net.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Andrea Di Giorgi
 */
@ExtendedObjectClassDefinition(
	category = "commerce", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.commerce.payment.engine.authorize.net.internal.configuration.AuthorizeNetCommercePaymentEngineGroupServiceConfiguration",
	localization = "content/Language",
	name = "commerce-payment-engine-authorize-net-group-service-configuration-name"
)
public interface AuthorizeNetCommercePaymentEngineGroupServiceConfiguration {

	@Meta.AD(required = false)
	public String apiLoginId();

	@Meta.AD(required = false)
	public String environment();

	@Meta.AD(required = false)
	public boolean requireCaptcha();

	@Meta.AD(required = false)
	public boolean requireCardCodeVerification();

	@Meta.AD(deflt = StringPool.TRUE, required = false)
	public boolean showBankAccount();

	@Meta.AD(deflt = StringPool.TRUE, required = false)
	public boolean showCreditCard();

	@Meta.AD(deflt = StringPool.TRUE, required = false)
	public boolean showStoreName();

	@Meta.AD(required = false)
	public String transactionKey();

}