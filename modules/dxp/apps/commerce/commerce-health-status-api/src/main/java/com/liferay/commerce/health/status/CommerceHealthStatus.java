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

package com.liferay.commerce.health.status;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public interface CommerceHealthStatus {

	public void fixIssue(HttpServletRequest httpServletRequest)
		throws PortalException;

	public String getDescription(Locale locale);

	public String getKey();

	public String getName(Locale locale);

	public boolean isFixed(long groupId) throws PortalException;

}