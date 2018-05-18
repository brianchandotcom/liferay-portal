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

package com.liferay.commerce.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.CommercePaymentEngineException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Locale;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Andrea Di Giorgi
 */
@ProviderType
public interface CommercePaymentEngine {

	public CommercePaymentEngineResult cancelPayment(
			CommerceOrder commerceOrder, ServiceContext serviceContext)
		throws CommercePaymentEngineException;

	public CommercePaymentEngineResult completePayment(
			CommerceOrder commerceOrder, ServiceContext serviceContext)
		throws CommercePaymentEngineException;

	public String getDescription(Locale locale);

	public String getName(Locale locale);

	public void renderConfiguration(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception;

	public CommercePaymentEngineResult.StartPayment startPayment(
			CommerceOrder commerceOrder, String cancelURL, String returnURL,
			ServiceContext serviceContext)
		throws CommercePaymentEngineException;

	public void updateConfiguration(
			Map<String, String> parameterMap, ServiceContext serviceContext)
		throws Exception;

}