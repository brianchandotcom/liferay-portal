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

package com.liferay.commerce.checkout.web.util;

import aQute.bnd.annotation.ProviderType;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marco Leo
 */
@ProviderType
public interface CommerceCheckoutStepServicesTracker {

	public CommerceCheckoutStep getCommerceCheckoutStep(
		String commerceCheckoutStepName);

	public List<CommerceCheckoutStep> getCommerceCheckoutSteps(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

	public CommerceCheckoutStep getNextCommerceCheckoutStep(
			String commerceCheckoutStepName,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

	public CommerceCheckoutStep getPreviousCommerceCheckoutStep(
			String commerceCheckoutStepName,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

}