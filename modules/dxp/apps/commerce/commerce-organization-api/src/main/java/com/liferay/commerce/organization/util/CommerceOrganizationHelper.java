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

package com.liferay.commerce.organization.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
@ProviderType
public interface CommerceOrganizationHelper {

	public String getCommerceUserPortletURL(
			HttpServletRequest httpServletRequest)
		throws PortalException;

	public Organization getCurrentOrganization(
			HttpServletRequest httpServletRequest)
		throws PortalException;

	public void setCurrentOrganization(
		HttpServletRequest httpServletRequest, long organizationId);

}