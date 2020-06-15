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

package com.liferay.commerce.data.integration.web.internal.display.context.util;

import com.liferay.commerce.data.integration.constants.CommerceDataIntegrationWebKeys;
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
import com.liferay.portal.kernel.display.context.util.BaseRequestHelper;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author guywandji
 */
public class CommerceDataIntegrationRequestHelper extends BaseRequestHelper {

	public CommerceDataIntegrationRequestHelper(RenderRequest renderRequest) {
		super(PortalUtil.getHttpServletRequest(renderRequest));
	}

	public CommerceDataIntegrationProcess getCommerceDataIntegrationProcess() {
		HttpServletRequest httpServletRequest = getRequest();

		return (CommerceDataIntegrationProcess)httpServletRequest.getAttribute(
			CommerceDataIntegrationWebKeys.COMMERCE_DATA_INTEGRATION_PROCESS);
	}

}