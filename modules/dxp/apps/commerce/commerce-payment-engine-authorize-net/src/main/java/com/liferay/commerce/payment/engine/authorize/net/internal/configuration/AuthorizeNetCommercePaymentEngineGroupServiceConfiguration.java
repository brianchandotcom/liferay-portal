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

package com.liferay.commerce.payment.engine.authorize.net.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

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

	@Meta.AD(name = "api-login-id", required = false)
	public String apiLoginId();

	@Meta.AD(name = "environment", required = false)
	public String environment();

	@Meta.AD(name = "require-captcha", required = false)
	public boolean requireCaptcha();

	@Meta.AD(name = "require-card-code-verification", required = false)
	public boolean requireCardCodeVerification();

	@Meta.AD(
		deflt = StringPool.TRUE, name = "show-bank-account", required = false
	)
	public boolean showBankAccount();

	@Meta.AD(
		deflt = StringPool.TRUE, name = "show-credit-card", required = false
	)
	public boolean showCreditCard();

	@Meta.AD(
		deflt = StringPool.TRUE, name = "show-store-name", required = false
	)
	public boolean showStoreName();

	@Meta.AD(name = "transaction-key", required = false)
	public String transactionKey();

}