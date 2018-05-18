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