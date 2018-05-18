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

package com.liferay.commerce.internal.servlet.filter;

import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.portal.servlet.filters.authverifier.AuthVerifierFilter;

import javax.servlet.Filter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"filter.init.auth.verifier.PortalSessionAuthVerifier.urls.includes=/" + CommerceConstants.PAYMENT_SERVLET_PATH + "/*",
		"osgi.http.whiteboard.filter.name=com.liferay.commerce.internal.servlet.filter.CommercePaymentAuthVerifierFilter",
		"osgi.http.whiteboard.servlet.pattern=/" + CommerceConstants.PAYMENT_SERVLET_PATH + "/*"
	},
	service = Filter.class
)
public class CommercePaymentAuthVerifierFilter extends AuthVerifierFilter {
}