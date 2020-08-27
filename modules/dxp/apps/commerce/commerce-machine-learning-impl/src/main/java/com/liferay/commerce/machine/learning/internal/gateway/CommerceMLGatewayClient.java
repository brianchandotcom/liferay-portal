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

package com.liferay.commerce.machine.learning.internal.gateway;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.File;
import java.io.InputStream;

/**
 * @author Riccardo Ferrari
 */
public interface CommerceMLGatewayClient {

	public File downloadCommerceMLJobResult(
			String applicationId, String resourceName,
			UnicodeProperties unicodeProperties)
		throws PortalException;

	public CommerceMLJobState getCommerceMLJobState(
			String applicationId, UnicodeProperties unicodeProperties)
		throws PortalException;

	public CommerceMLJobState startCommerceMLJob(
			UnicodeProperties unicodeProperties)
		throws PortalException;

	public void uploadCommerceMLJobResource(
			String resourceName, InputStream resourceInputStream,
			UnicodeProperties unicodeProperties)
		throws PortalException;

}